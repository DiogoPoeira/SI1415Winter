package JSON.Serializers;

import java.lang.reflect.Type;

import Entities.GoogleTask;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class GoogleTaskSerializer implements JsonSerializer<GoogleTask>{

	@Override
	public JsonElement serialize(GoogleTask task, Type type, JsonSerializationContext context) {
		JsonObject taskObj = new JsonObject();
		taskObj.add("kind", new JsonPrimitive(GoogleTask.kind));
		taskObj.add("title", new JsonPrimitive(task.title));
		taskObj.add("status", new JsonPrimitive(task.status));
		taskObj.add("completed", task.completed == null ? JsonNull.INSTANCE : new JsonPrimitive(task.completed.toString()));
		return taskObj;
	}
}
