package fr.iutvalence.info.dut.m2107.gui;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.events.GUIDragDroppedEvent;
import fr.iutvalence.info.dut.m2107.models.AbstractSprite;
import fr.iutvalence.info.dut.m2107.storage.Input;
import fr.iutvalence.info.dut.m2107.storage.Layer;

public class GUIMovable extends GUIElement {
	
	private Vector2f offset;
	private boolean bound;
	
	public GUIMovable(AbstractSprite spr, Vector2f pos, float width, float height) {
		super(spr, pos, width, height);
	}
	
	public void update(Layer layer) {
		super.update(layer);
		if (bound && Input.isMouseLeftUp()) {
			bound = false;
			Input.isDragingGUI = false;
			sendPreciseEvent(new GUIDragDroppedEvent(this));
		}
		if ((leftClicked && !Input.isDragingGUI) || bound) {
			if (!bound) {
				bound = true;
				Input.isDragingGUI = true;
			}
			if (offset == null) {
				offset = new Vector2f(Input.getMouseGUIPosX() - this.pos.x, Input.getMouseGUIPosY() - this.pos.y);
			}
			this.pos.x = Input.getMouseGUIPosX() - offset.x;
			this.pos.y = Input.getMouseGUIPosY() - offset.y;
		} else if (offset != null) {
			offset = null;
		}
	}
	
	public boolean isBound() {
		return bound;
	}
	
}
