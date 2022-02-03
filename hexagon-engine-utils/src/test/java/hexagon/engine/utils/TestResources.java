package hexagon.engine.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import hexagon.engine.utils.resources.ResourceLoadingException;
import hexagon.engine.utils.resources.Resources;

public class TestResources {
	
	@Test
	public void testReadAsString() throws ResourceLoadingException {
		String expected = "Hello!\n\nThis is a test file to test resource loading!\nI am very useful.";
		String actual = Resources.readAsString("/path/test.txt");
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testNonExistingFile() {
		Assertions.assertThrows(ResourceLoadingException.class, () -> Resources.readAsString("invalid"));
	}
}
