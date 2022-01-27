package hexagon.engine.math.vector;

public interface FloatVector<F extends FloatVector<F, I>, I extends IntVector<I,F>> {
	
	F plus(F vector);

	default F plus(I vector) {
		return this.plus(vector.asFloat());
	}

	F negative();

	default F minus(F vector) {
		return this.plus(vector.negative());
	}

	default F minus(I vector) {
		return this.minus(vector.asFloat());
	}

	F times(float k);

	default F dividedBy(float k) {
		return this.times(1.0f / k);
	}

	float dotProduct(F vector);

	default float dotProduct(I vector) {
		return this.dotProduct(vector.asFloat());
	}

	float lengthSquared();

	default float length() {
		return (float) Math.sqrt(this.lengthSquared());
	}

	default F normalized() {
		return this.dividedBy(this.length());
	}

	default float angle(F vector) {
		return (float) Math.acos(this.dotProduct(vector) / (this.length() * vector.length()));
	}

	default float angle(I vector) {
		return (float) Math.acos(this.dotProduct(vector) / (this.length() * vector.length()));
	}

	I castToInt();
}
