package hexagon.engine.ecs;

import java.util.List;
import java.util.Optional;

import hexagon.engine.utils.json.JsonObject;

/**
 * Base component class. All components inherit from this class.
 * 
 * @author Nico
 */
public abstract class Component {
	
	/**The entity that holds this component */
	protected final GameEntity entity;

	/**
	 * Creates component.
	 * 
	 * @param entity The entity that holds this component.
	 */
	public Component(GameEntity entity) {
		this.entity = entity;
	}

	/**
	 * Initializes this component with the data from an entity bundle file.
	 * Called when loading entities.
	 * 
	 * @param jsonObject JSON object containing the component's data.
	 */
	public abstract void init(JsonObject jsonObject);

	/**
	 * Initializes this component with the data from an entity bundle file.
	 * Called when loading entities.
	 * Can be used for components that need to reference other entities.
	 * 
	 * @param loadedEntities List containing all the entities that are being loaded
	 * @param jsonObject JSON object containing the component's data.
	 */
	public void init(List<GameEntity> loadedEntities, JsonObject jsonObject) {
		this.init(jsonObject);
	}

	/**
	 * Gets another component from the same entity holding this one.
	 * 
	 * @param <T> Type of the component.
	 * @param type Component class.
	 * 
	 * @return An optional containing the requested component
	 * 		or an empty optional if this entity does not have said component.
	 */
	public final <T> Optional<T> getSiblingComponent(Class<T> type) {
		return this.entity.getComponent(type);
	}
}
