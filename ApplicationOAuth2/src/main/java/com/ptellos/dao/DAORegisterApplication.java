package com.ptellos.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DAORegisterApplication {
	public static void registerApplication(String url, String codeSecret, String clientId, String clientSecret) throws Exception {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/oAuth2", "postgres", "cha43comjo");
			stmt = c.createStatement();
			c.setAutoCommit(false);
			// TODO: Esto hacerlo en una funcion a parte
			PreparedStatement st = c.prepareStatement(
					"INSERT INTO redoa2_application_request(url_redirect, code_secret, client_id, client_secret) VALUES (?, ?, ?, ?);");
			st.setString(1, url);
			st.setString(2, codeSecret);
			st.setString(3, clientId);
			st.setString(4, clientSecret);
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
