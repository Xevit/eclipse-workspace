package com.ptellos.api;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ptellos.dao.DAOSearchApplication;

@WebServlet("/GrantAuthorization")
public class ListenerGrant extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = Logger.getLogger("ListenerGrant");

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.log(Level.INFO, "doGet()");
		String path = request.getRequestURI();
		LOGGER.log(Level.INFO, "El path es: " + path);
		String response_type = URLDecoder.decode(request.getParameter(Constants.RESPONSE_TYPE), "UTF-8");
		String client_id = URLDecoder.decode(request.getParameter(Constants.CLIENT_ID), "UTF-8");
		String redirect_uri = URLDecoder.decode(request.getParameter(Constants.REDIRECT_URI), "UTF-8");
		String scope = URLDecoder.decode(request.getParameter(Constants.SCOPE), "UTF-8");
		// Ahora comprobaremos si en la BBDD se ha dado de alta este usuario con este
		// client_id
		if ((response_type != null) && (client_id != null) && (redirect_uri != null) && (scope != null)) {
			LOGGER.log(Level.INFO, "Parametros: " + " Response_Type: " + response_type + " Client_Id: " + client_id
					+ " Redirect_Uri: " + redirect_uri + " Scope: " + scope);
			boolean exist = false;
			if (response_type.equals("code")) {
				try {
					exist = DAOSearchApplication.existApp(client_id, redirect_uri);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					LOGGER.log(Level.SEVERE, "Error en el acceso a la BBDD: " + e);
				}
				if (exist) {
					LOGGER.log(Level.INFO, "La aplicación dispone de permisos para acceder a la concesión de autorización");
					//Tenemos que pasarle el redirect_uri y el client_id
					String cID = request.getParameter(Constants.CLIENT_ID);
					String rURI = request.getParameter(Constants.REDIRECT_URI);
					response.sendRedirect("login.jsp" + "?" + Constants.CLIENT_ID + "=" + cID + "&"
							+ Constants.REDIRECT_URI + "=" + rURI);
				} else {
					LOGGER.log(Level.WARNING, "La aplicación no dispone de permisos para acceder a la concesión de autorización");
					request.getRequestDispatcher("loginOff.html").forward(request, response);
					//response.sendRedirect("loginOff.html");
				}
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.log(Level.INFO, "doPost()");
		String path = request.getRequestURI();
		LOGGER.log(Level.INFO, "El path es: " + path);
	}
}
