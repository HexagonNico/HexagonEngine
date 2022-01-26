package hexagon.engine.lwjgl;

import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public final class OpenGL {
	
	private static final ArrayList<Integer> vaos = new ArrayList<>();
	private static final ArrayList<Integer> vbos = new ArrayList<>();

	public static void clearFrame(float red, float green, float blue) {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(red, green, blue, 1.0f);
	}

	public static int createVAO() {
		int vao = GL30.glGenVertexArrays();
		vaos.add(vao);
		return vao;
	}

	public static void bindVAO(int vao) {
		GL30.glBindVertexArray(vao);
	}

	public static void unbindVAO() {
		GL30.glBindVertexArray(0);
	}

	public static int createVBO() {
		int vbo = GL15.glGenBuffers();
		vbos.add(vbo);
		return vbo;
	}

	public static void bindVBO(int vbo) {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
	}

	public static void storeDataInVBO(float[] data, int index, int size) {
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(data.length).put(data).flip(), GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(index, size, GL11.GL_FLOAT, false, 0, 0);
	}

	public static void unbindVBO() {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}

	public static void enableAttributeList(int list) {
		GL20.glEnableVertexAttribArray(list);
	}

	public static void disableAttributeList(int list) {
		GL20.glDisableVertexAttribArray(list);
	}

	public static void drawTriangles(int vertices) {
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertices);
	}

	public static void cleanUp() {
		vaos.forEach(GL30::glDeleteVertexArrays);
		vbos.forEach(GL15::glDeleteBuffers);
	}
}
