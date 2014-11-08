package Servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Core.WebApp;
import HTTPS.AccessToken;

@SuppressWarnings("serial")
public class GoogleTasksCallbackServlet extends HttpServlet {

	public static final String CLIENT_SECRET = "BQpi9sd10uwgf8u4G-fclyh8";

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		String code = req.getParameter("code");
		System.out.println("c" + code);
		  WebApp.googleToken = AccessToken.getGoogleTasksAccessToken(code);
		resp.setStatus(200);
	}
}