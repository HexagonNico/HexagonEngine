package hexagon.math.matrix;

import hexagon.math.vector.Float4;

/**
 * Record that represents a 4x4 matrix.
 * 
 * <p>
 * 	Matrices are designed to be immutable.
 * 	Operations on matrices are supposed to work as operation on primitive types,
 * 	i.e., they do not affect the matrix they are called on, but return a new matrix instead.
 * </p>
 * 
 * @author Nico
 */
public record Matrix4(float m00, float m01, float m02, float m03, float m10, float m11, float m12, float m13, float m20, float m21, float m22, float m23,float m30, float m31, float m32, float m33) {

	/**Shorthand for a matrix with only zeros */
	public static final Matrix4 ZERO = new Matrix4(
			0.0f, 0.0f, 0.0f, 0.0f,
			0.0f, 0.0f, 0.0f, 0.0f,
			0.0f, 0.0f, 0.0f, 0.0f,
			0.0f, 0.0f, 0.0f, 0.0f
	);
	
	/**Matrix with only ones on the diagonal and zeros in the other cells */
	public static final Matrix4 IDENTITY = new Matrix4(
			1.0f, 0.0f, 0.0f, 0.0f,
			0.0f, 1.0f, 0.0f, 0.0f,
			0.0f, 0.0f, 1.0f, 0.0f,
			0.0f, 0.0f, 0.0f, 1.0f
	);

	/**
	 * Computes the sum of this matrix and the given one.
	 * The sum of two matrices M1 and M2 is a matrix N with every value N[i,j] is the sum of the values M1[i,j] and M2[i,j] of the two matrices.
	 * 
	 * @param matrix The second matrix in the sum
	 * 
	 * @return The result of the sum or a copy of this matrix if the given one is {@code null}
	 */
	public Matrix4 plus(Matrix4 matrix) {
		return matrix != null ? new Matrix4(
			this.m00() + matrix.m00(), this.m01() + matrix.m01(), this.m02() + matrix.m02(), this.m03() + matrix.m03(),
			this.m10() + matrix.m10(), this.m11() + matrix.m11(), this.m12() + matrix.m12(), this.m13() + matrix.m13(),
			this.m20() + matrix.m20(), this.m21() + matrix.m21(), this.m22() + matrix.m22(), this.m23() + matrix.m23(),
			this.m30() + matrix.m30(), this.m31() + matrix.m31(), this.m32() + matrix.m32(), this.m33() + matrix.m33()
		) : this;
	}

	/**
	 * Computes the difference between this matrix and the given one.
	 * The difference of two matrices M1 and M2 is a matrix N with every value N[i,j] is the difference of the values M1[i,j] and M2[i,j] of the two matrices.
	 * 
	 * @param matrix The second matrix in the subtraction
	 * 
	 * @return The result of the subtraction or a copy of this matrix if the given one is {@code null}
	 */
	public Matrix4 minus(Matrix4 matrix) {
		return matrix != null ? new Matrix4(
			this.m00() - matrix.m00(), this.m01() - matrix.m01(), this.m02() - matrix.m02(), this.m03() - matrix.m03(),
			this.m10() - matrix.m10(), this.m11() - matrix.m11(), this.m12() - matrix.m12(), this.m13() - matrix.m13(),
			this.m20() - matrix.m20(), this.m21() - matrix.m21(), this.m22() - matrix.m22(), this.m23() - matrix.m23(),
			this.m30() - matrix.m30(), this.m31() - matrix.m31(), this.m32() - matrix.m32(), this.m33() - matrix.m33()
		) : this;
	}

	/**
	 * Multiplies this matrix by a scalar.
	 * The product of a matrix M by a scalar k is a matrix N where every element N[i,j] is the product between the element M[i,j] of the first matrix by k.
	 * 
	 * @param k The scalar by which this matrix is multiplied
	 * 
	 * @return The result of the multiplication of this matrix by the given scalar
	 */
	public Matrix4 multiply(float k) {
		return new Matrix4(
			this.m00() * k, this.m01() * k, this.m02() * k, this.m03() * k,
			this.m10() * k, this.m11() * k, this.m12() * k, this.m13() * k,
			this.m20() * k, this.m21() * k, this.m22() * k, this.m23() * k,
			this.m30() * k, this.m31() * k, this.m32() * k, this.m33() * k
		);
	}

