package hexagon.engine.utils.json;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONException;

import hexagon.engine.utils.resources.ResourceLoadingException;
import hexagon.engine.utils.resources.Resources;

public final class JsonArray {
	
	public static JsonArray fromString(String jsonString) {
		return new JsonArray(jsonString);
	}

	public static JsonArray fromFile(String file) throws ResourceLoadingException {
		return fromString(Resources.readAsString(file));
	}

	public static JsonArray fromFileOrEmpty(String file) {
		try {
			return fromFile(file);
		} catch(ResourceLoadingException e) {
			return new JsonArray();
		}
	}

	public static JsonArray empty() {
		return new JsonArray();
	}
	
	private final JSONArray wrapped;

	private JsonArray(JSONArray wrapped) {
		this.wrapped = wrapped;
	}

	private JsonArray(String jsonString) {
		this(new JSONArray(jsonString));
	}

	private JsonArray() {
		this(new JSONArray());
	}

	public Optional<Integer> getInt(int index) {
		return this.get(index, this.wrapped::getInt);
	}

	public Optional<Float> getFloat(int index) {
		return this.get(index, this.wrapped::getFloat);
	}

	public Optional<String> getString(int index) {
		return this.get(index, this.wrapped::getString);
	}

	public Optional<Boolean> getBoolean(int index) {
		return this.get(index, this.wrapped::getBoolean);
	}

	private <T> Optional<T> get(int index, Function<Integer, T> getter) {
		try {
			return Optional.of(getter.apply(index));
		} catch(JSONException e) {
			return Optional.empty();
		}
	}

	public Optional<JsonObject> getObject(int index) {
		try {
			return Optional.of(JsonObject.fromString(this.wrapped.getJSONObject(index).toString()));
		} catch(JSONException e) {
			return Optional.empty();
		}
	}

	public Optional<JsonArray> getArray(int index) {
		try {
			return Optional.of(new JsonArray(this.wrapped.getJSONArray(index)));
		} catch(JSONException e) {
			return Optional.empty();
		}
	}

	public int length() {
		return this.wrapped.length();
	}

	private <T> Stream<T> stream(Function<Integer, Optional<T>> getter) {
		return IntStream.range(0, this.length())
			.mapToObj(getter::apply)
			.filter(Optional::isPresent)
			.map(Optional::get);
	}

	public void forEachInt(IntConsumer consumer) {
		this.streamInts().forEach(consumer);
	}

	public IntStream streamInts() {
		return IntStream.range(0, this.length())
			.mapToObj(i -> this.getInt(i))
			.filter(Optional::isPresent)
			.mapToInt(Optional::get);
	}

	public void forEachFloat(Consumer<Float> consumer) {
		this.streamFloats().forEach(consumer);
	}

	public Stream<Float> streamFloats() {
		return this.stream(this::getFloat);
	}

	public void forEachString(Consumer<String> consumer) {
		this.streamStrings().forEach(consumer);
	}

	public Stream<String> streamStrings() {
		return this.stream(this::getString);
	}

	public void forEachBoolean(Consumer<Boolean> consumer) {
		this.streamBooleans().forEach(consumer);
	}

	public Stream<Boolean> streamBooleans() {
		return this.stream(this::getBoolean);
	}

	public void forEachObject(Consumer<JsonObject> consumer) {
		this.streamObjects().forEach(consumer);
	}

	public Stream<JsonObject> streamObjects() {
		return this.stream(this::getObject);
	}

	public void forEachArray(Consumer<JsonArray> consumer) {
		this.streamArrays().forEach(consumer);
	}

	public Stream<JsonArray> streamArrays() {
		return this.stream(this::getArray);
	}

	// TODO - Test Json arrays
}
