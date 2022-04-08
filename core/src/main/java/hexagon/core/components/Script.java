package hexagon.core.components;

import hexagon.core.GameEntity;

/**
 * Special type of component used to perform a certain action on an entity every frame.
 * Scripts are an exception to the idea of components only storing data, not logic.
 * 
 * @author Nico
 */
public abstract class Script extends Component {

	/**Script was initialized */
	private boolean started = false;

	/**
	 * Checks if this script was already initialized,
	 * i.e., if {@link Script#start(GameEntity)} was already called.
	 * 
	 * @return True if the script has already started, otherwise false.
	 */
	public final boolean isStarted() {
		return this.started;
	}

	/**
	 * Marks this script as started.
	 * Called from the script system.
	 */
	public final void setStarted() {
		this.started = true;
	}

	/**
	 * Called when this script is processed by the script system for the first time.
	 * 
	 * @param entity The entity that holds this component
	 */
	public abstract void start(GameEntity entity);

	/**
	 * Called every frame by the script system.
	 * 
	 * @param entity The entity that holds this component
	 * @param deltaTime Time elapsed since last update (in seconds)
	 */
	public abstract void update(GameEntity entity, float deltaTime);
}
