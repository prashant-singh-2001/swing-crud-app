package com.swingcrud;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Validator {

	public Validator() {
		// TODO Auto-generated constructor stub
	}

	public static boolean isValidEmail(String email) {
		// Define the regular expression for a valid email address
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

		// Create a Pattern object
		Pattern pattern = Pattern.compile(emailRegex);

		// Create matcher object
		Matcher matcher = pattern.matcher(email);

		// Check if the email matches the pattern
		return matcher.matches();
	}
	public static void main(String[] args) {
		System.out.println(isValidEmail("ahsfdhgasd"));
	}
}
