package Core;
import HTTPS.AccessToken;
import HTTPS.HttpsServer;
import HTTPS.Issues;


public class WebApp {
	
	public static AccessToken githubToken,googleToken;
	
	public static void main(String [] args) throws Exception{
		HttpsServer server = new HttpsServer();
        server.start();
        System.out.println("Server is started");
        while(githubToken==null);
        //Issues.getIssuesFromAuthenticatedGitUser(WebApp.githubToken.getValue());
        System.in.read();
        server.stop();
        System.out.println("Server is stopped, bye"); 
	}

}
