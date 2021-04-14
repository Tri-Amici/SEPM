package org.triamici;
/**
 * @author Assignment Group 9
 *
 */

public class Ticket { // User class for creating Tickets objects
	private String name; // User's name variable
	private String description; // A description of the  fault
	private String assignee; // The support technician assigned to the fault
	private int severity; // The issue severity
	private String status; // The status of the issue
	private boolean resolved; // resolved Y/N
	private String time; // The time the issues was closed
	
	public Ticket(String name, String description, String assignee, int severity, String status, boolean resolved, String time) { //Constructor 
		this.name = name;
		this.description = description;
		this.assignee = assignee;
		this.severity = severity;
		this.status = status;
		this.resolved = resolved;
		this.time = time;
	}

	public String getName() { // Returns the user's name
		return name;
	}
	
	public String getDescription() { // Returns the ticket's description email
		return description;
	}

	public String getAssignee() { // Returns the ticket's Assignee
		return assignee;
	}
	
	public int getSeverity() { // Returns the ticket's Severity
		return severity;
	}
	
	public String getStatus() { // Returns the ticket's status
		return status;
	}

	public boolean getResolved() { // Returns the ticket's resolved status
		return resolved;
	}
	
	public String getTime() { // Returns the ticket's time stamp
		return time;
	}
	
	public void setSeverity(int severity) { // Sets the ticket's Severity
		this.severity = severity; 
	}
	
	public void setStatus(String status) { // Sets the ticket's Status
		this.status = status; 
	}
	
	public void setResolved(boolean resolved) { // Sets the ticket's resolved status
		this.resolved = resolved; 
	}
	
	public void setTime (String time) { // Sets the ticket's time stamp
		this.time = time; 
	}
}