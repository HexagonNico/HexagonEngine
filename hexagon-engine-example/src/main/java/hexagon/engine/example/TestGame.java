package hexagon.engine.example;

import hexagon.engine.ecs.SceneLoader;
import hexagon.engine.ecs.systems.LightSystem;
import hexagon.engine.ecs.systems.ModelRenderer;
import hexagon.engine.ecs.systems.SpriteRenderer;
import hexagon.engine.glfw.WindowSize;
import hexagon.engine.opengl.OpenGL;
import hexagon.engine.Application;

public final class TestGame extends Application {

	@Override
	protected void onInit() {
		OpenGL.alphaBlending(true);
		OpenGL.cullFace(true);
		OpenGL.depthTest(true);
		SceneLoader.loadScene("/scenes/test.json", this.gameManager);
		this.gameManager.addSystem(new SpriteRenderer());
		this.gameManager.addSystem(new ModelRenderer());
		this.gameManager.addSystem(new LightSystem());
	}

	@Override
	protected void onUpdate() {
		OpenGL.clearFrame(0.5f, 0.5f, 1.0f);
		OpenGL.setViewport(WindowSize.width(), WindowSize.height());
	}
	
	public static void main(String[] args) {
		new TestGame().run();
	}
}
