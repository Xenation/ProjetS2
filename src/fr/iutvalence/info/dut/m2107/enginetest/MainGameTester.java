package fr.iutvalence.info.dut.m2107.enginetest;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.entities.Collider;
import fr.iutvalence.info.dut.m2107.entities.EntityDatabase;
import fr.iutvalence.info.dut.m2107.entities.LivingEntity;
import fr.iutvalence.info.dut.m2107.events.EventManager;
import fr.iutvalence.info.dut.m2107.events.ListenersScanner;
import fr.iutvalence.info.dut.m2107.fontMeshCreator.GUIText;
import fr.iutvalence.info.dut.m2107.fontRendering.TextMaster;
import fr.iutvalence.info.dut.m2107.items.ItemDatabase;
import fr.iutvalence.info.dut.m2107.items.SpriteDatabase;
import fr.iutvalence.info.dut.m2107.items.WeaponItem;
import fr.iutvalence.info.dut.m2107.guiRendering.GUIElement;
import fr.iutvalence.info.dut.m2107.guiRendering.GUIMaster;
import fr.iutvalence.info.dut.m2107.listeners.TileListener;
import fr.iutvalence.info.dut.m2107.render.*;
import fr.iutvalence.info.dut.m2107.saving.WorldLoader;
import fr.iutvalence.info.dut.m2107.saving.WorldSaver;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Input;

public class MainGameTester {
	
	public static float degreeShoot = 0;
	public static float shootX;
	public static float shootY;
	
	static String strInventory;
	
