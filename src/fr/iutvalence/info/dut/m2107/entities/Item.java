package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.models.Sprite;
import fr.iutvalence.info.dut.m2107.storage.Layer;

public class Item extends Entity {

	/**
	 * The id of the item
	 */
	protected final int id;
	/**
	 * The name of the item
	 */
	protected final String name;
	/**
	 * The description of the item
	 */
	protected final String description;
	/**
	 * The rarity of the item
	 */
	protected final Rarity rarity;
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
	protected final int value;

	public Item(Vector2f pos, float rot, Sprite spr,
				int id, String name, String description, Rarity rarity, int maxStack, int value) {
		super(pos, rot, spr);
		this.id = id;
		this.name = name;
		this.description = description;
		this.rarity = rarity;
		this.MAX_STACK = maxStack;
		this.value = value;
	}
	
	public Item(Sprite spr, Collider col,
				int id, String name, String description, Rarity rarity, int maxStack, int value) {
		super(spr, col);
		this.id = id;
		this.name = name;
		this.description = description;
		this.rarity = rarity;
		this.MAX_STACK = maxStack;
		this.value = value;
	}
	
	public Item(Sprite spr,
				int id, String name, String description, Rarity rarity, int maxStack, int value) {
		super(spr);
		this.id = id;
		this.name = name;
		this.description = description;
		this.rarity = rarity;
		this.MAX_STACK = maxStack;
		this.value = value;
	}
	
	public Item(Item item) {
		super(item.spr, item.col);
		this.id = item.id;
		this.name = item.name;
		this.description = item.description;
		this.rarity = item.rarity;
		this.MAX_STACK = item.MAX_STACK;
		this.value = item.value;
	}

	@Override
	public void update(Layer layer) {
		super.update(layer);
	}

	public Item changeStack(int stackToAdd) {
		this.stack += stackToAdd;
		return this;
	}	

	public int getId() {return id;}
	public String getName() {return name;}
	public String getDescription() {return description;}
	public Rarity getRarity() {return rarity;}
	public int getMAX_STACK() {return MAX_STACK;}
	public int getStack() {return stack;}
	public int getValue() {return value;}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", description=" + description + ", rarity=" + rarity
				+ ", MAX_STACK=" + MAX_STACK + ", stack=" + stack + ", value=" + value + "]";
	}
	
}