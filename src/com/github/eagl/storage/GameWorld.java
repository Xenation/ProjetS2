package com.github.eagl.storage;

import com.github.eagl.entities.Camera;

public class GameWorld {
	
	private final Camera camera;
	
	private final ChunkMap chunkMap;
	
	public GameWorld(ChunkMap chkMap) {
		this.camera = new Camera();
		this.chunkMap = chkMap;
	}
	
	public void update() {
		camera.update();
	}

	public ChunkMap getChunkMap() {
		return chunkMap;
	}
	
	public Camera getCamera() {
		return camera;
	}
	
}
