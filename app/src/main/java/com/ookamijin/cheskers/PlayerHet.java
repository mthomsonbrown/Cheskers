package com.ookamijin.cheskers;

import java.util.ArrayList;

/**
 * This class contains definitions for heterogeneous player rules, as well as an
 * automated "best choice" algorithm for player vs. robot mode.
 *
 * @author Mike Brown
 */
public class PlayerHet extends Player {

	public PlayerHet(Board gameBoard) {
		super(gameBoard);

		name = "Heterogeneous";
		isHet = true;
		scoreLocation = new Coord(80, 123);
		poolBounds.set(40, 200, 120, 360);
		nameX = 0;
		nameY = 0;
	}

	public PlayerHet() {
		super();

		name = "Heterogeneous";
		isHet = true;
		scoreLocation = new Coord(80, 123);
		poolBounds.set(40, 200, 120, 360);
		nameX = 0;
		nameY = 0;
	}

	/**
	 * Determines if a move is valid based on the heterogeneous rules.
     *
	 * @param tilePath An ArrayList of coordinate values representing tiles visited in move.
     *                 List starts with the tile the chip was on and ends with the tile the chip
     *                 has been moved to.
	 * @param userChip A reference to the chip being moved.
	 * @param targets A list of coordinates of the chips that have legitimately been taken by the
     *                userChip.
	 * @return True if valid move, false if invalid.
	 */
	@Override
	public boolean isValid(ArrayList<Coord> tilePath, Chip userChip,
			ArrayList<Coord> targets) {

		boolean verdict = false;

		if (super.isValid(tilePath, userChip, targets))
			return true;

		// one piece jump
		if (tilePath.size() == 3) {
			return oneJump(tilePath, userChip, targets);
		} else if (tilePath.size() > 3 && (tilePath.size() - 2) % 3 != 0) {
			return false;
		} else {

			for (int i = 0; i < (tilePath.size() + 1) / 3; ++i) {
				ArrayList<Coord> subCoord = new ArrayList<Coord>();
				for (int j = 0; j < 3; ++j) {
					subCoord.add(tilePath.get(i * 2 + j));
				}
				verdict = oneJump(subCoord, userChip, targets);
			}
		}

		return verdict;
	}

    /**
     * This method receives a subset of one move (three tiles visited) and determines if
     * the move was legal.
     *
     * @param tilePath An ArrayList of three coordinate values representing tiles visited in move.
     *                 List starts with the tile the chip was on and ends with the tile the chip
     *                 has been moved to.
     * @param userChip A reference to the chip being moved.
     * @param targets A list of coordinates of the chips that have legitimately been taken by the
     *                userChip.
     * @return True if valid move, false if invalid.
     */
	private boolean oneJump(ArrayList<Coord> tilePath, Chip userChip,
			ArrayList<Coord> targets) {

		boolean verdict = false;

		if (userChip.isRed()) {
			verdict = mBoard.tileHasYellow(tilePath.get(1));

		} else
			verdict = mBoard.tileHasRed(tilePath.get(1));

		if (verdict) {
			++score;
			targets.add(tilePath.get(1));
		}

		return verdict;
	}

    /**
     * Determines if the last tile visited counts as a bonus tile in heterogeneous rules.
     *
     * @param tilePath An ArrayList of coordinate values representing tiles visited in move.
     *                 List starts with the tile the chip was on and ends with the tile the chip
     *                 has been moved to.
     * @param userChip A reference to the chip being moved.
     * @return True if move grants a bonus, false if not.
     */
	@Override
	public boolean isBonus(ArrayList<Coord> tilePath, Chip userChip) {

		Tile mTile = mBoard.getTile(tilePath.get(tilePath.size() - 1));

		if (userChip.isRed()) {
			return mTile.isBonusYellow();
		}
		return mTile.isBonusRed();
	}

    /**
     * Method that simulates player movement, returning a tilePath chosen by the robot algorithm.
     *
     * @param mBoard A reference to the board object.
     * @return A tilePath chosen by the robot algorithm.
     */
	@Override
	public ArrayList<Coord> doRobot(Board mBoard) {

		this.mBoard = mBoard;
		ArrayList<ArrayList<Coord>> moveList;

		moveList = findAllMoves();

		return pickPriority(moveList);
	}

