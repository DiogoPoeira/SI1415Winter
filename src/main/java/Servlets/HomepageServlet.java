package Servlets;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class HomepageServlet extends HttpServlet{

	private Map<String,String> resources = new HashMap<String, String>();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String content, requestPath = "resources" + URLDecoder.decode(req.getRequestURI(),"UTF-8");

		if (requestPath.equals("resources/"))
			requestPath = "resources/homepage.html";
		
		if (resources.containsKey(requestPath))
			content = resources.get(requestPath);
		else {
			StringBuffer homePageContent = new StringBuffer();
			
			InputStream inputStream = HomepageServlet.class.getClassLoader().getResourceAsStream(requestPath);
			
			if (inputStream == null)
				inputStream = HomepageServlet.class.getClassLoader().getResourceAsStream("resources/homepage.html");
			
			try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream)); ){

				String aux = br.readLine();

				while (aux != null){
					homePageContent.append(aux);
					aux = br.readLine();
				}
			}
			
			content = homePageContent.toString();
			resources.put(requestPath, content);
		}
		
		DataOutputStream wr = new DataOutputStream(resp.getOutputStream());
		wr.writeBytes(content.toString());
		wr.flush();
		wr.close();

		resp.setContentLength(content.length());
		resp.setStatus(200);
	}
}
