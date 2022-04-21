package hexagon.math.geometry;

import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import hexagon.math.vector.Float2;

public class TestRectangle {

	@ParameterizedTest
	@CsvSource({"0,4,3,0, 0,4,3,0", "4,0,3,0, 0,4,3,0", "0,4,0,3, 0,4,3,0", "4,0,0,3, 0,4,0,3"})
	public void testConstruct(float l, float r, float t, float b, float el, float er, float et, float eb) {
		Rectangle expected = new Rectangle(el, er, et, eb);
		Rectangle actual = new Rectangle(l, r, t, b);
		Assertions.assertEquals(expected, actual);
	}

	private static Stream<Arguments> valuesForConstructTest() {
		return Stream.of(
			Arguments.of(new Float2(0.0f, 2.0f), new Float2(4.0f, 5.0f), new Float2(0.0f, 2.0f), new Float2(4.0f, 5.0f)),
			Arguments.of(new Float2(0.0f, 5.0f), new Float2(4.0f, 2.0f), new Float2(0.0f, 2.0f), new Float2(4.0f, 5.0f)),
			Arguments.of(new Float2(4.0f, 2.0f), new Float2(0.0f, 5.0f), new Float2(0.0f, 2.0f), new Float2(4.0f, 5.0f)),
			Arguments.of(new Float2(4.0f, 5.0f), new Float2(0.0f, 2.0f), new Float2(0.0f, 2.0f), new Float2(4.0f, 5.0f)),
			Arguments.of(null, new Float2(4.0f, 5.0f), Float2.ZERO, new Float2(4.0f, 5.0f)),
			Arguments.of(new Float2(-1.0f, -2.0f), null, new Float2(-1.0f, -2.0f), Float2.ZERO),
			Arguments.of(null, null, Float2.ZERO, Float2.ZERO)
		);
	}

	@ParameterizedTest
	@MethodSource("valuesForConstructTest")
	public void testConstructWithVectors(Float2 bottomLeft, Float2 topRight, Float2 expectedBottomLeft, Float2 expectedTopRight) {
		Rectangle expected = new Rectangle(expectedBottomLeft, expectedTopRight);
		Rectangle actual = new Rectangle(bottomLeft, topRight);
		Assertions.assertEquals(expected, actual);
	}

	private static Stream<Arguments> valuesForCenterAndSizeTest() {
		return Stream.of(
			Arguments.of(Float2.ZERO, new Size(4.0f, 3.0f), -2.0f, 2.0f, 1.5f, -1.5f),
			Arguments.of(null, new Size(4.0f, 3.0f), -2.0f, 2.0f, 1.5f, -1.5f),
			Arguments.of(new Float2(1.0f, 1.0f), null, 1.0f, 1.0f, 1.0f, 1.0f),
			Arguments.of(null, null, 0.0f, 0.0f, 0.0f, 0.0f)
		);
	}

	@ParameterizedTest
	@MethodSource("valuesForCenterAndSizeTest")
	public void testConstructWithCenterAndSize(Float2 center, Size size, float left, float right, float top, float bottom) {
		Rectangle expected = new Rectangle(left, right, top, bottom);
		Rectangle actual = new Rectangle(center, size);
		Assertions.assertEquals(expected, actual);
	}

	static Rectangle rectangle = new Rectangle(Float2.ZERO, new Size(4.0f, 3.0f));

	@Test
	public void testBottomLeft() {
		Float2 expected = new Float2(-2.0f, -1.5f);
		Assertions.assertEquals(expected, rectangle.bottomLeft());
	}

	@Test
	public void testTopLeft() {
		Float2 expected = new Float2(-2.0f, 1.5f);
		Assertions.assertEquals(expected, rectangle.topLeft());
	}

	@Test
	public void testBottomRight() {
		Float2 expected = new Float2(2.0f, -1.5f);
		Assertions.assertEquals(expected, rectangle.bottomRight());
	}

	@Test
	public void testTopRight() {
		Float2 expected = new Float2(2.0f, 1.5f);
		Assertions.assertEquals(expected, rectangle.topRight());
	}

	@Test
	public void testSize() {
		Size expected = new Size(4.0f, 3.0f);
		Assertions.assertEquals(expected, rectangle.size());
	}

	@Test
	public void testCenter() {
		Float2 expected = Float2.ZERO;
		Assertions.assertEquals(expected, rectangle.center());
	}

	@Test
	public void testTranslated() {
		Rectangle expected = new Rectangle(new Float2(2.0f, 1.5f), new Size(4.0f, 3.0f));
		Rectangle actual = rectangle.translated(2.0f, 1.5f);
		Assertions.assertEquals(expected, actual);
	}

	private static Stream<Arguments> valuesForIntersectsTest() {
		return Stream.of(
			Arguments.of(rectangle, new Rectangle(new Float2(2.0f, 1.5f), new Size(4.0f, 3.0f)), true),
			Arguments.of(rectangle, new Rectangle(new Float2(12.0f, 21.5f), new Size(1.0f, 1.0f)), false)
		);
	}

	@ParameterizedTest
	@MethodSource("valuesForIntersectsTest")
	public void testIntersects(Rectangle r1, Rectangle r2, boolean result) {
		Assertions.assertEquals(r1.intersects(r2), result);
	}
}