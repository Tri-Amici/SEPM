/**
 * @author Assignment Group 9
 *
 */
package org.triamici;


import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
	static final short SHORT_FIELD = 10;
	static final short LONG_FIELD = 35;
	static final short MEDIUM_FIELD = 15;
	static final short MEDIUM_LONG_FIELD = 25;
	static final char LINE_CHAR = '-';
	private static int numResolved;
	private static int numUnresolved;
	private static LocalDate startDate = null;
	private static LocalDate endDate = null;
	private static Duration timeTaken = null;
	
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
		if (!userMenu())
			// Display the ticket menu
			ticketMenu();
		
		// Close user input
		userInput.close();
	}
	
	private static boolean userMenu() throws IOException {
		// Exit app boolean
		boolean exitApp = false;
		
		// Generate the log in menu
		Menu menu = new Menu();
		menu.addItem(new MenuItem(1, "Log in", (short)0));
		menu.addItem(new MenuItem(2, "Register as new user", (short)0));
		menu.addItem(new MenuItem(3, "Retrieve password", (short)0));
		menu.addItem(new MenuItem(0, "Exit the app", (short)0));
		
		String menuText = menu.buildMenu((short)0);
		
		// Default selection
		String selection = "";
		
		// Loop until we get valid input
		while (selection.length() == 0 && !exitApp) {
			// Display the menu
			System.out.println(menuText);
			
			// Grab the user's input
			selection = userInput.nextLine();
			
			switch (selection) {
				case "0": // Exit program
					exitApp = true;
					break;
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
					loggedInUser = logInUser();
					break;
				default:
					selection = "";
					break;
			}
		}
		
		return exitApp;
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
		menu.addItem(new MenuItem(7, "Display Report", (short)1));
		menu.addItem(new MenuItem(0, "Exit the app", (short)0));
		
		String menuText = menu.buildMenu(loggedInUser.getLevel());
		
		// Default selection
		String selection = "";
		
		// Loop until we get valid input
		while (!exitApp) {
			// Display the menu
			System.out.println(menuText);
			
			// Grab the user's input
			selection = userInput.nextLine();
			
			// If we have a insecure option or we're a technician
			if (Validation.validShortRange((short)0, (short)2, Short.parseShort(selection)) || loggedInUser.getLevel() > 0) {
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
								.filter(t -> t.getCreator().equalsIgnoreCase(loggedInUser.getEmail()) && !t.getClosed())
								.collect(Collectors.toList())
								);
						break;
					case "3": // View Assigned Open Tickets
						displayTickets(
								storage.getTickets()
								.stream()
								.filter(t -> t.getAssignee().equalsIgnoreCase(loggedInUser.getEmail()) && !t.getClosed())
								.collect(Collectors.toList())
								);
						break;
					case "4": // Change Ticket Severity
						// Get the open tickets
						List<Ticket> openTickets1 = storage.getTickets()
									.stream()
									.filter(t -> !t.getClosed())
									.collect(Collectors.toList());
						
						// Display the open tickets
						displayTickets(openTickets1);
						
						// Prompt for the severity
						changeSeverity(openTickets1);

						break;
					case "5": // Change Ticket Status
						// Get the open tickets
						List<Ticket> openTickets2 = storage.getTickets()
									.stream()
									.filter(t -> !t.getClosed() || t.getClosed() && Duration.between(t.getClosedTime(), LocalDateTime.now()).toMinutes() < 1440)
									.collect(Collectors.toList());
						
						// Display the open tickets
						displayTickets(openTickets2);
						
						// Prompt for status
						changeStatus(openTickets2);
						break;
					case "6": // View All Closed & Archived Tickets
						displayTickets(
								storage.getTickets()
								.stream()
								.filter(Ticket::getClosed)
								.collect(Collectors.toList())
								);
						break;
					case "7": // Display Report
						displayReport(
								storage.getTickets()
								.stream()
								);	
								break;
					default:
						break;
				}
			}
		}
	}
	
	private static void changeStatus(List<Ticket> tickets) throws IOException {
		String[] ticketID = new String[] {""};
		String status = "";
		String resolved = "";
		
		// Loop through until the ticket ID is not blank
		while (ticketID[0].equals("")) {
			// Prompt for the ticket ID
			System.out.println("Please enter a ticket ID");
			ticketID[0] = userInput.nextLine();
			
			// Check that it is a valid integer and that the ID exists in the tickets
			if (!Validation.validInteger(ticketID[0]) || 
					tickets.stream().filter(t -> t.getId() == Integer.parseInt(ticketID[0])).count() == 0) {
				ticketID[0] = "";
			}
		}
		
		// Prompt for the ticket status from the user
		while (status.equals("")) {
			System.out.println("Enter a ticket status\n0 - Closed\n1 - Open");
			status = userInput.nextLine().trim();
			
			if (!Validation.validInteger(status) ||
					!Validation.validShortRange((short)0, (short)1, Short.parseShort(status))) {
				status = "";
			}
		}
		
		// Prompt for the ticket resolution from the user
		if (status.equals("0"))
			while (resolved.equals("")) {
				System.out.println("Was the issue resolved\n0 - Not resolved\n1 - Resolved");
				resolved = userInput.nextLine().trim();
				
				if (!Validation.validInteger(resolved) ||
						!Validation.validShortRange((short)0, (short)1, Short.parseShort(resolved))) {
					resolved = "";
				}
			}
		
		// Grab the ticket
		Optional<Ticket> ticketToUpdate = tickets
				.stream()
				.filter(t -> t.getId() == Integer.parseInt(ticketID[0]))
				.findFirst();
		
		// Update the ticket
		if (ticketToUpdate.isPresent()) {
			ticketToUpdate.get().setClosed(status.equals("0"));
			ticketToUpdate.get().setResolved(resolved.equals("1"));
			ticketToUpdate.get().setCloseTime(status.equals("0") ?  LocalDateTime.now() : LocalDateTime.MIN);
			storage.saveTicketData();
		}
	}
	
	private static void changeSeverity(List<Ticket> tickets) throws IOException {
		String[] ticketID = new String[] {""};
		String severity = "";
		String assignee = "";
		
		// Loop through until the ticket ID is not blank
		while (ticketID[0].equals("")) {
			// Prompt for the ticket ID
			System.out.println("Please enter a ticket ID");
			ticketID[0] = userInput.nextLine();
			
			// Check that it is a valid integer and that the ID exists in the tickets
			if (!Validation.validInteger(ticketID[0]) || 
					tickets.stream().filter(t -> t.getId() == Integer.parseInt(ticketID[0])).count() == 0) {
				ticketID[0] = "";
			}
		}
		
		// Prompt for the ticket severity from the user
		while (severity.equals("")) {
			System.out.println("Enter a ticket severity\n0 - Low priority\n1 - Medium priority\n2 - High priority");
			severity = userInput.nextLine().trim();
			
			if (!Validation.validInteger(severity) ||
					!Validation.validShortRange((short)0, (short)2, Short.parseShort(severity))) {
				severity = "";
			}
		}
		
		// Get the new assignee
		assignee = getAssignee(Short.parseShort(severity));
		
		// Grab the ticket
		Optional<Ticket> ticketToUpdate = tickets
				.stream()
				.filter(t -> t.getId() == Integer.parseInt(ticketID[0]))
				.findFirst();
		
		// Update the ticket
		if (ticketToUpdate.isPresent()) {
			ticketToUpdate.get().setAssignee(assignee);
			ticketToUpdate.get().setSeverity(Short.parseShort(severity));
			storage.saveTicketData();
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
		
		String assignee = getAssignee(Short.parseShort(severity));
		
		// Add the ticket
		storage.addTicket(
				new Ticket(
					0,
					loggedInUser.getEmail(),
					description,
					assignee,
					Short.parseShort(severity),
					false,
					false,
					LocalDateTime.now(),
					LocalDateTime.MIN
					)
				);
		
		// Save the data
		storage.saveTicketData();
		
		// Success message
		System.out.println("Ticket logged!");
	}
	
	private static String getAssignee(short severity) {
		// Get the ticket lists
		LinkedList<TicketAssignment> ticketAssignments = new LinkedList<>();
		final short neededTechnician = (short) (severity != 2 ? 1 : 2);
		
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
									.filter(t -> t.getAssignee().equals(u) && !t.getClosed())
									.collect(Collectors.counting()))
						)
			);
			
		// Get the technician with the least amount of tickets
		Optional<TicketAssignment> assignee = ticketAssignments
				.stream()
				.sorted(Comparator.comparing(TicketAssignment::getCount))
				.findAny();
		
		if (assignee.isPresent())
			return assignee.get().getAssignee();
		else
			return "";
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
	
	private static void displayTickets(List<Ticket> tickets) {
	
		// Display the headers
		System.out.print(String.format("%-" + SHORT_FIELD + "s", "ID"));
		System.out.print(String.format("%-" + MEDIUM_LONG_FIELD + "s", "Creator"));
		System.out.print(String.format("%-" + MEDIUM_LONG_FIELD + "s", "Assignee"));
		System.out.print(String.format("%-" + MEDIUM_FIELD + "s", "Severity"));
		System.out.print(String.format("%-" + MEDIUM_FIELD + "s", "Resolution"));
		System.out.print(String.format("%-" + MEDIUM_LONG_FIELD + "s", "Status"));
		System.out.print(String.format("%-" + LONG_FIELD + "s", "Description"));
		System.out.println();
		
		System.out.print(repeat(LINE_CHAR, SHORT_FIELD - 1) + " ");
		System.out.print(repeat(LINE_CHAR, MEDIUM_LONG_FIELD - 1) + " ");
		System.out.print(repeat(LINE_CHAR, MEDIUM_LONG_FIELD - 1) + " ");
		System.out.print(repeat(LINE_CHAR, MEDIUM_FIELD - 1) + " ");
		System.out.print(repeat(LINE_CHAR, MEDIUM_FIELD - 1) + " ");
		System.out.print(repeat(LINE_CHAR, MEDIUM_LONG_FIELD - 1) + " ");
		System.out.print(repeat(LINE_CHAR, LONG_FIELD - 1));
		System.out.println();
		
		// Loop through the tickets
		tickets.forEach(t -> {
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
			System.out.print(String.format("%-" + SHORT_FIELD + "s", t.getId()));
			System.out.print(String.format("%-" + MEDIUM_LONG_FIELD + "s", creator.isPresent() ? creator.get().getName() : "NA"));
			System.out.print(String.format("%-" + MEDIUM_LONG_FIELD + "s", assignee.isPresent() ? assignee.get().getName() : "NA"));
			System.out.print(String.format("%-" + MEDIUM_FIELD + "s", (new String[] {"Low", "Medium", "High"})[t.getSeverity()]));
			System.out.print(String.format("%-" + MEDIUM_FIELD + "s", t.getResolved() ? "Resolved" : "Unresolved"));
			System.out.print(String.format("%-" + MEDIUM_LONG_FIELD + "s", 
					(t.getClosed() ? "Closed" : "Open") + 
					(Duration.between(t.getClosedTime(), LocalDateTime.now()).toMinutes() >= 1440 && t.getClosed() ? " - ARCHIVED" : "")));
			System.out.print(t.getDescription());
			System.out.println();
		});
	}
	
	
