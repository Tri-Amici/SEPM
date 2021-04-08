package TriAmici;
/**
 * @author Assignment Group 9
 *
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class Storage {
	private int numUsers; // Current number of Users
	private User[] users; // The user object array
	private int numTickets; // Current number of Tickets
	private Ticket[] tickets; // The Ticket object array
	private String fileDir = "./"; // location of the csv data files (e.g c:/users/...)

	public Storage() { // Main Constructor method
		this.numUsers = 0;
		this.users = new User[20];
		this.numTickets = 0;
		this.tickets = new Ticket[20];
		this.loadUserData();				
		this.loadTicketData();
		this.saveUserData();
		this.saveTicketData();
	}

	/**
	 * Load the User data from the csv file
	 */
	public void loadUserData() { // Load data from file
		BufferedReader inFile = null;
		String[] tempInput;
		try {
			inFile = new BufferedReader(new FileReader(fileDir + "UserData.csv"));
			String currLine = inFile.readLine();
			while (currLine != null && currLine.trim().length() > 0) { // A bit of input validation
				tempInput = currLine.split(","); // Split line into array elements
				this.addUser(tempInput[0], tempInput[1], tempInput[2], tempInput[3], tempInput[4]);
				currLine = inFile.readLine();
				numUsers++;
			}
			inFile.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Add a user to user object array
	 */
	public void addUser(String name, String email, String phone, String password, String level) {
		this.users[numUsers] = new User(name, email, phone, password, level);
//		System.out.print(this.users[numUsers].getName());
//		System.out.print(this.users[numUsers].getEmail());
//		System.out.print(this.users[numUsers].getPhone());
//		System.out.print(this.users[numUsers].getPassword());
//		System.out.print(this.users[numUsers].getLevel());
//		System.out.println();
	}
	
	/**
	 * Return the current number of users in the system
	 */
	 public int getNumUsers() {
		 return numUsers;
	 }

	 /**
	  * Save the user data to the csv file
	  */	 
	public void saveUserData() { // Save data to file
		BufferedWriter outFile = null;
		try {
			outFile = new BufferedWriter(new FileWriter("./" + "UserData.csv"));
			for (int i = 0; i < numUsers; i++)
				outFile.write(this.users[i].getName() + "," + this.users[i].getEmail() + "," + this.users[i].getPhone()
						+ "," + this.users[i].getPassword() + "," + this.users[i].getLevel()
						+ System.getProperty("line.separator"));
			outFile.flush();
			outFile.close();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

		}

	}

	/**
	 * Load the Ticket data from the csv file and add it to an ticket object array
	 */
	public void loadTicketData() { // Load ticket data from file
		BufferedReader inFile = null;
		String[] tempInput;
		try {
			inFile = new BufferedReader(new FileReader("./" + "TicketData.csv"));
			String currLine = inFile.readLine();
			while (currLine != null && currLine.trim().length() > 0) { // A bit of input validation
				tempInput = currLine.split(","); // Split line into array elements
				this.addTicket(tempInput[0], tempInput[1], tempInput[2], Integer.parseInt(tempInput[3]), tempInput[4],
						Boolean.parseBoolean(tempInput[5]), tempInput[6]);
				currLine = inFile.readLine();
				numTickets++;
			}
			inFile.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Add a ticket to the ticket array
	 */
	public void addTicket(String name, String description, String assignee, int severity, String status,
			boolean resolved, String time) {
		this.tickets[numTickets] = new Ticket(name, description, assignee, severity, status, resolved, time);
//		System.out.print(this.tickets[numTickets].getName());
//		System.out.print(this.tickets[numTickets].getDescription());
//		System.out.print(this.tickets[numTickets].getAssignee());
//		System.out.print(this.tickets[numTickets].getSeverity());
//		System.out.print(this.tickets[numTickets].getStatus());
//		System.out.print(this.tickets[numTickets].getResolved());
//		System.out.print(this.tickets[numTickets].getTime());
//		System.out.println();
	}
	
	/**
	 * Return the current number of Tickets in the system
	 */
	 public int getNumTickets() {
		 return numTickets;
	 }

	 /**
	  * Save the ticket data to the csv file
	  */	
	public void saveTicketData() { // Save data to file
		BufferedWriter outFile = null;
		try {
			outFile = new BufferedWriter(new FileWriter("./" + "TicketData.csv"));
			for (int i = 0; i < getNumTickets(); i++)
				outFile.write(this.tickets[i].getName() + "," + this.tickets[i].getDescription() + ","
						+ this.tickets[i].getAssignee() + "," + this.tickets[i].getSeverity() + ","
						+ this.tickets[i].getStatus() + "," + this.tickets[i].getResolved() + ","
						+ this.tickets[i].getTime() + System.getProperty("line.separator"));
			outFile.flush();
			outFile.close();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		Storage test = new Storage();

	}

}
