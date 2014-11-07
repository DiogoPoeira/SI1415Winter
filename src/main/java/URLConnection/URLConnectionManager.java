package URLConnection;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class URLConnectionManager {
	
	public static  HttpsURLConnection getConnection(String urlname){
		try {
		    URL myURL = new URL(urlname);
		    HttpsURLConnection myURLConnection = (HttpsURLConnection) myURL.openConnection();
		    myURLConnection.connect();
		    return myURLConnection;
		} 
		catch (MalformedURLException e) { 
		    // new URL() failed
		    // ...
		} 
		catch (IOException e) {   
		    // openConnection() failed
		    // ...
		}
		return null;
	}

}
