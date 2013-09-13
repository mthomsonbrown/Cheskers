package com.ookamijin.cheskers;

import java.util.ArrayList;

public class PlayerHom extends Player {

	public PlayerHom(Board gameBoard) {
		super(gameBoard);

		name = "Homogenus";
		isHom = true;
		scoreLocation = new Coord(720, 120);
		poolBounds.set(680, 200, 760, 380);

		nameX = 640;
		nameY = 0;
	}

	public PlayerHom() {
		super();

		name = "Homogenus";
		isHom = true;
		scoreLocation = new Coord(720, 120);
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
	public void doRobot(Board mBoard, ArrayList<Coord> tilePath, Chip userChip) {

		// hard cord test mode
		Coord coord = new Coord(-1, -1);
		coord.setX(5);
		coord.setY(1);

		// doesn't like this...
		userChip.setId(mBoard.getTileChipIndex(coord));
		if (mBoard.tileHasRed(coord)) {

		} else
			return;

		tilePath.add(new Coord(5, 1));
		tilePath.add(new Coord(4, 1));
		tilePath.add(new Coord(3, 1));

		// end hard code test mode

	}
}
