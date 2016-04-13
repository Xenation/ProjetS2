package fr.iutvalence.info.dut.m2107.shaders;

import org.lwjgl.util.vector.Matrix4f;

import fr.iutvalence.info.dut.m2107.entities.Camera;
import fr.iutvalence.info.dut.m2107.toolbox.Maths;

public class Shader extends ShaderProgram {
	
	/**
	 * The location of the Vertex Shader file
	 */
	private static final String VERTEX_FILE = "src/fr/iutvalence/info/dut/m2107/shaders/vertexShader.txt";
	/**
	 * The location of the Fragment Shader file
	 */
	private static final String FRAGMENT_FILE = "src/fr/iutvalence/info/dut/m2107/shaders/fragmentShader.txt";
	
	/**
	 * The location of the transformation matrix uniform variable in the Shader
	 */
	private int location_transformationMatrix;
	/**
	 * The location of the projection matrix uniform variable in the Shader
	 */
	private int location_projectionMatrix;
	/**
	 * The location of the view matrix uniform variable in the Shader
	 */
	private int location_viewMatrix;
	
	/**
	 * Creates a new ShaderProgram using the vertex and fragment files
	 */
	public Shader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	/**
	 * Loads the specified transformation matrix to the shader
	 * @param matrix the transformation matrix to load
	 */
	public void loadTransformation(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix);
	}
	
	/**
	 * Loads the specified projection matrix to the shader
	 * @param projection the projection matrix to load
	 */
	public void loadProjectionMatrix(Matrix4f projection) {
		super.loadMatrix(location_projectionMatrix, projection);
	}
	
	/**
	 * Loads the specified view matrix to the shader
	 * @param camera the view matrix to load
	 */
	public void loadViewMatrix(Camera camera) {
		super.loadMatrix(location_viewMatrix, Maths.createViewMatrix(camera));
	}
	
	/**
	 * Gets the location of all the uniform variables of the shader
	 */
	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
	}
	
	/**
	 * binds the VAOs attributes to shader variables
	 */
	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureUVs");
	}
	
}