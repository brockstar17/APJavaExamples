package brockstar17.dotsandboxes.base;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import brockstar17.dotsandboxes.multiplayer.Box;
import brockstar17.dotsandboxes.multiplayer.Line;
import brockstar17.dotsandboxes.multiplayer.Utils;

@SuppressWarnings("serial")
public class BoxandDots extends JPanel implements MouseListener
{

	public static int bwidth;
	public static int sDotX, sDotY;
	public static int dotSpace;
	public static int dotSize;
	public int rn;

	public static Box[] board;

	private ComputerAI ai;

	protected boolean goAgain;

	public BoxandDots()
	{

		JFrame frame = new JFrame("Dots and Boxes");

		this.setSize(800, 600);
		frame.setSize(this.getSize());
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("resources/DotsBoxes.png"));

		bwidth = 540;
		sDotX = 130;
		sDotY = 30;
		dotSize = 10;
		rn = (int) (Math.random() * 8);
		dotSpace = (bwidth - (3 * 10)) / 3;

		board = new Box[9];
		for (int i = 0; i < board.length; i++) {
			board[i] = new Box(i);
		}

		this.ai = new ComputerAI(this);
		goAgain = false;

		this.addMouseListener(this);

		Container c = frame.getContentPane();

		c.add(this);

		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		for (Box b : board) {
			b.drawBox(g);
		}
		drawDots(g);

	}

	private void drawDots(Graphics g) {

		int x = 130;
		int y = 30;

		g.setColor(Color.BLACK);

		for (int sx = 0; sx < 4; sx++) {

			for (int sy = 0; sy < 4; sy++) {
				g.fillRect(x, y, 10, 10);
				y += (BoxandDots.bwidth - (3 * 10)) / 3;

			}
			y = 30;
			x += (BoxandDots.bwidth - (3 * 10)) / 3;

		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		rn = (int) (Math.random() * 8);

		int box = Utils.getBoxId(e.getX(), e.getY());

		// System.out.println("Box " + box);

		if (box != -1 && board[box].getLineClicked(e.getX(), e.getY()).isLineClicked(e.getX(), e.getY()) && !board[box].isFullBox()) {
			Line click = board[box].getLineClicked(e.getX(), e.getY());
			// System.out.println("Line " + click.getOrdinance());
			if (!click.isTaken()) {
				click.setColor(Color.BLUE);
				click.setTaken();

				if (board[box].isFullBox()) {
					goAgain = true;
				}

				int adj = Utils.getAdjId(click.getOrdinance(), box);
				if (adj != -1) {
					board[adj].updateShareLine(click.getOrdinance(), Color.BLUE);
				}

				endTurn("player1");
			}

		}

		repaint();
	}

	public boolean isBoardFull() {
		boolean isFull = true;
		for (Box b : board) {
			if (!b.isFullBox()) {
				isFull = false;
			}
		}

		return isFull;
	}

	public void endTurn(String player) {

		for (Box b : board) {

			if (b.isFullBox()) {
				b.setName(player);
			}

		}

		// Call the AI
		if (player.equals("player1") && !goAgain) {
			ai.pickAISpace();
		}

		goAgain = false;

		repaint();

		if (isBoardFull()) {
			int count = 0;
			for (Box b : board) {
				if (b.getName().equals("player1")) {
					count++;
				}
			}
			if (count < 5) {
				JOptionPane.showMessageDialog(this, "Player 2 wins");
			}
			else {
				JOptionPane.showMessageDialog(this, "Player 1 wins");
			}

			for (int i = 0; i < board.length; i++) {
				board[i] = new Box(i);
			}

		}

	}

	public static void main(String[] args) {
		new BoxandDots();
	}

}
