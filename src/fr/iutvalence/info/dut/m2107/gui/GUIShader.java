package fr.iutvalence.info.dut.m2107.gui;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.shaders.ShaderProgram;

public class GUIShader extends ShaderProgram {
	

	/**
	 * The location of the Vertex Shader file
	 */
	private static final String VERTEX_FILE = "src/fr/iutvalence/info/dut/m2107/gui/guiVertex.txt";
	/**
	 * The location of the Fragment Shader file
	 */
	private static final String FRAGMENT_FILE = "src/fr/iutvalence/info/dut/m2107/gui/guiFragment.txt";
	

	/**
	 * The location of the translation uniform variable
	 */
	private int location_translation;
	
	private int location_scale;
	
	
	public GUIShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	@Override
	protected void getAllUniformLocations() {
		location_translation = super.getUniformLocation("translation");
		location_scale = super.getUniformLocation("scale");
	}
	
	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureUVs");
	}
	
	/**
	 * Loads the given translation to shader
	 * @param translation the translation to load
	 */
	public void loadTranslation(Vector2f translation) {
		super.load2DVector(location_translation, translation);
	}
	
	public void loadScale(Vector2f scale) {
		super.load2DVector(location_scale, scale);
	}
	
}
