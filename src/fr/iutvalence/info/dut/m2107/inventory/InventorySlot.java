package fr.iutvalence.info.dut.m2107.inventory;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.fontMeshCreator.GUIText;
import fr.iutvalence.info.dut.m2107.gui.GUIElement;
import fr.iutvalence.info.dut.m2107.gui.GUIMovable;
import fr.iutvalence.info.dut.m2107.gui.GUISprite;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;

public class InventorySlot implements Comparable {

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
		itemSprite.initLayer();
		itemSprite.getLayer().add(quantity);
		GameWorld.guiLayerMap.getLayer(1).add(itemSprite);
	}
	

	public GUIText getQuantity() {return quantity;}
	public void setQuantity(GUIText quantity) {this.quantity = quantity;}

	public Item getItem() {return item;}
	public void setItem(Item item) {this.item = item;}

	public GUIMovable getItemSprite() {return itemSprite;}
	public void setItemSprite(GUIMovable itemSprite) {this.itemSprite = itemSprite;}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		result = prime * result + ((itemSprite == null) ? 0 : itemSprite.hashCode());
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
		return result;
	}

	@Override
	public int compareTo(Object arg0) {
		return (this.getItem().id - ((InventorySlot)arg0).getItem().id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InventorySlot other = (InventorySlot) obj;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		if (itemSprite == null) {
			if (other.itemSprite != null)
				return false;
		} else if (!itemSprite.equals(other.itemSprite))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		return true;
	}

	
	
	
}
