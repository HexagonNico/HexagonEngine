package hexagon.engine.core.components;

import hexagon.engine.lwjgl.texture.Texture;
import hexagon.engine.math.color.Color;
import hexagon.engine.math.vector.Float2;
import hexagon.engine.utils.json.JsonObject;

public class SpriteComponent extends Transform2D {

	public Texture texture;
	public Float2 uv;
	public Float2 size;
	public Color color;

	public SpriteComponent(JsonObject jsonObject) {
		super(jsonObject);
		// TODO - Error texture
		this.texture = Texture.getOrLoad(jsonObject.getString("texture").orElseThrow());
		JsonObject uvJson = jsonObject.getObject("uv").orElse(JsonObject.empty());
		JsonObject sizeJson = jsonObject.getObject("size").orElse(JsonObject.empty());
		this.uv = new Float2(uvJson.getFloat("x").orElse(0.0f), uvJson.getFloat("y").orElse(0.0f));
		this.size = new Float2(sizeJson.getFloat("x").orElse(1.0f), sizeJson.getFloat("y").orElse(1.0f));
		jsonObject.getObject("color").ifPresentOrElse(colorJson -> {
			float r = colorJson.getFloat("r").orElse(0.0f);
			float g = colorJson.getFloat("g").orElse(0.0f);
			float b = colorJson.getFloat("b").orElse(0.0f);
			float a = colorJson.getFloat("a").orElse(1.0f);
			this.color = new Color(r, g, b, a);
		}, () -> {
			this.color = new Color(1.0f, 1.0f, 1.0f);
		});
	}

	public SpriteComponent(String texture) {
		this.texture = Texture.getOrLoad(texture);
		this.uv = Float2.ZERO;
		this.size = Float2.ONE;
		this.color = new Color(1.0f, 1.0f, 1.0f);
	}
}
