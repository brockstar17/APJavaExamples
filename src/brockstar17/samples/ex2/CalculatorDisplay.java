package brockstar17.samples.ex2;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * This class will display the calculator and handles all of the logic for the GUI components
 * 
 * @author Brock
 */
@SuppressWarnings("serial")
public class CalculatorDisplay extends JFrame implements ActionListener, KeyListener
{

	protected JTextArea textInput; // A private field for the text input box
	protected JTextArea history; // The history text area
	protected String input; // The string that stores what the current user
							 // input is

	public CalculatorDisplay()
	{
		// Call to super, this sets the name to the value of the string parameter
		super("Brock's Super Math Wizard in a Box");
		// Set the size of the window
		this.setSize(500, 500);
		// This is a handy trick to set the position of the window in the center of the screen
		this.setLocationRelativeTo(null);

		// Initialize input to an empty string
		this.input = "";
		// Call the function that adds components to the content pane
		addComponentsToPane(getContentPane());

		// This adds a key listener to this class/object and uses itself as the listener
		// Note: it can use any class that implements KeyListener
		addKeyListener(this);

		// Prevents resizing of the window
		this.setResizable(false);
		// This line makes the program close when the user clicks the red x
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Allows the user to see the GUI
		this.setVisible(true);
	}

	/**
	 * This method will add all the components needed for the calculator to the content pane
	 * 
	 * @param pane
	 *            The content pane to add components to
	 */
	private void addComponentsToPane(Container pane) {
		// Set the layout to be a GridBagLayout
		// This is a handy layout manager that is very flexible and I think looks nice
		pane.setLayout(new GridBagLayout());
		// Get an instance of the GridBagConstraints
		GridBagConstraints c = new GridBagConstraints();

		// Changing the gridy value changes what row the following elements will be placed on
		c.gridy = 0;
		// Sets the following elements to be 4 grid blocks wide
		c.gridwidth = 4;
		// Fill the grid horizontally
		c.fill = GridBagConstraints.HORIZONTAL;

		// Initialize textInput to default JTextArea
		this.textInput = new JTextArea();
		// Disable so the user can't click in it and prevent it from taking focus
		this.textInput.setEnabled(false);
		// Set the text color to black instead of the gray disabled color
		this.textInput.setDisabledTextColor(Color.BLACK);

		// This is the same process as the previous text area
		this.history = new JTextArea("History");
		this.history.setEnabled(false);
		this.history.setDisabledTextColor(Color.BLACK);

		// Set the insets to give a 10 pixel space below the text area
		c.insets = new Insets(0, 0, 10, 0);

		// Create a JScrollPane with the textInput text area inside
		JScrollPane inputScroll = new JScrollPane(this.textInput);
		// Prevent the scrollbars from appearing
		inputScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		inputScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		// Add the JScrollPane to the content pane
		pane.add(inputScroll, c);

		// Reset the spacing to zero
		c.insets = new Insets(0, 0, 0, 0);

		// Change the row that elements are placed on
		c.gridy = 1;
		c.gridwidth = 1; // Reset the width to 1 space

		/*
		 * Loop through the numbers 1-9 This loop is designed to add the numeric buttons with the
		 * exception of zero
		 */
		for (int i = 1; i < 10; i++) {
			if (i == 4) {
				c.gridy += 1; // Go to next row
			}
			if (i == 7) {
				c.gridy += 1; // Go to next row
			}

			addButton("" + i, pane, c); // Add the button
		}

		/*
		 * Del Button
		 */
		c.gridy = 1;
		c.gridx = 3;
		addButton("Back", pane, c);

		/*
		 * Addition button
		 */
		c.gridy = 2;
		c.gridx = 3;
		addButton("+", pane, c);

		/*
		 * Subtraction Button
		 */
		c.gridy = 3;
		c.gridx = 3;
		addButton("-", pane, c);

		/*
		 * Multiplication Button
		 */
		c.gridy = 4;
		c.gridx = 2;
		addButton("*", pane, c);

		/*
		 * Zero Button
		 */
		c.gridx = 0;
		addButton("0", pane, c);

		/*
		 * Decimal Button
		 */
		c.gridx = 1;
		addButton(".", pane, c);

		/*
		 * Division Button
		 */
		c.gridx = 3;
		addButton("/", pane, c);

		c.gridwidth = 2;

		/*
		 * Enter Button
		 */
		c.gridy = 5;
		c.gridx = 0;
		addButton("Enter", pane, c);

		/*
		 * Clear Button
		 */
		c.gridx = 2;
		addButton("Clear", pane, c);

		c.gridy = 0;
		c.gridx = 6;
		// Set the following elements to be 5 grid blocks tall
		c.gridheight = 5;
		// Set the fill type to fill the grid vertically
		c.fill = GridBagConstraints.VERTICAL;
		// Set the inner padding of the element to 40
		c.ipadx = 40;
		c.insets = new Insets(0, 20, 0, 0);

		// Same process as the other scrollPane except I allow the vertical scrollbar in this pane
		JScrollPane historyScroll = new JScrollPane(this.history);
		historyScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		historyScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		pane.add(historyScroll, c);

		c.gridy = 5;
		c.ipadx = 0; // Reset the inner padding
		addButton("Clear History", pane, c);

	}

