package hexagon.engine.core;

import hexagon.engine.lwjgl.Engine;
import hexagon.engine.lwjgl.OpenGL;
import hexagon.engine.lwjgl.Window;

public class Application {
	
	protected final void run() {
		Engine.init();
		Engine.configure(false, true);
		Window.makeVisible();
		Engine.createCapabilities();
		while(!Window.shouldClose()) {
			OpenGL.clearFrame(0.9f, 0.9f, 0.9f);
			Window.update();
		}
		Window.destroy();
		Engine.terminate();
	}

	public static void main(String[] args) {
		new Application().run();
	}
}
