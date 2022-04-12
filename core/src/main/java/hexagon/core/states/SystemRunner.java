package hexagon.core.states;

import java.util.HashMap;

import hexagon.core.systems.GameSystem;
import hexagon.utils.Log;

/**
 * Class responsible for holding a reference to all {@link SystemThread}s running.
 * Used in {@link GameState} class.
 * 
 * @author Nico
 */
public final class SystemRunner {
	
	/**Map holding all the running threads */
	private final HashMap<Class<?>, SystemThread<?>> systems = new HashMap<>();

	/**
	 * Starts a {@link SystemThread}.
	 * 
	 * @param gameState The state in which the system should run
	 * @param system The system to run
	 */
	public void start(GameState gameState, GameSystem<?> system) {
		if(system != null && gameState != null) {
			Log.info("Starting system " + system);
			systems.put(system.getClass(), new SystemThread<>(gameState, system));
		}
	}

	/**
	 * Stops a {@link SystemThread}.
	 * 
	 * @param type The class of the {@link GameSystem} whose thread needs to be stopped
	 */
	public void stop(Class<? extends GameSystem<?>> type) {
		if(this.systems.containsKey(type)) {
			this.systems.get(type).shutdown();
		}
	}

	/**
	 * Stops all the running systems.
	 */
	public void stopAll() {
		this.systems.values().forEach(SystemThread::shutdown);
	}
}
