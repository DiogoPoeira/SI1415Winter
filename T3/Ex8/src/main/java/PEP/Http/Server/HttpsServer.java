package PEP.Http.Server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

import PEP.Http.Servlets.Servlet;

public class HttpsServer {
	
	public static final int LISTEN_PORT = 8080;
	private Server server;
	private ServletHandler handler;
	
	public HttpsServer(){
		server = new Server(LISTEN_PORT);
		handler = new ServletHandler();
        server.setHandler(handler);
        handler.addServletWithMapping(Servlet.class, "/");
	}

	public void start() throws Exception {
		server.start();
	}
	
	public void stop() throws Exception {
		server.stop();
	}
}
