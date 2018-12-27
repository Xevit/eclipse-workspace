package com.ptellos.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOLogin {

	private final static Logger LOGGER = Logger.getLogger("DAOLogin");
	
	public static boolean existUser(String usuario, String password) throws Exception {
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
			ResultSet rs = stmt.executeQuery("SELECT * FROM redoa2_users;");
			while (rs.next()) {
				// Aquí hay que tener en cuenta codificar la clave que nos pongan
				if ((usuario.equals(rs.getString("usuario"))) && (password.equals(rs.getString("password")))) {
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
}
