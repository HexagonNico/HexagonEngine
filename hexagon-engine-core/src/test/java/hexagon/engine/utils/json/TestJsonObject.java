package hexagon.engine.utils.json;

import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestJsonObject {
	
	private static JsonObject jsonObject = JsonObject.fromString("{\"testStr\": \"This is a string\",\"testNum\": 42, \"testFloat\":\"4.2\", \"testBool\":\"true\"}");
	private static JsonObject composite = JsonObject.fromString("{\"test\": {\"value\": \"string value\", \"value2\": 42}}");
	private static JsonObject withArray = JsonObject.fromString("{\"array\": [0.0, 0.1, 0.2]}");

	@Test
	public void testGetInt() {
		Assertions.assertEquals(42, jsonObject.getInt("testNum").orElse(0));
	}

	@Test
	public void testGetFloat() {
		Assertions.assertEquals(4.2f, jsonObject.getFloat("testFloat").orElse(-1.0f));
	}

	@Test
	public void testGetString() {
		Assertions.assertEquals("This is a string", jsonObject.getString("testStr").orElse(":("));
	}

	@Test
	public void testGetBoolean() {
		Assertions.assertTrue(jsonObject.getBoolean("testBool").orElse(false));
	}

	@Test
	public void testGetObject() {
		JsonObject inner = composite.getObject("test").orElse(JsonObject.empty());
		Assertions.assertEquals("string value", inner.getString("value").orElse(":("));
		Assertions.assertEquals(42, inner.getInt("value2").orElse(0));
	}

	@Test
	public void testGetArray() {
		JsonArray array = withArray.getArray("array").orElse(JsonArray.empty());
		Assertions.assertEquals(0.0f, array.getFloat(0).orElse(-1.0f));
		Assertions.assertEquals(0.1f, array.getFloat(1).orElse(-1.0f));
		Assertions.assertEquals(0.2f, array.getFloat(2).orElse(-1.0f));
	}

	@Test
	public void testFromFile() {
		JsonObject fromFile = JsonObject.fromFileOrEmpty("/path/test.json");
		JsonObject inner = fromFile.getObject("inner").orElse(JsonObject.empty());
		Assertions.assertEquals("A string", inner.getString("stringValue").orElse(":("));
		Assertions.assertEquals(42, inner.getInt("numValue").orElse(0));
	}

	@Test
	public void testKeys() {
		Set<String> expected = Set.of("testStr", "testNum", "testFloat", "testBool");
		Set<String> actual = jsonObject.keySet();
		Assertions.assertEquals(expected, actual);
	}
}
