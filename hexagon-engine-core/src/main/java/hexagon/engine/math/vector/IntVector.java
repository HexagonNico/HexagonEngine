package hexagon.engine.math.vector;

public interface IntVector<I extends IntVector<I, F>, F extends FloatVector<F, I>> {
	
	I plus(I vector);

	default F plus(F vector) {
		return this.asFloat().plus(vector);
	}

	I negative();

	default I minus(I vector) {
		return this.plus(vector.negative());
	}

	default F minus(F vector) {
		return this.asFloat().minus(vector);
	}

	F asFloat();

	I times(int k);

	default F times(float k) {
		return this.asFloat().times(k);
	}

	I dividedBy(int k);

	default F dividedBy(float k) {
		return this.asFloat().dividedBy(k);
	}

	int dotProduct(I vector);

	default float dotProduct(F vector) {
		return this.asFloat().dotProduct(vector);
	}

	int lengthSquared();

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
}
