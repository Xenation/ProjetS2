package fr.iutvalence.info.dut.m2107.gui;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.core.GameManager;
import fr.iutvalence.info.dut.m2107.events.GUIMouseLeftDownEvent;
import fr.iutvalence.info.dut.m2107.events.Listener;
import fr.iutvalence.info.dut.m2107.render.DisplayManager;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;

public class GUIPauseMenu implements Listener {
	
	private GUIElement background;
	
	private GUIButton btn_resume;
	private GUIButton btn_mainMenu;
	private GUIButton btn_exit;
	
	public GUIPauseMenu() {
		this.background = new GUIElement(new GUISprite("gui/quick_bar_slot", new Vector2f(1, 1)), new Vector2f(0, .2f), .3f, .4f);
		this.background.initLayer();
		btn_resume = new GUIButton(new GUISprite("gui/quick_bar_slot", new Vector2f(1, 1)), new Vector2f(0, .15f), .2f, .05f, "Resume");
		btn_mainMenu = new GUIButton(new GUISprite("gui/quick_bar_slot", new Vector2f(1, 1)), new Vector2f(0, 0), .2f, .05f, "Main Menu");
		btn_exit = new GUIButton(new GUISprite("gui/quick_bar_slot", new Vector2f(1, 1)), new Vector2f(0, -.15f), .2f, .05f, "Exit Game");
		btn_resume.setParent(background);
		btn_mainMenu.setParent(background);
		btn_exit.setParent(background);
		btn_resume.registerListener(this);
		btn_mainMenu.registerListener(this);
		btn_exit.registerListener(this);
	}
	
	public void onGUIMouseLeftDown(GUIMouseLeftDownEvent event) {
		if (event.getElement() == btn_resume) {
			GameManager.unloadGUIPauseMenu();
		} else if (event.getElement() == btn_mainMenu) {
			GameManager.unloadGUIPlayer();
			GameManager.unloadGUI();
			GameManager.unloadDefaultEntities();
			GameManager.loadMainMenu();
		} else if (event.getElement() == btn_exit) {
			GameManager.isQuitting = true;
		}
	}
	
	public void loadGUIElements() {
		GameWorld.guiLayerMap.getLayer(1).add(background);
		DisplayManager.isPaused = true;
	}
	
	public void unloadGUIElements() {
		GameWorld.guiLayerMap.getLayer(1).remove(background);
		DisplayManager.isPaused = false;
	}
	
}
