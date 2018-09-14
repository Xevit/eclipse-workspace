package com.ptellos.aplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return existUser;
	}

	public void successUser(HttpServletRequest request, HttpServletResponse response) {
		String contextPath = request.getContextPath();// We need this path to set cookie's path.
		Cookie[] cookies = request.getCookies();
		Cookie cookieToProcess = null;
		for (Cookie cookie : cookies) {
			// Buscamos la cookie que necesitamos.
			// Esta es la forma original aunque me voy a quedar con la abreviada de momento
			// Aqui deberiamos ver si la cookie actual esta en BBDD
			// if ("profileUrl".equals(cookie.getName()) &&
			// "/userName".equals(cookie.getPath())) {
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
		}
	}
}