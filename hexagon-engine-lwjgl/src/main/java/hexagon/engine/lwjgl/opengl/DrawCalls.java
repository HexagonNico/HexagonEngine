package hexagon.engine.lwjgl.opengl;

import org.lwjgl.opengl.GL11;

/**
 * Utility class to wrap OpenGL draw calls.
 * 
 * @author Nico
 */
public final class DrawCalls {
	
	/**
	 * Draw call. Use this function to render models.
	 * <p>
	 * 	Calls {@link GL11#glDrawArrays(int, int, int)} using {@link GL11#GL_TRIANGLES} mode.
	 * 	Uses data from the currently bound {@link VertexObject}.
	 * </p>
	 * 
	 * @param first First vertex to render (0 for first vertex).
	 * @param count Number of vertices to render.
	 */
	public static void drawTriangles(int first, int count) {
		GL11.glDrawArrays(GL11.GL_TRIANGLES, first, count);
	}

	/**
	 * Draw call. Use this function to render models.
	 * <p>
	 * 	Calls {@link GL11#glDrawElements(int, int, int, long)} using {@link GL11#GL_TRIANGLES} mode.
	 * 	Uses data from the currently bound {@link VertexObject}.
	 * </p>
	 * 
	 * @param count Number of vertices to render.
	 */
	public static void drawElements(int count) {
		GL11.glDrawElements(GL11.GL_TRIANGLES, count, GL11.GL_UNSIGNED_INT, 0);
	}
}
