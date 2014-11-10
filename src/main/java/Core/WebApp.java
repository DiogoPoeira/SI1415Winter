package Core;
import java.util.List;

import Entities.GitHubIssue;
import Entities.GoogleTask;
import HTTPS.AccessToken;
import HTTPS.HttpIssuesRequester;
import HTTPS.HttpTaskPoster;
import HTTPS.HttpsServer;
import Utils.IssueToTaskListConverter;

public class WebApp {
	
	public static volatile AccessToken githubToken,googleToken;
	
	public static void main(String [] args) throws Exception{
		HttpsServer server = new HttpsServer();
        server.start();
        System.out.println("Server is started");
        while(googleToken==null);
        
        HttpIssuesRequester issuesRequester = new HttpIssuesRequester();
        GitHubIssue[] issues = issuesRequester.getIssuesFromAuthenticatedGitUser();
        List<GoogleTask> tasks = IssueToTaskListConverter.convertList(issues);
        HttpTaskPoster poster = new HttpTaskPoster();
        poster.post(tasks);
        
        System.in.read();
        server.stop();
        System.out.println("Server is stopped, bye"); 
	}

}
