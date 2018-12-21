package com.ptellos.api;

import java.io.IOException;
import java.net.URLDecoder;

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

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("ListenerGrant - doGet()");
		String path = request.getRequestURI();
		System.out.println("El path es: " + path);
		String response_type = URLDecoder.decode(request.getParameter(Constants.RESPONSE_TYPE), "UTF-8");;
		String client_id = URLDecoder.decode(request.getParameter(Constants.CLIENT_ID), "UTF-8");;
		String redirect_uri = URLDecoder.decode(request.getParameter(Constants.REDIRECT_URI), "UTF-8");;
		String scope = URLDecoder.decode(request.getParameter(Constants.SCOPE), "UTF-8");;
		System.out.println("Parametros:\n" + 
				"Response_Type: " + response_type + "\n" + 
				"Client_Id: " + client_id + "\n" + 
				"Redirect_Uri: " + redirect_uri + "\n" + 
				"Scope: " + scope);
		//Ahora comprobaremos si en la BBDD se ha dado de alta este usuario con este client_id
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("ListenerGrant - doPost()");

		String path = request.getRequestURI();
		System.out.println("El path es: " + path);
	}
}