	/**
	 * This method adds a button to the given container
	 * 
	 * @param text
	 *            The text to display on the button
	 * @param container
	 *            The container to add the button to
	 * @param c
	 *            The GridBagConstraints that govern positioning of the elements
	 */
	private void addButton(String text, Container container, GridBagConstraints c) {
		// Create a new JButton object called button
		JButton button = new JButton(text); // The text parameter sets the text on the button
		// Add this class as the action listener for this button.
		// This allows this class to handle button clicks
		button.addActionListener(this);
		// Prevent window focus being given to the button
		button.setFocusable(false);
		// Add the button to the provided container
		container.add(button, c);
	}

	/**
	 * This method is a method in the ActionPerformed Interface. <br>
	 * We override it here to allow our class to handle the action performed event the way we want
	 * to. <br>
	 * In this case we need to handle any button in the GUI that is clicked
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) { // Check that the source of the action is a button
			JButton b = (JButton) e.getSource(); // Create a JButton instance
			// Check that the button isn't the enter, backspace, clear, or clear history button.
			if (b.getText() != "Clear" && b.getText() != "Enter" && b.getText() != "Back" && b.getText() != "Clear History") {
				this.input += b.getText(); // Set the input string to what it was plus the text of
											 // the button
				this.textInput.setText(this.input); // Update the text area
			}
			else {
				// The backspace button
				if (b.getText() == "Back") {
					// Check that the input string has a characters
					if (input.length() > 0) {
						// Set input to what it previously was without the last character
						input = input.substring(0, input.length() - 1);
						textInput.setText(input); // Update text area
					}
				}
				// The clear button
				else if (b.getText() == "Clear") {
					input = ""; // Set input to an empty string
					textInput.setText(input); // Update text area
				}
				// Clear history button
				else if (b.getText() == "Clear History") {
					// Set the text to the string "History"
					history.setText("History");

				}
				// Enter key by default
				else {
					// Add the input string to history, eventually it will be the string returned by
					// FancyMath.performCalculation
					history.setText(history.getText() + "\n" + input);
					input = ""; // Set input to empty string
					textInput.setText(input); // Update text area
				}
			}
		}
	}

	/**
	 * The following three methods are overridden from the KeyListener Interface. <br>
	 * They allow us to handle keyboard input as we wish.
	 */
	@Override
	public void keyPressed(KeyEvent e) { // Called when key is pressed
		// Call the handleKey method from my utility class CalcUtils
		CalcUtils.handleKey(e.getKeyCode(), e.getKeyChar(), this);
	}

	@Override
	public void keyReleased(KeyEvent e) { // Called when key is released
	}

	@Override
	public void keyTyped(KeyEvent e) { // Called when a keystroke occurs in a component
	}
}
