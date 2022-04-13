package hexagon.math.vector;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestFloat2 {

	public static final float PRECISION_ERROR = 0.0001f;

	static Float2 vec1 = new Float2(1.2f, 3.4f);
	static Float2 vec2 = new Float2(0.5f, 1.5f);
	static Int2 ivec1 = new Int2(1, 2);
	static Float2 nullVec = null;
	static Int2 iNullVec = null;

	@Test
	public void testSumValues() {
		Float2 expected = new Float2(1.7f, 4.9f);
		Float2 actual = vec1.plus(0.5f, 1.5f);
		assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testSumVector() {
		Float2 expected = new Float2(1.7f, 4.9f);
		Float2 actual = vec1.plus(vec2);
		assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testSumInt() {
		Float2 expected = new Float2(2.2f, 5.4f);
		Float2 actual = vec1.plus(ivec1);
		assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testSumNull() {
		Float2 expected = vec1.plus(Float2.ZERO);
		Float2 actual = vec1.plus(nullVec);
		assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testSumNullInt() {
		Float2 expected = vec1.plus(Int2.ZERO);
		Float2 actual = vec1.plus(iNullVec);
		assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testSubtractValues() {
		Float2 expected = new Float2(0.7f, 1.9f);
		Float2 actual = vec1.minus(0.5f, 1.5f);
		assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testSubtractVector() {
		Float2 expected = new Float2(0.7f, 1.9f);
		Float2 actual = vec1.minus(vec2);
		assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testSubtractInt() {
		Float2 expected = new Float2(0.2f, 1.4f);
		Float2 actual = vec1.minus(ivec1);
		assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testSubtractNull() {
		Float2 expected = vec1.minus(Float2.ZERO);
		Float2 actual = vec1.minus(nullVec);
		assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testSubtractNullInt() {
		Float2 expected = vec1.minus(Int2.ZERO);
		Float2 actual = vec1.minus(iNullVec);
		assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testNegative() {
		Float2 expected = new Float2(-1.2f, -3.4f);
		Float2 actual = vec1.negative();
		assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testMultiply() {
		Float2 expected = new Float2(2.4f, 6.8f);
		Float2 actual = vec1.multiply(2.0f);
		assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testDivide() {
		Float2 expected = new Float2(0.6f, 1.7f);
		Float2 actual = vec1.divide(2.0f);
		assertEqualsWithinError(expected, actual);
	}

	@Test
	public void testDotValues() {
		float expected = 1.2f * 0.5f + 3.4f * 1.5f;
		float actual = vec1.dotProduct(0.5f, 1.5f);
		Assertions.assertEquals(expected, actual, PRECISION_ERROR);
	}

	@Test
	public void testDotVector() {
		float expected = 1.2f * 0.5f + 3.4f * 1.5f;
		float actual = vec1.dotProduct(vec2);
		Assertions.assertEquals(expected, actual, PRECISION_ERROR);
	}

	@Test
	public void testDotInt() {
		float expected = 1.2f * 1 + 3.4f * 2;
		float actual = vec1.dotProduct(ivec1);
		Assertions.assertEquals(expected, actual, PRECISION_ERROR);
	}

	@Test
	public void testDotNull() {
		float expected = vec1.dotProduct(Float2.ZERO);
		float actual = vec1.dotProduct(nullVec);
		Assertions.assertEquals(expected, actual, PRECISION_ERROR);
	}

	@Test
	public void testDotNullInt() {
		float expected = vec1.dotProduct(Int2.ZERO);
		float actual = vec1.dotProduct(iNullVec);
		Assertions.assertEquals(expected, actual, PRECISION_ERROR);
	}

	@Test
	public void testCast() {
		Int2 expected = new Int2(1, 3);
		Int2 actual = vec1.castToInt();
		Assertions.assertEquals(expected, actual);
	}

	public static void assertEqualsWithinError(Float2 expected, Float2 actual)  {
		Assertions.assertEquals(expected.x(), actual.x(), PRECISION_ERROR);
		Assertions.assertEquals(expected.y(), actual.y(), PRECISION_ERROR);
	}
}
