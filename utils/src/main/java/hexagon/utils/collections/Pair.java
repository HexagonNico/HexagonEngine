package hexagon.utils.collections;

public class Pair<L, R> {

	protected L left;
	protected R right;

	public Pair(L left, R right) {
		this.left = left;
		this.right = right;
	}

	public L left() {
		return this.left;
	}

	public Pair<L, R> left(L left) {
		this.left = left;
		return this;
	}

	public R right() {
		return this.right;
	}

	public Pair<L, R> right(R right) {
		this.right = right;
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		System.out.println(obj);
		return obj instanceof Pair<?, ?> that && this.left.equals(that.left) && this.right.equals(that.right);
	}
}
