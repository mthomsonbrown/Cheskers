package com.ookamijin.cheskers;

import static java.lang.Math.*;

public class Coord {
	int x, y;

	public Coord(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Coord(Coord coord) {
		this.x = coord.getX();
		this.y = coord.getY();
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

	//TODO There is something wrong here..lol
	public void mathyCoord(Coord next, int vel) {
		double mag;
		double theta;
		mag = sqrt(pow(x - next.getX(), 2) + pow(y - next.getY(), 2));
		mag = mag - vel;
		theta = atan(y/x);
		
		x = (int) (next.getX()+mag*cos(theta));
		y = (int) (next.getY()+mag*sin(theta));
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

	public void decX(int v) {
		x = x - v;
	}

	public void decY(int v) {
		y = y - v;
	}

	public void incX(int v) {
		x = x + v;
	}

	public void incY(int v) {
		y = y + v;
	}

	public void set(Coord next) {
		x = next.getX();
		y = next.getY();
		
	}
	
	public void display() {
		Uts.debug(x + ", " + y);
	}

}
