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
	static final String NOT_DONE = "Not yet commissioned...";
	
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
		
		// Display the user login/signup menu
		userMenu();
		
		// Display the ticket menu
		ticketMenu();
		
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
		
		// Loop until we get valid input
		while (selection.length() == 0) {
			// Display the menu
			System.out.println(menuText);
			
			// Grab the user's input
			selection = userInput.nextLine();
			
			switch (selection) {
				case "1":
					loggedInUser = logInUser();
					break;
				case "2":
					// User's input
					String email = "";
					String name = "";
					String phone = "";
					String password = "";
					
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
	
	private static void ticketMenu() {
		// Generate the log in menu
		Menu menu = new Menu();
		menu.addItem(new MenuItem(1, "Log a ticket", (short)0));
		menu.addItem(new MenuItem(2, "View Submitted Open Tickets", (short)0));
		menu.addItem(new MenuItem(3, "View Assigned Open Tickets", (short)1));
		menu.addItem(new MenuItem(4, "Change Ticket Severity", (short)1));
		menu.addItem(new MenuItem(5, "Change Ticket Status", (short)1));
		menu.addItem(new MenuItem(6, "View All Closed & Archived Tickets", (short)1));
		
		String menuText = menu.buildMenu((short)0);
		
		// Default selection
		String selection = "";
		
		// Loop until we get valid input
		while (selection.length() == 0) {
			// Display the menu
			System.out.println(menuText);
			
			// Grab the user's input
			selection = userInput.nextLine();
			
			// If we have a insecure option or we're a technician
			if (selection.equals("1") || selection.equals("2") || loggedInUser.getLevel() > 0) {
				switch (selection) {
					case "1": // Log a ticket
						System.out.println(NOT_DONE);
						break;
					case "2": // View Submitted Open Tickets
						System.out.println(NOT_DONE);
						break;
					case "3": // View Assigned Open Tickets
						System.out.println(NOT_DONE);
						break;
					case "4": // Change Ticket Severity
						System.out.println(NOT_DONE);
						break;
					case "5": // Change Ticket Status
						System.out.println(NOT_DONE);
						break;
					case "6": // View All Closed & Archived Tickets
						System.out.println(NOT_DONE);
						break;
					default:
						selection = "";
						break;
				}
			} else {
				// Invalid selection, reset
				selection = "";
			}
		}
	}
	
	private static User logInUser() {
		// User's input
		String email = "";
		String password = "";
		User currentUser = null;
		
		while(loggedInUser == null)  {
			// Prompt for the email from the user
			System.out.println("Enter your email address to log in");
			email = userInput.nextLine().trim();
			
			//Search for the user in the database by email
			for(User user: storage.getUsers() ) {
				if(email.equalsIgnoreCase(user.getEmail()) ) 
					currentUser = user;	
				
			}
			
			//If the user couldn't be found, start again 
			if(currentUser == null)
				System.out.println("User not found! Please try " +
						"again or register a new user.");
			
			else {
				//Prompt for the password
				System.out.println("Enter your password.");
				password = userInput.nextLine().trim();
				//Check that the password matches.
				if(password.equals(currentUser.getPassword())) {
					
					//If it matches, set the user to be logged in
					System.out.println("Successfully logged in user " +
					currentUser.getName() + ".");
					loggedInUser = currentUser;
					break;
				}
				else
					System.out.println("Password doesn't match!" +
				" Please try again or reset your password.");
				
			}	
		}
		
		return currentUser;
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
