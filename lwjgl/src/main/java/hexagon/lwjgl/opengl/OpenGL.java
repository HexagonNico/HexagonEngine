package hexagon.lwjgl.opengl;

import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import hexagon.utils.Log;

/**
 * Utility class used to create OpenGL objects
 * and to wrap some OpenGL functions.
 * 
 * @author Nico
 */
public final class OpenGL {

	static {
		// TODO - Write comment
		GL.createCapabilities();
	}

	/**Keeps track of all VAOs to delete them later */
	private static final ArrayList<Integer> vaos = new ArrayList<>();
	/**Keeps track of all VBOs to delete them later */
	private static final ArrayList<Integer> vbos = new ArrayList<>();
	/**Keeps track of all shaders to delete them later */
	private static final ArrayList<Integer> shaders = new ArrayList<>();
	/**Keeps track of all shader programs to detach shaders and delete them later */
	private static final HashMap<Integer, ArrayList<Integer>> shaderPrograms = new HashMap<>();
	/**Keeps track of all textures to delete them later */
	private static final ArrayList<Integer> textures = new ArrayList<>();

	/**
	 * Clears the frame buffer and swaps the color buffer.
	 * Can be called every frame to clear what was on the screen on the previous frame.
	 * 
	 * @param red Red component for background color.
	 * @param green Green component for background color.
	 * @param blue Blue component for background color.
	 */
	public static void clearFrame(float red, float green, float blue) {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(red, green, blue, 1.0f);
	}

	// TODO - Documentation
	public static void setViewport(int width, int height) {
		GL11.glViewport(0, 0, width, height);
	}

	/**
	 * Toggles depth test.
	 * When enabled OpenGL will render further objects behind closer objects.
	 * 
	 * @param enable True to enable, false to disable.
	 */
	public static void depthTest(boolean enable) {
		if(enable) GL11.glEnable(GL11.GL_DEPTH_TEST);
		else GL11.glDisable(GL11.GL_DEPTH_TEST);
	}

	/**
	 * Toggles back face culling.
	 * When enabled OpenGL will not render faces that are not visible.
	 * 
	 * @param enable True to enable, false to disable.
	 */
	public static void cullFace(boolean enable) {
		if(enable) GL11.glEnable(GL11.GL_CULL_FACE);
		else GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}

	/**
	 * Toggles alpha blending.
	 * When enabled OpenGL will render transparent pixels as transparent.
	 * 
	 * @param enable True to enable, false to disable.
	 */
	public static void alphaBlending(boolean enable) {
		if(enable) GL11.glEnable(GL11.GL_BLEND);
		else GL11.glDisable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}

	/**
	 * Creates a vertex array object using {@link GL30#glGenVertexArrays()}
	 * and stores the VAO id in a list to keep track of it.
	 * <p>
	 * 	VAOs should be created with this method instead of {@link GL30#glGenVertexArrays()} because they need to be deleted at the end.
	 * 	VAOs are deleted by calling {@link OpenGL#cleanUp()}.
	 * </p>
	 * 
	 * @return The vao id of the newly created vertex array object.
	 */
	public static int createVAO() {
		int vao = GL30.glGenVertexArrays();
		vaos.add(vao);
		Log.info("Created VAO with id " + vao);
		return vao;
	}

	/**
	 * Creates a vertex buffer object using {@link GL30#glGenBuffers()}
	 * and stores the VBO id in a list to keep track of it.
	 * <p>
	 * 	VBOs should be created with this method instead of {@link GL30#glGenBuffers()} because they need to be deleted at the end.
	 * 	VBOs are deleted by calling {@link OpenGL#cleanUp()}.
	 * </p>
	 * 
	 * @return The vbo id of the newly created vertex buffer object.
	 */
	public static int createVBO() {
		int vbo = GL15.glGenBuffers();
		vbos.add(vbo);
		Log.info("Created VBO with id " + vbo);
		return vbo;
	}

