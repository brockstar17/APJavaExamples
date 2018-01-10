package brockstar17.tictactics;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PaintBoard extends JPanel
{
	private TicTacToe frame;
	private int lvl;
	private int rvl;
	private int thl;
	private int bhl;

	// Constructs the JPanel that paints the board
	public PaintBoard(TicTacToe frame)
	{
		// Assign fields
		this.frame = frame;

		// Used for
		int y = 20;

		lvl = (int) (frame.getWidth() * .333); // The x pos of the left-most vertical line
		rvl = (int) (frame.getWidth() * .667); // The x pos of the right-most vertical line
		thl = (int) ((frame.getHeight() - y) * .333); // The y pos of the top-most horizontal line
		// The y pos of the bottom-most horizontal line
		bhl = (int) ((frame.getHeight() - y) * .667);

	}

	/**
	 * This method is overriden from the JPanel class.<br>
	 * This gets called whenever the canvas is painted.
	 */
	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		drawLines(g);
		drawBoard(g);

	}

	/**
	 * This method draws the lines of the board
	 * 
	 * @param g
	 *            the graphics instance used in paintComponent
	 */
	private void drawLines(Graphics g) {
		g.setColor(Color.BLACK);

		g.drawLine(lvl, 0, lvl, frame.getHeight());
		g.drawLine(rvl, 0, rvl, frame.getHeight());
		g.drawLine(0, thl, frame.getWidth(), thl);
		g.drawLine(0, bhl, frame.getWidth(), bhl);
	}

	/**
	 * Draws the X's and O's of the board.<br>
	 * This is based on the strings in the board array.<br>
	 * It is worth noting what strings will be drawn to the board.
	 * 
	 * @param g
	 *            the graphics instance used in paintComponent
	 */
	private void drawBoard(Graphics g) {

		for (int i = 0; i < frame.board.length; i++) {
			if (frame.board[i].equals("X")) {
				drawX(i, g);
			}
			else if (frame.board[i].equals("O")) {
				drawO(i, g);
			}
			else {
				// Do nothing
			}
		}
	}

	/**
	 * Draw the O at a given space.
	 * 
	 * @param space
	 *            the space the O is in.
	 * @param g
	 *            the graphics instance used in paintComponent
	 */
	private void drawO(int space, Graphics g) {

		g.setColor(Color.RED);
		int w = 110;
		int h = 130;

		int x = (lvl / 2) - (w / 2);
		int y = (thl / 2) - (h / 2);

		switch (space) {
		case 0:

			break;

		case 1:
			x += (rvl - lvl);
			break;

		case 2:
			x += (rvl - lvl) * 2;
			break;

		case 3:
			y += (bhl - thl);
			break;

		case 4:
			x += (rvl - lvl);
			y += (bhl - thl);
			break;

		case 5:
			x += (rvl - lvl) * 2;
			y += (bhl - thl);
			break;

		case 6:
			y += (bhl - thl) * 2;
			break;

		case 7:
			x += (rvl - lvl);
			y += (bhl - thl) * 2;
			break;

		case 8:
			x += (rvl - lvl) * 2;
			y += (bhl - thl) * 2;
			break;
		}

		g.drawArc(x, y, w, h, 0, 360);
	}

	/**
	 * Draws an X at the given space.
	 * 
	 * @param space
	 *            the space on the board.
	 * @param g
	 *            the graphics instance used in paintComponent.
	 */
	private void drawX(int space, Graphics g) {

		g.setColor(Color.BLUE);

		int rx = 10;
		int lx = 10;

		int ty = 10;
		int by = 10;

		switch (space) {
		case 0:
			rx = lvl - rx;
			by = thl - by;
			break;

		case 1:
			rx = rvl - rx;
			lx += lvl;
			by = thl - by;
			break;

		case 2:
			rx = frame.getWidth() - rx;
			lx += rvl;
			by = thl - by;
			break;

		case 3:
			rx = lvl - rx;
			ty += thl;
			by = bhl - by;
			break;

		case 4:
			lx += lvl;
			rx = rvl - rx;
			ty += thl;
			by = bhl - by;
			break;

		case 5:
			lx += rvl;
			rx = frame.getWidth() - rx;
			ty += thl;
			by = bhl - by;
			break;

		case 6:
			rx = lvl - rx;
			ty += bhl;
			by = frame.getHeight() - (by + 20);
			break;

		case 7:
			lx += lvl;
			rx = rvl - rx;
			ty += bhl;
			by = frame.getHeight() - (by + 20);
			break;

		case 8:
			lx += rvl;
			rx = frame.getWidth() - rx;
			ty += bhl;
			by = frame.getHeight() - (by + 20);
			break;

		default:

			break;
		}

		g.drawLine(lx, ty, rx, by);
		g.drawLine(rx, ty, lx, by);
	}

}
