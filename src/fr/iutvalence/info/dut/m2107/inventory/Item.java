package fr.iutvalence.info.dut.m2107.inventory;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.entities.Collider;
import fr.iutvalence.info.dut.m2107.entities.MovableEntity;
import fr.iutvalence.info.dut.m2107.models.EntitySprite;
import fr.iutvalence.info.dut.m2107.render.DisplayManager;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Layer;
import fr.iutvalence.info.dut.m2107.tiles.Tile;

/**
 * Class which represent an item
 * @author Voxelse
 *
 */
public class Item extends MovableEntity {

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
	
	private Tile collidingTile;
	
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
	public Item(EntitySprite spr, short id, String name, String description, Rarity rarity, short maxStack, short value) {
		super(spr);
		this.id = id;
		this.name = name;
		this.description = description;
		this.rarity = rarity;
		this.MAX_STACK = maxStack;
		this.value = value;
	}
	
	public Item(EntitySprite spr, Collider col, short spd,
			short id, String name, String description, Rarity rarity, short maxStack, short value) {
		super(spr, col, spd);
		this.id = id;
		this.name = name;
		this.description = description;
		this.rarity = rarity;
		this.MAX_STACK = maxStack;
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see fr.iutvalence.info.dut.m2107.entities.Entity#update(fr.iutvalence.info.dut.m2107.storage.Layer)
	 */
	@Override
	public void update(Layer layer) {
		if(this.getClass().getSimpleName().equals("Item")) {
			if(this.col.isCollidingWithPlayer()) {
				if(GameWorld.player.addItem(this))
					layer.remove(this);
			}
			if(collidingTile == null) {
				this.vel.y -= GameWorld.gravity * DisplayManager.deltaTime();
				
				if(this.vel.y < -25) this.vel.y = -25;
				
				Collider encompass = Collider.encompassCollider(this.col, this.vel);
				collidingTile = this.col.isCollidingWithMap(encompass);
				if(collidingTile != null) {
					this.pos.y = collidingTile.y + Tile.TILE_SIZE + this.col.getH()/3;
					this.vel.y = 0;
				}
			}
		}
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

	public Item copy() {
		return new Item((EntitySprite) this.spr,
						this.col == null ? null : new Collider(this.col),
						this.spd,
						this.id,
						this.name,
						this.description,
						this.rarity,
						this.MAX_STACK,
						this.value);
	}
	
	public static Item copyDropableItem(Item item, float x, float y) {
		Item newItem = new Item((EntitySprite) item.spr,
								new Collider(item.spr),
								item.spd,
								item.id,
								item.name,
								item.description,
								item.rarity,
								item.MAX_STACK,
								item.value);
		newItem.setCollider(new Collider(newItem.getSprite()));
		newItem.getCollider().setEnt(newItem);
		newItem.setPosition(new Vector2f(x, y));
		newItem.getCollider().updateColPos();
		return newItem;
	}
	
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