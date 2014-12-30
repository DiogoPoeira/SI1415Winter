package App;

import PEP.Http.Server.HttpsServer;

public class Program {
	
	public static void main(String[] args) throws Exception{
		//System.out.println(new String(PEP.PEP.getResource(new Action("APP"), "res/file3.txt")));
		
		HttpsServer server = new HttpsServer();
        server.start();
        System.out.println("Server is started");        
        
        System.in.read();
        server.stop();
        System.out.println("Server is stopped, bye"); 
	}
}
