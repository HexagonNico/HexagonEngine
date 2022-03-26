package hexagon.core.components;

import hexagon.core.base.Component;
import hexagon.core.base.GameEntity;
import hexagon.core.rendering.SpriteRenderer;
import hexagon.lwjgl.opengl.ShaderProgram;
import hexagon.lwjgl.opengl.Texture;
import hexagon.utils.json.JsonObject;

public final class SpriteComponent extends Component {
	
	private Texture texture;
	private ShaderProgram shader;

	public SpriteComponent(GameEntity entity) {
		super(entity);
	}

	@Override
	public void init(JsonObject jsonObject) {
		this.texture = Texture.getOrLoad(jsonObject.getString("texture").orElse(""));
		this.shader = ShaderProgram.getOrLoad(jsonObject.getString("shader").orElse(""));
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
