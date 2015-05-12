package com.ookamijin.cheskers;

import java.util.List;

import android.util.Log;

import com.ookamijin.framework.Game;
import com.ookamijin.framework.Graphics;
import com.ookamijin.framework.Input.TouchEvent;
import com.ookamijin.framework.Screen;

public class PlayerSelectScreen extends Screen {

	boolean robotGame = false;
	Player player1, player2;

	public PlayerSelectScreen(Game game) {
		super(game);

	}

	public PlayerSelectScreen(Game game, boolean robotGame) {
		super(game);

		this.robotGame = robotGame;
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
			if (event.type == TouchEvent.TOUCH_UP) {
				// player is het
				if (event.x < 400) {
					player1 = new PlayerHet();
					player2 = new PlayerHom();
					if (robotGame) {
						player2.setRobot(true);
					}
					game.setScreen(new GameScreen(game, player1, player2));

				} else {
					player1 = new PlayerHom();
					player2 = new PlayerHet();
					if (robotGame) {
						player2.setRobot(true);
					}
					game.setScreen(new GameScreen(game, player1, player2));
				}
			}
			

		}
	}

	@Override
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();
		g.drawImage(Assets.selectScreen, 0, 0);

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
