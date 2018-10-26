package com.ptellos.application;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.utils.URIBuilder;

/**
 * Servlet implementation class ApplicationUnsubscribe
 */

@WebServlet("/BajaEnAPI")
public class ApplicationUnsubscribe extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ApplicationUnsubscribe() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().print("ERROR: En esta pagina no se puede hacer un HTTP GET");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("ApplicationUnsubscribe - doPost()");		
		String path = request.getRequestURI();
		if (path.equals("/ApplicationOAuth2/AltaEnAPI")) {
			String oauthUri = "";

			// Logear
			try {
				oauthUri = new URIBuilder().setScheme(Constants.SCHEME).setHost(Constants.HOST).setPort(Constants.PORT)
						.setPath("/" + Constants.PATH_API + "/" + Constants.PATH_UNSUBSCRIBE)
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
			
		} else {
			
		}
	}

}
