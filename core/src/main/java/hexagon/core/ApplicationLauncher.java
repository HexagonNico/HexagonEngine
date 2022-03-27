package hexagon.core;

import hexagon.core.base.GameState;
import hexagon.core.rendering.RenderingSystem;
import hexagon.lwjgl.glfw.Engine;
import hexagon.lwjgl.opengl.OpenGL;
import hexagon.utils.Log;

public class ApplicationLauncher {

	protected final AbstractApplication init(String title, int width, int height) {
		try {
			Engine.init();
			long window = Engine.createWindow(title, width, height);
			return new ApplicationInstance(window);
		} catch (Exception any) {
			Log.error("Exception in initialization");
			any.printStackTrace();
			return new ApplicationError();
		}
	}

	protected static abstract sealed class AbstractApplication permits ApplicationInstance, ApplicationError {

		public abstract void run(String initialState);
	}

	protected static final class ApplicationInstance extends AbstractApplication {

		private final long window;

		private ApplicationInstance(long window) {
			this.window = window;
		}

		@Override
		public void run(String initialState) {
			try {
				Engine.showWindow(this.window);
				GameState.loadState(initialState);
				Log.info("Now running...");
				while(Engine.isRunning(this.window)) {
					Engine.update(this.window);
					GameState.update();
					RenderingSystem.renderingProcess();
				}
			} catch (Exception any) {
				Log.error("Uncaught exception in main");
				any.printStackTrace();
			} finally {
				OpenGL.cleanUp();
				Engine.terminate(this.window);
			}
		}
	}

	protected static final class ApplicationError extends AbstractApplication {

		@Override
		public void run(String initialState) {
			Log.error("Application error");
		}
	}
}
