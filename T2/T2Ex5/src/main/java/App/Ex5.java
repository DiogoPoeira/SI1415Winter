package App;

import java.security.cert.Certificate;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

public class Ex5 {

	public static void main(String [] args) throws Exception{
		Ex();
	}

	private static void Ex() throws SSLPeerUnverifiedException {
		Scanner in = new Scanner(System.in);
		System.out.println("Please insert the desired URL!");
		String urlname = in.nextLine();

		HttpsURLConnection connection = Utils.getConnection(urlname);
		Certificate[] certificates = connection.getServerCertificates();
		X509Certificate earliestExpirationDateCert = null;

		for(Certificate c : certificates){

			if(c instanceof X509Certificate){
				X509Certificate myCert = (X509Certificate) c;

				System.out.println("\n************************************************************************ \n");

				System.out.println("Issuer details - " + myCert.getIssuerDN());
				System.out.println("Serial number - " + myCert.getSerialNumber());

				if(earliestExpirationDateCert == null){
					earliestExpirationDateCert= myCert;
					printHostNameValidity(myCert);
				}
				else{
					if(earliestExpirationDateCert.getNotAfter().after(myCert.getNotAfter()))
						earliestExpirationDateCert = myCert;
				}

				System.out.println("Data de expiração do certificado - " + myCert.getNotAfter());
				System.out.println("Data de criação do certificado - " + myCert.getNotBefore());
				System.out.println("Versão de TLS/SSL - " + myCert.getVersion());
			}
			else
				System.out.println("Certificate not known : " + c.getType());
		}

		System.out.println("\n************************************************************************ \n");

		connection.disconnect();
		in.close();
	}

	private static void printHostNameValidity(X509Certificate cert) {

		try {
			Collection<List<?>> alternativeNames = cert.getSubjectAlternativeNames();
			System.out.println("Nomes para os quais o certificado é válido : ");
			for (List<?> s : alternativeNames){
				System.out.println(s.get(1).toString());
			}

		} catch (CertificateParsingException e) {
			e.printStackTrace();
			System.out.println("Error parsing certificate.");
		}

	}

}
