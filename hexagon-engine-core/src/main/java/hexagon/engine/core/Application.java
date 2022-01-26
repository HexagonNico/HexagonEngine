package hexagon.engine.core;

import org.lwjgl.opengl.GL11;

import hexagon.engine.lwjgl.Engine;
import hexagon.engine.lwjgl.Window;

public class Application {
	
	protected final void run() {
		Engine.init();
		Engine.configure(false, true);
		Window.makeVisible();
		Engine.createCapabilities();
		while(!Window.shouldClose()) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			GL11.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
			Window.update();
		}
		Window.destroy();
		Engine.terminate();
	}

	public static void main(String[] args) {
		new Application().run();
	}
}
