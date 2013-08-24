package com.ookamijin.cheskers;

import android.util.Log;

import com.ookamijin.framework.Input.TouchEvent;

public class Board {

	// these are the centers of the squares
	public static int x[] = { 200, 280, 360, 440, 520, 600 };
	public static int y[] = { 40, 120, 200, 280, 360, 440 };

	/**
	 * tiles of the board go left to right, top to bottom, starting with 0, and
	 * ending with 35
	 */
	public Tile mTile[];

	public Board() {

		mTile = new Tile[36];

		int tNum = 0;
		for (int i = 0; i < 6; ++i) {

			for (int j = 0; j < 6; ++j) {
				mTile[tNum] = new Tile(x[j], y[i]);
				++tNum;
			}
		}

		// initial board layout
		for (int i = 0; i < 6; ++i) {
			mTile[i].setHasYellow(true);
			mTile[i].setChipIndex(i);
			mTile[i + 24].setHasYellow(true);
			mTile[i + 24].setChipIndex(i + 10);

			mTile[i + 6].setHasRed(true);
			mTile[i + 6].setChipIndex(i);
			mTile[i + 30].setHasRed(true);
			mTile[i + 30].setChipIndex(i + 10);

			if (i != 2 && i != 3) {
				mTile[i + 12].setHasYellow(true);

				mTile[i + 18].setHasRed(true);
			}

		}

		// couldn't figure out how to fit these in the for loop...
		mTile[12].setChipIndex(6);
		mTile[13].setChipIndex(7);
		mTile[16].setChipIndex(8);
		mTile[17].setChipIndex(9);
		
		mTile[18].setChipIndex(6);
		mTile[19].setChipIndex(7);
		mTile[22].setChipIndex(8);
		mTile[23].setChipIndex(9);
		
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

	public int getTileIndex(TouchEvent event) {

		for (int i = 0; i < mTile.length; ++i) {
			if (mTile[i].inBounds(event)) {
				return i;
			}
		}
		return -1;
	}

}
