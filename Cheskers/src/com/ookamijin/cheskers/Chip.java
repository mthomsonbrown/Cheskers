package com.ookamijin.cheskers;

public class Chip {

	private int centerX, centerY;
	
	public Chip(int centerX, int centerY) {

        setCenterX(centerX);
        setCenterY(centerY);

    }
	
	public int getCenterX() {
		return centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	
	
}
