package hexagon.core.base;

import java.util.Optional;

public class Component {

	protected final GameEntity entity;

	public Component(GameEntity entity) {
		this.entity = entity;
	}

	public <T extends Component> Optional<T> getSiblingComponent(Class<T> type) {
		return this.entity.getComponent(type);
	}
}
