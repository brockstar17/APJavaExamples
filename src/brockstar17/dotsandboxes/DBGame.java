package brockstar17.dotsandboxes;

import javax.swing.JOptionPane;

import brockstar17.dotsandboxes.utils.IPFrame;

public class DBGame
{

	public static int bwidth;
	public static int sDotX, sDotY;
	public static int dotSpace;
	public static int dotSize;

	public static void main(String[] args) {
		bwidth = 540;
		sDotX = 130;
		sDotY = 30;
		dotSize = 10;

		dotSpace = (bwidth - (3 * 10)) / 3;

		boolean startServer = JOptionPane.showConfirmDialog(null, "Click yes to start the Server. Click no to join a server") == 0 ? true : false;

		if (startServer) {

			new DBServer(22222);
		}
		else {
			new IPFrame();

		}
	}

}
