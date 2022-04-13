package hexagon.math.vector;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestFloat4 {

	public static final float PRECISION_ERROR = 0.0001f;

	static Float4 vec1 = new Float4(1.2f, 3.4f, 0.6f, -1.5f);
	static Float4 vec2 = new Float4(0.5f, 1.5f, 2.0f, 3.2f);
	static Int4 ivec1 = new Int4(1, 2, 3, 4);
	static Float4 nullVec = null;
	static Int4 iNullVec = null;

	@Test
	public void testSumValues() {
		Float4 expected = new Float4(1.7f, 4.9f, 2.6f, 1.7f);
		Float4 actual = vec1.plus(0.5f, 1.5f, 2.0f, 3.2f);
		assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testSumVector() {
		Float4 expected = new Float4(1.7f, 4.9f, 2.6f, 1.7f);
		Float4 actual = vec1.plus(vec2);
		assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testSumInt() {
		Float4 expected = new Float4(2.2f, 5.4f, 3.6f, 3.5f);
		Float4 actual = vec1.plus(ivec1);
		assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testSumNull() {
		Float4 expected = vec1.plus(Float4.ZERO);
		Float4 actual = vec1.plus(nullVec);
		assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testSumNullInt() {
		Float4 expected = vec1.plus(Int4.ZERO);
		Float4 actual = vec1.plus(iNullVec);
		assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testSubtractValues() {
		Float4 expected = new Float4(0.7f, 1.9f, -1.4f, -4.7f);
		Float4 actual = vec1.minus(0.5f, 1.5f, 2.0f, 3.2f);
		assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testSubtractVector() {
		Float4 expected = new Float4(0.7f, 1.9f, -1.4f, -4.7f);
		Float4 actual = vec1.minus(vec2);
		assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testSubtractInt() {
		Float4 expected = new Float4(0.2f, 1.4f, -2.4f, -5.5f);
		Float4 actual = vec1.minus(ivec1);
		assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testSubtractNull() {
		Float4 expected = vec1.minus(Float4.ZERO);
		Float4 actual = vec1.minus(nullVec);
		assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testSubtractNullInt() {
		Float4 expected = vec1.minus(Int4.ZERO);
		Float4 actual = vec1.minus(iNullVec);
		assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testNegative() {
		Float4 expected = new Float4(-1.2f, -3.4f, -0.6f, 1.5f);
		Float4 actual = vec1.negative();
		assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testMultiply() {
		Float4 expected = new Float4(2.4f, 6.8f, 1.2f, -3.0f);
		Float4 actual = vec1.multiply(2.0f);
		assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testDivide() {
		Float4 expected = new Float4(0.6f, 1.7f, 0.3f, -0.75f);
		Float4 actual = vec1.divide(2.0f);
		assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testDotValues() {
		float expected = 1.2f * 0.5f + 3.4f * 1.5f + 0.6f * 2.0f - 1.5f * 3.2f;
		float actual = vec1.dotProduct(0.5f, 1.5f, 2.0f, 3.2f);
		Assertions.assertEquals(expected, actual, PRECISION_ERROR);
	}

	@Test
	public void testDotVector() {
		float expected = 1.2f * 0.5f + 3.4f * 1.5f + 0.6f * 2.0f - 1.5f * 3.2f;
		float actual = vec1.dotProduct(vec2);
		Assertions.assertEquals(expected, actual, PRECISION_ERROR);
	}

	@Test
	public void testDotInt() {
		float expected = 1.2f * 1 + 3.4f * 2 + 0.6f * 3 - 1.5f * 4;
		float actual = vec1.dotProduct(ivec1);
		Assertions.assertEquals(expected, actual, PRECISION_ERROR);
	}

	@Test
	public void testDotNull() {
		float expected = vec1.dotProduct(Float4.ZERO);
		float actual = vec1.dotProduct(nullVec);
		Assertions.assertEquals(expected, actual, PRECISION_ERROR);
	}

	@Test
	public void testDotNullInt() {
		float expected = vec1.dotProduct(Int4.ZERO);
		float actual = vec1.dotProduct(iNullVec);
		Assertions.assertEquals(expected, actual, PRECISION_ERROR);
	}

	@Test
	public void testCast() {
		Int4 expected = new Int4(1, 3, 0, -1);
		Int4 actual = vec1.castToInt();
		Assertions.assertEquals(expected, actual);
	}

	public static void assertEqualsWithinError(Float4 expected, Float4 actual)  {
		Assertions.assertEquals(expected.x(), actual.x(), PRECISION_ERROR);
		Assertions.assertEquals(expected.y(), actual.y(), PRECISION_ERROR);
		Assertions.assertEquals(expected.z(), actual.z(), PRECISION_ERROR);
	}
}
