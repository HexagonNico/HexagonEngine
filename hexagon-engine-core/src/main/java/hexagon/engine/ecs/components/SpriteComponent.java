package hexagon.engine.ecs.components;

import hexagon.engine.ecs.Component;
import hexagon.engine.ecs.GameEntity;
import hexagon.engine.math.color.Color;
import hexagon.engine.math.vector.Float2;
import hexagon.engine.opengl.Texture;
import hexagon.engine.utils.json.JsonObject;

public class SpriteComponent extends Component {

	private Texture texture = Texture.ERROR;
	private Float2 uv = Float2.ZERO;
	private Float2 size = Float2.ONE;
	private Color color = new Color(1.0f, 1.0f, 1.0f);

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

	public final Texture getTexture() {
		return texture;
	}

	public final void setTexture(String texture) {
		if(texture != null) this.texture = Texture.getOrLoad(texture);
	}

	public final Float2 getUv() {
		return uv;
	}

	public final void setUv(Float2 uv) {
		if(uv != null) this.uv = uv;
	}

	public final void setUv(float u, float v) {
		this.setUv(new Float2(u, v));
	}

	public final Float2 getSpriteSize() {
		return size;
	}

	public final void setSpriteSize(Float2 size) {
		if(size != null) this.size = size;
	}

	public final void setSpriteSize(float w, float h) {
		this.setSpriteSize(new Float2(w, h));
	}

	public final Color getColor() {
		return color;
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
