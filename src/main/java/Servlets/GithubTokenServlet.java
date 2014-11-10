package Servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Core.WebApp;

@SuppressWarnings("serial")
public class GithubTokenServlet extends HttpServlet {
	
	 public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		 resp.setStatus(302);
	     resp.setHeader("Location","https://api.github.com/user/issues?"
	    		 +"access_token="+WebApp.githubToken.getValue()+
	    		 "&state=all"+
	    		 "&filter=all");
	 }
}
