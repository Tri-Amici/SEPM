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
		// Run in Eclipse default
		boolean runInEclipse = false;
		
		// Check if we're running in Eclipse
		for(int i = 0; i < args.length; i++)
			if (args[0].contains("-runInEclipse"))
				runInEclipse = true;
		
		// Create a new instance of the Storage class
		Storage test = new Storage(runInEclipse);
		
		// Generate a sample menu
		Menu menu = new Menu();
		menu.addItem(new MenuItem(1, "Menu item A", (short)0));
		menu.addItem(new MenuItem(2, "Menu item B", (short)0));
		menu.addItem(new MenuItem(3, "Menu item C", (short)1));
		menu.addItem(new MenuItem(4, "Menu item D", (short)2));
		
		// Display the menu
		System.out.println(menu.buildMenu((short)1));
	}

}
