package hexagon.engine.ecs;

import hexagon.engine.Log;
import hexagon.engine.utils.json.JsonArray;
import hexagon.engine.utils.json.JsonObject;

/**
 * Utility class used to load a scene file in the game manager.
 * A scene is nothing but a collection of entities with their components.
 * 
 * @author Nico
 */
public final class SceneLoader {

	/**Class should not be instantiated */
	private SceneLoader() {}

	/**
	 * Loads a scene from a json file.
	 * <p>
	 * 	If this method fails to read the file, no entity is created.
	 * 	If a component cannot be read, it is not instantiated.
	 * </p>
	 * 
	 * @param filePath Path to the scene file from the resources folder, starting with {@code / }.
	 * @param gameManager Game manager needed to create entities.
	 */
	public static void loadScene(String filePath, GameManager gameManager) {
		JsonObject sceneJson = JsonObject.fromFileOrEmpty(filePath);
		JsonArray entitiesArray = sceneJson.getArray("entities").orElse(JsonArray.empty());
		entitiesArray.forEachObject(entityJson -> {
			GameEntity entity = gameManager.createEntity();
			entityJson.keySet().forEach(key -> {
				try {
					JsonObject componentJson = entityJson.getObject(key).orElse(JsonObject.empty());
					Object component = Class.forName(key).getConstructor(GameEntity.class, JsonObject.class).newInstance(entity, componentJson);
					entity.addComponent((Component) component);
				} catch(NoSuchMethodException e) {
					Log.error("Cannot instantiate component " + key + ": missing constructor");
				} catch(ClassNotFoundException e) {
					Log.error("Class " + key + " does not exist");
				} catch(Exception e) {
					Log.error("Component of class " + key + " cannot be instantiated");
				}
			});
		});
	}
}
