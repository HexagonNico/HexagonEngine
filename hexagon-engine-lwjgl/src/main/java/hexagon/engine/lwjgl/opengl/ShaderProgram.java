package hexagon.engine.lwjgl.opengl;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.lwjgl.opengl.GL20;

/**
 * Class that represents a shader program.
 * Shader programs are created from multiple shader objects and are used for rendering.
 * 
 * @author Nico
 */
public final class ShaderProgram {
	
	/**
	 * Starts a shader program.
	 * Everything rendered after this call will use this shader program.
	 * 
	 * @param program The shader program to use.
	 */
	public static void start(ShaderProgram program) {
		GL20.glUseProgram(program.id);
	}

	/**
	 * Stops the running shader program.
	 * Any running shader program will be stopped after this call.
	 */
	public static void stop() {
		GL20.glUseProgram(0);
	}

	/**Shader program id */
	public final int id;
	/**Map that stores uniform variables */
	private final HashMap<String, Integer> uniformVariables;

	/**
	 * Creates a shader program.
	 * 
	 * @param id Shader id.
	 */
	private ShaderProgram(int id) {
		this.id = id;
		this.uniformVariables = new HashMap<>();
	}

	/**
	 * Gets the location of a uniform variable.
	 * 
	 * @param variableName Name of the variable in the shader code.
	 * 
	 * @return Integer value of uniform variable location.
	 */
	private int getUniformLocation(String variableName) {
		Integer location = this.uniformVariables.get(variableName);
		if(location == null) {
			location = GL20.glGetUniformLocation(this.id, variableName);
			this.uniformVariables.put(variableName, location);
		}
		return location;
	}

	/**
	 * Loads a matrix as a uniform variable.
	 * 
	 * @param variableName Name of the variable in the shader code.
	 * @param matrixBuffer Buffer containing the values in the matrix.
	 */
	public void load(String variableName, FloatBuffer matrixBuffer) {
		int location = this.getUniformLocation(variableName);
		GL20.glUniformMatrix4fv(location, false, matrixBuffer);
	}

	/**
	 * Loads a float uniform variable.
	 * 
	 * @param variableName Name of the variable in the shader code.
	 * @param value The value to load.
	 */
	public void load(String variableName, float value) {
		int location = this.getUniformLocation(variableName);
		GL20.glUniform1f(location, value);
	}

	/**
	 * Loads a 2D float vector as a uniform variable.
	 * 
	 * @param variableName Name of the variable in the shader code.
	 * @param x X coordinate of the vector to load.
	 * @param y Y coordinate of the vector to load.
	 */
	public void load(String variableName, float x, float y) {
		int location = this.getUniformLocation(variableName);
		GL20.glUniform2f(location, x, y);
	}

	/**
	 * Loads an int uniform variable.
	 * 
	 * @param variableName Name of the variable in the shader code.
	 * @param value The value to load.
	 */
	public void load(String variableName, int value) {
		int location = this.getUniformLocation(variableName);
		GL20.glUniform1i(location, value);
	}

	/**
	 * Loads a 2D int vector as a uniform variable.
	 * 
	 * @param variableName Name of the variable in the shader code.
	 * @param a X coordinate of the vector to load.
	 * @param b Y coordinate of the vector to load.
	 */
	public void load(String variableName, int a, int b) {
		int location = this.getUniformLocation(variableName);
		GL20.glUniform2i(location, a, b);
	}

	/**
	 * Loads a 3D float vector as a uniform variable.
	 * 
	 * @param variableName Name of the variable in the shader code.
	 * @param x X coordinate of the vector to load.
	 * @param y Y coordinate of the vector to load.
	 * @param z Z coordinate of the vector to load.
	 */
	public void load(String variableName, float x, float y, float z) {
		int location = this.getUniformLocation(variableName);
		GL20.glUniform3f(location, x, y, z);
	}

	/**
	 * Loads a 3D int vector as a uniform variable.
	 * 
	 * @param variableName Name of the variable in the shader code.
	 * @param a X coordinate of the vector to load.
	 * @param b Y coordinate of the vector to load.
	 * @param c Z coordinate of the vector to load.
	 */
	public void load(String variableName, int a, int b, int c) {
		int location = this.getUniformLocation(variableName);
		GL20.glUniform3i(location, a, b, c);
	}

	/**
	 * Loads a 4D float vector as a uniform variable.
	 * 
	 * @param variableName Name of the variable in the shader code.
	 * @param x X coordinate of the vector to load.
	 * @param y Y coordinate of the vector to load.
	 * @param z Z coordinate of the vector to load.
	 * @param w W coordinate of the vector to load.
	 */
	public void load(String variableName, float x, float y, float z, float w) {
		int location = this.getUniformLocation(variableName);
		GL20.glUniform4f(location, x, y, z, w);
	}

	/**
	 * Loads a 4D int vector as a uniform variable.
	 * 
	 * @param variableName Name of the variable in the shader code.
	 * @param a X coordinate of the vector to load.
	 * @param b Y coordinate of the vector to load.
	 * @param c Z coordinate of the vector to load.
	 * @param d W coordinate of the vector to load.
	 */
	public void load(String variableName, int a, int b, int c, int d) {
		int location = this.getUniformLocation(variableName);
		GL20.glUniform4i(location, a, b, c, d);
	}

	/**
	 * Creates a Shader Program Builder.
	 * Uses the builder pattern to create a Shader Program.
	 * 
	 * @return {@code new ShaderProgram.Builder()}
	 */
	public static Builder with() {
		return new Builder();
	}

	/**
	 * Builder class for builder pattern.
	 */
	public static class Builder {

		/**List of all shaders to attach */
		private final List<Integer> shaders;
		/**List of all attributes to bind */
		private final List<Attribute> attributes;

		/**
		 * Creates builder.
		 */
		private Builder() {
			this.shaders = new ArrayList<>();
			this.attributes = new ArrayList<>();
		}

		/**
		 * Adds a shader to this program.
		 * 
		 * @param shader The shader to add.
		 * 
		 * @return {@code this}
		 */
		public Builder shader(Shader shader) {
			this.shaders.add(shader.id);
			return this;
		}

		/**
		 * Adds a vertex shader to this program.
		 * 
		 * @param name Name of the shader to add.
		 * 
		 * @return {@code this}
		 */
		public Builder vertexShader(String name) {
			return this.shader(Shader.vertex(name));
		}

		/**
		 * Adds a fragment shader to this program.
		 * 
		 * @param name Name of the shader to add.
		 * 
		 * @return {@code this}
		 */
		public Builder fragmentShader(String name) {
			return this.shader(Shader.fragment(name));
		}

		/**
		 * Adds an attribute to bind.
		 * This will bind the {@code in} variables in the shader to an attribute list.
		 * 
		 * @param list Index of the attribute list.
		 * @param name Name of the variable in the shader code
		 * 
		 * @return {@code this}
		 */
		public Builder attribute(int list, String name) {
			this.attributes.add(new Attribute(list, name));
			return this;
		}

		/**
		 * Creates the shader program.
		 * 
		 * @return The shader program created with the parameters passed to the builder.
		 */
		public ShaderProgram create() {
			int id = OpenGL.createShaderProgram();
			this.shaders.forEach(shader -> OpenGL.attachShaderToProgram(id, shader));
			this.attributes.forEach(attribute -> GL20.glBindAttribLocation(id, attribute.list(), attribute.name()));
			GL20.glLinkProgram(id);
			GL20.glValidateProgram(id);
			return new ShaderProgram(id);
		}

		/**
		 * Record used to store attributes in the builder.
		 */
		private static record Attribute(int list, String name) {
		}
	}
}