private static void displayReport(Stream<Ticket> stream) {
		//Enter date range for report.
		while(startDate == null || endDate == null ) {
			 System.out.println("Enter the start date for the range in the "
					+ "format: YYYY-MM-DD");
			 
			 try {	
				 startDate = LocalDate.parse(userInput.nextLine());
				 System.out.println("Enter the end date for the range");
				 endDate = LocalDate.parse(userInput.nextLine().trim());
			 }
				 catch(DateTimeParseException e) {
					 System.out.println("Error! Please enter the dates in format: YYYY-MM-DD");
			 }
		 }
			
		//Print the report heading
		System.out.println(String.format("%-" + SHORT_FIELD + "s", 
				"Showing Ticket Report for Dates: " + startDate.toString() 
				+ " to " + endDate ));
		System.out.println(repeat(LINE_CHAR, LONG_FIELD));
		System.out.print(String.format("%-" +  MEDIUM_FIELD + "s", "Creator"));
		System.out.print(String.format("%-" +  MEDIUM_LONG_FIELD + "s", "Assignee"));
		System.out.print(String.format("%-" + MEDIUM_LONG_FIELD + "s", "Time Created"));
		System.out.print(String.format("%-" +  SHORT_FIELD + "s", "Resolved"));
		System.out.print(String.format("%-" +  SHORT_FIELD + "s", "Severity"));
		System.out.println(String.format("%-" +  MEDIUM_FIELD + "s", "Time Taken"));
		
		
		System.out.print(repeat(LINE_CHAR, MEDIUM_FIELD -1) + " ");
		System.out.print(repeat(LINE_CHAR, MEDIUM_LONG_FIELD -1) + " ");
		System.out.print(repeat(LINE_CHAR, MEDIUM_LONG_FIELD -1) + " ");
		System.out.print(repeat(LINE_CHAR, SHORT_FIELD -1) + " ");
		System.out.print(repeat(LINE_CHAR, SHORT_FIELD -1) + " ");
		System.out.println(repeat(LINE_CHAR, MEDIUM_FIELD -1) + " ");
		
		stream.forEach(t -> {
			
			//Retrieve the creator
			Optional <User>creator = storage.getUsers()
					.stream()
					.filter(u -> u.getEmail().equalsIgnoreCase(t.getCreator()))
					.findFirst();
			
			//Retrieve the assignee
			
			Optional <User>assignee = storage.getUsers()
					.stream()
					.filter(u -> u.getEmail().equalsIgnoreCase(t.getAssignee()))
					.findFirst();

			//Resolved tickets
			if( t.getOpenedTime().toLocalDate().isAfter(startDate) &&
					 t.getOpenedTime().toLocalDate().isBefore(endDate) ) {
				System.out.print(String.format("%-" + MEDIUM_FIELD + "s", creator.get().getName() ));
				System.out.print(String.format("%-" + (MEDIUM_LONG_FIELD) + "s", assignee.get().getName() ));
				System.out.print(String.format("%-" + (MEDIUM_LONG_FIELD) + "s", t.getOpenedTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a"))));
				System.out.print(String.format("%-" + SHORT_FIELD + "s",  t.getResolved() ? "Yes" : "No"));
				System.out.print(String.format("%-" + SHORT_FIELD + "s", 
						(new String[] {"Low", "Medium", "High"}) [t.getSeverity()] ));
				
				if (t.getResolved()) {
					timeTaken = Duration.between(t.getOpenedTime(), t.getClosedTime());
					System.out.println(formatDuration(timeTaken));
				} else {
					System.out.println();
				}
					
				if(t.getResolved())
					numResolved++;
				else
					numUnresolved++;
			}
			
			
		});
		System.out.println();
		/* Display resolved and unresolved tickets apart
		 * or together?
		 */
		if(numResolved == 0 && numUnresolved == 0) 
			System.out.println("No tickets to show for the given date range.");
		else
			System.out.println(
					"Total Tickets: " + (numResolved + numUnresolved) +
					" - Resolved Tickets: " + numResolved +
					" - Unresolved Tickets: " + numUnresolved);
		
		startDate = null;
		endDate = null;
			
	}
	
	private static String repeat(char lineChar, int number) {
		StringBuilder bld = new StringBuilder();
		
		for (short i = 0; i < number; i++)
			bld.append(lineChar);
		
		return bld.toString();
	}
	
	private static String formatDuration(Duration duration) {
		
		
		
		long seconds = duration.getSeconds();
		long absSeconds;
		long daysElapsed = (seconds / 86400);
		absSeconds = Math.abs(seconds % 86400);
		return String.format(
				"%dD:%dH:%02dM",
				daysElapsed,
				absSeconds / 3600,
				(absSeconds % 3600) / 60);
	}
}
