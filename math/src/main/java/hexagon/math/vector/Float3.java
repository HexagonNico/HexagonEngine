package hexagon.math.vector;

/**
 * Record that represents a 3D float vector.
 * 
 * <p>
 * 	Vectors are designed to be immutable.
 * 	Operations on vectors are supposed to work as operation on primitive types,
 * 	i.e., they do not affect the vector they are called on, but return a new vector instead.
 * </p>
 * 
 * @author Nico
 */
public record Float3(float x, float y, float z) {
	
	/**Shorthand for {@code new Float3(0.0f, 0.0f, 0.0f)} */
	public static final Float3 ZERO = new Float3(0.0f, 0.0f, 0.0f);
	/**Shorthand for {@code new Float3(1.0f, 1.0f, 1.0f)} */
	public static final Float3 ONE = new Float3(1.0f, 1.0f, 1.0f);
	/**Shorthand for {@code new Float3(0.0f, 1.0f, 0.0f)} */
	public static final Float3 UP = new Float3(0.0f, 1.0f, 0.0f);
	/**Shorthand for {@code new Float3(0.0f, -1.0f, 0.0f)} */
	public static final Float3 DOWN = new Float3(0.0f, -1.0f, 0.0f);
	/**Shorthand for {@code new Float3(-1.0f, 0.0f, 0.0f)} */
	public static final Float3 LEFT = new Float3(-1.0f, 0.0f, 0.0f);
	/**Shorthand for {@code new Float3(1.0f, 0.0f, 0.0f)} */
	public static final Float3 RIGHT = new Float3(1.0f, 0.0f, 0.0f);
	/**Shorthand for {@code new Float3(0.0f, 0.0f, -1.0f)} */
	public static final Float3 BACKWARDS = new Float3(0.0f, 0.0f, -1.0f);
	/**Shorthand for {@code new Float3(0.0f, 0.0f, 1.0f)} */
	public static final Float3 FORWARD = new Float3(0.0f, 0.0f, 1.0f);

	/**
	 * Sums this vector and a vector with the passed values.
	 * 
	 * @param x X component of the second vector
	 * @param y Y component of the second vector
	 * @param z Z component of the second vector
	 * 
	 * @return The sum of this vector and the given one
	 */
	public Float3 plus(float x, float y, float z) {
		return new Float3(this.x() + x, this.y() + y, this.z() + z);
	}

	/**
	 * Sums this vector and the given one.
	 * If the given vector is {@code null}, it is treated as (0, 0, 0)
	 * 
	 * @param v The second vector to sum
	 * 
	 * @return The sum of this vector and the given one or a copy of this vector if the given one is {@code null}
	 */
	public Float3 plus(Float3 v) {
		return v != null ? this.plus(v.x(), v.y(), v.z()) : this;
	}

	/**
	 * Sums this vector and the given integer vector.
	 * The sum of a float vector and an integer vector is a float vector.
	 * If the given vector is {@code null}, it is treated as (0, 0, 0).
	 * 
	 * @param v The second vector to sum
	 * 
	 * @return The sum of this vector and the given one or a copy of this vector if the given one is {@code null}
	 */
	public Float3 plus(Int3 v) {
		return v != null ? this.plus(v.asFloat()) : this;
	}

	/**
	 * Subtracts the given values from this vector.
	 * 
	 * @param x X component of the second vector
	 * @param y Y component of the second vector
	 * @param z Z component of the second vector
	 * 
	 * @return The result of the subtraction of the given vector from this one
	 */
	public Float3 minus(float x, float y, float z) {
		return this.plus(-x, -y, -z);
	}

	/**
	 * Subtracts the given vector from this one.
	 * If the given vector is {@code null}, it is treated as (0, 0, 0)
	 * 
	 * @param v The vector to subtract
	 * 
	 * @return The result of the subtraction of the given vector from this one or a copy of this vector if the given one is {@code null}
	 */
	public Float3 minus(Float3 v) {
		return v != null ? this.minus(v.x(), v.y(), v.z()) : this;
	}

	/**
	 * Subtracts the given integer vector from this float vector.
	 * The difference between a float vector and an integer vector is a float vector.
	 * If the given vector is {@code null}, it is treated as (0, 0, 0).
	 * 
	 * @param v The vector to subtract
	 * 
	 * @return The result of the subtraction of the given vector from this one or a copy of this vector if the given one is {@code null}
	 */
	public Float3 minus(Int3 v) {
		return v != null ? this.minus(v.asFloat()) : this;
	}

	/**
	 * Gets the additive inverse of this vector,
	 * i.e., for a vector (x, y, z), the additive inverse is (-x, -y, -z).
	 * 
	 * @return The additive inverse of this vector.
	 */
	public Float3 negative() {
		return new Float3(-this.x(), -this.y(), -this.z());
	}

	/**
	 * Multiplies this vector by a scalar,
	 * i.e., for a vector (x, y, z), the vector (x, y, z) * k is (x * k, y * k, z * k).
	 * 
	 * @param k The scalar to which this vector is multiplied
	 * 
	 * @return The result of the multiplication of this vector by the given scalar
	 */
	public Float3 multiply(float k) {
		return new Float3(this.x() * k, this.y() * k, this.z() * k);
	}

	/**
	 * Divides this vector by a scalar,
	 * i.e., for a vector (x, y, z), the vector (x, y, z) / k is (x / k, y / k, z / k).
	 * 
	 * @param k The scalar by which this vector is divided
	 * 
	 * @return The result of the division of this vector by the given scalar
	 */
	public Float3 divide(float k) {
		return this.multiply(1.0f / k);
	}

	/**
	 * Computes the dot product between this vector and the given values.
	 * The dot product between two vectors (x1, y1, z1) and (x2, y2, z2) is the scalar x1 * x2 + y1 * y2 + z1 * z2.
	 * 
	 * @param x X component of the second vector
	 * @param y Y component of the second vector
	 * @param z Z component of the second vector
	 * 
	 * @return The result of the dot product between these two vectors
	 */
	public float dotProduct(float x, float y, float z) {
		return this.x() * x + this.y() * y + this.z() * z;
	}

	/**
	 * Computes the dot product (or scalar product) between this vector and the given one.
	 * The dot product between two vectors (x1, y1, z1) and (x2, y2, z2) is the scalar x1 * x2 + y1 * y2 + z1 * z2.
	 * If the given vector is {@code null}, it is treated as (0, 0, 0).
	 * 
	 * @param v The second vector of the multiplication
	 * 
	 * @return The dot product between these two vectors or 0 if the given one is {@code null}
	 */
	public float dotProduct(Float3 v) {
		return v != null ? this.dotProduct(v.x(), v.y(), v.z()) : 0.0f;
	}

	/**
	 * Computes the dot product (or scalar product) between this float vector and the given integer vector.
	 * The dot product between two vectors (x1, y1, z1) and (x2, y2, z2) is the scalar x1 * x2 + y1 * y2 + z1 * z2.
	 * If the given vector is {@code null}, it is treated as (0, 0, 0).
	 * 
	 * @param v The second vector of the multiplication
	 * 
	 * @return The dot product between these two vectors or 0 if the given one is {@code null}
	 */
	public float dotProduct(Int3 v) {
		return v != null ? this.dotProduct(v.asFloat()) : 0.0f;
	}

	// TODO - Normalized
	// TODO - Cross product

	/**
	 * Casts this float vector to an integer vector by casting all of its components.
	 * 
	 * @return An integer vector whose components are this vector's components cast to int.
	 */
	public Int3 castToInt() {
		return new Int3((int) this.x(), (int) this.y(), (int) this.z());
	}
}
