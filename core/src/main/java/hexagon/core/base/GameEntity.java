package hexagon.core.base;

import java.util.Optional;

public final class GameEntity {

	private final GameState state;

	public GameEntity(GameState state) {
		this.state = state;
	}

	public <T> T getComponent(Class<T> type) {
		return this.state.findComponent(this, type).orElse(null);
	}

	public <T> Optional<T> findComponent(Class<T> type) {
		return this.state.findComponent(this, type);
	}

	public void addComponent(Object component) {
		this.state.addComponent(this, component);
	}
}
