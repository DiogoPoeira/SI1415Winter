package Servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import HTTPS.AcessToken;

@SuppressWarnings("serial")
public class GithubCallbackServlet extends HttpServlet{
	

    public static final String CLIENT_SECRET = "ee52a7343f436c3c7c3300104468f78574480216";
//pensar em fazer o  doPost e ver como criar Requests e Responses para passar como parametros.
	  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		  String code = req.getParameter("code"),access_token=req.getParameter("access_token");
		  AcessToken.getGitHubAcessToken(code);
		  resp.setStatus(200);
		 /* if(access_token==null)
			  getAccessToken(code,resp);
		  else{
				  System.out.println("Eu chego a ter as cenas que pedi");
				 resp.setContentLength(access_token.getBytes().length);
				 resp.getOutputStream().write(access_token.getBytes());
			     resp.getOutputStream().close();
			     resp.setStatus(200);}*/
		  
	  }

	private void getAccessToken(String code, HttpServletResponse resp) {
		System.out.println("Eu chego a pedir cenas");
		resp.setStatus(200);
		resp.setHeader("Accept", "application/json");
	    resp.setHeader("Location","https://github.com/login/oauth/access_token?"+
	        		"client_id="+GithubServlet.CLIENT_ID+
	        		"&client_secret="+CLIENT_SECRET+
	        		"&redirect_uri=http://localhost:8080/githubcallback"+
	        		"&code="+code);
	}
}
