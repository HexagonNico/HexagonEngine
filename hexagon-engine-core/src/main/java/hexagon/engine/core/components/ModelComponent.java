package hexagon.engine.core.components;

import org.json.JSONObject;

import hexagon.engine.core.resources.Model;
import hexagon.engine.math.color.Color;

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
	public final Color color;

	/**
	 * Creates component from json object.
	 * 
	 * @param jsonObject Json object with the component's data.
	 */
	public ModelComponent(JSONObject jsonObject) {
		// TODO - Handle missing value
		this.model = Model.getOrLoad(jsonObject.getString("model"));
		JSONObject colorJson = jsonObject.optJSONObject("color", new JSONObject());
		float r = colorJson.optFloat("r", 0.8f);
		float g = colorJson.optFloat("g", 0.8f);
		float b = colorJson.optFloat("b", 0.8f);
		float a = colorJson.optFloat("a", 1.0f);
		if(r <= 1.0f && g <= 1.0f && b <= 1.0f && b <= 1.0f && a <= 1.0f)
			this.color = new Color(r, g, b, a);
		else
			this.color = new Color((int) r, (int) g, (int) b, (int) a);
	}
}
