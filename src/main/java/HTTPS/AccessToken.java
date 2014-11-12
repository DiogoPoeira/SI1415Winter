package HTTPS;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import Servlets.GithubCallbackServlet;
import Servlets.GithubServlet;
import Servlets.GoogleTasksServlet;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class AccessToken {

	private String token,site;
	
	public AccessToken(String value,String site){
		split(value);
		this.site=site;
		System.out.println(this.toString());
	}
	public String toString(){
		return "Token's website : "+site+" => access_token = "+token;
	}
	private void split(String value) {
		JsonParser parser = new JsonParser();
		JsonObject o = (JsonObject)parser.parse(value);
		token = o.get("access_token").getAsString();
	}
	public static AccessToken getGoogleTasksAccessToken(String code) throws IOException{
		String url = "https://accounts.google.com/o/oauth2/token";
		
			URL obj = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
			
			String urlParameters = "code=" + code +
					"&client_id=" + GoogleTasksServlet.CLIENT_ID +
					"&client_secret=" + GoogleTasksServlet.CLIENT_SECRET + 
					"&redirect_uri=http://localhost:8080/gtaskscallback" + 
					"&grant_type=authorization_code";
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();
			
			BufferedReader in = new BufferedReader(
					new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			return new AccessToken(response.toString(),"Google");
			
					
		
					
		
	}
	
	public static AccessToken getGitHubAcessToken(String code){
		String url = "https://github.com/login/oauth/access_token";
		try{
			URL obj = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			con.setRequestProperty("Accept", "application/json");

			String urlParameters = "client_id="+GithubServlet.CLIENT_ID+
	        		"&client_secret="+GithubCallbackServlet.CLIENT_SECRET+
	        		"&redirect_uri=http://localhost:8080/githubcallback"+
	        		"&code="+code;

			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();

			BufferedReader in = new BufferedReader(
					new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			return new AccessToken(response.toString(),"Github");
		}
		catch(IOException e){
			
		}
		return null;
 
	}
	public String getValue(){return token;}

}
