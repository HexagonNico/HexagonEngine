package hexagon.engine.core.components;

import hexagon.engine.core.ecs.Component;
import hexagon.engine.core.ecs.GameEntity;
import hexagon.engine.utils.json.JsonObject;

/**
 * A component that holds data about the effect light has on an object.
 * 
 * @author Nico
 */
public class ReflectivityComponent extends Component {
	
	/**Minimum brightness value */
	public float diffuseLight;
	/**Reflectivity for specular lighting */
	public float reflectivity;
	/**Specular lighting strength */
	public float shineDamper;

	/**
	 * Constructs a reflectivity component from a {@link JsonObject}.
	 * Constructor used when loading the component from a json file.
	 * 
	 * @param jsonObject JsonObject containing the component's data.
	 * @param entity GameEntity holding this component.
	 */
	public ReflectivityComponent(GameEntity entity, JsonObject jsonObject) {
		super(entity);
		this.diffuseLight = jsonObject.getFloat("diffuseLight").orElse(0.2f);
		this.reflectivity = jsonObject.getFloat("reflectivity").orElse(0.0f);
		this.shineDamper = jsonObject.getFloat("shineDamper").orElse(10.0f);
	}
}
