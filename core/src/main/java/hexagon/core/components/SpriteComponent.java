package hexagon.core.components;

import hexagon.core.rendering.Camera;
import hexagon.lwjgl.opengl.DrawCalls;
import hexagon.lwjgl.opengl.ShaderProgram;
import hexagon.lwjgl.opengl.Texture;
import hexagon.lwjgl.opengl.VertexObject;
import hexagon.math.vector.Float2;
import hexagon.utils.json.JsonObject;

/**
 * A component that represents a sprite.
 * Contains a reference to a texture and a reference to a shader.
 * 
 * @author Nico
 */
public final class SpriteComponent extends Render2DComponent {

	private static VertexObject quadModel = VertexObject.with()
			.attribute(0, new float[] {-0.5f,0.5f, -0.5f,-0.5f, 0.5f,-0.5f, 0.5f,0.5f}, 2)
			.indices(new int[] {0,1,3, 3,1,2})
			.create();
	
	/**The texture this sprite uses */
	private Texture texture = Texture.ERROR;
	/**The shader this sprite uses */
	private ShaderProgram shader = ShaderProgram.getOrLoad("/shaders/sprites_default.json");
	/**The offset of the sprite */
	private Float2 offset = Float2.ZERO;

	@Override
	public void init(JsonObject jsonObject) {
		super.init(jsonObject);
		jsonObject.getString("texture").ifPresent(textureFile -> {
			this.texture = Texture.getOrLoad(textureFile);
		});
		jsonObject.getString("shader").ifPresent(shaderFile -> {
			this.shader = ShaderProgram.getOrLoad(shaderFile);
		});
		jsonObject.getObject("offset").ifPresent(offsetJson -> {
			this.offset = new Float2(offsetJson.getFloat("x", 0.0f), offsetJson.getFloat("y", 0.0f));
		});
	}

	@Override
	public void render(Transform transform) {
		quadModel.activate(() -> {
			this.texture.bind();
			ShaderProgram.start(this.shader);
			this.shader.load("projection_matrix", Camera.main().projection());
			this.shader.load("view_matrix", Camera.main().view());
			this.shader.load("transformation_matrix", transform.matrix());
			this.shader.load("offset", this.offset);
			DrawCalls.drawElements(6);
			ShaderProgram.stop();
		});
	}

	/**
	 * Gets the texture that this sprite uses.
	 * 
	 * @return A {@link Texture} object
	 */
	public Texture texture() {
		return this.texture;
	}

	/**
	 * Sets this sprite's texture.
	 * 
	 * @param texture A {@link Texture} object
	 */
	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	/**
	 * Sets this sprite's texture.
	 * 
	 * @param path Path to the texture file, from the resources folder, starting with {@code /}
	 */
	public void setTexture(String path) {
		this.setTexture(Texture.getOrLoad(path));
	}

	/**
	 * Gets the shader that this sprite uses.
	 * 
	 * @return A {@link ShaderProgram} object
	 */
	public ShaderProgram shader() {
		return this.shader;
	}

	/**
	 * Sets this sprite's shader.
	 * 
	 * @param shader A {@link ShaderProgram} object
	 */
	public void setShader(ShaderProgram shader) {
		this.shader = shader;
	}

	/**
	 * Sets this sprite's shader.
	 * 
	 * @param path Path to the shader file, from the resources folder, starting with {@code /}
	 */
	public void setShader(String path) {
		this.setShader(ShaderProgram.getOrLoad(path));
	}

	/**
	 * Gets this sprite's offset.
	 * Sprites are rendered at their transform's position + their offset.
	 * 
	 * @return A {@link Float2} vector with the sprite's offset
	 */
	public Float2 offset() {
		return this.offset;
	}

	/**
	 * Sets this sprite's offset.
	 * Sprites are rendered at their transform's position + their offset.
	 * 
	 * @param offset A {@link Float2} vector with the sprite's offset
	 */
	public void setOffset(Float2 offset) {
		this.offset = offset;
	}

	/**
	 * Sets this sprite's offset.
	 * Sprites are rendered at their transform's position + their offset.
	 * 
	 * @param x Offset on the x axis
	 * @param y Offset on the y axis
	 */
	public void setOffset(float x, float y) {
		this.setOffset(new Float2(x, y));
	}

	/**
	 * Sets this sprite's x offset.
	 * Sprites are rendered at their transform's position + their offset.
	 * 
	 * @param x Offset on the x axis
	 */
	public void setOffsetX(float x) {
		this.setOffset(x, this.offset.y());
	}

	/**
	 * Sets this sprite's y offset.
	 * Sprites are rendered at their transform's position + their offset.
	 * 
	 * @param y Offset on the y axis
	 */
	public void setOffsetY(float y) {
		this.setOffset(this.offset.x(), y);
	}
}
