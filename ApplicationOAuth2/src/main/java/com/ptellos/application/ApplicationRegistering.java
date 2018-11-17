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

/**
 * Servlet implementation class ApplicationRegister
 * 
 */

@WebServlet("/AltaEnAPI/*")
public class ApplicationRegistering extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
		System.out.println("ApplicationRegistering - doGet()");
		Map<String, Object> map = new HashMap<String, Object>();
		String path = request.getRequestURI();
		if(path.equals("/ApplicationOAuth2/AltaEnAPI/Confirmation")) {
			//response.sendRedirect("http://localhost:8080//ApplicationOAuth2/altaEnAPI");
			String exist = URLDecoder.decode(request.getParameter("exist"), "UTF-8");
			if (exist != null) {
				map.put("exist", exist);
				write(response, map);
				//request.setAttribute("exist", exist);
				//request.getRequestDispatcher("/altaEnAPI.jsp").forward(request, response); 
			} 
		} else {
			//Este ser� el caso NO se puede dar
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {		
		System.out.println("ApplicationRegistering - doPost()");
		
		String path = request.getRequestURI();
		System.out.println("El path es: " + path);
		if (path.equals("/ApplicationOAuth2/AltaEnAPI")) {
			String oauthUri = "";
			// Logear
			System.out.println("Entramos normal");
			try {
				oauthUri = new URIBuilder()
						.setScheme(Constants.SCHEME)
						.setHost(Constants.HOST)
						.setPort(Constants.PORT)
						.setPath("/" + Constants.PATH_API + "/" + Constants.PATH_REGISTER)
						.setParameter(Constants.REDIRECT, Constants.REDIRECT_APPLICATION)
						// Este parametro deber�a estar codificado con una clave AES
						.setParameter(Constants.CODE_SECRET, Constants.CODE_SECRET_VALUE).build().toASCIIString();
			} catch (URISyntaxException e) {
				/*
				 * logger.debug("Ha ocurrido un error al dar de alta la aplicaci�n en la API: "
				 * + e); Activar cuando dispongamos de logger
				 */
				e.printStackTrace();
			}
			response.sendRedirect(oauthUri);
		}
	}
	
	/**
	 * Metodo que tramita la petici�n de vuelta del Ajax correspondiente
	 * @param response Respuesta HTTP
	 * @param map Mapa con los clave-valor que hayamos metido.
	 * @throws IOException
	 */
	private void write(HttpServletResponse response, Map<String, Object> map) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(new Gson().toJson(map));
	}
}
