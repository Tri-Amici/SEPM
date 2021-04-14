package org.triamici;
/**
 * @author Assignment Group 9
 *
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Storage {
	private LinkedList<User> users; // The user object array
	private int numTickets; // Current number of Tickets
	private LinkedList<Ticket> tickets; // The Ticket object array
	private String fileDir; // location of the csv data files (e.g c:/users/...)
	
	public Storage(boolean runInEclipse) throws IOException { // Main Constructor method
		// Set the location of the CSV files (different for Eclipse)
		if (runInEclipse) {
			fileDir = "./src/org/triamici/";
		} else {
			fileDir = "./org/triamici/";
		}
		
		this.users = new LinkedList<>();
		this.tickets = new LinkedList<>();
		this.loadUserData();				
		this.loadTicketData();
		this.saveUserData();
		this.saveTicketData();
	}

	public List<User> getUsers() {
		return users;
	}
	
	/**
	 * Load the User data from the csv file
	 * @throws IOException 
	 */
	public void loadUserData() throws IOException { // Load data from file
		String[] tempInput;
		try (BufferedReader inFile = new BufferedReader(new FileReader(fileDir + "UserData.csv"))) {
			String currLine = inFile.readLine();
			while (currLine != null && currLine.trim().length() > 0) { // A bit of input validation
				tempInput = currLine.split(","); // Split line into array elements
				this.addUser(new User(tempInput[0], tempInput[1], tempInput[2], tempInput[3], Short.parseShort(tempInput[4])));
				currLine = inFile.readLine();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Add a user to user object array
	 */
	public void addUser(User user) {
		this.users.add(user);
	}
	
	/**
	 * Return the current number of users in the system
	 */
	 public int getNumUsers() {
		 return users.size();
	 }

	 /**
	  * Save the user data to the csv file
	 * @throws IOException 
	  */	 
	public void saveUserData() throws IOException { // Save data to file
		try (BufferedWriter outFile = new BufferedWriter(new FileWriter(fileDir + "UserData.csv")))
		{
			for (User user : this.users)
				outFile.write(user.getName() + "," + user.getEmail() + "," + user.getPhone()
						+ "," + user.getPassword() + "," + user.getLevel()
						+ System.getProperty("line.separator"));
			outFile.flush();
		}
	}

	/**
	 * Load the Ticket data from the csv file and add it to an ticket object array
	 * @throws IOException 
	 * @throws  
	 */
	public void loadTicketData() throws IOException { // Load ticket data from file
		String[] tempInput;
		try (BufferedReader inFile = new BufferedReader(new FileReader(fileDir + "TicketData.csv"))) {
			String currLine = inFile.readLine();
			while (currLine != null && currLine.trim().length() > 0) { // A bit of input validation
				tempInput = currLine.split(","); // Split line into array elements
				this.addTicket(tempInput[0], tempInput[1], tempInput[2], Integer.parseInt(tempInput[3]), tempInput[4],
						Boolean.parseBoolean(tempInput[5]), tempInput[6]);
				currLine = inFile.readLine();
				numTickets++;
			}
		}
	}
	
	/**
	 * Add a ticket to the ticket array
	 */
	public void addTicket(String name, String description, String assignee, int severity, String status,
			boolean resolved, String time) {
		tickets.add(new Ticket(name, description, assignee, severity, status, resolved, time));
	}
	
	/**
	 * Return the current number of Tickets in the system
	 */
	 public int getNumTickets() {
		 return numTickets;
	 }

	 /**
	  * Save the ticket data to the csv file
	 * @throws IOException 
	  */	
	public void saveTicketData() throws IOException { // Save data to file
		try (BufferedWriter outFile = new BufferedWriter(new FileWriter(fileDir + "TicketData.csv"))) {
			for (Ticket ticket: this.tickets)
				outFile.write(ticket.getName() + "," + ticket.getDescription() + ","
						+ ticket.getAssignee() + "," + ticket.getSeverity() + ","
						+ ticket.getStatus() + "," + ticket.getResolved() + ","
						+ ticket.getTime() + System.getProperty("line.separator"));
			outFile.flush();
		} catch (Exception e) {
			// Print the exception
			e.printStackTrace();
		}
	}

}
