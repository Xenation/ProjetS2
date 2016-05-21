package fr.iutvalence.info.dut.m2107.inventory;

import java.util.*;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.entities.SpriteDatabase;
import fr.iutvalence.info.dut.m2107.fontMeshCreator.GUIText;
import fr.iutvalence.info.dut.m2107.gui.GUIElement;
import fr.iutvalence.info.dut.m2107.gui.GUISprite;

/**
 * An inventory system which contain item
 * @author boureaue
 *
 */

public class Inventory {
	
	private final int inventoryWidth = 6;
	private final float startX = 0.25f;
	private final float startY = 0.5f;
	
	/**
	 * A list of item which contain a bunch of item and play the role of inventory
	 */	
	private SortedSet<InventorySlot> inventorySlot = new TreeSet<InventorySlot>();
	
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
			lastSlot = new InventorySlot(null, new GUIElement(SpriteDatabase.getEmptySpr(), new Vector2f(startX, startY), 0, 0),new GUIElement(SpriteDatabase.getEmptySpr(), new Vector2f(startX, startY), 0, 0),new GUIText("", 0, startX, startY, 0, false));
		else
			lastSlot = inventorySlot.last();
		InventorySlot newSlot = new InventorySlot();
		item.stack = stack;
		newSlot.setItem(item);
		if(lastSlot.getBackground().getPosition().x + width < startX + inventoryWidth*width) {
			newSlot.setBackground(new GUIElement(SpriteDatabase.getQuickBarSlotStr(), new Vector2f(lastSlot.getBackground().getPosition().x + width, lastSlot.getBackground().getPosition().y), width, height));
			newSlot.setItemSprite(new GUIElement(new GUISprite(item.getSprite().getAtlas(), item.getSprite().getSize()), new Vector2f(), width, height));
			newSlot.setQuantity(new GUIText(""+item.stack, .5f, -width, -width/3, width, true));
		} else  {
			newSlot.setBackground(new GUIElement(SpriteDatabase.getQuickBarSlotStr(), new Vector2f(startX + width, lastSlot.getBackground().getPosition().y - height*1.75f), width, height));
			newSlot.setItemSprite(new GUIElement(new GUISprite(item.getSprite().getAtlas(), item.getSprite().getSize()), new Vector2f(), width, height));
			newSlot.setQuantity(new GUIText(""+item.stack, .5f, -width, -width/3, width, true));
		}
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
	

	private void replace() {
		int x = 0 ,y = 0;
		for (InventorySlot slot : inventorySlot) {
			slot.getBackground().setPosition(startX + width*x, startY + height*y);
			x++;
			if(x == inventoryWidth) {
				x =0;
				y++;
			}
		}
	}
	
	/*public void initInventory() {
		int i = 0;
		for (InventorySlot slot : inventorySlot) {
			InventorySlot slot = new InventorySlot();
			
			slot.setBackground(new GUIElement(SpriteDatabase.getQuickBarSlotStr(), new Vector2f(1 - width, 1 - height - height*i*2), width, height));
			slot.setItem(new GUIElement(item.getSprite(), new Vector2f(1 - width, 1 - height - height*i*2), width*.625f, height*.625f));
			slot.setQuantity(new GUIText("" + this.inventory.get(i).stack , .5f, 1 - width -0.1f/5.5f, height -0.02f + height*i, .1f, true));
			
			slot.display(0);
			
			inventorySlot.add(slot);
			i++;
		}
	}*/
	
	
//	/**
//	 * Sort an item list by Name
//	 * @param sort >= 0 is Ascending else Descending
//	 */
//	public void sortName(byte sort) {		
//		Collections.sort(inventory, new Comparator<Item>(){
//			@Override
//			public int compare(Item item1, Item item2) {
//				if(sort >= 0) return item1.getName().compareTo(item2.getName());
//				else return item2.getName().compareTo(item1.getName());
//			}		
//		});
//	}
//
//	/**
//	 * Sort an item list by Rarity
//	 * @param sort >= 0 is Ascending else Descending
//	 */
//	public void sortRarity(byte sort) {
//		Collections.sort(inventory, new Comparator<Item>(){
//			@Override
//			public int compare(Item item1, Item item2) {
//				if(sort >= 0) return item1.getRarity().compareTo(item2.getRarity());
//				else return item2.getRarity().compareTo(item1.getRarity());
//			}		
//		});
//	}
//	
//	/**
//	 * Sort an item list by Value
//	 * @param sort >= 0 is Ascending else Descending
//	 */
//	public void sortValue(byte sort) {
//		Collections.sort(inventory, new Comparator<Item>(){
//			@Override
//			public int compare(Item item1, Item item2) {
//				if(sort >= 0) return item1.getValue()*item1.getStack() - (item2.getValue()*item2.getStack());
//				else return item2.getValue()*item1.getStack() - (item1.getValue()*item2.getStack());
//			}		
//		});
//	}
//
//	/**
//	 * Sort an item list by Stack
//	 * @param sort >= 0 is Ascending else Descending
//	 */
//	public void sortStack(byte sort) {
//		Collections.sort(inventory, new Comparator<Item>(){
//			@Override
//			public int compare(Item item1, Item item2) {
//				if(sort >= 0) return item1.getStack() - (item2.getStack());
//				else return item2.getStack() - (item1.getStack());
//			}
//		});
//	}
	
	public Arrow getArrow() {
		for (InventorySlot slot : inventorySlot)
			if(slot.getItem() instanceof Arrow)
				return new Arrow((Arrow)slot.getItem());
		return null;
	}
	
	public Bullet getBullet() {
		for (InventorySlot slot : inventorySlot)
			if(slot.getItem() instanceof Bullet)
				return new Bullet((Bullet)slot.getItem());
		return null;
	}

	@Override
	public String toString() {
		String str = "";
		for (InventorySlot slot : inventorySlot)
			str += slot.getItem().toString() + "\n";
		return "Inventory :\n" + str;
	}
}