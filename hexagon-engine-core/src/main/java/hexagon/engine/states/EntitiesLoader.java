package hexagon.engine.states;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

import hexagon.engine.ecs.Component;
import hexagon.engine.ecs.GameEntity;
import hexagon.engine.ecs.components.Camera;
import hexagon.engine.ecs.components.LightComponent;
import hexagon.engine.ecs.components.ModelComponent;
import hexagon.engine.ecs.components.ReflectivityComponent;
import hexagon.engine.ecs.components.SpriteComponent;
import hexagon.engine.ecs.components.TexturedModelComponent;
import hexagon.engine.ecs.components.Transform2D;
import hexagon.engine.ecs.components.Transform3D;
import hexagon.engine.utils.Log;
import hexagon.engine.utils.json.JsonArray;
import hexagon.engine.utils.json.JsonObject;

public final class EntitiesLoader {

	private static final HashMap<String, Function<GameEntity, ? extends Component>> componentsRegistry = new HashMap<>();

	static {
		registerComponent("hexagon.engine.Transform2D", Transform2D::new);
		registerComponent("hexagon.engine.Transform3D", Transform3D::new);
		registerComponent("hexagon.engine.SpriteComponent", SpriteComponent::new);
		registerComponent("hexagon.engine.Camera", Camera::new);
		registerComponent("hexagon.engine.ModelComponent", ModelComponent::new);
		registerComponent("hexagon.engine.TexturedModelComponent", TexturedModelComponent::new);
		registerComponent("hexagon.engine.LightComponent", LightComponent::new);
		registerComponent("hexagon.engine.ReflectivityComponent", ReflectivityComponent::new);
	}

	public static void loadEntities(GameState state, String entitiesFile) {
		JsonObject jsonObject = JsonObject.fromFileOrEmpty(entitiesFile);
		JsonArray entitiesJsonArray = jsonObject.getArray("entities").orElse(JsonArray.empty());
		ArrayList<GameEntity> loadedEntities = new ArrayList<>();
		entitiesJsonArray.forEachObject(entityJson -> {
			GameEntity entity = state.createEntity();
			loadedEntities.add(entity);
			entityJson.keySet().forEach(key -> {
				if(componentsRegistry.containsKey(key)) {
					JsonObject componentJson = entityJson.getObject(key).orElse(JsonObject.empty());
					Component component = entity.addComponent(componentsRegistry.get(key));
					component.init(loadedEntities, componentJson);
				} else {
					Log.error("Component " + key + " is not in registry");
				}
			});
		});
	}

	public static void registerComponent(String key, Function<GameEntity, ? extends Component> constructor) {
		if(!componentsRegistry.containsKey(key)) {
			componentsRegistry.put(key, constructor);
		} else {
			Log.error("Registry already contains component with key " + key);
		}
	}

	private EntitiesLoader() {}
}
