package com.ookamijin.cheskers;

import java.util.ArrayList;

import com.ookamijin.framework.Game;
import com.ookamijin.framework.Graphics;
import com.ookamijin.framework.Graphics.ImageFormat;
import com.ookamijin.framework.Image;
import com.ookamijin.framework.Screen;

public class LoadingScreen extends Screen {

	float displayCount;
	boolean doneLoading = false;

	public LoadingScreen(Game game) {
		super(game);
		displayCount = 0;
	}

	@Override
	public void update(float deltaTime) {
		Graphics g = game.getGraphics();

		Assets.mainMenu = g.newImage("mainMenu.jpg", ImageFormat.RGB565);
		Assets.background = g.newImage("background.jpg", ImageFormat.RGB565);
		Assets.hetWin = g.newImage("hetWin.jpg", ImageFormat.RGB565);
		Assets.homWin = g.newImage("homWin.jpg", ImageFormat.RGB565);
		Assets.chipRed = g.newImage("chipRed.png", ImageFormat.ARGB4444);
		Assets.chipYellow = g.newImage("chipYellow.png", ImageFormat.ARGB4444);
		Assets.homOn = g.newImage("homOn.png", ImageFormat.ARGB4444);
		Assets.homOff = g.newImage("homOff.png", ImageFormat.ARGB4444);
		Assets.hetOn = g.newImage("hetOn.png", ImageFormat.ARGB4444);
		Assets.hetOff = g.newImage("hetOff.png", ImageFormat.ARGB4444);
		Assets.selectScreen = g.newImage("robotScreen.jpg", ImageFormat.RGB565);
		
		// instructions
		Assets.instructions = new ArrayList<Image>();
		Assets.instructions.add(g.newImage("instructions1.jpg",
				ImageFormat.RGB565));
		Assets.instructions.add(g.newImage("instructions2.jpg",
				ImageFormat.RGB565));
		

		doneLoading = true;

	}

	@Override
	public void paint(float deltaTime) {

		if (displayCount < 60 || !doneLoading) {
			++displayCount;
			Graphics g = game.getGraphics();
			g.drawImage(Assets.splash, 0, 0);

		} else {
			// TODO for now, just wraps to game screen
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
