package fr.iutvalence.info.dut.m2107.gui;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.entities.Entity;
import fr.iutvalence.info.dut.m2107.events.GUIMouseEnteredEvent;
import fr.iutvalence.info.dut.m2107.events.GUIMouseLeavedEvent;
import fr.iutvalence.info.dut.m2107.events.GUIMouseLeftDownEvent;
import fr.iutvalence.info.dut.m2107.events.GUIMouseLeftUpEvent;
import fr.iutvalence.info.dut.m2107.events.Sender;
import fr.iutvalence.info.dut.m2107.models.AbstractSprite;
import fr.iutvalence.info.dut.m2107.render.DisplayManager;
import fr.iutvalence.info.dut.m2107.storage.GUILayer;
import fr.iutvalence.info.dut.m2107.storage.Input;
import fr.iutvalence.info.dut.m2107.storage.Layer;

/**
 * A Basic GUI Element
 * @author Xenation
 *
 */
public class GUIElement extends Entity implements Sender {
	
	protected boolean mouseHover;
	
	protected boolean clicked;
	
	/**
	 * A GUIElement using the given file, position, width, and height
	 * @param fileName the name of the texture file
	 * @param pos the position
	 * @param width the width
	 * @param height the height
	 */
	public GUIElement(String fileName, Vector2f pos, float width, float height) {
		super(pos, new GUISprite(fileName, new Vector2f(1, 1)));
		this.scale.x = width;
		this.scale.y = height*DisplayManager.aspectRatio;
	}
	
	/**
	 * A GUIElement using the given sprite, position, width, and height
	 * @param spr the sprite to use
	 * @param pos the position
	 * @param width the width
	 * @param height the height
	 */
	public GUIElement(AbstractSprite spr, Vector2f pos, float width, float height) {
		super(pos, spr);
		this.scale.x = width;
		this.scale.y = height*DisplayManager.aspectRatio;
	}
	
	public void update(Layer layer) {
		float mX = Input.getMouseGUIPosX();
		float mY = Input.getMouseGUIPosY();
		
		if (mX >= this.pos.x - this.scale.x/2
				&& mX <= this.pos.x + this.scale.x/2
				&& mY >= this.pos.y - this.scale.y/2
				&& mY <= this.pos.y + this.scale.y/2) {
			if (!mouseHover) {
				sendPreciseEvent(new GUIMouseEnteredEvent(this));
				Input.isOverGUI = true;
				mouseHover = true;
			}
			if (Input.isMouseLeftDown()) {
				if (!clicked) {
					sendPreciseEvent(new GUIMouseLeftDownEvent(this));
					clicked = true;
				}
			} else if (clicked) {
				sendPreciseEvent(new GUIMouseLeftUpEvent(this));
				clicked = false;
			}
		} else if (mouseHover) {
			if (clicked) {
				sendPreciseEvent(new GUIMouseLeftUpEvent(this));
				clicked = false;
			}
			sendPreciseEvent(new GUIMouseLeavedEvent(this));
			Input.isOverGUI = false;
			mouseHover = false;
		}
	}
	
	/**
	 * Removes this element from layers using the GUIMaster
	 */
	public void remove() {
		GUIMaster.removeElement(this);
	}
	
	/**
	 * Sets the Y coordinate
	 * @param y the new Y coordinate
	 */
	public void setPositionY(float y) {
		this.pos.y = y;
	}
	
	/**
	 * Sets the X coordinate
	 * @param x the new X coordinate
	 */
	public void setPositionX(float x) {
		this.pos.x = x;
	}
	
	@Override
	public void initLayer() {
		this.layer = new GUILayer();
	}
	
	public GUILayer getLayer() {
		return (GUILayer) this.layer;
	}
	
	public boolean isClicked() {
		return clicked;
	}
	
}
