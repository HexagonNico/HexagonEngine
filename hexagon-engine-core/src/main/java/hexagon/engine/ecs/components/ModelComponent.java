package hexagon.engine.ecs.components;

import hexagon.engine.ecs.Component;
import hexagon.engine.ecs.GameEntity;
import hexagon.engine.math.color.Color;
import hexagon.engine.utils.json.JsonObject;
import hexagon.engine.utils.models.Model;

/**
 * A component class that stores a reference to a model resource.
 * <p>
 * 	This class is needed to avoid loading the same model once for each entity that uses it.
 * 	The component holds a reference to the model resource.
 * </p>
 * 
 * @author Nico
 */
public class ModelComponent extends Component {
	
	/**Reference to the model resource */
	public Model model;
	/**Color tint of the model */
	public Color color;

	public ModelComponent(GameEntity entity) {
		super(entity);
	}

	@Override
	protected void init(JsonObject jsonObject) {
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
