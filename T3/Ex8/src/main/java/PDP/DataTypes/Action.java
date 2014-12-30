package PDP.DataTypes;

import java.util.ArrayList;
import java.util.List;

public class Action {
	
	public String id;
	private List<String> resourceList;

	public Action(String id){
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
			if (obj instanceof Action){
				Action permissionObj = (Action)obj;
				return permissionObj.id.equals(this.id);
			}
		}
		
		return false;
	}
}
