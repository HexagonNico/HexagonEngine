package hexagon.engine.math.vector;

/**
 * Interface containing operations for float vectors
 * 
 * @author Nico
 */
public interface FloatVector<F extends FloatVector<F, I>, I extends IntVector<I,F>> {
	
	/**
	 * Computes the sum of two float vectors and returns the result.
	 * 
	 * @param vector Second operand of the sum
	 * 
	 * @return The sum of this vector and the given one
	 */
	F plus(F vector);

	/**
	 * Computes the sum of a float vector and an int vector and returns the result.
	 * 
	 * @param vector Second operand of the sum
	 * 
	 * @return The sum of this vector and the given one
	 */
	default F plus(I vector) {
		return this.plus(vector.asFloat());
	}

	/**
	 * Get the additive inverse of this vector.
	 * 
	 * @return The additive inverse of this vector.
	 */
	F negative();

	/**
	 * Computes the difference between two float vectors and returns the result,
	 * which is the same as the sum of this vector and the inverse of the given one.
	 * 
	 * @param vector The second operand of the difference
	 * 
	 * @return The difference between this vector and the given one.
	 */
	default F minus(F vector) {
		return this.plus(vector.negative());
	}

	/**
	 * Computes the difference between a float vector and an int vector and returns the result,
	 * which is the same as the sum of this vector and the inverse of the given one.
	 * 
	 * @param vector The second operand of the difference
	 * 
	 * @return The difference between this vector and the given one.
	 */
	default F minus(I vector) {
		return this.minus(vector.asFloat());
	}

	/**
	 * Multiplies this vector by a scalar and returns the result.
	 * 
	 * @param k The scalar
	 * 
	 * @return The product of this vector by a scalar
	 */
	F times(float k);

	/**
	 * Divides this vector by a scalar and returns the result,
	 * which is the same as the product between this vector and the inverse of the scalar.
	 * 
	 * @param k The scalar
	 * 
	 * @return The product of this vector by a scalar
	 */
	default F dividedBy(float k) {
		return this.times(1.0f / k);
	}

	/**
	 * Computes the dot product between two float vectors and returns the result.
	 * 
	 * @param vector The second operand
	 * 
	 * @return The result of the dot product between this vector and the given one
	 */
	float dotProduct(F vector);

	/**
	 * Computes the dot product between a float vector and an int vector and returns the result.
	 * 
	 * @param vector The second operand
	 * 
	 * @return The result of the dot product between this vector and the given one
	 */
	default float dotProduct(I vector) {
		return this.dotProduct(vector.asFloat());
	}

	/**
	 * Computes the squared length (or squared magnitude) of this vector.
	 * 
	 * @return The squared length (or squared magnitude) of this vector
	 */
	float lengthSquared();

	/**
	 * Computes the length (or magnitude) of this vector.
	 * 
	 * @return The length (or magnitude) of this vector
	 */
	default float length() {
		return (float) Math.sqrt(this.lengthSquared());
	}

	/**
	 * Returns a vector that has the same direction as this one, but a length of 1.
	 * 
	 * @return The normalized vector
	 */
	default F normalized() {
		return this.dividedBy(this.length());
	}

	/**
	 * Computes the angle between two float vectors and returns the result.
	 * 
	 * @param vector The second vector
	 * 
	 * @return The angle between this vector and the given one
	 */
	default float angle(F vector) {
		return (float) Math.acos(this.dotProduct(vector) / (this.length() * vector.length()));
	}

	/**
	 * Computes the angle between a float vector and an int vector and returns the result.
	 * 
	 * @param vector The second vector
	 * 
	 * @return The angle between this vector and the given one
	 */
	default float angle(I vector) {
		return (float) Math.acos(this.dotProduct(vector) / (this.length() * vector.length()));
	}

	/**
	 * Casts this float vector to an int vector.
	 * 
	 * @return The int vector.
	 */
	I castToInt();
}
