---------------------------------------------------------------------------------

---                        SEPM Assignment 2, Final Sprint                         ---

---------------------------------------------------------------------------------
                      Application Installation on myDesktop
---------------------------------------------------------------------------------
1. Copy the project zip file to RMIT's myDesktop H:\ drive

2. Unpack the zip file to H:\ You should see H:\Group 9_SEPM_A2_FinalSprint\code\...

3. Open the JDK command prompt and navigate to the java project source files:
	cd "Group 9_SEPM_A2_FinalSprint\code\src\org\triamici\"

4. Compile the Java files using the following command:
	javac --release 8 *.java
	
5. Navigate up two levels to the folder: "Group 9_SEPM_A2_FinalSprint\code\src\"
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

After a successful login the user will then be presented with the Ticket Menu.
The menu options that are displayed will depend on whether the user that has
logged in is a standard user or a technician.

A standard user will be presented with the following options:


	-------------------------
	0. Exit the app
	1. Log a ticket
	2. View Submitted Open Tickets
	-------------------------

A level 1 or level 2 technician will be presented with additonal options:
	-------------------------
	0. Exit the app
	1. Log a ticket
	2. View Submitted Open Tickets
    3. View Assigned Open Tickets
    4. Change Ticket Severity
    5. Change Ticket Status
    6. View All Closed & Archived Tickets
    7. Display Report
	-------------------------

Option 1. Log a ticket - This will result in the user being asked to provide a 
description and severity for the ticket. The system will then report that a ticket 
has been successfully logged and then return to the Ticket Menu. e.g.
	Enter a ticket description
	PC won't start
	Enter a ticket severity
	0 - Low priority
	1 - Medium priority
	2 - High priority
	Ticket logged!
	
Option 2. View Submitted Open Tickets - This option will result in the user being
presented with a list of the open tickets that they have submitted and the status of those 
tickets. For example:

    ID        Creator             Assignee            Severity       Resolution     Status         Description                        
    --------- ------------------- ------------------- -------------- -------------- -------------- ----------------------------------
    4         Matt Kellock        Louis Tomlinson     High           Unresolved     Open           My thing is broken too
    8         Matt Kellock        Niall Horan         Medium         Unresolved     Open           Equipment Malfunction


NOTE: The following options will only appear for technicians. 


Option 3. View Assigned Open Tickets - This option will result in the 
technician user being presented with a list of the
open tickets that have been assigned to them, if there are any. For example:

    ID        Creator             Assignee            Severity       Resolution     Status         Description                        
    --------- ------------------- ------------------- -------------- -------------- -------------- ----------------------------------
    11        Liam Chisari        Zayn Malik          High           Unresolved     Open           Description of issue


Option 4. Change Ticket Severity - This option allows the user to change the 
severity of an open and unresolved ticket. Changing the severity of the issue
may result in the system automatically reassigning the ticket to a technician
of the appropriate level. For example:

    ID        Creator             Assignee            Severity       Resolution     Status         Description                        
    --------- ------------------- ------------------- -------------- -------------- -------------- ----------------------------------
    4         Matt Kellock        Zayn Malik          High           Unresolved     Open           My thing is broken too
    8         Matt Kellock        Louis Tomlinson     High           Unresolved     Open           Equipment malfunction
    Please enter a ticket ID
    4
    Enter a ticket severity
    0 - Low priority
    1 - Medium priority
    2 - High priority
    0


Now that the severity of the ticket has been changed to low, the assigned technician has also
been changed to a level 1 technician from a level 2 technician.

ID        Creator             Assignee            Severity       Resolution     Status         Description                        
--------- ------------------- ------------------- -------------- -------------- -------------- ----------------------------------
4         Matt Kellock        Niall Horan         Low            Unresolved     Open           My thing is broken too
8         Matt Kellock        Louis Tomlinson     High           Unresolved     Open           Equipment malfunction


