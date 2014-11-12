package HTTPS;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

import Servlets.GithubCallbackServlet;
import Servlets.GithubServlet;
import Servlets.GithubTokenServlet;
import Servlets.GoogleTasksCallbackServlet;
import Servlets.GoogleTasksServlet;

public class HttpsServer {
	
	public static final int LISTEN_PORT = 8080;
	private Server server;
	private ServletHandler handler;
	
	public HttpsServer(){
		server = new Server(LISTEN_PORT);
		handler = new ServletHandler();
        server.setHandler(handler);
        handler.addServletWithMapping(HomepageServlet.class, "/");
        handler.addServletWithMapping(GithubServlet.class, "/github");
        handler.addServletWithMapping(GoogleTasksServlet.class, "/gtasks");
        handler.addServletWithMapping(GoogleTasksCallbackServlet.class, "/gtaskscallback");
        handler.addServletWithMapping(GithubCallbackServlet.class, "/githubcallback");
        handler.addServletWithMapping(GithubTokenServlet.class, "/githubtoken");       
	}

	public void start() throws Exception {
		server.start();
	}
	
	public void stop() throws Exception {
		server.stop();
	}

}
