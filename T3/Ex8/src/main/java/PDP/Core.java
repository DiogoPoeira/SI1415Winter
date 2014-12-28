package PDP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import JSONDeserializer.RoleDeserializer;
import JSONDeserializer.UserDeserializer;
import Permissions.Permissions;
import Roles.Role;
import Users.User;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class Core {

	private JsonParser parser = new JsonParser();
	private UserDeserializer userDeserializer = new UserDeserializer(this);
	private RoleDeserializer roleDeserializer = new RoleDeserializer();
	private List<User> userList;
	private List<Role> roleList;
	
	public Core(){
		this.userList = new ArrayList<User>();
		this.roleList = new ArrayList<Role>();
		load();
	}
	
	public boolean getResource(User user, Permissions permission, String resource){
		return getResource(user.getRoles(), permission, resource);
	}
	
	private boolean getResource(List<Role> roles, Permissions permission, String resource) {
		for (Role role : roles) {
			if (checkForPermissionInSubRoles(role, permission, resource)){
				return true;
			}
		}
		
		return false;
	}

	private boolean checkForPermissionInSubRoles(Role role, Permissions permission, String resource) {
		List<Permissions> permissionsList = role.getPermissionsList();
		if (permissionsList.contains(permission)) {
			for(Permissions perm : permissionsList){
				if (perm.equals(permission) && perm.getResourceList().contains(resource)){
					return true;
				}
			}
		}
		
		return getResource(role.getSubRoles(), permission, resource);
	}

	private void load(){
		try (InputStream stream = Core.class.getClassLoader().getResourceAsStream("res/permissionsFile.json");
				BufferedReader br = new BufferedReader(new InputStreamReader(stream))){
			
			StringBuffer buffer = new StringBuffer();
			String actualLine = br.readLine();
			
			
			while(actualLine != null){
				buffer.append(actualLine);
				actualLine = br.readLine();
			}
			
			JsonElement obj = parser.parse(buffer.toString());
			JsonArray usersJson = obj.getAsJsonObject().get("userArray").getAsJsonArray(),
					  roleJson = obj.getAsJsonObject().get("roleArray").getAsJsonArray(),
					  roleHierarchy = obj.getAsJsonObject().get("roleHierarchy").getAsJsonArray();
			
			loadRolesFromJson(roleJson);
			createRoleHierarchy(roleHierarchy);
			loadUsersFromJson(usersJson);
		
		} 
		catch (IOException e){
			System.out.println("Failed reading the file with the permissions and stuff");
		}
		System.out.println();
	}

	private void createRoleHierarchy(JsonArray roleHierarchy) {
		for(JsonElement roleDependency : roleHierarchy){
			String roleId = roleDependency.getAsJsonObject().get("Role").getAsString();
			JsonArray subRoles = roleDependency.getAsJsonObject().get("ContainedRoles").getAsJsonArray();
			
			Role masterRole = findRole(roleId);
			
			for (JsonElement subRole : subRoles){
				masterRole.addSubRole(findRole(subRole.getAsString()));
			}
		}
	}

	private Role findRole(String roleId) {
		for (Role role : roleList){
			if (role.id.equals(roleId))
				return role;
		}
		
		throw new NoSuchElementException();
	}

	private void loadRolesFromJson(JsonArray roleJson) {
		for(JsonElement roleElement : roleJson){
			roleList.add(roleDeserializer.deserialize(roleElement, Role.class, null));
		}
	}

	private void loadUsersFromJson(JsonArray usersJson) {
		for(JsonElement userElement : usersJson){
			userList.add(userDeserializer.deserialize(userElement, User.class, null));
		}
	}
	
	public List<Role> getRoles(){
		return roleList;
	}
}
