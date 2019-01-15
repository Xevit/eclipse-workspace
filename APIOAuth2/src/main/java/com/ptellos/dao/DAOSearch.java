package com.ptellos.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOSearch {

	private final static Logger LOGGER = Logger.getLogger("DAOSearch");
	
	public static boolean existApp(String client_id, String code_authorization, String client_secret) throws Exception {
		Connection c = null;
		Statement stmt = null;
		boolean isCorrect = false;
		boolean isCorrectOne = false;
		try {
			Class.forName("org.postgresql.Driver");
			// Ver como poner esto en un *.properties
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/oAuth2", "postgres", "cha43comjo");
			c.setAutoCommit(false);
			LOGGER.log(Level.INFO, "Opened database successfully");
			// Me gusta más como realiza la sentencia otro programa
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM apioa2_authorization_base;");
			while (rs.next()) {
				// Aquí hay que tener en cuenta codificar la clave que nos pongan
				if (client_id.equals(rs.getString("client_id")) && code_authorization.equals(rs.getString("authorization_code"))) {
					isCorrectOne = true;
				}
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
			throw (e);
		}
		if (isCorrectOne) {
			try {
				Class.forName("org.postgresql.Driver");
				// Ver como poner esto en un *.properties
				c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/oAuth2", "postgres", "cha43comjo");
				c.setAutoCommit(false);
				LOGGER.log(Level.INFO, "Opened database successfully");
				// Me gusta más como realiza la sentencia otro programa
				stmt = c.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM apioa2_application_request;");
				while (rs.next()) {
					// Aquí hay que tener en cuenta codificar la clave que nos pongan
					if (client_id.equals(rs.getString("client_id")) && client_secret.equals(rs.getString("client_secret"))) {
						isCorrect = true;
					}
				}
				rs.close();
				stmt.close();
				c.close();
			} catch (Exception e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
				System.exit(0);
				throw (e);
			}
		}
		
		LOGGER.log(Level.INFO, "Operation done successfully");
		return isCorrect;
	}

}
