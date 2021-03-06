package hexagon.lwjgl.opengl;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import hexagon.math.matrix.Matrix4;
import hexagon.math.vector.Float2;
import hexagon.math.vector.Float3;
import hexagon.math.vector.Float4;
import hexagon.math.vector.Int2;
import hexagon.math.vector.Int3;
import hexagon.math.vector.Int4;
import hexagon.utils.json.JsonObject;
import hexagon.utils.resources.ResourceLoadingException;

/**
 * Class that represents a shader program.
 * Shader programs are created from multiple shader objects and are used for rendering.
 * 
 * @author Nico
 */
public final class ShaderProgram {

	private static final HashMap<String, ShaderProgram> programs = new HashMap<>();

	public static ShaderProgram getOrLoad(String programFile) {
		ShaderProgram program = programs.get(programFile);
		return program != null ? program : loadProgram(programFile);
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

	public void start() {
		GL20.glUseProgram(this.id);
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
	 * @param matrix The matrix to load
	 */
	public void load(String variableName, Matrix4 matrix) {
		if(matrix != null) {
			int location = this.getUniformLocation(variableName);
			FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
			buffer.put(matrix.m00()); buffer.put(matrix.m01()); buffer.put(matrix.m02()); buffer.put(matrix.m03());
			buffer.put(matrix.m10()); buffer.put(matrix.m11()); buffer.put(matrix.m12()); buffer.put(matrix.m13());
			buffer.put(matrix.m20()); buffer.put(matrix.m21()); buffer.put(matrix.m22()); buffer.put(matrix.m23());
			buffer.put(matrix.m30()); buffer.put(matrix.m31()); buffer.put(matrix.m32()); buffer.put(matrix.m33());
			GL20.glUniformMatrix4fv(location, false, buffer.flip());
		}
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
	 * Loads a 2D float vector as a uniform variable.
	 * 
	 * @param variableName Name of the variable in the shader code.
	 * @param v The vector to load
	 */
	public void load(String variableName, Float2 v) {
		if(v != null) this.load(variableName, v.x(), v.y());
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
	 * Loads a 2D int vector as a uniform variable.
	 * 
	 * @param variableName Name of the variable in the shader code.
	 * @param v The vector to load
	 */
	public void load(String variableName, Int2 v) {
		if(v != null) this.load(variableName, v.x(), v.y());
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
	 * @param v The vector to load
	 */
	public void load(String variableName, Float3 v) {
		if(v != null) this.load(variableName, v.x(), v.y(), v.z());
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
	 * @param v The vector to load
	 */
	public void load(String variableName, Int3 v) {
		if(v != null) this.load(variableName, v.x(), v.y(), v.z());
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
	 * @param v The vector to load
	 */
	public void load(String variableName, Float4 v) {
		if(v != null) this.load(variableName, v.x(), v.y(), v.z(), v.w());
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
	 * @param v The vector to load
	 */
	public void load(String variableName, Int4 v) {
		if(v != null) this.load(variableName, v.x(), v.y(), v.z(), v.w());
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

	private static ShaderProgram loadProgram(String programFile) {
		try {
			int id = OpenGL.createShaderProgram();
			JsonObject programJson = JsonObject.fromFile(programFile);
			programJson.getArray("vertex").ifPresent(vertexArray -> {
				vertexArray.forEachString(vertexShader -> {
					Shader shader = Shader.vertex(vertexShader);
					OpenGL.attachShaderToProgram(id, shader.id);
				});
			});
			programJson.getArray("fragment").ifPresent(fragmentArray -> {
				fragmentArray.forEachString(fragmentShader -> {
					Shader shader = Shader.fragment(fragmentShader);
					OpenGL.attachShaderToProgram(id, shader.id);
				});
			});
			programJson.getObject("attributes").ifPresent(attributesJson -> {
				attributesJson.keySet().forEach(key -> {
					attributesJson.getString(key).ifPresent(attributeName -> {
						int attributeList = Integer.parseInt(key);
						GL20.glBindAttribLocation(id, attributeList, attributeName);
					});
				});
			});
			GL20.glLinkProgram(id);
			GL20.glValidateProgram(id);
			return new ShaderProgram(id);
		} catch (ResourceLoadingException e) {
			// TODO - Default error shader
			e.printStackTrace();
			return ShaderProgram.with().create();
		}
	}
}
