package hexagon.core.components;

import hexagon.math.geometry.Rectangle;
import hexagon.math.geometry.Size;
import hexagon.math.vector.Float2;
import hexagon.utils.json.JsonObject;

public class PhysicsBody2D extends Component {

	// TODO - Other collision shapes
	private Rectangle collisionShape = new Rectangle(Float2.ZERO, 1.0f, 1.0f);

	@Override
	public void init(JsonObject jsonObject) {
		jsonObject.getObject("collisionShape").ifPresent(shapeJson -> {
			shapeJson.getObject("offset").ifPresent(offsetJson -> {
				float x = offsetJson.getFloat("x", this.collisionShape.center().x());
				float y = offsetJson.getFloat("y", this.collisionShape.center().y());
				this.collisionShape = new Rectangle(new Float2(x, y), this.collisionShape.center());
			});
			shapeJson.getObject("size").ifPresent(sizeJson -> {
				float width = sizeJson.getFloat("width", this.collisionShape.size().width());
				float height = sizeJson.getFloat("height", this.collisionShape.size().height());
				this.collisionShape = new Rectangle(this.collisionShape.center(), new Size(width, height));
			});
		});
	}

	public Rectangle collisionShape() {
		return this.collisionShape;
	}

	// TODO - Set/get stuff
}
