package fr.iutvalence.info.dut.m2107.gui;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.fontMeshCreator.GUIText;
import fr.iutvalence.info.dut.m2107.models.AbstractSprite;

/**
 * A Label with a background and some centred text
 * @author Xenation
 *
 */
public class GUILabel extends GUIElement {
	
	/**
	 * The GUIText that contains the text
	 */
	private GUIText textElem;
	
	/**
	 * A GUILabel using the specified sprite, position, width, height and text
	 * @param spr the sprite to use as background
	 * @param pos the position
	 * @param width the width
	 * @param height the height
	 * @param text the text to be displayed on this element
	 */
	public GUILabel(AbstractSprite spr, Vector2f pos, float width, float height, String text) {
		super(spr, pos, width, height);
		textElem = new GUIText(text, 1, 0, 0, 1, false);
		textElem.setPosition(-textElem.getWidth()/2, textElem.getHeight()/2);
		this.initLayer();
		this.getLayer().add(textElem);
	}
	
}
