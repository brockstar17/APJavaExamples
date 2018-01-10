package brockstar17.dotsandboxes.multiplayer;

import java.awt.Color;
import java.awt.Graphics;

public class Box
{

	/**
	 * Game Board Dimensions:<br>
	 * - Width: 540<br>
	 * - Height: 540<br>
	 * Box Dimensions:<br>
	 * - Line thickness: 10<br>
	 * - Box Width: (540 - (3 * 10)) / 3<br>
	 * - Box Height: (540 - (3 * 10)) / 3
	 */

	private int boxId;
	private String name;

	private Line[] lines;

	public Box(int boxId)
	{
		this.boxId = boxId;
		this.name = "";
		lines = new Line[4];

		initLines();
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (this.name.equals(""))
			this.name = name;
	}

	public Line getLine(int line) {
		return lines[line];
	}

	public Line[] getLines() {
		return this.lines;
	}

	public int getBoxId() {
		return this.boxId;
	}

	public void drawBox(Graphics g) {

		int tlx = lines[0].getP1().x, trx = lines[0].getP2().x + 10;
		int blx = lines[2].getP1().x + 10, brx = lines[2].getP2().x;

		int tly = lines[0].getP2().y, tyr = lines[0].getP1().y;
		int bly = lines[2].getP2().y, bry = lines[2].getP1().y;

		if (name.equals("player1")) {
			g.setColor(Color.BLUE);
			g.drawLine(tlx, tly, brx, bry);
			g.drawLine(trx, tyr, blx, bly);
		}
		if (name.equals("player2")) {
			g.setColor(Color.RED);
			g.drawLine(tlx, tly, brx, bry);
			g.drawLine(trx, tyr, blx, bly);
		}

		for (Line l : lines) {
			if (l.isTaken()) {
				l.draw(g);
			}
		}
	}

	public void updateShareLine(String ord, Color c) {
		switch (ord) {
		case "N":
			lines[2].setColor(c);
			lines[2].setTaken();
			return;
		case "E":
			lines[3].setColor(c);
			lines[3].setTaken();
			return;
		case "S":
			lines[0].setColor(c);
			lines[0].setTaken();
			return;
		case "W":
			lines[1].setColor(c);
			lines[1].setTaken();
			return;

		}
	}

	public Line getLineClicked(int mx, int my) {

		int dn = Math.abs(my - lines[0].getCenterPoint().y), de = Math.abs(mx - lines[1].getCenterPoint().x),
				ds = Math.abs(my - lines[2].getCenterPoint().y), dw = Math.abs(mx - lines[3].getCenterPoint().x);

		// The mouse is closer to west
		if (dw < de) {
			// The mouse is closer to north than west
			if (dw > dn) {
				return lines[0];
			}
			// The mouse is clsoer to the south than the west
			else if (dw > ds) {
				return lines[2];
			}
			// The mouse is closer to the west
			else {
				return lines[3];
			}
		}
		// The mouse is closer to the east
		else {
			// The mouse is closer to north than east
			if (de > dn) {
				return lines[0];
			}
			// The mouse is clsoer to the south than the east
			else if (de > ds) {
				return lines[2];
			}
			// The mouse is closer to the east
			else {
				return lines[1];
			}
		}
	}

	public boolean isFullBox() {
		boolean isFull = true;
		for (Line l : getLines()) {
			if (!l.isTaken()) {
				isFull = false;
			}

		}

		return isFull;
	}

