package com.ptellos.api;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.utils.URIBuilder;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.ptellos.dao.DAOInsert;
import com.ptellos.dao.DAOSearch;
import com.ptellos.dao.DAOSearchApplication;

@WebServlet("/GrantAuthorization/*")
public class ListenerGrant extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = Logger.getLogger("ListenerGrant");

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.log(Level.INFO, "doGet()");
		String path = request.getRequestURI();
		LOGGER.log(Level.INFO, "El path es: " + path);
		if (path.equals("/" + Constants.PATH_API + "/" + Constants.PATH_GRANT + "/" + Constants.PATH_GRANT_FIRST)) {
			LOGGER.log(Level.INFO, "Entramos por: " + Constants.PATH_GRANT_FIRST);
			String response_type = URLDecoder.decode(request.getParameter(Constants.RESPONSE_TYPE), "UTF-8");
			String client_id = URLDecoder.decode(request.getParameter(Constants.CLIENT_ID), "UTF-8");
			String redirect_uri = URLDecoder.decode(request.getParameter(Constants.REDIRECT_URI), "UTF-8");
			String scope = URLDecoder.decode(request.getParameter(Constants.SCOPE), "UTF-8");
			// Ahora comprobaremos si en la BBDD se ha dado de alta este usuario con este
			// client_id
			if ((response_type != null) && (client_id != null) && (redirect_uri != null) && (scope != null)) {
				LOGGER.log(Level.INFO, "Parametros: " + " Response_Type: " + response_type + " Client_Id: " + client_id
						+ " Redirect_Uri: " + redirect_uri + " Scope: " + scope);
				boolean exist = false;
				if (response_type.equals("code")) {
					try {
						exist = DAOSearchApplication.existApp(client_id, redirect_uri);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						LOGGER.log(Level.SEVERE, "Error en el acceso a la BBDD: " + e);
					}
					if (exist) {
						LOGGER.log(Level.INFO,
								"La aplicación dispone de permisos para acceder a la concesión de autorización");
						// Tenemos que pasarle el redirect_uri y el client_id
						String cID = request.getParameter(Constants.CLIENT_ID);
						String rURI = request.getParameter(Constants.REDIRECT_URI);
						// http://localhost:8080/APIOAuth2/login.jsp
						response.sendRedirect(Constants.SCHEME + "://" + Constants.HOST + ":" + Constants.PORT + "/"
								+ Constants.PATH_API + "/" + "login.jsp" + "?" + Constants.CLIENT_ID + "=" + cID + "&"
								+ Constants.REDIRECT_URI + "=" + rURI);
					} else {
						LOGGER.log(Level.WARNING,
								"La aplicación no dispone de permisos para acceder a la concesión de autorización");
						response.sendRedirect(Constants.SCHEME + "://" + Constants.HOST + ":" + Constants.PORT + "/"
								+ Constants.PATH_API + "/" + "loginOff.html");
					}
				}
			}
		} else if (path
				.equals("/" + Constants.PATH_API + "/" + Constants.PATH_GRANT + "/" + Constants.PATH_GRANT_SECOND)) {
			// http://localhost:8080/APIOAuth2/GrantAuthorization/SecondStep?client_id=&client_secret=&code=&redirect_uri=
			LOGGER.log(Level.INFO, "Entramos por: " + Constants.PATH_GRANT_SECOND);
			// Comprobamos si el client_id, el client_secret y el code (authorización) se
			// corresponden entre sí
			String client_id = request.getParameter("client_id");
			String client_secret = request.getParameter("client_secret");
			String authorization_code = request.getParameter("code");
			String redirect_uri = request.getParameter("redirect_uri");
			// String oauthUri = "";
			try {
				String access_token = new RandomStringGenerator().nextString();
				String refresh_token = new RandomStringGenerator().nextString();
				long expires_in = System.currentTimeMillis() + 3600000;
				LOGGER.log(Level.INFO, "Tiempo actual: " + expires_in);
				if (DAOSearch.existApp(client_id, authorization_code, client_secret)) {
					// Guardamos todos los valores en apioa2_application_record. Esta parte llamamos
					// a POST

					DAOInsert.setCorrectGrant(client_id, client_secret, access_token, refresh_token, expires_in);
					String URI = URLDecoder.decode(redirect_uri, "UTF-8");
					responsePost(URI, access_token, expires_in, refresh_token);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// redirección a página para cerrar la ventana.
			LOGGER.log(Level.INFO, "Finalizamos el proceso en la parte de la API");
			response.sendRedirect(Constants.SCHEME + "://" + Constants.HOST + ":" + Constants.PORT + "/"
					+ Constants.PATH_API + "/" + "endAuthorization.jsp"); 
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
	}

	/**
	 * Procesamiento del envío del POST a la API cuando todas las autenticaciones
	 * son satisfactorias
	 * 
	 * @param redirect_uri
	 * @param access_token
	 * @param expires_in
	 * @param refresh_token
	 * @throws IOException
	 */
	// Esta es la parte más importante del código.
	public void responsePost(String redirect_uri, String access_token, long expires_in, String refresh_token)
			throws IOException {
		LOGGER.log(Level.INFO, "responsePost");
		HttpTransport httpTransport = new NetHttpTransport();
		HttpRequestFactory requestFactory = httpTransport.createRequestFactory();
		// generate the REST based URL
		GenericUrl url = new GenericUrl(redirect_uri);
		// make POST request
		String requestBody = "{'" + Constants.ACCESS_TOKEN + "': '" + access_token + "'," + "'" + Constants.TOKEN_TYPE
				+ "': '" + Constants.TOKEN_TYPE_VALUE + "'," + "'" + Constants.EXPIRES_IN + "': '" + expires_in + ""
				+ "'," + "'" + Constants.REFRESH_TOKEN + "': '" + refresh_token + "'," + "'" + Constants.SCOPE + "': '"
				+ Constants.SCOPE_VALUE + "'}";
		HttpRequest request = requestFactory.buildPostRequest(url,
				ByteArrayContent.fromString("application/json", requestBody));
		request.getHeaders().setContentType("application/json");
		// Google servers will fail to process a POST/PUT/PATCH unless the
		// Content-Length
		// header >= 1
		// request.setAllowEmptyContent(false);
		LOGGER.log(Level.INFO, "responsePost: HttpRequest request: " + requestBody);
		request.execute();
		// HttpResponse response = request.execute();
	}

}
