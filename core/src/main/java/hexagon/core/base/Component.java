package hexagon.core.base;

import java.util.Optional;

import hexagon.utils.json.JsonObject;

public abstract class Component {

	protected final GameEntity entity;

	public Component(GameEntity entity) {
		this.entity = entity;
	}

	public abstract void init(JsonObject jsonObject);

	public final <T extends Component> Optional<T> getSiblingComponent(Class<T> type) {
		return this.entity.getComponent(type);
	}
}
