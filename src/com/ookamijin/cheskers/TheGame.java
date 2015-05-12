package com.ookamijin.cheskers;

import com.ookamijin.framework.Screen;
import com.ookamijin.framework.implementation.AndroidGame;

public class TheGame extends AndroidGame {

	boolean firstTimeCreate = true;
	
	@Override
	public Screen getInitScreen() {

		if (firstTimeCreate) {
			firstTimeCreate = false;
		}

		return new SplashLoadingScreen(this);
	}

	@Override
	public void onBackPressed() {
		getCurrentScreen().backButton();
	}

	
}
