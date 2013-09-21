package com.ookamijin.cheskers;

import java.util.ArrayList;

public class PlayerHet extends Player {

	public PlayerHet(Board gameBoard) {
		super(gameBoard);

		name = "Heterogenus";
		isHet = true;
		scoreLocation = new Coord(80, 120);
		poolBounds.set(40, 200, 120, 360);
		nameX = 0;
		nameY = 0;
	}

	public PlayerHet() {
		super();

		name = "Heterogenus";
		isHet = true;
		scoreLocation = new Coord(80, 120);
		poolBounds.set(40, 200, 120, 360);
		nameX = 0;
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
			verdict = mBoard.tileHasYellow(tilePath.get(1));

		} else
			verdict = mBoard.tileHasRed(tilePath.get(1));

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
			return mTile.isBonusYellow();
		}
		return mTile.isBonusRed();
	}

	@Override
	public ArrayList<Coord> doRobot(Board mBoard) {

		this.mBoard = mBoard;
		ArrayList<ArrayList<Coord>> moveList;

		moveList = findAllMoves();

		debug("in robot movelist size is " + moveList.size());

		// taking random move...
		// TODO implement difficulty here
		if (moveList.size() > 1)
			tilePath = moveList.get(gen.nextInt(moveList.size() - 1));
		else
			tilePath = moveList.get(0);

		return tilePath;
	}

	@Override
	protected ArrayList<ArrayList<Coord>> findAllMoves() {
		ArrayList<ArrayList<Coord>> moveList = new ArrayList<ArrayList<Coord>>();
		ArrayList<ArrayList<Coord>> backupList = new ArrayList<ArrayList<Coord>>();

		// search each tile TTBLTR
		for (int j = 0; j < 6; ++j) {
			for (int i = 0; i < 6; ++i) {
				Chip objectChip = mBoard.getChip(new Coord(i, j));
				debug("searching coord " + i + ", " + j);

				// tile has chip
				if (!mBoard.tileHasNothing(new Coord(i, j))) {

					// Decide to check left
					if (i - 2 >= 0) {
						debug("it was a valid target coord to search left of");

						// add move to moveList
						if (mBoard.tileHasOpposite(new Coord(i - 1, j),
								objectChip)
								&& mBoard.tileHasNothing(new Coord(i - 2, j))) {
							debug("left search satisfies rules");

							moveList.add(new ArrayList<Coord>());
							moveList.get(moveList.size() - 1).add(
									new Coord(i, j));
							moveList.get(moveList.size() - 1).add(
									new Coord(i - 1, j));
							moveList.get(moveList.size() - 1).add(
									new Coord(i - 2, j));
						}

						// add move to backupList (moves chip one space left)
						if (mBoard.tileHasNothing(new Coord(i - 1, j))) {
							backupList.add(new ArrayList<Coord>());
							backupList.get(backupList.size() - 1).add(
									new Coord(i, j));
							backupList.get(backupList.size() - 1).add(
									new Coord(i - 1, j));
						}
					}

					// Decide to check up
					if (j - 2 >= 0) {
						if (mBoard.tileHasOpposite(new Coord(i, j - 1),
								objectChip)
								&& mBoard.tileHasNothing(new Coord(i, j - 2))) {
							moveList.add(new ArrayList<Coord>());
							moveList.get(moveList.size() - 1).add(
									new Coord(i, j));
							moveList.get(moveList.size() - 1).add(
									new Coord(i, j - 1));
							moveList.get(moveList.size() - 1).add(
									new Coord(i, j - 2));
						}

						// add move to backupList (moves chip one space left)
						if (mBoard.tileHasNothing(new Coord(i, j - 1))) {
							backupList.add(new ArrayList<Coord>());
							backupList.get(backupList.size() - 1).add(
									new Coord(i, j));
							backupList.get(backupList.size() - 1).add(
									new Coord(i, j - 1));
						}
					}

					// Decide to check down
					if (j + 2 <= 5) {
						if (mBoard.tileHasOpposite(new Coord(i, j + 1),
								objectChip)
								&& mBoard.tileHasNothing(new Coord(i, j + 2))) {
							moveList.add(new ArrayList<Coord>());
							moveList.get(moveList.size() - 1).add(
									new Coord(i, j));
							moveList.get(moveList.size() - 1).add(
									new Coord(i, j + 1));
							moveList.get(moveList.size() - 1).add(
									new Coord(i, j + 2));
						}

						// add move to backupList (moves chip one space left)
						if (mBoard.tileHasNothing(new Coord(i, j + 1))) {
							backupList.add(new ArrayList<Coord>());
							backupList.get(backupList.size() - 1).add(
									new Coord(i, j));
							backupList.get(backupList.size() - 1).add(
									new Coord(i, j + 1));
						}
					}

					// Decide to check right
					if (i + 2 <= 5) {

						// add move to moveList
						if (mBoard.tileHasOpposite(new Coord(i + 1, j),
								objectChip)
								&& mBoard.tileHasNothing(new Coord(i + 2, j))) {

							moveList.add(new ArrayList<Coord>());
							moveList.get(moveList.size() - 1).add(
									new Coord(i, j));
							moveList.get(moveList.size() - 1).add(
									new Coord(i + 1, j));
							moveList.get(moveList.size() - 1).add(
									new Coord(i + 2, j));
						}

						// add move to backupList (moves chip one space left)
						if (mBoard.tileHasNothing(new Coord(i + 1, j))) {
							backupList.add(new ArrayList<Coord>());
							backupList.get(backupList.size() - 1).add(
									new Coord(i, j));
							backupList.get(backupList.size() - 1).add(
									new Coord(i + 1, j));
						}
					}

				}
			}
		}
		if (moveList.size() > 0)
			return moveList;
		else
			return backupList;
	}

}
