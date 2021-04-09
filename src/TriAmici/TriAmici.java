/**
 * @author Assignment Group 9
 *
 */
package TriAmici;

public class TriAmici {

	/**
	 * Main application method
	 */
	public static void main(String[] args) {
		@SuppressWarnings("unused")
		
		// Run in Eclipse default
		boolean runInEclipse = false;
		
		// Check if we're running in Eclipse
		for(int i = 0; i < args.length; i++)
			if (args[0].contains("-runInEclipse"))
				runInEclipse = true;
		
		// Create a new instance of the Storage class
		Storage test = new Storage(runInEclipse);
		
		System.out.println("Working!");
	}

}
