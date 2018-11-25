package com.ptellos.api;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.utils.URIBuilder;

import com.ptellos.dao.DAORegisterApplication;

/**
 * Servlet implementation class ListenRequest
 */

@WebServlet("/Register")
public class ListenerRegistrationRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListenerRegistrationRequest() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("ListenerRegistrationRequest - doGet()");
		String application = URLDecoder.decode(request.getParameter("url_redirect"), "UTF-8");
		String secretKey = URLDecoder.decode(request.getParameter("code_secret"), "UTF-8");

		boolean exist = true;
		try {
			if (DAORegisterApplication.existApp(application, secretKey)) {
				// Aquí al existir la app no se si decir algo como "ya estaba dada de alta" o no
				// decir nada.
				String oauthUri = setURIExist(exist);
				response.sendRedirect(oauthUri);
			} else {
				/*
				 * Al no existir generamos un client_id y un client_secret clientId: Nombre
				 * identificador de la aplicación. clientSecret: Password identificador de la
				 * aplicación.
				 */
				String clientId = new RandomStringGenerator().nextString();
				String clientSecret = new RandomStringGenerator(23).nextString();
				DAORegisterApplication.registerApplication(application, secretKey, clientId, clientSecret);
				exist = false;
				String oauthUri = setURINotExist(exist, clientId, clientSecret);
				response.sendRedirect(oauthUri);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("ListenerRegistrationRequest - doPost()");
		doGet(request, response);
	}

	private String setURIExist(boolean exist) {
		System.out.println("ListenerRegistrationRequest - setURIExist()");
		String oauthUri = null;
		// Si existia
		try {
			// oauthUri =
			// http://localhost:8080/ApplicationOAuth2/AltaEnAPI/Confirmation?exist=true
			oauthUri = new URIBuilder().setScheme(Constants.SCHEME).setHost(Constants.HOST).setPort(Constants.PORT)
					.setPath("/" + Constants.PATH_APPLICATION + "/" + Constants.PATH_ALTAENAPI + "/"
							+ Constants.PATH_CONFIRMATION)
					.setParameter(Constants.PARAMETER_EXIST, "true").build().toASCIIString();
		} catch (URISyntaxException e) {
			/*
			 * logger.debug("Ha ocurrido un error al dar de alta la aplicación en la API: "
			 * + e); Activar cuando dispongamos de logger
			 */
			e.printStackTrace();
		}
		return oauthUri;
	}

	private String setURINotExist(boolean exist, String clientId, String clientSecret) {
		System.out.println("ListenerRegistrationRequest - setURINotExist()");
		String oauthUri = null;
		// Si no existia
		try {
			// oauthUri =
			// http://localhost:8080/ApplicationOAuth2/AltaEnAPI/Confirmation?exist=false&clientId=*&clientSecret=*&url_redirect=http://localhost:8080/APIOAuth2
			String url_redirect = Constants.SCHEME + "://" + Constants.HOST + ":" + Constants.PORT + "/"
					+ Constants.PATH_API;
			oauthUri = new URIBuilder().setScheme(Constants.SCHEME).setHost(Constants.HOST).setPort(Constants.PORT)
					.setPath("/" + Constants.PATH_APPLICATION + "/" + Constants.PATH_ALTAENAPI + "/"
							+ Constants.PATH_CONFIRMATION)
					.setParameter(Constants.PARAMETER_EXIST, "false").setParameter(Constants.CLIENT_ID, clientId)
					.setParameter(Constants.CLIENT_SECRET, clientSecret).setParameter(Constants.REDIRECT, url_redirect)
					.build().toASCIIString();
		} catch (URISyntaxException e) {
			/*
			 * logger.debug("Ha ocurrido un error al dar de alta la aplicación en la API: "
			 * + e); Activar cuando dispongamos de logger
			 */
			e.printStackTrace();
		}
		return oauthUri;
	}

}
