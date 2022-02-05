package hexagon.engine.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import de.matthiasmann.twl.utils.PNGDecoder;
import hexagon.engine.lwjgl.Log;

/**
 * Class that represents an OpenGL Texture object.
 * Also used as a utility class to load and store references to loaded textures.
 * 
 * @author Nico
 */
public final class Texture {
	
	/**Map that stores all loaded textures */
	private static final HashMap<String, Texture> textures = new HashMap<>();
	/**Black and white checkerboard texture */
	public static final Texture ERROR = errorTexture();

	/**
	 * Gets or loads a texture.
	 * <p>
	 * 	If the texture is not yet loaded, loads it and returns the texture object.
	 * 	If it is already loaded, returns the same instance.
	 * </p>
	 * 
	 * @param textureFile Path to the texture file, from the resources folder starting with {@code /}.
	 * 
	 * @return The requested texture object.
	 */
	public static Texture getOrLoad(String textureFile) {
		Texture texture = textures.get(textureFile);
		return texture != null ? texture : loadTexture(textureFile);
	}

	/**Texture id */
	public final int id;

	/**
	 * Creates a texture.
	 * 
	 * @param id Texture id.
	 */
	private Texture(int id) {
		this.id = id;
	}

	/**
	 * Binds this texture using.
	 * Textures need to be bound before they can be used.
	 */
	public void bind() {
		// TODO - Texture unit
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
	}

	/**
	 * Loads a texture.
	 * 
	 * @param file Path to texture file.
	 * 
	 * @return The new texture.
	 */
	private static Texture loadTexture(String file) {
		try {
			// TODO - New PNG Decoder
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
			Texture texture = new Texture(id);
			textures.put(file, texture);
			return texture;
		} catch (Exception e) {
			Log.error("Could not load texture " + file);
			return ERROR;
		}
	}

	/**
	 * Creates the error texture.
	 * 
	 * @return Error texture object.
	 */
	private static Texture errorTexture() {
		int id = OpenGL.createTexture();
		float[] pixels = {0.0f,0.0f,0.0f, 1.0f,1.0f,1.0f, 1.0f,1.0f,1.0f, 0.0f,0.0f,0.0f};
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
		GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, 2, 2, 0, GL11.GL_RGB, GL11.GL_FLOAT, pixels);
		return new Texture(id);
	}
}
