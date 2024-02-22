package com.swingcrud;

import java.util.regex.*;
import java.sql.*;

public final class Validator {


	public static boolean isValidEmail(String email,Connection conn) throws SQLException {
		// Define the regular expression for a valid email address
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		// Create a Pattern object
		Pattern pattern = Pattern.compile(emailRegex);
		// Create matcher object
		Matcher matcher = pattern.matcher(email);

		PreparedStatement st=conn.prepareStatement("SELECT * FROM user_master WHERE email=?");
		st.setString(0, email);
		ResultSet rs=st.executeQuery();
		// Check if the email matches the pattern
		return matcher.matches() && !rs.next();
	}
	public static boolean isValidEmail(String email,Connection conn, int UID) throws SQLException {
		// Define the regular expression for a valid email address
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		// Create a Pattern object
		Pattern pattern = Pattern.compile(emailRegex);
		// Create matcher object
		Matcher matcher = pattern.matcher(email);

		PreparedStatement st=conn.prepareStatement("SELECT UID FROM user_master WHERE email=?");
		st.setString(0, email);
		ResultSet rs=st.executeQuery();
		// Check if the email matches the pattern
		if(rs.next()) {
			if(UID==rs.getInt(0))
				return matcher.matches();
			else 
				return false;
		}else {
			return matcher.matches();
		}
	}
}
