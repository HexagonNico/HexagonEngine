package hexagon.example;

import hexagon.core.ApplicationLauncher;
import hexagon.lwjgl.opengl.OpenGL;
import hexagon.utils.Log;

public class ExampleGame extends ApplicationLauncher {
	
	@Override
	protected void onInit() {
		Log.info("Init!");
		OpenGL.depthTest(false);
		OpenGL.alphaBlending(true);
	}

	@Override
	protected void onClose() {
		Log.info("Close!");
	}

	public static void main(String[] args) {
		new ExampleGame().init("Example Game", 800, 450).run("/test_state.json");
	}
}
