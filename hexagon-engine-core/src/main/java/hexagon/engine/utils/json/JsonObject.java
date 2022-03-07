package hexagon.engine.utils.json;

import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import org.json.JSONObject;

import hexagon.engine.resources.ResourceLoadingException;
import hexagon.engine.resources.Resources;

/**
 * Class that represents a Json object.
 * Wraps an {@link org.json.JSONObject} to add more functions.
 * 
 * @author Nico
 */
public final class JsonObject {
	
	// TODO - Needs unit tests

	/**
	 * Creates a Json object from a Json string.
	 * 
	 * @param jsonString Json string value
	 * 
	 * @return The Json object created from the given string
	 */
	public static JsonObject fromString(String jsonString) {
		return new JsonObject(jsonString);
	}

	/**
	 * Loads a Json object from a {@code .json} file.
	 * 
	 * @param file Path to the file from the resources folder, starting with {@code /}
	 * 
	 * @return The Json object created from that file
	 * 
	 * @throws ResourceLoadingException If an exception occurs while loading the file,
	 * 		i. e., an IO error occurs or the file does not exist
	 */
	public static JsonObject fromFile(String file) throws ResourceLoadingException {
		return fromString(Resources.readString(file));
	}

	/**
	 * Loads a Json object from a {@code .json} file.
	 * 
	 * @param file Path to the file from the resources folder, starting with {@code /}
	 * 
	 * @return The Json object created from that file or an empty one if an IO error occurred
	 */
	public static JsonObject fromFileOrEmpty(String file) {
		try {
			return fromFile(file);
		} catch(ResourceLoadingException e) {
			return new JsonObject();
		}
	}

	/**
	 * Creates an empty Json object.
	 * 
	 * @return A new empty json object
	 */
	public static JsonObject empty() {
		return new JsonObject();
	}

	/**Wrapped {@link org.json.JSONObject} */
	private final JSONObject wrapped;

	/**
	 * Creates a Json object with this wrapped object.
	 * Also used in {@link JsonObject#getObject(String)}
	 * 
	 * @param wrapped Wrapped object
	 */
	private JsonObject(JSONObject wrapped) {
		this.wrapped = wrapped;
	}

	/**
	 * Creates a Json object from a Json string.
	 * 
	 * @param jsonString Json string
	 */
	private JsonObject(String jsonString) {
		this(new JSONObject(jsonString));
	}

	/**
	 * Creates an empty Json object.
	 */
	private JsonObject() {
		this(new JSONObject());
	}

	/**
	 * Gets an integer from this Json object.
	 * 
	 * @param key Key of the desired value
	 * 
	 * @return An {@link Optional} containing the desired value
	 * 		or an empty optional if this Json object does not contain such value
	 */
	public Optional<Integer> getInt(String key) {
		return this.get(key, this.wrapped::getInt);
	}

	/**
	 * Gets an integer from this Json object if present.
	 * 
	 * @param key Key of the desired value
	 * @param orElse Default value to return in case such value is not found
	 * 
	 * @return The desired value or the default value in case it is not found
	 */
	public int getInt(String key, int orElse) {
		return this.getInt(key).orElse(orElse);
	}

	/**
	 * Gets a float from this Json object.
	 * 
	 * @param key Key of the desired value
	 * 
	 * @return An {@link Optional} containing the desired value
	 * 		or an empty optional if this Json object does not contain such value
	 */
	public Optional<Float> getFloat(String key) {
		return this.get(key, this.wrapped::getFloat);
	}

	/**
	 * Gets a float from this Json object if present.
	 * 
	 * @param key Key of the desired value
	 * @param orElse Default value to return in case such value is not found
	 * 
	 * @return The desired value or the default value in case it is not found
	 */
	public float getFloat(String key, float orElse) {
		return this.getFloat(key).orElse(orElse);
	}

	/**
	 * Gets a string from this Json object.
	 * 
	 * @param key Key of the desired value
	 * 
	 * @return An {@link Optional} containing the desired value
	 * 		or an empty optional if this Json object does not contain such value
	 */
	public Optional<String> getString(String key) {
		return this.get(key, this.wrapped::getString);
	}

	/**
	 * Gets a string from this Json object if present.
	 * 
	 * @param key Key of the desired value
	 * @param orElse Default value to return in case such value is not found
	 * 
	 * @return The desired value or the default value in case it is not found
	 */
	public String getString(String key, String orElse) {
		return this.getString(key).orElse(orElse);
	}

	/**
	 * Gets a boolean from this Json object.
	 * 
	 * @param key Key of the desired value
	 * 
	 * @return An {@link Optional} containing the desired value
	 * 		or an empty optional if this Json object does not contain such value
	 */
	public Optional<Boolean> getBoolean(String key) {
		return this.get(key, this.wrapped::getBoolean);
	}

	/**
	 * Performs an action if this Json object contains a {@code true} boolean value.
	 * If the object does not contain such value, the action is not performed.
	 * 
	 * @param key Key of the desired value
	 * @param action Action to perform if such value is {@code true}
	 */
	public void ifBoolean(String key, Runnable action) {
		if(this.getBoolean(key).orElse(false)) action.run();
	}

	/**
	 * Performs an action if this Json object contains a {@code false} boolean value.
	 * If the object does not contain such value, the action is still performed.
	 * 
	 * @param key Key of the desired value
	 * @param action Action to perform if such value is {@code false}
	 */
	public void ifNotBoolean(String key, Runnable action) {
		if(!this.getBoolean(key).orElse(false)) action.run();
	}

	/**
	 * Gets any value from this Json object.
	 * 
	 * @param <T> Type of the value to get
	 * @param key Key of the value
	 * @param getter Getter method from the wrapped object
	 * 
	 * @return An optional containing the desired value or an empty optional
	 */
	private <T> Optional<T> get(String key, Function<String, T> getter) {
		if(this.wrapped.has(key)) {
			return Optional.of(getter.apply(key));
		} else {
			return Optional.empty();
		}
	}

	/**
	 * Gets a Json object from this Json object.
	 * 
	 * @param key Key of the desired object
	 * 
	 * @return An {@link Optional} containing the desired object
	 * 		or an empty optional if this Json object does not contain such object
	 */
	public Optional<JsonObject> getObject(String key) {
		if(this.wrapped.has(key)) {
			return Optional.of(new JsonObject(this.wrapped.getJSONObject(key)));
		} else {
			return Optional.empty();
		}
	}

	/**
	 * Gets a Json object from this Json object.
	 * 
	 * @param key Key of the desired object
	 * 
	 * @return The desired Json object or an empty one if that object is not found
	 */
	public JsonObject getObjectOrEmpty(String key) {
		return this.getObject(key).orElse(JsonObject.empty());
	}

	/**
	 * Gets a Json array from this Json object.
	 * 
	 * @param key Key of the desired array
	 * 
	 * @return An {@link Optional} containing the desired array
	 * 		or an empty optional if this Json object does not contain such array
	 */
	public Optional<JsonArray> getArray(String key) {
		if(this.wrapped.has(key)) {
			return Optional.of(JsonArray.fromString(this.wrapped.getJSONArray(key).toString()));
		} else {
			return Optional.empty();
		}
	}

	/**
	 * Gets a Json array from this Json object.
	 * 
	 * @param key Key of the desired array
	 * 
	 * @return The desired Json array or an empty one if that array is not found
	 */
	public JsonArray getArrayOrEmpty(String key) {
		return this.getArray(key).orElse(JsonArray.empty());
	}

	public Set<String> keySet() {
		return this.wrapped.keySet();
	}
}
