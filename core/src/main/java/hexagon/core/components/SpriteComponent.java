package hexagon.core.components;

import hexagon.core.base.Component;
import hexagon.lwjgl.opengl.Texture;

public final class SpriteComponent extends Component {
	
	private Texture texture;

	public SpriteComponent() {
		//this.texture = Texture.getOrLoad("/textures/logo.png");
	}

	public Texture texture() {
		return this.texture;
	}

	// TODO - Set texture
}
