package fr.iutvalence.info.dut.m2107.gui;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.models.AbstractSprite;
import fr.iutvalence.info.dut.m2107.models.Atlas;
import fr.iutvalence.info.dut.m2107.render.Loader;

public class GUISprite extends AbstractSprite {
	
	private int vertexCount;
	
	/**
	 * To be used by TextSprite
	 * @param atlas
	 * @param atlasIndex
	 * @param size
	 * @param loader
	 * @param vertexCount
	 */
	protected GUISprite(Atlas atlas, int atlasIndex, Vector2f size, Loader loader, int vertexCount) {
		super(atlas, atlasIndex, size, loader);
		this.vertexCount = vertexCount;
	}
	
	public GUISprite(Atlas atlas, int atlasIndex, Vector2f size) {
		super(atlas, atlasIndex, size, Loader.GUI_LOADER);
		this.vertexCount = 4;
	}
	
	public GUISprite(String fileName, Vector2f size) {
		this(new Atlas(fileName, 1, 1, Loader.GUI_LOADER), 0, size);
		this.vertexCount = 4;
	}
	
	public GUISprite(Atlas atlas, Vector2f size) {
		this(atlas, 0, size);
		this.vertexCount = 4;
	}

	public int getVertexCount() {
		return vertexCount;
	}

	public void setVertexCount(int vertexCount) {
		this.vertexCount = vertexCount;
	}
	
}
