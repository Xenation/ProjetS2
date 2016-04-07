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
		//Loader loader = new Loader();
		
//		TileSprite tilSpr = new TileSprite("tile_dirt");
		
		GameWorld gameWorld = new GameWorld(new ChunkMap());
		Chunk chk = new Chunk(new Vector2i(0, 0));
		gameWorld.getChunkMap().put(chk.getPosition(), chk);
		gameWorld.getChunkMap().addTile(new Tile(new TileSprite("tile_dirt"), 1, 1));
		gameWorld.getChunkMap().addTile(new Tile(new TileSprite("tile_dirt"), 3, 1));
		
		// Game Loop
		while (!Display.isCloseRequested()) {			
			renderer.prepare();
//			renderer.render(tilSpr);
			renderer.render(gameWorld);
			
			DisplayManager.updateDisplay();
		}
		
		DisplayManager.closeDisplay();
		
	}
	
}
