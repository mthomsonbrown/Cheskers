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
	private ArrayList<int[]> tilePath;

	public GameScreen(Game game) {
		super(game);
		player = new Player();
		initChip();
		mBoard = new Board();

		paint = new Paint();
		paint.setTextSize(20);
		paint.setTextAlign(Paint.Align.LEFT);
		paint.setAntiAlias(true);
		paint.setColor(Color.BLACK);
		tilePath = new ArrayList<int[]>();
	}

	private void initChip() {

		yellowChip = Assets.chipYellow;
		redChip = Assets.chipRed;
		for (int i = 0; i < 16; ++i) {
			yellowChips[i] = new ChipYellow(Board.topInitX(i),
					Board.topInitY(i));

			redChips[i] = new ChipRed(Board.botInitX(i), Board.botInitY(i));

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

				if (touchedBoard(event)) {
					int tNum[] = mBoard.getTileIndex(event);
					debug("tile is " + tNum[0] + ", " + tNum[1]);

					if (tNum[0] != -1) {

						if (mBoard.mTile[tNum[0]][tNum[1]].hasYellow()) {
							userChip = yellowChips[mBoard.mTile[tNum[0]][tNum[1]]
									.getChipIndex()];

							debug("user chip is yellow number "
									+ mBoard.mTile[tNum[0]][tNum[1]]
											.getChipIndex());
						}

						if (mBoard.mTile[tNum[0]][tNum[1]].hasRed()) {
							userChip = redChips[mBoard.mTile[tNum[0]][tNum[1]]
									.getChipIndex()];

							debug("user chip is red number "
									+ mBoard.mTile[tNum[0]][tNum[1]]
											.getChipIndex());
						}
					}
				}
			}

			if (event.type == TouchEvent.TOUCH_DRAGGED) {

				if (userChip != null) {
					userChip.setCenterX(event.x);
					userChip.setCenterY(event.y);

					int coord[] = mBoard.getTileIndex(event);
					if (coord[0] != -1) {
						if (tilePath.size() < 1)
							tilePath.add(coord);
						else {

							int comp[] = tilePath.get(tilePath.size() - 1);
							if (comp[0] != coord[0] || comp[1] != coord[1])

								tilePath.add(coord);
						}
					}

				}
			}

			if (event.type == TouchEvent.TOUCH_UP) {

				for (int j = 0; j < tilePath.size(); ++j) {
					int coord[] = tilePath.get(j);
					debug("coord " + j + " is " + coord[0] + ", " + coord[1]);
				}
				tilePath.clear();
			}
		}

	}

	private boolean touchedBoard(TouchEvent event) {
		if (event.x >= 160 && event.x <= 640)
			return true;
		else
			return false;
	}

	private boolean inBounds(TouchEvent event, int x, int y, int width,
			int height) {
		if (event.x > x && event.x < x + width - 1 && event.y > y
				&& event.y < y + height - 1)
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
