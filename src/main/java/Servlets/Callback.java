package Servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Callback  extends HttpServlet{
	
	private String CLIENT_ID = "914729438211-edj0k8loqmgapc3fv4qc5ij2la8l2niu.apps.googleusercontent.com";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		 System.out.println("HEY STEVE I CALLBACKED.");
	}
}