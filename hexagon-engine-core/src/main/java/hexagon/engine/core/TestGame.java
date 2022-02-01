package hexagon.engine.core;

import hexagon.engine.core.rendering.ModelRenderer;
import hexagon.engine.core.systems.CameraMovement;
import hexagon.engine.lwjgl.OpenGL;

public final class TestGame extends Application {

	@Override
	protected void onInit() {
		OpenGL.alphaBlending(false);
		OpenGL.cullFace(true);
		OpenGL.depthTest(true);
		this.gameManager.loadScene("/scenes/test.json");
		this.gameManager.addSystem(new ModelRenderer());
		this.gameManager.addSystem(new CameraMovement());
	}
	
	public static void main(String[] args) {
		new TestGame().run();
	}
}
