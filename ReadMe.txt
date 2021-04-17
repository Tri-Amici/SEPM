---------------------------------------------------------------------------------

---                        SEPM Assignment 2, Sprint 1                        ---

---------------------------------------------------------------------------------
                      Application Installation on myDesktop
---------------------------------------------------------------------------------
1. Copy the project zip file to RMIT's myDesktop H:\ drive

2. Unpack the zip file to H:\ You should see H:\Group 9_SEPM_A2_Sprint2\Code\...

3. Open the JDK command prompt and navigate to the java project source files:
	cd "Group 9_SEPM_A2_Sprint1\code\src\org\triamici\"

4. Compile the Java files using the following command:
	javac --release 8 *.java
	
5. Navigate up two levels to the folder: "Group 9_SEPM_A2_Sprint2\code\src\"
	cd ..\..\

5. Run the program using the following command:
	java org.triamici.TriAmici
	
---------------------------------------------------------------------------------
						      Application Usage
---------------------------------------------------------------------------------
When the java application is first run users will be presented with the following 
Main Menu:
	-------------------------
	1. Log in
	2. Register as new user
	3. Retrieve password
	------------------------- 

Option 1. Log In - The system will then ask you to enter an email and password 
from one of the example hard coded users. e.g.
	Enter your email address to log in
	hstyles@student.rmit.edu.au
	Enter your password.
	qwerty123
	Successfully logged in user Harry Styles.
	
Option 2. - Register a new user - The user will be asked to create a new user in 
the system. e.g.
	Enter your email address
	tmctest@student.rmit.edu.au
	Enter your name
	Testy McTest
	Enter your phone number
	12345678
	Enter your password
	- At least one uppercase, lowercase and number
	- No special characters
	- 8 characters in length)
	Password123 

Option 3. Retrieve password - The user will need to provide a valid email for 
which to retrieve the password. e.g.
	Enter your email address
	hstyles@student.rmit.edu.au
	User found, Your password is: qwerty123

After a successful login the user will then be presented with the Ticket Menu
	-------------------------
	0. Exit the app
	1. Log a ticket
	2. View Submitted Open Tickets
	-------------------------

Option 1. Log a ticket - This will result in the user being asked to provide a 
description and severity for the ticket. The system will then report that a ticket 
has been successfully logged and then return to the Ticket Menu. e.g.
	Enter a ticket description
	PC won't start
	Enter a ticket severity
	0 - Low priority
	1 - Medium priority
	2 = High priority
	Ticket logged!
	
Option 2. View Submitted Open Tickets - The feature is not yet commissioned
