package hexagon.engine.ecs.components;

import hexagon.engine.ecs.GameEntity;
import hexagon.engine.math.color.Color;
import hexagon.engine.resources.Model;
import hexagon.engine.utils.json.JsonObject;

/**
 * A component class that stores a reference to a model resource.
 * <p>
 * 	This class is needed to avoid loading the same model once for each entity that uses it.
 * 	The component holds a reference to the model resource.
 * </p>
 * 
 * @author Nico
 */
public class ModelComponent extends Transform3D {
	
	/**Reference to the model resource */
	public final Model model;
	/**Color tint of the model */
	public Color color;

	/**
	 * Constructs a model component from a {@link JsonObject}.
	 * Constructor used when loading the component from a json file.
	 * 
	 * @param jsonObject JsonObject containing the component's data.
	 * @param entity GameEntity holding this component.
	 */
	public ModelComponent(GameEntity entity, JsonObject jsonObject) {
		super(entity, jsonObject);
		// TODO - Error model
		this.model = Model.getOrLoad(jsonObject.getString("model").orElseThrow());
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
}
