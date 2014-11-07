
import java.security.cert.X509Certificate;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;


public class Execute {
	//https://www.google.pt
	public static void main(String [] args) throws SSLPeerUnverifiedException{
		Scanner in = new Scanner(System.in);
		System.out.println("Please insert the desired URL!");
		String urlname = in.nextLine();
		HttpsURLConnection connection = Utils.getConnection(urlname);
		X509Certificate[] certificates = (X509Certificate[])connection.getServerCertificates();
		System.out.println("Valid from : "+certificates[0].getNotBefore().toString()+"\n\tto :"+certificates[0].getNotAfter().toString()+"\n");
		Utils.printHostNameValidity(certificates[0]);
		Utils.printCertificates(certificates);
		System.out.println("Earliest expiration date on the chain: "+Utils.earliestExpirationCertificate(certificates).toString()+"\n");
		System.out.println("\n************************************************************************ \n");

		connection.disconnect();
		in.close();
	}


}
