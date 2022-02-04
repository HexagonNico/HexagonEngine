package hexagon.engine.utils.json;

import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import org.json.JSONException;
import org.json.JSONObject;

import hexagon.engine.utils.resources.ResourceLoadingException;
import hexagon.engine.utils.resources.Resources;

public final class JsonObject {
	
	public static JsonObject fromString(String jsonString) {
		return new JsonObject(jsonString);
	}

	public static JsonObject fromFile(String file) throws ResourceLoadingException {
		return fromString(Resources.readAsString(file));
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

	public Optional<Float> getFloat(String key) {
		return this.get(key, this.wrapped::getFloat);
	}

	public Optional<String> getString(String key) {
		return this.get(key, this.wrapped::getString);
	}

	public Optional<Boolean> getBoolean(String key) {
		return this.get(key, this.wrapped::getBoolean);
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

	public Optional<JsonArray> getArray(String key) {
		try {
			return Optional.of(JsonArray.fromString(this.wrapped.getJSONArray(key).toString()));
		} catch(JSONException e) {
			return Optional.empty();
		}
	}

	public Set<String> keySet() {
		return this.wrapped.keySet();
	}
}
