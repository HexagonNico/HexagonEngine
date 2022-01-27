package hexagon.engine.lwjgl.shader;

import java.util.ArrayList;
import java.util.List;

import hexagon.engine.lwjgl.OpenGL;

public final class ShaderProgram {
	
	public final int id;

	// TODO - Uniform variables

	private ShaderProgram(int id) {
		this.id = id;
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
