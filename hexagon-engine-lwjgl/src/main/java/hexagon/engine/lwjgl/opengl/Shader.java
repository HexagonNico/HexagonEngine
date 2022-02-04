package hexagon.engine.lwjgl.opengl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import hexagon.engine.lwjgl.Log;

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
		try(BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String shaderCode = reader.lines().collect(Collectors.joining("\n"));
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
		} catch(FileNotFoundException e) {
			Log.error("Could not find file " + filePath);
			throw new RuntimeException(e);
		} catch(IOException e) {
			Log.error("Could not read file " + filePath);
			throw new RuntimeException(e);
		}
	}
}
