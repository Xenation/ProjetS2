package fr.iutvalence.info.dut.m2107.listeners;

import java.util.ArrayList;
import java.util.List;

import fr.iutvalence.info.dut.m2107.entities.Entity;
import fr.iutvalence.info.dut.m2107.events.EventManager;
import fr.iutvalence.info.dut.m2107.events.GUIMouseEnteredEvent;
import fr.iutvalence.info.dut.m2107.events.GUIMouseLeavedEvent;
import fr.iutvalence.info.dut.m2107.events.Listener;
import fr.iutvalence.info.dut.m2107.gui.GUIElement;
import fr.iutvalence.info.dut.m2107.models.Atlas;
import fr.iutvalence.info.dut.m2107.storage.Input;

public class GUIListener implements Listener {
	
	/**
	 * Records the number of sub layers that are touched by the Mouse.
	 */
	private int guiSubLayersCounter = 0;
	
	/**
	 * The Elements touched by the mouse
	 */
	private List<GUIElement> elements = new ArrayList<GUIElement>();
	
	public void getGUIMouseEntered(GUIMouseEnteredEvent event) {
		this.guiSubLayersCounter++;
		elements.add(event.getElement());
		updateCounter();
	}
	
	public void getGUIMouseLeaved(GUIMouseLeavedEvent event) {
		this.guiSubLayersCounter--;
		elements.remove(event.getElement());
		if (event.getElement().getLayer() != null) {
			detectSubLayersLeave(event.getElement());
		}
		updateCounter();
	}
	
	public void detectSubLayersLeave(GUIElement elem) {
		for (Atlas atl : elem.getLayer().atlases()) {
			for (Entity ent : elem.getLayer().getEntities(atl)) {
				if (ent instanceof GUIElement) {
					GUIElement e = (GUIElement) ent;
					if (e.isMouseHover()) {
						e.setMouseHover(false);
						EventManager.sendEvent(new GUIMouseLeavedEvent(e));
					}
				}
			}
		}
	}
	
	public void updateCounter() {
		if (guiSubLayersCounter == 0) {
			Input.isOverGUI = false;
		} else {
			Input.isOverGUI = true;
		}
	}
	
	public void resetCounter() {
		guiSubLayersCounter = 0;
	}
	
	public List<GUIElement> hoveredElements() {
		return elements;
	}
	
}
