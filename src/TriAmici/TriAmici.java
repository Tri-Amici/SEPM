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
		MenuItem menuLogin = new MenuItem(1, "Log in", (short)0);
		MenuItem menuRegister = new MenuItem(2, "Register as new user", (short)0);
		menu.addItem(menuLogin);
		menu.addItem(menuRegister);
		
		
		// Default selection
		String selection = "";
		
		// Loop until we get valid input
		while (selection == "") {
			// Display the menu
			System.out.println(menu.buildMenu((short)0));
			
			// Grab the user's input
			selection = userInput.nextLine();
			//Moved these declarations up here for use with the login system
			String email = "";
			String password = "";
			User currentUser = null;
			
			switch (selection) {
				case "1":
					
					while(loggedInUser == null)  {
						// Prompt for the email from the user
						System.out.println("Enter your email address to log in");
						email = userInput.nextLine().trim();
						
						//Search for the user in the database by email
						for(User user: test.getUsers() ) {
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
							
					break;
				case "2":
					// Get the user's email
					
					
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
					
					
					while (password.length() == 0) {
						System.out.println("Enter your password");
						System.out.println("- At least one uppercase\n"
								+ "- Lowercase and number\n"
								+ "- No special characters\n"
								+ "- 8 characters in length");
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
		
		//Remove the old items from the menu now that the login process is complete
		menu.removeItem(menuLogin);
		menu.removeItem(menuRegister);
		
		//Generate a new menu here to test access levels
		menu.addItem(new MenuItem(1,"This is a level zero item", (short)0));
		menu.addItem(new MenuItem(2,"This is a level one item", (short)1));
		menu.addItem(new MenuItem(3,"This is a level two item", (short)2));
		System.out.println(menu.buildMenu(loggedInUser.getLevel()));
		
		
		
		

		
	}
}
