package Servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Core.WebApp;

@SuppressWarnings("serial")
public class ShowIssuesServlet extends HttpServlet{

	//private String CLIENT_ID = "914729438211-edj0k8loqmgapc3fv4qc5ij2la8l2niu.apps.googleusercontent.com";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setStatus(302);
		resp.setHeader("Location", "https://api.github.com/user/issues?access_token="+WebApp.githubToken.getValue()+"&state=all&filter=all");
	}

}
