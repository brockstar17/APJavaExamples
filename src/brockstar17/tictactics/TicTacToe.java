package brockstar17.tictactics;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class TicTacToe extends JFrame implements MouseListener
{

	// This string array represents the game board
	protected String[] board;

	// Constructor to create the game board
	public TicTacToe()
	{
		super("Tic Tac Toe"); // Call to super to set frame name
		this.setSize(600, 550); // Set size to 600 by 550 pixels
		this.setVisible(true); // This allows the frame to be visible
		this.setResizable(false); // Prevent resizing
		// Set the default close operation to terminate program when user clicks the red x
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null); // Handy trick to center the frame
		Container c = this.getContentPane(); // Get the content pane
		c.add(new PaintBoard(this)); // Add the JPanel to the frame

		this.addMouseListener(this); // Specify that this class is the mouse listener for this frame
		board = new String[9]; // Initialize the board
		// Assign values to the board, otherwise the strings will be null
		// and will throw null pointer exceptions
		for (int i = 0; i < board.length; i++) {
			board[i] = " ";
		}

		// This sets the icon image of the frame
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("resources/BDSigWhite.png"));

	}

	/**
	 * Main method, starts the program
	 * 
	 * @param args
	 *            only used in console apps
	 */
	public static void main(String[] args) {
		new TicTacToe();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	/**
	 * This is an event. <br>
	 * It is fired when the user clicks the mouse somewhere in the JFrame.<br>
	 * I use it to set the user's space.
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		int space = getArraySpace(e.getX(), e.getY());

		if (space != -1) {
			if (board[space].equals(" ")) {

				board[space] = "X";

			}

		}

		// Repaint the canvas
		this.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	/**
	 * Used to get the space in the String[] that corresponds to the space in the GUI that the user
	 * just clicked
	 * 
	 * @param mx
	 *            the x position of the mouse
	 * @param my
	 *            the y position of the mouse
	 * @return an index in the board array
	 */
	private int getArraySpace(int mx, int my) {
		int lvl = (int) (this.getWidth() * 0.333);
		int rvl = (int) (this.getWidth() * 0.667);
		int thl = (int) (this.getHeight() * 0.333);
		int bhl = (int) (this.getHeight() * 0.667);

		if (mx < lvl && mx > 0) { // Left Column
			if (my > 0 && my < thl) {
				// Top left space
				return 0;
			}
			else if (my > thl && my < bhl) {
				// Middle left space
				return 3;
			}
			else if (my > bhl && my < this.getHeight()) {
				// Bottom left space
				return 6;
			}
			else {
				return -1;
			}
		}
		else if (mx > lvl && mx < rvl) { // Middle Column
			if (my > 0 && my < thl) {
				// Top middle space
				return 1;
			}
			else if (my > thl && my < bhl) {
				// Middle middle space
				return 4;
			}
			else if (my > bhl && my < this.getHeight()) {
				// Bottom middle space
				return 7;
			}
			else {
				return -1;
			}
		}
		// Not an else to ensure cursor is actually in the GUI
		else if (mx > rvl && mx < this.getWidth()) { // Right Column
			if (my > 0 && my < thl) {
				// Top right space
				return 2;
			}
			else if (my > thl && my < bhl) {
				// Middle right space
				return 5;
			}
			else if (my > bhl && my < this.getHeight()) {
				// Bottom right space
				return 8;
			}
			else {
				return -1;
			}
		}
		else {
			// Return -1
			return -1;
		}

	}

}
