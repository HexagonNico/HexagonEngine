package hexagon.core.base;

import java.util.Timer;
import java.util.TimerTask;

import hexagon.utils.Log;

public final class SystemRunner<T> extends TimerTask {

	private final Timer timer = new Timer();

	private final GameSystem<T> system;
	private final GameState state;
	private final Class<T> componentType;

	public SystemRunner(GameSystem<T> system, GameState state, Class<T> componentType) {
		this.system = system;
		this.state = state;
		this.componentType = componentType;
		this.timer.schedule(this, 0, 20);
	}

	@Override
	public void run() {
		try {
			this.state.getComponents(this.componentType).forEach((entity, component) -> {
				this.system.process(entity, this.componentType.cast(component));
			});
		} catch (Exception e) {
			Log.error("Error in system " + this.system.getClass() + ": shutting down");
			e.printStackTrace();
			this.shutdown();
		}
	}

	public void shutdown() {
		this.timer.cancel();
	}
}
