package HTTPS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import Core.WebApp;
import Entities.GitHubIssue;
import JSON.Deserializers.IssueDeserializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

public class HttpIssuesRequester {

	private GitHubIssue[] issues;
	private JsonParser jsonParser = new JsonParser();
	private IssueDeserializer deserializer = new IssueDeserializer();

	public GitHubIssue[] getIssuesFromAuthenticatedGitUser() throws IOException{
		
		URL url = new URL("https://api.github.com/user/issues?access_token="+WebApp.githubToken.getValue()+"&state=all&filter=all");
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Accept", "application/json");			

		// Send post request
		con.setDoOutput(true);

		int responseCode = con.getResponseCode();
		System.out.println("Sending 'GET' request to URL : " + url.getHost());
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()));
		String inputLine = in.readLine();
		StringBuffer response = new StringBuffer();

		while ( inputLine != null){
			response.append(inputLine);
			inputLine = in.readLine();
		}
		
		in.close();
		
		setIssueListFromJsonString(response.toString());
		
		return issues;
	}
	
	private void setIssueListFromJsonString(String resp) {
		JsonArray issueArray = jsonParser.parse(resp).getAsJsonArray();
		issues = new GitHubIssue[issueArray.size()];
		

		for (int i = 0; i < issueArray.size() ; ++i){
			issues[i] = deserializer.deserialize(issueArray.get(i), GitHubIssue.class, null);
			//System.out.println(issues[i]);
		}
	}
	
	public GitHubIssue[] getIssues(){
		return issues;
	}
}
