package com.ptellos.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAOValueApplicationRequest {

	private final static Logger LOGGER = Logger.getLogger("DAOValueApplicationRequest");
	
	public static String returnValue(String column) throws Exception {
		Connection c = null;
		Statement stmt = null;
		String value = "";
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/oAuth2", "postgres", "cha43comjo");
			c.setAutoCommit(false);
			LOGGER.log(Level.INFO, "Opened database successfully");
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT " + column + " FROM redoa2_application_request;");
			while (rs.next()) {
				value = rs.getString(column);
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
		return value;
	}
	
	public static void setApplicationCredentials(String url_redirect, String access_token, String token_type, long expires_in, String refresh_token) throws SQLException, ClassNotFoundException {
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
					"INSERT INTO redoa2_application_record(url_redirect, access_token, token_type, expires_in, refresh_token) VALUES (?, ?, ?, ?, ?);");
			st.setString(1, url_redirect);
			st.setString(2, access_token);
			st.setString(3, token_type);
			st.setLong(4, expires_in);
			st.setString(5, refresh_token);
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
