package hexagon.math.vector;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestInt3 {

	static Int3 vec1 = new Int3(1, 3, 2);
	static Int3 vec2 = new Int3(2, 4, 3);
	static Float3 vecFloat = new Float3(0.1f, 0.2f, 0.3f);
	static Int3 nullVec = null;
	static Float3 nullFloatVec = null;

	@Test
	public void testSumValues() {
		Int3 expected = new Int3(3, 7, 5);
		Int3 actual = vec1.plus(2, 4, 3);
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testSumVector() {
		Int3 expected = new Int3(3, 7, 5);
		Int3 actual = vec1.plus(vec2);
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testSumFloatValues() {
		Float3 expected = new Float3(1.1f, 3.2f, 2.3f);
		Float3 actual = vec1.plus(0.1f, 0.2f, 0.3f);
		TestFloat3.assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testSumFloatVector() {
		Float3 expected = new Float3(1.1f, 3.2f, 2.3f);
		Float3 actual = vec1.plus(vecFloat);
		TestFloat3.assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testSumNull() {
		Int3 expected = vec1.plus(Int3.ZERO);
		Int3 actual = vec1.plus(nullVec);
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testSumFloatNull() {
		Float3 expected = vec1.plus(Float3.ZERO);
		Float3 actual = vec1.plus(nullFloatVec);
		TestFloat3.assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testSubtractValues() {
		Int3 expected = new Int3(-1, -1, -1);
		Int3 actual = vec1.minus(2, 4, 3);
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testSubtractVector() {
		Int3 expected = new Int3(-1, -1, -1);
		Int3 actual = vec1.minus(vec2);
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testSubtractFloatValues() {
		Float3 expected = new Float3(0.9f, 2.8f, 1.7f);
		Float3 actual = vec1.minus(0.1f, 0.2f, 0.3f);
		TestFloat3.assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testSubtractFloatVector() {
		Float3 expected = new Float3(0.9f, 2.8f, 1.7f);
		Float3 actual = vec1.minus(vecFloat);
		TestFloat3.assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testSubtractNull() {
		Int3 expected = vec1.minus(Int3.ZERO);
		Int3 actual = vec1.minus(nullVec);
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testSubtractFloatNull() {
		Float3 expected = vec1.minus(Float3.ZERO);
		Float3 actual = vec1.minus(nullFloatVec);
		TestFloat3.assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testNegative() {
		Int3 expected = new Int3(-1, -3, -2);
		Int3 actual = vec1.negative();
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testMultiply() {
		Int3 expected = new Int3(2, 6, 4);
		Int3 actual = vec1.multiply(2);
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testMultiplyFloat() {
		Float3 expected = new Float3(1.5f, 4.5f, 3.0f);
		Float3 actual = vec1.multiply(1.5f);
		TestFloat3.assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testDivide() {
		Int3 expected = new Int3(0, 1, 1);
		Int3 actual = vec1.divide(2);
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testDivideFloat() {
		Float3 expected = new Float3(0.5f, 1.5f, 1.0f);
		Float3 actual = vec1.divide(2.0f);
		TestFloat3.assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testDotValues() {
		int expected = 1 * 2 + 3 * 4 + 2 * 3;
		int actual = vec1.dotProduct(2, 4, 3);
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testDotVector() {
		int expected = 1 * 2 + 3 * 4 + 2 * 3;
		int actual = vec1.dotProduct(vec2);
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testDotFloatValues() {
		float expected = 1 * 0.1f + 3 * 0.2f + 2 * 0.3f;
		float actual = vec1.dotProduct(0.1f, 0.2f, 0.3f);
		Assertions.assertEquals(expected, actual, TestFloat3.PRECISION_ERROR);
	}

	@Test
	public void testDotFloatVector() {
		float expected = 1 * 0.1f + 3 * 0.2f + 2 * 0.3f;
		float actual = vec1.dotProduct(vecFloat);
		Assertions.assertEquals(expected, actual, TestFloat3.PRECISION_ERROR);
	}

	@Test
	public void testAsFloat() {
		Float3 expected = new Float3(1.0f, 3.0f, 2.0f);
		Float3 actual = vec1.asFloat();
		TestFloat3.assertEqualsWithinError(expected, actual);
	}
}
