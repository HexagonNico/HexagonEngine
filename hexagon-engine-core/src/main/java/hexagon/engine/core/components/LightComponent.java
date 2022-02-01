package hexagon.engine.core.components;

import org.json.JSONObject;

import hexagon.engine.math.color.Color;

public final class LightComponent {
	
	public Color color;
	public float intensity;

	public LightComponent(JSONObject jsonObject) {
		JSONObject colorJson = jsonObject.optJSONObject("color", new JSONObject());
		float r = colorJson.optFloat("r", 1.8f);
		float g = colorJson.optFloat("g", 1.8f);
		float b = colorJson.optFloat("b", 1.8f);
		if(r <= 1.0f && g <= 1.0f && b <= 1.0f && b <= 1.0f)
			this.color = new Color(r, g, b);
		else
			this.color = new Color((int) r, (int) g, (int) b);
		this.intensity = jsonObject.optFloat("intensity", 1.0f);
	}
}
