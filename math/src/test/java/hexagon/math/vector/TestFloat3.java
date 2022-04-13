package hexagon.math.vector;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestFloat3 {

	public static final float PRECISION_ERROR = 0.0001f;

	static Float3 vec1 = new Float3(1.2f, 3.4f, 0.6f);
	static Float3 vec2 = new Float3(0.5f, 1.5f, 2.0f);
	static Int3 ivec1 = new Int3(1, 2, 3);
	static Float3 nullVec = null;
	static Int3 iNullVec = null;

	@Test
	public void testSumValues() {
		Float3 expected = new Float3(1.7f, 4.9f, 2.6f);
		Float3 actual = vec1.plus(0.5f, 1.5f, 2.0f);
		assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testSumVector() {
		Float3 expected = new Float3(1.7f, 4.9f, 2.6f);
		Float3 actual = vec1.plus(vec2);
		assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testSumInt() {
		Float3 expected = new Float3(2.2f, 5.4f, 3.6f);
		Float3 actual = vec1.plus(ivec1);
		assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testSumNull() {
		Float3 expected = vec1.plus(Float3.ZERO);
		Float3 actual = vec1.plus(nullVec);
		assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testSumNullInt() {
		Float3 expected = vec1.plus(Int3.ZERO);
		Float3 actual = vec1.plus(iNullVec);
		assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testSubtractValues() {
		Float3 expected = new Float3(0.7f, 1.9f, -1.4f);
		Float3 actual = vec1.minus(0.5f, 1.5f, 2.0f);
		assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testSubtractVector() {
		Float3 expected = new Float3(0.7f, 1.9f, -1.4f);
		Float3 actual = vec1.minus(vec2);
		assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testSubtractInt() {
		Float3 expected = new Float3(0.2f, 1.4f, -2.4f);
		Float3 actual = vec1.minus(ivec1);
		assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testSubtractNull() {
		Float3 expected = vec1.minus(Float3.ZERO);
		Float3 actual = vec1.minus(nullVec);
		assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testSubtractNullInt() {
		Float3 expected = vec1.minus(Int3.ZERO);
		Float3 actual = vec1.minus(iNullVec);
		assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testNegative() {
		Float3 expected = new Float3(-1.2f, -3.4f, -0.6f);
		Float3 actual = vec1.negative();
		assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testMultiply() {
		Float3 expected = new Float3(2.4f, 6.8f, 1.2f);
		Float3 actual = vec1.multiply(2.0f);
		assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testDivide() {
		Float3 expected = new Float3(0.6f, 1.7f, 0.3f);
		Float3 actual = vec1.divide(2.0f);
		assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testDotValues() {
		float expected = 1.2f * 0.5f + 3.4f * 1.5f + 0.6f * 2.0f;
		float actual = vec1.dotProduct(0.5f, 1.5f, 2.0f);
		Assertions.assertEquals(expected, actual, PRECISION_ERROR);
	}

	@Test
	public void testDotVector() {
		float expected = 1.2f * 0.5f + 3.4f * 1.5f + 0.6f * 2.0f;
		float actual = vec1.dotProduct(vec2);
		Assertions.assertEquals(expected, actual, PRECISION_ERROR);
	}

	@Test
	public void testDotInt() {
		float expected = 1.2f * 1 + 3.4f * 2 + 0.6f * 3;
		float actual = vec1.dotProduct(ivec1);
		Assertions.assertEquals(expected, actual, PRECISION_ERROR);
	}

	@Test
	public void testDotNull() {
		float expected = vec1.dotProduct(Float3.ZERO);
		float actual = vec1.dotProduct(nullVec);
		Assertions.assertEquals(expected, actual, PRECISION_ERROR);
	}

	@Test
	public void testDotNullInt() {
		float expected = vec1.dotProduct(Int3.ZERO);
		float actual = vec1.dotProduct(iNullVec);
		Assertions.assertEquals(expected, actual, PRECISION_ERROR);
	}

	@Test
	public void testCast() {
		Int3 expected = new Int3(1, 3, 0);
		Int3 actual = vec1.castToInt();
		Assertions.assertEquals(expected, actual);
	}

	public static void assertEqualsWithinError(Float3 expected, Float3 actual)  {
		Assertions.assertEquals(expected.x(), actual.x(), PRECISION_ERROR);
		Assertions.assertEquals(expected.y(), actual.y(), PRECISION_ERROR);
		Assertions.assertEquals(expected.z(), actual.z(), PRECISION_ERROR);
	}
}
