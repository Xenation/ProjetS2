package fr.iutvalence.info.dut.m2107.events;

import fr.iutvalence.info.dut.m2107.gui.GUIElement;

public abstract class GUIEvent extends Event {
	
	private GUIElement element;
	
	public GUIEvent(GUIElement elem) {
		this.element = elem;
	}
	
	public GUIElement getElement() {
		return this.element;
	}
	
}
