package brockstar17.socketex;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ChatClient
{
	private int port = 22222;
	private String ip = "localhost";

	private DataOutputStream dos;
	private DataInputStream dis;

	private Socket socket;
	private int errors;

	private Scanner scanner;

	public ChatClient()
	{
		try {
			socket = new Socket(ip, port);
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

			scanner = new Scanner(System.in);

			errors = 0;

		} catch (UnknownHostException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

		String input;

		while (true) {
			try {

				// System.out.println("About to input");

				input = scanner.nextLine();

				if (input != null) {
					// System.out.println("Client says " + input);
					dos.writeUTF(input);
					dos.flush();
				}

				String serverResponse = dis.readUTF();
				if (serverResponse != null) {
					System.out.println("The server Says '" + serverResponse + "'");
				}

				if (errors > 10) {
					System.out.println("Fatal error communicating with client");
					dos.close();
					dis.close();
					socket.close();
				}

			} catch (IOException e) {
				errors++;
			}
		}

	}

	public static void main(String[] args) {
		new ChatClient();
	}
}
