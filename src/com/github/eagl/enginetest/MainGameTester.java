package com.github.eagl.enginetest;

import org.lwjgl.opengl.Display;

import com.github.eagl.models.TileSprite;
import com.github.eagl.render.*;
import com.github.eagl.storage.Chunk;
import com.github.eagl.storage.ChunkMap;
import com.github.eagl.storage.GameWorld;
import com.github.eagl.storage.Vector2i;
import com.github.eagl.tiles.Tile;

public class MainGameTester {
	
	public static void main(String[] args) {
		// Display Window initialization
		DisplayManager.createDisplay();
		
		Renderer renderer = new Renderer();
		
		TileSprite tilSpr = new TileSprite("tile_dirt");
		
		GameWorld gameWorld = new GameWorld(new ChunkMap());
		Chunk chk = new Chunk(new Vector2i(0, 0));
		gameWorld.getChunkMap().put(chk.getPosition(), chk);
		gameWorld.getChunkMap().addTile(new Tile(tilSpr, 0, 1));
		gameWorld.getChunkMap().addTile(new Tile(tilSpr, 2, 1));
		gameWorld.getChunkMap().addTile(new Tile(tilSpr, 4, 1));
		gameWorld.getChunkMap().addTile(new Tile(tilSpr, 6, 1));
		Chunk chk2 = new Chunk(new Vector2i(-1, 0));
		gameWorld.getChunkMap().put(chk2.getPosition(), chk2);
		gameWorld.getChunkMap().addTile(new Tile(tilSpr, -1, 1));
		gameWorld.getChunkMap().addTile(new Tile(tilSpr, -3, 1));
		gameWorld.getChunkMap().addTile(new Tile(tilSpr, -5, 1));
		gameWorld.getChunkMap().addTile(new Tile(tilSpr, -7, 1));
		
		// Game Loop
		while (!Display.isCloseRequested()) {
			gameWorld.update();
			
			renderer.prepare();
			renderer.render(gameWorld);
			
			DisplayManager.updateDisplay();
		}
		
		renderer.cleanUp();
		Loader.TILE_LOADER.unloadAll();
		DisplayManager.closeDisplay();
		
	}
	
}
