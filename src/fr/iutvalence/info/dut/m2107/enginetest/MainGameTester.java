package fr.iutvalence.info.dut.m2107.enginetest;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.entities.LivingEntity;
import fr.iutvalence.info.dut.m2107.entities.MovableEntity;
import fr.iutvalence.info.dut.m2107.models.Sprite;
import fr.iutvalence.info.dut.m2107.fontMeshCreator.GUIText;
import fr.iutvalence.info.dut.m2107.fontRendering.TextMaster;
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
		
		TextMaster.init();
		
		Renderer renderer = new Renderer();		
		
		GameWorld.camera.setTarget(GameWorld.player);

		GameWorld.layerMap.addEmpty(4);
		GameWorld.layerMap.getLayer(0).add(GameWorld.player);
		GameWorld.layerMap.getLayer(0).add(new LivingEntity(new Vector2f(-1, 1.5f), new Sprite("item/sugar", new Vector2f(1, 1))));
		GameWorld.layerMap.getLayer(0).add(new MovableEntity(new Vector2f(1, 1.5f), new Sprite("item/sugar", new Vector2f(1, 1))));
		
		// Debug for the whole chunk rendering 
		//System.out.println(ChunkLoader.CHUNK_LOADER.debugBuffers());
		//System.out.println(ChunkLoader.CHUNK_LOADER.debugGLBuffers());
		
		WorldSaver.setFilePath("res/test.sav");
		WorldLoader.setFilePath("res/test.sav");
		
		GUIText chunks = new GUIText("Chunks :", 1, 0, .8f, 0.5f, false);
		chunks.setColour(0, 1, 0);
		GUIText chunkStats = new GUIText("", .8f, 0, .82f, 0.5f, false);
		chunkStats.setColour(0, 1, 0);
		chunkStats.setLineHeight(0.024);
		
		GUIText loaders = new GUIText("Loaders :", 1, 0, .92f, 0.5f, false);
		loaders.setColour(0, 1, 0);
		GUIText loaderStats = new GUIText("", .8f, 0, .94f, 0.5f, false);
		loaderStats.setColour(0, 1, 0);
		loaderStats.setLineHeight(0.024);
		
		WorldLoader.loadWorld();
		
		// Game Loop
		while (!Display.isCloseRequested()) {

			GameWorld.update();
			
			chunkStats.updateText("Chunks: "+GameWorld.chunkMap.getChunkCount()
					+ "\nTiles: "+GameWorld.chunkMap.getTilesCount()
					+ "\nCurrent Tiles: "+GameWorld.chunkMap.getSurroundingTilesCount(-Renderer.UNITS_Y/2*DisplayManager.aspectRatio, Renderer.UNITS_Y/2*DisplayManager.aspectRatio, Renderer.UNITS_Y/2, -Renderer.UNITS_Y/2, GameWorld.camera.getPosition()));
			
			loaderStats.updateText("TILES: "+Loader.TILE_LOADER.debugValues()
					+ "\nSPRITES: "+Loader.SPRITE_LOADER.debugValues()
					+ "\nTEXT: "+Loader.TEXT_LOADER.debugValues());
			
			renderer.prepare();
			renderer.render();

			TextMaster.render();
			
			DisplayManager.updateDisplay();
		}
		
		renderer.cleanUp();
		TextMaster.cleanUp();
		Loader.TILE_LOADER.unloadAll();
		Loader.SPRITE_LOADER.unloadAll();
		Loader.TEXT_LOADER.unloadAll();
		// Code to clean the whole chunk loader
		//ChunkLoader.CHUNK_LOADER.unloadAll();
		DisplayManager.closeDisplay();
		
	}
	
}
