package hexagon.core.states;

import hexagon.core.GameEntity;
import hexagon.core.components.Component;
import hexagon.core.systems.GameSystem;
import hexagon.utils.Log;
import hexagon.utils.json.JsonObject;

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
		stateJson.getArrayOrEmpty("entities").forEachObject(entityJson -> {
			GameEntity entity = state.createEntity();
			entityJson.keySet().forEach(componentClass -> {
				try {
					Component component = (Component) Class.forName(componentClass).getConstructor().newInstance();
					component.init(entityJson.getObjectOrEmpty(componentClass));
					entity.addComponent(component);
				} catch (NoSuchMethodException e) {
					Log.error("Couldn't find a no-args constructor for class " + componentClass);
				} catch (ClassNotFoundException e) {
					Log.error("Class " + componentClass + " not found: couldn't instantiate component");
				} catch (ClassCastException e) {
					Log.error("Class " + componentClass + " does not extend " + Component.class);
				} catch (Exception e) {
					Log.error("Couldn't instantiate component " + componentClass + ": " + e.getMessage());
					e.printStackTrace();
				}
			});
		});
		stateJson.getArrayOrEmpty("systems").forEachString(systemString -> {
			try {
				GameSystem<?> system = (GameSystem<?>) Class.forName(systemString).getConstructor().newInstance();
				state.startSystem(system);
			} catch (NoSuchMethodException e) {
				Log.error("Couldn't find a no-args constructor for class " + systemString);
			} catch (ClassNotFoundException e) {
				Log.error("Class " + systemString + " not found: couldn't instantiate system");
			} catch (ClassCastException e) {
				Log.error("Class " + systemString + " does not extend " + GameSystem.class);
			} catch (Exception e) {
				Log.error("Couldn't instantiate system " + systemString + ": " + e.getMessage());
				e.printStackTrace();
			}
		});
		return state;
	}

	/**Class should not be instantiated */
	private StateLoader() {}
}
