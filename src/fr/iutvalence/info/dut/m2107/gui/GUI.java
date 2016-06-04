package fr.iutvalence.info.dut.m2107.gui;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.events.GUIMouseLeftDownEvent;
import fr.iutvalence.info.dut.m2107.events.Listener;
import fr.iutvalence.info.dut.m2107.fontMeshCreator.GUIText;
import fr.iutvalence.info.dut.m2107.render.DisplayManager;
import fr.iutvalence.info.dut.m2107.render.Loader;
import fr.iutvalence.info.dut.m2107.render.Renderer;
import fr.iutvalence.info.dut.m2107.saving.WorldLoader;
import fr.iutvalence.info.dut.m2107.saving.WorldSaver;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Input;
import fr.iutvalence.info.dut.m2107.tiles.TileBuilder;
import fr.iutvalence.info.dut.m2107.toolbox.Maths;
import fr.iutvalence.info.dut.m2107.toolbox.TrackingPrintStream;

/**
 * The in-game GUI
 * @author Xenation
 *
 */
public class GUI implements Listener {
	
	private GUITileSelect tileSelect;
	private boolean tileSelectOn;
	
	private GUIPauseMenu pauseMenu;
	private boolean pauseMenuOn;
	
	/**
	 * The chunks stats label
	 */
	private GUIText chunkStatsLabel;
	/**
	 * The chunk stats text
	 */
	private GUIText chunkStats;
	/**
	 * The loaders stats label
	 */
	private GUIText loaderStatsLabel;
	/**
	 * The loaders stats text
	 */
	private GUIText loaderStats;
	/**
	 * The camera stats
	 */
	private GUIText cameraStats;
	/**
	 * The console text
	 */
	private GUIText debugConsole;
	
	/**
	 * The debug button
	 */
	private GUIButton btn_debug;
	/**
	 * The save button
	 */
	private GUIButton btn_save;
	/**
	 * The load button
	 */
	private GUIButton btn_load;
	/**
	 * The clear button
	 */
	private GUIButton btn_clear;
	
	/**
	 * The save file name field
	 */
	private GUIField field_save;
	
	/**
	 * Whether the debug texts are displayed
	 */
	private boolean debugOn;
	/**
	 * Whether the in-game GUI elements are loaded.
	 */
	private boolean isLoaded;
	
	/**
	 * Creates all the elements needed to the in-game GUI
	 */
	public GUI() {
		this.btn_debug = new GUIButton(new GUISprite("gui/quick_bar_slot", new Vector2f(1, 1)), new Vector2f(-.85f, .9f), .2f, .05f, "Debug");
		this.btn_save = new GUIButton(new GUISprite("gui/quick_bar_slot", new Vector2f(1, 1)), new Vector2f(-.85f, .7f), .2f, .05f, "Save");
		this.btn_load = new GUIButton(new GUISprite("gui/quick_bar_slot", new Vector2f(1, 1)), new Vector2f(-.85f, .6f), .2f, .05f, "Load");
		this.btn_clear = new GUIButton(new GUISprite("gui/quick_bar_slot", new Vector2f(1, 1)), new Vector2f(-.85f, .5f), .2f, .05f, "Clear");
		btn_debug.registerListener(this);
		btn_save.registerListener(this);
		btn_load.registerListener(this);
		btn_clear.registerListener(this);
		
		// Debug Texts
		chunkStatsLabel = new GUIText("Chunks :", 1, -1, -.60f, 0.5f, false, true);
		chunkStats = new GUIText("", .8f, -1, -.65f, 0.5f, false, true);
		chunkStats.setLineHeight(0.024);
		
		loaderStatsLabel = new GUIText("Loaders :", 1, -1, -.80f, 0.5f, false, true);
		loaderStats = new GUIText("", .8f, -1, -.85f, 0.5f, false, true);
		loaderStats.setLineHeight(0.024);
		
		cameraStats = new GUIText("", .8f, -1, 1, .5f, false, true);
		
		field_save = new GUIField(new GUISprite("gui/quick_bar_slot", new Vector2f(1, 1)), new Vector2f(-.85f, .8f), .2f, .05f);
		
		debugConsole = new GUIText("", .5f, .5f, 1, .5f, false, true);
		debugConsole.setLineHeight(0.024);
		
		this.tileSelect = new GUITileSelect();
		this.pauseMenu = new GUIPauseMenu();
	}
	
