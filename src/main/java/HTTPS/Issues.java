package HTTPS;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import Servlets.GithubCallbackServlet;
import Servlets.GithubServlet;

public class Issues {

	public static void getIssuesFromAuthenticatedGitUser(String accessToken){
		String url = "https://api.github.com/user/email?access_token=" + accessToken;
		try{
			URL obj = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestProperty("AUTHORIZATION","token OAUTH-TOKEN");
			System.out.println(accessToken);
			
			
			String urlParameters = "q=DiogoPoeira";/*"client_id="+GithubServlet.CLIENT_ID+
	        		"&client_secret="+GithubCallbackServlet.CLIENT_SECRET+
	        		"&redirect_uri=http://localhost:8080/githubcallback"+*/
					//"access_token=" + accessToken;

			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();
//			int responseCode = con.getResponseCode();
//			System.out.println("\nSending 'GET' request to URL : " + url);
//			System.out.println("GET parameters : " + urlParameters);
//			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(
					new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			//print result
			System.out.println("derp" + response.toString());
		}
		catch(IOException e){
			System.out.println("asda");//e.printStackTrace();
		}
	}
	
}
