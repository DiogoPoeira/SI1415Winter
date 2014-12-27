package JSONDeserializer;

import java.lang.reflect.Type;

import Permissions.Permissions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class PermissionDeserializer implements JsonDeserializer<Permissions>{
	
	@Override
	public Permissions deserialize(JsonElement elem, Type type, JsonDeserializationContext context) throws JsonParseException {
		JsonObject elemObj = elem.getAsJsonObject();
		JsonElement id = elemObj.get("id"), resourceList = elemObj.get("resourceList");
		
		if (id.isJsonNull()) 
			throw new RuntimeException("Id is mandatory!");
		
		Permissions permission = new Permissions(id.getAsString());
		
		for (JsonElement resourceElement : resourceList.getAsJsonArray()){
			permission.addResource(resourceElement.getAsString());
		}
		
		return permission;
	}
}