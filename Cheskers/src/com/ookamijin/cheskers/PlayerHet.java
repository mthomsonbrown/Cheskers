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
			verdict = mBoard.mTile[tilePath.get(1).x][tilePath.get(1).y]
					.hasYellow();

		} else
			verdict = mBoard.mTile[tilePath.get(1).x][tilePath.get(1).y]
					.hasRed();

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
	public void doRobot(Board mBoard, ArrayList<Coord> tilePath, Chip userChip) {

		
	}

}
