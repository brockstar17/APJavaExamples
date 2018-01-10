package brockstar17.samplegui;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class HelloGUI extends JFrame
{

	/**
	 * This program should display a GUI with the title Hello GUI. <br>
	 * It does not. Figure out why. <br>
	 * There is also a weird problem with closing the window, can you figure it out? <br>
	 * I don't like the size of this window. Change it to be twice as wide as it is now and half as
	 * tall. <br>
	 * Lastly, take a look at the methods available in the JFrame class, do this by using this. ...
	 */

	public HelloGUI()
	{
		super("Hello GUI");
		/*
		 * Sets whether this frame is visible or not
		 */
		this.setVisible(false);
		/*
		 * Sets the size of the window
		 */
		this.setSize(600, 600);
		/*
		 * Sets the operation that will be performed on close command
		 */
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}

	public static void main(String[] args) {
		new HelloGUI();
	}
}
