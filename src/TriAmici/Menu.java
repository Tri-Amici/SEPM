package TriAmici;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Menu {

	// Constant divider
	final String divider = "-------------------------";
	
	// List of menu items
	List<MenuItem> menuItems;
	
	// Initialize the list of menu items
	public Menu() {
		menuItems = new LinkedList<MenuItem>();
	}
	
	// Add a menu item
	public void addItem(MenuItem item) {
		this.menuItems.add(item);
	}
	
	// Remove a menu item
	public void removeItem(MenuItem item) {
		this.menuItems.remove(item);
	}
	
	// Build the menu without access level
	public String buildMenu() {
		// Return the menu with a defaul menu level of 0
		return buildMenu((short)0);
	}
	
	// Build the menu with access level
	public String buildMenu(short accessLevel) {
		
		// Start the menu with a divider
		String returnValue = divider + '\n';
		
		// Sort the menu items
		Collections.sort(menuItems);
		
		// List the menu items
		for(MenuItem menuItem : this.menuItems) {
			// Add the menu item if the access level is greater than or equal to the menu item menu level
			if (accessLevel >= menuItem.getAccessLevel())
				returnValue += menuItem.getItemNumber() + ". " + menuItem.getMenuText() + '\n';
		}
		
		// End with a divider
		returnValue += divider;
		
		// Return the menu
		return returnValue;
	}
	
}