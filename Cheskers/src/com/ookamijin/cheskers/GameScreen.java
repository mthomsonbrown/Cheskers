package com.ookamijin.cheskers;

import java.util.List;

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

	public GameScreen(Game game) {
		super(game);
		initChip(); 
		mBoard = new Board();
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
					int tNum = mBoard.getTileIndex(event);
					if (mBoard.mTile[tNum].hasYellow()) {
						userChip = yellowChips[mBoard.mTile[tNum]
								.getChipIndex()];
					}
					if (mBoard.mTile[tNum].hasRed()) {
						userChip = redChips[mBoard.mTile[tNum].getChipIndex()];
					}
				}
			}

			if (event.type == TouchEvent.TOUCH_DRAGGED) {
				debug("isdragged");

				if (userChip != null) {
					userChip.setCenterX(event.x);
					userChip.setCenterY(event.y);
				}
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
