package org.triamici;

public class TicketAssignment {
	
	private long count = 1;
	private String assignee = "";
	
	public TicketAssignment(String assignee) {
		this.assignee = assignee;
	}

	public TicketAssignment(String assignee, long count) {
		this.assignee = assignee;
		this.count = count;
	}
	
	public long getCount() {
		return count;
	}
	
	public String getAssignee() {
		return assignee;
	}
	
	public void addOne() {
		this.count++;
	}
	
}
