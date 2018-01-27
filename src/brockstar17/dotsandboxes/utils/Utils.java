package brockstar17.dotsandboxes.utils;

import brockstar17.dotsandboxes.DBGame;

public class Utils
{
	public static int getAdjId(String ord, int bid) {
		switch (ord) {
		case "N":
			return getAboveId(bid);
		case "E":
			return getRightId(bid);
		case "S":
			return getBelowId(bid);
		case "W":
			return getLeftId(bid);
		default:
			return -1;

		}
	}

	public static int getLeftId(int bid) {

		for (int i = 0; i < 9; i += 3) {
			if (bid == i) {
				return -1;
			}
		}
		return bid - 1;
	}

	public static int getRightId(int bid) {
		for (int i = 2; i < 9; i += 3) {
			if (bid == i) {
				return -1;
			}
		}
		return bid + 1;
	}

	public static int getAboveId(int bid) {
		for (int i = 0; i < 3; i++) {
			if (bid == i) {
				return -1;
			}
		}
		return bid - 3;
	}

	public static int getBelowId(int bid) {
		for (int i = 6; i < 9; i++) {
			if (bid == i) {
				return -1;
			}
		}
		return bid + 3;
	}

	public static int getBoxId(int mx, int my) {

		int sx = DBGame.sDotX, sy = DBGame.sDotY, space = DBGame.dotSpace;

		for (int i = 0; i < 3; i++) {
			for (int l = 0; l < 3; l++) {
				if (mx > sx + ((space + 10) * i) && mx < sx + (space + 10) * (1 + i) && my > sy + ((space + 10) * l) && my < sy + (space + 10) * (1 + l)) {
					// System.out.println("sy " + (sy + (space * l)));

					return i + (3 * l);
				}
			}
		}

		return -1;
	}
}
