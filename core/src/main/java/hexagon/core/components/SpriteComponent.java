package hexagon.core.components;

import hexagon.lwjgl.opengl.ShaderProgram;
import hexagon.lwjgl.opengl.Texture;
import hexagon.utils.json.JsonObject;

public final class SpriteComponent {
	
	private Texture texture = Texture.ERROR;
	private ShaderProgram shader = ShaderProgram.getOrLoad("/shaders/sprites_default.json");

	public SpriteComponent(JsonObject jsonObject) {
		jsonObject.getString("texture").ifPresent(textureFile -> {
			this.texture = Texture.getOrLoad(textureFile);
		});
		jsonObject.getString("shader").ifPresent(shaderFile -> {
			this.shader = ShaderProgram.getOrLoad(shaderFile);
		});
		// TODO - SpriteRenderer.addToBatch(this);
	}

	public Texture texture() {
		return this.texture;
	}

	public ShaderProgram shader() {
		return this.shader;
	}

	// TODO - Set stuff
}