Option 5. Change Ticket Status - This option allows the user to change the status
of the ticket from open to closed and to mark it as resolved if it is being changed
to closed. For example:

    ID        Creator             Assignee            Severity       Resolution     Status         Description                        
    --------- ------------------- ------------------- -------------- -------------- -------------- ----------------------------------
    4         Matt Kellock        Niall Horan         Low            Unresolved     Open           My thing is broken too
    8         Matt Kellock        Louis Tomlinson     High           Unresolved     Open           Equipment malfunction
    Please enter a ticket ID
    4
    Enter a ticket status
    0 - Closed
    1 - Open
    0
    Was the issue resolved
    0 - Not resolved
    1 - Resolved
    1

The changes can be seen here: 

    ID        Creator             Assignee            Severity       Resolution     Status         Description                        
    --------- ------------------- ------------------- -------------- -------------- -------------- ----------------------------------
    4         Matt Kellock        Niall Horan         Low            Resolved       Closed         My thing is broken too
    8         Matt Kellock        Louis Tomlinson     High           Unresolved     Open           Equipment malfunction


Option 6. View All Closed & Archived Tickets - This option allows the user to
view the details of all closed and archived tickets. For example:

ID     Creator               Assignee              Severity    Resolution  Status                Description
------ --------------------- --------------------- ----------- ----------- --------------------- -------------------------------
1      Matt Kellock          Harry Styles          Medium      Unresolved  Closed - ARCHIVED     My thing is broken
2      Matt Kellock          Harry Styles          Low         Unresolved  Closed - ARCHIVED     My thing is broken too
3      Matt Kellock          Harry Styles          Medium      Unresolved  Closed - ARCHIVED     My thing is broken
5      Matt Kellock          Niall Horan           Medium      Resolved    Closed - ARCHIVED     My thing is broken
6      Matt Kellock          Liam Payne            Medium      Resolved    Closed - ARCHIVED     My thing is broken too
7      Matt Kellock          Liam Payne            Low         Unresolved  Closed - ARCHIVED     Desc
9      Matt Kellock          Liam Payne            Low         Unresolved  Closed - ARCHIVED     Blah
10     Matt Kellock          Louis Tomlinson       High        Resolved    Closed - ARCHIVED     sdfgd
11     Zayn Malik            Zayn Malik            High        Resolved    Closed - ARCHIVED     Description of issue

Option 7. Display Report - This option generates a report containing the details of all tickets
within the provided date range. For example:

    Enter the start date for the range in the format: YYYY-MM-DD
    2021-01-01
    Enter the end date for the range
    2022-01-01
    Showing Ticket Report for Dates: 2021-01-01 to 2022-01-01
    -----------------------------------
    Creator        Assignee                 Time Created             Resolved  Severity  Time Taken     
    -------------- ------------------------ ------------------------ --------- --------- -------------- 
    Matt Kellock   Harry Styles             14/04/2021 09:26 PM      No        Medium    
    Matt Kellock   Harry Styles             14/04/2021 09:26 PM      No        Low       
    Matt Kellock   Harry Styles             14/04/2021 09:26 PM      No        Medium    
    Matt Kellock   Niall Horan              14/04/2021 09:26 PM      No        Low       
    Matt Kellock   Niall Horan              14/04/2021 09:26 PM      Yes       Medium    7D:0H:00M
    Matt Kellock   Liam Payne               14/04/2021 09:26 PM      Yes       Medium    7D:0H:00M
    Matt Kellock   Liam Payne               15/04/2021 04:48 PM      No        Low       
    Matt Kellock   Louis Tomlinson          15/04/2021 04:49 PM      No        High      
    Matt Kellock   Liam Payne               20/04/2021 01:34 PM      No        Low       
    Matt Kellock   Louis Tomlinson          20/04/2021 01:40 PM      Yes       High      1D:16H:50M
    Zayn Malik     Zayn Malik               21/04/2021 06:30 AM      Yes       High      1D:0H:34M

    Total Tickets: 11 - Resolved Tickets: 4 - Unresolved Tickets: 7


To produce a uniform report that meets our standards for appearance and readibility,
we have decied to show the severity for both resolved & unresolved tickets. 
The requirements of the client have been met.
