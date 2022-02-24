package hexagon.engine.example;

import hexagon.engine.ecs.GameManager;
import hexagon.engine.ecs.SceneLoader;
import hexagon.engine.ecs.systems.LightSystem;
import hexagon.engine.ecs.systems.ModelRenderer;
import hexagon.engine.ecs.systems.SpriteRenderer;
import hexagon.engine.glfw.Engine;
import hexagon.engine.glfw.WindowSize;
import hexagon.engine.opengl.OpenGL;
import hexagon.engine.Application;

public final class TestGame extends Application {

	@Override
	protected void onInit() {
		OpenGL.alphaBlending(true);
		OpenGL.cullFace(true);
		OpenGL.depthTest(true);
		SceneLoader.loadScene("/scenes/test.json");
		GameManager.addSystem(new SpriteRenderer());
		GameManager.addSystem(new ModelRenderer());
		GameManager.addSystem(new LightSystem());
	}

	@Override
	protected void onUpdate() {
		OpenGL.clearFrame(0.5f, 0.5f, 1.0f);
		OpenGL.setViewport(WindowSize.width(), WindowSize.height());
	}

	public static void main(String[] args) {
		Engine.windowResizable(true);
		new TestGame().init("Hello!", 800, 450).run();
	}
}
