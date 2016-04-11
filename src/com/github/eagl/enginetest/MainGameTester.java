package com.github.eagl.enginetest;

import org.lwjgl.opengl.Display;

import com.github.eagl.render.*;
import com.github.eagl.storage.Chunk;
import com.github.eagl.storage.ChunkMap;
import com.github.eagl.storage.GameWorld;
import com.github.eagl.storage.Vector2i;
import com.github.eagl.tiles.Tile;
import com.github.eagl.tiles.TileType;

public class MainGameTester {
	
	public static void main(String[] args) {
		// Display Window initialization
		DisplayManager.createDisplay();
		
		Renderer renderer = new Renderer();
		
		GameWorld gameWorld = new GameWorld(new ChunkMap());
		Chunk chk = new Chunk(new Vector2i(0, 0));
		gameWorld.getChunkMap().put(chk.getPosition(), chk);
		gameWorld.getChunkMap().addTile(new Tile(TileType.Dirt, 0, 0));
		gameWorld.getChunkMap().addTile(new Tile(TileType.Dirt, 2, 0));
		gameWorld.getChunkMap().addTile(new Tile(TileType.Dirt, 4, 0));
		gameWorld.getChunkMap().addTile(new Tile(TileType.Dirt, 6, 0));
		Chunk chk2 = new Chunk(new Vector2i(-1, 0));
		gameWorld.getChunkMap().put(chk2.getPosition(), chk2);
		gameWorld.getChunkMap().addTile(new Tile(TileType.Stone, -1, 0));
		gameWorld.getChunkMap().addTile(new Tile(TileType.Stone, -3, 0));
		gameWorld.getChunkMap().addTile(new Tile(TileType.Stone, -5, 0));
		gameWorld.getChunkMap().addTile(new Tile(TileType.Stone, -7, 0));
		
		// Debug for the whole chunk rendering 
		//System.out.println(ChunkLoader.CHUNK_LOADER.debugBuffers());
		//System.out.println(ChunkLoader.CHUNK_LOADER.debugGLBuffers());
		
		// Game Loop
		while (!Display.isCloseRequested()) {
			gameWorld.update();
			
			renderer.prepare();
			renderer.render(gameWorld);
			
			DisplayManager.updateDisplay();
		}
		
		renderer.cleanUp();
		Loader.TILE_LOADER.unloadAll();
		// Code to clean the whole chunk loader
		//ChunkLoader.CHUNK_LOADER.unloadAll();
		DisplayManager.closeDisplay();
		
	}
	
}
