package hexagon.core.systems;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import hexagon.core.components.Component;
import hexagon.core.states.GameState;
import hexagon.utils.Log;

/**
 * Class used to run a system on its own thread.
 * 
 * @author Nico
 */
public final class SystemRunner<T extends Component> extends TimerTask {

	/**Map containing all running systems */
	private static HashMap<Class<?>, SystemRunner<?>> systems = new HashMap<>();

	/**
	 * Starts a new game system.
	 * 
	 * @param <T> Type of component that the system needs
	 * 
	 * @param system The system to start
	 */
	public static <T extends Component> void startSystem(GameSystem<T> system) {
		if(system != null) {
			Log.info("Starting system " + system);
			SystemRunner<T> runner = new SystemRunner<>(system);
			systems.put(system.getClass(), runner);
		}
	}

	/**
	 * Stops a specific system.
	 * 
	 * @param <T> Type of the system to stop
	 * 
	 * @param type Class of the system to stop
	 */
	public static <T extends GameSystem<?>> void stopSystem(Class<T> type) {
		if(systems.containsKey(type)) {
			systems.get(type).shutdown();
		}
	}

	/**
	 * Starts all systems.
	 * Called from the main application class.
	 */
	public static void startSystems() {
		// TODO - There should be a system registry that starts/stops system when there are/aren't components
		SystemRunner.startSystem(new ScriptSystem());
	}

	/**
	 * Stops all systems.
	 * Called from the main application class.
	 */
	public static void stopSystems() {
		systems.values().forEach(SystemRunner::shutdown);
	}

	/**Timer used to run the system */
	private final Timer timer = new Timer();

	/**The system that is running on this thread */
	private final GameSystem<T> system;

	/**
	 * Creates a system runner.
	 * 
	 * @param system The system to run on this thread
	 */
	private SystemRunner(GameSystem<T> system) {
		this.system = system;
		this.timer.schedule(this, 0, 20); // TODO - Timer time
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

	/**
	 * Stops this system.
	 */
	public void shutdown() {
		Log.info("Shutting down system " + this.system);
		this.timer.cancel();
	}
}
