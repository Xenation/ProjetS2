package fr.iutvalence.info.dut.m2107.core;

import org.lwjgl.Sys;
import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.entities.Collider;
import fr.iutvalence.info.dut.m2107.entities.Entity;
import fr.iutvalence.info.dut.m2107.entities.Rat;
import fr.iutvalence.info.dut.m2107.entities.Slime;
import fr.iutvalence.info.dut.m2107.entities.SpriteDatabase;
import fr.iutvalence.info.dut.m2107.events.EventManager;
import fr.iutvalence.info.dut.m2107.gui.GUI;
import fr.iutvalence.info.dut.m2107.gui.GUIMainMenu;
import fr.iutvalence.info.dut.m2107.gui.GUIMaster;
import fr.iutvalence.info.dut.m2107.inventory.ItemDatabase;
import fr.iutvalence.info.dut.m2107.listeners.GUIListener;
import fr.iutvalence.info.dut.m2107.render.DisplayManager;
import fr.iutvalence.info.dut.m2107.render.Loader;
import fr.iutvalence.info.dut.m2107.render.Renderer;
import fr.iutvalence.info.dut.m2107.saving.WorldLoader;
import fr.iutvalence.info.dut.m2107.sound.OpenAL;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Input;
import fr.iutvalence.info.dut.m2107.storage.Layer.LayerStore;

/**
 * Manages the different objects of the game.<br>
 * - Renderer<br>
 * - GUI<br>
 * - ChunkMap<br>
 * - LayerMap<br>
 * @author Xenation
 *
 */
public class GameManager {
	
	/**
	 * The renderer used for rendering
	 */
	private static Renderer renderer;
	
	/**
	 * The Main Menu
	 */
	private static GUIMainMenu mainMenu;
	/**
	 * Whether the main menu needs to be unloaded.<br>
	 * Used to avoid concurrent modifications
	 */
	private static boolean mainMenu_toUnload;
	
	/**
	 * The game GUI
	 */
	private static GUI gui;
	/**
	 * Whether the game GUI needs to be unloaded.<br>
	 * Used to avoid concurrent modifications
	 */
	private static boolean gui_toUnload;
	private static boolean gui_tileSelect_toUnload;
	
	/**
	 * The listener for GUI broadcast events
	 */
	private static GUIListener guiListener;
	
	/**
	 * Whether the game needs to be closed.
	 */
	public static boolean isQuitting;
	
	
	/**
	 * Initialises the components of the game.<br>
	 * - GUIMaster<br>
	 * - OpenAL<br>
	 * - ItemDatabase<br>
	 * - Renderer<br>
	 * - GameWorld<br>
	 * - EventManager<br>
	 * - GUI<br>
	 */
	public static void init() {
		// OpenAL Initialisation
		OpenAL.init();
		// Database Initialisation
		ItemDatabase.create();
		// Renderer
		renderer = new Renderer();
		// GUI Initialisation
		GUIMaster.init();
		// GameWorld Initialisation
		GameWorld.init();
		// Events Initialisation
		EventManager.init();
		
		guiListener = new GUIListener();
		EventManager.register(guiListener);
		
		mainMenu = new GUIMainMenu();
		gui = new GUI();
	}
	
	/**
	 * Renders the GameWorld
	 */
	public static void render() {
		renderer.prepare();
		renderer.render();
	}
	
	/**
	 * Updates the inputs and GameWorld.<br>
	 * Also applies the requested unloads.
	 */
	public static void update() {
		Input.input();
		GameWorld.update();
		if (gui.isLoaded()) {
			gui.update();
		}
		applyUnloads();
	}
	
	/**
	 * Applies the requested unloads
	 */
	private static void applyUnloads() {
		if (mainMenu_toUnload) {
			if (mainMenu.isLoaded()) {
				mainMenu.unloadGUIElement();
				guiListener.resetCounter();
			}
			mainMenu_toUnload = false;
		}
		if (gui_toUnload) {
			if (gui.isLoaded()) {
				gui.unloadGUIElements();
				guiListener.resetCounter();
			}
			gui_toUnload = false;
		}
		if (gui_tileSelect_toUnload) {
			if (gui.isTileSelectOn()) {
				gui.hideTileSelect();
			}
			gui_tileSelect_toUnload = false;
		}
	}
	
