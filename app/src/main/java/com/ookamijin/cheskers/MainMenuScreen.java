package com.ookamijin.cheskers;

import java.util.List;

import com.ookamijin.framework.Game;
import com.ookamijin.framework.Graphics;
import com.ookamijin.framework.Input.TouchEvent;
import com.ookamijin.framework.Screen;

public class MainMenuScreen extends Screen {

	private Player player1, player2;

	public MainMenuScreen(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
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
				if (startGame(event))
					game.setScreen(new PlayerSelectScreen(game, false));

				else if (robotGame(event))
					game.setScreen(new PlayerSelectScreen(game, true));
				
				else if (showInstructions(event))
					game.setScreen(new InstructionScreen(game));

				
			}
		}

	}

	private boolean showInstructions(TouchEvent event) {

		if (event.x >= 430 && event.x <= 690 && event.y >= 300
				&& event.y <= 360)
			return true;
		return false;
	}

	private boolean robotGame(TouchEvent event) {
		if (event.x >= 290 && event.x <= 395 && event.y >= 290
				&& event.y <= 370)
			return true;
		return false;
	}

	private boolean startGame(TouchEvent event) {
		if (event.x >= 80 && event.x <= 230 && event.y >= 280 && event.y <= 375)
			return true;
		return false;
	}

	@Override
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();
		g.drawImage(Assets.mainMenu, 0, 0);

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
