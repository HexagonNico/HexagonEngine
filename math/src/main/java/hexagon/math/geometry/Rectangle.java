package hexagon.math.geometry;

import hexagon.math.vector.Float2;

public record Rectangle(float left, float right, float top, float bottom) {

	public Rectangle(float left, float right, float top, float bottom) {
		this.left = Math.min(left, right);
		this.right = Math.max(right, left);
		this.top = Math.max(top, bottom);
		this.bottom = Math.min(bottom, top);
	}

	public Rectangle(Float2 bottomLeft, Float2 topRight) {
		this(
				bottomLeft != null ? bottomLeft.x() : 0.0f,
				topRight != null ? topRight.x() : 0.0f,
				topRight != null ? topRight.y() : 0.0f,
				bottomLeft != null ? bottomLeft.y() : 0.0f
		);
	}

	public Rectangle(Float2 center, Size size) {
		this(center, size != null ? size.width() : 0.0f, size != null ? size.height() : 0.0f);
	}

	// TODO - Redo test for this
	public Rectangle(Float2 center, float width, float height) {
		this(
			(center != null ? center : Float2.ZERO).minus(width / 2, height / 2),
			(center != null ? center : Float2.ZERO).plus(width / 2, height / 2)
		);
	}

	public Float2 bottomLeft() {
		return new Float2(this.left(), this.bottom());
	}

	public Float2 topLeft() {
		return new Float2(this.left(), this.top());
	}

	public Float2 bottomRight() {
		return new Float2(this.right(), this.bottom());
	}

	public Float2 topRight() {
		return new Float2(this.right(), this.top());
	}

	public Size size() {
		return new Size(this.right() - this.left(), this.top() - this.bottom());
	}

	public Float2 center() {
		return new Float2((this.left() + this.right()) / 2, (this.top() + this.bottom()) / 2);
	}

	public Rectangle translated(Float2 translation) {
		return new Rectangle(this.bottomLeft().plus(translation), this.topRight().plus(translation));
	}

	public Rectangle translated(float x, float y) {
		return this.translated(new Float2(x, y));
	}

	public boolean intersects(Rectangle that) {
		return !this.equals(that) &&
				this.left() < that.right() &&
				this.right() > that.left() &&
				this.top() > that.bottom() &&
				this.bottom() < that.top();
	}

	// TODO - Write tests
	public Rectangle intersection(Rectangle that) {
		float left = Math.max(this.left(), that.left());
		float right = Math.min(this.right(), that.right());
		float top = Math.min(this.top(), that.top());
		float bottom = Math.max(this.bottom(), that.bottom());
		return new Rectangle(left, right, top, bottom);
	}
}
