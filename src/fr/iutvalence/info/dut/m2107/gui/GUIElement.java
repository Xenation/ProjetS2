package fr.iutvalence.info.dut.m2107.gui;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.entities.Entity;
import fr.iutvalence.info.dut.m2107.models.AbstractSprite;
import fr.iutvalence.info.dut.m2107.render.DisplayManager;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;

public class GUIElement extends Entity {
	
	public GUIElement(String fileName, Vector2f pos, float width, float height) {
		super(pos, new GUISprite(fileName, new Vector2f(1, 1)));
		this.scale.x = width;
		this.scale.y = height*DisplayManager.aspectRatio;
		GameWorld.guiLayerMap.getLayer(0).add(this);
	}
	
	public GUIElement(AbstractSprite spr, Vector2f pos, float width, float height) {
		super(pos, spr);
		this.scale.x = width;
		this.scale.y = height*DisplayManager.aspectRatio;
		GameWorld.guiLayerMap.getLayer(0).add(this);
	}
	
	public GUIElement(AbstractSprite spr, Vector2f pos) {
		super(pos, spr);
		this.scale.x = spr.getSize().x;
		this.scale.y = spr.getSize().y*DisplayManager.aspectRatio;
		GameWorld.guiLayerMap.getLayer(0).add(this);
	}
	
	public void remove() {
		GameWorld.guiLayerMap.getLayer(0).remove(this);
	}
	
	public void setPositionY(float y) {
		this.pos.y = y;
	}
	
}
