package hexagon.core.base;

import java.util.Optional;
import java.util.function.Function;

public final class GameEntity {

	private final GameState state;

	public GameEntity(GameState state) {
		this.state = state;
	}

	public <T extends Component> Optional<T> getComponent(Class<T> type) {
		return this.state.getComponent(this, type);
	}

	public <T extends Component> T addComponent(Function<GameEntity, T> constructor) {
		return this.state.addComponent(this, constructor);
	}

	public <T extends Script> T addScript(Function<GameEntity, T> constructor) {
		return this.state.addScript(this, constructor);
	}

	// TODO - Get script?
}
