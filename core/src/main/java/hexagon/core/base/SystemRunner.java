package hexagon.core.base;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import hexagon.utils.Log;

public final class SystemRunner<T extends Component> extends TimerTask {

	private static HashMap<Class<?>, SystemRunner<?>> systems = new HashMap<>();

	public static <T extends Component> void startSystem(GameSystem<T> system, Class<T> componentType) {
		if(system != null && componentType != null) {
			SystemRunner<T> runner = new SystemRunner<>(system, componentType);
			systems.put(system.getClass(), runner);
		}
	}

	public static <T extends GameSystem<?>> void stopSystem(Class<T> type) {
		if(systems.containsKey(type)) {
			systems.get(type).shutdown();
		}
	}

	public static void stopSystems() {
		systems.values().forEach(SystemRunner::shutdown);
	}

	private final Timer timer = new Timer();

	private final GameSystem<T> system;
	private final Class<T> componentType;

	private SystemRunner(GameSystem<T> system, Class<T> componentType) {
		this.system = system;
		this.componentType = componentType;
		this.timer.schedule(this, 0, 20);
	}

	@Override
	public void run() {
		try {
			GameState.current().getComponents(this.componentType).forEach((entity, component) -> {
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
