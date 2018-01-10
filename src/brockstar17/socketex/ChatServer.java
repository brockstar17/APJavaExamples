package brockstar17.socketex;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer
{

	private ServerSocket serverSocket;
	private Socket clientSocket;
	private final int port = 22222;

	private DataInputStream dis;
	private DataOutputStream dos;
	private int errors;

	public static void main(String[] args) {
		new ChatServer();
	}

	public ChatServer()
	{
		try {
			this.serverSocket = new ServerSocket(port);

			System.out.println("The Server is Running.");

			this.clientSocket = serverSocket.accept();

			System.out.println("Client has joined.");

			dos = new DataOutputStream(clientSocket.getOutputStream());
			dis = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));

			errors = 0;

		} catch (IOException e) {
			e.printStackTrace();

		}

		serverLoop();

	}

	private void serverLoop() {
		while (true) {
			try {
				String input = dis.readUTF();
				if (input != null) {
					String out = input + " has " + input.length() + " characters.";
					// System.out.println(out);
					dos.writeUTF(out);
					dos.flush();
				}

				if (errors > 10) {
					System.out.println("Fatal error communicating with client");
					dos.close();
					dis.close();
					clientSocket.close();
				}

			} catch (IOException e) {
				errors++;

			}
		}
	}

}
