package Roles;

import java.util.ArrayList;
import java.util.List;

import Permissions.Permissions;

public class Role {
	
	public final String id;
	private List<Permissions> permissionList;

	public Role( String id ){
		this.id = id;
		this.permissionList = new ArrayList<Permissions>();
	}
	
	public void addPermission(Permissions permission){
		if (!permissionList.contains(permission))
			permissionList.add(permission);
	}
	
	public List<Permissions> getPermissionsList(){
		return permissionList;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj != null){
			if (obj instanceof Permissions){
				Role roleObj = (Role)obj;
				return roleObj.id.equals(this.id);
			}
		}
		
		return false;
	}
}