	private void initLines() {

		int ty = DBGame.sDotY;
		int rx = DBGame.sDotX + DBGame.dotSpace;
		int by = DBGame.sDotY + DBGame.dotSpace;
		int lx = DBGame.sDotX;

		switch (boxId) {

		case 0:

			lines[0] = new Line(lx, rx, ty, ty, "N");
			lines[1] = new Line(rx, rx, ty, by, "E");
			lines[2] = new Line(lx, rx, by, by, "S");
			lines[3] = new Line(lx, lx, ty, by, "W");
			return;
		case 1:
			ty = DBGame.sDotY;
			rx = DBGame.sDotX + 2 * DBGame.dotSpace;
			by = DBGame.sDotY + DBGame.dotSpace;
			lx = DBGame.sDotX + DBGame.dotSpace;

			lines[0] = new Line(lx, rx, ty, ty, "N");
			lines[1] = new Line(rx, rx, ty, by, "E");
			lines[2] = new Line(lx, rx, by, by, "S");
			lines[3] = new Line(lx, lx, ty, by, "W");
			return;
		case 2:
			ty = DBGame.sDotY;
			rx = DBGame.sDotX + 3 * DBGame.dotSpace;
			by = DBGame.sDotY + DBGame.dotSpace;
			lx = DBGame.sDotX + 2 * DBGame.dotSpace;

			lines[0] = new Line(lx, rx, ty, ty, "N");
			lines[1] = new Line(rx, rx, ty, by, "E");
			lines[2] = new Line(lx, rx, by, by, "S");
			lines[3] = new Line(lx, lx, ty, by, "W");
			return;
		case 3:
			ty = DBGame.sDotY + DBGame.dotSpace;
			rx = DBGame.sDotX + DBGame.dotSpace;
			by = DBGame.sDotY + 2 * DBGame.dotSpace;
			lx = DBGame.sDotX;

			lines[0] = new Line(lx, rx, ty, ty, "N");
			lines[1] = new Line(rx, rx, ty, by, "E");
			lines[2] = new Line(lx, rx, by, by, "S");
			lines[3] = new Line(lx, lx, ty, by, "W");
			return;
		case 4:
			ty = DBGame.sDotY + DBGame.dotSpace;
			rx = DBGame.sDotX + 2 * DBGame.dotSpace;
			by = DBGame.sDotY + 2 * DBGame.dotSpace;
			lx = DBGame.sDotX + DBGame.dotSpace;

			lines[0] = new Line(lx, rx, ty, ty, "N");
			lines[1] = new Line(rx, rx, ty, by, "E");
			lines[2] = new Line(lx, rx, by, by, "S");
			lines[3] = new Line(lx, lx, ty, by, "W");
			return;
		case 5:
			ty = DBGame.sDotY + DBGame.dotSpace;
			rx = DBGame.sDotX + 3 * DBGame.dotSpace;
			by = DBGame.sDotY + 2 * DBGame.dotSpace;
			lx = DBGame.sDotX + 2 * DBGame.dotSpace;

			lines[0] = new Line(lx, rx, ty, ty, "N");
			lines[1] = new Line(rx, rx, ty, by, "E");
			lines[2] = new Line(lx, rx, by, by, "S");
			lines[3] = new Line(lx, lx, ty, by, "W");
			return;
		case 6:
			ty = DBGame.sDotY + 2 * DBGame.dotSpace;
			rx = DBGame.sDotX + DBGame.dotSpace;
			by = DBGame.sDotY + 3 * DBGame.dotSpace;
			lx = DBGame.sDotX;

			lines[0] = new Line(lx, rx, ty, ty, "N");
			lines[1] = new Line(rx, rx, ty, by, "E");
			lines[2] = new Line(lx, rx, by, by, "S");
			lines[3] = new Line(lx, lx, ty, by, "W");
			return;
		case 7:
			ty = DBGame.sDotY + 2 * DBGame.dotSpace;
			rx = DBGame.sDotX + 2 * DBGame.dotSpace;
			by = DBGame.sDotY + 3 * DBGame.dotSpace;
			lx = DBGame.sDotX + DBGame.dotSpace;

			lines[0] = new Line(lx, rx, ty, ty, "N");
			lines[1] = new Line(rx, rx, ty, by, "E");
			lines[2] = new Line(lx, rx, by, by, "S");
			lines[3] = new Line(lx, lx, ty, by, "W");
			return;
		case 8:
			ty = DBGame.sDotY + 2 * DBGame.dotSpace;
			rx = DBGame.sDotX + 3 * DBGame.dotSpace;
			by = DBGame.sDotY + 3 * DBGame.dotSpace;
			lx = DBGame.sDotX + 2 * DBGame.dotSpace;

			lines[0] = new Line(lx, rx, ty, ty, "N");
			lines[1] = new Line(rx, rx, ty, by, "E");
			lines[2] = new Line(lx, rx, by, by, "S");
			lines[3] = new Line(lx, lx, ty, by, "W");
			return;
		}
	}
}
