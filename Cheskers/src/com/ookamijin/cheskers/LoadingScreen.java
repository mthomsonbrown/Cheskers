package com.ookamijin.cheskers;

import com.ookamijin.framework.Game;
import com.ookamijin.framework.Graphics;
import com.ookamijin.framework.Graphics.ImageFormat;
import com.ookamijin.framework.Screen;

public class LoadingScreen extends Screen {

	float displayTime;
	boolean doneLoading = false;

	public LoadingScreen(Game game) {
		super(game);
		displayTime = 0;
	}

	@Override
	public void update(float deltaTime) {
		Graphics g = game.getGraphics();

		Assets.background = g.newImage("background.jpg", ImageFormat.RGB565);
		Assets.chipRed = g.newImage("chipRed.png", ImageFormat.ARGB4444);
		Assets.chipYellow = g.newImage("chipYellow.png", ImageFormat.ARGB4444);

		doneLoading = true;

	}

	@Override
	public void paint(float deltaTime) {
		
		if (displayTime < 0 || !doneLoading) {
			displayTime += deltaTime;
			Graphics g = game.getGraphics();
			g.drawImage(Assets.splash, 0, 0);

		} else {
			//TODO for now, just wraps to game screen
			game.setScreen(new MainMenuScreen(game));
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

}
