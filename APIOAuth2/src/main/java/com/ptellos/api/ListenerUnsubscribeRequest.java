package com.ptellos.api;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String application = URLDecoder.decode(request.getParameter("url_redirect"), "UTF-8");
		String secretKey = URLDecoder.decode(request.getParameter("code_secret"), "UTF-8");

		try {
			if (DAOUnsubscribeApplication.existApp(application, secretKey)) {
				// Aquí al existir la app no se si decir algo como "ya estaba dada de alta" o no
				// decir nada.
				DAOUnsubscribeApplication.unsubscribeApplication(application, secretKey);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
