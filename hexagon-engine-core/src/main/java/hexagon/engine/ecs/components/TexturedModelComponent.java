package hexagon.engine.ecs.components;

import hexagon.engine.ecs.GameEntity;
import hexagon.engine.opengl.Texture;
import hexagon.engine.utils.json.JsonObject;

/**
 * A component class that stores a reference to a model resource and a texture.
 * <p>
 * 	This class is needed to avoid loading the same model once for each entity that uses it.
 * 	The component holds a reference to the model resource and the texture.
 * </p>
 * 
 * @author Nico
 */
public class TexturedModelComponent extends ModelComponent {

	/**Reference to the texture */
	public Texture texture;

	/**
	 * Constructs a textured model component from a {@link JsonObject}.
	 * Constructor used when loading the component from a json file.
	 * 
	 * @param jsonObject JsonObject containing the component's data.
	 * @param entity GameEntity holding this component.
	 */
	public TexturedModelComponent(GameEntity entity, JsonObject jsonObject) {
		super(entity, jsonObject);
		jsonObject.getString("texture").ifPresentOrElse(textureKey -> {
			this.texture = Texture.getOrLoad(textureKey);
		}, () -> {
			this.texture = Texture.ERROR;
		});
	}
}