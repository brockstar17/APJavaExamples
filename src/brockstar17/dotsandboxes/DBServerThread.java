package brockstar17.dotsandboxes;

import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import brockstar17.dotsandboxes.utils.Box;
import brockstar17.dotsandboxes.utils.Line;
import brockstar17.dotsandboxes.utils.Utils;

public class DBServerThread extends Thread
{
	private DBServer server;
	private Socket player1;
	private Socket player2;
	private int ID = -1;
	private DataInputStream p1dis;
	private DataInputStream p2dis;

	// private DataOutputStream p1dos;
	// private DataOutputStream p2dos;
	private ObjectOutputStream p1oos;
	private ObjectOutputStream p2oos;

	private boolean stop;

	private Box[] board;
	private boolean player1Turn;
	private boolean goAgain;
	private int fullBoxes;

	/**
	 * Constructs the server thread. This thread is used to connect two clients and enable their
	 * communication.
	 * 
	 * @param server
	 *            The server that this thread is running on.
	 * @param player1
	 *            The first player socket that connected.
	 * @param player2
	 *            The second player socket that connected.
	 */
	public DBServerThread(DBServer server, Socket player1, Socket player2)
	{
		super();
		// ----- Setup the thread.
		this.server = server;
		this.player1 = player1;
		this.player2 = player2;
		ID = player1.getPort();
		stop = false;
		fullBoxes = 0;
		// -----

		// ----- Setup the game state.
		player1Turn = false;
		board = new Box[9];

		for (int i = 0; i < board.length; i++) {
			board[i] = new Box(i);
		}
		// -----
	}

	/**
	 * Send data to the output stream of the thread. This will be sent to both of the clients.
	 * 
	 * @param command
	 *            The command to send.
	 */
	public void send(String command) {

		sendPacket(p1oos, command);
		sendPacket(p2oos, command);

	}

	/**
	 * Sends data to a specific output stream.
	 * 
	 * @param dos
	 *            The output stream to send over.
	 * @param command
	 *            The command to send.
	 */
	public void send(ObjectOutputStream oos, String command) {

		sendPacket(oos, command);

	}

	private void sendPacket(ObjectOutputStream oos, String command) {
		try {
			// Write the command first, the order is important because if we write something first
			// it must be read first as well.
			oos.writeUTF(command);
			// Write all boxes to the output stream.
			for (Box b : board) {
				b.sendSerialized(oos);

			}

			// Flush the output streams.
			oos.flush();

		} catch (IOException e) {

			server.logAction(ID + " ERROR sending: " + e.getMessage());
			// Error communicating with the client.
			server.remove(ID);
			// Set the value of stop to true, this will stop the thread on the next loop.
			stop = true;
		}

	}

	/**
	 * Sends an update to both clients updating whose turn it is.
	 */
	public void updateClientTurns() {

		int temp = 0;
		for (Box b : board) {
			if (b.isFullBox()) {
				temp++;
			}
		}
		if (temp > fullBoxes) {
			goAgain = true;
			fullBoxes = temp;
		}

		if (!goAgain) {
			player1Turn = !player1Turn;
		}
		if (player1Turn) {
			send(p1oos, "TURN_UPDATE true");
			send(p2oos, "TURN_UPDATE false");
		}
		else {
			send(p1oos, "TURN_UPDATE false");
			send(p2oos, "TURN_UPDATE true");
		}
		goAgain = false;
	}

	/**
	 * Gets the ID of this thread. The ID should be the port number of the first player.
	 * 
	 * @return The ID of the thread.
	 */
	public int getID() {
		return ID;
	}

	public void run() {
		server.logAction("Server Thread " + ID + " running.");
		while (true) {
			// If the value of stop is true then return from the run method.
			if (stop) {
				return;
			}
			try {
				// Handle input from client.

				if (player1Turn) {

					handle(player1.getPort(), p1dis.readUTF(), p1dis.readInt(), p1dis.readInt(), p1dis.readInt());
				}
				else {

					handle(player2.getPort(), p2dis.readUTF(), p2dis.readInt(), p2dis.readInt(), p2dis.readInt());
				}

			} catch (IOException ioe) {
				server.logAction(ID + " ERROR reading: " + ioe.getMessage());
				// Unable to communicate, terminate thread.
				server.remove(ID);
				// Return from the run method.
				return;
			}
		}
	}

