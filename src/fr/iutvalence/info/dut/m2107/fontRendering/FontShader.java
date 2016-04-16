package fr.iutvalence.info.dut.m2107.fontRendering;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import fr.iutvalence.info.dut.m2107.shaders.ShaderProgram;

/**
 * Defines a FontShader
 * @author Xenation
 *
 */
public class FontShader extends ShaderProgram{
	
	/**
	 * The path to the vertex shader file
	 */
	private static final String VERTEX_FILE = "src/fr/iutvalence/info/dut/m2107/fontRendering/fontVertex.txt";
	/**
	 * The path to the fragment shader file
	 */
	private static final String FRAGMENT_FILE = "src/fr/iutvalence/info/dut/m2107/fontRendering/fontFragment.txt";
	
	/**
	 * The location of the colour uniform variable
	 */
	private int location_colour;
	/**
	 * The location of the translation uniform variable
	 */
	private int location_translation;
	
	/**
	 * A FontShader that uses VERTEX_FILE and FRAGMENT_FILE
	 */
	public FontShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	/**
	 * Gets the location of all the uniform variables 
	 */
	@Override
	protected void getAllUniformLocations() {
		location_colour = super.getUniformLocation("colour");
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
	 * Loads the given colour to shader
	 * @param colour the colour to load
	 */
	protected void loadColour(Vector3f colour) {
		super.loadVector(location_colour, colour);
	}
	
	/**
	 * Loads the given translation to shader
	 * @param translation the translation to load
	 */
	protected void loadTranslation(Vector2f translation) {
		super.load2DVector(location_translation, translation);
	}

}
