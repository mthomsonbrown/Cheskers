package com.ookamijin.cheskers;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.widget.Toast;

import com.ookamijin.framework.Game;
import com.ookamijin.framework.Graphics;
import com.ookamijin.framework.Image;
import com.ookamijin.framework.Input.TouchEvent;
import com.ookamijin.framework.Screen;

public class GameScreen extends Screen {

	/**
	 * userchip only passes color information to player
	 */
	private Chip userChip;
	public ArrayList<Coord> tilePath;

	private Image yellowChip, redChip;
	private Image hetOn, hetOff, homOn, homOff;
	private Board mBoard;
	private Paint paint;
	private Player player;
	private int robotThought = 50;

	private PlayerHet hetPlay;
	private PlayerHom homPlay;
	private boolean endTurn;

	static final int HET_STARTS = 0;
	static final int HOM_STARTS = 1;

	public GameScreen(Game game, int startingPlayer) {
		super(game);
		if (startingPlayer == HET_STARTS) {

		}
	}

	public GameScreen(Game game, Player player1, Player player2) {
		super(game);

		mBoard = new Board();
		tilePath = new ArrayList<Coord>();
		loadAssets();
		

		setStartingPlayer(player1, player2);

		buildPaint();

	}

	private void loadAssets() {
		hetOn = Assets.hetOn;
		homOff = Assets.homOff;
		hetOff = Assets.hetOff;
		homOn = Assets.homOn;

		yellowChip = Assets.chipYellow;
		redChip = Assets.chipRed;

	}

	private void buildPaint() {
		paint = new Paint();
		paint.setTextSize(20);
		paint.setTextAlign(Paint.Align.LEFT);
		paint.setAntiAlias(true);
		paint.setColor(Color.BLACK);
	}

	private void setStartingPlayer(Player player1, Player player2) {
		if (player1.isHet) {
			hetPlay = (PlayerHet) player1;
			homPlay = (PlayerHom) player2;
			player = hetPlay;

		} else {
			hetPlay = (PlayerHet) player2;
			homPlay = (PlayerHom) player1;
			player = homPlay;

		}
		hetPlay.setBoard(mBoard);
		homPlay.setBoard(mBoard);
	}

	@Override
	public void update(float deltaTime) {

		mBoard.update();

		if (player.isRobot) {
			--robotThought;
			if(robotThought < 1) {
			debug("player is robot!");

			tilePath = player.doRobot(mBoard);
			userChip = mBoard.getChip(tilePath.get(0));
			userChip.setId(mBoard.getTileChipIndex(tilePath.get(0)));
			processMove();
			robotThought = 50;
			}
		} else
			handleTouchEvents();

	}

