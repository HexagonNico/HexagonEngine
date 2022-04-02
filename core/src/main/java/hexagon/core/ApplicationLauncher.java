package hexagon.core;

import hexagon.core.rendering.RenderingSystem;
import hexagon.core.states.GameState;
import hexagon.core.systems.SystemRunner;
import hexagon.lwjgl.glfw.Engine;
import hexagon.lwjgl.opengl.OpenGL;
import hexagon.utils.Log;

public abstract class ApplicationLauncher {

	protected final AbstractApplication init(String title, int width, int height) {
		try {
			Engine.init();
			long window = Engine.createWindow(title, width, height);
			return new ApplicationInstance(window, this::onInit, this::onClose);
		} catch (Exception any) {
			Log.error("Exception in initialization");
			any.printStackTrace();
			return new ApplicationError();
		}
	}

	protected abstract void onInit();

	protected abstract void onClose();

	protected static abstract sealed class AbstractApplication permits ApplicationInstance, ApplicationError {

		public abstract void run(String initialState);
	}

	protected static final class ApplicationInstance extends AbstractApplication {

		private final long window;
		private final Runnable onInit;
		private final Runnable onClose;

		private ApplicationInstance(long window, Runnable onInit, Runnable onClose) {
			this.window = window;
			this.onInit = onInit;
			this.onClose = onClose;
		}

		@Override
		public void run(String initialState) {
			try {
				Engine.showWindow(this.window);
				GameState.loadState(initialState);
				SystemRunner.startSystems();
				this.onInit.run();
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
				this.onClose.run();
				OpenGL.cleanUp();
				SystemRunner.stopSystems();
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
