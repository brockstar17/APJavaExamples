package brockstar17.samples.ex1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 * This class is designed to be an example of how java classes work together.<br>
 * It will demonstrate class hierarchy, Java inheritance, and static versus non-static.
 * @author Brock
 *
 */
@SuppressWarnings("serial")
public class Example1 extends JFrame implements ActionListener {

	// This is a static integer
	public static int testInt = 0;
	
	/* This is a constructor for the class. 
	 * It creates a new instance of this object.
	 * Every class has a default constructor that is built into the Java framework,
	 * however if you want to handle the creation of your class in a specific way it
	 * is best to make your own constructor as I do below.
	 */
	public Example1() {
		
		// This sets the size of the window
		this.setSize(500, 500);
		
		
		// This creates a new button
		JButton button = new JButton("Click to open dialog");
		button.addActionListener(this); // Adds the action listener to the button
		this.add(button); // Add the button to the frame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set the window close operation
		this.setVisible(true); // Allows us to see the gui
		
	}
	
	
	
	/**
	 * Classes can be nested inside of classes. <br>
	 * This is a protected class so it can only be accessed by classes within this package. <br>
	 * It can extend and implement just the same as any other class, as well as contain its own fields and methods.<br>
	 * I extend JFrame to inherit the properties of the JFrame class along with the methods inside of it.<br>
	 * I implement ActionListener to run code when the timer ticks.
	 * @author Brock
	 *
	 */
	protected class Dialog extends JFrame implements ActionListener {
		
		// This is a constructor for my Dialog object
		public Dialog(int num){
			
			testInt = num; // Reassign the value of testInt
			this.setSize(400, 100); // Size the window
			this.add(new Panel(num)); // Add a JPanel to the JFrame
			
			Timer t = new Timer(100, this); // A timer
			t.start(); // Start the timer
			
			this.setResizable(false); // Prevent resizing
			this.setVisible(true); // Allows us to see the gui
			this.setLocationRelativeTo(null); // This is a handy trick to center the window
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // The window close operation
		}

		/*
		 * This is a method that exists in the ActionPerformed interface.
		 * I override it to run certain code when a specific action is performed.
		 * In this case the action is the ticking of the timer in the Dialog class.
		 * Each time the timer ticks I want to repaint the JPanel to update the GUI.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			repaint(); // Repaints the gui, handled by action performed
			
		}
		
	}
	
	
	// This is another actionPerformed override to handle when the JButton is clicked
	@Override
	public void actionPerformed(ActionEvent e) {
		String num = JOptionPane.showInputDialog("Enter an integer."); // Get a string value of the inputted integer.
		int n = Integer.parseInt(num); // Parse the string to an integer
		new Dialog(n); // Open a Dialog GUI by creating a new instance of it.
		
	}
	
}
