package hexagon.core.states;

import hexagon.core.GameEntity;
import hexagon.core.components.Component;
import hexagon.core.systems.GameSystem;
import hexagon.utils.json.JsonObject;
import hexagon.utils.reflection.ReflectionHelper;
import hexagon.utils.reflection.SerializeField;

/**
 * Utility class used to load game states.
 * 
 * @author Nico
 */
public final class StateLoader {

	/**
	 * Loads a game state file by adding all entities (or their components) to the given state.
	 * 
	 * @param state An empty state
	 * @param filePath Path to the state file
	 * 
	 * @return The loaded state
	 */
	public static GameState loadState(GameState state, String filePath) {
		JsonObject stateJson = JsonObject.fromFileOrEmpty(filePath);
		stateJson.getArray("entities").ifPresent(entitiesJsonArray -> entitiesJsonArray.forEachObject(entityJson -> {
			GameEntity entity = state.createEntity();
			entityJson.keySet().forEach(componentKey -> {
				Component component = ReflectionHelper.instantiate(componentKey, Component.class);
				JsonObject componentJson = entityJson.getObjectOrEmpty(componentKey);
				entity.addComponent(component);
				ReflectionHelper.forEachField(component.getClass(), field -> {
					if(field.isAnnotationPresent(SerializeField.class)) {
						SerializeField annotation = field.getAnnotation(SerializeField.class);
						String name = annotation.value().isBlank() ? field.getName() : annotation.value();
						// TODO - Support all types
						if(ReflectionHelper.isFieldAssignable(field, float.class, double.class)) {
							ReflectionHelper.setField(component, field, componentJson.getFloat(name, 0.0f));
						}
					}
				});
			});
		}));
		stateJson.getArrayOrEmpty("systems").forEachString(systemClass -> {
			GameSystem<?> system = ReflectionHelper.instantiate(systemClass, GameSystem.class);
			state.startSystem(system);
		});
		return state;
	}

	/**Class should not be instantiated */
	private StateLoader() {}
}
