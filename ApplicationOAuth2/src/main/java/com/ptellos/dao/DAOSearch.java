package com.ptellos.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOSearch {

	private final static Logger LOGGER = Logger.getLogger("DAOSearch");
	
	public static boolean existClient(String client_id) throws Exception {
		Connection c = null;
		Statement stmt = null;
		boolean isCorrect = false;
		try {
			Class.forName("org.postgresql.Driver");
			// Ver como poner esto en un *.properties
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/oAuth2", "postgres", "cha43comjo");
			c.setAutoCommit(false);
			LOGGER.log(Level.INFO, "Opened database successfully");
			// Me gusta más como realiza la sentencia otro programa
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM redoa2_application_request;");
			while (rs.next()) {
				// Aquí hay que tener en cuenta codificar la clave que nos pongan
				if (client_id.equals(rs.getString("client_id"))) {
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
		LOGGER.log(Level.INFO, "Operation done successfully");
		return isCorrect;
	}
	
	public static String returnClientSecret(String client_id) throws Exception {
		Connection c = null;
		Statement stmt = null;
		String client_secret = null;
		try {
			Class.forName("org.postgresql.Driver");
			// Ver como poner esto en un *.properties
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/oAuth2", "postgres", "cha43comjo");
			c.setAutoCommit(false);
			LOGGER.log(Level.INFO, "Opened database successfully");
			// Me gusta más como realiza la sentencia otro programa
			stmt = c.createStatement();
			//SELECT client_secret FROM redoa2_application_request where client_id='B4QtOucohk0cSUngiSuhj';
			ResultSet rs = stmt.executeQuery("SELECT client_secret FROM redoa2_application_request WHERE client_id='" + client_id + "';");
			while (rs.next()) {
				client_secret = rs.getString("client_secret");
				// Aquí hay que tener en cuenta codificar la clave que nos pongan
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
			throw (e);
		}
		LOGGER.log(Level.INFO, "Operation done successfully");
		return client_secret;
	}
	
}
