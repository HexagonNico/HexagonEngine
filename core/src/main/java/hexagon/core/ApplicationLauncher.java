package hexagon.core;

import hexagon.core.states.GameState;
import hexagon.core.systems.RenderingSystem;
import hexagon.lwjgl.glfw.Engine;
import hexagon.lwjgl.opengl.OpenGL;
import hexagon.utils.Log;

/**
 * Main class that can be extended to create the entry point for the application.
 * 
 * @author Nico
 */
public abstract class ApplicationLauncher {

	/**
	 * Initializes the application.
	 * 
	 * @param title Title of the window
	 * @param width Width of the window
	 * @param height Height of the window
	 * 
	 * @return An application instance that can be started
	 */
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

	/**
	 * Method called after the engine has started.
	 * Can be used to start systems.
	 */
	protected abstract void onInit();

	/**
	 * Method called when closing the application.
	 * Can be used for clean up.
	 */
	protected abstract void onClose();

	/**
	 * Sealed abstract class to make sure the engine is initialized before the application starts.
	 */
	protected static abstract sealed class AbstractApplication permits ApplicationInstance, ApplicationError {

		/**
		 * Runs the application.
		 * Should be called from the main method to start the application.
		 * 
		 * @param initialState First state to load after the application was initialized
		 */
		public abstract void run(String initialState);
	}

	/**
	 * Concrete application instance.
	 */
	protected static final class ApplicationInstance extends AbstractApplication {

		/**Window handle */
		private final long window;
		/**Init method reference */
		private final Runnable onInit;
		/**Close method reference */
		private final Runnable onClose;

		/**
		 * Creates application instance.
		 * 
		 * @param window Window handle
		 * @param onInit Init method reference
		 * @param onClose Close method reference
		 */
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
				GameState.current().clear();
				Engine.terminate(this.window);
			}
		}
	}

	/**
	 * Application error.
	 * Used when an error in the initialization occurs.
	 */
	protected static final class ApplicationError extends AbstractApplication {

		@Override
		public void run(String initialState) {
			Log.error("Application error");
		}
	}
}
