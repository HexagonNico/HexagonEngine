package hexagon.core.components;

import java.util.ArrayList;
import java.util.function.BiConsumer;

import hexagon.core.GameEntity;
import hexagon.math.geometry.Rectangle;
import hexagon.math.geometry.Size;
import hexagon.math.vector.Float2;
import hexagon.utils.json.JsonObject;

public class TriggerArea extends Component {

	// TODO - Other collision shapes
	private Rectangle shape = new Rectangle(Float2.ZERO, 1.0f, 1.0f);

	// TODO - Collision layer / mask

	private ArrayList<BiConsumer<GameEntity, GameEntity>> listeners = new ArrayList<>();

	@Override
	public void init(JsonObject jsonObject) {
		jsonObject.getObject("collisionShape").ifPresent(shapeJson -> {
			shapeJson.getObject("offset").ifPresent(offsetJson -> {
				float x = offsetJson.getFloat("x", this.shape.center().x());
				float y = offsetJson.getFloat("y", this.shape.center().y());
				this.shape = new Rectangle(new Float2(x, y), this.shape.size());
			});
			shapeJson.getObject("size").ifPresent(sizeJson -> {
				float width = sizeJson.getFloat("width", this.shape.size().width());
				float height = sizeJson.getFloat("height", this.shape.size().height());
				this.shape = new Rectangle(this.shape.center(), new Size(width, height));
			});
		});
	}

	public final Rectangle shape() {
		return this.shape;
	}

	// TODO - Set stuff

	public void onBodyEntered(GameEntity thisEntity, GameEntity otherEntity) {
		this.listeners.forEach(consumer -> consumer.accept(thisEntity, otherEntity));
	}

	public final void addListener(BiConsumer<GameEntity, GameEntity> action) {
		this.listeners.add(action);
	}

	public final void removeListener(BiConsumer<GameEntity, GameEntity> action) {
		this.listeners.remove(action);
	}
}
