package com.ookamijin.cheskers;

public class ChipYellow extends Chip {

	public ChipYellow(int centerX, int centerY, int id) {
		super(centerX, centerY, id);

	}

	@Override
	public boolean isYellow() {
		return true;
	}

	@Override
	public boolean isRed() {
		return false;
	}

}
