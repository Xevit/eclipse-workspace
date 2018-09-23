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
 * Servlet implementation class ApplicationRegister
 * 
 */

@WebServlet(name = "ApplicationRegistering", urlPatterns = { "AltaEnAPI" })
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
		String oauthUri = "";
		
		try {
			oauthUri = new URIBuilder(Constantes.DNS + Constantes.URL_API)
					.setParameter(Constantes.NOMBRE, Constantes.NOMBRE_APPLICATION)
					.build().toASCIIString();
		} catch (URISyntaxException e) {			
			e.printStackTrace();
		}
//		oauthUri = new URIBuilder(authServerBaseUriCallback + authorizationServerIdCallback)
//				.setParameter(AbanteIDConstants.CODE, authCode).setParameter("grant_type", "authorization_code")
//				.setParameter("client_id", appOAuthClientId).setParameter("client_secret", apiKey)
//				.setParameter("redirect_uri", appOAuthRedirectUriCallback).setParameter(AbanteIDConstants.STATE, state)
//				.build().toASCIIString();
		response.sendRedirect(oauthUri);
		
		new Thread() {
	         public void run() {
	            
	         }
	      }.start();
		
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
