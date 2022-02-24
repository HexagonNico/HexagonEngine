package hexagon.engine.math.matrix;

import hexagon.engine.math.vector.FloatVector;

/**
 * Interface containing operations for matrices
 */
public interface Matrix<M extends Matrix<M, V>, V extends FloatVector<V, ?>> {
	
	// TODO - Matrix tests

	/**
	 * Computes the sum of two matrices and returns the result.
	 * 
	 * @param matrix The second matrix
	 * 
	 * @return The sum of the two matrices
	 */
	M plus(M matrix);

	/**
	 * Gets the additive inverse for this matrix.
	 * 
	 * @return The additive inverse for this matrix
	 */
	M negative();

	/**
	 * Computes the difference between two matrices and returns the result.
	 * 
	 * @param matrix The second matrix
	 * 
	 * @return The difference between the two matrices
	 */
	default M minus(M matrix) {
		return this.plus(matrix.negative());
	}

	/**
	 * Multiplies this matrix by a scalar and returns the result.
	 * 
	 * @param k The scalar
	 * 
	 * @return The product between this matrix and the given number
	 */
	M multiply(float k);

	/**
	 * Divides this matrix by a scalar and returns the result,
	 * which is the same as multiplying the matrix by the inverse of the scalar
	 * 
	 * @param k The scalar
	 * 
	 * @return The product between this matrix and the inverse of the given number
	 */
	default M divide(float k) {
		return this.multiply(1.0f / k);
	}

	/**
	 * Multiplies this matrix by a vector and returns the result.
	 * 
	 * @param vector The vector
	 * 
	 * @return The result of the product
	 */
	V multiply(V vector);

	/**
	 * Gets a row from this matrix.
	 * 
	 * @param i Index of the row
	 * 
	 * @return A row vector
	 * 
	 * @throws IndexOutOfBoundsException if i is not a row of this matrix
	 */
	V row(int i);

	/**
	 * Gets a column from this matrix.
	 * 
	 * @param i Index of the column
	 * 
	 * @return A column vector
	 * 
	 * @throws IndexOutOfBoundsException if i is not a column of this matrix
	 */
	V column(int i);

	/**
	 * Multiplies two matrices and returns the result.
	 * 
	 * @param matrix The second matrix
	 * 
	 * @return The product of the two matrices
	 */
	M multiply(M matrix);

	/**
	 * Returns the transposition of this matrix.
	 * 
	 * @return The transposed matrix.
	 */
	M transposed();

	/**
	 * Checks if this matrix is symmetric.
	 * 
	 * @return True if this matrix is symmetric, otherwise false
	 */
	boolean isSymmetric();

	/**
	 * Checks if this matrix is skew-symmetric.
	 * 
	 * @return True if this matrix is skew-symmetric, otherwise false
	 */
	boolean isSkewSymmetric();

	/**
	 * Computes a power of this matrix.
	 * 
	 * @param exponent Exponent of the power
	 * 
	 * @return The result of the power
	 */
	M power(int exponent);
}
