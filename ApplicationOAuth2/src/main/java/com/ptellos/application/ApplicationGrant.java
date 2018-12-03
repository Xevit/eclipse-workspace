package com.ptellos.application;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.utils.URIBuilder;

import com.google.gson.Gson;
import com.ptellos.dao.DAORegisterApplication;
import com.ptellos.dao.DAOValueApplicationRequest;

/**
 * Servlet implementation class ApplicationRegister
 * 
 */

@WebServlet("/GrantAuthorization/*")
public class ApplicationGrant extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ApplicationGrant() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("ApplicationGrant - doGet()");
		Map<String, Object> map = new HashMap<String, Object>();
		String path = request.getRequestURI();
		System.out.println("El path es: " + path);
		//Primera comunicacion
		if (path.equals("/" + Constants.PATH_APPLICATION + "/" + Constants.PATH_GRANT)) {
			String oauthUri = "";
			try {
				//Tenemos que buscar en BBDD el client_Id
				DAOValueApplicationRequest.returnValue(Constants.CLIENT_ID);
				// oauthUri =
				// http://localhost:8080/APIOAuth2/GrantAuthorization?response_type=code&client_id=CLIENT_ID&redirect_uri=CALLBACK_URL&scope=read
				oauthUri = new URIBuilder().setScheme(Constants.SCHEME).setHost(Constants.HOST).setPort(Constants.PORT)
						.setPath("/" + Constants.PATH_API + "/" + Constants.PATH_GRANT)
						.setParameter(Constants.RESPONSE_TYPE, Constants.RESPONSE_TYPE_PARAM)
						.setParameter(Constants.CLIENT_ID, "")
						.setParameter(Constants.REDIRECT_URI, Constants.REDIRECT_APPLICATION)
						.setParameter(Constants.SCOPE, Constants.SCOPE_PARAM)
						.build().toASCIIString();
			} catch (URISyntaxException e) {
				/*
				 * logger.debug("Ha ocurrido un error al dar de alta la aplicación en la API: "
				 * + e); Activar cuando dispongamos de logger
				 */
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			response.sendRedirect(oauthUri);
		} else if () {
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("ApplicationGrant - doPost()");

		String path = request.getRequestURI();
		System.out.println("El path es: " + path);
	}

	/**
	 * Metodo que tramita la petición de vuelta del Ajax correspondiente
	 * 
	 * @param response
	 *            Respuesta HTTP
	 * @param map
	 *            Mapa con los clave-valor que hayamos metido.
	 * @throws IOException
	 */
	private void write(HttpServletResponse response, Map<String, Object> map) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(new Gson().toJson(map));
	}
}
