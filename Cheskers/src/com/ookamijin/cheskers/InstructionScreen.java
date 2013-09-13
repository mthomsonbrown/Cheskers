package com.ookamijin.cheskers;

import java.util.List;

import android.util.Log;

import com.ookamijin.framework.Game;
import com.ookamijin.framework.Graphics;
import com.ookamijin.framework.Image;
import com.ookamijin.framework.Input.TouchEvent;
import com.ookamijin.framework.Screen;

public class InstructionScreen extends Screen {

	Image currentBackground;
	int index = 1;

	public InstructionScreen(Game game) {
		super(game);
		currentBackground = Assets.instructions.get(0);
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
				if (nextButton(event) && index < Assets.instructions.size()) {

					currentBackground = Assets.instructions.get(index);
					++index;

				} else if (nextButton(event)) {
					debug("clicked next for last time");
					
				} else if (startButton(event))
					game.setScreen(new MainMenuScreen(game));
			}

		}

	}

	private boolean nextButton(TouchEvent event) {
		if (event.x >= 680 && event.x <= 780 && event.y >= 365
				&& event.y <= 465)
			return true;
		return false;
	}

	private boolean startButton(TouchEvent event) {
		if (event.x >= 20 && event.x <= 120 && event.y >= 365 && event.y <= 465)
			return true;
		return false;
	}

	@Override
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();
		g.drawImage(currentBackground, 0, 0);

	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {

	}

	@Override
	public void backButton() {

	}
	
	private void debug(String message) {
		Log.d("DEBUG", message);
	}

}
