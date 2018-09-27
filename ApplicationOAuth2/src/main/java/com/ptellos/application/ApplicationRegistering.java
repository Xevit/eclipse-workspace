package com.ptellos.application;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

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

@WebServlet("/AltaEnAPI")
public class ApplicationRegistering extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApplicationRegistering() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * EN URL, 2. PASOS CONTINUOS. 05 Explico el problema que había
		 */
		
		//String oauthUri = "";
		URI oauthUri = null;
		
		try {
			System.out.println("Accedemos a la construcción de la URI");
			oauthUri = new URIBuilder()
					.setScheme("http")
					.setHost("localhost")
					.setPort(8080)
					.setPath("/APIOAuth2")
					//.addParameter("nombreApplication", "ApplicationoAuth2")
					//.setParameter("nombre_application", "ApplicationoAuth2")
					.build();//.toASCIIString();
		} catch (Exception e) {			
			System.out.println("Error en la construcción de la URI: " + oauthUri.toString());
		}
//		oauthUri = new URIBuilder(authServerBaseUriCallback + authorizationServerIdCallback)
//				.setParameter(AbanteIDConstants.CODE, authCode).setParameter("grant_type", "authorization_code")
//				.setParameter("client_id", appOAuthClientId).setParameter("client_secret", apiKey)
//				.setParameter("redirect_uri", appOAuthRedirectUriCallback).setParameter(AbanteIDConstants.STATE, state)
//				.build().toASCIIString();
		//response.sendRedirect(oauthUri);
		
//		new Thread() {
//	         public void run() {
//	            
//	         }
//	      }.start();
		
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().print("ERROR: En esta pagina no se puede hacer un HTTP POST");
	}

}
