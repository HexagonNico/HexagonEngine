package hexagon.core.components;

import hexagon.core.base.Component;
import hexagon.core.base.GameEntity;

public abstract class Script extends Component {

	private boolean started = false;

	public final boolean isStarted() {
		return this.started;
	}

	public final void setStarted() {
		this.started = true;
	}

	public abstract void start(GameEntity entity);

	public abstract void update(GameEntity entity);
}
