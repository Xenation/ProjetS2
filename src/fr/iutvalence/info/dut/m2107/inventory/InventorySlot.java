package fr.iutvalence.info.dut.m2107.inventory;

import fr.iutvalence.info.dut.m2107.fontMeshCreator.GUIText;
import fr.iutvalence.info.dut.m2107.gui.GUIElement;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Layer;

public class InventorySlot {

	private Item item;
	private GUIElement background;
	private GUIElement itemSprite;
	private GUIText quantity;
	
	private Layer layer;
	
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
	
	public void display(Layer layer) {
		this.layer = layer;
		this.layer.add(this.background);
		
		if(this.background.getLayer() == null) {
			this.background.initLayer();
			if(this.itemSprite != null) {
				this.itemSprite.initLayer();
				this.background.getLayer().add(this.itemSprite);
			}
			if(this.quantity != null) this.itemSprite.getLayer().add(this.quantity);
		}
	}
	
	public void display(int index) {
		this.layer = GameWorld.guiLayerMap.getLayer(index);
		this.layer.add(this.background);
		
		if(this.background.getLayer() == null) {
			this.background.initLayer();
			if(this.itemSprite != null) {
				this.itemSprite.initLayer();
				this.background.getLayer().add(this.itemSprite);
			}
			if(this.quantity != null) this.itemSprite.getLayer().add(this.quantity);
		}
	}
	
	public void empty() {
		this.background.getLayer().remove(this.itemSprite);
		this.itemSprite.getLayer().remove(this.quantity);
		this.itemSprite = null;
		this.quantity = null;
		this.item = null;
	}
	

	public GUIElement getBackground() {return background;}
	public void setBackground(GUIElement background) {this.background = background;}

	public GUIText getQuantity() {return quantity;}
	public void setQuantity(GUIText quantity) {this.quantity = quantity;}

	public Item getItem() {return item;}
	public void setItem(Item item) {this.item = item;}

	public GUIElement getItemSprite() {return itemSprite;}
	public void setItemSprite(GUIElement itemSprite) {this.itemSprite = itemSprite;}
}
