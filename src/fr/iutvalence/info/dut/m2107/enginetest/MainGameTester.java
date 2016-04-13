package fr.iutvalence.info.dut.m2107.enginetest;

import org.lwjgl.opengl.Display;

import fr.iutvalence.info.dut.m2107.fontMeshCreator.GUIText;
import fr.iutvalence.info.dut.m2107.fontRendering.TextMaster;
import fr.iutvalence.info.dut.m2107.render.*;
import fr.iutvalence.info.dut.m2107.saving.WorldLoader;
import fr.iutvalence.info.dut.m2107.saving.WorldSaver;
import fr.iutvalence.info.dut.m2107.storage.Chunk;
import fr.iutvalence.info.dut.m2107.storage.ChunkMap;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.LayerMap;
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
		
		GameWorld gameWorld = new GameWorld(new ChunkMap(), new LayerMap());
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
		
		gameWorld.getLayerMap().addEmpty(4);

		// Debug for the whole chunk rendering 
		//System.out.println(ChunkLoader.CHUNK_LOADER.debugBuffers());
		//System.out.println(ChunkLoader.CHUNK_LOADER.debugGLBuffers());
		
		WorldSaver.setFilePath("res/test.sav");
		WorldLoader.setFilePath("res/test.sav");
		
		GUIText chunks = new GUIText("Chunks :", 1, 0, .8f, 0.5f, false);
		chunks.setColour(0, 1, 0);
		GUIText chunkNb = new GUIText("", .8f, 0, .82f, 0.5f, false);
		chunkNb.setColour(0, 1, 0);
		GUIText tilesNb = new GUIText("", .8f, 0, .84f, 0.5f, false);
		tilesNb.setColour(0, 1, 0);
		GUIText tilesCur = new GUIText("", .8f, 0, .86f, 0.5f, false);
		tilesCur.setColour(0, 1, 0);
		
		GUIText loaders = new GUIText("Loaders :", 1, 0, .92f, 0.5f, false);
		loaders.setColour(0, 1, 0);
		GUIText tilLoad = new GUIText("", .8f, 0, .94f, .5f, false);
		tilLoad.setColour(0, 1, 0);
		GUIText sprLoad = new GUIText("", .8f, 0, .96f, .5f, false);
		sprLoad.setColour(0, 1, 0);
		GUIText texLoad = new GUIText("", .8f, 0, .98f, .5f, false);
		texLoad.setColour(0, 1, 0);
		
		// Game Loop
		while (!Display.isCloseRequested()) {
			
//			gameWorld.getLayerMap().getLayer(0).add(
//					new MovableEntity(new Vector2f((float) (-10 + (Math.random()* 20)), (float) (-10 + (Math.random()* 20))), (float) (-360 + (Math.random()* 720)), new Sprite("item/diamond", new Vector2f(1, 1)), new Vector2f((float) (-10 + (Math.random()* 20)), (float) (-10 + (Math.random()* 20))), (float) (-10 + (Math.random()* 40))));
//			gameWorld.getLayerMap().getLayer(0).add(
//					new MovableEntity(new Vector2f((float) (-10 + (Math.random()* 20)), (float) (-10 + (Math.random()* 20))), (float) (-360 + (Math.random()* 720)), new Sprite("item/coal", new Vector2f(1, 1)), new Vector2f((float) (-10 + (Math.random()* 20)), (float) (-10 + (Math.random()* 20))), (float) (-10 + (Math.random()* 40))));
//			gameWorld.getLayerMap().getLayer(0).add(
//					new MovableEntity(new Vector2f((float) (-10 + (Math.random()* 20)), (float) (-10 + (Math.random()* 20))), (float) (-360 + (Math.random()* 720)), new Sprite("item/cookie", new Vector2f(1, 1)), new Vector2f((float) (-10 + (Math.random()* 20)), (float) (-10 + (Math.random()* 20))), (float) (-10 + (Math.random()* 40))));
//			gameWorld.getLayerMap().getLayer(0).add(
//					new MovableEntity(new Vector2f((float) (-10 + (Math.random()* 20)), (float) (-10 + (Math.random()* 20))), (float) (-360 + (Math.random()* 720)), new Sprite("item/sugar", new Vector2f(1, 1)), new Vector2f((float) (-10 + (Math.random()* 20)), (float) (-10 + (Math.random()* 20))), (float) (-10 + (Math.random()* 40))));
			
			chunkNb.updateText("Chunks: "+gameWorld.getChunkMap().getChunkCount());
			tilesNb.updateText("Tiles: "+gameWorld.getChunkMap().getTilesCount());
			tilesCur.updateText("Current Tiles: "+gameWorld.getChunkMap().getSurroundingTilesCount(-Renderer.UNITS_Y/2*DisplayManager.aspectRatio, Renderer.UNITS_Y/2*DisplayManager.aspectRatio, Renderer.UNITS_Y/2, -Renderer.UNITS_Y/2, gameWorld.getCamera().getPosition()));
			
			tilLoad.updateText("TILES: "+Loader.TILE_LOADER.debugValues());
			sprLoad.updateText("SPRITES: "+Loader.SPRITE_LOADER.debugValues());
			texLoad.updateText("TEXT: "+Loader.TEXT_LOADER.debugValues());
			
			gameWorld.update();
			
			renderer.prepare();
			renderer.render(gameWorld);
			
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
