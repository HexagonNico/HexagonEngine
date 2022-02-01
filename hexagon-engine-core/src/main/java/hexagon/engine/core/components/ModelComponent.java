package hexagon.engine.core.components;

import org.json.JSONObject;

import hexagon.engine.core.resources.Model;
import hexagon.engine.math.vector.Float3;

/**
 * A component class that stores a reference to a model resource.
 * <p>
 * 	This class is needed to avoid loading the same model once for each entity that uses it.
 * 	The component holds a reference to the model resource.
 * </p>
 * 
 * @author Nico
 */
public final class ModelComponent {
	
	/**Reference to the model resource */
	public final Model model;
	/**Color tint of the model */
	public final Float3 color;

	/**
	 * Creates component from json object.
	 * 
	 * @param jsonObject Json object with the component's data.
	 */
	public ModelComponent(JSONObject jsonObject) {
		// TODO - Handle missing value
		this.model = Model.getOrLoad(jsonObject.getString("model"));
		JSONObject colorJson = jsonObject.optJSONObject("color", new JSONObject());
		this.color = new Float3(colorJson.optFloat("r", 0.8f), colorJson.optFloat("g", 0.8f), colorJson.optFloat("b", 0.8f));
	}
}
