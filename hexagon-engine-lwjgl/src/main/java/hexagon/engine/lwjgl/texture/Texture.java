package hexagon.engine.lwjgl.texture;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;

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

	private static Texture loadTexture(String file) {
		try {
			// TODO - PNG Decoder
			PNGDecoder decoder = new PNGDecoder(Texture.class.getResourceAsStream(file));
			ByteBuffer buffer = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
			decoder.decode(buffer, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
			buffer.flip();
			int id = OpenGL.createTexture();
			OpenGL.decodeTexture(id, decoder.getWidth(), decoder.getHeight(), buffer);
			return new Texture(id);
		} catch (IOException e) {
			// TODO - Error texture
			throw new RuntimeException("Could not load texture " + file);
		}
	}
}
