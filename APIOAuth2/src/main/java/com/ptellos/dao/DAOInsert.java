package com.ptellos.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOInsert {

	private final static Logger LOGGER = Logger.getLogger("DAOInsert");

	public static void setCorrectGrant(String client_id, String client_secret, String access_token,
			String refresh_token, long expires_in) throws SQLException, ClassNotFoundException {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/oAuth2", "postgres", "cha43comjo");
			stmt = c.createStatement();
			c.setAutoCommit(false);
			LOGGER.log(Level.INFO, "Opened database successfully");
			// TODO: Esto hacerlo en una funcion a parte
			PreparedStatement st = c.prepareStatement(
					"INSERT INTO apioa2_application_record(client_id, client_secret, access_token, refresh_token, expires_in) VALUES (?, ?, ?, ?, ?);");
			st.setString(1, client_id);
			st.setString(2, client_secret);
			st.setString(3, access_token);
			st.setString(4, refresh_token);
			st.setLong(5, expires_in);
			st.executeUpdate();
			c.commit();
			// Cerramos todos los hilos abiertos
			st.close();
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

	}

}
