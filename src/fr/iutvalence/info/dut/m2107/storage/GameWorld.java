package fr.iutvalence.info.dut.m2107.storage;

import fr.iutvalence.info.dut.m2107.entities.Camera;

public class GameWorld {
	
	private final Camera camera;
	
	private final ChunkMap chunkMap;
	private final LayerMap layerMap;
	
	public GameWorld(ChunkMap chkMap, LayerMap layMap) {
		this.camera = new Camera();
		this.chunkMap = chkMap;
		this.layerMap = layMap;
	}
	
	public void update() {
		camera.update(this);
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
	
}
