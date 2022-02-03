package hexagon.engine.core.components;

import hexagon.engine.core.ecs.Component;
import hexagon.engine.core.ecs.GameEntity;
import hexagon.engine.math.matrix.Matrices;
import hexagon.engine.math.matrix.Matrix4;
import hexagon.engine.math.vector.Float3;
import hexagon.engine.utils.json.JsonObject;

/**
 * Component that holds a 3D transformation.
 * 
 * @author Nico
 */
public class Transform3D extends Component {
	
	/**Position in a 3D space */
	public Float3 position;
	/**Rotation around x, y and z axis */
	public Float3 rotation;
	/**Scale on x, y and z axis */
	public Float3 scale;

	/**
	 * Constructs a transform component from a {@link JsonObject}.
	 * Constructor used when loading the component from a json file.
	 * 
	 * @param jsonObject JsonObject containing the component's data.
	 * @param entity GameEntity holding this component.
	 */
	public Transform3D(GameEntity entity, JsonObject jsonObject) {
		super(entity);
		JsonObject positionJson = jsonObject.getObject("position").orElse(JsonObject.empty());
		JsonObject rotationJson = jsonObject.getObject("rotation").orElse(JsonObject.empty());
		JsonObject scaleJson = jsonObject.getObject("scale").orElse(JsonObject.empty());
		float x = positionJson.getFloat("x").orElse(0.0f);
		float y = positionJson.getFloat("y").orElse(0.0f);
		float z = positionJson.getFloat("z").orElse(0.0f);
		this.position = new Float3(x, y, z);
		x = rotationJson.getFloat("x").orElse(0.0f);
		y = rotationJson.getFloat("y").orElse(0.0f);
		z = rotationJson.getFloat("z").orElse(0.0f);
		this.rotation = new Float3(x, y, z);
		x = scaleJson.getFloat("x").orElse(1.0f);
		y = scaleJson.getFloat("y").orElse(1.0f);
		z = scaleJson.getFloat("z").orElse(1.0f);
		this.scale = new Float3(x, y, z);
	}

	/**
	 * Computes the transformation matrix using position, rotation and scale in this component.
	 * 
	 * @return The transformation matrix.
	 */
	public Matrix4 transformationMatrix() {
		return Matrices.transformation(this.position, this.rotation, this.scale);
	}
}
