package brockstar17;

import brockstar17.sorting.Mergesort;

/**
 * This class contains the project's main method.<br>
 * This is the starting point of the program. I will use it to run the example programs that I
 * create.
 * 
 * @author Brock
 */
public class DemoMain
{

	/**
	 * This is the main method of the program. Every Java Application has to have one.<br>
	 * This is what the compiler looks for to start the program.
	 * 
	 * @param args
	 *            is a String[] that is used as the parameters of the main method.<br>
	 *            This array is very useful when running a program from the command line, and can be
	 *            used to give the program intial values on startup.
	 */
	public static void main(String[] args) {

		int x = 25;

		int[] fancyIntArray = new int[x];
		for (int i = 0; i < fancyIntArray.length; i++) {
			fancyIntArray[i] = (int) (Math.random() * 100);
		}

		System.out.println("Before Mergesort");

		for (int i : fancyIntArray) {
			System.out.print(i + " ");
		}

		fancyIntArray = Mergesort.mergesort(fancyIntArray);

		System.out.println("");
		System.out.println("After Mergesort");

		for (int i : fancyIntArray) {
			System.out.print(i + " ");
		}
	}

}
