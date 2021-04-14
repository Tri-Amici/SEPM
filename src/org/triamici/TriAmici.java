/**
 * @author Assignment Group 9
 *
 */
package org.triamici;

import java.io.IOException;
import java.util.Scanner;

public class TriAmici {

	static User loggedInUser;
	static Storage storage;
	static Scanner userInput;
	
	/**
	 * Main application method
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// Run in Eclipse default
		boolean runInEclipse = false;
		boolean runningInCI = false;
		userInput = new Scanner( System.in );
		
		// Check if we're running in Eclipse
		for(int i = 0; i < args.length; i++)
			if (args[0].contains("-runInEclipse"))
				runInEclipse = true;
			else if (args[0].contains("-runInCI")) {
				System.out.println("CI: running smoke test and quitting...");	
				runningInCI = true;
			}
		
		// Create a new instance of the Storage class
		storage = new Storage(runInEclipse);
		
		// Quit if we're running in CI
		if (runningInCI) {
			System.exit(0);
		}
		
		// Display the menu
		userMenu();
		
		// Close user input
		userInput.close();
	}
	
	private static void userMenu() throws IOException {
		// Generate the log in menu
		Menu menu = new Menu();
		menu.addItem(new MenuItem(1, "Log in", (short)0));
		menu.addItem(new MenuItem(2, "Register as new user", (short)0));
		
		String menuText = menu.buildMenu((short)0);
		
		// Default selection
		String selection = "";
		
		// User's input
		String email = "";
		String name = "";
		String phone = "";
		String password = "";
		
		// Loop until we get valid input
		while (selection.length() == 0) {
			// Display the menu
			System.out.println(menuText);
			
			// Grab the user's input
			selection = userInput.nextLine();
			
			switch (selection) {
				case "1":
					System.out.println("Not yet commissioned");
					break;
				case "2":
					// Get the user's email
					email = userEmail();
					name = userName();
					phone = userPhone();
					password = userPassword();
					
					// Set the logged in user
					loggedInUser = new User(name, email, phone, password, (short)0);
					
					// Add the user the the data store
					storage.addUser(loggedInUser);
					storage.saveUserData();
					break;
				default:
					selection = "";
			}
		}
	}
	
	private static String userEmail() {
		// Get the user's email
		String email = "";
		
		while (email.length() == 0) {
			System.out.println("Enter your email address");
			email = userInput.nextLine().trim();
			
			// Validate the email and set to blank if not valid
			if (!Validation.validEmail(email)) {
				System.out.println("You need to enter a valid email");
				email = "";
			} else {
				// Loop through the users
				for (User user : storage.getUsers()) {
					// If the user is already in the system
					if (user.getEmail().equalsIgnoreCase(email)) {
						// Throw an error
						System.out.println("You are already in the system");
						email = "";
						break;
					}
				}
			}
		}
		
		// Return the email
		return email;
	}
	
	private static String userName() {
		// Get the user's name
		String name = "";
		
		while (name.length() == 0) {
			System.out.println("Enter your name");
			name = userInput.nextLine().trim();
		}
		
		// Return the name
		return name;
	}
	
	private static String userPhone() {
		// Get the user's phone
		String phone = "";
		
		while (phone.length() == 0) {
			System.out.println("Enter your phone number");
			phone = userInput.nextLine().trim();
			
			// Validate the email and set to blank if not valid
			if (!Validation.validPhone(phone)) {
				System.out.println("You need to enter a valid phone number");
				phone = "";
			}
		}
		
		// Return the user's phone number
		return phone;
	}
	
	private static String userPassword() {
		// Get the user's password
		String password = "";
		
		while (password.length() == 0) {
			System.out.println("Enter your password");
			System.out.println("- At least one uppercase, lowercase and number\n"
					+ "- No special characters\n"
					+ "- 8 characters in length)");
			password = userInput.nextLine().trim();
			
			// Validate the password and set to blank if not valid
			if (!Validation.validPassword(password)) {
				System.out.println("You need to enter a valid password");
				password = "";
			}
		}
		
		// Return the user's phone number
		return password;
	}
}
