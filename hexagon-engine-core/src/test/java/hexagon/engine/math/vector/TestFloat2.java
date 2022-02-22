package hexagon.engine.math.vector;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestFloat2 {
	
	private static final Float2 f1 = new Float2(1.1f, 2.5f);
	private static final Float2 f2 = new Float2(2.3f, 1.7f);
	public static final Int2 i1 = new Int2(1, 2);
	private static final Float2 u = new Float2(1.0f, 1.0f);

	@Test
	@DisplayName("Float2 + Float2")
	public void testFPlusF() {
		Float2 expected = new Float2(3.4f, 4.2f);
		Float2 actual = f1.plus(f2);
		assertionHelper(expected, actual);
	}

	@Test
	@DisplayName("Float2 + (x, y, z)")
	public void testFPlusXyz() {
		Float2 expected = new Float2(3.4f, 4.2f);
		Float2 actual = f1.plus(2.3f, 1.7f);
		assertionHelper(expected, actual);
	}
	
	@Test
	@DisplayName("Float2 + Int2")
	public void testFPlusI() {
		Float2 expected = new Float2(2.1f, 4.5f);
		Float2 actual = f1.plus(i1);
		assertionHelper(expected, actual);
	}
	
	@Test
	@DisplayName("-Float2")
	public void testNegative() {
		Float2 expected = new Float2(-1.1f, -2.5f);
		Float2 actual = f1.negative();
		assertionHelper(expected, actual);
	}
	
	@Test
	@DisplayName("Float2 - Float2")
	public void testFMinusF() {
		Float2 expected = new Float2(1.2f, -0.8f);
		Float2 actual = f2.minus(f1);
		assertionHelper(expected, actual);
	}

	@Test
	@DisplayName("Float2 - (x, y)")
	public void testFMinusXy() {
		Float2 expected = new Float2(1.2f, -0.8f);
		Float2 actual = f2.minus(1.1f, 2.5f);
		assertionHelper(expected, actual);
	}
	
	@Test
	@DisplayName("Float2 - Int2")
	public void testFMinusI() {
		Float2 expected = new Float2(0.1f, 0.5f);
		Float2 actual = f1.minus(i1);
		assertionHelper(expected, actual);
	}
	
	@Test
	@DisplayName("Float2 * k")
	public void testFTimesK() {
		Float2 expected = new Float2(2.2f, 5.0f);
		Float2 actual = f1.times(2.0f);
		assertionHelper(expected, actual);
	}
	
	@Test
	@DisplayName("Float2 / k")
	public void testFDividedByK() {
		Float2 expected = new Float2(0.55f, 1.25f);
		Float2 actual = f1.dividedBy(2.0f);
		assertionHelper(expected, actual);
	}
	
	@Test
	@DisplayName("Float2 ° Float2")
	public void testFDotF() {
		float expected = 1.1f * 2.3f + 2.5f * 1.7f;
		float actual = f1.dotProduct(f2);
		Assertions.assertEquals(expected, actual, 0.00001f);
	}
	
	@Test
	@DisplayName("Float2 ° (x, y)")
	public void testFDotXy() {
		float expected = 1.1f * 2.3f + 2.5f * 1.7f;
		float actual = f1.dotProduct(2.3f, 1.7f);
		Assertions.assertEquals(expected, actual, 0.00001f);
	}
	
	@Test
	@DisplayName("Float2 ° Int2")
	public void testFDotI() {
		float expected = 1.1f * 1 + 2.5f * 2; 
		float actual = f1.dotProduct(i1);
		Assertions.assertEquals(expected, actual, 0.00001f);
	}
	
	@Test
	@DisplayName("|Float2|^2")
	public void testLengthSquared() {
		float expected = 2; 
		float actual = u.lengthSquared();
		Assertions.assertEquals(expected, actual, 0.00001f);
	}
	
	@Test
	@DisplayName("|Float2|")
	public void testLength() {
		float expected = (float) Math.sqrt(2); 
		float actual = u.length();
		Assertions.assertEquals(expected, actual, 0.00001f);
	}

	// TODO - Test normalized
	// TODO - Test angle

	public static void assertionHelper(Float2 expected, Float2 actual) {
		Assertions.assertEquals(expected.x(), actual.x(), 0.00001f);
		Assertions.assertEquals(expected.y(), actual.y(), 0.00001f);
	}
}
