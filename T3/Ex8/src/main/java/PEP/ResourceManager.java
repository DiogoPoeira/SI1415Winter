package PEP;

import java.util.HashMap;
import java.util.Map;

import PEP.Retrievers.Retriever;
import PEP.Retrievers.TextfilesRetriever;

public class ResourceManager {

	private Map<String, Retriever> resourceMapping;
	
	public ResourceManager(){
		resourceMapping = new HashMap<String, Retriever>();
		resourceMapping.put(".txt", new TextfilesRetriever());
	}
	
	public byte[] retrieveResource(String resourceId) {
		String extension = resourceId.substring(resourceId.lastIndexOf("."));
		return resourceMapping.get(extension).retrieve(resourceId);
	}
}
