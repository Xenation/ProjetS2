package fr.iutvalence.info.dut.m2107.gui;

import org.lwjgl.util.vector.Matrix4f;

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
	
	
	private int location_transformation;
	
	
	public GUIShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	@Override
	protected void getAllUniformLocations() {
		location_transformation = super.getUniformLocation("transformation");
	}
	
	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureUVs");
	}
	
	public void loadTransformation(Matrix4f matrix) {
		super.loadMatrix(location_transformation, matrix);
	}
	
}