	/**
	 * CleanUp the game by:<br>
	 * - deleting OpenAL buffers<br>
	 * - detaching the shaders<br>
	 * - unloading all the VAOs and VBOs 
	 */
	public static void cleanUp() {
		OpenAL.delete();
		renderer.cleanUp();
		Loader.TILE_LOADER.unloadAll();
		Loader.SPRITE_LOADER.unloadAll();
		Loader.TEXT_LOADER.unloadAll();
		Loader.GUI_LOADER.unloadAll();
	}
	
	//// MAIN MENU \\\\
	/**
	 * Loads the Main Menu
	 */
	public static void loadMainMenu() {
		if (!mainMenu.isLoaded()) {
			mainMenu.loadGUIElement();
		}
		WorldLoader.setFilePath("saves/default/menu.eagl");
		WorldLoader.loadWorld();
		GameWorld.chunkMap.updateWhole();
		GameWorld.camera.setTarget(new Entity());
	}
	/**
	 * Requests the unloading of the MainMenu.<br>
	 * It will be unloaded at the end of the update() call.
	 */
	public static void unloadMainMenu() {
		mainMenu_toUnload = true;
	}
	
	//// GUI \\\\
	/**
	 * Loads the game GUI
	 */
	public static void loadGUI() {
		if (!gui.isLoaded()) {
			gui.loadGUIElements();
		}
	}
	/**
	 * Requests the unloading of the game GUI.<br>
	 * It will be unloaded at the end of the update() call.
	 */
	public static void unloadGUI() {
		gui_toUnload = true;
	}
	public static void unloadGUITileSelect() {
		gui_tileSelect_toUnload = true;
	}
	
	//// CHUNKMAP \\\\
	/**
	 * Loads the default ChunkMap
	 */
	public static void loadDefaultChunkMap() {
		GameWorld.chunkMap.clear();
		GameWorld.backChunkMap.clear();
		WorldLoader.setFilePath("saves/default/test.eagl");
		WorldLoader.loadWorld();
		GameWorld.chunkMap.updateWhole();
	}
	/**
	 * Unloads the current ChunkMap.<br>
	 * Simple clear() call
	 */
	public static void unloadChunkMap() {
		GameWorld.chunkMap.clear();
	}
	
	//// LAYERMAP \\\\
	/**
	 * Loads the Default Entities
	 */
	public static void loadDefaultEntities() {
		// Start Time Initialisation
		DisplayManager.setStartTime((float)Sys.getTime());
		
		GameWorld.player.init();
		GameWorld.camera.setTarget(GameWorld.player);
		GameWorld.layerMap.getStoredLayer(LayerStore.PLAYER).add(GameWorld.player);
		
		Collider chestCollider = new Collider(-SpriteDatabase.getChestSpr().getSize().x/2, -SpriteDatabase.getChestSpr().getSize().y/2, SpriteDatabase.getChestSpr().getSize().x/2, SpriteDatabase.getChestSpr().getSize().y/2 - 0.5f);
		Entity chest = new Entity(new Vector2f(6.5f, -3f), 0, SpriteDatabase.getChestSpr(), chestCollider);
		GameWorld.layerMap.getStoredLayer(LayerStore.DECORATION).add(chest);
		
		for (byte i = 0; i < 100; i++) {
			GameWorld.layerMap.getStoredLayer(LayerStore.MOBS).add(new Slime(new Vector2f(2+(float)Math.random()*i*2, 2+(float)Math.random()*i*2), 0, SpriteDatabase.getSlimeSpr() , new Collider(-.5f, -.625f, .5f, .625f), new Vector2f(), 3, 10, 15));
			GameWorld.layerMap.getStoredLayer(LayerStore.MOBS).add(new Rat(new Vector2f(2+(float)Math.random()*i*2, 2+(float)Math.random()*i*2), 0, SpriteDatabase.getRatSpr() , new Collider(-.25f, -.25f, .25f, .25f), new Vector2f(), 6, 1, 0));
		}
	}
	
}
