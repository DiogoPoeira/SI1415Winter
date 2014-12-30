package PDP.DataTypes;

import java.util.ArrayList;
import java.util.List;

public class Role {
	
	public final String id;
	private List<Action> permissionList;
	private List<Role> subRoles;

	public Role( String id ){
		this.id = id;
		this.permissionList = new ArrayList<Action>();
		this.subRoles = new ArrayList<Role>();
	}
	
	public void addPermission(Action permission){
		if (!permissionList.contains(permission))
			permissionList.add(permission);
	}
	
	public List<Action> getPermissionsList(){
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
			if (obj instanceof Action){
				Role roleObj = (Role)obj;
				return roleObj.id.equals(this.id);
			}
		}
		
		return false;
	}
}
