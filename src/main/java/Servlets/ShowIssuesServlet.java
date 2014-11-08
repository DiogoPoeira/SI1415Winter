package Servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ShowIssuesServlet extends HttpServlet{
	
	private String CLIENT_ID = "914729438211-edj0k8loqmgapc3fv4qc5ij2la8l2niu.apps.googleusercontent.com";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		 System.out.println("--New request was received --");
	        System.out.println(req.getRequestURI()); 
	        System.out.println(req.getMethod());
	        System.out.println(req.getHeader("Accept"));
	        
	        resp.setStatus(302);
	        resp.setHeader("Location",
	          "https://github.com/lcduarte/SITestRepo/issues?"+
	          "scope=https://github.com/settings/applications/143837&"+
	          //state=some.security.state&
	          "redirect_uri=http://localhost:8080/callback&"+
	          "response_type=code&"+
	          "client_id="+CLIENT_ID +
	          "&approval_prompt=force");
	}
	
}
