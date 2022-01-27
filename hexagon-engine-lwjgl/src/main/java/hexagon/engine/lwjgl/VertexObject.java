package hexagon.engine.lwjgl;

import java.util.HashMap;
import java.util.Set;

/**
 * A class to represent a Vertex Array Object, or VAO.
 * Can be used for rendering.
 * 
 * @author Nico
 */
public final class VertexObject {
	
	/**VAO id */
	private final int vao;
	/**Keeps track of all the attribute lists */
	private final Set<Integer> attributes;

	/**
	 * Creates a VertexObject.
	 * 
	 * @param vao VAO id
	 * @param attributes Set of attribute lists
	 */
	private VertexObject(int vao, Set<Integer> attributes) {
		this.vao = vao;
		this.attributes = attributes;
	}

	/**
	 * <p>
	 * 	Draw call.
	 * </p>
	 * Uses the data in this VAO to draw a model using {@code glDrawArrays} and {@code GL_TRIANGLES}.
	 * 
	 * @param vertices Number of vertices to draw.
	 */
	public void drawTriangles(int vertices) {
		OpenGL.bindVAO(this.vao);
		this.attributes.forEach(OpenGL::enableAttributeList);
		OpenGL.drawTriangles(vertices);
		this.attributes.forEach(OpenGL::disableAttributeList);
		OpenGL.unbindVAO();
	}

	/**
	 * Creates a Vertex Object Builder.
	 * Uses the builder pattern to create a Vertex Object.
	 * 
	 * @return {@code new VertexObject.Builder()}
	 */
	public static Builder with() {
		return new Builder();
	}

	/**
	 * Builder class for builder pattern.
	 */
	public static final class Builder {

		/**Keeps track of all the attributes */
		private final HashMap<Integer, Attribute> attributes;

		/**
		 * Creates builder.
		 */
		private Builder() {
			this.attributes = new HashMap<>();
		}

		/**
		 * Adds an attribute to the Vertex Object.
		 * 
		 * @param list Index of the attribute list.
		 * @param data Data to store.
		 * @param size 2 for 2D coords, 3 for 3D coords.
		 * @return {@code this}
		 */
		public Builder attribute(int list, float[] data, int size) {
			this.attributes.put(list, new FloatAttribArray(list, data, size));
			return this;
		}

		/**
		 * Creates the Vertex Object.
		 * 
		 * @return The Vertex Object created with the parameters passed to the builder.
		 */
		public VertexObject create() {
			int vao = OpenGL.createVAO();
			OpenGL.bindVAO(vao);
			this.attributes.values().forEach(Attribute::storeData);
			OpenGL.unbindVAO();
			return new VertexObject(vao, this.attributes.keySet());
		}
	}

	/**
	 * Interface to represent an attribute.
	 */
	private static interface Attribute {

		void storeData();
	}

	/**
	 * Class to represent a float attribute array.
	 */
	private static class FloatAttribArray implements Attribute {

		private final int list;
		private final float[] data;
		private final int size;

		/**
		 * Creates float attrib array.
		 * 
		 * @param list Index of the attribute list.
		 * @param data Data to store.
		 * @param size 2 for 2D coords, 3 for 3D coords.
		 */
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
