package HTTPS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Entities.GoogleTask;
import Entities.GoogleTaskList;
import JSON.Serializers.GoogleTaskSerializer;
import Utils.HttpRequests;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class HttpTaskPoster {
	
	private GoogleTaskSerializer serializer = new GoogleTaskSerializer();
	private HttpGoogleTaskListRetriever retriever = new HttpGoogleTaskListRetriever();
	
	public void getIssuesFromAuthenticatedGitUser(List<GoogleTask> tasks, GoogleTaskList gl) throws IOException{
		String urlname = "https://www.googleapis.com/tasks/v1/lists/tasklist/tasks";
		JsonElement tasklist = retriever.lookForTaskList(tasks);
		List<JsonElement> elements =  null;
		
		if (tasklist != null)
			elements = retriever.removeDuplicateTasksFromInsertList(tasklist, tasks);
		else {
			addNewList();
			elements = tasksToJson(tasks);
		}
		
		for(JsonElement elem : elements){
			HttpRequests.sendPost("", elem);
		}
	}

	private void addNewList() throws IOException {
		String taskListUrl = "";
		JsonObject taskListElem = new JsonObject();
		taskListElem.add( "kind" , new JsonPrimitive("tasks#taskList"));
		taskListElem.add( "title" , new JsonPrimitive("GitHubIssues"));
		
		HttpRequests.sendPost(taskListUrl, taskListElem);
	}

	private List<JsonElement> tasksToJson(List<GoogleTask> tasks) {
		List<JsonElement> elements = new ArrayList<JsonElement>();
		for(GoogleTask g: tasks){
			elements.add(serializer.serialize(g, GoogleTask.class, null));
		}
		return elements;
	}
}
