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
		Assets.hetWin = g.newImage("hetWin.png", ImageFormat.ARGB4444);
		Assets.homWin = g.newImage("homWin.png", ImageFormat.ARGB4444);
		Assets.winBackground = g.newImage("winBackground.jpg", ImageFormat.RGB565);
		Assets.chipRed = g.newImage("chipRed.png", ImageFormat.ARGB4444);
		Assets.chipYellow = g.newImage("chipYellow.png", ImageFormat.ARGB4444);
		Assets.homOn = g.newImage("homOn.png", ImageFormat.ARGB4444);
		Assets.homOff = g.newImage("homOff.png", ImageFormat.ARGB4444);
		Assets.hetOn = g.newImage("hetOn.png", ImageFormat.ARGB4444);
		Assets.hetOff = g.newImage("hetOff.png", ImageFormat.ARGB4444);
		Assets.selectScreen = g.newImage("robotScreen.jpg", ImageFormat.RGB565);
		
		//animation
		Assets.fire0 = g.newImage("fire0.png", ImageFormat.ARGB4444);
		Assets.fire1 = g.newImage("fire1.png", ImageFormat.ARGB4444);
		Assets.fire2 = g.newImage("fire2.png", ImageFormat.ARGB4444);
		Assets.fire3 = g.newImage("fire3.png", ImageFormat.ARGB4444);
		Assets.fire4 = g.newImage("fire4.png", ImageFormat.ARGB4444);
		Assets.fire5 = g.newImage("fire5.png", ImageFormat.ARGB4444);
		

		// instructions
		Assets.instructions = new ArrayList<Image>();

		for (int i = 1; i <= 5; ++i) {
			Assets.instructions.add(g.newImage("instructions" + i + ".jpg",
					ImageFormat.RGB565));
		}

		doneLoading = true;

	}

	@Override
	public void paint(float deltaTime) {

		if (displayCount < 15 || !doneLoading) {
			++displayCount;
			Graphics g = game.getGraphics();
			g.drawImage(Assets.splash, 0, 0);
 
		} else {
			
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
