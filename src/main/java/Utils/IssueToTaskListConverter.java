package Utils;

import org.joda.time.DateTime;

import Entities.GitHubIssue;
import Entities.GoogleTask;

public class IssueToTaskListConverter {
	
	public static GoogleTask[] convertList(GitHubIssue[] issueList){
		GoogleTask[] taskList = new GoogleTask[issueList.length];
		
		for (int i = 0 ; i < issueList.length ; ++i){
			taskList[i] = convertSingle(issueList[i]);
		}
		
		return taskList;
	}

	private static GoogleTask convertSingle(GitHubIssue issue) {
		String[] days, secs;
		String status = null;
		DateTime dateOfCompletion = null;
		
		if (issue.state != null)
			status = issue.state.equalsIgnoreCase("open") ? "needsAction" : "completed";
		
		
		if (issue.closed_at != null){
			days = issue.closed_at.substring(0, issue.closed_at.indexOf("T")).split("-");
			secs = issue.closed_at.substring(issue.closed_at.indexOf("T")+1 , issue.closed_at.length() - 1).split(":");
			
			dateOfCompletion = new DateTime(Integer.parseInt(days[0]), 
					Integer.parseInt(days[1]),
					Integer.parseInt(days[2]),
					Integer.parseInt(secs[0]),
					Integer.parseInt(secs[1]),
					Integer.parseInt(secs[2]), 
					0);
		}
		
		
		return new GoogleTask(issue.title, issue.body, status, dateOfCompletion);
	}

}
