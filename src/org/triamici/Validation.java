package org.triamici;
/**
 * @author Assignment Group 9
 *
 */
public class Validation {

	/**
	 * Constructor
	 */
	Validation() {
	}
	
	/**
	 * Validate length
	 */
	public static boolean validLength(short acceptableLength, String input) {

		// Test the length of the string against the inputed length
		return input.length() <= acceptableLength;
		
	}
	
	/**
	 * Validate email
	 */
	public static boolean validEmail(String input) {
		
		// Sample regex from https://www.tutorialspoint.com/validate-email-address-in-java
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		
		// Test email against regex
		return input.matches(regex);
		
	}
	
	/**
	 * Validate Australian phone numbers
	 */
	public static boolean validPhone(String input) {

		// Sample regex from https://ilikekillnerds.com/2014/08/regular-expression-for-validating-australian-phone-numbers-including-landline-and-mobile
		String regex = "^\\({0,1}((0|\\+61)(2|4|3|7|8)){0,1}\\){0,1}(\\ |-){0,1}[0-9]{2}(\\ |-){0,1}[0-9]{2}(\\ |-){0,1}[0-9]{1}(\\ |-){0,1}[0-9]{3}$";

		// Test phone against regex
		return input.matches(regex);
		
	}
	
	/**
	 * Validate passwords
	 */
	public static boolean validPassword(String input) {

		// Sample regex from https://techearl.com/regular-expressions/regex-password-strength-validation
		String regex = "^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[0-9])[a-zA-Z0-9]{8,}$";
		
		// Test password against regex
		return input.matches(regex);
		
	}
	
	/**
	 * Validate integer
	 */
	public static boolean validInteger(String input) {

		// Sample regex from https://techearl.com/regular-expressions/regex-password-strength-validation
		String regex = "^[0-9]{1,}$";
		
		// Test integer against regex
		return input.matches(regex);
		
	}
	
	/**
	 * Validate short range
	 */
	public static boolean validShortRange(short low, short high, short input) {

		// Test short against range
		return input >= low && input <= high;
		
	}
}
