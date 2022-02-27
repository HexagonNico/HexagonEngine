package hexagon.engine.ecs.components;

import hexagon.engine.ecs.Component;
import hexagon.engine.ecs.GameEntity;
import hexagon.engine.math.color.Color;
import hexagon.engine.utils.json.JsonObject;

public class LightComponent extends Component {
	
	private Color color = new Color(1.0f, 1.0f, 1.0f);
	private float intensity = 1.0f;

	public LightComponent(GameEntity entity) {
		super(entity);
	}

	@Override
	protected void init(JsonObject jsonObject) {
		jsonObject.getObject("color").ifPresent(colorJson -> {
			float r = colorJson.getFloat("r").orElse(this.color.r());
			float g = colorJson.getFloat("g").orElse(this.color.g());
			float b = colorJson.getFloat("b").orElse(this.color.b());
			float a = colorJson.getFloat("a").orElse(this.color.a());
			this.color = new Color(r, g, b, a);
		});
		this.intensity = jsonObject.getFloat("intensity").orElse(this.intensity);
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

	public final float getIntensity() {
		return this.intensity;
	}

	public final void setIntensity(float intensity) {
		this.intensity = Math.max(intensity, 0.0f);
	}
}
