package hexagon.lwjgl.opengl;

public abstract class AbstractTexture {

	private static int currentlyBound = -1;

	protected final int id;

	protected AbstractTexture(int id) {
		this.id = id;
	}

	protected abstract void bind(int textureUnit);

	public final void bindIfNotBound() {
		this.bindIfNotBound(0);
	}

	public final void bindIfNotBound(int textureUnit) {
		if(this.id != currentlyBound) {
			textureUnit = textureUnit >= 0 && textureUnit < 32 ? textureUnit : 0;
			this.bind(textureUnit);
		}
	}
}
