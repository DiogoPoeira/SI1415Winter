
package Servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@SuppressWarnings("serial")
public class GoogleTasksServlet extends HttpServlet{
	public static final String CLIENT_ID = "914729438211-edj0k8loqmgapc3fv4qc5ij2la8l2niu.apps.googleusercontent.com";
    public static final String CLIENT_SECRET = "BQpi9sd10uwgf8u4G-fclyh8";

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		 resp.setStatus(302);
	     resp.setHeader("Location",
	        		"https://accounts.google.com/o/oauth2/auth?"+
	    		 	"scope=https://www.googleapis.com/auth/tasks"+
	        		"&redirect_uri=http://localhost:8080/gtaskscallback"+
	        		"&client_id="+CLIENT_ID+
	        		"&response_type=code"+
	        		"&approval_prompt=force");

    }
}