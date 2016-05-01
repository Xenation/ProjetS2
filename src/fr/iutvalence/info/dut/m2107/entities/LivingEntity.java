package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.models.Sprite;
import fr.iutvalence.info.dut.m2107.storage.Layer;

/**
 * A living entity
 * @author Voxelse
 *
 */
public class LivingEntity extends MovableEntity {

	private static final int DEF_HEALTH = 10;
	private static final int DEF_ARMOR = 10;
	private static final int DEF_JUMP_HEIGHT = 12;
	
	/**
	 * The health of the entity
	 */
	protected int health;
	/**
	 * The armor of the entity
	 */
	protected int armor;
	/**
	 * The jump height of the entity
	 */
	protected int jumpHeight;
	
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
	public LivingEntity(Vector2f pos, float rot, Sprite spr, Collider col,
						Vector2f vel, float spd,
						int health, int armor, int jumpHeight) {
		super(pos, rot, spr, col, vel, spd);
		this.health = health;
		this.armor = armor;
		this.jumpHeight = jumpHeight;
	}
	
	/**
	 * Constructor of a LivingEntity
	 * @param pos The position of the entity
	 * @param spr The sprite of the entity
	 */
	public LivingEntity(Vector2f pos, Sprite spr) {
		super(pos, spr);
		this.health = DEF_HEALTH;
		this.armor = DEF_ARMOR;
		this.jumpHeight = DEF_JUMP_HEIGHT;
	}
	
	/**
	 * Constructor of a LivingEntity
	 * @param pos The position of the entity
	 * @param spr The sprite of the entity
	 * @param col The collider of the entity
	 */
	public LivingEntity(Vector2f pos, Sprite spr, Collider col) {
		super(pos, spr, col);
		this.health = DEF_HEALTH;
		this.armor = DEF_ARMOR;
		this.jumpHeight = DEF_JUMP_HEIGHT;
	}

	/**
	 * Constructor of a LivingEntity
	 */
	public LivingEntity() {
		super();
		this.health = DEF_HEALTH;
		this.armor = DEF_ARMOR;
		this.jumpHeight = DEF_JUMP_HEIGHT;
	}
	
	/* (non-Javadoc)
	 * @see fr.iutvalence.info.dut.m2107.entities.MovableEntity#update(fr.iutvalence.info.dut.m2107.storage.Layer)
	 */
	@Override
	public void update(Layer layer) {
		super.update(layer);
		if(this.health <= 0) layer.remove(this);
	}

	/**
	 * Remove health from entity
	 * @param damage The amount of life take off
	 */
	public void takeDamage(int damage) {
		if(damage > 0) this.health -= damage;
	}

	/**
	 * Return the health of the entity
	 * @return the health of the entity
	 */
	public int getHealth() {return health;}
	
	/**
	 * Return the armor of the entity
	 * @return the armor of the entity
	 */
	public int getArmor() {return armor;}
	
	/**
	 * Return the jump height of the entity
	 * @return the jump height of the entity
	 */
	public int getJumpHeight() {return jumpHeight;}

}