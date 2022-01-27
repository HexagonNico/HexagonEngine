package hexagon.engine.math.vector;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestFloat4 {
	
	private static final Float4 f1 = new Float4(1.1f, 0.4f, 2.5f, 1.7f);
	private static final Float4 f2 = new Float4(2.3f, 1.7f, 0.9f, 2.6f);
	public static final Int4 i1 = new Int4(1, 2, 3, 4);
	private static final Float4 u = new Float4(1.0f, 1.0f, 1.0f, 1.0f);

	@Test
	@DisplayName("Float4 + Float4")
	public void testFPlusF() {
		Float4 expected = new Float4(3.4f, 2.1f, 3.4f, 4.3f);
		Float4 actual = f1.plus(f2);
		assertionHelper(expected, actual);
	}

	@Test
	@DisplayName("Float4 + (x, y, z, w)")
	public void testFPlusXyzw() {
		Float4 expected = new Float4(3.4f, 2.1f, 3.4f, 4.3f);
		Float4 actual = f1.plus(2.3f, 1.7f, 0.9f, 2.6f);
		assertionHelper(expected, actual);
	}
	
	@Test
	@DisplayName("Float4 + Int4")
	public void testFPlusI() {
		Float4 expected = new Float4(2.1f, 2.4f, 5.5f, 5.7f);
		Float4 actual = f1.plus(i1);
		assertionHelper(expected, actual);
	}
	
	@Test
	@DisplayName("-Float4")
	public void testNegative() {
		Float4 expected = new Float4(-1.1f, -0.4f, -2.5f, -1.7f);
		Float4 actual = f1.negative();
		assertionHelper(expected, actual);
	}
	
	@Test
	@DisplayName("Float4 - Float4")
	public void testFMinusF() {
		Float4 expected = new Float4(1.2f, 1.3f, -1.6f, 0.9f);
		Float4 actual = f2.minus(f1);
		assertionHelper(expected, actual);
	}

	@Test
	@DisplayName("Float4 - (x, y, z, w)")
	public void testFMinusXyzw() {
		Float4 expected = new Float4(1.2f, 1.3f, -1.6f, 0.9f);
		Float4 actual = f2.minus(1.1f, 0.4f, 2.5f, 1.7f);
		assertionHelper(expected, actual);
	}
	
	@Test
	@DisplayName("Float4 - Int4")
	public void testFMinusI() {
		Float4 expected = new Float4(0.1f, -1.6f, -0.5f, -2.3f);
		Float4 actual = f1.minus(i1);
		assertionHelper(expected, actual);
	}
	
	@Test
	@DisplayName("Float4 * k")
	public void testFTimesK() {
		Float4 expected = new Float4(2.2f, 0.8f, 5.0f, 3.4f);
		Float4 actual = f1.times(2.0f);
		assertionHelper(expected, actual);
	}
	
	@Test
	@DisplayName("Float4 / k")
	public void testFDividedByK() {
		Float4 expected = new Float4(0.55f, 0.2f, 1.25f, 0.85f);
		Float4 actual = f1.dividedBy(2.0f);
		assertionHelper(expected, actual);
	}
	
	@Test
	@DisplayName("Float4 ° Float4")
	public void testFDotF() {
		float expected = 1.1f * 2.3f + 0.4f * 1.7f + 2.5f * 0.9f + 1.7f * 2.6f;
		float actual = f1.dotProduct(f2);
		Assertions.assertEquals(expected, actual, 0.00001f);
	}
	
	@Test
	@DisplayName("Float4 ° (x, y, z, w)")
	public void testFDotXyzw() {
		float expected = 1.1f * 2.3f + 0.4f * 1.7f + 2.5f * 0.9f + 1.7f * 2.6f;
		float actual = f1.dotProduct(2.3f, 1.7f, 0.9f, 2.6f);
		Assertions.assertEquals(expected, actual, 0.00001f);
	}
	
	@Test
	@DisplayName("Float4 ° Int4")
	public void testFDotI() {
		float expected = 1.1f * 1 + 0.4f * 2 + 2.5f * 3 + 1.7f * 4;
		float actual = f1.dotProduct(i1);
		Assertions.assertEquals(expected, actual, 0.00001f);
	}
	
	@Test
	@DisplayName("|Float4|^2")
	public void testLengthSquared() {
		float expected = 4;
		float actual = u.lengthSquared();
		Assertions.assertEquals(expected, actual, 0.00001f);
	}
	
	@Test
	@DisplayName("|Float4|")
	public void testLength() {
		float expected = (float) Math.sqrt(4);
		float actual = u.length();
		Assertions.assertEquals(expected, actual, 0.00001f);
	}

	// TODO - Test normalized
	// TODO - Test angle

	public static void assertionHelper(Float4 expected, Float4 actual) {
		Assertions.assertEquals(expected.x(), actual.x(), 0.00001f);
		Assertions.assertEquals(expected.y(), actual.y(), 0.00001f);
		Assertions.assertEquals(expected.z(), actual.z(), 0.00001f);
		Assertions.assertEquals(expected.w(), actual.w(), 0.00001f);
	}
}
