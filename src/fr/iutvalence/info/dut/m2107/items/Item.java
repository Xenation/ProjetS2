package fr.iutvalence.info.dut.m2107.items;

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
	 * @param id of the item
	 * @param name of the item
	 * @param description of the item
	 * @param rarity of the item
	 * @param maxStack of the item
	 * @param value of the item
	 */
	Item(int id, String name, String description, Rarity rarity, int maxStack, int value) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.rarity = rarity;
		this.MAX_STACK = maxStack;
		this.value = value;
	}

	/**
	 * Return the current stack of the item
	 * @return the current stack of the item
	 */
	public int stack() { return stack; }
	/**
	 * Return the id of the item
	 * @return the id of the item
	 */
	public int id() { return id; }
	/**
	 * Return the name of the item
	 * @return the name of the item
	 */
	public String name() { return name; }
	/**
	 * Return the description of the item
	 * @return the description of the item
	 */
	public String description() { return description; }
	/**
	 * Return the rarity of the item
	 * @return the rarity of the item
	 */
	public Rarity rarity() { return rarity; }
	/**
	 * Return the max stack of the item
	 * @return the max stack of the item
	 */
	public int max_stack() { return MAX_STACK; }
	/**
	 * Return the value of the item
	 * @return the value of the item
	 */
	public int value() { return value; }

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