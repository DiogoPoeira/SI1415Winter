package Servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import App.WebApp;
import HTTPS.AccessToken;

@SuppressWarnings("serial")
public class AuxGitHubCallbackServlet  extends HttpServlet{

	public static final String CLIENT_SECRET = "ee52a7343f436c3c7c3300104468f78574480216";

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		String code = req.getParameter("code");
		WebApp.githubToken = AccessToken.getGitHubAcessToken(code);
		resp.setStatus(200);
		resp.sendRedirect("http://localhost:8080/githubissues");
	}
}
