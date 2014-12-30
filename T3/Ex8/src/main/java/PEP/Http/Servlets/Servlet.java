package PEP.Http.Servlets;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URLDecoder;

import PDP.DataTypes.Action;
import PEP.PEP;
import PEP.Exceptions.UnauthorizedAccess;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class Servlet extends HttpServlet{

	private static Action httpAction = new Action("HTTP");
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		byte[] content;
		String resourceId = URLDecoder.decode(req.getRequestURI(),"UTF-8").substring(1);

		try {
			content = PEP.getResource(httpAction, resourceId);
			resp.setStatus(200);
		} catch (UnauthorizedAccess e){
			content = ("ERRO :\n" + e.getMessage()).getBytes();
			resp.setStatus(403);
		}
		
		DataOutputStream wr = new DataOutputStream(resp.getOutputStream());
		wr.writeBytes(new String(content));
		wr.flush();
		wr.close();

		resp.setContentLength(content.length);
	}
}
