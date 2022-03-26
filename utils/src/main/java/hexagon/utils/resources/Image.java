package hexagon.utils.resources;

import java.nio.ByteBuffer;
import java.awt.image.BufferedImage;

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
	
	private final BufferedImage wrappedImage;

	protected Image(BufferedImage wrappedImage) {
		this.wrappedImage = wrappedImage;
	}

	/**
	 * Gets the width of this image.
	 * 
	 * @return The width of this image in pixels
	 */
	public int width() {
		return this.wrappedImage.getWidth();
	}

	/**
	 * Gets the height of this image.
	 * 
	 * @return The height of this image in pixels
	 */
	public int height() {
		return this.wrappedImage.getHeight();
	}

	public ByteBuffer toBuffer(ByteBuffer buffer) {
		return this.toBuffer(buffer, 0, 0, this.width(), this.height());
	}

	public ByteBuffer toBuffer(ByteBuffer buffer, int x, int y, int w, int h) {
		int[] argb = this.wrappedImage.getRGB(x, y, w, h, null, 0, w);
		for(int i = 0; i < argb.length; i++) {
			buffer.put((byte) ((argb[i] >> 16) & 0xff));
			buffer.put((byte) ((argb[i] >> 8) & 0xff));
			buffer.put((byte) ((argb[i]) & 0xff));
			buffer.put((byte) ((argb[i] >> 24) & 0xff));
		}
		return buffer;
	}
}
