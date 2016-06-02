package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.Sys;
import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.models.EntitySprite;
import fr.iutvalence.info.dut.m2107.storage.Layer;

/**
 * A living entity
 * @author Voxelse
 *
 */
public class LivingEntity extends MovableEntity {

	public static final int DEF_HEALTH = 10;
	public static final int DEF_JUMP_HEIGHT = 12;
	
	/**
	 * The health of the entity
	 */
	protected int health;
	/**
	 * The jump height of the entity
	 */
	protected int jumpHeight;
	
	protected float recoil = 0;
	
	protected float recoilVel;
	
	protected int[] getHit = new int[2];
	
	/**
	 * Constructor of a LivingEntity
	 * @param pos The position of the entity
	 * @param rot The rotation of the entity
	 * @param spr The sprite of the entity
	 * @param col The collider of the entity
	 * @param vel The velocity of the entity
	 * @param spd The speed of the entity
	 * @param health The health of the entity
	 * @param armor The armor of the entity
	 * @param jumpHeight The jump height of the entity
	 */
	public LivingEntity(Vector2f pos, float rot, EntitySprite spr, Collider col,
						Vector2f vel, float spd,
						int health, int jumpHeight) {
		super(pos, rot, spr, col, vel, spd);
		this.health = health;
		this.jumpHeight = jumpHeight;
	}
	
	/**
	 * Constructor of a LivingEntity
	 * @param pos The position of the entity
	 * @param spr The sprite of the entity
	 */
	public LivingEntity(Vector2f pos, EntitySprite spr) {
		super(pos, spr);
		this.health = DEF_HEALTH;
		this.jumpHeight = DEF_JUMP_HEIGHT;
	}
	
	/**
	 * Constructor of a LivingEntity
	 * @param pos The position of the entity
	 * @param spr The sprite of the entity
	 * @param col The collider of the entity
	 */
	public LivingEntity(Vector2f pos, EntitySprite spr, Collider col) {
		super(pos, spr, col);
		this.health = DEF_HEALTH;
		this.jumpHeight = DEF_JUMP_HEIGHT;
	}

	/**
	 * Constructor of a LivingEntity
	 */
	public LivingEntity() {
		super();
		this.health = DEF_HEALTH;
		this.jumpHeight = DEF_JUMP_HEIGHT;
	}
	
	/* (non-Javadoc)
	 * @see fr.iutvalence.info.dut.m2107.entities.MovableEntity#update(fr.iutvalence.info.dut.m2107.storage.Layer)
	 */
	@Override
	public void update(Layer layer) {
		if(this.health <= 0 || this.pos.y < -500) layer.remove(this);
		
		if(this.getHit[0] != 0 || this.getHit[1] != 0) {
			takeKnockback(this.getHit[0]);
			takeDamage(this.getHit[1]);
			this.getHit[0] = 0;
			this.getHit[1] = 0;
		}
		
		if(this instanceof TerrestrialCreature) {
			if(this.recoil != 0) {
				if(!((TerrestrialCreature)this).isGrounded) {
					this.vel.x = this.recoil + recoilVel;
					if(this.col != null) this.col.checkContinuousCollision();
				} else {
					this.vel.x = 0;
					this.recoil = 0;
				}
			}
		}
		super.update(layer);
	}

	public void doDamage(int damage, int knockback) {
		if(this instanceof Player) {
			if(((Player)this).invulnerabilityTime < Sys.getTime()/1000f) {
				((Player)this).invulnerabilityTime = Sys.getTime()/1000f+.5f;
				this.getHit[0] = knockback;
				this.getHit[1] = damage;
			}
		} else {
			this.getHit[0] = knockback;
			this.getHit[1] = damage;
		}
	}
	
	/**
	 * Remove health from entity
	 * @param damage The amount of life take off
	 */
	private void takeDamage(int damage) {
		recoilVel = this.vel.x;
		if(damage > 0) this.health -= damage;
		if(this instanceof TerrestrialCreature) ((TerrestrialCreature)this).isGrounded = false;
	}
	
	private void takeKnockback(int knockback) {
		this.recoil = knockback;
		this.vel.y += this.jumpHeight/2;
	}
	
	/**
	 * Return the health of the entity
	 * @return the health of the entity
	 */
	public int getHealth() {return health;}
	
	/**
	 * Return the jump height of the entity
	 * @return the jump height of the entity
	 */
	public int getJumpHeight() {return jumpHeight;}
	
}