package com.ookamijin.cheskers;

import java.util.List;

import android.util.Log;


import com.ookamijin.framework.Game;
import com.ookamijin.framework.Graphics;
import com.ookamijin.framework.Input.TouchEvent;
import com.ookamijin.framework.Screen;

public class WinningScreen extends Screen {

	Graphics g;
	Player winner;
	int height = 480;


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
		g.drawImage(Assets.winBackground, 0, 0);

		if (winner.isHet)
			g.drawImage(Assets.hetWin, 0, height);
		else
			g.drawImage(Assets.homWin, 0, height);

		if (height > 0)
			--height;

		/*TODO add fireworks that are in random locations and 
		 * equal the total number of points ahead the winner was
		 * */
		//g.drawImage(fire.getImage(), 500, 250);

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
