package fr.iutvalence.info.dut.m2107.guiRendering;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.shaders.ShaderProgram;

/**
 * Defines a GUI Shader
 * @author Xenation
 *
 */
public class GUIShader extends ShaderProgram {
	
	/**
	 * The path to the vertex shader file
	 */
	private static final String VERTEX_FILE = "src/fr/iutvalence/info/dut/m2107/guiRendering/guiVertex.txt";
	/**
	 * The path to the fragment shader file
	 */
	private static final String FRAGMENT_FILE = "src/fr/iutvalence/info/dut/m2107/guiRendering/guiFragment.txt";
	
	/**
	 * The location of the translation uniform variable
	 */
	private int location_translation;
	
	/**
	 * A GUIShader that uses VERTEX_FILE and FRAGMENT_FILE
	 */
	public GUIShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	/**
	 * Gets the location of all the uniform variables 
	 */
	@Override
	protected void getAllUniformLocations() {
		location_translation = super.getUniformLocation("translation");
	}
	
	/**
	 * Binds the attributes of the VAO to the shader
	 */
	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureUVs");
	}
	
	/**
	 * Loads the given translation to shader
	 * @param translation the translation to load
	 */
	protected void loadTranslation(Vector2f translation) {
		super.load2DVector(location_translation, translation);
	}
	
}
