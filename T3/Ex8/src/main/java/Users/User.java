package Users;

import java.util.ArrayList;
import java.util.List;

import Permissions.Permissions;
import Roles.Role;

public class User {

	public String id;
	private List<Role> roleList;
	
	public User(String id){
		this.id = id;
		this.roleList = new ArrayList<Role>();
	}
	
	public void addRole(Role newRole){
		roleList.add(newRole);
	}
	
	public List<Role> getRoles(){
		return roleList;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj != null){
			if (obj instanceof Permissions){
				User userObj = (User)obj;
				return userObj.id.equals(this.id);
			}
		}
		
		return false;
	}
}
