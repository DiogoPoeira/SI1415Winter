import java.security.cert.Certificate;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

import URLConnection.URLConnectionManager;


public class Execute {
	
	public static void main(String [] args) throws SSLPeerUnverifiedException{
		Scanner in = new Scanner(System.in);
		System.out.println("Please insert the desired URL!");
		int valid = 0;
		String urlname = in.nextLine();
		HttpsURLConnection connection = URLConnectionManager.getConnection(urlname);
		Certificate[] certificates = connection.getServerCertificates();
		X509Certificate earliestExpirationDateCert = null;
		for(Certificate c : certificates){
			//System.out.println(c.toString());
			//https://www.google.pt
			if(c instanceof X509Certificate){
				try {
					if(earliestExpirationDateCert==null)
						earliestExpirationDateCert=((X509Certificate)c);
					else{
						if(earliestExpirationDateCert.getNotAfter().after(((X509Certificate)c).getNotAfter()))
							earliestExpirationDateCert = ((X509Certificate)c);
					}
					((X509Certificate)c).checkValidity();
				} catch (CertificateExpiredException e) {
					System.out.println("The Certificate has expired!");
				} catch (CertificateNotYetValidException e) {
					System.out.println("The Certificate is not yet valid!");
				}
				finally{
					valid++;}
			}
			else
				System.out.println("Certificate not known : " + c.getType());
		}
		if(valid==certificates.length)
			System.out.println("The Certificate chain is valid!");
		connection.disconnect();

	}

}
