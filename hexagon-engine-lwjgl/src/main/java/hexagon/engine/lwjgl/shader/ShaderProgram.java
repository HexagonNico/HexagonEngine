package hexagon.engine.lwjgl.shader;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import hexagon.engine.lwjgl.OpenGL;
import hexagon.engine.math.matrix.Matrix4;
import hexagon.engine.math.vector.Float2;
import hexagon.engine.math.vector.Float3;
import hexagon.engine.math.vector.Float4;
import hexagon.engine.math.vector.Int2;
import hexagon.engine.math.vector.Int3;
import hexagon.engine.math.vector.Int4;

public final class ShaderProgram {
	
	public final int id;
	private final HashMap<String, Integer> uniformVariables;

	private ShaderProgram(int id) {
		this.id = id;
		this.uniformVariables = new HashMap<>();
	}

	private int getUniformLocation(String variableName) {
		Integer location = this.uniformVariables.get(variableName);
		if(location == null) {
			location = GL20.glGetUniformLocation(this.id, variableName);
			this.uniformVariables.put(variableName, location);
		}
		return location;
	}

	public void load(String variableName, Matrix4 matrix) {
		int location = this.getUniformLocation(variableName);
		// TODO - Move to another class
		FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
		buffer.put(matrix.m00()); buffer.put(matrix.m01()); buffer.put(matrix.m02()); buffer.put(matrix.m03());
		buffer.put(matrix.m10()); buffer.put(matrix.m11()); buffer.put(matrix.m12()); buffer.put(matrix.m13());
		buffer.put(matrix.m20()); buffer.put(matrix.m21()); buffer.put(matrix.m22()); buffer.put(matrix.m23());
		buffer.put(matrix.m30()); buffer.put(matrix.m31()); buffer.put(matrix.m32()); buffer.put(matrix.m33());
		GL20.glUniformMatrix4fv(location, false, buffer.flip());
	}

	public void load(String variableName, Float2 vector) {
		this.load(variableName, vector.x(), vector.y());
	}

	public void load(String variableName, float x, float y) {
		int location = this.getUniformLocation(variableName);
		GL20.glUniform2f(location, x, y);
	}

	public void load(String variableName, Int2 vector) {
		this.load(variableName, vector.a(), vector.b());
	}

	public void load(String variableName, int a, int b) {
		int location = this.getUniformLocation(variableName);
		GL20.glUniform2i(location, a, b);
	}

	public void load(String variableName, Float3 vector) {
		this.load(variableName, vector.x(), vector.y(), vector.z());
	}

	public void load(String variableName, float x, float y, float z) {
		int location = this.getUniformLocation(variableName);
		GL20.glUniform3f(location, x, y, z);
	}

	public void load(String variableName, Int3 vector) {
		this.load(variableName, vector.a(), vector.b(), vector.c());
	}

	public void load(String variableName, int a, int b, int c) {
		int location = this.getUniformLocation(variableName);
		GL20.glUniform3i(location, a, b, c);
	}

	public void load(String variableName, Float4 vector) {
		this.load(variableName, vector.x(), vector.y(), vector.z(), vector.w());
	}

	public void load(String variableName, float x, float y, float z, float w) {
		int location = this.getUniformLocation(variableName);
		GL20.glUniform4f(location, x, y, z, w);
	}

	public void load(String variableName, Int4 vector) {
		this.load(variableName, vector.a(), vector.b(), vector.c(), vector.d());
	}

	public void load(String variableName, int a, int b, int c, int d) {
		int location = this.getUniformLocation(variableName);
		GL20.glUniform4i(location, a, b, c, d);
	}

	public static Builder with() {
		return new Builder();
	}

	public static class Builder {

		private final List<Integer> shaders;
		private final List<Attribute> attributes;

		private Builder() {
			this.shaders = new ArrayList<>();
			this.attributes = new ArrayList<>();
		}

		public Builder shader(Shader shader) {
			this.shaders.add(shader.id);
			return this;
		}

		public Builder attribute(int list, String name) {
			this.attributes.add(new Attribute(list, name));
			return this;
		}

		public ShaderProgram create() {
			int id = OpenGL.createShaderProgram();
			this.shaders.forEach(shader -> OpenGL.attachShaderToProgram(id, shader));
			this.attributes.forEach(attribute -> OpenGL.bindShaderVariableToAttribute(id, attribute.list, attribute.name));
			OpenGL.validateShaderProgram(id);
			return new ShaderProgram(id);
		}

		private static record Attribute(int list, String name) {
		}
	}
}
