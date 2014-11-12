package Servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Core.WebApp;
import Entities.GitHubIssue;
import Entities.GoogleTask;
import HTTPS.AccessToken;
import HTTPS.HttpIssuesRequester;
import HTTPS.HttpTaskPoster;
import Utils.IssueToTaskListConverter;

@SuppressWarnings("serial")
public class GoogleTasksCallbackServlet extends HttpServlet {

	public static final String CLIENT_SECRET = "BQpi9sd10uwgf8u4G-fclyh8";
	public static final HttpTaskPoster poster = new HttpTaskPoster();
    

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		String code = req.getParameter("code");
		WebApp.googleToken = AccessToken.getGoogleTasksAccessToken(code);
		resp.setStatus(200);
		HttpIssuesRequester issuesRequester = new HttpIssuesRequester();
        GitHubIssue[] issues = issuesRequester.getIssuesFromAuthenticatedGitUser();
        List<GoogleTask> tasks = IssueToTaskListConverter.convertList(issues);
        poster.post(tasks);
        resp.sendRedirect("https://mail.google.com/tasks/canvas?list=GitHubIssues");
	}
}