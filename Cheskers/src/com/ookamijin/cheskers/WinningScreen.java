package com.ookamijin.cheskers;

import java.util.List;

import android.util.Log;

import com.ookamijin.framework.Game;
import com.ookamijin.framework.Graphics;
import com.ookamijin.framework.Screen;
import com.ookamijin.framework.Graphics.ImageFormat;
import com.ookamijin.framework.Input.TouchEvent;

public class WinningScreen extends Screen {

	Graphics g;
	Player winner;

	public WinningScreen(Game game) {
		super(game);

	}

	public WinningScreen(Game game, Player winner) {
		super(game);

		this.winner = winner; 
		g = game.getGraphics();
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
				game.setScreen(new MainMenuScreen(game));
			}
		}
	}

	@Override
	public void paint(float deltaTime) {

		if (winner.isHet)
			g.drawImage(Assets.hetWin, 0, 0);
		else
			g.drawImage(Assets.homWin, 0, 0);

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
