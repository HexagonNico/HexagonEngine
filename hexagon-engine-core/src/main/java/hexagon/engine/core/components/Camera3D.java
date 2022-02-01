package hexagon.engine.core.components;

import org.json.JSONObject;

import hexagon.engine.math.matrix.Matrices;
import hexagon.engine.math.matrix.Matrix4;
import hexagon.engine.math.vector.Float3;

/**
 * Component for 3D camera.
 * 
 * @author Nico
 */
public final class Camera3D {
	
	/**Current main camera */
	private static Camera3D main = null;

	/**
	 * Gets the current main camera.
	 * 
	 * @return Component of main camera.
	 */
	public static Camera3D main() {
		return main;
	}

	/**Field of view */
	public float fov;
	/**Near distance plane */
	public float nearPlane;
	/**Far distance plane */
	public float farPlane;

	/**Camera position */
	public Float3 position;
	/**Camera rotation up and down */
	public float pitch;
	/**Camera rotation left and right */
	public float yaw;

	/**
	 * Creates component from json object.
	 * 
	 * @param jsonObject Json object with the component's data.
	 */
	public Camera3D(JSONObject jsonObject) {
		JSONObject projectionJson = jsonObject.optJSONObject("projection", new JSONObject());
		this.fov = projectionJson.optFloat("fov", 70.0f);
		this.nearPlane = projectionJson.optFloat("near", 0.1f);
		this.farPlane = projectionJson.optFloat("far", 100.0f);
		JSONObject positionJson = jsonObject.optJSONObject("position", new JSONObject());
		this.position = new Float3(positionJson.optFloat("x", 0.0f), positionJson.optFloat("y", 0.0f), positionJson.optFloat("z", 0.0f));
		this.pitch = jsonObject.optFloat("pitch", 0.0f);
		this.yaw = jsonObject.optFloat("yaw", 0.0f);
		boolean isMain = jsonObject.optBoolean("main", false);
		if(isMain || main == null) main = this;
	}

	/**
	 * Computes the projection matrix to be used in shaders.
	 * 
	 * @return Projection matrix.
	 */
	public Matrix4 projectionMatrix() {
		return Matrices.projection(this.fov, this.nearPlane, this.farPlane);
	}

	/**
	 * Computes the view matrix to be used in shaders.
	 * 
	 * @return View matrix.
	 */
	public Matrix4 viewMatrix() {
		return Matrices.view(this.position, this.pitch, this.yaw);
	}
}
