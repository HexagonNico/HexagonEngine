package hexagon.utils.json;

import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import org.json.JSONException;
import org.json.JSONObject;

import hexagon.utils.resources.ResourceLoadingException;
import hexagon.utils.resources.Resources;

public final class JsonObject {
	
	public static JsonObject fromString(String jsonString) {
		return new JsonObject(jsonString);
	}

	public static JsonObject fromFile(String file) throws ResourceLoadingException {
		return fromString(Resources.readString(file));
	}

	public static JsonObject fromFileOrEmpty(String file) {
		try {
			return fromFile(file);
		} catch(ResourceLoadingException e) {
			return new JsonObject();
		}
	}

	public static JsonObject empty() {
		return new JsonObject();
	}

	private final JSONObject wrapped;

	private JsonObject(JSONObject wrapped) {
		this.wrapped = wrapped;
	}

	private JsonObject(String jsonString) {
		this(new JSONObject(jsonString));
	}

	private JsonObject() {
		this(new JSONObject());
	}

	public Optional<Integer> getInt(String key) {
		return this.get(key, this.wrapped::getInt);
	}

	public int getInt(String key, int orElse) {
		return this.getInt(key).orElse(orElse);
	}

	public Optional<Float> getFloat(String key) {
		return this.get(key, this.wrapped::getFloat);
	}

	public float getFloat(String key, float orElse) {
		return this.getFloat(key).orElse(orElse);
	}

	public Optional<String> getString(String key) {
		return this.get(key, this.wrapped::getString);
	}

	public String getString(String key, String orElse) {
		return this.getString(key).orElse(orElse);
	}

	public Optional<Boolean> getBoolean(String key) {
		return this.get(key, this.wrapped::getBoolean);
	}

	public boolean getBoolean(String key, boolean orElse) {
		return this.getBoolean(key).orElse(orElse);
	}

	private <T> Optional<T> get(String key, Function<String, T> getter) {
		try {
			return Optional.of(getter.apply(key));
		} catch(JSONException e) {
			return Optional.empty();
		}
	}

	public Optional<JsonObject> getObject(String key) {
		try {
			return Optional.of(new JsonObject(this.wrapped.getJSONObject(key)));
		} catch(JSONException e) {
			return Optional.empty();
		}
	}

	public JsonObject getObjectOrEmpty(String key) {
		return this.getObject(key).orElse(JsonObject.empty());
	}

	public Optional<JsonArray> getArray(String key) {
		try {
			return Optional.of(JsonArray.fromString(this.wrapped.getJSONArray(key).toString()));
		} catch(JSONException e) {
			return Optional.empty();
		}
	}

	public JsonArray getArrayOrEmpty(String key) {
		return this.getArray(key).orElse(JsonArray.empty());
	}

	public Set<String> keySet() {
		return this.wrapped.keySet();
	}
}