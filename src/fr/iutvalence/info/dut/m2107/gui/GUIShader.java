package fr.iutvalence.info.dut.m2107.gui;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import fr.iutvalence.info.dut.m2107.shaders.ShaderProgram;

public class GUIShader extends ShaderProgram {
	

	/**
	 * The location of the Vertex Shader file
	 */
	private static final String VERTEX_FILE = "/fr/iutvalence/info/dut/m2107/gui/guiVertex.txt";
	/**
	 * The location of the Fragment Shader file
	 */
	private static final String FRAGMENT_FILE = "/fr/iutvalence/info/dut/m2107/gui/guiFragment.txt";
	
	
	private int location_transformation;
	
	private int location_colourFilter;
	
	
	public GUIShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	@Override
	protected void getAllUniformLocations() {
		location_transformation = super.getUniformLocation("transformation");
		location_colourFilter = super.getUniformLocation("colourFilter");
	}
	
	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureUVs");
	}
	
	public void loadTransformation(Matrix4f matrix) {
		super.loadMatrix(location_transformation, matrix);
	}
	
	public void loadColourFilter(Vector3f color) {
		super.loadVector(location_colourFilter, color);
	}
	
}
