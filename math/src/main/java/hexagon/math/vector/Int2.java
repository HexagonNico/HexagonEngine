package hexagon.math.vector;

/**
 * Record that represents a 2D integer vector.
 * 
 * <p>
 * 	Vectors are designed to be immutable.
 * 	Operations on vectors are supposed to work as operation on primitive types,
 * 	i.e., they do not affect the vector they are called on, but return a new vector instead.
 * </p>
 * 
 * @author Nico
 */
public record Int2(int x, int y) {
	
	/**Shorthand for {@code new Int2(0, 0)} */
	public static final Int2 ZERO = new Int2(0, 0);
	/**Shorthand for {@code new Int2(1, 1)} */
	public static final Int2 ONE = new Int2(1, 1);
	/**Shorthand for {@code new Int2(0, 1)} */
	public static final Int2 UP = new Int2(0, 1);
	/**Shorthand for {@code new Int2(0, -1)} */
	public static final Int2 DOWN = new Int2(0, -1);
	/**Shorthand for {@code new Int2(-1, 0)} */
	public static final Int2 LEFT = new Int2(-1, 0);
	/**Shorthand for {@code new Int2(1, 0)} */
	public static final Int2 RIGHT = new Int2(1, 0);

	/**
	 * Sums this vector and a vector with the passed values.
	 * 
	 * @param x X component of the second vector
	 * @param y Y component of the second vector
	 * 
	 * @return The sum of this vector and the given one
	 */
	public Int2 plus(int x, int y) {
		return new Int2(this.x() + x, this.y() + y);
	}

	/**
	 * Sums this vector and the given one.
	 * If the given vector is {@code null}, it is treated as (0, 0)
	 * 
	 * @param v The second vector to sum
	 * 
	 * @return The sum of this vector and the given one or a copy of this vector if the given one is {@code null}
	 */
	public Int2 plus(Int2 v) {
		return v != null ? this.plus(v.x(), v.y()) : this;
	}

	/**
	 * Sums this integer vector and a float vector with the passed values.
	 * The sum of an integer vector and a float vector is a float vector.
	 * 
	 * @param x X component of the second vector
	 * @param y Y component of the second vector
	 * 
	 * @return The sum of this vector and the given one
	 */
	public Float2 plus(float x, float y) {
		return this.asFloat().plus(x, y);
	}

	/**
	 * Sums this vector and the given one.
	 * The sum of an integer vector and a float vector is a float vector.
	 * If the given vector is {@code null}, it is treated as (0, 0)
	 * 
	 * @param v The second vector to sum
	 * 
	 * @return The sum of this vector and the given one or this vector as a float vector if the given one is {@code null}
	 */
	public Float2 plus(Float2 v) {
		return v != null ? this.asFloat().plus(v) : this.asFloat();
	}

	/**
	 * Subtracts the given values from this vector.
	 * 
	 * @param x X component of the second vector
	 * @param y Y component of the second vector
	 * 
	 * @return The result of the subtraction of the given vector from this one
	 */
	public Int2 minus(int x, int y) {
		return this.plus(-x, -y);
	}

	/**
	 * Subtracts the given vector from this one.
	 * If the given vector is {@code null}, it is treated as (0, 0)
	 * 
	 * @param v The vector to subtract
	 * 
	 * @return The result of the subtraction of the given vector from this one or a copy of this vector if the given one is {@code null}
	 */
	public Int2 minus(Int2 v) {
		return v != null ? this.minus(v.x(), v.y()) : this;
	}

	/**
	 * Subtracts the float vector with the given values from this one.
	 * The difference between an integer vector and a float vector is a float vector.
	 * 
	 * @param x X component of the second vector
	 * @param y Y component of the second vector
	 * 
	 * @return The result of the subtraction of the given vector from this one
	 */
	public Float2 minus(float x, float y) {
		return this.asFloat().minus(x, y);
	}

	/**
	 * Subtracts the given float vector from this one.
	 * The difference between an integer vector and a float vector is a float vector.
	 * If the given vector is {@code null}, it is treated as (0, 0).
	 * 
	 * @param v The vector to subtract
	 * 
	 * @return The result of the subtraction of the given vector from this one or a copy of this vector if the given one is {@code null}
	 */
	public Float2 minus(Float2 v) {
		return v != null ? this.asFloat().minus(v) : this.asFloat();
	}

