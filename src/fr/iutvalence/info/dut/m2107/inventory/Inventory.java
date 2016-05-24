package fr.iutvalence.info.dut.m2107.inventory;

import java.util.*;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.entities.SpriteDatabase;
import fr.iutvalence.info.dut.m2107.fontMeshCreator.GUIText;
import fr.iutvalence.info.dut.m2107.gui.GUIElement;
import fr.iutvalence.info.dut.m2107.gui.GUISprite;
import fr.iutvalence.info.dut.m2107.toolbox.Maths;

/**
 * An inventory system which contain item
 * @author boureaue
 *
 */

public class Inventory {
	
	/**
	 * The number of inventory slot by row
	 */
	private final int inventoryWidth = 6;
	
	/**
	 * The start x position of the inventory display
	 */
	private final float startX = 0.45f;
	
	/**
	 * The start y position of the inventory display
	 */
	private final float startY = 0.5f;
	
	/**
	 * A list of inventory slot which contain a bunch of item and it's slot and play the role of inventory
	 */	
	private List<InventorySlot> inventorySlot = new ArrayList<InventorySlot>();
	
	//Temporary
	private float width = 0.075f;
	private float height = 0.075f;
	//Temporary
	
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
		InventorySlot lastSlot;
		if(inventorySlot.isEmpty())
			lastSlot = new InventorySlot(null, new GUIElement(SpriteDatabase.getEmptySpr(), new Vector2f(startX-width, startY), 0, 0),new GUIElement(SpriteDatabase.getEmptySpr(), new Vector2f(startX-width, startY), 0, 0),new GUIText("", 0, startX-width, startY, 0, false));
		else
			lastSlot = inventorySlot.get(inventorySlot.size()-1);
		InventorySlot newSlot = new InventorySlot();
		item.stack = stack;
		newSlot.setItem(item);
		if(Maths.round(lastSlot.getBackground().getPosition().x + width, 5) < startX + inventoryWidth*width) {
			newSlot.setBackground(new GUIElement(SpriteDatabase.getQuickBarSlotStr(), new Vector2f(lastSlot.getBackground().getPosition().x + width, lastSlot.getBackground().getPosition().y), width, height));
			newSlot.setItemSprite(new GUIElement(new GUISprite(item.getSprite().getAtlas(), item.getSprite().getSize()), new Vector2f(), width, height));
			newSlot.setQuantity(new GUIText(""+item.stack, .5f, -width, -width/3, width, true));
		} else  {
			newSlot.setBackground(new GUIElement(SpriteDatabase.getQuickBarSlotStr(), new Vector2f(startX, lastSlot.getBackground().getPosition().y - height*1.75f), width, height));
			newSlot.setItemSprite(new GUIElement(new GUISprite(item.getSprite().getAtlas(), item.getSprite().getSize()), new Vector2f(), width, height));
			newSlot.setQuantity(new GUIText(""+item.stack, .5f, -width, -width/3, width, true));
		}
		newSlot.getItemSprite().setRotation(-45);
		float scaleMult = newSlot.getItemSprite().getSprite().getSize().x/newSlot.getItemSprite().getSprite().getSize().y;
		if(scaleMult == 1)
			newSlot.getItemSprite().setScale(newSlot.getItemSprite().getScale().x / 1.25f, newSlot.getItemSprite().getScale().y / 1.25f);
		else
			newSlot.getItemSprite().setScale(newSlot.getItemSprite().getScale().x / scaleMult, newSlot.getItemSprite().getScale().y / scaleMult);
		newSlot.display();
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
			if(item.id == slot.getItem().id) {
				if(slot.getItem().stack - stack < 0)
					return false;
					
				slot.getItem().changeStack(-stack);
				slot.getQuantity().updateText(""+slot.getItem().stack);					
				
				if(slot.getItem().stack == 0) {
					inventorySlot.remove(slot);
					this.replace();
					slot.hide();
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
			slot.getBackground().setPosition(startX + width*x, startY - height*1.75f*y);
			System.out.println(y);
			x++;
			if(x == inventoryWidth) {
				x =0;
				y++;
			}
		}
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
}