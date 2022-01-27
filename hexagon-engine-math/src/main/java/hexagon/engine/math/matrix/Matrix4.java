package hexagon.engine.math.matrix;

import hexagon.engine.math.vector.Float4;

public record Matrix4(float m00, float m01, float m02, float m03, float m10, float m11, float m12, float m13, float m20, float m21, float m22, float m23, float m30, float m31, float m32, float m33) implements Matrix<Matrix4, Float4> {

	public static final Matrix4 IDENTITY = new Matrix4(
		1.0f, 0.0f, 0.0f, 0.0f,
		0.0f, 1.0f, 0.0f, 0.0f,
		0.0f, 0.0f, 1.0f, 0.0f,
		0.0f, 0.0f, 0.0f, 1.0f
	);

	@Override
	public Matrix4 plus(Matrix4 matrix) {
		return new Matrix4(
			this.m00() + matrix.m00(), this.m01() + matrix.m01(), this.m02() + matrix.m02(), this.m03() + matrix.m03(),
			this.m10() + matrix.m10(), this.m11() + matrix.m11(), this.m12() + matrix.m12(), this.m13() + matrix.m13(),
			this.m20() + matrix.m20(), this.m21() + matrix.m21(), this.m22() + matrix.m22(), this.m23() + matrix.m23(),
			this.m30() + matrix.m30(), this.m31() + matrix.m31(), this.m32() + matrix.m32(), this.m33() + matrix.m33()
		);
	}

	@Override
	public Matrix4 negative() {
		return new Matrix4(
			-this.m00(), -this.m01(), -this.m02(), -this.m03(),
			-this.m10(), -this.m11(), -this.m12(), -this.m13(),
			-this.m20(), -this.m21(), -this.m22(), -this.m23(),
			-this.m30(), -this.m31(), -this.m32(), -this.m33()
		);
	}

	@Override
	public Matrix4 multiply(float k) {
		return new Matrix4(
			this.m00() * k, this.m01() * k, this.m02() * k, this.m03() * k,
			this.m10() * k, this.m11() * k, this.m12() * k, this.m13() * k,
			this.m20() * k, this.m21() * k, this.m22() * k, this.m23() * k,
			this.m30() * k, this.m31() * k, this.m32() * k, this.m33() * k
		);
	}

	@Override
	public Float4 multiply(Float4 vector) {
		float x = this.row(0).dotProduct(vector);
		float y = this.row(1).dotProduct(vector);
		float z = this.row(2).dotProduct(vector);
		float w = this.row(3).dotProduct(vector);
		return new Float4(x, y, z, w);
	}

	@Override
	public Float4 row(int i) {
		return switch (i) {
			case 0 -> new Float4(this.m00(), this.m01(), this.m02(), this.m03());
			case 1 -> new Float4(this.m10(), this.m11(), this.m12(), this.m13());
			case 2 -> new Float4(this.m20(), this.m21(), this.m22(), this.m23());
			case 3 -> new Float4(this.m30(), this.m31(), this.m32(), this.m33());
			default -> throw new IndexOutOfBoundsException("Matrix only has 4 rows");
		};
	}

	@Override
	public Float4 column(int i) {
		return switch (i) {
			case 0 -> new Float4(this.m00(), this.m10(), this.m20(), this.m30());
			case 1 -> new Float4(this.m01(), this.m11(), this.m21(), this.m31());
			case 2 -> new Float4(this.m02(), this.m12(), this.m22(), this.m32());
			case 3 -> new Float4(this.m03(), this.m13(), this.m23(), this.m33());
			default -> throw new IndexOutOfBoundsException("Matrix only has 4 columns");
		};
	}

	@Override
	public Matrix4 multiply(Matrix4 matrix) {
		return new Matrix4(
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
		);
	}

	@Override
	public Matrix4 transposed() {
		return new Matrix4(
			this.m00(), this.m10(), this.m20(), this.m30(),
			this.m01(), this.m11(), this.m21(), this.m31(),
			this.m02(), this.m12(), this.m22(), this.m32(),
			this.m03(), this.m13(), this.m23(), this.m33()
		);
	}

	@Override
	public boolean isSymmetric() {
		return this.m01 == this.m10 && this.m02 == this.m20 && this.m03 == this.m30 && this.m21 == this.m12 && this.m31 == this.m13 && this.m32 == this.m23;
	}

	@Override
	public boolean isSkewSymmetric() {
		return this.m01 == -this.m10 && this.m02 == -this.m20 && this.m03 == -this.m30 && this.m21 == -this.m12 && this.m31 == -this.m13 && this.m32 == -this.m23;
	}

	@Override
	public Matrix4 power(int exponent) {
		if(exponent < 0) {
			return this.transposed().power(-exponent);
		} else if(exponent == 0) {
			return IDENTITY;
		} else {
			Matrix4 result = this;
			for(int i = 0; i < exponent - 1; i++) {
				result = result.multiply(this);
			}
			return result;
		}
	}
}
