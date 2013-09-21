package com.ookamijin.cheskers;

import java.util.ArrayList;
import java.util.Random;

import android.graphics.Rect;
import android.util.Log;

public abstract class Player {
	protected String name = "Add Name!";
	protected Board mBoard;
	protected Chip userChip;
	protected ArrayList<Coord> tilePath;

	protected int score;
	public boolean isHet = false;
	public boolean isHom = false;
	public boolean isRobot = false;

	protected int nameX;
	protected int nameY;

	public Coord scoreLocation;
	protected int poolX, poolY;
	protected Rect poolBounds;

	protected Random gen;

	public Player() {
		poolBounds = new Rect();
		score = 0;

		gen = new Random(666);
	}

	public void setBoard(Board mBoard) {
		this.mBoard = mBoard;
	}

	public Player(Board gameBoard) {
		this.mBoard = gameBoard;
		poolBounds = new Rect();
		score = 0;

		gen = new Random(666);
	}

	public boolean isRobot() {
		return isRobot;
	}

	public void setRobot(boolean isRobot) {
		this.isRobot = isRobot;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public boolean isValid(ArrayList<Coord> coords, Chip userChip,
			ArrayList<Coord> targets) {

		// was a push
		for (int i = 1; i < coords.size(); ++i) {
			if (!mBoard.tileIsEmpty(coords.get(i)))
				return false;
		}
		if (coords.get(0).x != coords.get(coords.size() - 1).x
				&& coords.get(0).y != coords.get(coords.size() - 1).y)
			return false;

		return true;

	}

	public abstract boolean isBonus(ArrayList<Coord> tilePath, Chip userChip);

	public int getPoolX() {
		return poolBounds.left
				+ gen.nextInt(poolBounds.right - poolBounds.left) + 1;

	}

	public int getPoolY() {

		return poolBounds.top + gen.nextInt(poolBounds.bottom - poolBounds.top)
				+ 1;
	}

	public Coord getPoolCoord() {
		int x, y;
		Coord coord;
		x = poolBounds.left + gen.nextInt(poolBounds.right - poolBounds.left)
				+ 1;

		y = poolBounds.top + gen.nextInt(poolBounds.bottom - poolBounds.top)
				+ 1;

		coord = new Coord(x, y);

		return coord;
	}

	protected void debug(String message) {
		Log.d("DEBUG", message);
	}

	public void won() {
		// TODO Auto-generated method stub

	}

	public int getNameX() {
		return nameX;
	}

	public int getNameY() {
		return nameY;
	}

	public abstract ArrayList<Coord> doRobot(Board mBoard);

	protected abstract ArrayList<ArrayList<Coord>> findAllMoves();

}
