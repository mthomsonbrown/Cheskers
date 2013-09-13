package com.ookamijin.cheskers;

import com.ookamijin.framework.Input.TouchEvent;

public class Board {

	// these are the centers of the squares
	public static int x[] = { 200, 280, 360, 440, 520, 600 };
	public static int y[] = { 40, 120, 200, 280, 360, 440 };

	public Tile mTile[][];

	public Board() {

		mTile = new Tile[6][6];
		int tNum = 0;

		// populates the tile array with coordinate information
		for (int j = 0; j < 6; ++j) {
			for (int i = 0; i < 6; ++i) {
				mTile[i][j] = new Tile(x[i], y[j]);
			}
		}

		// initial board layout colors
		for (int j = 0; j < 6; ++j) {
			for (int i = 0; i < 6; ++i) {

				switch (j) {
				case 0:
				case 2:
				case 4:
					mTile[i][j].setHasYellow(true);

					break;
				case 1:
				case 3:
				case 5:
					mTile[i][j].setHasRed(true);
					break;
				}

				// TODO don't know if this will work correctly
				if (j == 2 && i == 2 || j == 2 && i == 3 || j == 3 && i == 2
						|| j == 3 && i == 3) {
					mTile[i][j].setHasNothing(true);
				}
			}
		}
		// initialize chip ids
		int yellow = 0;
		int red = 0;
		for (int j = 0; j < 6; ++j) {
			for (int i = 0; i < 6; ++i) {

				if (mTile[i][j].hasYellow()) {
					mTile[i][j].setChipIndex(yellow);
					++yellow;
				}
				if (mTile[i][j].hasRed()) {
					mTile[i][j].setChipIndex(red);
					++red;
				}
			}
		}

		// set bonus squares
		mTile[1][1].setBonusYellow(true);
		mTile[2][1].setBonusYellow(true);
		mTile[4][2].setBonusYellow(true);
		mTile[4][3].setBonusYellow(true);
		mTile[1][4].setBonusYellow(true);
		mTile[2][4].setBonusYellow(true);

		mTile[3][1].setBonusRed(true);
		mTile[4][1].setBonusRed(true);
		mTile[1][2].setBonusRed(true);
		mTile[1][3].setBonusRed(true);
		mTile[4][4].setBonusRed(true);
		mTile[3][4].setBonusRed(true);

	}

	public static int topInitX(int index) {
		return botInitX(index);
	}

	public static int botInitX(int index) {

		switch (index) {
		case 0:
		case 6:
		case 10:
			return x[0];
		case 1:
		case 7:
		case 11:
			return x[1];
		case 2:
		case 12:
			return x[2];
		case 3:
		case 13:
			return x[3];
		case 4:
		case 8:
		case 14:
			return x[4];
		case 5:
		case 9:
		case 15:
			return x[5];

		}

		return -1;
	}

	private static int getY(int index) {

		// calculates topInitIndex. add one for bottom
		if (index < 6) {
			return 0;
		} else if (index < 10) {
			return 2;
		} else {
			return 4;
		}
	}

	public static int topInitY(int index) {
		return y[getY(index)];
	}

	public static int botInitY(int index) {
		return y[getY(index) + 1];

	}

	/**
	 * 
	 * @param event
	 * @return returns the center X value for the board square
	 */
	public static int getXCoord(TouchEvent event) {

		if (event.x <= 240)
			return Board.x[0];
		else if (event.x <= 320)
			return Board.x[1];
		else if (event.x <= 400)
			return Board.x[2];
		else if (event.x <= 480)
			return Board.x[3];
		else if (event.x <= 560)
			return Board.x[4];
		else if (event.x <= 640)
			return Board.x[5];

		return 0;
	}

	public static int getYCoord(TouchEvent event) {
		if (event.y <= 80)
			return Board.y[0];
		else if (event.y <= 160)
			return Board.y[1];
		else if (event.y <= 240)
			return Board.y[2];
		else if (event.y <= 320)
			return Board.y[3];
		else if (event.y <= 400)
			return Board.y[4];
		else if (event.y <= 480)
			return Board.y[5];

		return 0;
	}

	public Coord getTileIndex(TouchEvent event) {

		Coord coords = new Coord(-1, -1);
		for (int j = 0; j < 6; ++j) {
			for (int i = 0; i < 6; ++i) {
				if (mTile[i][j].inBounds(event)) {
					coords.setX(i);
					coords.setY(j);
					return coords;
				}
			}
		}
		return coords;
	}

	public Coord getTileCenter(Coord coord) {
		Coord chipPos = new Coord(x[coord.x], y[coord.y]);
		return chipPos;
	}

	public boolean tileIsEmpty(Coord coord) {

		if (mTile[coord.x][coord.y].hasNothing())
			return true;
		return false;
	}

	public boolean tileHasRed(Coord coord) {
		if (mTile[coord.x][coord.y].hasRed())
			return true;
		return false;
	}

	public boolean tileHasYellow(Coord coord) {

		if (mTile[coord.x][coord.y].hasYellow())
			return true;
		return false;
	}

	public void setTileHasNothing(Coord coord) {
		mTile[coord.x][coord.y].setHasNothing(true);
		mTile[coord.x][coord.y].setChipIndex(0);
	}

	public void setTileHasYellow(Coord coord) {
		mTile[coord.x][coord.y].setHasYellow(true);

	}

	public void setTileHasRed(Coord coord) {
		mTile[coord.x][coord.y].setHasRed(true);

	}

	public void setTileChipIndex(Coord coord, int id) {
		mTile[coord.x][coord.y].setChipIndex(id);

	}

	/**
	 * 
	 * @param coord
	 * @return index of chip at tile coord
	 */
	public int getTileChipIndex(Coord coord) {
		return mTile[coord.x][coord.y].getChipIndex();

	}

	public String tileStatus(Coord coord) {
		String status = "";

		if (mTile[coord.x][coord.y].hasNothing()) {
			status += "N";
		} else if (mTile[coord.x][coord.y].hasRed()) {
			status += "R";
		} else {
			status += "Y";
		}

		if (mTile[coord.x][coord.y].getChipIndex() < 10)
			status += "0";
		status += mTile[coord.x][coord.y].getChipIndex();
		return status;
	}

	public boolean tileIsBonus(Coord tile) {
		// TODO Auto-generated method stub
		return false;
	}

	public Tile getTile(Coord coord) {
		return mTile[coord.x][coord.y];
	}

	public boolean tileHasNothing(Coord coord) {
		return mTile[coord.x][coord.y].hasNothing();
	}

}
