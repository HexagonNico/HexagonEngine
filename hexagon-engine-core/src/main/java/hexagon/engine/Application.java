package hexagon.engine;

import hexagon.engine.glfw.Engine;
import hexagon.engine.opengl.OpenGL;
import hexagon.engine.states.GameManager;
import hexagon.engine.utils.Log;

/**
 * Base class that represents the entry point of the game.
 * <p>
 * 	The game class should extend this Application class
 * 	and will contain the main method.
 * </p>
 * <p>
 * 	From a class called {@code Game} that extends {@code Application}
 * 	the application can be created as follows:
 * 	{@code new Game().init().run()}
 * </p>
 * 
 * @author Nico
 */
public abstract class Application {

	/**
	 * Initializes the application with the default window settings:
	 * <ul>
	 * 	<li>Title: "untitled"</li>
	 * 	<li>Window size: 300x300</li>
	 * </ul>
	 * This method should be called from the main method to start the application.
	 * 
	 * @return The initialized application that can be used to call {@link AbstractApplication#run()}
	 */
	protected final AbstractApplication init() {
		return this.init("untitled", 300, 300);
	}

	/**
	 * Initializes the application by creating the GLFW window with the given options.
	 * This method should be called from the main method to start the application.
	 * 
	 * @param title Title of the window
	 * @param width Width of the window in pixels
	 * @param height Height of the window in pixels
	 * 
	 * @return The initialized application that can be used to call {@link AbstractApplication#run()}
	 */
	protected final AbstractApplication init(String title, int width, int height) {
		try {
			long window = Engine.createWindow(title, width, height);
			return new ApplicationInstance(this::onInit, this::onUpdate, window);
		} catch (Exception any) {
			Log.error("Error in initialization");
			any.printStackTrace();
			return new ApplicationError();
		}
	}

	/**
	 * Method call after the initialization of the engine.
	 * Can be used for the game's initialization phase.
	 */
	protected abstract void onInit();

	/**
	 * Called every loop in the game's main loop.
	 */
	protected abstract void onUpdate();

	/**
	 * Class that represents an instantiated application created using the {@link Application#init()} method.
	 * This makes sure that {@code init()} is called before {@code run()}.
	 * The class being abstract allows the handling of exceptions in the initialization phase.
	 */
	protected static sealed abstract class AbstractApplication permits ApplicationError, ApplicationInstance {

		/**
		 * The method that contains the main game loop,
		 * used to start the game from the main method.
		 */
		public abstract void run();
	}

	/**
	 * Class that represents an instantiated working application.
	 * Contains the main game loop.
	 */
	private static final class ApplicationInstance extends AbstractApplication {

		/**Runnable that contains the {@link Application#onInit()} method */
		private final Runnable onInit;
		/**Runnable that contains the {@link Application#onUpdate()} method */
		private final Runnable onUpdate;

		/**Represents the window handle */
		private final long window;

		/**
		 * Creates the application instance.
		 * Called from {@link Application#init()}.
		 * 
		 * @param onInit {@link Application#onInit()} method
		 * @param onUpdate {@link Application#onUpdate()} method
		 * @param window The window handle
		 */
		public ApplicationInstance(Runnable onInit, Runnable onUpdate, long window) {
			this.onInit = onInit;
			this.onUpdate = onUpdate;
			this.window = window;
		}

		// TODO - OpenGL#clearColor and OpenGL#viewport

		@Override
		public final void run() {
			try {
				Engine.showWindow(this.window);
				this.onInit.run();
	
				while(Engine.running(this.window)) {
					this.onUpdate.run();
					GameManager.update();
					Engine.update(this.window);
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

	/**
	 * Application class used for when an error in initialization occurs.
	 * Will print a fatal error message to the console and terminate the program.
	 */
	private static final class ApplicationError extends AbstractApplication {

		@Override
		public final void run() {
			Log.fatal("Application could not be created");
		}
	}
}