	/**
	 * Gets the additive inverse of this vector,
	 * i.e., for a vector (x, y), the additive inverse is (-x, -y).
	 * 
	 * @return The additive inverse of this vector.
	 */
	public Int2 negative() {
		return new Int2(-this.x(), -this.y());
	}

	/**
	 * Multiplies this vector by a scalar,
	 * i.e., for a vector (x, y), the vector (x, y) * k is (x * k, y * k).
	 * 
	 * @param k The scalar to which this vector is multiplied
	 * 
	 * @return The result of the multiplication of this vector by the given scalar
	 */
	public Int2 multiply(int k) {
		return new Int2(this.x() * k, this.y() * k);
	}

	/**
	 * Multiplies this vector by a scalar,
	 * i.e., for a vector (x, y), the vector (x, y) * k is (x * k, y * k).
	 * The product between an integer vector and a float scalar is a float vector.
	 * 
	 * @param k The scalar to which this vector is multiplied
	 * 
	 * @return The result of the multiplication of this vector by the given scalar
	 */
	public Float2 multiply(float k) {
		return new Float2(this.x() * k, this.y() * k);
	}

	/**
	 * Divides this vector by a scalar,
	 * i.e., for a vector (x, y), the vector (x, y) / k is (x / k, y / k).
	 * Dividing an integer vector by an integer will perform an integer division.
	 * 
	 * @param k The scalar by which this vector is divided
	 * 
	 * @return The result of the division of this vector by the given scalar
	 */
	public Int2 divide(int k) {
		return new Int2(this.x() / k, this.y() / k);
	}

	/**
	 * Divides this vector by a scalar,
	 * i.e., for a vector (x, y), the vector (x, y) / k is (x / k, y / k).
	 * The division of an integer vector by a float is a float vector.
	 * 
	 * @param k The scalar by which this vector is divided
	 * 
	 * @return The result of the division of this vector by the given scalar
	 */
	public Float2 divide(float k) {
		return this.multiply(1.0f / k);
	}

	/**
	 * Computes the dot product between this vector and the given values.
	 * The dot product between two vectors (x1, y1) and (x2, y2) is the scalar x1 * x2 + y1 * y2.
	 * 
	 * @param x X component of the second vector
	 * @param y Y component of the second vector
	 * 
	 * @return The result of the dot product between these two vectors
	 */
	public int dotProduct(int x, int y) {
		return this.x() * x + this.y() * y;
	}

	/**
	 * Computes the dot product (or scalar product) between this vector and the given one.
	 * The dot product between two vectors (x1, y1) and (x2, y2) is the scalar x1 * x2 + y1 * y2.
	 * If the given vector is {@code null}, it is treated as (0, 0).
	 * 
	 * @param v The second vector of the multiplication
	 * 
	 * @return The dot product between these two vectors or 0 if the given one is {@code null}
	 */
	public int dotProduct(Int2 v) {
		return v != null ? this.dotProduct(v.x(), v.y()) : 0;
	}

	/**
	 * Computes the dot product between this vector and the given values.
	 * The dot product between two vectors (x1, y1) and (x2, y2) is the scalar x1 * x2 + y1 * y2.
	 * 
	 * @param x X component of the second vector
	 * @param y Y component of the second vector
	 * 
	 * @return The result of the dot product between these two vectors
	 */
	public float dotProduct(float x, float y) {
		return this.asFloat().dotProduct(x, y);
	}

	/**
	 * Computes the dot product (or scalar product) between this vector and the given one.
	 * The dot product between two vectors (x1, y1) and (x2, y2) is the scalar x1 * x2 + y1 * y2.
	 * If the given vector is {@code null}, it is treated as (0, 0).
	 * 
	 * @param v The second vector of the multiplication
	 * 
	 * @return The dot product between these two vectors or 0 if the given one is {@code null}
	 */
	public float dotProduct(Float2 v) {
		return v != null ? this.asFloat().dotProduct(v) : 0.0f;
	}

	// TODO - Normalized

	/**
	 * Gets a float vector with the same value as this one but as a float.
	 * Useful for conversions.
	 * 
	 * @return A float vector with the same value as this one but as a float
	 */
	public Float2 asFloat() {
		return new Float2(this.x(), this.y());
	}
}
