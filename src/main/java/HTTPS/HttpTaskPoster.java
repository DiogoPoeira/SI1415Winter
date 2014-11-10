package HTTPS;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import Core.WebApp;
import Entities.GoogleTask;
import Entities.GoogleTaskList;
import JSON.Serializers.GoogleTaskSerializer;

import com.google.gson.JsonElement;

public class HttpTaskPoster {
	
	private GoogleTaskSerializer serializer = new GoogleTaskSerializer();
	private HttpGoogleTaskListRetriever retriever = new HttpGoogleTaskListRetriever();
	
	public void getIssuesFromAuthenticatedGitUser(List<GoogleTask> tasks, GoogleTaskList gl) throws IOException{
		String urlname = "https://www.googleapis.com/tasks/v1/lists/tasklist/tasks?access_token="+WebApp.googleToken.getValue();
		JsonElement tasklist = retriever.lookForTaskList(tasks);
		List<JsonElement> elements; 
		if (tasklist != null)
			elements = retriever.removeDuplicateTasksFromInsertList(tasklist, tasks);
		else{
			addNewList();
			elements= tasksToJson(tasks);
		}
		URL url = new URL(urlname);
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", "Mozilla/5.0");
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Accept", "application/json");			

		// Send post request

		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		String body;//json dos elements lista cens
		wr.writeBytes(body);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("Sending 'POST' request to URL : " + url.getHost());
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()));
		String inputLine = in.readLine();
		StringBuffer response = new StringBuffer();

		while ( inputLine != null){
			response.append(inputLine);
			inputLine = in.readLine();
		}
		
		in.close();
	}

	private void addNewList() {
		
	}

	private List<JsonElement> tasksToJson(List<GoogleTask> tasks) {
		List<JsonElement> elements = new ArrayList<JsonElement>();
		for(GoogleTask g: tasks){
			elements.add(serializer.serialize(g, GoogleTask.class, null));
		}
		return elements;
	}
}
