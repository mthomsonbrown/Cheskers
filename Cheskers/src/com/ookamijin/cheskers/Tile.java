package com.ookamijin.cheskers;

import com.ookamijin.framework.Input.TouchEvent;

public class Tile {
	private boolean hasRed = false;
	private boolean hasYellow = false;
	private boolean hasNothing = true;
	private boolean isRed = false;
	private boolean isYellow = false;
	private boolean isBlank = true;
	private boolean isBonusYellow = false;
	private boolean isBonusRed = false;

	public boolean isBonusYellow() {
		return isBonusYellow;
	}

	public boolean isBonusRed() {
		return isBonusRed;
	}

	public void setBonusYellow(boolean isBonusYellow) {
		this.isBonusYellow = isBonusYellow;
	}

	public void setBonusRed(boolean isBonusRed) {
		this.isBonusRed = isBonusRed;
	}

	private int centerX, centerY;
	private int chipIndex;

	public Tile(int centerX, int centerY) {
		this.centerX = centerX;
		this.centerY = centerY;
	}

	public boolean isRed() {
		return isRed;
	}

	public boolean isYellow() {
		return isYellow;
	}

	public boolean isBlank() {
		return isBlank;
	}

	public void setRed(boolean isRed) {
		this.isRed = isRed;
	}

	public void setYellow(boolean isYellow) {
		this.isYellow = isYellow;
	}

	public void setBlank(boolean isBlank) {
		this.isBlank = isBlank;
	}

	public int getChipIndex() {
		return chipIndex;
	}

	public void setChipIndex(int chipIndex) {
		this.chipIndex = chipIndex;
	}

	public int getCenterX() {
		return centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public boolean hasRed() {
		return hasRed;
	}

	public boolean hasYellow() {
		return hasYellow;
	}

	public boolean hasNothing() {
		return hasNothing;
	}

	public void setHasRed(boolean hasRed) {
		this.hasRed = hasRed;
		if (this.hasRed) {
			this.hasYellow = false;
			this.hasNothing = false;
		}
	}

	public void setHasYellow(boolean hasYellow) {
		this.hasYellow = hasYellow;
		if (this.hasYellow) {
			this.hasRed = false;
			this.hasNothing = false;
		}
	}

	public void setHasNothing(boolean hasNothing) {
		this.hasNothing = hasNothing;
		if (this.hasNothing) {
			this.hasRed = false;
			this.hasYellow = false;
		}
	}

	public boolean inBounds(TouchEvent event) {
		if (event.x < centerX + 40 && event.x > centerX - 40
				&& event.y < centerY + 40 && event.y > centerY - 40)
			return true;
		return false;
	}

	public boolean matches(Chip objectChip) {
		if(hasYellow && objectChip.isYellow())
			return true;
		if(hasRed && objectChip.isRed())
			return true;
		return false;
	}

	public boolean opposite(Chip objectChip) {
		if(hasYellow && objectChip.isRed())
			return true;
		if(hasRed && objectChip.isYellow())
			return true;
		return false;
	}

}
