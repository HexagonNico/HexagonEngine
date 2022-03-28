package hexagon.core.base;

import hexagon.utils.json.JsonObject;

public abstract class Component {

	public final GameEntity entity;

	public Component(GameEntity entity) {
		this.entity = entity;
	}

	public abstract void init(JsonObject jsonObject);
}
