package HTTPS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import App.WebApp;
import Entities.GoogleTask;
import Entities.GoogleTaskList;
import JSON.Deserializers.GoogleTaskDeserializer;
import JSON.Serializers.GoogleTaskSerializer;
import Utils.HttpRequests;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class HttpGoogleTaskListRetriever {
	
	private GoogleTaskDeserializer googledeserializer = new GoogleTaskDeserializer();
	private GoogleTaskSerializer googleserializer = new GoogleTaskSerializer();
	private JsonParser jsonParser = new JsonParser();
	
	public JsonElement lookForTaskList() throws IOException{
		String url = "https://www.googleapis.com/tasks/v1/users/@me/lists";
		Map<String, String> urlProperties = new HashMap<String,String>();
		
		urlProperties.put("User-Agent", "Mozilla/5.0");
		urlProperties.put("Accept-Language", "en-US,en;q=0.5");
		urlProperties.put("Accept", "application/json");	
		urlProperties.put("Authorization", "Bearer " + WebApp.googleToken.getValue());

		String getResponse = HttpRequests.sendGet(url, urlProperties, null);
		
		return getTaskListsFromJsonString(getResponse);
	}

	private JsonElement getTaskListsFromJsonString(String resp) {
		JsonArray tasklistArray = jsonParser.parse(resp).getAsJsonObject().get("items").getAsJsonArray();
		JsonElement elem;
		for (int i = 0; i < tasklistArray.size() ; ++i){
			elem = tasklistArray.get(i);
			if(!elem.isJsonNull()&&((JsonObject) elem).get("title").getAsString().equals(GoogleTaskList.title))
				return elem;
		}
		return null;
				
	}

	public List<JsonElement> removeDuplicateTasksFromInsertList(JsonElement tasklist, List<GoogleTask> tasks) throws IOException {
		String urlString = "https://www.googleapis.com/tasks/v1/lists/"+((JsonObject) tasklist).get("id").getAsString()+
									"/tasks?access_token="+WebApp.googleToken.getValue();
		
		String getResponse = HttpRequests.sendGet(urlString, null, null);

		return filterTasks(tasks, getResponse);
	}

	private List<JsonElement> filterTasks(List<GoogleTask> tasks, String resp) {
		JsonArray taskArray = jsonParser.parse(resp).getAsJsonObject().get("items").getAsJsonArray();
		List<JsonElement> tasksfound = new ArrayList<JsonElement>();
		List<GoogleTask> existingTasks = new ArrayList<GoogleTask>();
		
		for(int i = 0;i<taskArray.size();i++)
			existingTasks.add(googledeserializer.deserialize(taskArray.get(i),GoogleTask.class,null));

		for (GoogleTask task: tasks){
			if(!existingTasks.contains(task))
				tasksfound.add(googleserializer.serialize(task, GoogleTask.class, null));
		}
		
		return tasksfound;
	}
}
