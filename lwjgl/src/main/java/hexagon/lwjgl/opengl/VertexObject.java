package hexagon.lwjgl.opengl;

import java.util.HashMap;
import java.util.Set;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * A class to represent a Vertex Array Object, or VAO.
 * Can be used for rendering.
 * 
 * @author Nico
 */
public final class VertexObject {

	private static VertexObject currentlyBound;

	public static void unbind() {
		if(currentlyBound != null) {
			currentlyBound.attributes.forEach(GL20::glDisableVertexAttribArray);
			GL30.glBindVertexArray(0);
			currentlyBound = null;
		}
	}

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

	public void bindIfNotBound() {
		if(this != currentlyBound) {
			GL30.glBindVertexArray(this.vao);
			this.attributes.forEach(GL20::glEnableVertexAttribArray);
			currentlyBound = this;
		}
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
		 * 
		 * @return {@code this} for builder pattern.
		 */
		public Builder attribute(int list, float[] data, int size) {
			this.attributes.put(list, new FloatAttribArray(list, data, size));
			return this;
		}

		/**
		 * Adds an index buffer.
		 * Vertex objects with index buffers can be rendered with {@link DrawCalls#drawElements(int)}.
		 * 
		 * @param indices Array containing the indices.
		 * 
		 * @return {@code this} for builder pattern.
		 */
		public Builder indices(int[] indices) {
			this.attributes.put(-1, new IndicesBuffer(indices));
			return this;
		}

		/**
		 * Creates the Vertex Object.
		 * 
		 * @return The Vertex Object created with the parameters passed to the builder.
		 */
		public VertexObject create() {
			int vao = OpenGL.createVAO();
			GL30.glBindVertexArray(vao);
			this.attributes.values().forEach(Attribute::storeData);
			GL30.glBindVertexArray(0);
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
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(this.data.length).put(this.data).flip(), GL15.GL_STATIC_DRAW);
			GL20.glVertexAttribPointer(this.list, this.size, GL11.GL_FLOAT, false, 0, 0);
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		}
	}

	/**
	 * Class to represent an indices buffer.
	 */
	private static class IndicesBuffer implements Attribute {

		/**Array containing the indices */
		private final int[] indices;

		/**
		 * Creates an index buffer.
		 * 
		 * @param indices Array containing the indices.
		 */
		private IndicesBuffer(int[] indices) {
			this.indices = indices;
		}

		@Override
		public void storeData() {
			int vbo = OpenGL.createVBO();
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vbo);
			GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, BufferUtils.createIntBuffer(indices.length).put(indices).flip(), GL15.GL_STATIC_DRAW);
		}

	}
}
