package hexagon.core.components;

import hexagon.core.rendering.Camera;
import hexagon.utils.json.JsonObject;

public final class CameraComponent extends Component {

	public final Camera camera = new Camera();

	@Override
	public void init(JsonObject jsonObject) {
		jsonObject.getObject("position").ifPresent(positionJson -> {
			float x = positionJson.getFloat("x", this.camera.position().x());
			float y = positionJson.getFloat("y", this.camera.position().y());
			float z = positionJson.getFloat("z", this.camera.position().z());
			this.camera.setPosition(x, y, z);
		});
		jsonObject.getObject("rotation").ifPresent(rotationJson -> {
			float pitch = rotationJson.getFloat("pitch", this.camera.pitch());
			float yaw = rotationJson.getFloat("yaw", this.camera.yaw());
			float roll = rotationJson.getFloat("roll", this.camera.roll());
			this.camera.setRotation(pitch, yaw, roll);
		});
		jsonObject.getObject("projection").ifPresent(projectionJson -> {
			float fov = projectionJson.getFloat("fov", this.camera.fov());
			float near = projectionJson.getFloat("near", this.camera.nearPlane());
			float far = projectionJson.getFloat("far", this.camera.farPlane());
			this.camera.setProjection(fov, near, far);
		});
		this.camera.setMain();
		// TODO - Handle multiple cameras
	}
}
