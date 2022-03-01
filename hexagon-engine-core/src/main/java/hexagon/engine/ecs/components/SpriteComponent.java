package hexagon.engine.ecs.components;

import hexagon.engine.ecs.Component;
import hexagon.engine.ecs.GameEntity;
import hexagon.engine.math.color.Color;
import hexagon.engine.math.vector.Float2;
import hexagon.engine.opengl.Texture;
import hexagon.engine.utils.json.JsonObject;

/**
 * Component that represents a sprite that can be rendered.
 * 
 * @author Nico
 */
public class SpriteComponent extends Component {

	/**Sprite's texture */
	private Texture texture = Texture.ERROR;
	/**Top left corner of the sprite in the texture */
	private Float2 uv = Float2.ZERO;
	/**Size of the sprite in the texture */
	private Float2 size = Float2.ONE;
	/**Sprite color tint */
	private Color color = new Color(1.0f, 1.0f, 1.0f);

	/**
	 * Creates a sprite component.
	 * 
	 * @param entity The entity that holds this component
	 */
	public SpriteComponent(GameEntity entity) {
		super(entity);
	}

	@Override
	protected void init(JsonObject jsonObject) {
		jsonObject.getString("texture").ifPresent(textureKey -> {
			this.texture = Texture.getOrLoad(textureKey);
		});
		JsonObject uvJson = jsonObject.getObject("uv").orElse(JsonObject.empty());
		JsonObject sizeJson = jsonObject.getObject("size").orElse(JsonObject.empty());
		this.uv = new Float2(uvJson.getFloat("x").orElse(this.uv.x()), uvJson.getFloat("y").orElse(this.uv.y()));
		this.size = new Float2(sizeJson.getFloat("x").orElse(this.size.x()), sizeJson.getFloat("y").orElse(this.size.y()));
		jsonObject.getObject("color").ifPresent(colorJson -> {
			float r = colorJson.getFloat("r").orElse(this.color.r());
			float g = colorJson.getFloat("g").orElse(this.color.g());
			float b = colorJson.getFloat("b").orElse(this.color.b());
			float a = colorJson.getFloat("a").orElse(this.color.a());
			this.color = new Color(r, g, b, a);
		});
	}

	/**
	 * Gets the texture used by this sprite.
	 * If the texture is null, this returns {@link Texture#ERROR}.
	 * 
	 * @return This sprite's texture
	 */
	public final Texture getTexture() {
		return this.texture != null ? this.texture : Texture.ERROR;
	}

	/**
	 * Sets the texture used by this sprite.
	 * If that texture cannot be found or the value passed is null,
	 * the texture is set to {@link Texture#ERROR}.
	 * 
	 * @param texture Path to the texture file, from the resources folder, starting with {@code /}
	 */
	public final void setTexture(String texture) {
		if(texture != null) this.texture = Texture.getOrLoad(texture);
		else this.texture = Texture.ERROR;
	}

	/**
	 * Gets the sprite's uv, the coordinates of the top left corner of the sprite
	 * from the top left corner of the texture file.
	 * 
	 * @return A {@link Float2} containing the sprite's uv coordinates
	 */
	public final Float2 getUv() {
		return this.uv;
	}

	/**
	 * Sets this sprite's uv, the coordinates of the top left corner of the sprite
	 * from the top left corner of the texture file.
	 * 
	 * @param uv A {@link Float2} containing the sprite's uv coordinates
	 */
	public final void setUv(Float2 uv) {
		if(uv != null) this.uv = uv;
	}

	/**
	 * Sets this sprite's uv, the coordinates of the top left corner of the sprite
	 * from the top left corner of the texture file.
	 * 
	 * @param u U coordinate
	 * @param v V coordinate
	 */
	public final void setUv(float u, float v) {
		this.setUv(new Float2(u, v));
	}

	/**
	 * Gets the size of this sprite in the texture file.
	 * 
	 * @return A {@link Float2} containing the sprite's size
	 */
	public final Float2 getSpriteSize() {
		return this.size;
	}

	/**
	 * Sets this sprite's size in the texture file.
	 * 
	 * @param size A {@link Float2} containing the sprite's size
	 */
	public final void setSpriteSize(Float2 size) {
		if(size != null) this.size = size;
	}

	/**
	 * Sets this sprite's size in the texture file.
	 * 
	 * @param w Sprite width
	 * @param h Sprite height
	 */
	public final void setSpriteSize(float w, float h) {
		this.setSpriteSize(new Float2(w, h));
	}

	public final Color getColor() {
		return this.color;
	}

	public final void setColor(Color color) {
		if(color != null) this.color = color;
	}

	public final void setColor(float r, float g, float b) {
		this.setColor(new Color(r, g, b));
	}

	public final void setColor(float r, float g, float b, float a) {
		this.setColor(new Color(r, g, b, a));
	}

	public final void setColor(int r, int g, int b) {
		this.setColor(new Color(r, g, b));
	}

	public final void setColor(int r, int g, int b, int a) {
		this.setColor(new Color(r, g, b, a));
	}
}
