package hexagon.core.states;

import java.util.HashMap;
import java.util.Optional;
import java.util.function.Predicate;

import hexagon.core.GameEntity;
import hexagon.core.components.Component;

/**
 * Class that represents a table of components.
 * Uses the component's class as a row index and the entity as the column index.
 * Used in {@link GameState} to store components.
 * 
 * @author Nico
 */
public final class ComponentsTable {

	/**The table holding the components */
	private final HashMap<Class<?>, HashMap<GameEntity, Component>> table = new HashMap<>();

	/**
	 * Adds a component to the table.
	 * If any of the given parameters is {@code null} this method does nothing.
	 * 
	 * @param entity The entity that should hold this component
	 * @param component The component to add
	 */
	public void add(GameEntity entity, Component component) {
		if(entity != null && component != null) {
			Class<?> componentKey = this.getKey(component.getClass());
			if(this.table.containsKey(componentKey)) {
				this.table.get(componentKey).put(entity, component);
			} else {
				HashMap<GameEntity, Component> map = new HashMap<>();
				map.put(entity, component);
				this.table.put(componentKey, map);
			}
		}
	}

	/**
	 * Finds a component in the table if present.
	 * 
	 * @param <T> Type of the component
	 * @param entity The entity that holds that component
	 * @param type Class of the component to look for
	 * 
	 * @return An {@link Optional} containing the required component or
	 * 		an empty {@link Optional} if the table does not contain said component
	 * 		or if the given entity or the given type are {@code null}
	 */
	public <T extends Component> Optional<T> find(GameEntity entity, Class<T> type) {
		if(entity != null && type != null) {
			Class<?> componentKey = this.getKey(type);
			if(this.table.containsKey(componentKey)) {
				HashMap<GameEntity, Component> components = this.table.get(componentKey);
				if(components.containsKey(entity)) {
					return Optional.of(type.cast(components.get(entity)));
				}
			}
		}
		return Optional.empty();
	}

	/**
	 * Gets all the components of a certain type from all entities in the table.
	 * 
	 * @param type The type of component to look for
	 * 
	 * @return A {@link HashMap} containing all the components of the requested type
	 * 		that uses the {@link GameEntity} holding them as key
	 * 		or an empty {@link HashMap} if no components of that type are found
	 * 		or if the given type is {@code null}
	 */
	public HashMap<GameEntity, Component> getAll(Class<?> type) {
		if(type != null) {
			Class<?> componentKey = this.getKey(type);
			if(this.table.containsKey(componentKey)) {
				return this.table.get(componentKey);
			}
		}
		return new HashMap<>();
	}

	/**
	 * Used internally to get a component's class key.
	 * 
	 * @param componentType Component's class
	 * 
	 * @return The last superclass of the given one that directly extends {@link Component}
	 */
	private Class<?> getKey(Class<?> componentType) {
		Class<?> superClass = componentType.getSuperclass();
		return superClass.equals(Component.class) ? componentType : this.getKey(superClass);
	}

	/**
	 * Removes all components from the table if the given predicate is satisfied.
	 * 
	 * @param predicate A predicate which returns {@code true} for elements to be removed
	 */
	public void removeIf(Predicate<Component> predicate) {
		this.table.forEach((type, map) -> map.values().removeIf(predicate));
	}
}
