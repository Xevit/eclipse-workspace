package com.ptellos.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOSearchApplication {

	private final static Logger LOGGER = Logger.getLogger("DAOSearchApplication");
	
	public static boolean existApp(String client_id, String redirect_uri) throws Exception {
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
			ResultSet rs = stmt.executeQuery("SELECT * FROM apioa2_application_request;");
			while (rs.next()) {
				// Aquí hay que tener en cuenta codificar la clave que nos pongan
				if (client_id.equals(rs.getString("client_id")) && redirect_uri.contains(rs.getString("url_redirect"))) {
					isCorrect = true;
				}
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (SQLException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
			throw (e);
		} catch (ClassNotFoundException eclass) {
			System.err.println(eclass.getClass().getName() + ": " + eclass.getMessage());
			System.exit(0);
			throw (eclass);
		}
		LOGGER.log(Level.INFO, "Operation done successfully");
		return isCorrect;
	}
}