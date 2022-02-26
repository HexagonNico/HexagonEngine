package hexagon.engine.example;

import hexagon.engine.Application;
import hexagon.engine.glfw.Engine;
import hexagon.engine.glfw.WindowSize;
import hexagon.engine.opengl.OpenGL;
import hexagon.engine.states.GameManager;

public final class TestGame extends Application {

	@Override
	protected void onInit() {
		OpenGL.alphaBlending(true);
		OpenGL.cullFace(true);
		OpenGL.depthTest(true);
		GameManager.changeState(new TestState());
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