	/**
	 * Handles commands from both clients playing the game.
	 * 
	 * @param ID
	 *            The port of the client.
	 * @param command
	 *            The command the client sent.
	 * @param temp
	 *            The box array that the client sent to the server for validation.
	 */
	private void handle(int ID, String command, int box, int mx, int my) {
		switch (command.substring(0, command.indexOf(" "))) {
		case "MOVE":
			// server.logAction("Client with ID " + ID + " attempted to move.");
			if (box != -1 && board[box].getLineClicked(mx, my).isLineClicked(mx, my) && !board[box].isFullBox()) {
				Line click = board[box].getLineClicked(mx, my);

				// Check if the line is taken.
				if (!click.isTaken()) {
					click.setColor(player1Turn ? Color.BLUE : Color.RED);
					click.setTaken();
					if (board[box].isFullBox()) {
						board[box].setName(player1Turn ? "player1" : "player2");
					}

					int adj = Utils.getAdjId(click.getOrdinance(), box);
					if (adj != -1) {
						board[adj].updateShareLine(click.getOrdinance(), player1Turn ? "player1" : "player2", player1Turn ? Color.BLUE : Color.RED);
					}

					if (isBoardFull()) {
						send("VALID_MOVE ");
						endGame();
					}
					else {
						updateClientTurns();
						send("VALID_MOVE ");
					}

				}
				else {
					server.logAction("Player " + ID + " made an invalid move.");
					if (player1Turn) {
						send(p1oos, "INVALID_MOVE ");
					}
					else {
						send(p2oos, "INVALID_MOVE ");
					}
				}

			}
			else {
				server.logAction("Player " + ID + " made an invalid move.");
				if (player1Turn) {
					send(p1oos, "INVALID_MOVE ");
				}
				else {
					send(p2oos, "INVALID_MOVE ");
				}
			}

			break;

		case "PLAY_AGAIN":
			resetGame();
			break;
		}
	}

	/**
	 * Determines if the game is over because the board is full.
	 * 
	 * @return True if the board is full.
	 */
	private boolean isBoardFull() {
		boolean isFull = true;
		for (Box b : board) {
			if (!b.isFullBox()) {
				isFull = false;
			}
		}

		return isFull;
	}

	private void endGame() {

		int count = 0;
		for (Box b : board) {
			if (b.getName().equals("player1")) {
				count++;
			}
		}
		if (count < 5) {
			send(p1oos, "LOSE ");
			send(p2oos, "WON ");
		}
		else {
			send(p1oos, "WON ");
			send(p2oos, "LOSE ");
		}

		server.logAction("Game " + ID + " has ended.");

	}

	private void resetGame() {
		for (int i = 0; i < board.length; i++) {
			board[i] = new Box(i);
		}
		updateClientTurns();
	}

	/**
	 * Opens the data streams.
	 * 
	 * @throws IOException
	 *             Pray it doesn't throw this exception, because if it does, we have problems.
	 */
	public void open() throws IOException {
		p1dis = new DataInputStream(new BufferedInputStream(player1.getInputStream()));

		// p1dos = new DataOutputStream(new BufferedOutputStream(player1.getOutputStream()));
		p1oos = new ObjectOutputStream(player1.getOutputStream());
		p2dis = new DataInputStream(new BufferedInputStream(player2.getInputStream()));

		// p2dos = new DataOutputStream(new BufferedOutputStream(player2.getOutputStream()));
		p2oos = new ObjectOutputStream(player2.getOutputStream());
		updateClientTurns();
	}

	/**
	 * Close the data streams.
	 * 
	 * @throws IOException
	 *             Something went wrong communicating.
	 */
	public void close() throws IOException {
		server.logAction("Closing streams for game thread " + ID);
		if (player1 != null)
			player1.close();
		if (player2 != null) {
			player2.close();
		}
	}

	/**
	 * Stop this thread by setting value of stop to true.
	 */
	protected void stopThread() {
		server.logAction("Stopping game thread with ID " + ID);
		this.stop = true;
	}
}
