package JSON.Deserializers;

import java.lang.reflect.Type;

import Entities.GitHubIssue;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class IssueDeserializer implements JsonDeserializer<GitHubIssue>{

	@Override
	public GitHubIssue deserialize(JsonElement elem, Type type, JsonDeserializationContext context) throws JsonParseException {
		JsonObject elemObj = elem.getAsJsonObject();
		JsonElement title = elemObj.get("title") , state = elemObj.get("state") , closed_at = elemObj.get("closed_at") , body = elemObj.get("body");
		
		return new GitHubIssue( title.isJsonNull() ? null : title.getAsString(),
								state.isJsonNull() ? null : state.getAsString(),
								closed_at.isJsonNull() ? null : closed_at.getAsString(),
								body.isJsonNull() ? null : body.getAsString()
								);
	}
}
