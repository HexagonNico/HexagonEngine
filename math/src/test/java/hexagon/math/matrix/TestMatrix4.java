package hexagon.math.matrix;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import hexagon.math.vector.Float4;

public class TestMatrix4 {

	static Matrix4 mat1 = new Matrix4(
			1.0f, 3.0f, 4.0f, 2.0f,
			2.0f, 5.0f, 3.0f, 1.0f,
			3.0f, 2.0f, 1.0f, 6.0f,
			4.0f, 1.0f, 2.0f, 5.0f
	);

	static Matrix4 mat2 = new Matrix4(
			2.0f, 1.0f, 3.0f, 4.0f,
			3.0f, 2.0f, 4.0f, 1.0f,
			1.0f, 4.0f, 2.0f, 3.0f,
			4.0f, 3.0f, 1.0f, 2.0f
	);

	@Test
	public void testSum() {
		Matrix4 expected = new Matrix4(
				3.0f, 4.0f, 7.0f, 6.0f,
				5.0f, 7.0f, 7.0f, 2.0f,
				4.0f, 6.0f, 3.0f, 9.0f,
				8.0f, 4.0f, 3.0f, 7.0f
		);
		Assertions.assertEquals(expected, mat1.plus(mat2));
	}

	@Test
	public void testSumNull() {
		Matrix4 expected = mat1.plus(Matrix4.ZERO);
		Matrix4 actual = mat1.plus(null);
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testSubtract() {
		Matrix4 expected = new Matrix4(
				-1.0f, 2.0f, 1.0f, -2.0f,
				-1.0f, 3.0f, -1.0f, 0.0f,
				2.0f, -2.0f, -1.0f, 3.0f,
				0.0f, -2.0f, 1.0f, 3.0f
		);
		Assertions.assertEquals(expected, mat1.minus(mat2));
	}

	@Test
	public void testSubtractNull() {
		Matrix4 expected = mat1.minus(Matrix4.ZERO);
		Matrix4 actual = mat1.minus(null);
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testMultiplyScalar() {
		Matrix4 expected = new Matrix4(
				2.0f, 6.0f, 8.0f, 4.0f,
				4.0f, 10.0f, 6.0f, 2.0f,
				6.0f, 4.0f, 2.0f, 12.0f,
				8.0f, 2.0f, 4.0f, 10.0f
		);
		Assertions.assertEquals(expected, mat1.multiply(2.0f));
	}

	@Test
	public void testMultiplyVector() {
		Float4 expected = new Float4(27, 25, 34, 32);
		Float4 actual = mat1.multiply(new Float4(1, 2, 3, 4));
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testGetRow() {
		Assertions.assertEquals(new Float4(1.0f, 3.0f, 4.0f, 2.0f), mat1.row(0));
		Assertions.assertEquals(new Float4(2.0f, 5.0f, 3.0f, 1.0f), mat1.row(1));
		Assertions.assertEquals(new Float4(3.0f, 2.0f, 1.0f, 6.0f), mat1.row(2));
		Assertions.assertEquals(new Float4(4.0f, 1.0f, 2.0f, 5.0f), mat1.row(3));
		Assertions.assertEquals(new Float4(0.0f, 0.0f, 0.0f, 0.0f), mat1.row(5));
	}

	@Test
	public void testGetColumn() {
		Assertions.assertEquals(new Float4(1.0f, 2.0f, 3.0f, 4.0f), mat1.column(0));
		Assertions.assertEquals(new Float4(3.0f, 5.0f, 2.0f, 1.0f), mat1.column(1));
		Assertions.assertEquals(new Float4(4.0f, 3.0f, 1.0f, 2.0f), mat1.column(2));
		Assertions.assertEquals(new Float4(2.0f, 1.0f, 6.0f, 5.0f), mat1.column(3));
		Assertions.assertEquals(new Float4(0.0f, 0.0f, 0.0f, 0.0f), mat1.column(5));
	}

	// TODO - Test product

	@Test
	public void testProductNull() {
		Matrix4 m = null;
		Assertions.assertEquals(Matrix4.ZERO, mat1.multiply(m));
	}

	@Test
	public void testTransposed() {
		Matrix4 expected = new Matrix4(
				1.0f, 2.0f, 3.0f, 4.0f,
				3.0f, 5.0f, 2.0f, 1.0f,
				4.0f, 3.0f, 1.0f, 2.0f,
				2.0f, 1.0f, 6.0f, 5.0f
		);
		Assertions.assertEquals(expected, mat1.transposed());
	}
}
