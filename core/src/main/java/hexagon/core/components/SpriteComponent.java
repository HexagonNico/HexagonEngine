package hexagon.core.components;

import hexagon.core.base.Component;
import hexagon.core.base.GameEntity;
import hexagon.core.rendering.SpriteRenderer;
import hexagon.lwjgl.opengl.ShaderProgram;
import hexagon.lwjgl.opengl.Texture;

public final class SpriteComponent extends Component {
	
	private Texture texture;
	private ShaderProgram shader;

	public SpriteComponent(GameEntity entity) {
		super(entity);
		// TODO - Load things
		this.texture = Texture.getOrLoad("/textures/logo.png");
		this.shader = ShaderProgram.getOrLoad("/shaders/test_shader.json");
		SpriteRenderer.addToBatch(this);
	}

	public Texture texture() {
		return this.texture;
	}

	public ShaderProgram shader() {
		return this.shader;
	}

	// TODO - Set stuff
}
