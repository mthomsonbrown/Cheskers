package com.ookamijin.cheskers;

import java.util.ArrayList;

public class PlayerHom extends Player {

	public PlayerHom(Board gameBoard) {
		super(gameBoard);

		name = "Homogeneous";
		isHom = true;
		scoreLocation = new Coord(720, 123);
		poolBounds.set(680, 200, 760, 380);

		nameX = 640;
		nameY = 0;
	}

	public PlayerHom() {
		super();

		name = "Homogeneous";
		isHom = true;
		scoreLocation = new Coord(720, 123);
		poolBounds.set(680, 200, 760, 380);

		nameX = 640;
		nameY = 0;
	}

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

	private boolean oneJump(ArrayList<Coord> tilePath, Chip userChip,
			ArrayList<Coord> targets) {

		boolean verdict = false;

		if (userChip.isRed()) {
			verdict = mBoard.tileHasRed(tilePath.get(1));

		} else
			verdict = mBoard.tileHasYellow(tilePath.get(1));
		if (verdict) {
			++score;
			targets.add(tilePath.get(1));
		}
		return verdict;

	}

	@Override
	public boolean isBonus(ArrayList<Coord> tilePath, Chip userChip) {

		Tile mTile = mBoard.getTile(tilePath.get(tilePath.size() - 1));

		if (userChip.isRed()) {
			return mTile.isBonusRed();
		}
		return mTile.isBonusYellow();
	}

	@Override
	public ArrayList<Coord> doRobot(Board mBoard) {

		this.mBoard = mBoard;
		ArrayList<ArrayList<Coord>> moveList;

		moveList = findAllMoves();

		return pickPriority(moveList);
	}

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

	private boolean validUpMove(Coord startCoord, Chip objectChip,
			ArrayList<Coord> mTilePath) {
		boolean verdict = false;
		int x = startCoord.getX();
		int y = startCoord.getY();

		if (y - 2 >= 0) {
			debug("it was a valid target coord to search up of");

			// add move to moveList
			if (mBoard.tileHasMatch(new Coord(x, y - 1), objectChip)
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
			if (mBoard.tileHasMatch(new Coord(x, y + 1), objectChip)
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

	private boolean validLeftMove(Coord startCoord, Chip objectChip,
			ArrayList<Coord> mTilePath) {

		boolean verdict = false;
		int x = startCoord.getX();
		int y = startCoord.getY();

		if (x - 2 >= 0) {
			debug("it was a valid target coord to search left of");

			// add move to moveList
			if (mBoard.tileHasMatch(new Coord(x - 1, y), objectChip)
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

	private boolean validRightMove(Coord startCoord, Chip objectChip,
			ArrayList<Coord> mTilePath) {

		boolean verdict = false;
		int x = startCoord.getX();
		int y = startCoord.getY();

		if (x + 2 <= 5) {
			debug("it was a valid target coord to search right of");

			// add move to moveList
			if (mBoard.tileHasMatch(new Coord(x + 1, y), objectChip)
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

	private boolean isRobotBonus(ArrayList<Coord> tilePath) {
		if (mBoard.tileHasRed(tilePath.get(0))
				&& mBoard.tileIsBonusRed(tilePath.get(2)))
			return true;

		if (mBoard.tileHasYellow(tilePath.get(0))
				&& mBoard.tileIsBonusYellow(tilePath.get(2)))
			return true;
		return false;
	}
}
