package fr.iutvalence.info.dut.m2107.enginetest;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.entities.LivingEntity;
import fr.iutvalence.info.dut.m2107.entities.MovableEntity;
import fr.iutvalence.info.dut.m2107.models.Sprite;
import fr.iutvalence.info.dut.m2107.render.*;
import fr.iutvalence.info.dut.m2107.saving.WorldLoader;
import fr.iutvalence.info.dut.m2107.saving.WorldSaver;
import fr.iutvalence.info.dut.m2107.storage.Chunk;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Vector2i;
import fr.iutvalence.info.dut.m2107.tiles.Tile;
import fr.iutvalence.info.dut.m2107.tiles.TileType;

public class MainGameTester {
	
	public static void main(String[] args) {
		// Display Window initialization
		DisplayManager.createDisplay();
		DisplayManager.updateDisplay();
		
		Renderer renderer = new Renderer();		
		
		GameWorld.camera.setTarget(GameWorld.player);
		
		Chunk chk = new Chunk(new Vector2i(0, 0));
		GameWorld.chunkMap.put(chk.getPosition(), chk);
		GameWorld.chunkMap.addTile(new Tile(TileType.Dirt, 0, 0));
		GameWorld.chunkMap.addTile(new Tile(TileType.Dirt, 2, 0));
		GameWorld.chunkMap.addTile(new Tile(TileType.Dirt, 4, 0));
		GameWorld.chunkMap.addTile(new Tile(TileType.Dirt, 6, 0));
		Chunk chk2 = new Chunk(new Vector2i(-1, 0));
		GameWorld.chunkMap.put(chk2.getPosition(), chk2);
		GameWorld.chunkMap.addTile(new Tile(TileType.Stone, -1, 0));
		GameWorld.chunkMap.addTile(new Tile(TileType.Stone, -3, 0));
		GameWorld.chunkMap.addTile(new Tile(TileType.Stone, -5, 0));
		GameWorld.chunkMap.addTile(new Tile(TileType.Stone, -7, 0));
		
		GameWorld.layerMap.addEmpty(4);
		GameWorld.layerMap.getLayer(0).add(GameWorld.player);
		GameWorld.layerMap.getLayer(0).add(new LivingEntity(new Vector2f(-1, 1.5f), new Sprite("item/sugar", new Vector2f(1, 1)), GameWorld.layerMap.getLayer(0)));
		GameWorld.layerMap.getLayer(0).add(new MovableEntity(new Vector2f(1, 1.5f), new Sprite("item/sugar", new Vector2f(1, 1)), GameWorld.layerMap.getLayer(0)));
		
		// Debug for the whole chunk rendering 
		//System.out.println(ChunkLoader.CHUNK_LOADER.debugBuffers());
		//System.out.println(ChunkLoader.CHUNK_LOADER.debugGLBuffers());
		
		WorldSaver.setFilePath("res/test.sav");
		WorldLoader.setFilePath("res/test.sav");
		
		// Game Loop
		while (!Display.isCloseRequested()) {
			GameWorld.update();
			
			renderer.prepare();
			renderer.render();
			
			DisplayManager.updateDisplay();
		}
		
		renderer.cleanUp();
		Loader.TILE_LOADER.unloadAll();
		Loader.SPRITE_LOADER.unloadAll();
		// Code to clean the whole chunk loader
		//ChunkLoader.CHUNK_LOADER.unloadAll();
		DisplayManager.closeDisplay();
		
	}
	
}
