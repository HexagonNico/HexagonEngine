package hexagon.engine.math.vector;

/**
 * Record that represents a 4D float vector with components [x, y, z, w]
 * 
 * @author Nico
 */
public record Float4(float x, float y, float z, float w) implements FloatVector<Float4, Int4> {
	
	@Override
	public Float4 plus(Float4 vector) {
		return this.plus(vector.x(), vector.y(), vector.z(), vector.w());
	}

	/**
	 * Sums this vector and the vector [x, y, z, w]
	 * 
	 * @param x First component of the vector
	 * @param y Second component of the vector
	 * @param z Third component of the vector
	 * @param w Fourth component of the vector
	 * 
	 * @return The sum of this vector and the vector [x, y, z, w]
	 */
	public Float4 plus(float x, float y, float z, float w) {
		return new Float4(this.x() + x, this.y() + y, this.z() + z, this.w() + w);
	}

	@Override
	public Float4 negative() {
		return new Float4(-this.x(), -this.y(), -this.z(), -this.w());
	}

	/**
	 * Subtracts the vector [x, y, z, w] from this vector 
	 * 
	 * @param x First component of the vector
	 * @param y Second component of the vector
	 * @param z Third component of the vector
	 * @param w Fourth component of the vector
	 * 
	 * @return The difference between this vector and the vector [x, y, z, w]
	 */
	public Float4 minus(float x, float y, float z, float w) {
		return this.plus(-x, -y, -z, -w);
	}

	@Override
	public Float4 times(float k) {
		return new Float4(this.x() * k, this.y() * k, this.z() * k, this.w() * k);
	}

	@Override
	public float dotProduct(Float4 vector) {
		return this.dotProduct(vector.x(), vector.y(), vector.z(), vector.w());
	}

	/**
	 * Computes the dot product between this vector and the vector [x, y, z, w]
	 * 
	 * @param x First component of the vector
	 * @param y Second component of the vector
	 * @param z Third component of the vector
	 * @param w Fourth component of the vector
	 * 
	 * @return The dot product between this vector and the vector [x, y, z, w]
	 */
	public float dotProduct(float x, float y, float z, float w) {
		return this.x() * x + this.y() * y + this.z() * z + this.w() * w;
	}

	@Override
	public float lengthSquared() {
		return this.dotProduct(this);
	}

	@Override
	public Int4 castToInt() {
		return new Int4((int) this.x(), (int) this.y(), (int) this.z(), (int) this.w());
	}
}
