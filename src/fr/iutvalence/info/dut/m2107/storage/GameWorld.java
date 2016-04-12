package fr.iutvalence.info.dut.m2107.storage;

import java.awt.Font;

import org.newdawn.slick.TrueTypeFont;

import fr.iutvalence.info.dut.m2107.entities.Camera;

public class GameWorld {
	
	private final Camera camera;
	private final ChunkMap chunkMap;
	private final LayerMap layerMap;
	
	private TrueTypeFont debugFont;
	
	public GameWorld(ChunkMap chkMap, LayerMap layMap) {
		this.camera = new Camera();
		this.chunkMap = chkMap;
		this.layerMap = layMap;
		Font awtFont = new Font("Times New Roman", Font.BOLD, 16);
		debugFont = new TrueTypeFont(awtFont, true);
	}
	
	public void update() {
		camera.update(this);
		layerMap.update();
	}

	public ChunkMap getChunkMap() {
		return chunkMap;
	}
	
	public LayerMap getLayerMap() {
		return layerMap;
	}
	
	public Camera getCamera() {
		return camera;
	}

	public TrueTypeFont getDebugFont() {
		return debugFont;
	}
	
}
