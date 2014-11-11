package Utils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import Core.WebApp;

import com.google.gson.JsonElement;

public class HttpRequests {
	
	public static void sendPost(String urlString, JsonElement elem) throws IOException{
		if (elem.isJsonNull()) 
			return;
		
		String content = elem.toString();
		URL url = new URL(urlString);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Accept", "application/json");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Content-Length", "" + content.length());
		con.setRequestProperty("Authorization", "Bearer " + WebApp.googleToken.getValue());

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(content);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("Sending 'POST' request to URL : " + con.getURL().getHost()+con.getURL().getFile());
		System.out.println("Response Code : " + responseCode);
	}
}
