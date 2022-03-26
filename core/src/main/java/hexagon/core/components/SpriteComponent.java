package hexagon.core.components;

import hexagon.core.base.Component;
import hexagon.core.base.GameEntity;
import hexagon.core.rendering.SpriteRenderer;
import hexagon.lwjgl.opengl.Texture;

public final class SpriteComponent extends Component {
	
	private Texture texture;

	public SpriteComponent(GameEntity entity) {
		super(entity);
		this.texture = Texture.getOrLoad("/textures/logo.png");
		SpriteRenderer.addToBatch(this);
	}

	public Texture texture() {
		return this.texture;
	}

	// TODO - Set texture
}
