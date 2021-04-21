package org.triamici;

import java.time.LocalDateTime;

/**
 * @author Assignment Group 9
 *
 */

public class Ticket { // User class for creating Tickets objects
	private int id = 0; // The ticket's ID
	private String creator; // User's email variable
	private String description; // A description of the  fault
	private String assignee; // The support technician's email assigned to the fault
	private short severity; // The issue severity
	private short status; // The status of the issue
	private boolean resolved; // resolved Y/N
	private LocalDateTime time; // The time the issues was closed
	
	public Ticket(int id, String creator, String description, String assignee, short severity, short status, boolean resolved, LocalDateTime time) { //Constructor 
		this.id = id;
		this.creator = creator;
		this.description = description;
		this.assignee = assignee;
		this.severity = severity;
		this.status = status;
		this.resolved = resolved;
		this.time = time;
	}
	
	public int getId() { // Sets the ticket ID
		return this.id;
	}
	
	public String getCreator() { // Returns the user's name
		return creator;
	}
	
	public String getDescription() { // Returns the ticket's description email
		return description;
	}

	public String getAssignee() { // Returns the ticket's Assignee
		return assignee;
	}
	
	public short getSeverity() { // Returns the ticket's Severity
		return severity;
	}
	
	public short getStatus() { // Returns the ticket's status
		return status;
	}

	public boolean getResolved() { // Returns the ticket's resolved status
		return resolved;
	}
	
	public LocalDateTime getTime() { // Returns the ticket's time stamp
		return time;
	}
	
	public void setId(int id) { // Sets the ticket ID
		this.id = id;
	}
	
	public void setSeverity(short severity) { // Sets the ticket's Severity
		this.severity = severity; 
	}
	
	public void setStatus(short status) { // Sets the ticket's Status
		this.status = status; 
	}
	
	public void setResolved(boolean resolved) { // Sets the ticket's resolved status
		this.resolved = resolved; 
	}
	
	public void setTime (LocalDateTime time) { // Sets the ticket's time stamp
		this.time = time; 
	}
}