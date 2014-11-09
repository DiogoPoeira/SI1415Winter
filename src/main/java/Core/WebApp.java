package Core;
import com.google.gson.JsonElement;

import Entities.GitHubIssue;
import Entities.GoogleTask;
import HTTPS.AccessToken;
import HTTPS.HttpsServer;
import HTTPS.HttpIssuesRequester;
import JSON.Serializers.GoogleTaskSerializer;
import Utils.IssueToTaskListConverter;

public class WebApp {
	
	public static volatile AccessToken githubToken,googleToken;
	
	public static void main(String [] args) throws Exception{
		HttpsServer server = new HttpsServer();
        server.start();
        System.out.println("Server is started");
        while(githubToken==null);
        
        HttpIssuesRequester issuesRequester = new HttpIssuesRequester();
        GitHubIssue[] issues = issuesRequester.getIssuesFromAuthenticatedGitUser();
        
        GoogleTask[] tasks = IssueToTaskListConverter.convertList(issues);
        
        for (GoogleTask task : tasks){
        	JsonElement elem = new GoogleTaskSerializer().serialize(task, GoogleTask.class, null);
        	System.out.println(elem);
        }
        
        System.in.read();
        server.stop();
        System.out.println("Server is stopped, bye"); 
	}

}
