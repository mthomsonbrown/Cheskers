package com.ookamijin.cheskers;

import com.ookamijin.framework.Game;
import com.ookamijin.framework.Graphics;
import com.ookamijin.framework.Graphics.ImageFormat;
import com.ookamijin.framework.Screen;

public class SplashLoadingScreen extends Screen {

	

	public SplashLoadingScreen(Game game) {
		super(game);

		

	}

	@Override
	public void update(float deltaTime) {
		Graphics g = game.getGraphics();
		Assets.splash = g.newImage("splash.jpg", ImageFormat.RGB565);

		game.setScreen(new LoadingScreen(game));
	}

	@Override
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();
		g.drawImage(Assets.splash, 0, 0);
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
