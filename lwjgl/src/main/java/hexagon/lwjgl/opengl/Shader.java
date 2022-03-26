package hexagon.lwjgl.opengl;

import java.util.HashMap;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import hexagon.utils.Log;
import hexagon.utils.resources.ResourceLoadingException;
import hexagon.utils.resources.Resources;

/**
 * Class to represent an OpenGL shader.
 * Also used as a utility class to load and store references to shaders.
 * 
 * @author Nico
 */
public final class Shader {
	
	/**Map that stores all vertex shaders */
	private static final HashMap<String, Shader> vertexShaders = new HashMap<>();
	/**Map that stores all fragment shaders */
	private static final HashMap<String, Shader> fragmentShaders = new HashMap<>();

	/**
	 * Gets or loads a vertex shader.
	 * <p>
	 * 	If the shader is not yet loaded, loads it and returns the shader object.
	 * 	If it is already loaded, returns the same instance.
	 * </p>
	 * 
	 * @param file Path to the shader file, from the resources folder starting with {@code /}.
	 * 
	 * @return The requested shader
	 */
	public static Shader vertex(String file) {
		Shader shader = vertexShaders.get(file);
		if(shader == null) {
			int id = OpenGL.createVertexShader();
			return loadShader(file, id, vertexShaders);
		} else {
			return shader;
		}
	}

	/**
	 * Gets or loads a fragment shader.
	 * <p>
	 * 	If the shader is not yet loaded, loads it and returns the shader object.
	 * 	If it is already loaded, returns the same instance.
	 * </p>
	 * 
	 * @param file Path to the shader file, from the resources folder starting with {@code /}.
	 * 
	 * @return The requested shader
	 */
	public static Shader fragment(String file) {
		Shader shader = fragmentShaders.get(file);
		if(shader == null) {
			int id = OpenGL.createFragmentShader();
			return loadShader(file, id, fragmentShaders);
		} else {
			return shader;
		}
	}

	/**Shader id */
	public final int id;

	/**
	 * Creates a shader.
	 * 
	 * @param id Shader id.
	 */
	private Shader(int id) {
		this.id = id;
	}

	/**
	 * Loads a shader.
	 * 
	 * @param filePath Path to the shader file.
	 * @param id Shader id.
	 * @param map Map where to store the shader.
	 * 
	 * @return Instance of the shader.
	 */
	private static Shader loadShader(String filePath, int id, HashMap<String, Shader> map) {
		try {
			String shaderCode = Resources.readString(filePath);
			GL20.glShaderSource(id, shaderCode);
			GL20.glCompileShader(id);
			if(GL20.glGetShaderi(id, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
				Log.error("Could not compile shader " + filePath);
				Log.info("Shader compilation info: \n" + GL20.glGetShaderInfoLog(id, 1024));
				throw new RuntimeException("Could not compile shader " + filePath);
			} else {
				Shader shader = new Shader(id);
				map.put(filePath, shader);
				return shader;
			}
		} catch (ResourceLoadingException e) {
			// TODO - Use default shader in case of error
			Log.error("Error loading shader " + filePath);
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}
