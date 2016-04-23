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
		if(stack <= item.max_stack()) {
			if(index == -1) {
				item.changeStack(stack);
				inventory.add(item);
				return true;
			} else {
				Item itemToAdd = inventory.get(index);
				if(itemToAdd.stack() + stack <= itemToAdd.max_stack()) {
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
			if(stack < inventory.get(index).stack()) {
				item.changeStack(-stack);
				return true;
			} else if(stack == inventory.get(index).stack()) {
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
				if(sort >= 0) return item1.name().compareTo(item2.name());
				else return item2.name().compareTo(item1.name());
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
				if(sort >= 0) return item1.rarity().compareTo(item2.rarity());
				else return item2.rarity().compareTo(item1.rarity());
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
				if(sort >= 0) return item1.value()*item1.stack() - (item2.value()*item2.stack());
				else return item2.value()*item1.stack() - (item1.value()*item2.stack());
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
				if(sort >= 0) return item1.stack() - (item2.stack());
				else return item2.stack() - (item1.stack());
			}
		});
	}
	
	public AmmunitionItem getArrow() {
		for (Item item : inventory)
			if(item.type() == ItemType.AMMO && (((AmmunitionItem) item).getSubtype() == AmmoType.ARROW))
				return (AmmunitionItem)item;
		return null;
	}
	
	public AmmunitionItem getBullet() {
		for (Item item : inventory)
			if(item.type() == ItemType.AMMO)
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