package brockstar17.samples.ex2;

import java.awt.event.KeyEvent;

/**
 * This class defines static methods used in my Calculator
 * 
 * @author Brock
 */
public class CalcUtils
{

	/**
	 * Handle keyboard input
	 * 
	 * @param key
	 *            The integer value of the key pressed
	 * @param c
	 *            The actual character pressed
	 * @param cd
	 *            An instance of CalculatorDisplay. <br>
	 *            This allows me to acess the protected fields inside of the class.
	 */
	public static void handleKey(int key, char c, CalculatorDisplay cd) {
		switch (key) { // Simple switch statement
		case KeyEvent.VK_ENTER: // Enter key
			// Same as enter button
			cd.history.setText(cd.history.getText() + "\n" + cd.input);
			cd.input = "";
			cd.textInput.setText(cd.input);
			return;

		case KeyEvent.VK_BACK_SPACE: // Backspace key
			// Same as back button
			if (cd.input.length() > 0) {
				cd.input = cd.input.substring(0, cd.input.length() - 1);
				cd.textInput.setText(cd.input);
			}

			return;

		case KeyEvent.VK_C: // C key
			// Same as clear button
			cd.input = "";
			cd.textInput.setText(cd.input);

		}

		/*
		 * Try catch statements are very useful. They allow us to execute code that may not always
		 * work and sometimes returns a fatal exception. This is because the try block attempts to
		 * execute the code and if it fails, the exception is caught by the catch block. In the
		 * catch block you can then try to fix the error that caused the exception or you can ignore
		 * it.
		 */
		try {
			Integer.parseInt(Character.toString(c)); // Try to parse an int from the character
			// If this code runs then the parse was successful
			cd.input += c; // Add the character to the input string
			cd.textInput.setText(cd.input); // Update text area

			/*
			 * The exception being caught is a NumberFormatException. This exception is caused by
			 * improper assignments to ints or unsuccessful integer parsing
			 */
		} catch (NumberFormatException e) {
			// Check if the char is an operator or the decimal symbol
			if (c == '*' || c == '/' || c == '-' || c == '+' || c == '.') {
				// It was so...
				cd.input += c; // Add the character to the input string
				cd.textInput.setText(cd.input); // Update text field
			}
		}
	}
}
