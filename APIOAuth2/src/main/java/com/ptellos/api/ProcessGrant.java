package com.ptellos.api;

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
import com.ptellos.dao.DAOLogin;
import com.ptellos.dao.DAOSetAuthorizationCode;

/**
 * Servlet implementation class ProcessGrant
 */
@WebServlet("/ProcessGrant/*")
public class ProcessGrant extends HttpServlet {
	private final static Logger LOGGER = Logger.getLogger("ProcessGrant");
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProcessGrant() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		LOGGER.log(Level.INFO, "ProcessGrant mediante GET");
		String client_id = URLDecoder.decode(request.getParameter(Constants.CLIENT_ID), "UTF-8");
		String redirect_uri = URLDecoder.decode(request.getParameter(Constants.REDIRECT_URI), "UTF-8");
		LOGGER.log(Level.INFO, "client_id: " + client_id + "\t request_uri: " + redirect_uri);
		String authorization_code = new RandomStringGenerator().nextString();
		//Aquí hay que comprobar primero si existe el clientId antes de meterlo ya que si no nos podrían meter cualquier cosa.
		try {
			if (DAOSetAuthorizationCode.existApp(client_id)) {
				LOGGER.log(Level.INFO, "La aplicación está dada de alta y tiene autorización");
				try {
					//TODO: Aquí primero tenemos que comprobar si ya existe para no saturar la BBDD.
					DAOSetAuthorizationCode.setApplication(client_id, authorization_code);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String oauthUri = "";
				try {
					oauthUri = new URIBuilder()
							.setPath(redirect_uri)
							.setParameter(Constants.RESPONSE_TYPE_PARAM, authorization_code)
							.setParameter(Constants.CLIENT_ID, client_id)
							.build().toASCIIString();
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
				LOGGER.log(Level.INFO, "Llamamos a: \n" + oauthUri);
				response.sendRedirect(oauthUri);
			} else {
				LOGGER.log(Level.INFO, "La aplicación NO está dada de alta y NO tiene autorización");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.log(Level.INFO, "ProcessGrant mediante POST");
		String nombre = request.getParameter("username");
		String password = request.getParameter("password");
		Map<String, Object> map = new HashMap<String, Object>();
		LOGGER.log(Level.INFO, "Username: " + nombre + "\tPassword: " + password);
		boolean isValidUser = false; 
		try {
			if (DAOLogin.existUser(nombre, password)) {
				LOGGER.log(Level.INFO, "El usuario se encuentra dado de alta en la API");
				isValidUser = true;
			} else {
				LOGGER.log(Level.INFO, "El usuario no se encuentra dado de alta en la API");
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error de acceso a DAOLogin: " + e);
		}		
		map.put("isValidUser", isValidUser);
		write(response, map);
		
	}
	
	private void write(HttpServletResponse response, Map<String, Object> map) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(new Gson().toJson(map));
	}

}
