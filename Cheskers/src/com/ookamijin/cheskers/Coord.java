package com.ookamijin.cheskers;

public class Coord {
	int x, y;

	public Coord(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * 
	 * @return true if coord is not (-1, -1)
	 */
	public boolean isValid() {
		if (x == -1 || y == -1)
			return false;
		return true;
	}

	public int[] toIntArray() {
		int[] tNum = new int[2];
		
		tNum[0] = x;
		tNum[1] = y;
		return tNum;

	}

	public boolean equals(Coord comp) {
		if (this.x == comp.x && this.y == comp.y)
			return true;
		return false;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String text() {
		return x + ", " + y;
	}


}
