package fr.iutvalence.info.dut.m2107.inventory;

import java.util.*;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.entities.SpriteDatabase;
import fr.iutvalence.info.dut.m2107.gui.GUIButton;
import fr.iutvalence.info.dut.m2107.gui.GUIElement;
import fr.iutvalence.info.dut.m2107.gui.GUISprite;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.toolbox.Maths;

/**
 * An inventory system which contain item
 * @author boureaue
 *
 */

public class Inventory {
	
	private boolean isDisplayed = false;
	
	/**
	 * The number of inventory slot by row
	 */
	private final int inventoryWidth = 4;
	
	public static final float width = 0.075f;

	public static final float height = 0.075f;
	
	private final GUIButton exitButton = new GUIButton(new GUISprite(SpriteDatabase.getEmptySpr().getAtlas(), SpriteDatabase.getEmptySpr().getSize()), new Vector2f(0.185f, 0.75f), width/1.5f, height/1.5f, "");
	
	private final GUIElement inventoryGUI = new GUIElement(SpriteDatabase.getInventoryGUIStr(), new Vector2f(0.7f, 0), width*(inventoryWidth+1)*1.25f, height*(inventoryWidth+1)*2*1.25f);
	
	/**
	 * A list of inventory slot which contain a bunch of item and it's slot and play the role of inventory
	 */	
	private List<InventorySlot> inventorySlot = new ArrayList<InventorySlot>();
	
	public void init() {
		GameWorld.player.getInventory().getInventoryGUI().initLayer();
		
		GameWorld.player.initQuickBar();
		GameWorld.player.initInventory();
		
		this.exitButton.setParent(this.inventoryGUI);
	}
	
	/**
	 * Add an item with a certain amount into the inventory
	 * @param item The item to add to the inventory
	 * @param stack The amount of items to add to the inventory
	 * @return true if added otherwise false
	 */
	public boolean add(Item item, int stack) {
		// Stack is over the max stack
		if(stack > item.MAX_STACK)
			return false;
		
		// Search if already in inventory
		for (InventorySlot slot : inventorySlot) {
			if(item.id == slot.getItem().id) { // Found
				if(slot.getItem().stack + stack > item.MAX_STACK)
					return false; // Can't add that number of stack
				
				// Add the stack to the item and update the quantity text
				slot.getItem().changeStack(stack);
				slot.getQuantity().updateText(""+slot.getItem().stack);					
				return true;
			}
		}
		
		// Not found so item is not in the inventory
		InventorySlot lastSlot = null;
		InventorySlot newSlot;
		item.stack = stack;
		
		if(inventorySlot.isEmpty()) {
			newSlot = new InventorySlot(item, new Vector2f(-width*inventoryWidth/2+.005f, height*(inventoryWidth+3.25f)));
		} else {
			lastSlot = inventorySlot.get(inventorySlot.size()-1);
			newSlot = new InventorySlot(item, new Vector2f(lastSlot.getItemSprite().getPosition().x + width*1.3f, lastSlot.getItemSprite().getPosition().y));
			
			if(Maths.round(lastSlot.getItemSprite().getPosition().x + width, 5) >= width*inventoryWidth/2) {
				newSlot.getItemSprite().setPositionY(newSlot.getItemSprite().getPosition().y - height*2*1.135f);
				newSlot.getItemSprite().setPositionX(-width*inventoryWidth/2+.005f);
			}
		}

		newSlot.getItemSprite().setRotation(-45);
		
		float scaleMult = newSlot.getItemSprite().getSprite().getSize().x*newSlot.getItemSprite().getSprite().getSize().y;
		newSlot.getItemSprite().setScale((Vector2f)newSlot.getItemSprite().getScale().scale(1/scaleMult));	//scaleMult == 1 ? 1 : scaleMult));
		inventorySlot.add(newSlot);
		return true;
	}
	
