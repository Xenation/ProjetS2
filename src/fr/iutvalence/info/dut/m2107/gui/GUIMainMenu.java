package fr.iutvalence.info.dut.m2107.gui;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.core.GameManager;
import fr.iutvalence.info.dut.m2107.events.GUIMouseLeftDownEvent;
import fr.iutvalence.info.dut.m2107.events.Listener;
import fr.iutvalence.info.dut.m2107.fontMeshCreator.GUIText;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;

/**
 * The GUI for the main menu
 * @author Xenation
 *
 */
public class GUIMainMenu implements Listener {
	
	/**
	 * the title 
	 */
	private GUIText text_title;
	
	/**
	 * the play button
	 */
	private GUIButton btn_play;
	/**
	 * the credits button
	 */
	private GUIButton btn_credits;
	/**
	 * the quit button
	 */
	private GUIButton btn_quit;
	
	/**
	 * Whether the main menu is currently loaded
	 */
	private boolean isLoaded;
	
	/**
	 * Creates all the GUIElements needed for the main menu
	 */
	public GUIMainMenu() {
		this.text_title = new GUIText("\nEAGL GAME", 3, -1, 1, 1, true);
		this.btn_play = new GUIButton(new GUISprite("gui/quick_bar_slot", new Vector2f(1, 1)), new Vector2f(0, .35f), .5f, .2f, "PLAY");
		this.btn_play.registerListener(this);
		this.btn_credits = new GUIButton(new GUISprite("gui/quick_bar_slot", new Vector2f(1, 1)), new Vector2f(0, -.125f), .5f, .2f, "CREDITS");
		this.btn_credits.registerListener(this);
		this.btn_quit = new GUIButton(new GUISprite("gui/quick_bar_slot", new Vector2f(1, 1)), new Vector2f(0, -.6f), .5f, .2f, "QUIT");
		this.btn_quit.registerListener(this);
	}
	
	/**
	 * Called when the left mouse button is downed on one of the buttons. DO NOT CALL.<br>
	 * This method is called automatically it doesn't need to be called.
	 * @param event the event
	 */
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
	
	/**
	 * Loads all the elements in the GUI layer map.
	 */
	public void loadGUIElement() {
		GUIMaster.addText(text_title, 1);
		GameWorld.guiLayerMap.getLayer(2).add(text_title);
		GameWorld.guiLayerMap.getLayer(2).add(btn_play);
		GameWorld.guiLayerMap.getLayer(2).add(btn_credits);
		GameWorld.guiLayerMap.getLayer(2).add(btn_quit);
		isLoaded = true;
	}
	
	/**
	 * Unloads all the elements from the GUI layer map.
	 */
	public void unloadGUIElement() {
		GUIMaster.removeFromLayer(text_title);
		GameWorld.guiLayerMap.getLayer(2).remove(text_title);
		GameWorld.guiLayerMap.getLayer(2).remove(btn_play);
		GameWorld.guiLayerMap.getLayer(2).remove(btn_credits);
		GameWorld.guiLayerMap.getLayer(2).remove(btn_quit);
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
