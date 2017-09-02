package brockstar17;

import brockstar17.samples.Example1;

/**
 * This class contains the project's main method.<br>
 * This is the starting point of the program. I will use it to run the example programs that I create.
 * @author Brock
 *
 */
public class DemoMain {

	/**
	 * This is the main method of the program. Every Java Application has to have one.<br>
	 * This is what the compiler looks for to start the program.
	 * 
	 * @param args is a String[] that is used as the parameters of the main method.<br>
	 * This array is very useful when running a program from the command line, and can be
	 * used to give the program intial values on startup.
	 * 
	 */
	public static void main(String[] args){
		//System.out.println("Run"); DEBUG
		new Example1(); // Create a new instance of Example1, this will effectively open the Example1 GUI
	}

}
