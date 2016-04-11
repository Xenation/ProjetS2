package com.github.eagl.items;

/**
 * A basic item which have basics attributes
 * @author boureaue
 *
 */

public class Item {

	/**
	 * The id of the item
	 */
	private int id;
	/**
	 * The name of the item
	 */
	private String name;
	/**
	 * The description of the item
	 */
	private String description;
	/**
	 * The rarity of the item
	 */
	private Rarity rarity;
	/**
	 * The max stack of the item
	 */
	private final int MAX_STACK;
	/**
	 * The current stack of the item
	 */
	private int stack = 0;
	/**
	 * The value of the item
	 */
	private int value;
	
	/**
	 * Create an item with basics attributes
	 * @param _id of the item
	 * @param _name of the item
	 * @param _description of the item
	 * @param _rarity of the item
	 * @param _maxStack of the item
	 * @param _value of the item
	 */
	Item(int _id, String _name, String _description, Rarity _rarity, int _maxStack, int _value) {
		this.id = _id;
		this.name = _name;
		this.description = _description;
		this.rarity = _rarity;
		this.MAX_STACK = _maxStack;
		this.value = _value;
	}

	/**
	 * Return the current stack of the item
	 * @return the current stack of the item
	 */
	public int Stack() { return stack; }
	/**
	 * Return the id of the item
	 * @return the id of the item
	 */
	public int Id() { return id; }
	/**
	 * Return the name of the item
	 * @return the name of the item
	 */
	public String Name() { return name; }
	/**
	 * Return the description of the item
	 * @return the description of the item
	 */
	public String Description() { return description; }
	/**
	 * Return the rarity of the item
	 * @return the rarity of the item
	 */
	public Rarity Rarity() { return rarity; }
	/**
	 * Return the max stack of the item
	 * @return the max stack of the item
	 */
	public int MAX_STACK() { return MAX_STACK; }
	/**
	 * Return the value of the item
	 * @return the value of the item
	 */
	public int Value() { return value; }

	/**
	 * Update the current stack of the item
	 * @param stack The amount of item to add
	 * @return the new item with the modified stack
	 */
	public Item changeStack(int stack){
		this.stack += stack;
		return this;
	}
	
	/**
	 * Return a modified version of the toString method for the Item class
	 * @return the new toString of the item
	 */
	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", description=" + description + ", rarity=" + rarity
				+ ", MAX_STACK=" + MAX_STACK + ", stack=" + stack + ", value=" + value + "]";
	}
}