package fr.iutvalence.info.dut.m2107.enginetest;

import org.lwjgl.opengl.Display;

import fr.iutvalence.info.dut.m2107.render.*;
import fr.iutvalence.info.dut.m2107.saving.WorldLoader;
import fr.iutvalence.info.dut.m2107.saving.WorldSaver;
import fr.iutvalence.info.dut.m2107.storage.Chunk;
import fr.iutvalence.info.dut.m2107.storage.ChunkMap;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Vector2i;
import fr.iutvalence.info.dut.m2107.tiles.Tile;
import fr.iutvalence.info.dut.m2107.tiles.TileType;

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
		
		WorldSaver.setFilePath("res/test.sav");
		WorldLoader.setFilePath("res/test.sav");
		
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
