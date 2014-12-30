package JSONDeserializer;

import java.lang.reflect.Type;
import java.util.List;

import PDP.PDP;
import PDP.DataTypes.Role;
import PDP.DataTypes.User;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class UserDeserializer implements JsonDeserializer<User>{

	private PDP core;
	
	public UserDeserializer(PDP core) {
		this.core = core;
	}

	@Override
	public User deserialize(JsonElement elem, Type type, JsonDeserializationContext context) throws JsonParseException {
		List<Role> roleList = core.getRoles();
		
		JsonObject elemObj = elem.getAsJsonObject();
		JsonElement id = elemObj.get("id"), roles = elemObj.get("roles");
		
		if (id.isJsonNull()) 
			throw new RuntimeException("Id is mandatory!");
		
		User user = new User(id.getAsString());
		
		for (JsonElement roleElem : roles.getAsJsonArray()){
			String roleId = roleElem.getAsJsonObject().get("id").getAsString();
			for (Role role : roleList){
				if (role.id.equals(roleId))
					user.addRole(role);
			}
		}
		
		return user;
	}
}