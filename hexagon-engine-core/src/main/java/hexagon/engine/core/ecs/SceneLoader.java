package hexagon.engine.core.ecs;

import hexagon.engine.core.components.SpriteComponent;
import hexagon.engine.utils.json.JsonArray;
import hexagon.engine.utils.json.JsonObject;

public final class SceneLoader {

	public static void loadScene(String filePath, GameManager gameManager) {
		JsonObject sceneJson = JsonObject.fromFileOrEmpty(filePath);
		JsonArray entitiesArray = sceneJson.getArray("entities").orElse(JsonArray.empty());
		entitiesArray.forEachObject(entityJson -> {
			GameEntity entity = gameManager.createEntity();
			// TODO - From class name
			JsonObject spriteJson = entityJson.getObject("hexagon.engine.core.components.SpriteComponent").orElse(JsonObject.empty());
			entity.addComponent(new SpriteComponent(spriteJson));
		});
	}
}
