package hexagon.engine.lwjgl;

import org.lwjgl.opengl.GL11;

public final class DrawCalls {
	
	public static void drawTriangles(int first, int count) {
		GL11.glDrawArrays(GL11.GL_TRIANGLES, first, count);
	}

	public static void drawElements(int count) {
		GL11.glDrawElements(GL11.GL_TRIANGLES, count, GL11.GL_UNSIGNED_INT, 0);
	}
}
