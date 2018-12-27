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


@WebServlet("/GrantAuthorization")
public class ListenerGrant extends HttpServlet{

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
		LOGGER.log(Level.INFO, "Parametros: " + " Response_Type: " + response_type + " Client_Id: " + client_id + " Redirect_Uri: " + redirect_uri + " Scope: " + scope);
		//Ahora comprobaremos si en la BBDD se ha dado de alta este usuario con este client_id
		
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
