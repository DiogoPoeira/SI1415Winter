package Roles;

import java.util.ArrayList;
import java.util.List;

import Permissions.Permissions;

public class Role {
	
	public final String id;
	private List<Permissions> permissionList;
	private List<Role> subRoles;

	public Role( String id ){
		this.id = id;
		this.permissionList = new ArrayList<Permissions>();
		this.subRoles = new ArrayList<Role>();
	}
	
	public void addPermission(Permissions permission){
		if (!permissionList.contains(permission))
			permissionList.add(permission);
	}
	
	public List<Permissions> getPermissionsList(){
		return permissionList;
	}
	
	public void addSubRole(Role role){
		if(!subRoles.contains(role))
			subRoles.add(role);
	}
	
	public List<Role> getSubRoles(){
		return subRoles;
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
