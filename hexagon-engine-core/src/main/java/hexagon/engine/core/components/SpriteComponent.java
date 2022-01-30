package hexagon.engine.core.components;

import org.json.JSONObject;

import hexagon.engine.lwjgl.texture.Texture;
import hexagon.engine.math.vector.Float2;

public final class SpriteComponent {

	public final Texture texture;
	public Float2 uv;
	public Float2 size;
	// TODO - Color tint

	public SpriteComponent(JSONObject jsonObject) {
		this.texture = Texture.getOrLoad(jsonObject.getString("texture"));
		JSONObject uvJson = jsonObject.optJSONObject("uv", new JSONObject());
		JSONObject sizeJson = jsonObject.optJSONObject("uv", new JSONObject());
		this.uv = new Float2(uvJson.optFloat("x", 0.0f), uvJson.optFloat("y", 0.0f));
		this.size = new Float2(sizeJson.optFloat("x", 1.0f), sizeJson.optFloat("y", 1.0f));
	}

	public SpriteComponent(String texture, Float2 uv, Float2 size) {
		this.texture = Texture.getOrLoad(texture);
		this.uv = uv;
		this.size = size;
	}

	public SpriteComponent(String texture, float uvx, float uvy, float uvw, float uvh) {
		this(texture, new Float2(uvx, uvy), new Float2(uvw, uvh));
	}

	public SpriteComponent(String texture) {
		this(texture, 0.0f, 0.0f, 1.0f, 1.0f);
	}
}
