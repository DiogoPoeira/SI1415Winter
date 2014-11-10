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
		String status = null;
		DateTime dateOfCompletion = DateTimeUtils.getDateTimeFromString(issue.closed_at); 
		
		if (issue.state != null)
			status = issue.state.equalsIgnoreCase("open") ? "needsAction" : "completed";
		
		return new GoogleTask(issue.title, issue.body, status, dateOfCompletion);
	}
}
