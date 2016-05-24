package fr.iutvalence.info.dut.m2107.inventory;

import fr.iutvalence.info.dut.m2107.fontMeshCreator.GUIText;
import fr.iutvalence.info.dut.m2107.gui.GUIElement;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;

public class InventorySlot implements Comparable{

	private Item item;
	private GUIElement background;
	private GUIElement itemSprite;
	private GUIText quantity;
	
	public InventorySlot() {
		this.item = null;
		this.background = null;
		this.itemSprite = null;
		this.quantity = null;
	}
	
	public InventorySlot(Item item, GUIElement background, GUIElement itemSpr, GUIText quantity) {
		this.item = item;
		this.background = background;
		this.itemSprite = itemSpr;
		this.quantity = quantity;
	}
	
	public void display() {
		GameWorld.guiLayerMap.getLayer(1).add(this.background);
		
		if(this.background.getLayer() == null) {
			this.background.initLayer();
			if(this.itemSprite != null) this.background.getLayer().add(this.itemSprite);
			if(this.quantity != null) this.background.getLayer().add(this.quantity);
		}
		
		//if(this.itemSprite != null) GameWorld.guiLayerMap.getLayer(2).add(this.itemSprite);
		//if(this.quantity != null) GameWorld.guiLayerMap.getLayer(1).add(this.quantity);
	}
	
	public void hide() {
		GameWorld.guiLayerMap.getLayer(1).remove(this.background);
		//GameWorld.guiLayerMap.getLayer(2).remove(this.itemSprite);
		//GameWorld.guiLayerMap.getLayer(1).remove(this.quantity);
	}
	
	

	public GUIElement getBackground() {return background;}
	public void setBackground(GUIElement background) {this.background = background;}

	public GUIText getQuantity() {return quantity;}
	public void setQuantity(GUIText quantity) {this.quantity = quantity;}

	public Item getItem() {return item;}
	public void setItem(Item item) {this.item = item;}

	public GUIElement getItemSprite() {return itemSprite;}
	public void setItemSprite(GUIElement itemSprite) {this.itemSprite = itemSprite;}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((background == null) ? 0 : background.hashCode());
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
		if (background == null) {
			if (other.background != null)
				return false;
		} else if (!background.equals(other.background))
			return false;
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
