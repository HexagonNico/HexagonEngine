package hexagon.engine.lwjgl;

import org.lwjgl.opengl.GL11;

public final class OpenGL {
	
	public static void clearFrame(float red, float green, float blue) {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(red, green, blue, 1.0f);
	}
}
