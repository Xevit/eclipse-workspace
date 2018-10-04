package com.ptellos.api;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		/*
		 * PRUEBA:
		 * 
		 * String queryCompleta = request.getQueryString();
		 * response.getWriter().append("Served at: ").append(queryCompleta);
		 * 
		 */
		// Extraemos los parámetros de la query
		// String queryCompleta = URLDecoder.decode(request.getQueryString(), "UTF-8");

		String application = URLDecoder.decode(request.getParameter("url_redirect"), "UTF-8");
		String secretKey = URLDecoder.decode(request.getParameter("code_secret"), "UTF-8");

		try {
			if (DAORegisterApplication.existApp(application, secretKey)) {
				// Aquí al existir la app no se si decir algo como "ya estaba dada de alta" o no
				// decir nada.
			} else {
				DAORegisterApplication.registerApplication(application, secretKey);
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
		doGet(request, response);
	}

}
