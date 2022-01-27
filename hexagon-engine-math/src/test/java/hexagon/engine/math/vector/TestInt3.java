package hexagon.engine.math.vector;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestInt3 {
	
	private static final Int3 i1 = new Int3(1, 2, 3);
	private static final Int3 i2 = new Int3(2, 3, 1);
	public static final Float3 f1 = new Float3(1.1f, 2.2f, 3.3f);
	private static final Int3 u = new Int3(1, 1, 1);

	@Test
	@DisplayName("Int3 + Int3")
	public void testIPlusI() {
		Int3 expected = new Int3(3, 5, 4);
		Int3 actual = i1.plus(i2);
		Assertions.assertEquals(expected, actual);
	}

	@Test
	@DisplayName("Int3 + (a, b, c)")
	public void testFPlusAbc() {
		Int3 expected = new Int3(3, 5, 4);
		Int3 actual = i1.plus(2, 3, 1);
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	@DisplayName("Int3 + Float3")
	public void testIPlusF() {
		Float3 expected = new Float3(2.1f, 4.2f, 6.3f);
		Float3 actual = i1.plus(f1);
		TestFloat3.assertionHelper(expected, actual);
	}

	@Test
	@DisplayName("Int3 + (x, y, z)")
	public void testIPlusXyz() {
		Float3 expected = new Float3(2.1f, 4.2f, 6.3f);
		Float3 actual = i1.plus(1.1f, 2.2f, 3.3f);
		TestFloat3.assertionHelper(expected, actual);
	}
	
	@Test
	@DisplayName("-Int3")
	public void testNegative() {
		Int3 expected = new Int3(-1, -2, -3);
		Int3 actual = i1.negative();
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	@DisplayName("Int3 - Int3")
	public void testIMinusI() {
		Int3 expected = new Int3(1, 1, -2);
		Int3 actual = i2.minus(i1);
		Assertions.assertEquals(expected, actual);
	}

	@Test
	@DisplayName("Int3 - (a, b, c)")
	public void testIMinusAbc() {
		Int3 expected = new Int3(1, 1, -2);
		Int3 actual = i2.minus(1, 2, 3);
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	@DisplayName("Int3 - Float3")
	public void testIMinusF() {
		Float3 expected = new Float3(0.9f, 0.8f, -2.3f);
		Float3 actual = i2.minus(f1);
		TestFloat3.assertionHelper(expected, actual);
	}
	
	@Test
	@DisplayName("Int3 - (x, y, z)")
	public void testIMinusXyz() {
		Float3 expected = new Float3(0.9f, 0.8f, -2.3f);
		Float3 actual = i2.minus(1.1f, 2.2f, 3.3f);
		TestFloat3.assertionHelper(expected, actual);
	}
	
	@Test
	@DisplayName("Int3 * n")
	public void testITimesN() {
		Int3 expected = new Int3(2, 4, 6);
		Int3 actual = i1.times(2);
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	@DisplayName("Int3 * k")
	public void testITimesK() {
		Float3 expected = new Float3(1.5f, 3.0f, 4.5f);
		Float3 actual = i1.times(1.5f);
		TestFloat3.assertionHelper(expected, actual);
	}
	
	@Test
	@DisplayName("Int3 / n")
	public void testIDividedByN() {
		Int3 expected = new Int3(0, 1, 1);
		Int3 actual = i1.dividedBy(2);
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	@DisplayName("Int3 / k")
	public void testIDividedByK() {
		Float3 expected = new Float3(0.5f, 1.0f, 1.5f);
		Float3 actual = i1.dividedBy(2.0f);
		TestFloat3.assertionHelper(expected, actual);
	}
	
	@Test
	@DisplayName("Int3 ° Int3")
	public void testIDotI() {
		int expected = 1 * 2 + 2 * 3 + 3 * 1;
		int actual = i1.dotProduct(i2);
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	@DisplayName("Int3 ° (a, b, c)")
	public void testIDotAbc() {
		int expected = 1 * 2 + 2 * 3 + 3 * 1;
		int actual = i1.dotProduct(2, 3, 1);
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	@DisplayName("Int3 ° (x, y, z)")
	public void testIDotXyz() {
		float expected = 1 * 2.3f + 2 * 1.7f + 3 * 0.9f; 
		float actual = i1.dotProduct(2.3f, 1.7f, 0.9f);
		Assertions.assertEquals(expected, actual, 0.00001f);
	}
	
	@Test
	@DisplayName("Int3 ° Float3")
	public void testIDotF() {
		float expected = 1 * 1.1f + 2 * 2.2f + 3 * 3.3f;
		float actual = i1.dotProduct(f1);
		Assertions.assertEquals(expected, actual, 0.00001f);
	}
	
	@Test
	@DisplayName("|Int3|^2")
	public void testLengthSquared() {
		int expected = 3; 
		int actual = u.lengthSquared();
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	@DisplayName("|Int3|")
	public void testLength() {
		float expected = (float) Math.sqrt(3); 
		float actual = u.length();
		Assertions.assertEquals(expected, actual, 0.00001f);
	}

	// TODO - Test normalized
	// TODO - Test angle
}
