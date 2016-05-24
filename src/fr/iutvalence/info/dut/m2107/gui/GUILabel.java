package fr.iutvalence.info.dut.m2107.gui;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.fontMeshCreator.GUIText;
import fr.iutvalence.info.dut.m2107.models.AbstractSprite;

public class GUILabel extends GUIElement {
	
	private GUIText textElem;
	
	public GUILabel(AbstractSprite spr, Vector2f pos, float width, float height, String text) {
		super(spr, pos, width, height);
		textElem = new GUIText(text, 1, 0, 0, 1, false);
		textElem.setPosition(-textElem.getWidth()/2, textElem.getHeight()/2);
		this.initLayer();
		this.getLayer().add(textElem);
	}
	
}
