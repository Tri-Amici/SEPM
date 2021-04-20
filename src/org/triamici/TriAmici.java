/**
 * @author Assignment Group 9
 *
 */
package org.triamici;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
		menu.addItem(new MenuItem(3, "Retrieve password", (short)0));
		
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
				case "3":
					retrievePassword();
					break;
				default:
					selection = "";
			}
		}
	}
	
	private static void ticketMenu() throws IOException {
		// Exit app boolean
		boolean exitApp = false;
		
		// Generate the log in menu
		Menu menu = new Menu();
		menu.addItem(new MenuItem(1, "Log a ticket", (short)0));
		menu.addItem(new MenuItem(2, "View Submitted Open Tickets", (short)0));
		menu.addItem(new MenuItem(3, "View Assigned Open Tickets", (short)1));
		menu.addItem(new MenuItem(4, "Change Ticket Severity", (short)1));
		menu.addItem(new MenuItem(5, "Change Ticket Status", (short)1));
		menu.addItem(new MenuItem(6, "View All Closed & Archived Tickets", (short)1));
		menu.addItem(new MenuItem(0, "Exit the app", (short)0));
		
		String menuText = menu.buildMenu((short)0);
		
		// Default selection
		String selection = "";
		
		// Loop until we get valid input
		while (!exitApp) {
			// Display the menu
			System.out.println(menuText);
			
			// Grab the user's input
			selection = userInput.nextLine();
			
			// If we have a insecure option or we're a technician
			if (selection.equals("1") || selection.equals("2") || loggedInUser.getLevel() > 0) {
				switch (selection) {
					case "0": // Exit program
						exitApp = true;
						break;
					case "1": // Log a ticket
						logTicket();
						break;
					case "2": // View Submitted Open Tickets
						displayTickets(
								storage.getTickets()
								.stream()
								.filter(t -> t.getCreator().equalsIgnoreCase(loggedInUser.getEmail()) && !t.getResolved())
								);
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
						break;
				}
			}
		}
	}
	
	private static void logTicket() throws IOException {
		
		String description = "";
		String severity = "";
		
		// Prompt for the ticket description from the user
		while (description.length() == 0) {
			System.out.println("Enter a ticket description");
			description = userInput.nextLine().trim();
		}
		
		// Prompt for the ticket severity from the user
		while (severity.equals("")) {
			System.out.println("Enter a ticket severity\n0 - Low priority\n1 - Medium priority\n2 = High priority");
			severity = userInput.nextLine().trim();
			
			if (!Validation.validInteger(severity) ||
					!Validation.validShortRange((short)0, (short)2, Short.parseShort(severity))) {
				severity = "";
			}
		}
		
		// Get the ticket lists
		LinkedList<TicketAssignment> ticketAssignments = new LinkedList<>();
		final short neededTechnician = (short) (Short.parseShort(severity) != (short)2 ? 1 : 2);
		
		// Get the technician email addresses
		List<String> user = storage.getUsers()
				.stream()
				.filter(u -> u.getLevel() == neededTechnician)
				.map(User::getEmail)
				.collect(Collectors.toList());
		
		// Loop through the tickets and get each technician's ticket count
		user
			.stream()
			.forEach(u -> 
				ticketAssignments.add(
						new TicketAssignment(u,
								storage.getTickets()
									.stream()
									.filter(t -> t.getAssignee().equals(u))
									.collect(Collectors.counting()))
						)
			);
			
		// Get the technician with the least amount of tickets
		Optional<TicketAssignment> assignee = ticketAssignments
				.stream()
				.sorted(Comparator.comparing(TicketAssignment::getCount))
				.findFirst();
		
		
		// Log a new instance of a ticket
		if (assignee.isPresent()) {
			// Add the ticket
			storage.addTicket(
					new Ticket(
						0,
						loggedInUser.getEmail(),
						description,
						assignee.get().getAssignee(),
						Short.parseShort(severity),
						(short)0,
						false,
						LocalDateTime.now()
						)
					);
			
			// Save the data
			storage.saveTicketData();
			
			// Success message
			System.out.println("Ticket logged!");
		} else {
			// Failure message
			System.out.println("No technicians in the system");
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
	
	private static void retrievePassword() {
		// Prompt for the email from the user
		String email = "";
		boolean emailMatch = false;
		
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
						emailMatch = true;
						System.out.println("User found, Your password is: " + user.getPassword());						
						break;
					}
				}
				if (!emailMatch)
						System.out.println("Sorry, the email address was not found");
			}
		}
	}
	
	private static void displayTickets(Stream<Ticket> stream) {
		final short shortField = 10;
		final short longField = 35;
		final short mediumField = 35;
		final String lineChar = "-";
		
		// Display the headers
		System.out.print(String.format("%-" + shortField + "s", "ID"));
		System.out.print(String.format("%-" + longField + "s", "Creator"));
		System.out.print(String.format("%-" + longField + "s", "Assignee"));
		System.out.print(String.format("%-" + mediumField + "s", "Severity"));
		System.out.print(String.format("%-" + longField + "s", "Description"));
		System.out.println();
		
		System.out.print(lineChar.repeat(shortField - 1) + " ");
		System.out.print(lineChar.repeat(longField - 1) + " ");
		System.out.print(lineChar.repeat(longField - 1) + " ");
		System.out.print(lineChar.repeat(mediumField - 1) + " ");
		System.out.print(lineChar.repeat(longField - 1));
		System.out.println();
		
		// Loop through the tickets
		stream.forEach(t -> {
			// Retrieve the creator
			Optional<User> creator = storage.getUsers()
					.stream()
					.filter(u -> u.getEmail().equalsIgnoreCase(t.getCreator()))
					.findFirst();
			
			// Retrieve the assignee
			Optional<User> assignee = storage.getUsers()
					.stream()
					.filter(u -> u.getEmail().equalsIgnoreCase(t.getAssignee()))
					.findFirst();
			
			// Display the ticket ID
			System.out.print(String.format("%-" + shortField + "s", t.getId()));
			System.out.print(String.format("%-" + longField + "s", creator.isPresent() ? creator.get().getName() : "NA"));
			System.out.print(String.format("%-" + longField + "s", assignee.isPresent() ? assignee.get().getName() : "NA"));
			System.out.print(String.format("%-" + mediumField + "s", (new String[] {"Low", "Medium", "High"})[t.getSeverity()]));
			System.out.print(t.getDescription());
			System.out.println();
		});
	}
}