	/**
	 * Multiplies this matrix by a vector.
	 * The product of a matrix by a vector is a vector where every element is the dot product between the i-th row of the matrix and the first vector.
	 * 
	 * @param vector The vector by which this matrix is multiplied
	 * 
	 * @return The result of the multiplication of this matrix by the given vector
	 */
	public Float4 multiply(Float4 vector) {
		float x = this.row(0).dotProduct(vector);
		float y = this.row(1).dotProduct(vector);
		float z = this.row(2).dotProduct(vector);
		float w = this.row(3).dotProduct(vector);
		return new Float4(x, y, z, w);
	}

	/**
	 * Gets a row of this matrix as a 4D vector.
	 * 
	 * @param i Index of the row to get as a number between 0 and 3
	 * 
	 * @return The requested row of this matrix or (0, 0, 0, 0) if the given index is not between 0 and 3
	 */
	public Float4 row(int i) {
		return switch (i) {
			case 0 -> new Float4(this.m00(), this.m01(), this.m02(), this.m03());
			case 1 -> new Float4(this.m10(), this.m11(), this.m12(), this.m13());
			case 2 -> new Float4(this.m20(), this.m21(), this.m22(), this.m23());
			case 3 -> new Float4(this.m30(), this.m31(), this.m32(), this.m33());
			default -> Float4.ZERO;
		};
	}

	/**
	 * Gets a column of this matrix as a 4D vector.
	 * 
	 * @param i Index of the column to get as a number between 0 and 3
	 * 
	 * @return The requested column of this matrix or (0, 0, 0, 0) if the given index is not between 0 and 3
	 */
	public Float4 column(int i) {
		return switch (i) {
			case 0 -> new Float4(this.m00(), this.m10(), this.m20(), this.m30());
			case 1 -> new Float4(this.m01(), this.m11(), this.m21(), this.m31());
			case 2 -> new Float4(this.m02(), this.m12(), this.m22(), this.m32());
			case 3 -> new Float4(this.m03(), this.m13(), this.m23(), this.m33());
			default -> Float4.ZERO;
		};
	}

	/**
	 * Multiplies this matrix by the given matrix
	 * The product of two matrices is a matrix M where every element M[i,j] is the dot product between the i-th row of the first matrix and the j-th column of the second matrix.
	 * 
	 * @param matrix The second matrix of the multiplication
	 * 
	 * @return The result of the product or the zero matrix if the given one is {@code null}
	 */
	public Matrix4 multiply(Matrix4 matrix) {
		return matrix != null ? new Matrix4(
			this.row(0).dotProduct(matrix.column(0)),
			this.row(0).dotProduct(matrix.column(1)),
			this.row(0).dotProduct(matrix.column(2)),
			this.row(0).dotProduct(matrix.column(3)),
			this.row(1).dotProduct(matrix.column(0)),
			this.row(1).dotProduct(matrix.column(1)),
			this.row(1).dotProduct(matrix.column(2)),
			this.row(1).dotProduct(matrix.column(3)),
			this.row(2).dotProduct(matrix.column(0)),
			this.row(2).dotProduct(matrix.column(1)),
			this.row(2).dotProduct(matrix.column(2)),
			this.row(2).dotProduct(matrix.column(3)),
			this.row(3).dotProduct(matrix.column(0)),
			this.row(3).dotProduct(matrix.column(1)),
			this.row(3).dotProduct(matrix.column(2)),
			this.row(3).dotProduct(matrix.column(3))
		) : ZERO;
	}

	/**
	 * Gets the transposed of this matrix.
	 * The transposed of a matrix is a matrix where each row i is the i-th column of the first matrix.
	 * 
	 * @return The transposed of this matrix
	 */
	public Matrix4 transposed() {
		return new Matrix4(
			this.m00(), this.m10(), this.m20(), this.m30(),
			this.m01(), this.m11(), this.m21(), this.m31(),
			this.m02(), this.m12(), this.m22(), this.m32(),
			this.m03(), this.m13(), this.m23(), this.m33()
		);
	}
}
