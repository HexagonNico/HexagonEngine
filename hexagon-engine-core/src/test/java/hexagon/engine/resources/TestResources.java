package hexagon.engine.resources;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestResources {
	
	// TODO - Write more tests

	@Test
	public void testReadString() throws ResourceLoadingException {
		String expected = "Hello!\n\nThis is a test file to test resource loading!\nI am very useful.";
		String actual = Resources.readString("/path/test.txt");
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testNonExistingFile() {
		Assertions.assertThrows(ResourceLoadingException.class, () -> Resources.readString("invalid"));
	}
}
