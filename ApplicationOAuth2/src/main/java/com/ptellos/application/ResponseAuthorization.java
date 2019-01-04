package com.ptellos.application;

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

import com.ptellos.dao.DAOSearch;

/**
 * Servlet implementation class ResponseAuthorization
 */
@WebServlet("/ResponseAuthorization")
public class ResponseAuthorization extends HttpServlet {
	private final static Logger LOGGER = Logger.getLogger("ResponseAuthorization");
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResponseAuthorization() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.log(Level.INFO, "Entramos  mediante GET");
		String authorization_code = URLDecoder.decode(request.getParameter(Constants.RESPONSE_TYPE_PARAM), "UTF-8");
		String client_id = URLDecoder.decode(request.getParameter(Constants.CLIENT_ID), "UTF-8");
		LOGGER.log(Level.INFO, "El código de autorización es: " + authorization_code + " client_id: " + client_id);
		String oauthUri = "";
		try {
			if (DAOSearch.existClient(client_id)) {
				String client_secret = DAOSearch.returnClientSecret(client_id);
				//Buscamos en BBDD el client_Secret
				oauthUri = new URIBuilder().setScheme(Constants.SCHEME).setHost(Constants.HOST).setPort(Constants.PORT)
						.setPath("/" + Constants.PATH_API + "/" + Constants.PATH_GRANT)
						.setParameter(Constants.CLIENT_ID, client_id)
						.setParameter(Constants.CLIENT_SECRET, client_secret)
						.setParameter(Constants.RESPONSE_TYPE_PARAM, authorization_code)
						.setParameter(Constants.REDIRECT_URI, Constants.REDIRECT_APPLICATION + "/" + Constants.PATH_GRANT)
						.build().toASCIIString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.log(Level.INFO, "Llamamos a: \n" + oauthUri);
		response.sendRedirect(oauthUri);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOGGER.log(Level.INFO, "Entramos  mediante POST");
		doGet(request, response);
	}

}
