package HTTPS;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

import Servlets.AuxGitHubCallbackServlet;
import Servlets.AuxGitHubServlet;
import Servlets.GithubCallbackServlet;
import Servlets.GithubServlet;
import Servlets.GithubTokenServlet;
import Servlets.GoogleTasksCallbackServlet;
import Servlets.GoogleTasksServlet;
import Servlets.HomepageServlet;
import Servlets.ShowIssuesServlet;

public class HttpsServer {
	
	public static final int LISTEN_PORT = 8080;
	private Server server;
	private ServletHandler handler;
	
	public HttpsServer(){
		server = new Server(LISTEN_PORT);
		handler = new ServletHandler();
        server.setHandler(handler);
        handler.addServletWithMapping(HomepageServlet.class, "/");
        handler.addServletWithMapping(AuxGitHubServlet.class, "/showgithubissues");
        handler.addServletWithMapping(AuxGitHubCallbackServlet.class, "/githubcallback/show");
        handler.addServletWithMapping(ShowIssuesServlet.class, "/githubissues");
        handler.addServletWithMapping(GithubServlet.class, "/importissues");
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
