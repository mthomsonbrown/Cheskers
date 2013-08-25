package com.ookamijin.cheskers;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.ookamijin.framework.Game;
import com.ookamijin.framework.Graphics;
import com.ookamijin.framework.Image;
import com.ookamijin.framework.Input.TouchEvent;
import com.ookamijin.framework.Screen;

public class GameScreen extends Screen {

	private static ChipYellow yellowChips[] = new ChipYellow[16];
	private static ChipRed redChips[] = new ChipRed[16];

	private Chip userChip;

	private Image yellowChip, redChip;
	private Board mBoard;
	private Paint paint;
	private Player player;
	private ArrayList<Coord> tilePath;

	private PlayerHet hetPlay;
	private PlayerHom homPlay;
	private boolean endTurn;

	public GameScreen(Game game) {
		super(game);
		initChip();
		mBoard = new Board();

		// TODO hard coded to start with hom
		hetPlay = new PlayerHet(mBoard);
		homPlay = new PlayerHom(mBoard);
		player = hetPlay;

		paint = new Paint();
		paint.setTextSize(20);
		paint.setTextAlign(Paint.Align.LEFT);
		paint.setAntiAlias(true);
		paint.setColor(Color.BLACK);
		tilePath = new ArrayList<Coord>();
	}

	private void initChip() {

		yellowChip = Assets.chipYellow;
		redChip = Assets.chipRed;
		for (int i = 0; i < 16; ++i) {
			yellowChips[i] = new ChipYellow(Board.topInitX(i),
					Board.topInitY(i), i);

			redChips[i] = new ChipRed(Board.botInitX(i), Board.botInitY(i), i);

		}

	}

	@Override
	public void update(float deltaTime) {

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

					int tNum[] = { -1, -1 };
					coord.parse(tNum);

					if (coord.isValid()
							&& !mBoard.mTile[tNum[0]][tNum[1]].hasNothing()) {

						if (mBoard.mTile[tNum[0]][tNum[1]].hasYellow()) {
							userChip = yellowChips[mBoard.mTile[tNum[0]][tNum[1]]
									.getChipIndex()];
							userChip.setId(mBoard.mTile[tNum[0]][tNum[1]]
									.getChipIndex());
							tilePath.add(coord);
						}

						if (mBoard.mTile[tNum[0]][tNum[1]].hasRed()) {
							debug("Hes red");
							userChip = redChips[mBoard.mTile[tNum[0]][tNum[1]]
									.getChipIndex()];
							userChip.setId(mBoard.mTile[tNum[0]][tNum[1]]
									.getChipIndex());
							tilePath.add(coord);
						}
					}
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

			// decide if tile path is legal, affect necessary chips, center
			// user chip on target tile, and clear tile array
			if (event.type == TouchEvent.TOUCH_UP) {

				Coord chipEndPos = new Coord(0, 0);
				ArrayList<Coord> targets = new ArrayList<Coord>();

				if (legalMove()) {
					if (player.isValid(tilePath, userChip, targets)) {

						chipEndPos = mBoard.getTileCenter(tilePath.get(tilePath
								.size() - 1));
						userChip.setCoords(chipEndPos);

						// update tiles
						mBoard.setTileHasNothing(tilePath.get(0));
						mBoard.setTileChipIndex(tilePath.get(0), 0);

						if (userChip.isRed()) {
							mBoard.setTileHasRed(tilePath.get(tilePath.size() - 1));
							mBoard.setTileChipIndex(
									tilePath.get(tilePath.size() - 1),
									userChip.getId());
						} else {
							mBoard.setTileHasYellow(tilePath.get(tilePath
									.size() - 1));
							mBoard.setTileChipIndex(
									tilePath.get(tilePath.size() - 1),
									userChip.getId());
						}

						endTurn = true;
						debug("valid move!");

					} else {
						chipEndPos = mBoard.getTileCenter(tilePath.get(0));
						userChip.setCoords(chipEndPos);

						debug("invalid move");
					}
				} else {
					if (tilePath.size() > 0) {
						chipEndPos = mBoard.getTileCenter(tilePath.get(0));
						userChip.setCoords(chipEndPos);
					}
					debug("illegal move!");

				}

				for (int j = 0; j < tilePath.size(); ++j) {
					debug("tilePath " + j + " is " + tilePath.get(j).x + ", "
							+ tilePath.get(j).y);
				}
				debug("Score is now: " + player.getScore());
				displayBoardStatus();
				tilePath.clear();

				if (endTurn) {
					if (player.isHet) {
						take(targets);
						player = homPlay;
					} else {
						take(targets);
						player = hetPlay;
					}
					endTurn = false;
				}
			}

		}
	}

	private void take(ArrayList<Coord> targets) {
		debug("intake targets size is " + targets.size());
		Chip tChip;
		for (int i = 0; i < targets.size(); ++i) {

			if (mBoard.tileHasRed(targets.get(i))) {
				debug("IT was reDD!");
				tChip = redChips[mBoard.getTileChipIndex(targets.get(i))];
				tChip.setCenterX(player.poolX);
				tChip.setCenterY(player.poolY);
			} else {
				tChip = yellowChips[mBoard.getTileChipIndex(targets.get(i))];
				tChip.setCenterX(player.poolX);
				tChip.setCenterY(player.poolY);
			}
			mBoard.setTileHasNothing(targets.get(i));
		}

	}

	private boolean legalMove() {

		// empty landing spot
		if (tilePath.size() == 0)
			return false;
		if (!mBoard.tileIsEmpty(tilePath.get(tilePath.size() - 1)))
			return false;

		// start doesn't equal end
		if (tilePath.get(0).equals(tilePath.get(tilePath.size() - 1)))
			return false;

		return true;
	}

	private boolean touchedBoard(TouchEvent event) {
		if (event.x >= 160 && event.x <= 640)
			return true;
		else
			return false;
	}

	@Override
	public void paint(float deltaTime) {

		Graphics g = game.getGraphics();
		g.drawImage(Assets.background, 0, 0);
		for (int i = 0; i < 16; ++i) {
			g.drawImage(yellowChip, yellowChips[i].getCenterX() - 40,
					yellowChips[i].getCenterY() - 40);
			g.drawImage(redChip, redChips[i].getCenterX() - 40,
					redChips[i].getCenterY() - 40);
		}
		g.drawString(player.name, 10, 30, paint);
		g.drawString("" + hetPlay.score, hetPlay.scoreLocation.x,
				hetPlay.scoreLocation.y, paint);
		g.drawString("" + homPlay.score, homPlay.scoreLocation.x,
				homPlay.scoreLocation.y, paint);
	}

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

	public void takeYellow(int index) {

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
