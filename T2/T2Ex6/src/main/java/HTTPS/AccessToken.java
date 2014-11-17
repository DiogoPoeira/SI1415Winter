package HTTPS;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import Servlets.GithubCallbackServlet;
import Servlets.GithubServlet;
import Servlets.GoogleTasksServlet;
import Utils.HttpRequests;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class AccessToken {

	private String token , site;

	public AccessToken(String value,String site){
		split(value);
		this.site=site;
		System.out.println(this.toString());
	}
	
	private void split(String value) {
		JsonParser parser = new JsonParser();
		JsonObject o = (JsonObject)parser.parse(value);
		token = o.get("access_token").getAsString();
	}
	
	public static AccessToken getGoogleTasksAccessToken(String code) throws IOException{
		String url = "https://accounts.google.com/o/oauth2/token";
		Map<String, String> urlParameters = new HashMap<String, String>();
		
		urlParameters.put("code", code);
		urlParameters.put("client_id", GoogleTasksServlet.CLIENT_ID);
		urlParameters.put("client_secret", GoogleTasksServlet.CLIENT_SECRET);
		urlParameters.put("redirect_uri", "http://localhost:8080/gtaskscallback");
		urlParameters.put("grant_type", "authorization_code");

		String getResponse = HttpRequests.sendGet(url, null, urlParameters);
		
		return new AccessToken(getResponse.toString(),"Google");
	}
	
	public static AccessToken getGitHubAcessToken(String code) throws IOException{
		String url = "https://github.com/login/oauth/access_token";
		Map<String, String> urlParameters = new HashMap<String, String>();

		urlParameters.put("client_id", GithubServlet.CLIENT_ID);
		urlParameters.put("client_secret", GithubCallbackServlet.CLIENT_SECRET);
		urlParameters.put("redirect_uri", "http://localhost:8080/githubcallback");
		urlParameters.put("code", code);
		
		String getResponse = HttpRequests.sendGet(url, null, urlParameters);
		
		return new AccessToken(getResponse , "Github");
	}
	
	public String getValue(){
		return token;
	}
	
	public String toString(){
		return "Token's website : " + site + " => access_token = " + token;
	}
}
