package JSONDeserializer;

import java.lang.reflect.Type;
import java.security.acl.Permission;

import Roles.Role;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class RoleDeserializer implements JsonDeserializer<Role>{

	private PermissionDeserializer permissionDeserializer = new PermissionDeserializer();
	
	@Override
	public Role deserialize(JsonElement elem, Type type, JsonDeserializationContext context) throws JsonParseException {
		JsonObject elemObj = elem.getAsJsonObject();
		JsonElement id = elemObj.get("id"), permissionMap = elemObj.get("permissionList");
		
		if (id.isJsonNull()) 
			throw new RuntimeException("Id is mandatory!");
		
		Role role = new Role(id.getAsString());
		
		for (JsonElement permissionElement : permissionMap.getAsJsonArray()){
			role.addPermission(permissionDeserializer.deserialize(permissionElement, Permission.class, null));
		}
		
		return role;
	}
}