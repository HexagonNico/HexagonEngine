package hexagon.math.vector;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestInt2 {

	static Int2 vec1 = new Int2(1, 3);
	static Int2 vec2 = new Int2(2, 4);
	static Float2 vecFloat = new Float2(0.1f, 0.2f);
	static Int2 nullVec = null;
	static Float2 nullFloatVec = null;

	@Test
	public void testSumValues() {
		Int2 expected = new Int2(3, 7);
		Int2 actual = vec1.plus(2, 4);
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testSumVector() {
		Int2 expected = new Int2(3, 7);
		Int2 actual = vec1.plus(vec2);
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testSumFloatValues() {
		Float2 expected = new Float2(1.1f, 3.2f);
		Float2 actual = vec1.plus(0.1f, 0.2f);
		TestFloat2.assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testSumFloatVector() {
		Float2 expected = new Float2(1.1f, 3.2f);
		Float2 actual = vec1.plus(vecFloat);
		TestFloat2.assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testSumNull() {
		Int2 expected = vec1.plus(Int2.ZERO);
		Int2 actual = vec1.plus(nullVec);
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testSumFloatNull() {
		Float2 expected = vec1.plus(Float2.ZERO);
		Float2 actual = vec1.plus(nullFloatVec);
		TestFloat2.assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testSubtractValues() {
		Int2 expected = new Int2(-1, -1);
		Int2 actual = vec1.minus(2, 4);
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testSubtractVector() {
		Int2 expected = new Int2(-1, -1);
		Int2 actual = vec1.minus(vec2);
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testSubtractFloatValues() {
		Float2 expected = new Float2(0.9f, 2.8f);
		Float2 actual = vec1.minus(0.1f, 0.2f);
		TestFloat2.assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testSubtractFloatVector() {
		Float2 expected = new Float2(0.9f, 2.8f);
		Float2 actual = vec1.minus(vecFloat);
		TestFloat2.assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testSubtractNull() {
		Int2 expected = vec1.minus(Int2.ZERO);
		Int2 actual = vec1.minus(nullVec);
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testSubtractFloatNull() {
		Float2 expected = vec1.minus(Float2.ZERO);
		Float2 actual = vec1.minus(nullFloatVec);
		TestFloat2.assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testNegative() {
		Int2 expected = new Int2(-1, -3);
		Int2 actual = vec1.negative();
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testMultiply() {
		Int2 expected = new Int2(2, 6);
		Int2 actual = vec1.multiply(2);
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testMultiplyFloat() {
		Float2 expected = new Float2(1.5f, 4.5f);
		Float2 actual = vec1.multiply(1.5f);
		TestFloat2.assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testDivide() {
		Int2 expected = new Int2(0, 1);
		Int2 actual = vec1.divide(2);
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testDivideFloat() {
		Float2 expected = new Float2(0.5f, 1.5f);
		Float2 actual = vec1.divide(2.0f);
		TestFloat2.assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testDotValues() {
		int expected = 1 * 2 + 3 * 4;
		int actual = vec1.dotProduct(2, 4);
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testDotVector() {
		int expected = 1 * 2 + 3 * 4;
		int actual = vec1.dotProduct(vec2);
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testDotFloatValues() {
		float expected = 1 * 0.1f + 3 * 0.2f;
		float actual = vec1.dotProduct(0.1f, 0.2f);
		Assertions.assertEquals(expected, actual, TestFloat2.PRECISION_ERROR);
	}

	@Test
	public void testDotFloatVector() {
		float expected = 1 * 0.1f + 3 * 0.2f;
		float actual = vec1.dotProduct(vecFloat);
		Assertions.assertEquals(expected, actual, TestFloat2.PRECISION_ERROR);
	}

	@Test
	public void testAsFloat() {
		Float2 expected = new Float2(1.0f, 3.0f);
		Float2 actual = vec1.asFloat();
		TestFloat2.assertEqualsWithinError(expected, actual);
	}
}
