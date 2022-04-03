package hexagon.core.components;

import hexagon.utils.json.JsonObject;

/**
 * Base class for all components.
 * A component is any data that an entity in the ECS system may store.
 * <p>
 * 	Component classes should ideally only contain data, i.e., instance variables, getters, and setters.
 * 	The only exception to this is the {@link Script} component.
 * </p>
 * <p>
 * 	All classes that inherit from this need a no-args constructor to be instantiated in the state loader.
 * </p>
 * 
 * @author Nico
 */
public abstract class Component {
	
	/**If this component was marked to be removed */
	private boolean remove = false;

	/**
	 * Called when the component is loaded in the current state. Initializes the component.
	 * This method takes in the {@link JsonObject} from the game state's json file
	 * that contains all the component's data and uses it to load values.
	 * 
	 * @param jsonObject JsonObject containing the component's data
	 */
	public abstract void init(JsonObject jsonObject);

	/**
	 * Marks this component to be removed.
	 * Components marked to be removed will be removed the next frame.
	 * Components cannot be removed directly to avoid concurrent modification.
	 */
	public final void markToRemove() {
		this.remove = true;
	}

	/**
	 * Checks if this component was marked to be removed,
	 * i.e., if {@link Component#markToRemove} was called this frame.
	 * 
	 * @return True if this component was marked to be removed, otherwise false.
	 */
	public final boolean markedForRemoval() {
		return this.remove;
	}
}
