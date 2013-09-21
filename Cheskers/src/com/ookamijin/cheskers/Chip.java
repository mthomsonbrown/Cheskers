package com.ookamijin.cheskers;

import android.util.Log;

public abstract class Chip {

	private Coord coord, old, next;
	private int id;
	private static int V = 10;

	public Chip(int centerX, int centerY, int id) {

		coord = new Coord(centerX, centerY);

	}

	public void update() {
		if (next != null) {
			if (old.getX() > next.getX()) {
				coord.decX(V);
				if (coord.getX() < next.getX()) {
					coord.setX(next.getX());
				}
			} else if (old.getX() < next.getX()) {
				coord.incX(V);
				if (coord.getX() > next.getX()) {
					coord.setX(next.getX());
				}
			}

			if (old.getY() > next.getY()) {
				coord.decY(V);
				if (coord.getY() < next.getY()) {
					coord.setY(next.getY());
				}
			}

			else if (old.getY() < next.getY()) {
				coord.incY(V);
				if (coord.getY() > next.getY()) {
					coord.setY(next.getY());
				}
			}
			
			if (coord.getX() == next.getX() && coord.getY() == next.getY()) {
				next = old = null;
			}
			
		}

	}

	public void setNextCoord(Coord next) {
		if (next != null) {
			this.next = next;
			this.old = new Coord(coord);
		}
	}

	public int getCenterX() {
		return coord.getX();
	}

	public int getCenterY() {
		return coord.getY();
	}

	public int getId() {
		return id;
	}

	public void setCenterX(int centerX) {
		this.coord.setX(centerX);
	}

	public void setCenterY(int centerY) {
		this.coord.setY(centerY);
	}

	public void setCoords(Coord coord) {
		this.coord = coord;

	}

	public abstract boolean isYellow();

	public abstract boolean isRed();

	public void setId(int id) {
		this.id = id;

	}

	private void debug(String message) {
		Log.d("DEBUG", message);
	}

}
