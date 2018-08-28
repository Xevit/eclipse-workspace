package com.ptellos.aplication;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ptellos.dao.DAORegistro;

@WebServlet(name = "RegisterServlet", urlPatterns = { "registra_usuario" })
public class RegisterServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().print("ERROR: En esta página no se puede hacer un HTTP GET");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		
		if (checkUser(request)) {
			// Si el usuario existe avisamos de que el usuario ya existe
			request.setAttribute("user", "El usuario ya existe!: " + request.getParameter("username"));
			request.getRequestDispatcher("response.jsp").forward(request, response);
		} else {
			// Si el usuario no existe le registramos
			setUser(request);	
			request.setAttribute("user", "register: " + request.getParameter("username"));
			request.getRequestDispatcher("response.jsp").forward(request, response);
		}
		
	}

	public boolean checkUser(HttpServletRequest request)  {
		boolean existe = false;
		try {
			if (DAORegistro.existUser(request.getParameter("username"))) {
				existe = true;
			}
		} catch (SQLException esql) {
			System.out.println("Error en SQLException");
		} catch (ClassNotFoundException eclass) {
			System.out.println("Error en ClassNotFoundException");
		} catch (Exception e) {
			System.out.println("Error general");
		}
		return existe;
	}
	
	public void setUser(HttpServletRequest request) {
		try {
			DAORegistro.setUser(request);
		} catch (SQLException esql) {
			System.out.println("Error en SQLException");
		} catch (ClassNotFoundException eclass) {
			System.out.println("Error en ClassNotFoundException");
		}
	}
	
}