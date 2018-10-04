package com.ptellos.application;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.utils.URIBuilder;

/**
 * Servlet implementation class ApplicationRegister
 * 
 */

@WebServlet("/AltaEnAPI")
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
		/*
		 * EN URL, 2. PASOS CONTINUOS. 05 Explico el problema que había
		 */
		String oauthUri = "";

		// Logear
		System.out.println("Accedemos a la construcción de la URI para el registro de la application");
		try {
			oauthUri = new URIBuilder().setScheme(Constants.SCHEME).setHost(Constants.HOST).setPort(Constants.PORT)
					.setPath("/" + Constants.PATH_API + "/" + Constants.PATH_REGISTER)
					.setParameter(Constants.REDIRECT, Constants.REDIRECT_APPLICATION)
					// Este parametro debería estar codificado con una clave AES
					.setParameter(Constants.CODE_SECRET, Constants.CODE_SECRET_VALUE).build().toASCIIString();
		} catch (URISyntaxException e) {
			/*
			 * logger.debug("Ha ocurrido un error al dar de alta la aplicación en la API: "
			 * + e); Activar cuando dispongamos de logger
			 */
			e.printStackTrace();
		}
		/*
		 * Esto habría que ver si se podría modificarlo para que funcione porque
		 * necesito controlar la respuesta
		 * 
		 * peticionOAuth2(oauthUri);
		 */
		response.sendRedirect(oauthUri);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().print("ERROR: En esta pagina no se puede hacer un HTTP POST");
	}

	public void peticionOAuth2(String oauthUri) {
		String responseString = "";
		String outputString = "";
		String wsURL = oauthUri;
		URL url = null;
		URLConnection connection = null;
		HttpURLConnection httpConn = null;
		try {
			url = new URL(wsURL);
			connection = url.openConnection();
			httpConn = (HttpURLConnection) connection;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// Set the appropriate HTTP parameters.
		httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
		try {
			httpConn.setRequestMethod("GET");
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		httpConn.setDoOutput(true);
		httpConn.setDoInput(true);
		OutputStream out = null;
		try {
			out = httpConn.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		InputStreamReader isr = null;
		try {
			isr = new InputStreamReader(httpConn.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedReader in = null;
		if (isr != null) {
			in = new BufferedReader(isr);
		}
		// Write the SOAP message response to a String.
		if (in != null) {
			try {
				while ((responseString = in.readLine()) != null) {
					outputString = outputString + responseString;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
