package com.ptellos.application;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.utils.URIBuilder;

import com.google.gson.Gson;
import com.ptellos.dao.DAORegisterApplication;

/**
 * Servlet implementation class ApplicationRegister
 * 
 */

@WebServlet("/AltaEnAPI/*")
public class ApplicationRegistering extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = Logger.getLogger("ApplicationRegistering");
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
		LOGGER.log(Level.INFO, "doGet()");
		Map<String, Object> map = new HashMap<String, Object>();
		String path = request.getRequestURI();

		if (path.equals("/" + Constants.PATH_APPLICATION + "/" + Constants.PATH_ALTAENAPI + "/"
				+ Constants.PATH_CONFIRMATION)) {
			LOGGER.log(Level.INFO, "El path es: " + path);
			// response.sendRedirect("http://localhost:8080//ApplicationOAuth2/altaEnAPI");
			String exist = URLDecoder.decode(request.getParameter(Constants.PARAMETER_EXIST), "UTF-8");

			if (exist != null) {
				if (exist.equals("true")) {
					map.put("exist", exist);
					write(response, map);
					// request.setAttribute("exist", exist);
					// request.getRequestDispatcher("/altaEnAPI.jsp").forward(request, response);
				} else if (exist.equals("false")) {
					String clientId = URLDecoder.decode(request.getParameter(Constants.CLIENT_ID), "UTF-8");
					String clientSecret = URLDecoder.decode(request.getParameter(Constants.CLIENT_SECRET), "UTF-8");
					String url_redirect = URLDecoder.decode(request.getParameter(Constants.REDIRECT), "UTF-8");
					try {
						DAORegisterApplication.registerApplication(url_redirect, Constants.CODE_SECRET_VALUE, clientId,
								clientSecret);
					} catch (Exception e) {
						e.printStackTrace();
					}

					map.put("exist", exist);
					write(response, map);
					// request.setAttribute("exist", exist);
					// request.getRequestDispatcher("/altaEnAPI.jsp").forward(request, response);
				}
			}
		} else {
			// Este será el caso NO se puede dar
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.log(Level.INFO, "doPost()");
		String path = request.getRequestURI();
		// if (path.equals("/ApplicationOAuth2/AltaEnAPI")) {
		if (path.equals("/" + Constants.PATH_APPLICATION + "/" + Constants.PATH_ALTAENAPI)) {
			LOGGER.log(Level.INFO, "El path es: " + path);
			String oauthUri = "";
			try {
				// oauthUri =
				// http://localhost:8080/APIOAuth2/Register?url_redirect=http://localhost:8080/ApplicationOAuth2&code_secret=NDUwODM5Nzg=
				oauthUri = new URIBuilder().setScheme(Constants.SCHEME).setHost(Constants.HOST).setPort(Constants.PORT)
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
		}
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
