package hexagon.engine.opengl;

import java.nio.ByteBuffer;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;

import hexagon.engine.resources.Image;
import hexagon.engine.resources.ResourceLoadingException;
import hexagon.engine.resources.Resources;
import hexagon.engine.utils.Log;
import hexagon.engine.utils.json.JsonArray;
import hexagon.engine.utils.json.JsonObject;

/**
 * Class that represents an OpenGL Array Texture object.
 * <p>
 * 	An array texture is a texture that contains an array of images of the same size.
 * </p>
 * Also used as a utility class to store and get references to loaded texture arrays.
 * 
 * @author Nico
 */
public final class TextureArray {

	/**Map that stores all loaded array textures */
	private static final HashMap<String, TextureArray> textures = new HashMap<>();
	/**Black and white checkerboard texture */
	public static final TextureArray ERROR = errorTextureArray();

	/**
	 * Gets or loads an array texture.
	 * <p>
	 * 	If the texture is not yet loaded, loads it and returns the texture object.
	 * 	If it is already loaded, returns the same instance.
	 * </p>
	 * 
	 * @param textureFile Path to the array texture file, from the resources folder starting with {@code /}.
	 * 
	 * @return The requested array texture object.
	 */
	public static TextureArray getOrLoad(String textureFile) {
		TextureArray texture = textures.get(textureFile);
		return texture != null ? texture : loadTextureArray(textureFile);
	}

	/**Texture id */
	public final int id;

	/**
	 * Creates a texture array.
	 * 
	 * @param id Texture id.
	 */
	private TextureArray(int id) {
		this.id = id;
	}
	
	/**
	 * Binds this texture.
	 * Textures need to be bound before they can be used.
	 */
	public void bind() {
		// TODO - Texture unit
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL30.GL_TEXTURE_2D_ARRAY, this.id);
	}

	/**
	 * Loads an array texture.
	 * 
	 * @param file Path to array texture file.
	 * 
	 * @return The new array texture.
	 */
	private static TextureArray loadTextureArray(String file) {
		try {
			// Load json file
			JsonObject textureJson = JsonObject.fromFile(file);
			JsonObject sizeJson = textureJson.getObjectOrEmpty("size");
			JsonArray layersArray = textureJson.getArrayOrEmpty("layers");
			
			// All layers have the same size
			int width = sizeJson.getInt("width", 16);
			int height = sizeJson.getInt("height", 16);
			int layers = layersArray.length();

			// Allocate buffer
			ByteBuffer buffer = ByteBuffer.allocateDirect(4 * width * height * layers);
			HashMap<String, Image> loadedImages = new HashMap<>();

			// Load layers
			layersArray.forEachObject(layerJson -> {
				layerJson.getString("image").ifPresent(imagePath -> {
					Image image = loadedImages.get(imagePath);
					if(image == null) {
						try {
							image = Resources.loadImage(imagePath);
							loadedImages.put(imagePath, image);
						} catch (ResourceLoadingException e) {
							// TODO - Error layer
						}
					}
					int x = layerJson.getInt("x", 0);
					int y = layerJson.getInt("y", 0);
					image.toBuffer(buffer, x, y, width, height);
				});
			});
			buffer.flip();

			// Create texture
			int id = OpenGL.createTexture();
			GL11.glBindTexture(GL30.GL_TEXTURE_2D_ARRAY, id);
			// glTexStorage3D(GL_TEXTURE_2D_ARRAY, mipLevelCount, GL_RGBA8, width, height, layerCount); ?
			GL12.glTexImage3D(GL30.GL_TEXTURE_2D_ARRAY, 0, GL11.GL_RGBA, width, height, layers, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
			GL11.glTexParameteri(GL30.GL_TEXTURE_2D_ARRAY, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST); // TODO - Texture parameters
			GL11.glTexParameteri(GL30.GL_TEXTURE_2D_ARRAY, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL30.GL_TEXTURE_2D_ARRAY, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
			GL11.glTexParameteri(GL30.GL_TEXTURE_2D_ARRAY, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
			return new TextureArray(id);
		} catch (ResourceLoadingException e) {
			Log.error("Error loading texture array " + file);
			return ERROR;
		}
	}

	/**
	 * Creates the error texture.
	 * 
	 * @return Error texture object.
	 */
	private static TextureArray errorTextureArray() {
		int id = OpenGL.createTexture();
		float[] pixels = {0.0f,0.0f,0.0f, 1.0f,1.0f,1.0f, 1.0f,1.0f,1.0f, 0.0f,0.0f,0.0f, 1.0f,1.0f,1.0f, 0.0f,0.0f,0.0f, 0.0f,0.0f,0.0f, 1.0f,1.0f,1.0f};
		GL11.glBindTexture(GL30.GL_TEXTURE_2D_ARRAY, id);
		// glTexStorage3D(GL_TEXTURE_2D_ARRAY, mipLevelCount, GL_RGBA8, width, height, layerCount); ?
		GL12.glTexImage3D(GL30.GL_TEXTURE_2D_ARRAY, 0, GL11.GL_RGBA, 2, 2, 2, 0, GL11.GL_RGBA, GL11.GL_FLOAT, pixels);
		GL11.glTexParameteri(GL30.GL_TEXTURE_2D_ARRAY, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL30.GL_TEXTURE_2D_ARRAY, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL30.GL_TEXTURE_2D_ARRAY, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL30.GL_TEXTURE_2D_ARRAY, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
		return new TextureArray(id);
	}
}
