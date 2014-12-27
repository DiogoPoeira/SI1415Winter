package Permissions;

import java.util.ArrayList;
import java.util.List;

public class Permissions {
	
	public String id;
	private List<String> resourceList;

	public Permissions(String id){
		this.id = id;
		this.resourceList = new ArrayList<String>();
	}
	
	public List<String> getResourceList() {
		return resourceList;
	}

	public void addResource(String resource) {
		if (!resourceList.contains(resource))
			resourceList.add(resource);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj != null){
			if (obj instanceof Permissions){
				Permissions permissionObj = (Permissions)obj;
				return permissionObj.id.equals(this.id);
			}
		}
		
		return false;
	}
}
