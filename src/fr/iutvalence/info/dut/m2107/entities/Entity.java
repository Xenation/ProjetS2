package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.models.AbstractSprite;
import fr.iutvalence.info.dut.m2107.models.EntitySprite;
import fr.iutvalence.info.dut.m2107.storage.Layer;

/**
 * A basic Entity
 * @author Voxelse
 * 
 */
public class Entity {
	
	private static final Vector2f DEF_POS = new Vector2f(0, 0);
	private static final float DEF_ROT = 0;
	private static final EntitySprite DEF_SPR = new EntitySprite("item/default", new Vector2f(1, 1));
	private static final Vector2f DEF_SCALE = new Vector2f(1, 1);
	
	/**
	 * The position of the entity
	 */
	protected Vector2f pos;
	
	/**
	 * The scale of the entity
	 */
	protected Vector2f scale;
	
	/**
	 * The rotation of the entity
	 */
	protected float rot;
	
	/**
	 * The sprite of the entity
	 */
	protected final AbstractSprite spr;
	
	/**
	 * The alpha filter to apply to this entity's sprite
	 */
	protected float alpha;

	/**
	 * The collider of the entity
	 */
	protected Collider col;
	
	/**
	 * The layer of entities contained by this entity
	 */
	protected Layer layer;
	
	/**
	 * Constructor of an Entity
	 * @param pos The position of the entity
	 * @param rot The rotation of the entity
	 * @param spr The sprite of the entity
	 */
	public Entity(Vector2f pos, float rot, AbstractSprite spr) {
		this.pos = pos;
		this.rot = rot;
		this.spr = spr;
		this.alpha = 1;
		this.scale = new Vector2f(DEF_SCALE.x, DEF_SCALE.y);
		this.col = new Collider(spr);
		col.setEnt(this);
		col.updateColPos();
	}

	/**
	 * Constructor of an Entity
	 * @param pos The position of the entity
	 * @param rot The rotation of the entity
	 * @param spr The sprite of the entity
	 * @param col The collider of the entity
	 */
	public Entity(Vector2f pos, float rot, AbstractSprite spr, Collider col) {
		this.pos = pos;
		this.rot = rot;
		this.spr = spr;
		this.alpha = 1;
		this.scale = new Vector2f(DEF_SCALE.x, DEF_SCALE.y);
		this.col = col;
		if(col != null) {
			col.setEnt(this);
			col.updateColPos();
		}
	}
	
	/**
	 * Constructor of an Entity
	 * @param pos The position of the entity
	 * @param spr The sprite of the entity
	 */
	public Entity(Vector2f pos, AbstractSprite spr) {
		this.pos = pos;
		this.rot = DEF_ROT;
		this.spr = spr;
		this.alpha = 1;
		this.scale = new Vector2f(DEF_SCALE.x, DEF_SCALE.y);
		this.col = new Collider(spr);
		col.setEnt(this);
		col.updateColPos();
	}
	
	/**
	 * Constructor of an Entity
	 * @param pos The position of the entity
	 * @param spr The sprite of the entity
	 * @param col The collider of the entity
	 */
	public Entity(Vector2f pos, AbstractSprite spr, Collider col) {
		this.pos = pos;
		this.rot = DEF_ROT;
		this.spr = spr;
		this.alpha = 1;
		this.scale = new Vector2f(DEF_SCALE.x, DEF_SCALE.y);
		this.col = col;
		if (col != null) {
			col.setEnt(this);
			col.updateColPos();
		}
	}
	
	/**
	 * Constructor of an Entity
	 * @param spr The sprite of the entity
	 */
	public Entity(AbstractSprite spr) {
		this.pos = new Vector2f(DEF_POS.x, DEF_POS.y);
		this.rot = DEF_ROT;
		this.spr = spr;
		this.alpha = 1;
		this.scale = new Vector2f(DEF_SCALE.x, DEF_SCALE.y);
		this.col = new Collider(spr);
		col.setEnt(this);
		col.updateColPos();
	}
	
	/**
	 * Constructor of an Entity
	 * @param spr The sprite of the entity
	 * @param col The collider of the entity
	 */
	public Entity(AbstractSprite spr, Collider col) {
		this.pos = new Vector2f(DEF_POS.x, DEF_POS.y);
		this.rot = DEF_ROT;
		this.spr = spr;
		this.alpha = 1;
		this.scale = new Vector2f(DEF_SCALE.x, DEF_SCALE.y);
		this.col = col;
		col.setEnt(this);
		col.updateColPos();
	}
	
	/**
	 * Constructor of an Entity
	 */
	public Entity() {
		this.pos = new Vector2f(DEF_POS.x, DEF_POS.y);
		this.rot = DEF_ROT;
		this.spr = DEF_SPR;
		this.alpha = 1;
		this.scale = new Vector2f(DEF_SCALE.x, DEF_SCALE.y);
		this.col = new Collider(spr);
		col.setEnt(this);
		col.updateColPos();
	}
	
	/**
	 * Update the entity
	 * @param layer The layer in which the entity is
	 */
	public void update(Layer layer) {
		return;
	}
	
	/**
	 * Return the sprite of the entity
	 * @return the sprite of the entity
	 */
	public AbstractSprite getSprite() {return this.spr;}
	
	/**
	 * Return the position of the entity
	 * @return the position of the entity
	 */
	public Vector2f getPosition() {return pos;}
	
	/**
	 * Set the entity position
	 * @param pos The position to set
	 */
	public void setPosition(Vector2f pos) {this.pos = pos;}
	
	/**
	 * Set the entity position
	 * @param x The X position to set
	 * @param y The Y position to set
	 */
	public void setPosition(float x, float y) {
		this.pos.x = pos.x;
		this.pos.y = pos.y;
	}
	
	/**
	 * Return the scale of the entity
	 * @return the scale of the entity
	 */
	public Vector2f getScale() {return scale;}
	
	/**
	 * Set the entity scale
	 * @param scale The scale to set
	 */
	public void setScale(Vector2f scale) {this.scale = scale;}
	
	/**
	 * Set the entity scale
	 * @param w The width scale to set
	 * @param h The height scale to set
	 */
	public void setScale(float w, float h) {
		this.scale.x = w;
		this.scale.y = h;
	}
	
	/**
	 * Return the rotation of the entity
	 * @return the rotation of the entity
	 */
	public float getRotation() {return rot;}
	
	/**
	 * Set the entity rotation
	 * @param rot The rotation to set
	 */
	public void setRotation(float rot) {this.rot = rot;}
	
	/**
	 * Returns the alpha of the entity
	 * @return the alpha of the entity
	 */
	public float getAlpha() {return alpha;}

	/**
	 * Sets the alpha of the entity
	 * @param alpha the new alpha of the entity
	 */
	public void setAlpha(float alpha) {this.alpha = alpha;}
	
	/**
	 * Returns the group layer
	 * @return the group layer
	 */
	public Layer getLayer() {return layer;}
	
	/**
	 * Initialises a empty layer for this entity
	 */
	public void initLayer() {this.layer = new Layer();}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entity other = (Entity) obj;
		if (pos == null) {
			if (other.pos != null)
				return false;
		} else if (!pos.equals(other.pos))
			return false;
		if (spr == null) {
			if (other.spr != null)
				return false;
		} else if (!spr.equals(other.spr))
			return false;
		return true;
	}
	
}