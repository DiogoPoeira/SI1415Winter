package Core;
import HTTPS.AccessToken;
import HTTPS.HttpsServer;


public class WebApp {
	
	public static AccessToken githubToken,googleToken;
	
	public static void main(String [] args) throws Exception{
		HttpsServer server = new HttpsServer();
        server.start();
        System.out.println("Server is started");
        while(githubToken==null);
        server.stop();
        System.out.println("Server is stopped, bye"); 
	}

}
