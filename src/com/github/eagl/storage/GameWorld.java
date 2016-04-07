package com.github.eagl.storage;

public class GameWorld {
	
	private final ChunkMap chunkMap;
	
	public GameWorld(ChunkMap chkMap) {
		this.chunkMap = chkMap;
	}

	public ChunkMap getChunkMap() {
		return chunkMap;
	}
	
}
