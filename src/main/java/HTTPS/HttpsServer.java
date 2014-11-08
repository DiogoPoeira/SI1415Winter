package HTTPS;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

import Servlets.GithubCallbackServlet;
import Servlets.GithubServlet;
import Servlets.GithubTokenServlet;
import Servlets.GoogleTasksServlet;

public class HttpsServer {
	
	public static final int LISTEN_PORT = 8080;
	
	public static void main(String [] args) throws Exception{
		Server server = new Server(LISTEN_PORT);
		ServletHandler handler = new ServletHandler();
        server.setHandler(handler);
        handler.addServletWithMapping(GithubServlet.class, "/github");
        handler.addServletWithMapping(GoogleTasksServlet.class, "/gtasks");
        handler.addServletWithMapping(GithubCallbackServlet.class, "/githubcallback");
        handler.addServletWithMapping(GithubTokenServlet.class, "/githubtoken");
        
        server.start();
        System.out.println("Server is started");
        
        System.in.read();
        server.stop();
        System.out.println("Server is stopped, bye");        
	}

}
