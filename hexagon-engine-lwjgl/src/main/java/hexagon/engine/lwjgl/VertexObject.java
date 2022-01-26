package hexagon.engine.lwjgl;

import java.util.HashMap;
import java.util.Set;

public final class VertexObject {
	
	private final int vao;
	private final Set<Integer> attributes;

	private VertexObject(int vao, Set<Integer> attributes) {
		this.vao = vao;
		this.attributes = attributes;
	}

	public void drawTriangles(int vertices) {
		OpenGL.bindVAO(this.vao);
		this.attributes.forEach(OpenGL::enableAttributeList);
		OpenGL.drawTriangles(vertices);
		this.attributes.forEach(OpenGL::disableAttributeList);
		OpenGL.unbindVAO();
	}

	public static Builder with() {
		return new Builder();
	}

	public static final class Builder {

		private final HashMap<Integer, Attribute> attributes;

		private Builder() {
			this.attributes = new HashMap<>();
		}

		public Builder attribute(int list, float[] data, int size) {
			this.attributes.put(list, new FloatAttribArray(list, data, size));
			return this;
		}

		public VertexObject create() {
			int vao = OpenGL.createVAO();
			OpenGL.bindVAO(vao);
			this.attributes.values().forEach(Attribute::storeData);
			OpenGL.unbindVAO();
			return new VertexObject(vao, this.attributes.keySet());
		}
	}

	private static interface Attribute {

		void storeData();
	}

	private static class FloatAttribArray implements Attribute {

		private final int list;
		private final float[] data;
		private final int size;

		private FloatAttribArray(int list, float[] data, int size) {
			this.list = list;
			this.data = data;
			this.size = size;
		}

		@Override
		public void storeData() {
			int vbo = OpenGL.createVBO();
			OpenGL.bindVBO(vbo);
			OpenGL.storeDataInVBO(data, list, size);
			OpenGL.unbindVBO();
		}
		
	}
}
