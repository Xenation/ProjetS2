package fr.iutvalence.info.dut.m2107.gui;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.events.GUIMouseLeftDownEvent;
import fr.iutvalence.info.dut.m2107.events.Listener;
import fr.iutvalence.info.dut.m2107.models.AbstractSprite;

/**
 * A GUIButton simplifies the detection of clicks.
 * @author brisaca
 *
 */
public class GUIButton extends GUILabel implements Listener {
	
	/**
	 * Used to determine whether the clicked event was handled by another object
	 */
	private boolean handled;
	
	/**
	 * A GUIButton using the given sprite, position, width, and height
	 * @param spr the sprite to use
	 * @param pos the position
	 * @param width the width
	 * @param height the height
	 */
	public GUIButton(AbstractSprite spr, Vector2f pos, float width, float height, String text) {
		super(spr, pos, width, height, text);
		registerListener(this);
	}
	
	/**
	 * Returns <tt>true</tt> if the button was pressed and this function was never called, <tt>false</tt> otherwise
	 * @return <tt>true</tt> if the button was pressed and this function was never called, <tt>false</tt> otherwise
	 */
	public boolean clicked() {
		if (!handled) {
			handled = true;
			return true;
		}
		return false;
	}
	
	/**
	 * Do not call (automaticly called by Senders)
	 * @param event 
	 */
	public void onGUIMouseLeftDown(GUIMouseLeftDownEvent event) {
		handled = false;
	}
	
}
