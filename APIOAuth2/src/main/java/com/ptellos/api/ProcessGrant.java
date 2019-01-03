package com.ptellos.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.ptellos.dao.DAOLogin;

/**
 * Servlet implementation class ProcessGrant
 */
@WebServlet("/ProcessGrant")
public class ProcessGrant extends HttpServlet {
	private final static Logger LOGGER = Logger.getLogger("ProcessGrant");
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProcessGrant() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String nombre = request.getParameter("username");
		String password = request.getParameter("password");
		Map<String, Object> map = new HashMap<String, Object>();
		LOGGER.log(Level.INFO, "Username: " + nombre + "\tPassword: " + password);
		boolean isValidUser = false; 
		try {
			if (DAOLogin.existUser(nombre, password)) {
				LOGGER.log(Level.INFO, "El usuario se encuentra dado de alta en la API");
				isValidUser = true;
			} else {
				LOGGER.log(Level.INFO, "El usuario no se encuentra dado de alta en la API");
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error de acceso a DAOLogin: " + e);
		}
		final boolean valid = isValidUser; 
		Thread thread = new Thread(){
		    public void run(){
		    	LOGGER.log(Level.INFO, "Se abre un hilo adicional");
		    	if (valid) {
					LOGGER.log(Level.INFO, "Finalizado el proceso con Ajax. Procedemos a tramitar el Authorization Code");
					//Procesamos todo lo que tenga que ver con la respuesta a la Application
					try {
						response.sendRedirect("ProcessGrant");
					} catch (IOException e) {
						LOGGER.log(Level.SEVERE, "Error al realizar la redirección: " + e);
						e.printStackTrace();
					}
				}
		    }
		};		
		map.put("isValidUser", isValidUser);
		write(response, map);
		thread.start();
	}
	
	private void write(HttpServletResponse response, Map<String, Object> map) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(new Gson().toJson(map));
	}

}
