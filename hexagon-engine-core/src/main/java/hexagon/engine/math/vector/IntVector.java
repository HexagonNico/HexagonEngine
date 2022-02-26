package hexagon.engine.math.vector;

public interface IntVector<I extends IntVector<I, F>, F extends FloatVector<F, I>> {
	
	/**
	 * Computes the sum of two int vectors and returns the result.
	 * 
	 * @param vector Second operand of the sum
	 * 
	 * @return The sum of this vector and the given one
	 */
	I plus(I vector);

	/**
	 * Computes the sum of an int vector and a float vector and returns the result.
	 * 
	 * @param vector Second operand of the sum
	 * 
	 * @return The sum of this vector and the given one
	 */
	default F plus(F vector) {
		return this.asFloat().plus(vector);
	}

	/**
	 * Get the additive inverse of this vector.
	 * 
	 * @return The additive inverse of this vector.
	 */
	I negative();

	/**
	 * Computes the difference between two int vectors and returns the result,
	 * which is the same as the sum of this vector and the inverse of the given one.
	 * 
	 * @param vector The second operand of the difference
	 * 
	 * @return The difference between this vector and the given one.
	 */
	default I minus(I vector) {
		return this.plus(vector.negative());
	}

	/**
	 * Computes the difference between an int vector and a float vector and returns the result,
	 * which is the same as the sum of this vector and the inverse of the given one.
	 * 
	 * @param vector The second operand of the difference
	 * 
	 * @return The difference between this vector and the given one.
	 */
	default F minus(F vector) {
		return this.asFloat().minus(vector);
	}

	/**
	 * Casts this vector to a float vector.
	 * 
	 * @return A float vector with the same value as this one
	 */
	F asFloat();

	/**
	 * Multiplies this vector by a scalar and returns the result.
	 * 
	 * @param k The scalar
	 * 
	 * @return The product of this vector by a scalar
	 */
	I times(int k);

	/**
	 * Multiplies this vector by a scalar and returns the result.
	 * 
	 * @param k The scalar
	 * 
	 * @return The product of this vector by a scalar
	 */
	default F times(float k) {
		return this.asFloat().times(k);
	}

	/**
	 * Divides this vector by a scalar performing an integer division and returns the result,
	 * which is the same as the product between this vector and the inverse of the scalar.
	 * 
	 * @param k The scalar
	 * 
	 * @return The product of this vector by a scalar
	 */
	I dividedBy(int k);

	/**
	 * Divides this vector by a scalar and returns the result,
	 * which is the same as the product between this vector and the inverse of the scalar.
	 * 
	 * @param k The scalar
	 * 
	 * @return The product of this vector by a scalar
	 */
	default F dividedBy(float k) {
		return this.asFloat().dividedBy(k);
	}

	/**
	 * Computes the dot product between two int vectors and returns the result.
	 * 
	 * @param vector The second operand
	 * 
	 * @return The result of the dot product between this vector and the given one
	 */
	int dotProduct(I vector);

	/**
	 * Computes the dot product between an int vector and a float vector and returns the result.
	 * 
	 * @param vector The second operand
	 * 
	 * @return The result of the dot product between this vector and the given one
	 */
	default float dotProduct(F vector) {
		return this.asFloat().dotProduct(vector);
	}

	/**
	 * Computes the squared length (or squared magnitude) of this vector.
	 * 
	 * @return The squared length (or squared magnitude) of this vector
	 */
	int lengthSquared();

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
	 * Computes the angle between an int vector and a float vector and returns the result.
	 * 
	 * @param vector The second vector
	 * 
	 * @return The angle between this vector and the given one
	 */
	default float angle(F vector) {
		return (float) Math.acos(this.dotProduct(vector) / (this.length() * vector.length()));
	}

	/**
	 * Computes the angle between two int vectors and returns the result.
	 * 
	 * @param vector The second vector
	 * 
	 * @return The angle between this vector and the given one
	 */
	default float angle(I vector) {
		return (float) Math.acos(this.dotProduct(vector) / (this.length() * vector.length()));
	}
}
