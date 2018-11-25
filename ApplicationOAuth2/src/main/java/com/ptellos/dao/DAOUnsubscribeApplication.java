package com.ptellos.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOUnsubscribeApplication {
	public static void unsubscribeApplication(String url) throws Exception {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/oAuth2", "postgres", "cha43comjo");
			stmt = c.createStatement();
			c.setAutoCommit(false);
			PreparedStatement st = c.prepareStatement(
					"DELETE FROM redoa2_application_request WHERE url_redirect = ?;");
			st.setString(1, url);
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
	}
}