	private void handleTouchEvents() {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {

			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_DOWN) {

				// find what chip and what tile was touched
				if (touchedBoard(event)) {
					Coord coord = mBoard.getTileIndex(event);

					if (coord.isValid() && !mBoard.tileHasNothing(coord)) {

						if (mBoard.tileHasYellow(coord)) {
							userChip = mBoard.getChip(coord);
							userChip.setId(mBoard.getTileChipIndex(coord));
							tilePath.add(coord);
						}

						if (mBoard.tileHasRed(coord)) {
							userChip = mBoard.getChip(coord);
							userChip.setId(mBoard.getTileChipIndex(coord));
							tilePath.add(coord);
						}
					}
				} else if (touchedReset(event)) {
					game.setScreen(new MainMenuScreen(game));
				}
			}

			// track chip location and build array of tiles visited
			if (event.type == TouchEvent.TOUCH_DRAGGED) {

				if (userChip != null) {
					userChip.setCenterX(event.x);
					userChip.setCenterY(event.y);

					Coord coord = mBoard.getTileIndex(event);
					if (coord.isValid()) {

						if (tilePath.size() < 1) {

							tilePath.add(coord);
						} else {
							Coord comp = tilePath.get(tilePath.size() - 1);
							if (!coord.equals(comp)) {

								tilePath.add(coord);
							}
						}
					}
				}
			}

			if (event.type == TouchEvent.TOUCH_UP) {

				processMove();
			}

		}
	}

	// decide if tile path is legal, affect necessary chips, center
	// user chip on target tile, and clear tile array
	private void processMove() {
		Coord chipEndPos = new Coord(0, 0);
		ArrayList<Coord> targets = new ArrayList<Coord>();

		if (legalMove()) {
			if (player.isValid(tilePath, userChip, targets)) {

				chipEndPos = mBoard
						.getTileCenter(tilePath.get(tilePath.size() - 1));

				if (player.isRobot()) {
					userChip.setNextCoord(chipEndPos);
				} else
					userChip.setCoords(chipEndPos);

				// update tiles
				mBoard.setTileHasNothing(tilePath.get(0));
				mBoard.setTileChipIndex(tilePath.get(0), 0);

				if (userChip.isRed()) {
					mBoard.setTileHasRed(tilePath.get(tilePath.size() - 1));
					mBoard.setTileChipIndex(tilePath.get(tilePath.size() - 1),
							userChip.getId());
				} else {
					mBoard.setTileHasYellow(tilePath.get(tilePath.size() - 1));
					mBoard.setTileChipIndex(tilePath.get(tilePath.size() - 1),
							userChip.getId());
				}

				if (player.isBonus(tilePath, userChip) && targets.size() > 0) {
					endTurn = false;
					take(targets);
					if (player.score >= 13)
						gameWon();
				} else
					endTurn = true;

			} else {
				chipEndPos = mBoard.getTileCenter(tilePath.get(0));
				userChip.setCoords(chipEndPos);
			}
		} else {
			if (tilePath.size() > 0) {
				debug("something's null... chipEndPos = " + chipEndPos.getX()
						+ ", " + chipEndPos.getY());
				debug("tilePath.get(0) is " + tilePath.get(0).getX() + ", "
						+ tilePath.get(0).getY());
				debug("userchip is " + userChip.getCenterX() + ", "
						+ userChip.getCenterY());

				chipEndPos = mBoard.getTileCenter(tilePath.get(0));
				userChip.setCoords(chipEndPos);
			}

		}

		// after move stuff
		for (int j = 0; j < tilePath.size(); ++j) {
			debug("tilePath " + j + " is " + tilePath.get(j).x + ", "
					+ tilePath.get(j).y);
		}
		debug("Score is now: " + player.getScore());
		displayBoardStatus();
		tilePath.clear();
		userChip = null;

		if (endTurn) {
			debug("Reached end of Turn!");
			take(targets);
			if (player.score >= 13)
				gameWon();
			else {
				if (player.isHet) {
					player = homPlay;

				} else {
					player = hetPlay;
				}
			}

			endTurn = false;
		}

		// end of giant pain in the ass function "processMove"
	}

	private void gameWon() {

		game.setScreen(new WinningScreen(game, player));

	}

	private void take(ArrayList<Coord> targets) {
		debug("intake targets size is " + targets.size());
		Chip tChip;
		for (int i = 0; i < targets.size(); ++i) {

			tChip = mBoard.getChip(targets.get(i));
			mBoard.setTileHasNothing(targets.get(i));

			tChip.setNextCoord(player.getPoolCoord());

		}

	}

	/**
	 * only checks tilePath for validity
	 * 
	 * @return
	 */
	private boolean legalMove() {

		// empty landing spot
		if (tilePath.size() == 0) {
			debug("illegal, empty landing spot");
			return false;
		}

		if (!mBoard.tileIsEmpty(tilePath.get(tilePath.size() - 1))) {
			debug("illegal, tile not empty");
			return false;
		}

		// start doesn't equal end
		if (tilePath.get(0).equals(tilePath.get(tilePath.size() - 1))) {
			debug("illegal, start doesn't equal end");
			return false;
		}

		return true;
	}

	private boolean touchedBoard(TouchEvent event) {
		if (event.x >= 160 && event.x <= 640)
			return true;
		else
			return false;
	}

	private boolean touchedReset(TouchEvent event) {

		// rough position of reset button.
		if (event.y >= 415 && event.x >= 725) {
			return true;
		}
		return false;
	}

	@Override
	public void paint(float deltaTime) {

		Graphics g = game.getGraphics();
		g.drawImage(Assets.background, 0, 0);
		for (int i = 0; i < 16; ++i) {
			g.drawImage(yellowChip, mBoard.getYellowChipX(i) - 40,
					mBoard.getYellowChipY(i) - 40);
			g.drawImage(redChip, mBoard.getRedChipX(i) - 40,
					mBoard.getRedChipY(i) - 40);
		}

		// draw player names
		if (player.isHet) {
			g.drawImage(hetOn, hetPlay.getNameX(), hetPlay.getNameY());
			g.drawImage(homOff, homPlay.getNameX(), homPlay.getNameY());
		} else if (player.isHom) {
			g.drawImage(hetOff, hetPlay.getNameX(), hetPlay.getNameY());
			g.drawImage(homOn, homPlay.getNameX(), homPlay.getNameY());
		}

		g.drawString("" + hetPlay.score, hetPlay.scoreLocation.x,
				hetPlay.scoreLocation.y, paint);
		g.drawString("" + homPlay.score, homPlay.scoreLocation.x,
				homPlay.scoreLocation.y, paint);
	}

	// debug method
	private void displayBoardStatus() {

		String line = "";
		for (int j = 0; j < 6; ++j) {
			for (int i = 0; i < 6; ++i) {
				Coord coord = new Coord(i, j);
				line = line + " " + mBoard.tileStatus(coord);
			}
			debug(line);
			line = "";
		}

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void backButton() {
		// TODO Auto-generated method stub

	}

	private void debug(String message) {
		Log.d("DEBUG", message);
	}

}
