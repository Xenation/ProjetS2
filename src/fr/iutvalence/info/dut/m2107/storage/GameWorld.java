package fr.iutvalence.info.dut.m2107.storage;

import fr.iutvalence.info.dut.m2107.entities.Camera;

public class GameWorld {
	
	private final Camera camera;
	
	private final ChunkMap chunkMap;
	
	public GameWorld(ChunkMap chkMap) {
		this.camera = new Camera();
		this.chunkMap = chkMap;
	}
	
	public void update() {
		camera.update(this);
	}

	public ChunkMap getChunkMap() {
		return chunkMap;
	}
	
	public Camera getCamera() {
		return camera;
	}
	
}