	public static void main(String[] args) {
		// Display Window initialization
		DisplayManager.createDisplay();
		DisplayManager.updateDisplay();
		
		TextMaster.init();
		GUIMaster.init();
		
		Renderer renderer = new Renderer();
		
		GameWorld.camera.setTarget(GameWorld.player);
		
		GameWorld.layerMap.addEmpty(4);
		GameWorld.layerMap.getLayer(1).add(GameWorld.player);
		
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
		
		GUIText loaders = new GUIText("Loaders :", 1, 0, .90f, 0.5f, false);
		loaders.setColour(0, 1, 0);
		GUIText loaderStats = new GUIText("", .8f, 0, .92f, 0.5f, false);
		loaderStats.setColour(0, 1, 0);
		loaderStats.setLineHeight(0.024);
		
		WorldLoader.loadWorld();
		
		ItemDatabase.create();
		
		GameWorld.layerMap.getLayer(1).add(new LivingEntity(new Vector2f(4, -6), 45, SpriteDatabase.getSwordSpr(), new Collider(SpriteDatabase.getSwordSpr()), new Vector2f(), 0, 10, 0, 0));
		
		GameWorld.player.getInventory().add(ItemDatabase.get(0), 20);
		GameWorld.player.getInventory().add(ItemDatabase.get(1), 10);
		
		strInventory = GameWorld.player.getInventory().toString();
		System.out.println(strInventory);
		
		EventManager.init();
		for (Class<?> cla : ListenersScanner.listenersClasses) {
			System.out.println("LISTENER: "+cla.getSimpleName());
		}

		EventManager.register(new TileListener());
		
		GameWorld.player.getQuickBar()[0] = ItemDatabase.get(2);
		GameWorld.player.getQuickBar()[1] = ItemDatabase.get(3);
		GameWorld.player.getQuickBar()[2] = ItemDatabase.get(0);
		GameWorld.player.getQuickBar()[3] = ItemDatabase.get(1);
		
		if(GameWorld.player.getQuickBar()[0] != null) {
			switch (GameWorld.player.getQuickBar()[0].getType()) {
				case WEAPON:
					GameWorld.player.setItemToUse(EntityDatabase.weapon((WeaponItem) GameWorld.player.getQuickBar()[0], GameWorld.player));
					break;
				case AMMO:
				case ARMOR:
					GameWorld.player.setItemToUse(EntityDatabase.item(GameWorld.player.getQuickBar()[0], GameWorld.player));
					break;
				default:
					break;
			}
			GameWorld.layerMap.getLayer(0).add(GameWorld.player.getItemToUse());
		}
		
		// GUI
		float width = 0.05f;
		float height = 0.05f;
		float posX = .5f - width/2;
		float posY = height;
		float offsetX = width;
		for (int slotNumber = 0; slotNumber < 8; slotNumber++) {
			GUIElement quickBar = new GUIElement("gui/quick_bar_slot", new Vector2f(posX - offsetX*3.5f + offsetX*slotNumber, 1-posY), width, height*DisplayManager.aspectRatio);
			if(GameWorld.player.getQuickBar()[slotNumber] != null) {
				GUIElement quickBarItem = new GUIElement(GameWorld.player.getQuickBar()[slotNumber].getSpr().getTextureID(), new Vector2f(posX - offsetX*3.5f + offsetX*slotNumber + 0.01f, 1-posY - 0.02f), width-0.02f, (height-0.02f)*DisplayManager.aspectRatio);
			}
		}
		int selectSlot = 0;
		GUIElement selectQuickBar = new GUIElement("gui/select_quick_bar_slot", new Vector2f(posX - offsetX*3.5f + selectSlot*offsetX, 1-posY), width, height*DisplayManager.aspectRatio);
		//
		
		// Game Loop
		while (!Display.isCloseRequested()) {
			if(!strInventory.equals(GameWorld.player.getInventory().toString())) {
				strInventory = GameWorld.player.getInventory().toString();
				System.out.println(strInventory);
			}
			
			Input.input();
			
			shootX = GameWorld.camera.getMouseWorldX() - GameWorld.player.getPosition().x;
			shootY = GameWorld.camera.getMouseWorldY() - GameWorld.player.getPosition().y;
			
			if (shootY > 0) degreeShoot = (float) (Math.atan(shootX / shootY)*180/Math.PI-90);
			else degreeShoot = (float) (Math.atan(shootX / shootY)*180/Math.PI+90);

			chunkStats.updateText("Chunks: "+GameWorld.chunkMap.getChunkCount()
					+ "\nTiles: "+GameWorld.chunkMap.getTilesCount()
					+ "\nCurrent Tiles: "+GameWorld.chunkMap.getSurroundingTilesCount(-Renderer.UNITS_Y/2*DisplayManager.aspectRatio, Renderer.UNITS_Y/2*DisplayManager.aspectRatio, Renderer.UNITS_Y/2, -Renderer.UNITS_Y/2, GameWorld.camera.getPosition()));
			
			loaderStats.updateText("TILES: "+Loader.TILE_LOADER.debugValues()
					+ "\nSPRITES: "+Loader.SPRITE_LOADER.debugValues()
					+ "\nTEXT: "+Loader.TEXT_LOADER.debugValues()
					+ "\nGUI: "+Loader.GUI_LOADER.debugValues());
			
			GameWorld.update();
			
			renderer.prepare();
			renderer.render();
			
			// GUI
			selectSlot += Input.WheelScrolling();
			if(selectSlot > 7) selectSlot -= 8;
			if(selectSlot < 0) selectSlot += 8;
			selectQuickBar.setPosition(new Vector2f(posX - offsetX*3.5f + selectSlot*offsetX, selectQuickBar.getPosition().y));
			//
			if(GameWorld.player.getQuickBar()[selectSlot] != null) {
				if(GameWorld.player.getItemToUse() == null || !GameWorld.player.getItemToUse().equals(EntityDatabase.item(GameWorld.player.getQuickBar()[selectSlot], GameWorld.player))) {
					if(GameWorld.player.getItemToUse() != null) GameWorld.layerMap.getLayer(0).remove(GameWorld.player.getItemToUse());	
					switch (GameWorld.player.getQuickBar()[selectSlot].getType()) {
						case WEAPON:
							GameWorld.player.setItemToUse(EntityDatabase.weapon((WeaponItem) GameWorld.player.getQuickBar()[selectSlot], GameWorld.player));
							break;
						case AMMO:
						case ARMOR:
							GameWorld.player.setItemToUse(EntityDatabase.item(GameWorld.player.getQuickBar()[selectSlot], GameWorld.player));
							break;
						default:
							break;
					}
					GameWorld.layerMap.getLayer(0).add(GameWorld.player.getItemToUse());
				}
			} else if(GameWorld.player.getItemToUse() != null) {
					GameWorld.layerMap.getLayer(0).remove(GameWorld.player.getItemToUse());
					GameWorld.player.setItemToUse(null);
			}
			
			GUIMaster.render();
			TextMaster.render();
			
			DisplayManager.updateDisplay();
		}
		
		renderer.cleanUp();
		TextMaster.cleanUp();
		GUIMaster.cleanUp();
		Loader.TILE_LOADER.unloadAll();
		Loader.SPRITE_LOADER.unloadAll();
		Loader.TEXT_LOADER.unloadAll();
		Loader.GUI_LOADER.unloadAll();
		// Code to clean the whole chunk loader
		//ChunkLoader.CHUNK_LOADER.unloadAll();
		DisplayManager.closeDisplay();
		
	}
	
}
