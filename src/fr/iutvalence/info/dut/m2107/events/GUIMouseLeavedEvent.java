package fr.iutvalence.info.dut.m2107.events;

import java.lang.reflect.Method;
import java.util.Map;

import fr.iutvalence.info.dut.m2107.gui.GUIElement;

public class GUIMouseLeavedEvent extends GUIEvent {
	
	/**
	 * The handling classes and the method that handles this event
	 */
	public static Map<Class<?>, Method> handlerClasses;
	
	/**
	 * The instances that handles this event
	 */
	public static HandlerList handlers = new HandlerList();
	
	/**
	 * Initialises the handling classes
	 */
	public static void init() {
		handlerClasses = ListenersScanner.getHandlers(GUIMouseLeavedEvent.class);
	}
	
	public GUIMouseLeavedEvent(GUIElement elem) {
		super(elem);
	}
	
}
