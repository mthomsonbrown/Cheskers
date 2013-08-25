package com.ookamijin.cheskers;

public class ChipRed extends Chip {

	public ChipRed(int centerX, int centerY, int id) {
		super(centerX, centerY, id);

	}

	@Override
	public boolean isYellow() {
		return false;
	}

	@Override
	public boolean isRed() {
		return true;
	}

}
