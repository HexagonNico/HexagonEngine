package hexagon.core.rendering;

import java.util.HashMap;

import hexagon.core.components.Component;
import hexagon.core.systems.GameSystem;
import hexagon.lwjgl.opengl.OpenGL;
import hexagon.utils.Log;

/**
 * Class used internally to run rendering systems on the main thread.
 * 
 * @author Nico
 */
public abstract class RenderingSystem<T extends Component> extends GameSystem<T> {

	private static HashMap<Class<?>, RenderingSystem<?>> renderers = new HashMap<>();

	public static void addSystem(RenderingSystem<?> system) {
		if(renderers.put(system.componentType, system) == null) {
			Log.info("Starting rendering system " + system);
		}
	}

	public static void renderingProcess() {
		OpenGL.clearFrame(0.8f, 0.8f, 0.8f); // TODO - Set color
		renderers.values().forEach(RenderingSystem::processEntities);
		renderers.values().forEach(RenderingSystem::render);
	}

	public RenderingSystem(Class<T> componentType) {
		super(componentType);
	}

	public abstract void processEntities();

	public abstract void render();
}
