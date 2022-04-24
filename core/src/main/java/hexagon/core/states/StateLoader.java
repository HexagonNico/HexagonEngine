package hexagon.core.states;

import hexagon.core.GameEntity;
import hexagon.core.components.Component;
import hexagon.core.systems.GameSystem;
import hexagon.utils.Log;
import hexagon.utils.json.JsonObject;
import hexagon.utils.reflection.ReflectionException;
import hexagon.utils.reflection.ReflectionHelper;

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
				try {
					Component component = ReflectionHelper.instantiate(componentKey, Component.class);
					JsonObject componentJson = entityJson.getObjectOrEmpty(componentKey);
					component.init(componentJson);
					entity.addComponent(component);
				} catch (ReflectionException e) {
					Log.error("Could not instantiate component " + componentKey);
					e.printStackTrace();
				}
			});
		}));
		stateJson.getArrayOrEmpty("systems").forEachString(systemClass -> {
			try {
				GameSystem<?> system = ReflectionHelper.instantiate(systemClass, GameSystem.class);
				state.startSystem(system);
			} catch (ReflectionException e) {
				Log.error("Could not instantiate system " + systemClass);
				e.printStackTrace();
			}
		});
		return state;
	}

	/**Class should not be instantiated */
	private StateLoader() {}
}
