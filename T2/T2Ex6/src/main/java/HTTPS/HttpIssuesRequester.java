package HTTPS;

import java.io.IOException;

import App.WebApp;
import Entities.GitHubIssue;
import JSON.Deserializers.IssueDeserializer;
import Utils.HttpRequests;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

public class HttpIssuesRequester {

	private GitHubIssue[] issues;
	private JsonParser jsonParser = new JsonParser();
	private IssueDeserializer deserializer = new IssueDeserializer();

	public GitHubIssue[] getIssuesFromAuthenticatedGitUser() throws IOException{

		String urlString = "https://api.github.com/user/issues?access_token="+WebApp.githubToken.getValue()+"&state=all&filter=all";

		String getResponse = HttpRequests.sendGet(urlString, null, null);
		
		setIssueListFromJsonString(getResponse);
		
		return issues;
	}
	
	private void setIssueListFromJsonString(String resp) {
		JsonArray issueArray = jsonParser.parse(resp).getAsJsonArray();
		issues = new GitHubIssue[issueArray.size()];
		
		for (int i = 0; i < issueArray.size() ; ++i){
			issues[i] = deserializer.deserialize(issueArray.get(i), GitHubIssue.class, null);
		}
	}
	
	public GitHubIssue[] getIssues(){
		return issues;
	}
}
