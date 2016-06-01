package fr.iutvalence.info.dut.m2107.core;

import org.lwjgl.Sys;
import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.entities.Collider;
import fr.iutvalence.info.dut.m2107.entities.Entity;
import fr.iutvalence.info.dut.m2107.entities.Rat;
import fr.iutvalence.info.dut.m2107.entities.SpriteDatabase;
import fr.iutvalence.info.dut.m2107.events.EventManager;
import fr.iutvalence.info.dut.m2107.gui.GUI;
import fr.iutvalence.info.dut.m2107.gui.GUIMainMenu;
import fr.iutvalence.info.dut.m2107.gui.GUIMaster;
import fr.iutvalence.info.dut.m2107.inventory.ItemDatabase;
import fr.iutvalence.info.dut.m2107.render.DisplayManager;
import fr.iutvalence.info.dut.m2107.render.Loader;
import fr.iutvalence.info.dut.m2107.render.Renderer;
import fr.iutvalence.info.dut.m2107.saving.WorldLoader;
import fr.iutvalence.info.dut.m2107.sound.OpenAL;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Input;
import fr.iutvalence.info.dut.m2107.storage.Layer.LayerStore;

public class GameManager {
	
	private static Renderer renderer;
	
	private static GUIMainMenu mainMenu;
	private static boolean mainMenu_toUnload;
	private static GUI gui;
	private static boolean gui_toUnload;
	
	public static boolean isQuitting;
	
	
	public static void init() {
		// GUI Initialisation
		GUIMaster.init();
		// OpenAL Initialisation
		OpenAL.init();
		// Database Initialisation
		ItemDatabase.create();
		// Renderer
		renderer = new Renderer();
		// GameWorld Initialisation
		GameWorld.init();
		// Events Initialisation
		EventManager.init();
		
		mainMenu = new GUIMainMenu();
		gui = new GUI();
	}
	
	public static void render() {
		renderer.prepare();
		renderer.render();
	}
	
	public static void update() {
		Input.input();
		GameWorld.update();
		if (gui.isLoaded()) {
			gui.update();
		}
		applyUnloads();
	}
	
	private static void applyUnloads() {
		if (mainMenu_toUnload) {
			if (mainMenu.isLoaded()) {
				mainMenu.unloadGUIElement();
			}
			mainMenu_toUnload = false;
		}
		if (gui_toUnload) {
			if (gui.isLoaded()) {
				gui.unloadGUIElements();
			}
			gui_toUnload = false;
		}
	}
	
	public static void cleanUp() {
		OpenAL.delete();
		renderer.cleanUp();
		Loader.TILE_LOADER.unloadAll();
		Loader.SPRITE_LOADER.unloadAll();
		Loader.TEXT_LOADER.unloadAll();
		Loader.GUI_LOADER.unloadAll();
	}
	
	// MAIN MENU
	public static void loadMainMenu() {
		if (!mainMenu.isLoaded()) {
			mainMenu.loadGUIElement();
		}
		WorldLoader.setFilePath("saves/default/menu.eagl");
		WorldLoader.loadWorld();
		GameWorld.chunkMap.updateWhole();
		GameWorld.camera.setTarget(new Entity());
	}
	public static void unloadMainMenu() {
		mainMenu_toUnload = true;
	}
	
	// GUI
	public static void loadGUI() {
		if (!gui.isLoaded()) {
			gui.loadGUIElements();
		}
	}
	public static void unloadGUI() {
		gui_toUnload = true;
	}
	
	// CHUNKMAP
	public static void loadDefaultChunkMap() {
		GameWorld.chunkMap.clear();
		GameWorld.backChunkMap.clear();
		WorldLoader.setFilePath("saves/default/test.eagl");
		WorldLoader.loadWorld();
		GameWorld.chunkMap.updateWhole();
	}
	public static void unloadChunkMap() {
		GameWorld.chunkMap.clear();
	}
	
	// LAYERMAP
	public static void loadDefaultEntities() {
		// Start Time Initialisation
		DisplayManager.setStartTime((float)Sys.getTime());
		
		GameWorld.player.init();
		GameWorld.camera.setTarget(GameWorld.player);
		GameWorld.layerMap.getStoredLayer(LayerStore.PLAYER).add(GameWorld.player);
		
		Collider chestCollider = new Collider(-SpriteDatabase.getChestSpr().getSize().x/2, -SpriteDatabase.getChestSpr().getSize().y/2, SpriteDatabase.getChestSpr().getSize().x/2, SpriteDatabase.getChestSpr().getSize().y/2 - 0.5f);
		Entity chest = new Entity(new Vector2f(6.5f, -3f), 0, SpriteDatabase.getChestSpr(), chestCollider);
		GameWorld.layerMap.getStoredLayer(LayerStore.DECORATION).add(chest);
		
		for (int i = 5; i < 100; i++) {
			GameWorld.layerMap.getStoredLayer(LayerStore.MOBS).add(new Rat(new Vector2f(i/2f, i/2f), 0, SpriteDatabase.getRatSpr() , new Collider(-.25f, -.25f, .25f, .25f), new Vector2f(), 6, 1, 0, 0));
		}
	}
	
}