	/**
	 * Called when the left mouse button is downed on one of the buttons or fields. DO NOT CALL.<br>
	 * This method is called automatically it doesn't need to be called.
	 * @param event the event
	 */
	public void onGUIMouseLeftDown(GUIMouseLeftDownEvent event) {
		GUIElement btn = event.getElement();
		if (btn == btn_save) {
			if (field_save.getEffectiveText().length() != 0) {
				WorldSaver.setFilePath("saves/"+field_save.getEffectiveText()+".eagl");
				WorldSaver.writeWorld();
			} else {
				System.out.println("ERROR: Empty Save File Name");
			}
		} else if (btn == btn_load) {
			if (field_save.getEffectiveText().length() != 0) {
				GameWorld.chunkMap.clear();
				GameWorld.backChunkMap.clear();
				WorldLoader.setFilePath("saves/"+field_save.getEffectiveText()+".eagl");
				WorldLoader.loadWorld();
			} else {
				System.out.println("ERROR: Empty Save File Name");
			}
		} else if (btn == btn_debug) {
			if (debugOn)
				hideDebugTexts();
			else
				showDebugTexts();
		} else if (btn == btn_clear) {
			GameWorld.chunkMap.clear();
			GameWorld.backChunkMap.clear();
		}
	}
	
	/**
	 * Updates the debug texts
	 */
	public void update() {
		if (debugOn) {
			chunkStats.updateText("Chunks: "+GameWorld.chunkMap.getChunkCount()
					+ "\nTiles: "+GameWorld.chunkMap.getTilesCount()
					+ "\n\tCurrent Tiles: "+GameWorld.chunkMap.getSurroundingTilesCount(-Renderer.UNITS_Y/2*DisplayManager.aspectRatio, Renderer.UNITS_Y/2*DisplayManager.aspectRatio, Renderer.UNITS_Y/2, -Renderer.UNITS_Y/2, GameWorld.camera.getPosition()));
	
			loaderStats.updateText("TILES: "+Loader.TILE_LOADER.debugValues()
					+ "\nSPRITES: "+Loader.SPRITE_LOADER.debugValues()
					+ "\nTEXT: "+Loader.TEXT_LOADER.debugValues()
					+ "\nGUI: "+Loader.GUI_LOADER.debugValues());
			
			// Camera Stats
			String updateStr = "Mouse: "+Maths.round(GameWorld.camera.getMouseWorldX(), 3)+", "+Maths.round(GameWorld.camera.getMouseWorldY(), 3) 
			+ "\nFPS: "+DisplayManager.getFPS()
			+ "\nVSync = "+DisplayManager.vSyncTracker
			+ "\nSelecting = "+GameWorld.camera.isSelecting();
			if (GameWorld.camera.getPointed() != null) {
				updateStr += "\nTile:";
				ArrayList<String> stats = TileBuilder.getStats(GameWorld.camera.getPointed());
				for (String stat : stats) {
					updateStr += "\n"+stat;
				}
			}
			cameraStats.updateText(updateStr);
			debugConsole.updateText(((TrackingPrintStream)System.out).getLastWrittenLines(60));
		}
		if (Input.isKeyU()) {
			if (!tileSelectOn) {
				tileSelectOn = true;
				tileSelect.loadGUIElements();
			} else {
				tileSelectOn = false;
				tileSelect.unloadGUIElements();
			}
		}
		if (Input.isEscape()) {
			if (tileSelectOn || GameWorld.player.getInventory().isDisplayed()) {
				if(tileSelectOn) {
					tileSelectOn = false;
					tileSelect.unloadGUIElements();
				}
				if(GameWorld.player.getInventory().isDisplayed()) {
					GameWorld.player.getInventory().changeDisplay();
				}
			} else if (pauseMenuOn) {
				pauseMenuOn = false;
				pauseMenu.unloadGUIElements();
			} else {
				pauseMenuOn = true;
				pauseMenu.loadGUIElements();
			}
		}
	}
	