    /**
     * This method is implemented by the robot algorithm to find all possible moves a
     * player could make.
     *
     * @return An ArrayList of valid tilePaths.
     */
	@Override
	protected ArrayList<ArrayList<Coord>> findAllMoves() {
		ArrayList<ArrayList<Coord>> moveList = new ArrayList<ArrayList<Coord>>();
		ArrayList<Coord> mTilePath;

		// search each tile TTBLTR
		for (int j = 0; j < 6; ++j) {
			for (int i = 0; i < 6; ++i) {
				Coord startCoord = new Coord(i, j);

				debug("searching coord " + i + ", " + j);

				// tile has chip
				if (!mBoard.tileHasNothing(startCoord)) {
					Chip objectChip = mBoard.getChip(startCoord);

					mTilePath = new ArrayList<Coord>();
					if (validLeftMove(startCoord, objectChip, mTilePath)) {
						moveList.add(mTilePath);
					}

					mTilePath = new ArrayList<Coord>();
					if (validUpMove(startCoord, objectChip, mTilePath)) {
						moveList.add(mTilePath);
					}

					mTilePath = new ArrayList<Coord>();
					if (validRightMove(startCoord, objectChip, mTilePath)) {
						moveList.add(mTilePath);
					}

					mTilePath = new ArrayList<Coord>();
					if (validDownMove(startCoord, objectChip, mTilePath)) {
						moveList.add(mTilePath);
					}

					addBufferMoves(startCoord, moveList);
				}
			}
		}
		return moveList;
	}

    /**
     * Used by robot to determine if an upward move was valid.
     *
     * @param startCoord Reference of first coord in tilePath.
     * @param objectChip A reference to the chip being moved.
     * @param mTilePath An ArrayList of coordinate values representing tiles visited in move.
     *                 List starts with the tile the chip was on and ends with the tile the chip
     *                 has been moved to.
     * @return True if valid move, false if not.
     */
	private boolean validUpMove(Coord startCoord, Chip objectChip,
			ArrayList<Coord> mTilePath) {

		boolean verdict = false;
		int x = startCoord.getX();
		int y = startCoord.getY();

		if (y - 2 >= 0) {
			debug("it was a valid target coord to search up of");

			// add move to moveList
			if (mBoard.tileHasOpposite(new Coord(x, y - 1), objectChip)
					&& mBoard.tileHasNothing(new Coord(x, y - 2))) {
				debug("up search satisfies rules");

				if (mTilePath.size() < 1) {
					mTilePath.add(new Coord(x, y));
				}
				mTilePath.add(new Coord(x, y - 1));
				mTilePath.add(new Coord(x, y - 2));
				verdict = true;

				// double move
				if (!validUpMove(mTilePath.get(mTilePath.size() - 1),
						objectChip, mTilePath)) {
					if (!validRightMove(mTilePath.get(mTilePath.size() - 1),
							objectChip, mTilePath)) {
						validLeftMove(mTilePath.get(mTilePath.size() - 1),
								objectChip, mTilePath);
					}
				}
			}
		}
		mTilePath = null;
		return verdict;
	}

    /**
     * Used by robot to determine if a downward move was valid.
     *
     * @param startCoord Reference of first coord in tilePath.
     * @param objectChip A reference to the chip being moved.
     * @param mTilePath An ArrayList of coordinate values representing tiles visited in move.
     *                 List starts with the tile the chip was on and ends with the tile the chip
     *                 has been moved to.
     * @return True if valid move, false if not.
     */
	private boolean validDownMove(Coord startCoord, Chip objectChip,
			ArrayList<Coord> mTilePath) {

		boolean verdict = false;
		int x = startCoord.getX();
		int y = startCoord.getY();

		debug("valid down is checking:");
		startCoord.display();
		if (y + 2 <= 5) {
			debug("it was a valid target coord to search down of");

			// add move to moveList
			if (mBoard.tileHasOpposite(new Coord(x, y + 1), objectChip)
					&& mBoard.tileHasNothing(new Coord(x, y + 2))) {
				debug("down search satisfies rules");

				if (mTilePath.size() < 1) {
					mTilePath.add(new Coord(x, y));
				}
				mTilePath.add(new Coord(x, y + 1));
				mTilePath.add(new Coord(x, y + 2));
				verdict = true;

				// double move
				if (!validDownMove(mTilePath.get(mTilePath.size() - 1),
						objectChip, mTilePath)) {
					if (!validRightMove(mTilePath.get(mTilePath.size() - 1),
							objectChip, mTilePath)) {
						validLeftMove(mTilePath.get(mTilePath.size() - 1),
								objectChip, mTilePath);
					}
				}
			}
		}
		mTilePath = null;
		return verdict;
	}

