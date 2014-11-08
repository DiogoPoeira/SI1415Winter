package Servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class GithubTokenServlet  extends HttpServlet {
	
	 public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		 String code = req.getParameter("access_token"); 
		 resp.getOutputStream().write(code.getBytes());
	     resp.getOutputStream().close();
	     resp.setStatus(200);
	 }
}
