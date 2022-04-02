package hexagon.core.systems;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import hexagon.core.components.Component;
import hexagon.core.states.GameState;
import hexagon.utils.Log;

public final class SystemRunner<T extends Component> extends TimerTask {

	private static HashMap<Class<?>, SystemRunner<?>> systems = new HashMap<>();

	public static <T extends Component> void startSystem(GameSystem<T> system) {
		if(system != null) {
			SystemRunner<T> runner = new SystemRunner<>(system);
			systems.put(system.getClass(), runner);
		}
	}

	public static <T extends GameSystem<?>> void stopSystem(Class<T> type) {
		if(systems.containsKey(type)) {
			systems.get(type).shutdown();
		}
	}

	public static void startSystems() {
		SystemRunner.startSystem(new SpriteRenderer());
		SystemRunner.startSystem(new ScriptSystem());
	}

	public static void stopSystems() {
		systems.values().forEach(SystemRunner::shutdown);
	}

	private final Timer timer = new Timer();

	private final GameSystem<T> system;

	private SystemRunner(GameSystem<T> system) {
		this.system = system;
		this.timer.schedule(this, 0, 20);
	}

	@Override
	public void run() {
		try {
			GameState.current().getComponents(this.system.componentType).forEach((entity, component) -> {
				this.system.process(entity, this.system.componentType.cast(component));
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
