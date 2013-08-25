package com.ookamijin.cheskers;

public abstract class Chip {

	private int centerX, centerY;
	private int id;
	
	public Chip(int centerX, int centerY, int id) {

        setCenterX(centerX);
        setCenterY(centerY);

    }
	
	public int getCenterX() {
		return centerX;
	}

	public int getCenterY() {
		return centerY;
	}
	
	public int getId() {
		return id;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	public void setCoords(Coord coord) {
		centerX = coord.x;
		centerY = coord.y;
		
	}
	
	public abstract boolean isYellow();
	
	public abstract boolean isRed();

	public void setId(int id) {
		this.id = id;
		
	}
	
}
