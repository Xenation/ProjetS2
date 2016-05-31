package fr.iutvalence.info.dut.m2107.gui;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.core.GameManager;
import fr.iutvalence.info.dut.m2107.events.GUIMouseLeftDownEvent;
import fr.iutvalence.info.dut.m2107.events.Listener;
import fr.iutvalence.info.dut.m2107.fontMeshCreator.GUIText;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;

public class GUIMainMenu implements Listener {
	
	private GUIText text_title;
	
	private GUIButton btn_play;
	private GUIButton btn_credits;
	private GUIButton btn_quit;
	
	private boolean isLoaded;
	
	public GUIMainMenu() {
		this.text_title = new GUIText("\nEAGL GAME", 3, -1, 1, 1, true);
		this.btn_play = new GUIButton(new GUISprite("gui/quick_bar_slot", new Vector2f(1, 1)), new Vector2f(0, .35f), .5f, .2f, "PLAY");
		this.btn_play.registerListener(this);
		this.btn_credits = new GUIButton(new GUISprite("gui/quick_bar_slot", new Vector2f(1, 1)), new Vector2f(0, -.125f), .5f, .2f, "CREDITS");
		this.btn_credits.registerListener(this);
		this.btn_quit = new GUIButton(new GUISprite("gui/quick_bar_slot", new Vector2f(1, 1)), new Vector2f(0, -.6f), .5f, .2f, "QUIT");
		this.btn_quit.registerListener(this);
	}
	
	public void onGUIMouseLeftDown(GUIMouseLeftDownEvent event) {
		GUIElement elem = event.getElement();
		if (elem == btn_quit) {
			GameManager.isQuitting = true;
		} else if (elem == btn_play) {
			GameManager.unloadMainMenu();
			GameManager.loadGUI();
			GameManager.loadDefaultChunkMap();
			GameManager.loadDefaultEntities();
		}
	}
	
	public void loadGUIElement() {
		GUIMaster.addText(text_title, 1);
		GameWorld.guiLayerMap.getLayer(2).add(text_title);
		GameWorld.guiLayerMap.getLayer(2).add(btn_play);
		GameWorld.guiLayerMap.getLayer(2).add(btn_credits);
		GameWorld.guiLayerMap.getLayer(2).add(btn_quit);
		isLoaded = true;
	}
	
	public void unloadGUIElement() {
		GUIMaster.removeFromLayer(text_title);
		GameWorld.guiLayerMap.getLayer(2).remove(text_title);
		GameWorld.guiLayerMap.getLayer(2).remove(btn_play);
		GameWorld.guiLayerMap.getLayer(2).remove(btn_credits);
		GameWorld.guiLayerMap.getLayer(2).remove(btn_quit);
		isLoaded = false;
	}
	
	public boolean isLoaded() {
		return isLoaded;
	}
	
}
