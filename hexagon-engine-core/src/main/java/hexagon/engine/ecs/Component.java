package hexagon.engine.ecs;

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

	protected abstract void init(JsonObject jsonObject);

	/**
	 * Gets another component from the same entity holding this one.
	 * 
	 * @param <T> Type of the component.
	 * @param type Component class.
	 * 
	 * @return An optional containing the requested component
	 * 		or an empty optional if this entity does not have said component.
	 */
	public <T> Optional<T> getSiblingComponent(Class<T> type) {
		return this.entity.getComponent(type);
	}
}
