package fr.iutvalence.info.dut.m2107.items;

import java.util.*;

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
		if(stack <= item.MAX_STACK()) {
			if(index == -1) {
				item.changeStack(stack);
				inventory.add(item);
				return true;
			} else {
				Item itemToAdd = inventory.get(index);
				if(itemToAdd.Stack() + stack <= itemToAdd.MAX_STACK()) {
					inventory.set(index, itemToAdd.changeStack(stack));
					return true;
				}
			}
		}
		StackOverflow(item);
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
			if(stack < inventory.get(index).Stack()) {
				item.changeStack(-stack);
				return true;
			} else if(stack == inventory.get(index).Stack()) {
				inventory.remove(index);
				return true;
			}
		}
		StackUnderflow(inventory.get(index));
		return false;
	}
	
	/**
	 * Display a message that says you can't take more item
	 * @param item The item which is in overflow
	 */
	private void StackOverflow(Item item) {
		System.out.println("You can't carry more than : " + item.MAX_STACK() + " " + item.Name());
	}
	
	/**
	 * Display a message that says you can't remove more item
	 * @param item The item which is in underflow
	 */
	private void StackUnderflow(Item item) {
		System.out.println("You can't remove more than : " + item.Stack() + " " + item.Name());
	}
	
	/**
	 * Sort an item list by Name
	 * @param sort >= 0 is Ascending else Descending
	 */
	public void sortName(byte sort) {		
		Collections.sort(inventory, new Comparator<Item>(){
			@Override
			public int compare(Item item1, Item item2) {
				if(sort >= 0) return item1.Name().compareTo(item2.Name());
				else return item2.Name().compareTo(item1.Name());
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
				if(sort >= 0) return item1.Rarity().compareTo(item2.Rarity());
				else return item2.Rarity().compareTo(item1.Rarity());
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
				if(sort >= 0) return item1.Value()*item1.Stack() - (item2.Value()*item2.Stack());
				else return item2.Value()*item1.Stack() - (item1.Value()*item2.Stack());
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
				if(sort >= 0) return item1.Stack() - (item2.Stack());
				else return item2.Stack() - (item1.Stack());
			}		
		});
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