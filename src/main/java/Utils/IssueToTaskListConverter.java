package Utils;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import Entities.GitHubIssue;
import Entities.GoogleTask;

public class IssueToTaskListConverter {
	
	public static List<GoogleTask> convertList(GitHubIssue[] issueList){
		List<GoogleTask> taskList = new ArrayList<GoogleTask>();
		
		for (GitHubIssue g : issueList){
			taskList.add(convertSingle(g));
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
