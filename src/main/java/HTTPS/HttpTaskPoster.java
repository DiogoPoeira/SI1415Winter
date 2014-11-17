package HTTPS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

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
	
	public void post(List<GoogleTask> tasks) throws IOException, InterruptedException{
		JsonElement tasklist = retriever.lookForTaskList();
		final Map<String, String> urlProperties = new HashMap<String, String>();
		List<JsonElement> elements;
		final Semaphore sem = new Semaphore(0);
		
		urlProperties.put("User-Agent", "Mozilla/5.0");
		urlProperties.put("Accept-Language", "en-US,en;q=0.5");
		urlProperties.put("Accept", "application/json");
		urlProperties.put("Content-Type", "application/json");
		urlProperties.put("Authorization", "Bearer " + WebApp.googleToken.getValue());

		if (tasklist != null)
			elements = retriever.removeDuplicateTasksFromInsertList(tasklist, tasks);
		else {
			tasklist = addNewList(urlProperties);
			elements = tasksToJson(tasks);
		}
		
		final String urlname = "https://www.googleapis.com/tasks/v1/lists/"+((JsonObject)tasklist).get("id").getAsString()+"/tasks";
		
		for(final JsonElement elem : elements){
			Thread posterThread = new Thread( new Runnable() {
				
				@Override
				public void run() {
					try {
						
						HttpRequests.sendPost(urlname, urlProperties, elem);
						sem.release();
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			});
			
			posterThread.start();
		}
		
		sem.acquire(elements.size());
	}

	private JsonElement addNewList(Map<String, String> urlProperties) throws IOException {
		String taskListUrl = "https://www.googleapis.com/tasks/v1/users/@me/lists";
		
		JsonObject taskListElem = new JsonObject();
		taskListElem.add( "kind" , new JsonPrimitive(GoogleTaskList.kind));
		taskListElem.add( "title" , new JsonPrimitive(GoogleTaskList.title));	
		
		HttpRequests.sendPost(taskListUrl, urlProperties, taskListElem);
		
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
