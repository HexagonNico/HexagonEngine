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

	public TexturedModelComponent(GameEntity entity) {
		super(entity);
	}

	@Override
	public void init(JsonObject jsonObject) {
		super.init(jsonObject);
		jsonObject.getString("texture").ifPresentOrElse(textureKey -> {
			this.texture = Texture.getOrLoad(textureKey);
		}, () -> {
			this.texture = Texture.ERROR;
		});
	}
}
