package brockstar17.dotsandboxes;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import brockstar17.dotsandboxes.utils.Box;
import brockstar17.dotsandboxes.utils.Utils;

@SuppressWarnings("serial")
public class DBClient extends JPanel implements Runnable, MouseListener, WindowListener
{
	private Socket socket;
	private Thread thread;

	private DataOutputStream dos;
	private ObjectInputStream ois;
	private DBClientThread clientThread;

	private String command;
	private boolean stop;

	private JFrame frame;

	private Box[] board;

	private boolean isMyTurn;
	private String gameEndedMessage;

	/**
	 * Construct the client.
	 * 
	 * @param serverName
	 *            The IP address of the server.
	 * @param serverPort
	 *            The port to connect to on the server.
	 */
	public DBClient(String serverName, int serverPort)
	{

		// ----- Initialize Client State
		// This client should predict how the server will respond.
		board = new Box[9];
		for (int i = 0; i < board.length; i++) {
			board[i] = new Box(i);
		}

		// -----

		// ----- Setup Client GUI
		frame = new JFrame("Dots and Boxes");
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("resources/DotsBoxes.png"));
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));

		Container c = frame.getContentPane();
		c.add(this);

		addMouseListener(this);

		frame.addWindowListener(this);

		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// -----

		// ----- Initialize Client
		stop = false;

		try {
			// Create a socket to connect with the server.
			socket = new Socket(serverName, serverPort);

			// Start this thread.
			start();
		} catch (UnknownHostException uhe) {

		} catch (IOException ioe) {

		}

		// -----
	}

	/**
	 * The run method for the client.
	 */
	public void run() {
		while (thread != null) {
			// Stop the client if stop is true.
			if (stop) {
				return;
			}

		}
	}

	/**
	 * Sends a packet to the server.
	 * 
	 * @param cmd
	 *            The command to send.
	 * @param box
	 *            The id of the box.
	 * @param mx
	 *            The mouse x position.
	 * @param my
	 *            The mouse y position.
	 */
	private void sendPacket(String cmd, int box, int mx, int my) {
		try {
			dos.writeUTF(cmd);
			dos.writeInt(box);
			dos.writeInt(mx);
			dos.writeInt(my);
		} catch (IOException e) {

			e.printStackTrace();
			stop();
		}

	}

	/**
	 * Handle commands received from the server thread.
	 * 
	 * @param command
	 *            The command the server thread sent.
	 * @param board
	 *            The board sent from the server.
	 */
	public void handle(String command) {
		String[] parts = command.split(" ");
		// System.out.println("Handle " + parts[0]);
		switch (parts[0]) {

		case "VALID_MOVE":

			break;

		case "INVALID_MOVE":
			// Add a message to client message board.
			break;

		case "TURN_UPDATE":
			// System.out.println("Turn Updatae");

			if (parts[1] != null) {
				if (parts[1].equals("true")) {
					this.isMyTurn = true;
					frame.setTitle("Dots and Boxes: Your turn.");
				}
				else {
					this.isMyTurn = false;
					frame.setTitle("Dots and Boxes: Opponents Turn");
				}
			}

			break;

		case "WON":
			gameEndedMessage = "You Won!";

			break;

		case "LOSE":
			gameEndedMessage = "You Lost!";

			break;

		}

		repaint();

	}

	/**
	 * Starts the client thread and initializes the data streams.
	 * 
	 * @throws IOException
	 */
	public void start() throws IOException {

		dos = new DataOutputStream(socket.getOutputStream());

		if (thread == null) {
			clientThread = new DBClientThread(this, socket);
			thread = new Thread(this);
			thread.start();
		}
	}

	/**
	 * Stops this client and all threads associated with it.
	 */
	public void stop() {
		if (thread != null) {

			try {

				if (socket != null)

					socket.close();
			} catch (IOException ioe) {

			}
			// clientThread.close();
			clientThread.stopThread();
			System.exit(0);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		for (Box b : board) {
			if (b != null) {
				b.drawBox(g);
			}

		}
		drawDots(g);

		if (gameEndedMessage != null && !gameEndedMessage.equals("")) {
			g.setFont(new Font("Arial", Font.BOLD, 50));
			g.drawString(gameEndedMessage, this.frame.getWidth() / 2 - g.getFontMetrics().stringWidth(gameEndedMessage) / 2, this.frame.getHeight() / 2);

		}

	}

	private void drawDots(Graphics g) {

		int x = 130;
		int y = 30;

		g.setColor(Color.BLACK);

		for (int sx = 0; sx < 4; sx++) {

			for (int sy = 0; sy < 4; sy++) {
				g.fillRect(x, y, 10, 10);
				y += (DBGame.bwidth - (3 * 10)) / 3;

			}
			y = 30;
			x += (DBGame.bwidth - (3 * 10)) / 3;

		}

	}

	public void setBoard(Box[] board) {
		this.board = board;
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
		stop();
	}

	@Override
	public void windowClosing(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		if (isMyTurn) {

			int box = Utils.getBoxId(e.getX(), e.getY());

			sendPacket("MOVE ", box, e.getX(), e.getY());

		}
		else {

		}
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
	}
}