	public void hideTileSelect() {
		tileSelectOn = false;
		tileSelect.unloadGUIElements();
	}
	
	public void hidePauseMenu() {
		pauseMenuOn = false;
		pauseMenu.unloadGUIElements();
	}
	
	public boolean isTileSelectOn() {
		return tileSelectOn;
	}
	
	public boolean isPauseMenuOn() {
		return pauseMenuOn;
	}
	
	/**
	 * Hides the debug texts
	 */
	public void hideDebugTexts() {
		debugOn = false;
		GUIMaster.removeText(chunkStatsLabel);
		GUIMaster.removeText(chunkStats);
		GUIMaster.removeText(loaderStatsLabel);
		GUIMaster.removeText(loaderStats);
		GUIMaster.removeText(cameraStats);
		GUIMaster.removeText(debugConsole);
	}
	
	/**
	 * Shows the debug texts
	 */
	public void showDebugTexts() {
		debugOn = true;
		GUIMaster.addText(chunkStatsLabel);
		GUIMaster.addText(chunkStats);
		GUIMaster.addText(loaderStatsLabel);
		GUIMaster.addText(loaderStats);
		GUIMaster.addText(cameraStats);
		GUIMaster.addText(debugConsole);
	}
	
	/**
	 * Loads all the GUIElements to the GUI layer map.
	 */
	public void loadGUIElements() {
//		GUIMaster.addElement(btn_debug, 1);
//		GUIMaster.addElement(btn_save, 1);
//		GUIMaster.addElement(btn_load, 1);
//		GUIMaster.addElement(btn_clear, 1);
//		GUIMaster.addElement(field_save, 1);
		GameWorld.guiLayerMap.getLayer(1).add(btn_debug);
		GameWorld.guiLayerMap.getLayer(1).add(btn_save);
		GameWorld.guiLayerMap.getLayer(1).add(btn_load);
		GameWorld.guiLayerMap.getLayer(1).add(btn_clear);
		GameWorld.guiLayerMap.getLayer(1).add(field_save);
		isLoaded = true;
	}
	
	/**
	 * Unloads all the GUIElements from the GUI layer map.
	 */
	public void unloadGUIElements() {
//		GUIMaster.removeElement(btn_debug);
//		GUIMaster.removeElement(btn_save);
//		GUIMaster.removeElement(btn_load);
//		GUIMaster.removeElement(btn_clear);
//		GUIMaster.removeElement(field_save);
		GameWorld.guiLayerMap.getLayer(1).remove(btn_debug);
		GameWorld.guiLayerMap.getLayer(1).remove(btn_save);
		GameWorld.guiLayerMap.getLayer(1).remove(btn_load);
		GameWorld.guiLayerMap.getLayer(1).remove(btn_clear);
		GameWorld.guiLayerMap.getLayer(1).remove(field_save);
		if (debugOn) {
			hideDebugTexts();
		}
		if (tileSelectOn) {
			hideTileSelect();
		}
		if (pauseMenuOn) {
			hidePauseMenu();
		}
		isLoaded = false;
	}
	
	/**
	 * Returns <tt>true</tt> if the elements are loaded into the GUI layer map, <tt>false</tt> otherwise.
	 * @return <tt>true</tt> if the elements are loaded into the GUI layer map, <tt>false</tt> otherwise.
	 */
	public boolean isLoaded() {
		return isLoaded;
	}
	
}
