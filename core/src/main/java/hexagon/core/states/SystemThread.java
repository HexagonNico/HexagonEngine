package hexagon.core.states;

import java.util.Timer;
import java.util.TimerTask;

import hexagon.core.components.Component;
import hexagon.core.systems.GameSystem;
import hexagon.utils.Log;

/**
 * A system thread is responsible for running a {@link GameSystem} on its own thread.
 * <p>
 * 	All {@link GameSystem}s run on different threads.
 * 	This class stores a reference to those threads to stop them when needed.
 * </p>
 * 
 * @author Nico
 */
public final class SystemThread<T extends Component> extends TimerTask {

	/**Timer that runs the game system at fixed intervals */
	private final Timer timer = new Timer();

	/**The game state in which this system is running */
	private final GameState gameState;
	/**The system handled by this thread */
	private final GameSystem<T> system;

	/**Used to compute delta time between updates */
	private long previousTime;

	/**
	 * Creates and starts a system threads.
	 * 
	 * @param state The game state in which the system is running
	 * @param system The game system to run
	 */
	protected SystemThread(GameState state, GameSystem<T> system) {
		this.gameState = state;
		this.system = system;
		this.timer.schedule(this, 0, 20); // TODO - Timer time
	}

	@Override
	public void run() {
		try {
			long time = System.nanoTime();
			this.gameState.getComponents(this.system.componentType).forEach((entity, component) -> {
				this.system.process(entity, this.system.componentType.cast(component), (time - previousTime) / 1e9f);
			});
			previousTime = time;
		} catch (Exception e) {
			Log.error("Error in system " + this.system + ": shutting down");
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
