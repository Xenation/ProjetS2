package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.models.Sprite;
import fr.iutvalence.info.dut.m2107.render.DisplayManager;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Layer;

/**
 * An ammunition item
 * @author Voxelse
 *
 */
public abstract class Ammunition extends Item {
	
	private static final Vector2f DEF_VEL = new Vector2f(0, 0);
	
	/**
	 * Time before destruction
	 */
	protected final int DESTROY_TIME = 5;
	
	/**
	 * Time remaining before destruction
	 */
	protected float remainingTime = 0;
	
	/**
	 * The damage of the ammo
	 */
	protected int damage;
	
	/**
	 * The knockback of the ammo
	 */
	protected int knockback;
	
	/**
	 * The velocity of the ammo
	 */
	protected Vector2f vel;
	
	/**
	 * The speed of the ammo
	 */
	protected int speed;
	
	/**
	 * Constructor of an ammunition
	 * @param pos The position of the ammo
	 * @param rot The rotation of the ammo
	 * @param spr The sprite of the ammo
	 * @param id The id of the id
	 * @param name The name of the ammo
	 * @param description The description of the ammo
	 * @param rarity The rarity of the ammo
	 * @param maxStack The max stack of the ammo
	 * @param value The value of the ammo
	 * @param damage The damage of the ammo
	 * @param knockback The knocback of the ammo
	 * @param velocity The velocity of the ammo
	 * @param speed The speed of the ammo
	 */
	public Ammunition(Vector2f pos, float rot, Sprite spr,
					int id, String name, String description, Rarity rarity, int maxStack, int value,
					int damage, int knockback, Vector2f velocity, int speed) {
		super(pos, rot, spr, id, name, description, rarity, maxStack, value);
		this.damage = damage;
		this.knockback = knockback;
		this.vel = velocity;
		this.vel.scale(speed);
	}
	
	/**
	 * Constructor of an ammunition
	 * @param spr The sprite of the ammo
	 * @param col The collider of the ammo
	 * @param id The id of the id
	 * @param name The name of the ammo
	 * @param description The description of the ammo
	 * @param rarity The rarity of the ammo
	 * @param maxStack The max stack of the ammo
	 * @param value The value of the ammo
	 * @param damage The damage of the ammo
	 * @param knockback The knocback of the ammo
	 * @param speed The speed of the ammo
	 */
	public Ammunition(Sprite spr, Collider col,
					int id, String name, String description, Rarity rarity, int maxStack, int value,
					int damage, int knockback, int speed) {
		super(spr, col, id, name, description, rarity, maxStack, value);
		this.damage = damage;
		this.knockback = knockback;
		this.vel = DEF_VEL;
		this.speed = speed;
	}
	
	/**
	 * Constructor of an ammunition
	 * @param ammo The ammo to copy
	 */
	public Ammunition(Ammunition ammo) {
		super(ammo);
		this.damage = ammo.damage;
		this.knockback = ammo.knockback;
		this.speed = ammo.speed;
	}

	/* (non-Javadoc)
	 * @see fr.iutvalence.info.dut.m2107.entities.Item#update(fr.iutvalence.info.dut.m2107.storage.Layer)
	 */
	@Override
	public void update(Layer layer) {
		this.pos.x += this.vel.x * DisplayManager.deltaTime();
		this.pos.y += this.vel.y * DisplayManager.deltaTime();
		this.remainingTime += DisplayManager.deltaTime();
		if(this.remainingTime >= DESTROY_TIME) layer.remove(this);
		super.update(layer);
	}
	
	/**
	 * Initialize the position, rotation and velocity of the ammunition
	 */
	public void initLaunch() {
		this.pos = new Vector2f(GameWorld.player.pos.x, GameWorld.player.pos.y);
		this.rot = GameWorld.player.getDegreeShoot();
		this.vel = new Vector2f(GameWorld.player.getShoot().x, GameWorld.player.getShoot().y);
		this.vel.scale(this.speed);
	}
	
	/**
	 * Add the weapon stats to the ammo
	 * @param weapon The weapon to take stats
	 */
	public void addWeaponStats(Weapon weapon) {
		this.damage += weapon.damage;
		this.knockback += weapon.knockback;
	}

	/**
	 * Return the damage of the ammo
	 * @return the damage of the ammo
	 */
	public int getDamage() {return damage;}
	
	/**
	 * Return the knockback of the ammo
	 * @return the knockback of the ammo
	 */
	public int getKnockback() {return knockback;}
	
	/**
	 * Return the velocity of the ammo
	 * @return the velocity of the ammo
	 */
	public Vector2f getVelocity() {return vel;}
	
	/**
	 * Return the speed of the ammo
	 * @return the speed of the ammo
	 */
	public int getSpeed() {return speed;}
}
