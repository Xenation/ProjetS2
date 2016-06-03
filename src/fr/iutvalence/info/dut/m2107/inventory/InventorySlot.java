package fr.iutvalence.info.dut.m2107.inventory;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.fontMeshCreator.GUIText;
import fr.iutvalence.info.dut.m2107.gui.GUIMovable;
import fr.iutvalence.info.dut.m2107.gui.GUISlot;
import fr.iutvalence.info.dut.m2107.gui.GUISprite;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;

public class InventorySlot {

	private Item item;
	private GUISlot itemSprite;
	private GUIText quantity;
	
	public InventorySlot() {
		this.item = null;
		this.itemSprite = null;
		this.quantity = null;
	}
	
	public InventorySlot(Item item, Vector2f pos) {
		this.item = item;
		this.itemSprite = new GUISlot(pos, Inventory.width, Inventory.height, this);
		this.itemSprite.setItem(new GUIMovable(new GUISprite(item.getSprite().getAtlas(), item.getSprite().getSize()), new Vector2f(0, 0), Inventory.width, Inventory.height));
		this.quantity = new GUIText(""+item.stack, .5f, -Inventory.width, -Inventory.width/3, Inventory.width, true);
		quantity.setParent(itemSprite);
		itemSprite.setParent(GameWorld.player.getInventory().getInventoryGUI());
	}
	
	public void prepareDisplay() {
		quantity.setParent(itemSprite);
	}
	
	public void empty() {
		GameWorld.player.getInventory().getInventoryGUI().getLayer().remove(this.itemSprite);
		this.quantity.setParent(null);
		this.itemSprite = null;
		this.quantity = null;
		this.item = null;
	}
	
	public void setSlot(InventorySlot slot) {
		this.item = slot.item;
		this.itemSprite = slot.itemSprite;
		this.quantity = slot.quantity;
	}
	
	public void setSlot(Item item, GUISlot sprite, GUIText text) {
		this.item = item;
		this.itemSprite = sprite;
		this.quantity = text;
	}

	public GUIText getQuantity() {return quantity;}
	public void setQuantity(GUIText quantity) {this.quantity = quantity;}

	public Item getItem() {return item;}
	public void setItem(Item item) {this.item = item;}

	public GUISlot getItemSprite() {return itemSprite;}
	public void setItemSprite(GUISlot itemSprite) {this.itemSprite = itemSprite;}

	@Override
	public String toString() {
		return "InventorySlot [item=" + item + ", itemSprite=" + itemSprite.getPosition() + ", quantity=" + quantity.getPosition() + "]";
	}
	
	
}
