package fr.iutvalence.info.dut.m2107.inventory;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.fontMeshCreator.GUIText;
import fr.iutvalence.info.dut.m2107.gui.GUIMovable;
import fr.iutvalence.info.dut.m2107.gui.GUISprite;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;

public class InventorySlot {

	private Item item;
	private GUIMovable itemSprite;
	private GUIText quantity;
	
	public InventorySlot() {
		this.item = null;
		this.itemSprite = null;
		this.quantity = null;
	}
	
	public InventorySlot(Item item, Vector2f pos) {
		this.item = item;
		this.itemSprite = new GUIMovable(new GUISprite(item.getSprite().getAtlas(), item.getSprite().getSize()), pos, Inventory.width, Inventory.height);
		this.quantity = new GUIText(""+item.stack, .5f, -Inventory.width, -Inventory.width/3, Inventory.width, true);
		quantity.setParent(itemSprite);
		itemSprite.setParent(GameWorld.player.getInventory().getInventoryGUI());
	}
	
	public void prepareDisplay() {
		quantity.setParent(itemSprite);
		GameWorld.guiLayerMap.getLayer(1).add(this.itemSprite);
	}
	
	public void empty() {
		GameWorld.player.getInventory().getInventoryGUI().getLayer().remove(this.itemSprite);
		this.quantity.setParent(null);
		this.itemSprite = null;
		this.quantity = null;
		this.item = null;
	}

	public GUIText getQuantity() {return quantity;}
	public void setQuantity(GUIText quantity) {this.quantity = quantity;}

	public Item getItem() {return item;}
	public void setItem(Item item) {this.item = item;}

	public GUIMovable getItemSprite() {return itemSprite;}
	public void setItemSprite(GUIMovable itemSprite) {this.itemSprite = itemSprite;}
}
