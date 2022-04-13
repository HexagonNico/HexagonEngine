package hexagon.math.matrix;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import hexagon.math.vector.Float2;
import hexagon.math.vector.Float3;
import hexagon.math.vector.Float4;

public class TestMatrices {

	@Test
	public void testTranslation2D() {
		Float4 origin = new Float4(2.0f, 1.0f, 0.0f, 1.0f);
		Float2 translation = new Float2(1.0f, 1.0f);
		Matrix4 matrix = Matrices.translation(translation);
		Float4 expected = origin.plus(translation.x(), translation.y(), 0.0f, 0.0f);
		Float4 actual = matrix.multiply(origin);
		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testTranslation3D() {
		Float4 origin = new Float4(2.0f, 1.0f, 4.0f, 1.0f);
		Float3 translation = new Float3(1.0f, 1.0f, 1.0f);
		Matrix4 matrix = Matrices.translation(translation);
		Float4 expected = origin.plus(translation.x(), translation.y(), translation.z(), 0.0f);
		Float4 actual = matrix.multiply(origin);
		Assertions.assertEquals(expected, actual);
	}

	// TODO - Test rotation

	// TODO - Test scale
}
