package com.ptellos.application;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.ptellos.dao.DAORegistro;

@WebServlet(name = "RegisterServlet", urlPatterns = { "registra_usuario" })
public class RegisterServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().print("ERROR: En esta página no se puede hacer un HTTP GET");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		boolean userExist = false;
		boolean goodFormat = false;
		String username = request.getParameter("username");
		if (username != null && username.trim().length() != 0) {
			goodFormat = true;
			if (checkUser(request)) {
				// Si el usuario existe avisamos de que el usuario ya existe
				userExist = true;
			} else {
				// Si el usuario no existe le registramos
				setUser(request);
			}
		}
		map.put("goodFormat", goodFormat);
		map.put("userExist", userExist);
		write(response, map);
	}

	private void write(HttpServletResponse response, Map<String, Object> map) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(new Gson().toJson(map));
	}

	public boolean checkUser(HttpServletRequest request) {
		boolean existe = false;
		try {
			if (DAORegistro.existUser(request.getParameter("username"))) {
				existe = true;
			}
		} catch (SQLException esql) {
			//TODO: En todos estos catch activar LOGGER
			System.out.println("Error en SQLException");
		} catch (ClassNotFoundException eclass) {
			System.out.println("Error en ClassNotFoundException");
		} catch (Exception e) {
			System.out.println("Error general");
		}
		return existe;
	}

	public void setUser(HttpServletRequest request) {
		try {
			DAORegistro.setUser(request);
		} catch (SQLException esql) {
			//TODO: En todos estos catch activar LOGGER
			System.out.println("Error en SQLException");
		} catch (ClassNotFoundException eclass) {
			System.out.println("Error en ClassNotFoundException");
		}
	}

}