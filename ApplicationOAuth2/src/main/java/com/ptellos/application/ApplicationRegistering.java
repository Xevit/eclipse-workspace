package com.ptellos.application;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.utils.URIBuilder;

/**
 * Servlet implementation class ApplicationRegister
 * 
 */

@WebServlet("/AltaEnAPI/*")
public class ApplicationRegistering extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ApplicationRegistering() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("ApplicationRegistering - doGet()");
		
		String path = request.getRequestURI();
		System.out.println("El path es: " + path);
		if (path.equals("/ApplicationOAuth2/AltaEnAPI")) {
			String oauthUri = "";
			// Logear
			System.out.println("Entramos normal");
			try {
				oauthUri = new URIBuilder()
						.setScheme(Constants.SCHEME)
						.setHost(Constants.HOST)
						.setPort(Constants.PORT)
						.setPath("/" + Constants.PATH_API + "/" + Constants.PATH_REGISTER)
						.setParameter(Constants.REDIRECT, Constants.REDIRECT_APPLICATION)
						// Este parametro debería estar codificado con una clave AES
						.setParameter(Constants.CODE_SECRET, Constants.CODE_SECRET_VALUE).build().toASCIIString();
			} catch (URISyntaxException e) {
				/*
				 * logger.debug("Ha ocurrido un error al dar de alta la aplicación en la API: "
				 * + e); Activar cuando dispongamos de logger
				 */
				e.printStackTrace();
			}
			response.sendRedirect(oauthUri);
		} else if(path.equals("/ApplicationOAuth2/AltaEnAPI/Confirmation")) {
			//response.sendRedirect("http://localhost:8080//ApplicationOAuth2/altaEnAPI");
			String exist = URLDecoder.decode(request.getParameter("exist"), "UTF-8");
			if (exist != null) {
				request.setAttribute("exist", exist);
				request.getRequestDispatcher("/altaEnAPI.jsp").forward(request, response); 
			} 
		} else {
			//Este será el caso NO se puede dar
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().print("ERROR: En esta pagina no se puede hacer un HTTP POST");
	}
}
