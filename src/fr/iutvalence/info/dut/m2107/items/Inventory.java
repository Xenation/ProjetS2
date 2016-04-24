package fr.iutvalence.info.dut.m2107.items;

import java.util.*;

import fr.iutvalence.info.dut.m2107.items.AmmunitionItem.AmmoType;
import fr.iutvalence.info.dut.m2107.items.Item.ItemType;

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

	/**
	 * Add an item with a certain amount into the inventory
	 * @param item The item to add to the inventory
	 * @param stack The amount of items to add to the inventory
	 */
	public boolean add(Item item, int stack) {
		int index = inventory.indexOf(item);
		if(stack <= item.getMaxStack()) {
			if(index == -1) {
				item.changeStack(stack);
				inventory.add(item);
				return true;
			} else {
				Item itemToAdd = inventory.get(index);
				if(itemToAdd.getStack() + stack <= itemToAdd.getMaxStack()) {
					inventory.set(index, itemToAdd.changeStack(stack));
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
				item.changeStack(-stack);
				return true;
			} else if(stack == inventory.get(index).getStack()) {
				inventory.remove(index);
				return true;
			}
		}
		return false;
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
	
	public AmmunitionItem getArrow() {
		for (Item item : inventory)
			if(item.getType() == ItemType.AMMO && (((AmmunitionItem) item).getSubtype() == AmmoType.ARROW))
				return (AmmunitionItem)item;
		return null;
	}
	
	public AmmunitionItem getBullet() {
		for (Item item : inventory)
			if(item.getType() == ItemType.AMMO)
				return (AmmunitionItem)item;
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