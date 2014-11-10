package HTTPS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Core.WebApp;
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
	
	public void post(List<GoogleTask> tasks) throws IOException{
		JsonElement tasklist = retriever.lookForTaskList();
		List<JsonElement> elements; 
		if (tasklist != null)
			elements = retriever.removeDuplicateTasksFromInsertList(tasklist, tasks);
		else{
			tasklist = addNewList();
			elements= tasksToJson(tasks);
		}
		String urlname = "https://www.googleapis.com/tasks/v1/lists/"+((JsonObject)tasklist).get("id").getAsString()+"/tasks";	
		for(JsonElement elem : elements)
			HttpRequests.sendPost(urlname, elem);
	}

	private JsonElement addNewList() throws IOException {
		String taskListUrl = "https://www.googleapis.com/tasks/v1/users/@me/lists";
		JsonObject taskListElem = new JsonObject();
		taskListElem.add( "kind" , new JsonPrimitive(GoogleTaskList.kind));
		taskListElem.add( "title" , new JsonPrimitive(GoogleTaskList.title));	
		HttpRequests.sendPost(taskListUrl, taskListElem);
		return retriever.lookForTaskList();
	}

	private List<JsonElement> tasksToJson(List<GoogleTask> tasks) {
		List<JsonElement> elements = new ArrayList<JsonElement>();
		for(GoogleTask g: tasks){
			elements.add(serializer.serialize(g, GoogleTask.class, null));
		}
		return elements;
	}
}
