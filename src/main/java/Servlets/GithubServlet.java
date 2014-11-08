package Servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class GithubServlet extends HttpServlet{

	public static final String CLIENT_ID = "f65766878f3d2eebd683";
    
	@Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		 resp.setStatus(302);
	     resp.setHeader("Location","https://github.com/login/oauth/authorize?"+
	        		"client_id="+CLIENT_ID+
	        		"&redirect_uri=http://localhost:8080/githubcallback"+
	        		"&scope=user:email+repo+read:org+is:private"+
	        		"&response_type=code");
	}
}
