package com.ptellos.application;

import java.io.IOException;
import java.util.HashMap;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.ptellos.dao.DAOLogin;

@WebServlet("/login_usuario")
public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = Logger.getLogger("LoginServlet");
	
	public LoginServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Aqui sera escribir un mensaje de error normal.
		response.getWriter().print("ERROR: En esta pagina no se puede hacer un HTTP GET");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean isValid = false;
		boolean goodFormat = false;
		String username = request.getParameter("username");
		if (username != null && username.trim().length() != 0) {
			goodFormat = true;
			if (checkUser(request)) {
				successUser(request, response);
				isValid = true;
			} else {
				map.put("username", username);
			}
		}
		/*
		 * Esto lo vamos a usar siempre y cuando necesitemos que un resultado vuelva al
		 * front map.put("username", username); write(response, map);
		 */
		map.put("goodFormat", goodFormat);
		map.put("isValid", isValid);
		write(response, map);
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

	public boolean checkUser(HttpServletRequest request) {
		boolean existUser = false;
		String nombre = request.getParameter("username");
		String password = request.getParameter("password");
		try {
			if (DAOLogin.existUser(nombre, password)) {
				existUser = true;
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error: " + e);
		}
		return existUser;
	}

	public void successUser(HttpServletRequest request, HttpServletResponse response) {
		String contextPath = request.getContextPath();// We need this path to set cookie's path.
		Cookie[] cookies = request.getCookies();
		Cookie cookieToProcess = null;
		for (Cookie cookie : cookies) {
			if ("profileUrl".equals(cookie.getName())) {
				cookieToProcess = cookie;
				break;
			}
		}
		if (cookieToProcess == null) {
			// No hay cookie.
			// Esto ocurre cuando un usuario entra al sitio por primera vez o tiene
			// deshabilitadas las cookies.
			// En este caso creamos a nueva cookie.
			String cookieName = "profileUrl";
			String cookieValue = request.getParameter("username");
			Cookie newCookie = new Cookie(cookieName, cookieValue);
			newCookie.setPath(contextPath);
			response.addCookie(newCookie);
		} else {
			String cookieValue = cookieToProcess.getValue();// Retrieve value from the cookie.
			// TODO: Activar LOGGER y mostrar la cookie en modo debug
		}
	}
}