package hexagon.engine.math.vector;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestFloat3 {
	
	private static final Float3 f1 = new Float3(1.1f, 0.4f, 2.5f);
	private static final Float3 f2 = new Float3(2.3f, 1.7f, 0.9f);
	public static final Int3 i1 = new Int3(1, 2, 3);
	private static final Float3 u = new Float3(1.0f, 1.0f, 1.0f);

	@Test
	@DisplayName("Float3 + Float3")
	public void testFPlusF() {
		Float3 expected = new Float3(3.4f, 2.1f, 3.4f);
		Float3 actual = f1.plus(f2);
		assertionHelper(expected, actual);
	}

	@Test
	@DisplayName("Float3 + (x, y, z)")
	public void testFPlusXyz() {
		Float3 expected = new Float3(3.4f, 2.1f, 3.4f);
		Float3 actual = f1.plus(2.3f, 1.7f, 0.9f);
		assertionHelper(expected, actual);
	}
	
	@Test
	@DisplayName("Float3 + Int3")
	public void testFPlusI() {
		Float3 expected = new Float3(2.1f, 2.4f, 5.5f);
		Float3 actual = f1.plus(i1);
		assertionHelper(expected, actual);
	}
	
	@Test
	@DisplayName("-Float3")
	public void testNegative() {
		Float3 expected = new Float3(-1.1f, -0.4f, -2.5f);
		Float3 actual = f1.negative();
		assertionHelper(expected, actual);
	}
	
	@Test
	@DisplayName("Float3 - Float3")
	public void testFMinusF() {
		Float3 expected = new Float3(1.2f, 1.3f, -1.6f);
		Float3 actual = f2.minus(f1);
		assertionHelper(expected, actual);
	}

	@Test
	@DisplayName("Float3 - (x, y, z)")
	public void testFMinusXyz() {
		Float3 expected = new Float3(1.2f, 1.3f, -1.6f);
		Float3 actual = f2.minus(1.1f, 0.4f, 2.5f);
		assertionHelper(expected, actual);
	}
	
	@Test
	@DisplayName("Float3 - Int3")
	public void testFMinusI() {
		Float3 expected = new Float3(0.1f, -1.6f, -0.5f);
		Float3 actual = f1.minus(i1);
		assertionHelper(expected, actual);
	}
	
	@Test
	@DisplayName("Float3 * k")
	public void testFTimesK() {
		Float3 expected = new Float3(2.2f, 0.8f, 5.0f);
		Float3 actual = f1.times(2.0f);
		assertionHelper(expected, actual);
	}
	
	@Test
	@DisplayName("Float3 / k")
	public void testFDividedByK() {
		Float3 expected = new Float3(0.55f, 0.2f, 1.25f);
		Float3 actual = f1.dividedBy(2.0f);
		assertionHelper(expected, actual);
	}
	
	@Test
	@DisplayName("Float3 ° Float3")
	public void testFDotF() {
		float expected = 1.1f * 2.3f + 0.4f * 1.7f + 2.5f * 0.9f; 
		float actual = f1.dotProduct(f2);
		Assertions.assertEquals(expected, actual, 0.00001f);
	}
	
	@Test
	@DisplayName("Float3 ° (x, y, z)")
	public void testFDotXyz() {
		float expected = 1.1f * 2.3f + 0.4f * 1.7f + 2.5f * 0.9f; 
		float actual = f1.dotProduct(2.3f, 1.7f, 0.9f);
		Assertions.assertEquals(expected, actual, 0.00001f);
	}
	
	@Test
	@DisplayName("Float3 ° Int3")
	public void testFDotI() {
		float expected = 1.1f * 1 + 0.4f * 2 + 2.5f * 3; 
		float actual = f1.dotProduct(i1);
		Assertions.assertEquals(expected, actual, 0.00001f);
	}
	
	@Test
	@DisplayName("|Float3|^2")
	public void testLengthSquared() {
		float expected = 3; 
		float actual = u.lengthSquared();
		Assertions.assertEquals(expected, actual, 0.00001f);
	}
	
	@Test
	@DisplayName("|Float3|")
	public void testLength() {
		float expected = (float) Math.sqrt(3); 
		float actual = u.length();
		Assertions.assertEquals(expected, actual, 0.00001f);
	}

	// TODO - Test normalized
	// TODO - Test angle

	public static void assertionHelper(Float3 expected, Float3 actual) {
		Assertions.assertEquals(expected.x(), actual.x(), 0.00001f);
		Assertions.assertEquals(expected.y(), actual.y(), 0.00001f);
		Assertions.assertEquals(expected.z(), actual.z(), 0.00001f);
	}
}
