package fr.iutvalence.info.dut.m2107.enginetest;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.entities.Collider;
import fr.iutvalence.info.dut.m2107.entities.Entity;
import fr.iutvalence.info.dut.m2107.entities.ItemDatabase;
import fr.iutvalence.info.dut.m2107.entities.SpriteDatabase;
import fr.iutvalence.info.dut.m2107.entities.Zombie;
import fr.iutvalence.info.dut.m2107.events.EventManager;
import fr.iutvalence.info.dut.m2107.events.ListenersScanner;
import fr.iutvalence.info.dut.m2107.fontMeshCreator.GUIText;
import fr.iutvalence.info.dut.m2107.fontRendering.TextMaster;
import fr.iutvalence.info.dut.m2107.listeners.TileListener;
import fr.iutvalence.info.dut.m2107.render.*;
import fr.iutvalence.info.dut.m2107.saving.SaveFileUpdater;
import fr.iutvalence.info.dut.m2107.saving.WorldLoader;
import fr.iutvalence.info.dut.m2107.saving.WorldSaver;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Input;
import fr.iutvalence.info.dut.m2107.storage.Layer.LayerStore;

public class MainGameTester {
	
	/**
	 * Launches the game (test version)
	 * arguments:
	 * <ul>
	 * <li>0: anything - won't create the window</li>
	 * </ul>
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		// Display Window initialisation
		if (args.length == 0) {
			DisplayManager.createDisplay();
			DisplayManager.updateDisplay();
			TextMaster.init();
		}
		
		//openAL.init();
		
		ItemDatabase.create();
		
		Renderer renderer = new Renderer();
		
		GameWorld.init();
		
		GameWorld.guiLayerMap.addEmpty(4);
		
		GameWorld.camera.setTarget(GameWorld.player);
		
		GameWorld.layerMap.addEmpty(4);
		GameWorld.layerMap.getStoredLayer(LayerStore.PLAYER).add(GameWorld.player);
		
		// Debug for the whole chunk rendering 
		//System.out.println(ChunkLoader.CHUNK_LOADER.debugBuffers());
		//System.out.println(ChunkLoader.CHUNK_LOADER.debugGLBuffers());
		
		WorldSaver.setFilePath("res/test.sav");
		WorldLoader.setFilePath("res/test.sav");
		SaveFileUpdater.setFilePath("res/test.sav");
		
		new GUIText("Chunks :", 1, 0, .8f, 0.5f, false, true);
		GUIText chunkStats = new GUIText("", .8f, 0, .82f, 0.5f, false, true);
		chunkStats.setLineHeight(0.024);
		
		new GUIText("Loaders :", 1, 0, .90f, 0.5f, false, true);
		GUIText loaderStats = new GUIText("", .8f, 0, .92f, 0.5f, false, true);
		loaderStats.setLineHeight(0.024);
		
		//SaveFileUpdater.updateFileFormat("tvxy", 10);
		
		WorldLoader.loadWorld();
		Collider chestCollider = new Collider(-SpriteDatabase.getChestSpr().getSize().x/2, -SpriteDatabase.getChestSpr().getSize().y/2, SpriteDatabase.getChestSpr().getSize().x/2, SpriteDatabase.getChestSpr().getSize().y/2 - 0.5f);
		Entity chest = new Entity(new Vector2f(6.5f, -3f), 0, SpriteDatabase.getChestSpr(), chestCollider);
		GameWorld.layerMap.getStoredLayer(LayerStore.DECORATION).add(chest);
		
		EventManager.init();
		for (Class<?> cla : ListenersScanner.listenersClasses) {
			System.out.println("LISTENER: "+cla.getSimpleName());
		}

		EventManager.register(new TileListener());
		
		// Game Loop
		while (!Display.isCloseRequested()) {
			Input.input();
			
			chunkStats.updateText("Chunks: "+GameWorld.chunkMap.getChunkCount()
					+ "\nTiles: "+GameWorld.chunkMap.getTilesCount()
					+ "\n\tCurrent Tiles: "+GameWorld.chunkMap.getSurroundingTilesCount(-Renderer.UNITS_Y/2*DisplayManager.aspectRatio, Renderer.UNITS_Y/2*DisplayManager.aspectRatio, Renderer.UNITS_Y/2, -Renderer.UNITS_Y/2, GameWorld.camera.getPosition()));
			
			loaderStats.updateText("TILES: "+Loader.TILE_LOADER.debugValues()
					+ "\nSPRITES: "+Loader.SPRITE_LOADER.debugValues()
					+ "\nTEXT: "+Loader.TEXT_LOADER.debugValues()
					+ "\nGUI: "+Loader.GUI_LOADER.debugValues());
			
			if(Input.isKeyWater())
				GameWorld.layerMap.getStoredLayer(LayerStore.MOBS).add(new Zombie(new Vector2f(0, 0), SpriteDatabase.getZombieSpr(), new Collider(-.5f, -1.55f, .5f, 1.55f)));
			
			//openAL.update();
			
			GameWorld.update();
			
			renderer.prepare();
			renderer.render();
			
			TextMaster.render();
			
			DisplayManager.updateDisplay();
		}
		//openAL.delete();
		renderer.cleanUp();
		TextMaster.cleanUp();
		Loader.TILE_LOADER.unloadAll();
		Loader.SPRITE_LOADER.unloadAll();
		Loader.TEXT_LOADER.unloadAll();
		Loader.GUI_LOADER.unloadAll();
		DisplayManager.closeDisplay();
		
	}
	
}
