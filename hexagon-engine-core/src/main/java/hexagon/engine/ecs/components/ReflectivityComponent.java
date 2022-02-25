package hexagon.engine.ecs.components;

import hexagon.engine.ecs.Component;
import hexagon.engine.ecs.GameEntity;
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

	public ReflectivityComponent(GameEntity entity) {
		super(entity);
	}

	@Override
	protected void init(JsonObject jsonObject) {
		this.diffuseLight = jsonObject.getFloat("diffuseLight").orElse(0.2f);
		this.reflectivity = jsonObject.getFloat("reflectivity").orElse(0.0f);
		this.shineDamper = jsonObject.getFloat("shineDamper").orElse(10.0f);
	}
}
