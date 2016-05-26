package fr.iutvalence.info.dut.m2107.gui;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.entities.Entity;
import fr.iutvalence.info.dut.m2107.events.GUIMouseEnteredEvent;
import fr.iutvalence.info.dut.m2107.events.GUIMouseLeavedEvent;
import fr.iutvalence.info.dut.m2107.events.GUIMouseLeftDownEvent;
import fr.iutvalence.info.dut.m2107.events.GUIMouseLeftUpEvent;
import fr.iutvalence.info.dut.m2107.events.GUIMouseRightDownEvent;
import fr.iutvalence.info.dut.m2107.events.GUIMouseRightUpEvent;
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
	
	/**
	 * Whether the mouse is over this element.
	 */
	protected boolean mouseHover;
	
	/**
	 * Whether this element is being clicked on.
	 */
	protected boolean leftClicked;

	/**
	 * Whether this element is being clicked on.
	 */
	protected boolean rightClicked;
	
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
		if (this.layer != null) {
			((GUILayer)this.layer).update();
		}
		
		float mX = Input.getMouseGUIPosX();
		float mY = Input.getMouseGUIPosY();
		
		// Collision Detection
		if (mX >= getScreenX() - this.scale.x/2
				&& mX <= getScreenX() + this.scale.x/2
				&& mY >= getScreenY() - this.scale.y/2
				&& mY <= getScreenY() + this.scale.y/2) {
			
			// Hover Start
			if (!mouseHover) {
				sendPreciseEvent(new GUIMouseEnteredEvent(this));
				Input.isOverGUI = true;
				mouseHover = true;
			}
			// Mouse Left click
			if (Input.isMouseLeftDown()) {
				if (!leftClicked) {
					sendPreciseEvent(new GUIMouseLeftDownEvent(this));
					leftClicked = true;
				}
			} else if (leftClicked) {
				sendPreciseEvent(new GUIMouseLeftUpEvent(this));
				leftClicked = false;
			}
			// Mouse Right click
			if (Input.isMouseRightDown()) {
				if (!rightClicked) {
					sendPreciseEvent(new GUIMouseRightDownEvent(this));
					rightClicked = true;
				}
			} else if (rightClicked) {
				sendPreciseEvent(new GUIMouseRightUpEvent(this));
				rightClicked = false;
			}
		} else if (mouseHover) { // Hover End
			// Mouse Left End
			if (leftClicked) {
				sendPreciseEvent(new GUIMouseLeftUpEvent(this));
				leftClicked = false;
			}
			// Mouse Right End
			if (rightClicked) {
				sendPreciseEvent(new GUIMouseRightUpEvent(this));
				rightClicked = false;
			}
			sendPreciseEvent(new GUIMouseLeavedEvent(this));
			Input.isOverGUI = false;
			mouseHover = false;
		}
	}

	/**
	 * Returns <tt>true</tt> if this element is being clicked on (left), <tt>false</tt> otherwise.
	 * @return <tt>true</tt> if this element is being clicked on (left), <tt>false</tt> otherwise.
	 */
	public boolean isLeftClicked() {
		return leftClicked;
	}
	
	/**
	 * Returns <tt>true</tt> if this element is being clicked on (right), <tt>false</tt> otherwise.
	 * @return <tt>true</tt> if this element is being clicked on (right), <tt>false</tt> otherwise.
	 */
	public boolean isRightClicked() {
		return rightClicked;
	}
	
	/**
	 * Removes this element from layers using the GUIMaster
	 */
	public void remove() {
		GUIMaster.removeElement(this);
	}
	
	/**
	 * Returns the x position on the screen
	 * @return the x position on the screen
	 */
	public float getScreenX() {
		GUIElement curPar = (GUIElement) this.getParent();
		float screenX = this.pos.x;
		while (curPar != null) {
			screenX += curPar.pos.x;
			curPar = (GUIElement) curPar.getParent();
		}
		return screenX;
	}
	
	/**
	 * Returns the y position on the screen
	 * @return the y position on the screen
	 */
	public float getScreenY() {
		GUIElement curPar = (GUIElement) this.getParent();
		float screenY = this.pos.y;
		while (curPar != null) {
			screenY += curPar.pos.y;
			curPar = (GUIElement) curPar.getParent();
		}
		return screenY;
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
	
}
