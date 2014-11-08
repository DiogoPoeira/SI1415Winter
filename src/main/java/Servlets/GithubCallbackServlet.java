package Servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Core.WebApp;
import HTTPS.AccessToken;

@SuppressWarnings("serial")
public class GithubCallbackServlet extends HttpServlet{
	

    public static final String CLIENT_SECRET = "ee52a7343f436c3c7c3300104468f78574480216";
    
	  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		  String code = req.getParameter("code"),access_token=req.getParameter("access_token");
		  WebApp.githubToken = AccessToken.getGitHubAcessToken(code);
		  resp.setStatus(200);
	  }
}
