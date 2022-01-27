package hexagon.engine.lwjgl.texture;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import de.matthiasmann.twl.utils.PNGDecoder;
import hexagon.engine.lwjgl.OpenGL;

public final class Texture {
	
	private static final HashMap<String, Texture> textures = new HashMap<>();

	public static Texture getOrLoad(String textureFile) {
		Texture texture = textures.get(textureFile);
		return texture != null ? texture : loadTexture(textureFile);
	}

	public final int id;

	public Texture(int id) {
		this.id = id;
	}

	public void bind() {
		// TODO - Texture unit
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
	}

	private static Texture loadTexture(String file) {
		try {
			// TODO - PNG Decoder
			PNGDecoder decoder = new PNGDecoder(Texture.class.getResourceAsStream(file));
			ByteBuffer buffer = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
			decoder.decode(buffer, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
			buffer.flip();
			int id = OpenGL.createTexture();
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
			GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
			return new Texture(id);
		} catch (IOException e) {
			// TODO - Error texture
			throw new RuntimeException("Could not load texture " + file);
		}
	}
}
