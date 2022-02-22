package hexagon.engine.ecs.components;

import hexagon.engine.ecs.GameEntity;
import hexagon.engine.math.color.Color;
import hexagon.engine.utils.json.JsonObject;

/**
 * A component that represents a light source in a 3D space.
 * 
 * @author Nico
 */
public class LightComponent extends Transform3D {
	
	/**Color of the light */
	public Color color;
	/**Light intensity */
	public float intensity;

	/**
	 * Constructs a light component from a {@link JsonObject}.
	 * Constructor used when loading the component from a json file.
	 * 
	 * @param jsonObject JsonObject containing the component's data.
	 * @param entity GameEntity holding this component.
	 */
	public LightComponent(GameEntity entity, JsonObject jsonObject) {
		super(entity, jsonObject);
		jsonObject.getObject("color").ifPresentOrElse(colorJson -> {
			float r = colorJson.getFloat("r").orElse(0.0f);
			float g = colorJson.getFloat("g").orElse(0.0f);
			float b = colorJson.getFloat("b").orElse(0.0f);
			float a = colorJson.getFloat("a").orElse(1.0f);
			this.color = new Color(r, g, b, a);
		}, () -> {
			this.color = new Color(1.0f, 1.0f, 1.0f);
		});
		this.intensity = jsonObject.getFloat("intensity").orElse(1.0f);
	}
}
