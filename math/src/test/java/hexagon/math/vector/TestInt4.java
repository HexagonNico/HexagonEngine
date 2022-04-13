package hexagon.math.vector;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestInt4 {

	static Int4 vec1 = new Int4(1, 3, 2, 4);
	static Int4 vec2 = new Int4(2, 4, 3, 1);
	static Float4 vecFloat = new Float4(0.1f, 0.2f, 0.3f, 0.4f);
	static Int4 nullVec = null;
	static Float4 nullFloatVec = null;

	@Test
	public void testSumValues() {
		Int4 expected = new Int4(3, 7, 5, 5);
		Int4 actual = vec1.plus(2, 4, 3, 1);
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testSumVector() {
		Int4 expected = new Int4(3, 7, 5, 5);
		Int4 actual = vec1.plus(vec2);
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testSumFloatValues() {
		Float4 expected = new Float4(1.1f, 3.2f, 2.3f, 4.4f);
		Float4 actual = vec1.plus(0.1f, 0.2f, 0.3f, 0.4f);
		TestFloat4.assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testSumFloatVector() {
		Float4 expected = new Float4(1.1f, 3.2f, 2.3f, 4.4f);
		Float4 actual = vec1.plus(vecFloat);
		TestFloat4.assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testSumNull() {
		Int4 expected = vec1.plus(Int4.ZERO);
		Int4 actual = vec1.plus(nullVec);
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testSumFloatNull() {
		Float4 expected = vec1.plus(Float4.ZERO);
		Float4 actual = vec1.plus(nullFloatVec);
		TestFloat4.assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testSubtractValues() {
		Int4 expected = new Int4(-1, -1, -1, 3);
		Int4 actual = vec1.minus(2, 4, 3, 1);
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testSubtractVector() {
		Int4 expected = new Int4(-1, -1, -1, 3);
		Int4 actual = vec1.minus(vec2);
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testSubtractFloatValues() {
		Float4 expected = new Float4(0.9f, 2.8f, 1.7f, 3.6f);
		Float4 actual = vec1.minus(0.1f, 0.2f, 0.3f, 0.4f);
		TestFloat4.assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testSubtractFloatVector() {
		Float4 expected = new Float4(0.9f, 2.8f, 1.7f, 3.6f);
		Float4 actual = vec1.minus(vecFloat);
		TestFloat4.assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testSubtractNull() {
		Int4 expected = vec1.minus(Int4.ZERO);
		Int4 actual = vec1.minus(nullVec);
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testSubtractFloatNull() {
		Float4 expected = vec1.minus(Float4.ZERO);
		Float4 actual = vec1.minus(nullFloatVec);
		TestFloat4.assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testNegative() {
		Int4 expected = new Int4(-1, -3, -2, -4);
		Int4 actual = vec1.negative();
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testMultiply() {
		Int4 expected = new Int4(2, 6, 4, 8);
		Int4 actual = vec1.multiply(2);
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testMultiplyFloat() {
		Float4 expected = new Float4(1.5f, 4.5f, 3.0f, 6.0f);
		Float4 actual = vec1.multiply(1.5f);
		TestFloat4.assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testDivide() {
		Int4 expected = new Int4(0, 1, 1, 2);
		Int4 actual = vec1.divide(2);
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testDivideFloat() {
		Float4 expected = new Float4(0.5f, 1.5f, 1.0f, 2.0f);
		Float4 actual = vec1.divide(2.0f);
		TestFloat4.assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testDotValues() {
		int expected = 1 * 2 + 3 * 4 + 2 * 3 + 4 * 1;
		int actual = vec1.dotProduct(2, 4, 3, 1);
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testDotVector() {
		int expected = 1 * 2 + 3 * 4 + 2 * 3 + 4 * 1;
		int actual = vec1.dotProduct(vec2);
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testDotFloatValues() {
		float expected = 1 * 0.1f + 3 * 0.2f + 2 * 0.3f + 4 * 0.4f;
		float actual = vec1.dotProduct(0.1f, 0.2f, 0.3f, 0.4f);
		Assertions.assertEquals(expected, actual, TestFloat4.PRECISION_ERROR);
	}

	@Test
	public void testDotFloatVector() {
		float expected = 1 * 0.1f + 3 * 0.2f + 2 * 0.3f + 4 * 0.4f;
		float actual = vec1.dotProduct(vecFloat);
		Assertions.assertEquals(expected, actual, TestFloat4.PRECISION_ERROR);
	}

	@Test
	public void testAsFloat() {
		Float4 expected = new Float4(1.0f, 3.0f, 2.0f, 4.0f);
		Float4 actual = vec1.asFloat();
		TestFloat4.assertEqualsWithinError(expected, actual);
	}
}
