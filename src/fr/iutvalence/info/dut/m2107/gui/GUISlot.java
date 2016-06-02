package fr.iutvalence.info.dut.m2107.gui;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.core.GameManager;
import fr.iutvalence.info.dut.m2107.entities.SpriteDatabase;
import fr.iutvalence.info.dut.m2107.events.GUIDragDroppedEvent;
import fr.iutvalence.info.dut.m2107.events.Listener;
import fr.iutvalence.info.dut.m2107.inventory.InventorySlot;

public class GUISlot extends GUIElement implements Listener {
	
	private InventorySlot slot;
	
	private GUIMovable item;
	
	public GUISlot(Vector2f pos, float width, float height, InventorySlot slot) {
		super(SpriteDatabase.getEmptySpr(), pos, width, height);
		this.slot = slot;
		this.initLayer();
		this.item = null;
	}
	
	public void setItem(GUIMovable i) {
		if (item != null) {
			item.unregisterListener(this);
		}
		this.item = i;
		if (item != null) {
			item.setParent(this);
			item.setPosition(0, 0);
			item.registerListener(this);
		}
	}
	
	public GUIMovable getItem() {
		return item;
	}
	
	public InventorySlot getSlot() {
		return slot;
	}
	
	public void onGUIDragDropped(GUIDragDroppedEvent event) {
		for (GUIElement elem : GameManager.getGUIListener().hoveredElements()) {
			if (elem instanceof GUISlot) {
				GameManager.setSlotsToSwitch((GUISlot) elem, this);
			}
		}
		item.setPosition(0, 0);
	}
	
}
