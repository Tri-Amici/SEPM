/**
 * @author Assignment Group 9
 *
 */
package TriAmici;

import java.io.IOException;
import java.util.Scanner;

public class TriAmici {

	static User loggedInUser;
	
	/**
	 * Main application method
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// Run in Eclipse default
		boolean runInEclipse = false;
		Scanner userInput = new Scanner( System.in );
		
		// Check if we're running in Eclipse
		for(int i = 0; i < args.length; i++)
			if (args[0].contains("-runInEclipse"))
				runInEclipse = true;
		
		// Create a new instance of the Storage class
		Storage test = new Storage(runInEclipse);
		
		// Generate the log in menu
		Menu menu = new Menu();
		menu.addItem(new MenuItem(1, "Log in", (short)0));
		menu.addItem(new MenuItem(2, "Register as new user", (short)0));
		
		// 
		String selection = "";
		
		// Loop until we get valid input
		while (selection == "") {
			// Display the menu
			System.out.println(menu.buildMenu((short)0));
			
			// Grab the user's input
			selection = userInput.nextLine();
			
			switch (selection) {
				case "1":
					System.out.println("Not yet commissioned");
					break;
				case "2":
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
							for (User user : test.getUsers()) {
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
					
					// Get the user's name
					String name = "";
					
					while (name.length() == 0) {
						System.out.println("Enter your name");
						name = userInput.nextLine().trim();
					}
					
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
					
					// Get the user's password
					String password = "";
					
					while (password.length() == 0) {
						System.out.println("Enter your password");
						System.out.println("- At least one uppercase\n"
								+ "- Lowercase and number\n"
								+ "- No special characters\n"
								+ "- 8 characters in length)");
						password = userInput.nextLine().trim();
						
						// Validate the password and set to blank if not valid
						if (!Validation.validPassword(password)) {
							System.out.println("You need to enter a valid password");
							password = "";
						}
					}
					
					loggedInUser = new User(name, email, phone, password, (short)0);
					
					test.addUser(loggedInUser);
					test.saveUserData();
					break;
				default:
					selection = "";
			}
		}
		
		// Close user input
		userInput.close();
	}
}
