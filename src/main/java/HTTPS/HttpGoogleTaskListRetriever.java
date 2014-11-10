package HTTPS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import Core.WebApp;
import Entities.GoogleTask;
import Entities.GoogleTaskList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class HttpGoogleTaskListRetriever {
	
	private GoogleTaskDeserializer googledeserializer = new GoogleTaskDeserializer();
	private JsonParser jsonParser = new JsonParser();
	
	public JsonElement lookForTaskList(List<GoogleTask> tasks) throws IOException{
		
		URL url = new URL("https://www.googleapis.com/tasks/v1/users/@me/lists?access_token="+WebApp.googleToken.getValue());
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Accept", "application/json");			

		// Send post request
		con.setDoOutput(true);

		int responseCode = con.getResponseCode();
		System.out.println("Sending 'GET' request to URL : " + url.getHost());
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()));
		String inputLine = in.readLine();
		StringBuffer response = new StringBuffer();

		while ( inputLine != null){
			response.append(inputLine);
			inputLine = in.readLine();
		}
		in.close();
		
		return getTaskListsFromJsonString(response.toString());
		
	}

	private JsonElement getTaskListsFromJsonString(String resp) {
		JsonArray tasklistArray = jsonParser.parse(resp).getAsJsonArray();
		JsonElement elem;
		for (int i = 0; i < tasklistArray.size() ; ++i){
			elem = tasklistArray.get(i);
			if(!elem.isJsonNull()&&((JsonObject) elem).get("title").equals(GoogleTaskList.title))
				return elem;
		}
		return null;
				
	}

	public List<JsonElement> removeDuplicateTasksFromInsertList(JsonElement tasklist, List<GoogleTask> tasks) throws IOException {
		URL url = new URL("https://www.googleapis.com/tasks/v1/lists/"+((JsonObject) tasklist).get("id").getAsString()+"/tasks?access_token="+WebApp.googleToken.getValue());
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Accept", "application/json");			

		// Send post request
		con.setDoOutput(true);

		int responseCode = con.getResponseCode();
		System.out.println("Sending 'GET' request to URL : " + url.getHost());
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()));
		String inputLine = in.readLine();
		StringBuffer response = new StringBuffer();

		while ( inputLine != null){
			response.append(inputLine);
			inputLine = in.readLine();
		}
		in.close();
		
		return filterTasks(tasks,response.toString());
	}

	private List<JsonElement> filterTasks(List<GoogleTask> tasks, String resp) {
		JsonArray taskArray = jsonParser.parse(resp).getAsJsonArray();
		List<JsonElement> tasksfound = new ArrayList<JsonElement>();
		GoogleTask aux;
		JsonElement elem;

		for (int i = 0; i < taskArray.size() ; ++i){
			elem = taskArray.get(i);
			aux= googledeserializer.deserialize(elem,GoogleTask.class,null);
			if(!tasks.contains(aux))
				tasksfound.add(elem);
		}
		return tasksfound;
	}

}
