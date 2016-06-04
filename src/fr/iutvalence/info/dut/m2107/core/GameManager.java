package fr.iutvalence.info.dut.m2107.core;

import org.lwjgl.Sys;
import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.entities.Chest;
import fr.iutvalence.info.dut.m2107.entities.Collider;
import fr.iutvalence.info.dut.m2107.entities.Entity;
import fr.iutvalence.info.dut.m2107.entities.Player;
import fr.iutvalence.info.dut.m2107.entities.Slime;
import fr.iutvalence.info.dut.m2107.entities.SpriteDatabase;
import fr.iutvalence.info.dut.m2107.entities.Zombie;
import fr.iutvalence.info.dut.m2107.events.EventManager;
import fr.iutvalence.info.dut.m2107.fontMeshCreator.GUIText;
import fr.iutvalence.info.dut.m2107.gui.GUI;
import fr.iutvalence.info.dut.m2107.gui.GUIMainMenu;
import fr.iutvalence.info.dut.m2107.gui.GUIMaster;
import fr.iutvalence.info.dut.m2107.gui.GUISlot;
import fr.iutvalence.info.dut.m2107.inventory.Item;
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
	private static boolean mainMenu_toLoad;
	
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
	private static boolean gui_pause_toUnload;
	private static boolean gui_player_toUnload;
	
	private static GUISlot s1 = null;
	private static GUISlot s2 = null;
	
	/**
	 * The listener for GUI broadcast events
	 */
	private static GUIListener guiListener;
	
	private static boolean ent_default_toUnload;
	
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
	
	public static GUIListener getGUIListener() {
		return guiListener;
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
		/*if (Input.isKeyC()) {
			Light l = new Light(new Vector2f(GameWorld.camera.getMouseWorldX(), GameWorld.camera.getMouseWorldY()), new Vector3f(1, 1, 1), 1, 15);
			GameWorld.layerMap.getLayer(2).add(l);
		}*/
		applyChanges();
	}
	
	/**
	 * Applies the requested unloads
	 */
	private static void applyChanges() {
		if (mainMenu_toUnload) {
			if (mainMenu.isLoaded()) {
				mainMenu.unloadGUIElement();
				guiListener.resetCounter();
			}
			mainMenu_toUnload = false;
		}
		if (mainMenu_toLoad) {
			applyMainMenuLoad();
			mainMenu_toLoad = false;
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
		if (gui_pause_toUnload) {
			if (gui.isPauseMenuOn()) {
				gui.hidePauseMenu();
			}
			gui_pause_toUnload = false;
		}
		if (gui_player_toUnload) {
			if (GameWorld.player.isGUIOn()) {
				GameWorld.player.unloadGUIElements();
			}
			gui_player_toUnload = false;
		}
		if (ent_default_toUnload) {
			unloadEntityLayers();
			GameWorld.player = new Player();
			ent_default_toUnload = false;
		}
		// Slot Switch
		if (s1 != null && s2 != null) {
			switchSlots();
			s1 = null;
			s2 = null;
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
	private static void applyMainMenuLoad() {
		if (!mainMenu.isLoaded()) {
			mainMenu.loadGUIElement();
		}
		unloadChunkMap();
		WorldLoader.setFilePath("saves/default/menu.eagl");
		WorldLoader.loadWorld();
		GameWorld.chunkMap.updateWhole();
		GameWorld.camera.setTarget(new Entity());
	}
	public static void loadMainMenu() {
		mainMenu_toLoad = true;
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
	public static void unloadGUIPauseMenu() {
		gui_pause_toUnload = true;
	}
	public static void unloadGUIPlayer() {
		gui_player_toUnload = true;
	}
	
	//// CHUNKMAP \\\\
	/**
	 * Loads the default ChunkMap
	 */
	public static void loadDefaultChunkMap() {
		GameWorld.chunkMap.clear();
		GameWorld.backChunkMap.clear();
		WorldLoader.setFilePath("saves/Final.eagl");
		WorldLoader.loadWorld();
		GameWorld.chunkMap.updateWhole();
	}
	/**
	 * Unloads the current ChunkMap.<br>
	 * Simple clear() call
	 */
	public static void unloadChunkMap() {
		GameWorld.chunkMap.clear();
		GameWorld.backChunkMap.clear();
	}
	
	//// LAYERMAP \\\\
	/**
	 * Loads the Default Entities
	 */
	public static void loadDefaultEntities() {
		// Start Time Initialisation
		DisplayManager.setStartTime((float)Sys.getTime());
		
		if (GameWorld.player.getLayer() == null)
			GameWorld.player.init();
		GameWorld.camera.setTarget(GameWorld.player);
		GameWorld.layerMap.getStoredLayer(LayerStore.PLAYER).add(GameWorld.player);
		
		Collider chestCollider = new Collider(-SpriteDatabase.getChestSpr().getSize().x/2, -SpriteDatabase.getChestSpr().getSize().y/2, SpriteDatabase.getChestSpr().getSize().x/2, SpriteDatabase.getChestSpr().getSize().y/2 - 0.5f);
		Chest chest = new Chest(new Vector2f(182, 6), SpriteDatabase.getChestSpr(), chestCollider, 10, ItemDatabase.get(2), ItemDatabase.get(0, (short) 10));
		GameWorld.layerMap.getStoredLayer(LayerStore.FURNITURE).add(chest);
		
		for (int i = 0; i < 3; i++) {
			GameWorld.layerMap.getStoredLayer(LayerStore.MOBS).add(new Zombie(new Vector2f((float)(90+Math.random()+i), (float)(20+Math.random()+i)), SpriteDatabase.getZombieSpr(), new Collider(-.5f, -1.55f, .5f, 1.55f), (short) 2, 10, 0));
			GameWorld.layerMap.getStoredLayer(LayerStore.MOBS).add(new Slime(new Vector2f((float)(145+Math.random()+i), (float)(10+Math.random()+i)), SpriteDatabase.getSlimeSpr() , new Collider(-.5f, -.625f, .5f, .625f), (short) 3, 7, 15));
		}
		
		//Item item = Item.copyDropableItem(ItemDatabase.get(10, (short) 10), 0, 10);
		//GameWorld.layerMap.getStoredLayer(LayerStore.ITEM).add(item);
		
		//item = Item.copyDropableItem(ItemDatabase.get(6), 0, 10);
		//GameWorld.layerMap.getStoredLayer(LayerStore.ITEM).add(item);
		
//		org.junit.runner.JUnitCore.runClasses(InventoryTest.class);
		
//		Light sun = new Light(new Vector2f(0, 50), new Vector3f(1, 1, 1), 1, 200);
//		sun.setParent(GameWorld.player);
		
//		for (byte i = 0; i < 100; i++) {
//			GameWorld.layerMap.getStoredLayer(LayerStore.MOBS).add(new Slime(new Vector2f(2+(float)Math.random()*i*2, 2+(float)Math.random()*i*2), 0, SpriteDatabase.getSlimeSpr() , new Collider(-.5f, -.625f, .5f, .625f), new Vector2f(), (short) 3, 10, 15));
//			GameWorld.layerMap.getStoredLayer(LayerStore.MOBS).add(new Rat(new Vector2f(2+(float)Math.random()*i*2, 2+(float)Math.random()*i*2), 0, SpriteDatabase.getRatSpr() , new Collider(-.25f, -.25f, .25f, .25f), new Vector2f(), 6, 1, 0));
//		}
	}
	
	public static void unloadDefaultEntities() {
		ent_default_toUnload = true;
	}
	
	public static void unloadEntityLayers() {
		GameWorld.layerMap.reset();
	}
	
	public static void setSlotsToSwitch(GUISlot slot1, GUISlot slot2) {
		s1 = slot1;
		s2 = slot2;
	}
	
	private static void switchSlots() {
		Vector2f pos1 = new Vector2f (s1.getSlot().getItemSprite().getPosition().x,s1.getSlot().getItemSprite().getPosition().y);
		Vector2f pos2 = new Vector2f (s2.getSlot().getItemSprite().getPosition().x,s2.getSlot().getItemSprite().getPosition().y);
		
		Item item = s1.getSlot().getItem();
		GUISlot sprite = s1.getSlot().getItemSprite();
		GUIText text = s1.getSlot().getQuantity();
		
		s1.getSlot().setSlot(s2.getSlot());
		
		s2.getSlot().setSlot(item, sprite, text);
		
		s1.getSlot().getItemSprite().setPosition(pos1);
		s2.getSlot().getItemSprite().setPosition(pos2);
	}
}
