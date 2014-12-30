package PEP.Retrievers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import PDP.PDP;

public class TextfilesRetriever implements Retriever {

	@Override
	public byte[] retrieve(String resourceId) {
		try (InputStream stream = PDP.class.getClassLoader().getResourceAsStream(resourceId);
				BufferedReader br = new BufferedReader(new InputStreamReader(stream))){
			
			StringBuffer buffer = new StringBuffer();
			String actualLine = br.readLine();
			
			
			while(actualLine != null){
				buffer.append(actualLine);
				actualLine = br.readLine();
			}
			
			return buffer.toString().getBytes();
		} catch (IOException e){
			throw new RuntimeException("Error reading file " + resourceId);
		}
	}
}
