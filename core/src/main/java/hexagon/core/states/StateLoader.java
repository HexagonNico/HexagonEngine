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
		stateJson.getArrayOrEmpty("entities").forEachArray(entityJsonArray -> {
			GameEntity entity = state.createEntity();
			entityJsonArray.forEachObject(componentJson -> {
				componentJson.getString("class").ifPresentOrElse(componentClass -> {
					try {
						Component component = (Component) Class.forName(componentClass).getConstructor().newInstance();
						component.init(componentJson);
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
				}, () -> Log.error("A component is missing a class"));
			});
		});
		stateJson.getArrayOrEmpty("systems").forEachObject(systemJson -> {
			systemJson.getString("class").ifPresentOrElse(systemClass -> {
				try {
					GameSystem<?> system = (GameSystem<?>) Class.forName(systemClass).getConstructor().newInstance();
					system.init(systemJson);
					state.startSystem(system);
				} catch (NoSuchMethodException e) {
					Log.error("Couldn't find a no-args constructor for class " + systemClass);
				} catch (ClassNotFoundException e) {
					Log.error("Class " + systemClass + " not found: couldn't instantiate system");
				} catch (ClassCastException e) {
					Log.error("Class " + systemClass + " does not extend " + GameSystem.class);
				} catch (Exception e) {
					Log.error("Couldn't instantiate system " + systemClass + ": " + e.getMessage());
					e.printStackTrace();
				}
			}, () -> Log.error("A system is missing a class"));
		});
		return state;
	}

	/**Class should not be instantiated */
	private StateLoader() {}
}
