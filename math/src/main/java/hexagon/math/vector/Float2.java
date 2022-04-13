package hexagon.math.vector;

/**
 * Record that represents a 2D float vector.
 * 
 * <p>
 * 	Vectors are designed to be immutable.
 * 	Operations on vectors are supposed to work as operation on primitive types,
 * 	i.e., they do not affect the vector they are called on, but return a new vector instead.
 * </p>
 * 
 * @author Nico
 */
public record Float2(float x, float y) {
	
	/**Shorthand for {@code new Float2(0.0f, 0.0f)} */
	public static final Float2 ZERO = new Float2(0.0f, 0.0f);
	/**Shorthand for {@code new Float2(1.0f, 1.0f)} */
	public static final Float2 ONE = new Float2(1.0f, 1.0f);
	/**Shorthand for {@code new Float2(0.0f, 1.0f)} */
	public static final Float2 UP = new Float2(0.0f, 1.0f);
	/**Shorthand for {@code new Float2(0.0f, -1.0f)} */
	public static final Float2 DOWN = new Float2(0.0f, -1.0f);
	/**Shorthand for {@code new Float2(-1.0f, 0.0f)} */
	public static final Float2 LEFT = new Float2(-1.0f, 0.0f);
	/**Shorthand for {@code new Float2(1.0f, 1.0f)} */
	public static final Float2 RIGHT = new Float2(1.0f, 0.0f);

	/**
	 * Sums this vector and a vector with the passed values.
	 * 
	 * @param x X component of the second vector
	 * @param y Y component of the second vector
	 * 
	 * @return The sum of this vector and the given one
	 */
	public Float2 plus(float x, float y) {
		return new Float2(this.x() + x, this.y() + y);
	}

	/**
	 * Sums this vector and the given one.
	 * If the given vector is {@code null}, it is treated as (0, 0)
	 * 
	 * @param v The second vector to sum
	 * 
	 * @return The sum of this vector and the given one or a copy of this vector if the given one is {@code null}
	 */
	public Float2 plus(Float2 v) {
		return v != null ? this.plus(v.x(), v.y()) : this;
	}

	/**
	 * Sums this vector and the given integer vector.
	 * The sum of a float vector and an integer vector is a float vector.
	 * If the given vector is {@code null}, it is treated as (0, 0).
	 * 
	 * @param v The second vector to sum
	 * 
	 * @return The sum of this vector and the given one or a copy of this vector if the given one is {@code null}
	 */
	public Float2 plus(Int2 v) {
		return v != null ? this.plus(v.asFloat()) : this;
	}

	/**
	 * Subtracts the given values from this vector.
	 * 
	 * @param x X component of the second vector
	 * @param y Y component of the second vector
	 * 
	 * @return The result of the subtraction of the given vector from this one
	 */
	public Float2 minus(float x, float y) {
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
	public Float2 minus(Float2 v) {
		return v != null ? this.minus(v.x(), v.y()) : this;
	}

	/**
	 * Subtracts the given integer vector from this float vector.
	 * The difference between a float vector and an integer vector is a float vector.
	 * If the given vector is {@code null}, it is treated as (0, 0).
	 * 
	 * @param v The vector to subtract
	 * 
	 * @return The result of the subtraction of the given vector from this one or a copy of this vector if the given one is {@code null}
	 */
	public Float2 minus(Int2 v) {
		return v != null ? this.minus(v.asFloat()) : this;
	}

	/**
	 * Gets the additive inverse of this vector,
	 * i.e., for a vector (x, y), the additive inverse is (-x, -y).
	 * 
	 * @return The additive inverse of this vector.
	 */
	public Float2 negative() {
		return new Float2(-this.x(), -this.y());
	}

	/**
	 * Multiplies this vector by a scalar,
	 * i.e., for a vector (x, y), the vector (x, y) * k is (x * k, y * k).
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
	public float dotProduct(float x, float y) {
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
	public float dotProduct(Float2 v) {
		return v != null ? this.dotProduct(v.x(), v.y()) : 0.0f;
	}

	/**
	 * Computes the dot product (or scalar product) between this float vector and the given integer vector.
	 * The dot product between two vectors (x1, y1) and (x2, y2) is the scalar x1 * x2 + y1 * y2.
	 * If the given vector is {@code null}, it is treated as (0, 0).
	 * 
	 * @param v The second vector of the multiplication
	 * 
	 * @return The dot product between these two vectors or 0 if the given one is {@code null}
	 */
	public float dotProduct(Int2 v) {
		return v != null ? this.dotProduct(v.asFloat()) : 0.0f;
	}

	// TODO - Normalized

	/**
	 * Casts this float vector to an integer vector by casting all of its components.
	 * 
	 * @return An integer vector whose components are this vector's components cast to int.
	 */
	public Int2 castToInt() {
		return new Int2((int) this.x(), (int) this.y());
	}
}
