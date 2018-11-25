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

import com.ptellos.dao.DAOUnsubscribeApplication;

/**
 * Servlet implementation class ListenerUnsubscribeRequest
 */

@WebServlet("/Unsubscribe")
public class ListenerUnsubscribeRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListenerUnsubscribeRequest() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("ListenerUnsubscribeRequest - doGet()");
		String application = URLDecoder.decode(request.getParameter("url_redirect"), "UTF-8");
		String secretKey = URLDecoder.decode(request.getParameter("code_secret"), "UTF-8");

		boolean exist = true;
		try {
			if (DAOUnsubscribeApplication.existApp(application, secretKey)) {
				// Si existe la desuscribimos
				DAOUnsubscribeApplication.unsubscribeApplication(application, secretKey);
				String oauthUri = setURI(exist);
				response.sendRedirect(oauthUri);
			} else {
				exist = false;
				String oauthUri = setURI(exist);
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
		System.out.println("ListenerUnsubscribeRequest - doPost()");
		doGet(request, response);
	}

	private String setURI(boolean exist) {
		System.out.println("ListenerUnsubscribeRequest - setURI()");
		String oauthUri = null;
		if (exist) {
			// Si existia
			try {
				// oauthUri =
				// http://localhost:8080/ApplicationOAuth2/BajaEnAPI/Confirmation?exist=true&url_redirect=http://localhost:8080/APIOAuth2
				String url_redirect = Constants.SCHEME + "://" + Constants.HOST + ":" + Constants.PORT + "/"
						+ Constants.PATH_API;
				oauthUri = new URIBuilder().setScheme(Constants.SCHEME).setHost(Constants.HOST).setPort(Constants.PORT)
						.setPath("/" + Constants.PATH_APPLICATION + "/" + Constants.PATH_BAJAENAPI + "/"
								+ Constants.PATH_CONFIRMATION)
						.setParameter(Constants.PARAMETER_EXIST, "true").setParameter(Constants.REDIRECT, url_redirect)
						.build().toASCIIString();
			} catch (URISyntaxException e) {
				/*
				 * logger.debug("Ha ocurrido un error al dar de alta la aplicación en la API: "
				 * + e); Activar cuando dispongamos de logger
				 */
				e.printStackTrace();
			}
		} else {
			// Si no existia
			try {
				// oauthUri =
				// http://localhost:8080/ApplicationOAuth2/BajaEnAPI/Confirmation?exist=false
				oauthUri = new URIBuilder().setScheme(Constants.SCHEME).setHost(Constants.HOST).setPort(Constants.PORT)
						.setPath("/" + Constants.PATH_APPLICATION + "/" + Constants.PATH_BAJAENAPI + "/"
								+ Constants.PATH_CONFIRMATION)
						.setParameter(Constants.PARAMETER_EXIST, "false").build().toASCIIString();
			} catch (URISyntaxException e) {
				/*
				 * logger.debug("Ha ocurrido un error al dar de alta la aplicación en la API: "
				 * + e); Activar cuando dispongamos de logger
				 */
				e.printStackTrace();
			}
		}

		return oauthUri;
	}

}
