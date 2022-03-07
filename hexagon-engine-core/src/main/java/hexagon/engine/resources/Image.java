package hexagon.engine.resources;

import java.nio.ByteBuffer;

/**
 * A class that represents an image resource. Used to load {@link hexagon.engine.opengl.Texture}s.
 * <p>
 * 	Differently from {@link hexagon.engine.opengl.Texture}, which is an OpenGL object,
 * 	this only contains data about the image, namely its size and its pixels,
 * 	and cannot be used in the rendering system.
 * </p>
 * 
 * @author Nico
 */
public final class Image {
	
	/**Image width */
	private final int width;
	/**Image height */
	private final int height;
	/**Image pixels */
	private final int[] argb;

	/**
	 * Creates an image.
	 * 
	 * @param width Image width
	 * @param height Image height
	 * @param argb Image pixels
	 */
	protected Image(int width, int height, int[] argb) {
		this.width = width;
		this.height = height;
		this.argb = argb;
	}

	/**
	 * Gets the width of this image.
	 * 
	 * @return The width of this image in pixels
	 */
	public int width() {
		return this.width;
	}

	/**
	 * Gets the height of this image.
	 * 
	 * @return The height of this image in pixels
	 */
	public int height() {
		return this.height;
	}

	/**
	 * Adds this image's pixels to the byte buffer.
	 * 
	 * @param buffer Buffer to hold the pixels
	 * 
	 * @return The same buffer
	 */
	public ByteBuffer toBuffer(ByteBuffer buffer) {
		for(int i = 0; i < this.argb.length; i++) {
			buffer.put((byte) ((this.argb[i] >> 16) & 0xff));
			buffer.put((byte) ((this.argb[i] >> 8) & 0xff));
			buffer.put((byte) ((this.argb[i]) & 0xff));
			buffer.put((byte) ((this.argb[i] >> 24) & 0xff));
		}
		return buffer;
	}
}
