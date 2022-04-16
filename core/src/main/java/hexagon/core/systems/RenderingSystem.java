package hexagon.core.systems;

import java.util.HashMap;

import hexagon.core.components.Component;
import hexagon.lwjgl.glfw.WindowSize;
import hexagon.lwjgl.opengl.OpenGL;
import hexagon.lwjgl.opengl.ShaderProgram;
import hexagon.lwjgl.opengl.VertexObject;

public abstract class RenderingSystem<T extends Component> extends GameSystem<T> {

	private static HashMap<Class<?>, RenderingSystem<?>> renderers = new HashMap<>();

	public RenderingSystem(Class<T> componentType) {
		super(componentType);
		renderers.put(this.getClass(), this);
	}

	public abstract void renderAll();

	public static synchronized void renderingProcess() {
		OpenGL.clearFrame(0.8f, 0.8f, 0.8f); // TODO - Set color
		OpenGL.setViewport(WindowSize.width(), WindowSize.height());
		renderers.values().forEach(RenderingSystem::renderAll);
		ShaderProgram.stop();
		VertexObject.unbind();
	}
}
