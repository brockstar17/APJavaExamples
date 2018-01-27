package brockstar17.dotsandboxes;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import brockstar17.dotsandboxes.utils.Box;

public class DBClientThread extends Thread
{
	private Socket socket;
	private DBClient client;
	// private DataInputStream dis;
	private ObjectInputStream ois;

	private boolean stop;

	private Box[] board;

	/**
	 * Construct the client thread.
	 * 
	 * @param client
	 *            The client to communicate with.
	 * @param socket
	 *            The socket that the server provides.
	 */
	public DBClientThread(DBClient client, Socket socket)
	{
		this.board = new Box[9];
		for (int i = 0; i < board.length; i++) {
			board[i] = new Box(i);
		}
		this.client = client;
		this.socket = socket;
		open();
		start();
	}

	/**
	 * Open the input stream.
	 */
	public void open() {
		try {

			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException ioe) {

			client.stop();
		}
	}

	/**
	 * Close the input stream.
	 */
	public void close() {
		client.stop();
	}

	/**
	 * The run method of this thread. Continuously listens for input from the server thread and
	 * handles that input.
	 */
	public void run() {
		while (true) {
			if (stop) {
				return;
			}
			try {
				String msg = ois.readUTF();
				for (Box b : board) {
					b.deserialize(ois);

				}
				client.setBoard(board);
				client.handle(msg);

			} catch (IOException ioe) {

				client.stop();
			} catch (ClassNotFoundException e) {

				e.printStackTrace();
			}
		}
	}

	/**
	 * Stop this thread by setting value of stop to true.
	 */
	protected void stopThread() {

		this.stop = true;

	}
}