	/**
	 * Remove an item with a certain amount from the inventory
	 * @param item The item to remove to the inventory
	 * @param stack The amount of items to remove to the inventory
	 */
	public boolean remove(Item item, int stack) {
		if(stack > item.MAX_STACK)
			return false;
		
		for (InventorySlot slot : inventorySlot) {
			if(slot.getItem() != null && item.id == slot.getItem().id) {
				if(slot.getItem().stack - stack < 0)
					return false;
					
				slot.getItem().changeStack(-stack);
				slot.getQuantity().updateText(""+slot.getItem().stack);					
				
				if(slot.getItem().stack == 0) {
					slot.empty();
					inventorySlot.remove(slot);
					this.replace();
				}
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Re-organize the inventory display 
	 */
	private void replace() {
		int x = 0 ,y = 0;
		for (InventorySlot slot : inventorySlot) {
			slot.getItemSprite().setPosition(-width*inventoryWidth/2 + width*1.3f*x + 0.005f, height*(inventoryWidth+3.25f) - height*y*2*1.135f);
			x++;
			if(x == inventoryWidth) {
				x = 0;
				y++;
			}
		}
	}
	
	public void changeDisplay() {
		if(isDisplayed)
			GameWorld.guiLayerMap.getLayer(1).remove(this.inventoryGUI);
		else
			GameWorld.guiLayerMap.getLayer(1).add(this.inventoryGUI);
		this.isDisplayed = !this.isDisplayed;
	}
	
	/**
	 * Sort an item list by Name
	 * @param sort >= 0 is Ascending else Descending
	 */
	public void sortName(byte sort) {		
		Collections.sort(inventorySlot, new Comparator<InventorySlot>(){
			@Override
			public int compare(InventorySlot item1, InventorySlot item2) {
				if(sort >= 0) return item1.getItem().getName().compareTo(item2.getItem().getName());
				else return item2.getItem().getName().compareTo(item1.getItem().getName());
			}
		});
		this.replace();
	}

	/**
	 * Sort an item list by Rarity
	 * @param sort >= 0 is Ascending else Descending
	 */
	public void sortRarity(byte sort) {
		Collections.sort(inventorySlot, new Comparator<InventorySlot>(){
			@Override
			public int compare(InventorySlot item1, InventorySlot item2) {
				if(sort >= 0) return item1.getItem().getRarity().compareTo(item2.getItem().getRarity());
				else return item2.getItem().getRarity().compareTo(item1.getItem().getRarity());
			}		
		});
		this.replace();
	}
	
	/**
	 * Sort an item list by Value
	 * @param sort >= 0 is Ascending else Descending
	 */
	public void sortValue(byte sort) {
		Collections.sort(inventorySlot, new Comparator<InventorySlot>(){
			@Override
			public int compare(InventorySlot item1, InventorySlot item2) {
				if(sort >= 0) return item1.getItem().getValue()*item1.getItem().getStack() - (item2.getItem().getValue()*item2.getItem().getStack());
				else return item2.getItem().getValue()*item1.getItem().getStack() - (item1.getItem().getValue()*item2.getItem().getStack());
			}		
		});
		this.replace();
	}

	/**
	 * Sort an item list by Stack
	 * @param sort >= 0 is Ascending else Descending
	 */
	public void sortStack(byte sort) {
		Collections.sort(inventorySlot, new Comparator<InventorySlot>(){
			@Override
			public int compare(InventorySlot item1, InventorySlot item2) {
				if(sort >= 0) return item1.getItem().getStack() - (item2.getItem().getStack());
				else return item2.getItem().getStack() - (item1.getItem().getStack());
			}
		});
		this.replace();
	}
	
	/**
	 * Return the first arrow found in the inventory
	 * @return the first arrow found in the inventory
	 */
	public Arrow getArrow() {
		for (InventorySlot slot : inventorySlot)
			if(slot.getItem() instanceof Arrow)
				return new Arrow((Arrow)slot.getItem());
		return null;
	}
	
	/**
	 * Return the first bullet found in the inventory
	 * @return the first bullet found in the inventory
	 */
	public Bullet getBullet() {
		for (InventorySlot slot : inventorySlot)
			if(slot.getItem() instanceof Bullet)
				return new Bullet((Bullet)slot.getItem());
		return null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String str = "";
		for (InventorySlot slot : inventorySlot)
			str += slot.getItem().toString() + "\n";
		return "Inventory :\n" + str;
	}


	/**
	 * @return
	 */
	public GUIElement getInventoryGUI() {return inventoryGUI;}
	
	public GUIButton getExitButton() {return exitButton;}
	
}