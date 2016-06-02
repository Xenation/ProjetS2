package fr.iutvalence.info.dut.m2107.gui;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.models.AbstractSprite;
import fr.iutvalence.info.dut.m2107.render.DisplayManager;
import fr.iutvalence.info.dut.m2107.render.Loader;

public class GUIButtonIcon extends GUIButton {
	
	private GUIElement iconElem;
	
	public GUIButtonIcon(AbstractSprite spr, Vector2f pos, float width, float height, AbstractSprite icon) {
		super(spr, pos, width, height, "");
		if (icon != null) {
			this.iconElem = new GUIElement(icon, new Vector2f(0, 0), height/1.5f, height/1.5f);
			this.getLayer().add(iconElem);
		} else {
			this.iconElem = null;
		}
	}
	
	public void setIcon(AbstractSprite icon) {
		if (iconElem != null) {
			this.getLayer().remove(iconElem);
			Loader.GUI_LOADER.unloadVAO(iconElem.getSprite().getVaoID());
		}
		if (icon == null) {
			this.iconElem = null;
			return;
		}
		iconElem = new GUIElement(icon, new Vector2f(0, 0), scale.x/1.5f, (scale.y/DisplayManager.aspectRatio)/1.5f);
		this.getLayer().add(iconElem);
	}

}
