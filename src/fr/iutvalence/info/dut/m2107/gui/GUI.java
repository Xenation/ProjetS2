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
import fr.iutvalence.info.dut.m2107.tiles.TileBuilder;
import fr.iutvalence.info.dut.m2107.toolbox.Maths;

public class GUI implements Listener {
	
	private GUIText chunkStatsLabel;
	private GUIText chunkStats;
	private GUIText loaderStatsLabel;
	private GUIText loaderStats;
	private GUIText cameraStats;
	
	private GUIButton btn_debug;
	private GUIButton btn_save;
	private GUIButton btn_load;
	
	private boolean debugOn;
	
	public GUI() {
		this.btn_debug = new GUIButton(new GUISprite("gui/quick_bar_slot", new Vector2f(1, 1)), new Vector2f(-.85f, .9f), .2f, .05f, "Debug");
		this.btn_save = new GUIButton(new GUISprite("gui/quick_bar_slot", new Vector2f(1, 1)), new Vector2f(-.85f, .8f), .2f, .05f, "Save");
		this.btn_load = new GUIButton(new GUISprite("gui/quick_bar_slot", new Vector2f(1, 1)), new Vector2f(-.85f, .7f), .2f, .05f, "Load");
		GameWorld.guiLayerMap.getLayer(1).add(btn_debug);
		GameWorld.guiLayerMap.getLayer(1).add(btn_save);
		GameWorld.guiLayerMap.getLayer(1).add(btn_load);
		btn_debug.registerListener(this);
		btn_save.registerListener(this);
		btn_load.registerListener(this);
		
		// Debug Texts
		chunkStatsLabel = new GUIText("Chunks :", 1, -1, -.60f, 0.5f, false, true);
		chunkStats = new GUIText("", .8f, -1, -.65f, 0.5f, false, true);
		chunkStats.setLineHeight(0.024);
		
		loaderStatsLabel = new GUIText("Loaders :", 1, -1, -.80f, 0.5f, false, true);
		loaderStats = new GUIText("", .8f, -1, -.85f, 0.5f, false, true);
		loaderStats.setLineHeight(0.024);
		
		cameraStats = new GUIText("", .8f, -1, 1, .5f, false, true);
		
	}
	
	public void onGUIMouseLeftDown(GUIMouseLeftDownEvent event) {
		GUIElement btn = event.getElement();
		if (btn == btn_save) {
			WorldSaver.writeWorld();
		} else if (btn == btn_load) {
			GameWorld.chunkMap.clear();
			WorldLoader.loadWorld();
		} else if (btn == btn_debug) {
			if (debugOn)
				hideDebugTexts();
			else
				showDebugTexts();
		}
	}
	
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
		}
	}
	
	public void hideDebugTexts() {
		debugOn = false;
		GUIMaster.removeFromLayer(chunkStatsLabel);
		GUIMaster.removeFromLayer(chunkStats);
		GUIMaster.removeFromLayer(loaderStatsLabel);
		GUIMaster.removeFromLayer(loaderStats);
		GUIMaster.removeFromLayer(cameraStats);
	}
	
	public void showDebugTexts() {
		debugOn = true;
		GUIMaster.addText(chunkStatsLabel);
		GUIMaster.addText(chunkStats);
		GUIMaster.addText(loaderStatsLabel);
		GUIMaster.addText(loaderStats);
		GUIMaster.addText(cameraStats);
	}
	
}
