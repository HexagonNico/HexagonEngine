package hexagon.engine.lwjgl;

import java.util.ArrayList;
import java.util.HashMap;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * Utility class that wraps the most used OpenGL functions.
 * 
 * @author Nico
 */
public final class OpenGL {
	
	/**Keeps track of all VAOs to delete them later */
	private static final ArrayList<Integer> vaos = new ArrayList<>();
	/**Keeps track of all VBOs to delete them later */
	private static final ArrayList<Integer> vbos = new ArrayList<>();
	private static final ArrayList<Integer> shaders = new ArrayList<>();
	private static final HashMap<Integer, ArrayList<Integer>> shaderPrograms = new HashMap<>();

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

	/**
	 * Used in {@link VertexObject} to create a vertex array object.
	 * <p>
	 * 	All VAOs that are created are immediately stored in a list so that
	 * 	they can be deleted later by calling {@link OpenGL#cleanUp()}.
	 * </p>
	 * 
	 * @return The vao id of the newly created vertex array object.
	 */
	public static int createVAO() {
		int vao = GL30.glGenVertexArrays();
		vaos.add(vao);
		return vao;
	}

	/**
	 * Binds a vertex array object.
	 * VAOs need to be bound before they can be used to store or read data.
	 * VAOs also need to be unbound when they are no longer being used by calling {@link OpenGL#unbindVAO()}.
	 * 
	 * @param vao Id of the VAO to bind
	 */
	public static void bindVAO(int vao) {
		GL30.glBindVertexArray(vao);
	}

	/**
	 * Unbinds the currently bound vertex array object.
	 * VAOs need to be unbound when they are no longer being used.
	 */
	public static void unbindVAO() {
		GL30.glBindVertexArray(0);
	}

	/**
	 * Used in {@link VertexObject} to create a vertex buffer object.
	 * <p>
	 * 	All VBOs that are created are immediately stored in a list so that
	 * 	they can be deleted later by calling {@link OpenGL#cleanUp()}.
	 * </p>
	 * 
	 * @return The vbo id of the newly created vertex buffer object.
	 */
	public static int createVBO() {
		int vbo = GL15.glGenBuffers();
		vbos.add(vbo);
		return vbo;
	}

	/**
	 * Binds a vertex buffer object.
	 * VBOs need to be bound before they can be used to store or read data.
	 * VBOs also need to be unbound when they are no longer being used by calling {@link OpenGL#unbindVBO()}.
	 * 
	 * @param vao Id of the VAO to bind
	 */
	public static void bindVBO(int vbo) {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
	}

	/**
	 * Stores data in a VBO.
	 * 
	 * @param data The data to store.
	 * @param index Index of the attribute list to use.
	 * @param size 2 for 2D coordinates, 3 for 3D.
	 */
	public static void storeDataInVBO(float[] data, int index, int size) {
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(data.length).put(data).flip(), GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(index, size, GL11.GL_FLOAT, false, 0, 0);
	}

	/**
	 * Unbinds the currently bound vertex buffer object.
	 * VBOs need to be unbound when they are no longer being used.
	 */
	public static void unbindVBO() {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}

	/**
	 * Enables an attribute list so that it can be used for rendering.
	 * Attribute lists need to be disabled by calling {@link OpenGL#disableAttributeList(int)}.
	 * 
	 * @param list Index of the attribute list to enable.
	 */
	public static void enableAttributeList(int list) {
		GL20.glEnableVertexAttribArray(list);
	}

	/**
	 * Disables an attribute list after it has been used for rendering.
	 * 
	 * @param list Index of the attribute list to disable.
	 */
	public static void disableAttributeList(int list) {
		GL20.glDisableVertexAttribArray(list);
	}

	/**
	 * A draw call that can be used to render models.
	 * Calls {@code glDrawArrays} with the {@code GL_TRIANGLES} mode.
	 * 
	 * @param vertices The number of vertices to render.
	 */
	public static void drawTriangles(int vertices) {
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertices);
	}

	public static int createVertexShader() {
		int id = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
		shaders.add(id);
		return id;
	}

	public static int createFragmentShader() {
		int id = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
		shaders.add(id);
		return id;
	}

	public static boolean compileShader(String source, int id) {
		GL20.glShaderSource(id, source);
		GL20.glCompileShader(id);
		return GL20.glGetShaderi(id, GL20.GL_COMPILE_STATUS) == GL11.GL_TRUE;
	}

	public static String shaderCompilationInfo(int id) {
		return GL20.glGetShaderInfoLog(id, 512);
	}

	public static int createShaderProgram() {
		int id = GL20.glCreateProgram();
		shaderPrograms.put(id, new ArrayList<>());
		return id;
	}

	public static void attachShaderToProgram(int program, int shader) {
		GL20.glAttachShader(program, shader);
		shaderPrograms.get(program).add(shader);
	}

	public static void bindShaderVariableToAttribute(int program, int attribute, String variable) {
		GL20.glBindAttribLocation(program, attribute, variable);
	}

	public static void validateShaderProgram(int id) {
		GL20.glLinkProgram(id);
		GL20.glValidateProgram(id);
	}

	public static void startShader(int id) {
		GL20.glUseProgram(id);
	}

	public static void stopShader() {
		GL20.glUseProgram(0);
	}

	/**
	 * Clean up function.
	 * Deletes all VAOs and VBOs.
	 * Must be called before terminating the engine.
	 */
	public static void cleanUp() {
		vaos.forEach(GL30::glDeleteVertexArrays);
		vbos.forEach(GL15::glDeleteBuffers);
		shaders.forEach(GL20::glDeleteShader);
		shaderPrograms.forEach((program, shaders) -> shaders.forEach(shader -> GL20.glDetachShader(program, shader)));
		shaderPrograms.keySet().forEach(GL20::glDeleteProgram);
	}
}
