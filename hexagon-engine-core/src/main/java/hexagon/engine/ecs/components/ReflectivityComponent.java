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
	protected void init(JsonObject jsonObject) {
		this.diffuseLight = jsonObject.getFloat("diffuseLight").orElse(this.diffuseLight);
		this.reflectivity = jsonObject.getFloat("reflectivity").orElse(this.reflectivity);
		this.shineDamper = jsonObject.getFloat("shineDamper").orElse(this.shineDamper);
	}

	public final float getDiffuse() {
		return diffuseLight;
	}

	public final void setDiffuse(float diffuseLight) {
		this.diffuseLight = MathExtra.clamp(diffuseLight, 0.0f, 1.0f);
	}

	public final float getReflectivity() {
		return reflectivity;
	}

	public final void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}

	public float getShineDamper() {
		return shineDamper;
	}

	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}
}
