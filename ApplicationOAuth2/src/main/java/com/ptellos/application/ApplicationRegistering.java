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
		String oauthUri = "";
		
		System.out.println("Accedemos a la construcción de la URI");
		try {
			oauthUri = new URIBuilder()
					.setScheme(Constants.SCHEME)
					.setHost(Constants.HOST)
					.setPort(Constants.PORT)
					.setPath("/" + Constants.URL_API)
					.setParameter(Constants.REDIRECT, Constants.REDIRECT_APPLICATION)
					.build().toASCIIString();
		} catch (URISyntaxException e) {
			/*
			 * logger.debug("Ha ocurrido un error al dar de alta la aplicación en la API: " + e);
			 * Activar cuando dispongamos de logger
			 */
			e.printStackTrace();
		}
		response.sendRedirect(oauthUri);
		/*
		 * Aquí vamos a esperar hasta que la API nos confirme aprobada o rechazada la application
		 */
		
//		new Thread() {
//	         public void run() {
//	            
//	         }
//	      }.start();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().print("ERROR: En esta pagina no se puede hacer un HTTP POST");
	}

}
