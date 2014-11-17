package Utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.JsonElement;

public class HttpRequests {

	private static Map<String, String> DEFAULT_GET_CONNECTION_PROPERTIES;
	private static Map<String, String> DEFAULT_POST_CONNECTION_PROPERTIES;
	
	static {
		DEFAULT_GET_CONNECTION_PROPERTIES = new HashMap<String, String>();
		DEFAULT_GET_CONNECTION_PROPERTIES.put("User-Agent", "Mozilla/5.0");
		DEFAULT_GET_CONNECTION_PROPERTIES.put("Accept-Language", "en-US,en;q=0.5");
		DEFAULT_GET_CONNECTION_PROPERTIES.put("Accept", "application/json");
		
		DEFAULT_POST_CONNECTION_PROPERTIES = new HashMap<String, String>();
		DEFAULT_POST_CONNECTION_PROPERTIES.putAll(DEFAULT_GET_CONNECTION_PROPERTIES);
		DEFAULT_POST_CONNECTION_PROPERTIES.put("Content-Type", "application/json");
	}

	public static void sendPost(String urlString, Map<String,String> urlProperties, JsonElement elem) throws IOException{
		if (elem.isJsonNull()) 
			return;

		String content = elem.toString();
		URL url = new URL(urlString);
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

		if (urlProperties != null)
			urlProperties.put("Content-Length", "" + content.length());
		
		addPostRequestProperties(connection, urlProperties);
	
		writeBodyContent(connection, content);

		printDebugMessages(connection, "POST");
	}

	public static String sendPost(String urlString, Map<String, String> urlParameters) throws IOException {
		String content = getParametersFromMap(urlParameters);
		URL url = new URL(urlString);
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

		addRequestProperties(connection, DEFAULT_POST_CONNECTION_PROPERTIES);

		writeBodyContent(connection, content);

		printDebugMessages(connection, "POST");

		return retrieveGetResponse(connection);
	}

	public static String sendGet(String urlString, Map<String, String> urlProperties, Map<String, String> urlParameters) throws IOException{
		URL obj = new URL(urlString);
		HttpsURLConnection connection = (HttpsURLConnection) obj.openConnection();

		addGetRequestProperties(connection, urlProperties);

		writeBodyContent(connection, getParametersFromMap(urlParameters));

		printDebugMessages(connection, "GET");
		
		return retrieveGetResponse(connection);
	}
	
	private static void addPostRequestProperties(HttpsURLConnection connection,	Map<String, String> urlProperties) {
		if (urlProperties == null)
			urlProperties = DEFAULT_POST_CONNECTION_PROPERTIES;
		
		addRequestProperties(connection, urlProperties);
	}

	private static void addGetRequestProperties(HttpsURLConnection connection,	Map<String, String> urlProperties) {
		if (urlProperties == null)
			urlProperties = DEFAULT_GET_CONNECTION_PROPERTIES;
		
		addRequestProperties(connection, urlProperties);
	}
	
	private static void addRequestProperties(HttpsURLConnection connection, Map<String, String> urlProperties) {
		for (String key : urlProperties.keySet())
			connection.setRequestProperty(key, urlProperties.get(key));
	}

	private static String retrieveGetResponse(HttpsURLConnection connection) throws IOException {
		BufferedReader in = new BufferedReader( new InputStreamReader ( connection.getInputStream() ) );

		String inputLine = in.readLine();
		StringBuffer response = new StringBuffer();

		while (inputLine != null) {
			response.append(inputLine);
			inputLine = in.readLine();
		}

		in.close();

		return response.toString();
	}

	private static void writeBodyContent(HttpsURLConnection con, String parametersString) throws IOException {
		con.setDoOutput(true);

		if (parametersString != null){
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(parametersString);
			wr.flush();
			wr.close();
		}
	}

	private static String getParametersFromMap(Map<String, String> urlParameters) {
		if (urlParameters == null || urlParameters.size() == 0)
			return null;

		StringBuilder mapParameters = new StringBuilder();
		Iterator<String> parametersIterator = urlParameters.keySet().iterator();

		for(int i = 0 ; i < urlParameters.keySet().size() ; ++i){
			if (!parametersIterator.hasNext())
				break;

			String key = parametersIterator.next();

			if (i == 0)
				mapParameters.append(String.format("%s=%s", key, urlParameters.get(key)));
			else 
				mapParameters.append(String.format("&%s=%s", key, urlParameters.get(key)));
		}

		return mapParameters.toString(); 
	}

	@Deprecated
	private static void printDebugMessages(HttpsURLConnection connection, String requestType) throws IOException {
		int responseCode = connection.getResponseCode();
		System.out.println("Sending " + requestType + " request to URL : " + connection.getURL().getHost()+connection.getURL().getFile());
		System.out.println("Response Code : " + responseCode);
	}
}
