package hexagon.engine.math.vector;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestInt2 {
	
	private static final Int2 i1 = new Int2(1, 2);
	private static final Int2 i2 = new Int2(2, 3);
	public static final Float2 f1 = new Float2(1.1f, 2.2f);
	private static final Int2 u = new Int2(1, 1);

	@Test
	@DisplayName("Int2 + Int2")
	public void testIPlusI() {
		Int2 expected = new Int2(3, 5);
		Int2 actual = i1.plus(i2);
		Assertions.assertEquals(expected, actual);
	}

	@Test
	@DisplayName("Int2 + (a, b)")
	public void testFPlusAb() {
		Int2 expected = new Int2(3, 5);
		Int2 actual = i1.plus(2, 3);
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	@DisplayName("Int2 + Float2")
	public void testIPlusF() {
		Float2 expected = new Float2(2.1f, 4.2f);
		Float2 actual = i1.plus(f1);
		TestFloat2.assertionHelper(expected, actual);
	}

	@Test
	@DisplayName("Int2 + (x, y)")
	public void testIPlusXy() {
		Float2 expected = new Float2(2.1f, 4.2f);
		Float2 actual = i1.plus(1.1f, 2.2f);
		TestFloat2.assertionHelper(expected, actual);
	}
	
	@Test
	@DisplayName("-Int2")
	public void testNegative() {
		Int2 expected = new Int2(-1, -2);
		Int2 actual = i1.negative();
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	@DisplayName("Int2 - Int2")
	public void testIMinusI() {
		Int2 expected = new Int2(1, 1);
		Int2 actual = i2.minus(i1);
		Assertions.assertEquals(expected, actual);
	}

	@Test
	@DisplayName("Int2 - (a, b)")
	public void testIMinusAb() {
		Int2 expected = new Int2(1, 1);
		Int2 actual = i2.minus(1, 2);
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	@DisplayName("Int2 - Float2")
	public void testIMinusF() {
		Float2 expected = new Float2(0.9f, 0.8f);
		Float2 actual = i2.minus(f1);
		TestFloat2.assertionHelper(expected, actual);
	}
	
	@Test
	@DisplayName("Int2 - (x, y)")
	public void testIMinusXy() {
		Float2 expected = new Float2(0.9f, 0.8f);
		Float2 actual = i2.minus(1.1f, 2.2f);
		TestFloat2.assertionHelper(expected, actual);
	}
	
	@Test
	@DisplayName("Int2 * n")
	public void testITimesN() {
		Int2 expected = new Int2(2, 4);
		Int2 actual = i1.times(2);
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	@DisplayName("Int2 * k")
	public void testITimesK() {
		Float2 expected = new Float2(1.5f, 3.0f);
		Float2 actual = i1.times(1.5f);
		TestFloat2.assertionHelper(expected, actual);
	}
	
	@Test
	@DisplayName("Int2 / n")
	public void testIDividedByN() {
		Int2 expected = new Int2(0, 1);
		Int2 actual = i1.dividedBy(2);
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	@DisplayName("Int2 / k")
	public void testIDividedByK() {
		Float2 expected = new Float2(0.5f, 1.0f);
		Float2 actual = i1.dividedBy(2.0f);
		TestFloat2.assertionHelper(expected, actual);
	}
	
	@Test
	@DisplayName("Int2 ° Int2")
	public void testIDotI() {
		int expected = 1 * 2 + 2 * 3;
		int actual = i1.dotProduct(i2);
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	@DisplayName("Int2 ° (a, b)")
	public void testIDotAb() {
		int expected = 1 * 2 + 2 * 3;
		int actual = i1.dotProduct(2, 3);
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	@DisplayName("Int2 ° (x, y)")
	public void testIDotXy() {
		float expected = 1 * 2.3f + 2 * 1.7f; 
		float actual = i1.dotProduct(2.3f, 1.7f);
		Assertions.assertEquals(expected, actual, 0.00001f);
	}
	
	@Test
	@DisplayName("Int2 ° Float2")
	public void testIDotF() {
		float expected = 1 * 1.1f + 2 * 2.2f;
		float actual = i1.dotProduct(f1);
		Assertions.assertEquals(expected, actual, 0.00001f);
	}
	
	@Test
	@DisplayName("|Int2|^2")
	public void testLengthSquared() {
		int expected = 2; 
		int actual = u.lengthSquared();
		Assertions.assertEquals(expected, actual);
	}
	
	@Test
	@DisplayName("|Int2|")
	public void testLength() {
		float expected = (float) Math.sqrt(2); 
		float actual = u.length();
		Assertions.assertEquals(expected, actual, 0.00001f);
	}

	// TODO - Test normalized
	// TODO - Test angle
}
