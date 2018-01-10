package brockstar17.dotsandboxes.multiplayer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class DBServer extends JFrame implements Runnable
{

	private ServerSocket serverSocket;
	private final int PORT = 22222;
	private Thread thread;
	private DBServerThread client;

	private int clientCount;

	public DBServer()
	{

		/*
		 * Start the server and wait for clients to connect.
		 */
		try {
			serverSocket = new ServerSocket(PORT);

		} catch (IOException e) {

		}

		start();
	}

	public void addThread(Socket socket) {
		System.out.println("Client accepted: " + socket);
		client = new DBServerThread(this, socket);
		try {
			client.open();
			client.start();
		} catch (IOException ioe) {
			System.out.println("Error opening thread: " + ioe);
		}
	}

	@Override
	public void run() {
		while (thread != null) {
			try {
				System.out.println("Waiting for a client ...");
				addThread(serverSocket.accept());
			} catch (IOException ie) {
				System.out.println("Acceptance Error: " + ie);
			}
		}
	}

	public void start() {
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}

	public boolean stop() {
		if (thread != null) {

			thread = null;
			return true;
		}
		return false;
	}

}
