package com.ptellos.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

public class DAORegistro {

	private final static Logger LOGGER = Logger.getLogger("DAORegistro");
	
	public static boolean existUser(String usuario) throws SQLException, ClassNotFoundException {
		Connection c = null;
		Statement stmt = null;
		boolean isCorrect = false;
		try {
			Class.forName("org.postgresql.Driver");
			// Ver como poner esto en un *.properties
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/oAuth2", "postgres", "cha43comjo");
			c.setAutoCommit(false);
			LOGGER.log(Level.INFO, "Opened database successfully");
			// Me gusta m�s como realiza la sentencia otro programa
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM redoa2_users;");
			while (rs.next()) {
				// Aqu� hay que tener en cuenta codificar la clave que nos pongan
				if (usuario.equals(rs.getString("usuario"))) {
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

	public static void setUser(HttpServletRequest request) throws SQLException, ClassNotFoundException {
		Connection c = null;
		Statement stmt = null;
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		int id = 0;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/oAuth2", "postgres", "cha43comjo");
			stmt = c.createStatement();
			c.setAutoCommit(false);
			LOGGER.log(Level.INFO, "Opened database successfully");
			// TODO: Hacerlo en funcion a parte
			ResultSet rs1 = stmt.executeQuery("SELECT count(*) FROM redoa2_users;");
			while (rs1.next()) {
				id = rs1.getInt(1) + 1;
			}
			// TODO: Esto hacerlo en una funcion a parte
			PreparedStatement st = c.prepareStatement("INSERT INTO redoa2_users(id, nombre, usuario, password, email) VALUES (?, ?, ?, ?, ?);");
			st.setInt(1, id);
			st.setString(2, username);
			st.setString(3, username);
			st.setString(4, password);
			st.setString(5, email);
			st.executeUpdate();
			c.commit();
			//Cerramos todos los hilos abiertos
			rs1.close();
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