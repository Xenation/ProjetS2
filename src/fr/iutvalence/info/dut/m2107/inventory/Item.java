package fr.iutvalence.info.dut.m2107.inventory;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.entities.Collider;
import fr.iutvalence.info.dut.m2107.entities.Entity;
import fr.iutvalence.info.dut.m2107.models.EntitySprite;
import fr.iutvalence.info.dut.m2107.storage.Layer;

/**
 * Class which represent an item
 * @author Voxelse
 *
 */
public class Item extends Entity {

	/**
	 * The id of the item
	 */
	protected final short id;
	
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
	protected final short MAX_STACK;
	
	/**
	 * The current stack of the item
	 */
	protected short stack = 1;
	
	/**
	 * The value of the item
	 */
	protected final short value;

	/**
	 * Constructor of an Item
	 * @param pos The position of the item
	 * @param rot The rotation of the item
	 * @param spr The sprite of the item
	 * @param id The id of the item
	 * @param name The name of the item
	 * @param description The description of the item
	 * @param rarity The rarity of the item
	 * @param maxStack The maximum stack of the item
	 * @param value The value of the item
	 */
	public Item(Vector2f pos, float rot, EntitySprite spr,
				short id, String name, String description, Rarity rarity, short maxStack, short value) {
		super(pos, rot, spr);
		this.id = id;
		this.name = name;
		this.description = description;
		this.rarity = rarity;
		this.MAX_STACK = maxStack;
		this.value = value;
	}
	
	/**
	 * Constructor of an Item
	 * @param spr The sprite of the item
	 * @param col The collider of the item
	 * @param id The id of the item
	 * @param name The name of the item
	 * @param description The description of the item
	 * @param rarity The rarity of the item
	 * @param maxStack The maximum stack of the item
	 * @param value The value of the item
	 */
	public Item(EntitySprite spr, Collider col,
			short id, String name, String description, Rarity rarity, short maxStack, short value) {
		super(spr, col);
		this.id = id;
		this.name = name;
		this.description = description;
		this.rarity = rarity;
		this.MAX_STACK = maxStack;
		this.value = value;
	}
	
	/**
	 * Constructor of an Item
	 * @param spr The sprite of the item
	 * @param id The id of the item
	 * @param name The name of the item
	 * @param description The description of the item
	 * @param rarity The rarity of the item
	 * @param maxStack The maximum stack of the item
	 * @param value The value of the item
	 */
	public Item(EntitySprite spr,
			short id, String name, String description, Rarity rarity, short maxStack, short value) {
		super(spr);
		this.id = id;
		this.name = name;
		this.description = description;
		this.rarity = rarity;
		this.MAX_STACK = maxStack;
		this.value = value;
	}
	
	/**
	 * Constructor of an Item
	 * @param item The item to copy
	 */
	public Item(Item item) {
		super(item.spr, item.col);
		this.id = item.id;
		this.name = item.name;
		this.description = item.description;
		this.rarity = item.rarity;
		this.MAX_STACK = item.MAX_STACK;
		this.value = item.value;
	}

	/* (non-Javadoc)
	 * @see fr.iutvalence.info.dut.m2107.entities.Entity#update(fr.iutvalence.info.dut.m2107.storage.Layer)
	 */
	@Override
	public void update(Layer layer) {
		super.update(layer);
	}

	/**
	 * Add a number of stack to the current stack of the item
	 * @param stackToAdd The number of stack to add
	 */
	public void changeStack(short stackToAdd) {this.stack += stackToAdd;}	

	/**
	 * Return the id of the item
	 * @return the id of the item
	 */
	public short getId() {return id;}
	
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
	 * Return the maximum stack of the item
	 * @return the maximum stack of the item
	 */
	public short getMAX_STACK() {return MAX_STACK;}
	
	/**
	 * Return the current stack of the item
	 * @return the current stack of the item
	 */
	public short getStack() {return stack;}
	
	/**
	 * Return the value of the item
	 * @return the value of the item
	 */
	public short getValue() {return value;}

	/* (non-Javadoc)
	 * @see fr.iutvalence.info.dut.m2107.entities.Entity#equals(java.lang.Object)
	 */
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
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", description=" + description + ", rarity=" + rarity
				+ ", MAX_STACK=" + MAX_STACK + ", stack=" + stack + ", value=" + value + "]";
	}
	
}