package hexagon.core.systems;

import hexagon.core.GameEntity;
import hexagon.core.components.Component;

public abstract class GameSystem<T extends Component> {

	public final Class<T> componentType;

	public GameSystem(Class<T> componentType) {
		this.componentType = componentType;
	}

	public abstract void process(GameEntity entity, T component);
}
