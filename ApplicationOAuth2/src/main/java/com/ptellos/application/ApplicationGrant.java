package com.ptellos.application;

import java.io.BufferedReader;
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
import org.json.JSONException;
import org.json.JSONObject;

import com.google.api.client.util.IOUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ptellos.dao.DAOValueApplicationRequest;

/**
 * Servlet implementation class ApplicationRegister
 * 
 */

@WebServlet("/GrantAuthorization")
public class ApplicationGrant extends HttpServlet {
	private final static Logger LOGGER = Logger.getLogger("ApplicationGrant");
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
		LOGGER.log(Level.INFO, "doGet()");
		// ogger.debug("ApplicationGrant - doGet()");
		String path = request.getRequestURI();
		LOGGER.log(Level.INFO, "El path es: " + path);
		// logger.debug("El path es: " + path);

		if (path.equals("/" + Constants.PATH_APPLICATION + "/" + Constants.PATH_GRANT)) {
			LOGGER.log(Level.INFO, "doGet: Envío de primer contacto.");
			// logger.debug("ApplicationGrant - doGet: Envío de primer contacto.");;
			String oauthUri = "";
			String client_id = null;
			try {
				client_id = DAOValueApplicationRequest.returnValue(Constants.CLIENT_ID);
				if (client_id != null) {
					// oauthUri =
					// http://localhost:8080/APIOAuth2/GrantAuthorization?response_type=code&client_id=CLIENT_ID&redirect_uri=CALLBACK_URL&scope=read
					oauthUri = new URIBuilder().setScheme(Constants.SCHEME).setHost(Constants.HOST)
							.setPort(Constants.PORT)
							.setPath("/" + Constants.PATH_API + "/" + Constants.PATH_GRANT + "/"
									+ Constants.PATH_GRANT_FIRST)
							.setParameter(Constants.RESPONSE_TYPE, Constants.RESPONSE_TYPE_PARAM)
							.setParameter(Constants.CLIENT_ID, client_id)
							.setParameter(Constants.REDIRECT_URI,
									Constants.REDIRECT_APPLICATION + "/" + Constants.PATH_RESPONSE_AUTHORIZATION)
							.setParameter(Constants.SCOPE, Constants.SCOPE_PARAM).build().toASCIIString();
				}
			} catch (URISyntaxException e) {
				/*
				 * logger.debug("Ha ocurrido un error al dar de alta la aplicación en la API: "
				 * + e); Activar cuando dispongamos de logger
				 */
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			LOGGER.log(Level.INFO, "Llamamos a: \n" + oauthUri);
			response.sendRedirect(oauthUri);
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
		LOGGER.log(Level.INFO, "El path es: " + path);
		JSONObject jsonObj = null;
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = request.getReader();

		try {
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line).append('\n');
			}
		} finally {
			reader.close();
		}
		try {
			jsonObj = new JSONObject(sb.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			// Esto es correcto.
			LOGGER.log(Level.INFO, "El contenido del POST en JSON es: " + sb.toString() + "\n JSON: "
					+ jsonObj.getString("access_token"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
