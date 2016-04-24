package fr.iutvalence.info.dut.m2107.items;

import fr.iutvalence.info.dut.m2107.models.Sprite;

/**
 * A basic item which have basics attributes
 * @author boureaue
 *
 */

public class Item {
	
	public enum ItemType{
		WEAPON,
		ARMOR,
		AMMO,
	}

	/**
	 * The id of the item
	 */
	protected int id;
	/**
	 * The sprite of the item
	 */
	protected Sprite spr;
	/**
	 * The type of the item
	 */
	protected ItemType type;
	/**
	 * The name of the item
	 */
	protected String name;
	/**
	 * The description of the item
	 */
	protected String description;
	/**
	 * The rarity of the item
	 */
	protected Rarity rarity;
	/**
	 * The max stack of the item
	 */
	protected final int MAX_STACK;
	/**
	 * The current stack of the item
	 */
	protected int stack = 0;
	/**
	 * The value of the item
	 */
	protected int value;
	
	/**
	 * Create an item with basics attributes
	 * @param id of the item
	 * @param type of the item
	 * @param name of the item
	 * @param description of the item
	 * @param rarity of the item
	 * @param maxStack of the item
	 * @param value of the item
	 */
	public Item(int id, Sprite spr, ItemType type, String name, String description, Rarity rarity, int maxStack, int value) {
		this.id = id;
		this.spr = spr;
		this.type = type;
		this.name = name;
		this.description = description;
		this.rarity = rarity;
		this.MAX_STACK = maxStack;
		this.value = value;
	}

	public Item(Item item) {
		this.id = item.id;
		this.spr = item.spr;
		this.type = item.type;
		this.name = item.name;
		this.description = item.description;
		this.rarity = item.rarity;
		this.MAX_STACK = item.MAX_STACK;
		this.value = item.value;
	}

	/**
	 * Return the current stack of the item
	 * @return the current stack of the item
	 */
	public int getStack() {return stack;}
	/**
	 * Return the id of the item
	 * @return the id of the item
	 */
	public int getId() {return id; }
	/**
	 * Return the sprite of the item
	 * @return the sprite of the item
	 */
	public Sprite getSpr() {return spr;}
	/**
	 * Return the type of the item
	 * @return the type of the item
	 */
	public ItemType getType() {return type;}
	/**
	 * Return the name of the item
	 * @return the name of the item
	 */
	public String getName() {return name;}
	/**
	 * Return the description of the item
	 * @return the description of the item
	 */
	public String getDescription() {return description;}
	/**
	 * Return the rarity of the item
	 * @return the rarity of the item
	 */
	public Rarity getRarity() {return rarity;}
	/**
	 * Return the max stack of the item
	 * @return the max stack of the item
	 */
	public int getMaxStack() {return MAX_STACK;}
	/**
	 * Return the value of the item
	 * @return the value of the item
	 */
	public int getValue() {return value;}

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
		return "Item [id=" + id + ", type=" + type + ", name=" + name + ", description=" + description
				+ ", stack=" + stack + ", MAX_STACK=" + MAX_STACK + ", rarity=" + rarity + ", value=" + value + "]";
	}
	
}