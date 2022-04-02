package hexagon.core.base;

import java.util.Optional;

public final class GameEntity {

	private final GameState state;

	public GameEntity(GameState state) {
		this.state = state;
	}

	public <T extends Component> T getComponent(Class<T> type) {
		return this.state.findComponent(this, type).orElse(null);
	}

	public <T extends Component> Optional<T> findComponent(Class<T> type) {
		return this.state.findComponent(this, type);
	}

	public void addComponent(Component component) {
		this.state.addComponent(this, component);
	}

	public <T extends Component> void removeComponent(Class<T> type) {
		this.getComponent(type).queueRemove();
	}
}
