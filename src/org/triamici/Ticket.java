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
	private boolean closed; // The closed status of the issue
	private boolean resolved; // resolved Y/N
	private LocalDateTime openedTime; // The time the issues was raised
	private LocalDateTime closedTime; // The time the issues was raised
	
	public Ticket(int id, String creator, String description, String assignee, short severity, boolean closed, boolean resolved, LocalDateTime openedTime, LocalDateTime closedTime) { //Constructor 
		this.id = id;
		this.creator = creator;
		this.description = description;
		this.assignee = assignee;
		this.severity = severity;
		this.closed = closed;
		this.resolved = resolved;
		this.openedTime = openedTime;
		this.closedTime = closedTime;
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
	
	public boolean getClosed() { // Returns the ticket's status
		return closed;
	}

	public boolean getResolved() { // Returns the ticket's resolved status
		return resolved;
	}

	public LocalDateTime getOpenedTime() { // Returns the ticket's opened time stamp
		return openedTime;
	}
	
	public LocalDateTime getClosedTime() { // Returns the ticket's closed time stamp
		return closedTime;
	}
	
	public void setId(int id) { // Sets the ticket ID
		this.id = id;
	}
	
	public void setAssignee(String assignee) { // Sets the ticket's Severity
		this.assignee = assignee; 
	}
	
	public void setSeverity(short severity) { // Sets the ticket's Severity
		this.severity = severity; 
	}
	
	public void setClosed(boolean closed) { // Sets the ticket's Status
		this.closed = closed; 
	}
	
	public void setResolved(boolean resolved) { // Sets the ticket's resolved status
		this.resolved = resolved; 
	}

	public void setOpenedTime (LocalDateTime time) { // Sets the ticket's time stamp
		this.openedTime = time; 
	}
	
	public void setCloseTime (LocalDateTime time) { // Sets the ticket's time stamp
		this.closedTime = time; 
	}
}