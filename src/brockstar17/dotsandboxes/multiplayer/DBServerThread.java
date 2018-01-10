package brockstar17.dotsandboxes.multiplayer;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class DBServerThread extends Thread
{
	private Socket socket = null;
	private DBServer server = null;
	private int ID = -1;
	private DataInputStream streamIn = null;

	public DBServerThread(DBServer server, Socket socket)
	{
		this.server = server;
		this.socket = socket;
		ID = socket.getPort();
	}

	public void run() {
		System.out.println("Server Thread " + ID + " running.");
		while (true) {
			try {
				System.out.println(streamIn.readUTF());
			} catch (IOException ioe) {
			}
		}
	}

	public void open() throws IOException {
		streamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
	}

	public void close() throws IOException {
		if (socket != null)
			socket.close();
		if (streamIn != null)
			streamIn.close();
	}
}
