package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.models.Sprite;
import fr.iutvalence.info.dut.m2107.render.DisplayManager;
import fr.iutvalence.info.dut.m2107.storage.Layer;

/**
 * A movable entity
 * @author Voxelse
 *
 */
public class MovableEntity extends Entity {
	
	private static final Vector2f DEF_VEL = new Vector2f(0, 0);
	private static final float DEF_SPD = 4;
	
	/**
	 * The velocity of the entity
	 */
	protected Vector2f vel;
	
	/**
	 * The speed of the entity
	 */
	protected float spd;

	/**
	 * Constructor of a MovableEntity
	 * @param pos The position of the entity
	 * @param rot The rotation of the entity
	 * @param spr The sprite of the entity
	 * @param col The collider of the entity
	 * @param vel The velocity of the entity
	 * @param spd The speed of the entity
	 */
	public MovableEntity(Vector2f pos, float rot, Sprite spr, Collider col, Vector2f vel, float spd) {
		super(pos, rot, spr, col);
		this.vel = vel;
		this.spd = spd;
	}
	
	/**
	 * Constructor of a MovableEntity
	 * @param pos The position of the entity
	 * @param spr The sprite of the entity
	 */
	public MovableEntity(Vector2f pos, Sprite spr) {
		super(pos, spr);
		this.vel = new Vector2f(DEF_VEL.x, DEF_VEL.y);
		this.spd = DEF_SPD;
	}
	
	/**
	 * Constructor of a MovableEntity
	 * @param pos The position of the entity
	 * @param spr The sprite of the entity
	 * @param col The collider of the entity
	 */
	public MovableEntity(Vector2f pos, Sprite spr, Collider col) {
		super(pos, spr, col);
		this.vel = new Vector2f(DEF_VEL.x, DEF_VEL.y);
		this.spd = DEF_SPD;
	}
	
	/**
	 * Constructor of a MovableEntity
	 * @param pos The position of the entity
	 * @param rot The rotation of the entity
	 * @param spr The sprite of the entity
	 * @param vel The velocity of the entity
	 */
	public MovableEntity(Vector2f pos, float rot, Sprite spr, Vector2f vel) {
		super(pos, rot, spr);
		this.vel = vel;
		this.spd = DEF_SPD;
	}
	
	/**
	 * Constructor of a MovableEntity
	 */
	public MovableEntity() {
		super();
		this.vel = new Vector2f(DEF_VEL.x, DEF_VEL.y);
		this.spd = DEF_SPD;
	}
	
	/* (non-Javadoc)
	 * @see fr.iutvalence.info.dut.m2107.entities.Entity#update(fr.iutvalence.info.dut.m2107.storage.Layer)
	 */
	@Override
	public void update(Layer layer) {
		if(this.col != null)
			this.col.updateColPos();
		if(this.vel != null) {
			this.pos.x += this.vel.x * DisplayManager.deltaTime();
			this.pos.y += this.vel.y * DisplayManager.deltaTime();
		}
	}

	/**
	 * Set the velocity of the entity
	 * @param the velocity to set of the entity
	 */
	public void setVelocity(Vector2f vel) {this.vel = vel;}
	
	/**
	 * Return the velocity of the entity
	 * @return the velocity of the entity
	 */
	public Vector2f getVelocity() {return vel;}
	
	/**
	 * Return the speed of the entity
	 * @return the speed of the entity
	 */
	public float getSpeed() {return spd;}
}