package hexagon.engine.core;

import hexagon.engine.core.ecs.GameManager;
import hexagon.engine.lwjgl.DrawCalls;
import hexagon.engine.lwjgl.Engine;
import hexagon.engine.lwjgl.OpenGL;
import hexagon.engine.lwjgl.VertexObject;
import hexagon.engine.lwjgl.Window;
import hexagon.engine.lwjgl.shader.Shader;
import hexagon.engine.lwjgl.shader.ShaderProgram;
import hexagon.engine.math.matrix.Matrices;
import hexagon.engine.math.vector.Float3;
import hexagon.engine.utils.Log;
import hexagon.engine.utils.parsers.ObjParser;

public abstract class Application {
	
	protected GameManager gameManager;

	protected abstract void onInit();

	protected final void run() {
		try {
			Engine.errorCallback(System.err);
			Engine.init();
			Engine.configure(false, false);
			Window.makeVisible();
			Engine.createCapabilities();

			this.temporaryInit();
			
			this.gameManager = new GameManager();
			this.onInit();

			while(!Window.shouldClose()) {
				OpenGL.clearFrame(0.5f, 0.5f, 1.0f);
				gameManager.update();
				this.temporaryLoop();
				Window.update();
			}
		} catch(Exception any) {
			Log.error("Uncaught exception in main thread");
			any.printStackTrace();
		} finally {
			OpenGL.cleanUp();
			Window.destroy();
			Engine.terminate();
		}
	}

	// TODO - All of this is temporary

	private VertexObject vertexObject;
	private ShaderProgram shader;
	private int i;

	private void temporaryInit() {
		ObjParser.Data data = ObjParser.parse("/models/dragon.obj");
		this.vertexObject = VertexObject.with()
			.attribute(0, data.vertices, 3)
			.indices(data.indices)
			.create();
		this.shader = ShaderProgram.with()
			.shader(Shader.vertex("/shaders/test_vertex.glsl"))
			.shader(Shader.fragment("/shaders/test_fragment.glsl"))
			.attribute(0, "vertex")
			.create();
		this.i = data.indices.length;
	}

	private Float3 thing = Float3.ZERO;

	private void temporaryLoop() {
		ShaderProgram.start(this.shader);
		this.shader.load("projection_matrix", Matrices.projection(70.0f, 0.1f, 100.0f));
		this.shader.load("view_matrix", Matrices.view(new Float3(0.0f, 0.0f, 5.0f), 0.0f, 0.0f));
		this.thing = this.thing.plus(0.0f, 0.1f, 0.0f);
		this.shader.load("transformation_matrix", Matrices.transformation(new Float3(0.0f, -3.0f, -10.0f), thing, new Float3(1, 1, 1)));
		this.vertexObject.activate(() -> DrawCalls.drawElements(i));
		ShaderProgram.stop();
	}
}
