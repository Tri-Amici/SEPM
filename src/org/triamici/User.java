package org.triamici;

/**
 * @author Assignment Group 9
 *
 */

public class User { // User class for creating User objects
	private String name; // User's name variable
	private String email; // User's email variable
	private String phone; // User's phone number variable
	private String password; // User's password variable
	private short level; // User's support level variable

	public User(String name, String email, String phone, String password, short level) { // Constructor
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.password = password;
		this.level = level;
	}

	public String getName() { // Returns the user's name
		return name;
	}

	public void setName(String name) { // Sets the user's name
		this.name = name;
	}

	public String getEmail() { // Returns the users' email
		return this.email;
	}

	public String getPhone() { // Returns the users' phone number
		return this.phone;
	}

	public String getPassword() { // Returns the users' password
		return this.password;
	}

	public short getLevel() { // Returns the users' support level
		return this.level;
	}

	public void setLevel(short level) { // Sets the users support level
		this.level = level;
	}
}