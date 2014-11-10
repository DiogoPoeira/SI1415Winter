package JSON.Deserializers;

import java.lang.reflect.Type;

import Entities.GoogleTask;
import Utils.DateTimeUtils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class GoogleTaskDeserializer implements JsonDeserializer<GoogleTask>{

	@Override
	public GoogleTask deserialize(JsonElement elem, Type type, JsonDeserializationContext context) throws JsonParseException {
		JsonObject elemObj = elem.getAsJsonObject();
		JsonElement title = elemObj.get("title") , notes = elemObj.get("notes") , status = elemObj.get("status") , completed = elemObj.get("completed");
		
		
		return new GoogleTask( title.isJsonNull() ? null : title.getAsString(),
								notes.isJsonNull() ? null : notes.getAsString(),
								status.isJsonNull() ? null : status.getAsString(),
								completed.isJsonNull() ? null : DateTimeUtils.getDateTimeFromString(completed.getAsString())
								);
	}

}
