package brockstar17.dotsandboxes;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class DBServer extends JFrame implements Runnable
{

	private DBServerThread games[] = new DBServerThread[10];
	private Socket pair[] = new Socket[2];
	private ServerSocket serverSocket;
	private Thread thread;
	private int gameCount;
	private boolean stop;
	private int clients = 0;

	private JTextArea serverOutputLog;

	public DBServer(int port)
	{
		// ----- Setup the Server GUI
		super("Server Log");
		this.setSize(400, 600);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		serverOutputLog = new JTextArea();
		serverOutputLog.setEditable(false);

		this.add(serverOutputLog);

		this.logAction("Starting Server.");

		// -----

		try {
			// Setting up the server, this server can handle up to 10 games.
			logAction("Binding to port " + port + ", please wait  ...");
			serverSocket = new ServerSocket(port);
			logAction("Server started: " + serverSocket);
			stop = false;
			start();
		} catch (IOException ioe) {
			logAction("Can not bind to port " + port + ": " + ioe.getMessage());
		}

	}

	/**
	 * This is where the server listens for clients.
	 */
	public void run() {

		// Check that the thread is not null.
		while (thread != null) {
			// Stop this thread if stop is true.
			if (stop) {
				return;
			}
			try {

				logAction("Waiting for a client ...");
				// Try to accept a client a client pair
				pair[clients] = serverSocket.accept();
				clients++;
				if (pair[0] != null && pair[1] != null) {
					addGameThread(pair[0], pair[1]);
					pair[0] = null;
					pair[1] = null;
					clients = 0;
				}
			} catch (IOException ioe) {
				logAction("Server accept error: " + ioe);
				// Something went wrong, stop the server.

				stop();
			}
		}
	}

	/**
	 * This method initializes the thread and starts it, basically it just calls the run method.
	 */
	public void start() {
		// Make sure the thread is not already initialized, it would be bad if it was.
		if (thread == null) {
			// Make a new thread.
			thread = new Thread(this);
			// Start it.
			thread.start();
		}
	}

	/**
	 * This method will set the boolean value stop to true. This effectively stops the server on the
	 * next loop of the run method.
	 */
	public void stop() {
		// Make sure the thread is not null.
		if (thread != null) {
			// Set the value.
			this.stop = true;
			// Null the thread.
			clients--;
			thread = null;

		}
	}

	private int findGame(int ID) {

		for (int i = 0; i < gameCount; i++)
			if (games[i].getID() == ID)
				return i; // Success return the index of the array.
		return -1;
	}

	/**
	 * Removes the target thread from the server. Used if a client disconnects or the server is
	 * unable to communicate with a client.
	 */
	public synchronized void remove(int ID) {
		// Get the index of the client.
		int pos = findGame(ID);
		// Check that the client was found successfully.
		if (pos >= 0) {
			// The thread to terminate.
			DBServerThread toTerminate = games[pos];
			logAction("Removing client thread " + ID + " at " + pos);

			// Shift the clients down the array.
			if (pos < gameCount - 1) // Only necessary if the client is not the last one to join.
				for (int i = pos + 1; i < gameCount; i++)
					games[i - 1] = games[i];
			gameCount--;
			try {
				toTerminate.close();
			} catch (IOException ioe) {
				// Hope this line doesn't ever happen. Not the end of the world. Just a loose thread
				// with unclosed data streams running around the memory of your program. It probably
				// wont haunt you for the rest of your days. Probably.
				logAction("Error closing thread: " + ioe);
			}
			toTerminate.stopThread(); // Stop the thread.
		}
	}

	/**
	 * Add a thread to the server. This thread will be used to communicate with a client pair.
	 * 
	 * @param player1
	 *            A socket accepted from the server.
	 * @param player2
	 *            A socket accepted from the server.
	 */
	private void addGameThread(Socket player1, Socket player2) {
		if (gameCount < games.length) {
			logAction("Starting game thread.");
			games[gameCount] = new DBServerThread(this, player1, player2);
			try {
				games[gameCount].open();
				games[gameCount].start();
				gameCount++;
			} catch (IOException ioe) {
				logAction("Error opening thread: " + ioe);
			}
		}
		else
			logAction("Game refused: maximum " + games.length + " reached.");
	}

	protected void logAction(String message) {
		this.serverOutputLog.setText(this.serverOutputLog.getText() + message + "\n");
	}

}
