package hexagon.math.vector;

/**
 * Record that represents a 3D integer vector.
 * 
 * <p>
 * 	Vectors are designed to be immutable.
 * 	Operations on vectors are supposed to work as operation on primitive types,
 * 	i.e., they do not affect the vector they are called on, but return a new vector instead.
 * </p>
 * 
 * @author Nico
 */
public record Int3(int x, int y, int z) {
	
	/**Shorthand for {@code new Int3(0, 0, 0)} */
	public static final Int3 ZERO = new Int3(0, 0, 0);
	/**Shorthand for {@code new Int3(1, 1, 1)} */
	public static final Int3 ONE = new Int3(1, 1, 1);
	/**Shorthand for {@code new Int3(0, 1, 0)} */
	public static final Int3 UP = new Int3(0, 1, 0);
	/**Shorthand for {@code new Int3(0, -1, 0)} */
	public static final Int3 DOWN = new Int3(0, -1, 0);
	/**Shorthand for {@code new Int3(-1, 0, 0)} */
	public static final Int3 LEFT = new Int3(-1, 0, 0);
	/**Shorthand for {@code new Int3(1, 0, 0)} */
	public static final Int3 RIGHT = new Int3(1, 0, 0);
	/**Shorthand for {@code new Int3(0, 0, -1)} */
	public static final Int3 BACKWARDS = new Int3(0, 0, -1);
	/**Shorthand for {@code new Int3(0, 0, 1)} */
	public static final Int3 FORWARD = new Int3(0, 0, 1);

	/**
	 * Sums this vector and a vector with the passed values.
	 * 
	 * @param x X component of the second vector
	 * @param y Y component of the second vector
	 * @param z Z component of the second vector
	 * 
	 * @return The sum of this vector and the given one
	 */
	public Int3 plus(int x, int y, int z) {
		return new Int3(this.x() + x, this.y() + y, this.z() + z);
	}

	/**
	 * Sums this vector and the given one.
	 * If the given vector is {@code null}, it is treated as (0, 0, 0)
	 * 
	 * @param v The second vector to sum
	 * 
	 * @return The sum of this vector and the given one or a copy of this vector if the given one is {@code null}
	 */
	public Int3 plus(Int3 v) {
		return v != null ? this.plus(v.x(), v.y(), v.z()) : this;
	}

	/**
	 * Sums this integer vector and a float vector with the passed values.
	 * The sum of an integer vector and a float vector is a float vector.
	 * 
	 * @param x X component of the second vector
	 * @param y Y component of the second vector
	 * @param z Z component of the second vector
	 * 
	 * @return The sum of this vector and the given one
	 */
	public Float3 plus(float x, float y, float z) {
		return this.asFloat().plus(x, y, z);
	}

	/**
	 * Sums this vector and the given one.
	 * The sum of an integer vector and a float vector is a float vector.
	 * If the given vector is {@code null}, it is treated as (0, 0, 0)
	 * 
	 * @param v The second vector to sum
	 * 
	 * @return The sum of this vector and the given one or this vector as a float vector if the given one is {@code null}
	 */
	public Float3 plus(Float3 v) {
		return v != null ? this.asFloat().plus(v) : this.asFloat();
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
	public Int3 minus(int x, int y, int z) {
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
	public Int3 minus(Int3 v) {
		return v != null ? this.minus(v.x(), v.y(), v.z()) : this;
	}

	/**
	 * Subtracts the float vector with the given values from this one.
	 * The difference between an integer vector and a float vector is a float vector.
	 * 
	 * @param x X component of the second vector
	 * @param y Y component of the second vector
	 * @param z Z component of the second vector
	 * 
	 * @return The result of the subtraction of the given vector from this one
	 */
	public Float3 minus(float x, float y, float z) {
		return this.asFloat().minus(x, y, z);
	}

	/**
	 * Subtracts the given float vector from this one.
	 * The difference between an integer vector and a float vector is a float vector.
	 * If the given vector is {@code null}, it is treated as (0, 0, 0).
	 * 
	 * @param v The vector to subtract
	 * 
	 * @return The result of the subtraction of the given vector from this one or a copy of this vector if the given one is {@code null}
	 */
	public Float3 minus(Float3 v) {
		return v != null ? this.asFloat().minus(v) : this.asFloat();
	}

	/**
	 * Gets the additive inverse of this vector,
	 * i.e., for a vector (x, y, z), the additive inverse is (-x, -y, -z).
	 * 
	 * @return The additive inverse of this vector.
	 */
	public Int3 negative() {
		return new Int3(-this.x(), -this.y(), -this.z());
	}

	/**
	 * Multiplies this vector by a scalar,
	 * i.e., for a vector (x, y, z), the vector (x, y, z) * k is (x * k, y * k, z * k).
	 * 
	 * @param k The scalar to which this vector is multiplied
	 * 
	 * @return The result of the multiplication of this vector by the given scalar
	 */
	public Int3 multiply(int k) {
		return new Int3(this.x() * k, this.y() * k, this.z() * k);
	}

	/**
	 * Multiplies this vector by a scalar,
	 * i.e., for a vector (x, y, z), the vector (x, y, z) * k is (x * k, y * k, z * k).
	 * The product between an integer vector and a float scalar is a float vector.
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
	 * Dividing an integer vector by an integer will perform an integer division.
	 * 
	 * @param k The scalar by which this vector is divided
	 * 
	 * @return The result of the division of this vector by the given scalar
	 */
	public Int3 divide(int k) {
		return new Int3(this.x() / k, this.y() / k, this.z() / k);
	}

	/**
	 * Divides this vector by a scalar,
	 * i.e., for a vector (x, y, z), the vector (x, y, z) / k is (x / k, y / k, z / k).
	 * The division of an integer vector by a float is a float vector.
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
	public int dotProduct(int x, int y, int z) {
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
	public int dotProduct(Int3 v) {
		return v != null ? this.dotProduct(v.x(), v.y(), v.z()) : 0;
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
		return this.asFloat().dotProduct(x, y, z);
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
		return v != null ? this.asFloat().dotProduct(v) : 0.0f;
	}

	// TODO - Normalized
	// TODO - Cross product

	/**
	 * Gets a float vector with the same value as this one but as a float.
	 * Useful for conversions.
	 * 
	 * @return A float vector with the same value as this one but as a float
	 */
	public Float3 asFloat() {
		return new Float3(this.x(), this.y(), this.z());
	}
}
