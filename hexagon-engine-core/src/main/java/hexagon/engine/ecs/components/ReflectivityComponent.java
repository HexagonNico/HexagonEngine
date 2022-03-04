package hexagon.engine.ecs.components;

import hexagon.engine.ecs.Component;
import hexagon.engine.ecs.GameEntity;
import hexagon.engine.math.MathExtra;
import hexagon.engine.utils.json.JsonObject;

public class ReflectivityComponent extends Component {
	
	private float diffuseLight = 0.2f;
	private float reflectivity = 0.0f;
	private float shineDamper = 10.0f;

	public ReflectivityComponent(GameEntity entity) {
		super(entity);
	}

	@Override
	public void init(JsonObject jsonObject) {
		this.diffuseLight = jsonObject.getFloat("diffuseLight", this.diffuseLight);
		this.reflectivity = jsonObject.getFloat("reflectivity", this.reflectivity);
		this.shineDamper = jsonObject.getFloat("shineDamper", this.shineDamper);
	}

	public final float getDiffuse() {
		return this.diffuseLight;
	}

	public final void setDiffuse(float diffuseLight) {
		this.diffuseLight = MathExtra.clamp(diffuseLight, 0.0f, 1.0f);
	}

	public final float getReflectivity() {
		return this.reflectivity;
	}

	public final void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}

	public float getShineDamper() {
		return this.shineDamper;
	}

	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}
}