	/**
	 * Creates a vertex shader using {@link GL30#glCreateShader(int)} and {@link GL20#GL_VERTEX_SHADER}
	 * and stores the shader id in a list to keep track of it.
	 * <p>
	 * 	Shaders should be created with this method instead of {@link GL30#glCreateShader(int)} because they need to be deleted at the end.
	 * 	Shaders are deleted by calling {@link OpenGL#cleanUp()}.
	 * </p>
	 * 
	 * @return The id of the newly created shader object.
	 */
	public static int createVertexShader() {
		int id = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
		shaders.add(id);
		Log.info("Created vertex shader with id " + id);
		return id;
	}

	/**
	 * Creates a fragment shader using {@link GL30#glCreateShader(int)} and {@link GL20#GL_FRAGMENT_SHADER}
	 * and stores the shader id in a list to keep track of it.
	 * <p>
	 * 	Shaders should be created with this method instead of {@link GL30#glCreateShader(int)} because they need to be deleted at the end.
	 * 	Shaders are deleted by calling {@link OpenGL#cleanUp()}.
	 * </p>
	 * 
	 * @return The id of the newly created shader object.
	 */
	public static int createFragmentShader() {
		int id = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
		shaders.add(id);
		Log.info("Created fragment shader with id " + id);
		return id;
	}

	/**
	 * Creates a shader program using {@link GL20#glCreateProgram()}
	 * and stores the program id in a list to keep track of it.
	 * <p>
	 * 	Shader programs should be created with this method instead of {@link GL20#glCreateProgram()} because they need to be deleted at the end.
	 * 	Shader programs are deleted by calling {@link OpenGL#cleanUp()}.
	 * </p>
	 * 
	 * @return The id of the newly created shader program.
	 */
	public static int createShaderProgram() {
		int id = GL20.glCreateProgram();
		shaderPrograms.put(id, new ArrayList<>());
		Log.info("Created shader program with id " + id);
		return id;
	}

	/**
	 * Attaches a shader to a shader program using {@link GL20#glAttachShader(int, int)}
	 * and stores the shader id in a map to keep track of it.
	 * 
	 * <p>
	 * 	Shaders should be attached with this method instead of {@link GL20#glAttachShader(int, int)} because they need to be detached at the end.
	 * 	Shaders are detached by calling {@link OpenGL#cleanUp()}.
	 * </p>
	 * 
	 * @param program Id of the shader program
	 * @param shader Id of the shader
	 */
	public static void attachShaderToProgram(int program, int shader) {
		GL20.glAttachShader(program, shader);
		shaderPrograms.get(program).add(shader);
	}

	/**
	 * Creates a texture object using {@link GL11#glGenTextures()}
	 * and stores the texture id in a list to keep track of it.
	 * <p>
	 * 	Textures should be created with this method instead of {@link GL11#glGenTextures()} because they need to be deleted at the end.
	 * 	Textures are deleted by calling {@link OpenGL#cleanUp()}.
	 * </p>
	 * 
	 * @return The id of the newly created texture object.
	 */
	public static int createTexture() {
		int id = GL11.glGenTextures();
		textures.add(id);
		Log.info("Created texture with id " + id);
		return id;
	}

	/**
	 * Clean up function that is called when terminating the engine.
	 * Deletes all VAOs, VBOs, shaders and textures.
	 */
	public static void cleanUp() {
		Log.info("Deleting " + vaos.size() + " VAOs and " + vbos.size() + " VBOs");
		vaos.forEach(GL30::glDeleteVertexArrays);
		vbos.forEach(GL15::glDeleteBuffers);
		Log.info("Deleting " + shaders.size() + " shaders and " + shaderPrograms.size() + " programs");
		shaders.forEach(GL20::glDeleteShader);
		shaderPrograms.forEach((program, shaders) -> shaders.forEach(shader -> GL20.glDetachShader(program, shader)));
		shaderPrograms.keySet().forEach(GL20::glDeleteProgram);
		Log.info("Deleting " + textures.size() + " textures");
		textures.forEach(GL15::glDeleteTextures);
	}
}
