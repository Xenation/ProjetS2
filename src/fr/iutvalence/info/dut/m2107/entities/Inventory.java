package fr.iutvalence.info.dut.m2107.entities;

import java.util.*;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.fontMeshCreator.GUIText;
import fr.iutvalence.info.dut.m2107.guiRendering.GUIElement;

/**
 * An inventory system which contain item
 * @author boureaue
 *
 */

public class Inventory {
	
	/**
	 * A list of item which contain a bunch of item and play the role of inventory
	 */
	private ArrayList<Item> inventory = new ArrayList<Item>();

	private List<GUIElement> inventoryGUI = new ArrayList<GUIElement>();
	private List<GUIElement> inventoryGUISpr = new ArrayList<GUIElement>();
	
	private List<GUIText> inventoryGUIText = new ArrayList<GUIText>();
	
	//Temporary
	private float width = 0.05f;
	private float height = 0.05f;
	//Temporary
	
	/**
	 * Add an item with a certain amount into the inventory
	 * @param item The item to add to the inventory
	 * @param stack The amount of items to add to the inventory
	 * @return true if added otherwise false
	 */
	public boolean add(Item item, int stack) {
		int index = inventory.indexOf(item);
		if(stack <= item.MAX_STACK) {
			if(index == -1) {
				item.stack = stack;
				inventory.add(item);
				return true;
			} else {
				Item itemToAdd = inventory.get(index);
				if(itemToAdd.stack + stack <= itemToAdd.MAX_STACK) {
					inventory.get(index).changeStack(stack);
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Remove an item with a certain amount from the inventory
	 * @param item The item to remove to the inventory
	 * @param stack The amount of items to remove to the inventory
	 */
	public boolean remove(Item item, int stack) {
		int index = inventory.indexOf(item);
		
		if(index != -1) {
			
			if(stack < inventory.get(index).getStack()) {
				inventory.get(index).changeStack(-stack);
				inventoryGUIText.get(index).updateText("" + inventory.get(index).stack);
				return true;
			} else if(stack == inventory.get(index).getStack()) {
				inventory.remove(index);
				inventoryGUISpr.remove(index).remove();
				for (int i = index; i < inventory.size(); i++) {
					inventoryGUISpr.get(i).setPositionY(inventoryGUISpr.get(i).getPosition().y + height*2);
					inventoryGUIText.get(i).updateText(inventoryGUIText.get(i+1).getTextString());
				}
				inventoryGUIText.get(inventory.size()).remove();
				inventoryGUI.remove(inventory.size()).remove();
				return true;
			}
		}
		return false;
	}
	

	public void initInventory() {
		int i = 0;
		for (Item item : inventory) {
			inventoryGUI.add(new GUIElement(SpriteDatabase.getQuickBarSlotStr(), new Vector2f(1 - width, 1 - height - height*i*2), width, height));
			inventoryGUISpr.add(new GUIElement(item.getSprite().getTextureID(), new Vector2f(1 - width, 1 - height - height*i*2), width*.625f, height*.625f));
			inventoryGUIText.add(new GUIText("" + this.inventory.get(i).stack , .5f, 1 - width -0.1f/5.5f, height -0.02f + height*i, .1f, true));
			i++;
		}
	}
	
	
	/**
	 * Sort an item list by Name
	 * @param sort >= 0 is Ascending else Descending
	 */
	public void sortName(byte sort) {		
		Collections.sort(inventory, new Comparator<Item>(){
			@Override
			public int compare(Item item1, Item item2) {
				if(sort >= 0) return item1.getName().compareTo(item2.getName());
				else return item2.getName().compareTo(item1.getName());
			}		
		});
	}

	/**
	 * Sort an item list by Rarity
	 * @param sort >= 0 is Ascending else Descending
	 */
	public void sortRarity(byte sort) {
		Collections.sort(inventory, new Comparator<Item>(){
			@Override
			public int compare(Item item1, Item item2) {
				if(sort >= 0) return item1.getRarity().compareTo(item2.getRarity());
				else return item2.getRarity().compareTo(item1.getRarity());
			}		
		});
	}
	
	/**
	 * Sort an item list by Value
	 * @param sort >= 0 is Ascending else Descending
	 */
	public void sortValue(byte sort) {
		Collections.sort(inventory, new Comparator<Item>(){
			@Override
			public int compare(Item item1, Item item2) {
				if(sort >= 0) return item1.getValue()*item1.getStack() - (item2.getValue()*item2.getStack());
				else return item2.getValue()*item1.getStack() - (item1.getValue()*item2.getStack());
			}		
		});
	}

	/**
	 * Sort an item list by Stack
	 * @param sort >= 0 is Ascending else Descending
	 */
	public void sortStack(byte sort) {
		Collections.sort(inventory, new Comparator<Item>(){
			@Override
			public int compare(Item item1, Item item2) {
				if(sort >= 0) return item1.getStack() - (item2.getStack());
				else return item2.getStack() - (item1.getStack());
			}
		});
	}
	
	public Arrow getArrow() {
		for (Item item : inventory)
			if(item instanceof Arrow)
				return new Arrow((Arrow)item);
		return null;
	}
	
	public Bullet getBullet() {
		for (Item item : inventory)
			if(item instanceof Bullet)
				return new Bullet((Bullet)item);
		return null;
	}

	@Override
	public String toString() {
		String str = "";
		for (Item item : inventory) {
			str += item.toString() + "\n";
		}
		return "Inventory :\n" + str;
	}
}