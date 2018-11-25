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
import com.ptellos.dao.DAOUnsubscribeApplication;

/**
 * Servlet implementation class ApplicationUnsubscribe
 */

@WebServlet("/BajaEnAPI/*")
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
		System.out.println("ApplicationUnsubscribe - doGet()");
		Map<String, Object> map = new HashMap<String, Object>();
		String path = request.getRequestURI();
		if (path.equals("/ApplicationOAuth2/BajaEnAPI/Confirmation")) {
			// response.sendRedirect("http://localhost:8080//ApplicationOAuth2/altaEnAPI");
			String exist = URLDecoder.decode(request.getParameter("exist"), "UTF-8");
			if (exist != null) {
				if (exist.equals("true")) {
					String url_redirect = URLDecoder.decode(request.getParameter("url_redirect"), "UTF-8");
					try {
						DAOUnsubscribeApplication.unsubscribeApplication(url_redirect);
					} catch (Exception e) {
						e.printStackTrace();
					}

					map.put("exist", exist);
					write(response, map);
				} else if (exist.equals("false")) {
					map.put("exist", exist);
					write(response, map);
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
		System.out.println("ApplicationUnsubscribe - doPost()");
		String path = request.getRequestURI();
		if (path.equals("/ApplicationOAuth2/BajaEnAPI")) {
			String oauthUri = "";

			// Logear
			try {
				// oauthUri =
				// http://localhost:8080/APIOAuth2/Unsubscribe?url_redirect=http://localhost:8080/ApplicationOAuth2&code_secret=NDUwODM5Nzg=
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
