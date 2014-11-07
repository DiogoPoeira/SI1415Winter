

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;

public class Utils {
	
	public static  HttpsURLConnection getConnection(String urlname){
		try {
		    URL myURL = new URL(urlname);
		    HttpsURLConnection myURLConnection = (HttpsURLConnection) myURL.openConnection();
		    myURLConnection.connect();
		    return myURLConnection;
		} 
		catch (MalformedURLException e) { 
		} 
		catch (IOException e) {   
		}
		return null;
	}
	public static void printCertificates(X509Certificate[] certificates){
		int i = 0;
		for(X509Certificate c : certificates){
			System.out.println("Certificate : "+(i++));
			System.out.println("Issuer : "+c.getIssuerDN().getName()+"\nSerial Number:"+c.getSerialNumber());
			System.out.println("Version : "+c.getVersion()+"\n");
		}
	}
	public static Date earliestExpirationCertificate(X509Certificate[] certificates){
		X509Certificate earliestExpirationDateCert = null;
		for(X509Certificate c : certificates){
				if(earliestExpirationDateCert==null)
					earliestExpirationDateCert=c;
				else{
					if(earliestExpirationDateCert.getNotAfter().after(c.getNotAfter()))
						earliestExpirationDateCert = c;
			}
		}
		return earliestExpirationDateCert.getNotAfter();
	}
	
	public static void printHostNameValidity(X509Certificate cert) {

		try {
			Collection<List<?>> alternativeNames = cert.getSubjectAlternativeNames();
			System.out.println("Names for which this certificate is valid: ");
			for (List<?> s : alternativeNames){
				System.out.println(s.get(1).toString());
			}
			System.out.println();

		} catch (CertificateParsingException e) {
			e.printStackTrace();
			System.out.println("Error parsing certificate.");
		}
	}
}
