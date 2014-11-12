package Servlets;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class HomepageServlet extends HttpServlet{

	private String PATH = "resources/homepage.html";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		StringBuffer homePageContent = new StringBuffer();
		
		try (BufferedReader br = new BufferedReader(new InputStreamReader(HomepageServlet.class.getClassLoader().getResourceAsStream(PATH))); ){
			
			String aux = br.readLine();
			
			while (aux != null){
				homePageContent.append(aux);
				aux = br.readLine();
			}
		}
		
		DataOutputStream wr = new DataOutputStream(resp.getOutputStream());
		System.out.println(homePageContent.toString());
		wr.writeBytes(homePageContent.toString());
		wr.flush();
		wr.close();
		
		resp.setContentLength(homePageContent.length());
		resp.setStatus(200);
	}
}
