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

public class AccessToken {

	private String token,type,scope;
	
	public AccessToken(String value){
		split(value);
	}

	private void split(String value) {
		String[] tokenParts = value.split(","),pairs;
		pairs = tokenParts[0].split(":");
		token = pairs[1].replace("\"", "");
		
		
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
			System.out.println("estouaqui.");
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
			System.out.println(response.toString());
			return new AccessToken(response.toString());
			
					
		
					
		
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

//			int responseCode = con.getResponseCode();
//			System.out.println("\nSending 'POST' request to URL : " + url);
//			System.out.println("Post parameters : " + urlParameters);
//			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(
					new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			System.out.println(response.toString());
			return new AccessToken(response.toString());
		}
		catch(IOException e){
			
		}
		return null;
 
	}
	public String getValue(){return token;}

}