    /**
     * Used by robot to determine if move to the left was valid.
     *
     * @param startCoord Reference of first coord in tilePath.
     * @param objectChip A reference to the chip being moved.
     * @param mTilePath An ArrayList of coordinate values representing tiles visited in move.
     *                 List starts with the tile the chip was on and ends with the tile the chip
     *                 has been moved to.
     * @return True if valid move, false if not.
     */
	private boolean validLeftMove(Coord startCoord, Chip objectChip,
			ArrayList<Coord> mTilePath) {

		boolean verdict = false;
		int x = startCoord.getX();
		int y = startCoord.getY();

		if (x - 2 >= 0) {
			debug("it was a valid target coord to search left of");

			// add move to moveList
			if (mBoard.tileHasOpposite(new Coord(x - 1, y), objectChip)
					&& mBoard.tileHasNothing(new Coord(x - 2, y))) {
				debug("left search satisfies rules");

				if (mTilePath.size() < 1) {
					mTilePath.add(new Coord(x, y));
				}
				mTilePath.add(new Coord(x - 1, y));
				mTilePath.add(new Coord(x - 2, y));
				verdict = true;

				// double move
				if (!validUpMove(mTilePath.get(mTilePath.size() - 1),
						objectChip, mTilePath)) {
					if (!validDownMove(mTilePath.get(mTilePath.size() - 1),
							objectChip, mTilePath)) {
						validLeftMove(mTilePath.get(mTilePath.size() - 1),
								objectChip, mTilePath);
					}
				}
			}
		}
		mTilePath = null;
		return verdict;
	}

    /**
     * Used by robot to determine if move to the right was valid.
     *
     * @param startCoord Reference of first coord in tilePath.
     * @param objectChip A reference to the chip being moved.
     * @param mTilePath An ArrayList of coordinate values representing tiles visited in move.
     *                 List starts with the tile the chip was on and ends with the tile the chip
     *                 has been moved to.
     * @return True if valid move, false if not.
     */
	private boolean validRightMove(Coord startCoord, Chip objectChip,
			ArrayList<Coord> mTilePath) {

		boolean verdict = false;
		int x = startCoord.getX();
		int y = startCoord.getY();

		if (x + 2 <= 5) {
			debug("it was a valid target coord to search right of");

			// add move to moveList
			if (mBoard.tileHasOpposite(new Coord(x + 1, y), objectChip)
					&& mBoard.tileHasNothing(new Coord(x + 2, y))) {
				debug("right search satisfies rules");

				if (mTilePath.size() < 1) {
					mTilePath.add(new Coord(x, y));
				}
				mTilePath.add(new Coord(x + 1, y));
				mTilePath.add(new Coord(x + 2, y));
				verdict = true;

				// double move
				if (!validUpMove(mTilePath.get(mTilePath.size() - 1),
						objectChip, mTilePath)) {
					if (!validDownMove(mTilePath.get(mTilePath.size() - 1),
							objectChip, mTilePath)) {
						validRightMove(mTilePath.get(mTilePath.size() - 1),
								objectChip, mTilePath);
					}
				}
			}
		}

		mTilePath = null;
		return verdict;
	}

    /**
     * Method of the robot algorithm to pick the best possible move.
     *
     * @param moveList List of tilePath objects, which are ArrayLists of Coord objects.
     * @return The tilePath chosen by the algorithm.
     */
	protected ArrayList<Coord> pickPriority(ArrayList<ArrayList<Coord>> moveList) {

		// sub array of double move options.
		ArrayList<ArrayList<Coord>> subList = new ArrayList<ArrayList<Coord>>();
		for (int i = 0; i < moveList.size(); ++i) {
			if (moveList.get(i).size() > 3) {
				subList.add(moveList.get(i));
			}
		}

		if (subList.size() > 0) {
			moveList = subList;
		} else {
			// no double moves, sub array of single moves with bonus
			for (int i = 0; i < moveList.size(); ++i) {
				if (moveList.get(i).size() == 3) {
					if (isRobotBonus(moveList.get(i)))
						subList.add(moveList.get(i));
				}
			}
		}
		if (subList.size() > 0) {
			moveList = subList;
		} else {
			// no bonuses, sub array of single moves
			for (int i = 0; i < moveList.size(); ++i) {
				if (moveList.get(i).size() == 3)
					subList.add(moveList.get(i));
			}
		}
		if (subList.size() > 0)
			moveList = subList;

		if (moveList.size() > 1)
			tilePath = moveList.get(gen.nextInt(moveList.size() - 1));
		else
			tilePath = moveList.get(0);

		return tilePath;
	}

    /**
     * Determines if the last tile visited counts as a bonus tile in heterogeneous rules.
     *
     * @param tilePath An ArrayList of coordinate values representing tiles visited in move.
     *                 List starts with the tile the chip was on and ends with the tile the chip
     *                 has been moved to.
     * @return True if move grants a bonus, false if not.
     */
	private boolean isRobotBonus(ArrayList<Coord> tilePath) {
		if (mBoard.tileHasRed(tilePath.get(0))
				&& mBoard.tileIsBonusYellow(tilePath.get(2)))
			return true;

		if (mBoard.tileHasYellow(tilePath.get(0))
				&& mBoard.tileIsBonusRed(tilePath.get(2)))
			return true;
		return false;
	}
}
