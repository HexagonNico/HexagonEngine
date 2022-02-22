package hexagon.engine.math.matrix;

import hexagon.engine.math.vector.FloatVector;

public interface Matrix<M extends Matrix<M, V>, V extends FloatVector<V, ?>> {
	
	// TODO - Matrix tests

	M plus(M matrix);

	M negative();

	default M minus(M matrix) {
		return this.plus(matrix.negative());
	}

	M multiply(float k);

	default M divide(float k) {
		return this.multiply(1.0f / k);
	}

	V multiply(V vector);

	V row(int i);

	V column(int i);

	M multiply(M matrix);

	M transposed();

	boolean isSymmetric();

	boolean isSkewSymmetric();

	M power(int exponent);
}
