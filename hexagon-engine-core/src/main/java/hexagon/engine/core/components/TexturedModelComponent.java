package hexagon.engine.core.components;

import org.json.JSONObject;

import hexagon.engine.core.resources.Model;
import hexagon.engine.lwjgl.texture.Texture;

/**
 * A component class that stores a reference to a model resource and a texture.
 * <p>
 * 	This class is needed to avoid loading the same model once for each entity that uses it.
 * 	The component holds a reference to the model resource and the texture.
 * </p>
 * 
 * @author Nico
 */
public final class TexturedModelComponent {

	/**Reference to the model resource */
	public final Model model;
	/**Reference to the texture */
	public final Texture texture;

	/**
	 * Creates component from json object.
	 * 
	 * @param jsonObject Json object with the component's data.
	 */
	public TexturedModelComponent(JSONObject jsonObject) {
		// TODO - Handle missing value
		this.model = Model.getOrLoad(jsonObject.getString("model"));
		this.texture = Texture.getOrLoad(jsonObject.getString("texture"));
	}
}
