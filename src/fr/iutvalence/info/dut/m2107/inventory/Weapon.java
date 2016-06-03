package fr.iutvalence.info.dut.m2107.inventory;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.entities.Character;
import fr.iutvalence.info.dut.m2107.entities.Collider;
import fr.iutvalence.info.dut.m2107.models.EntitySprite;
import fr.iutvalence.info.dut.m2107.render.DisplayManager;
import fr.iutvalence.info.dut.m2107.storage.Layer;

/**
 * A weapon entity
 * @author Voxelse
 *
 */
public class Weapon extends Item {
	
	/**
	 * The damage of the weapon
	 */
	protected final short damage;
	
	/**
	 * The range of the weapon
	 */
	protected final short range;
	
	/**
	 * The delay between two usage
	 */
	protected final float useTime;
	
	/**
	 * The knockback of the weapon
	 */
	protected final short knockback;
	
	/**
	 * The remaining time before the next usage
	 */
	protected float remainingTime = 0;
	
	protected float lockTime;
	
	protected short handRotation;

	/**
	 * Constructor of a weapon
	 * @param spr The sprite of the weapon
	 * @param id The id of the weapon
	 * @param name The name of the weapon
	 * @param description The description of the weapon
	 * @param rarity The rarity of the weapon
	 * @param maxStack The maximum stack of the weapon
	 * @param value The value of the weapon
	 * @param damage The damage of the weapon
	 * @param range The range of the weapon
	 * @param useTime The use time of the weapon
	 * @param knockback The knockback of the weapon
	 */
	public Weapon(Vector2f pos, float rot, EntitySprite spr, Collider col, Vector2f vel, short spd,
			short id, String name, String description, Rarity rarity, short maxStack, short value,
			short damage, short range, float useTime, short knockback, short handRotation) {
		super(pos, rot, spr, col, vel, spd, id, name, description, rarity, maxStack, value);
		this.damage = damage;
		this.range = range;
		this.useTime = useTime;
		this.knockback = knockback;
		this.handRotation = handRotation;
	}
	
	/**
	 * Constructor of a weapon
	 * @param spr The sprite of the weapon
	 * @param id The id of the weapon
	 * @param name The name of the weapon
	 * @param description The description of the weapon
	 * @param rarity The rarity of the weapon
	 * @param maxStack The maximum stack of the weapon
	 * @param value The value of the weapon
	 * @param damage The damage of the weapon
	 * @param range The range of the weapon
	 * @param useTime The use time of the weapon
	 * @param knockback The knockback of the weapon
	 */
	public Weapon(EntitySprite spr,
			short id, String name, String description, Rarity rarity, short maxStack, short value,
			short damage, short range, float useTime, short knockback, short handRotation) {
		super(spr, id, name, description, rarity, maxStack, value);
		this.damage = damage;
		this.range = range;
		this.useTime = useTime;
		this.knockback = knockback;
		this.handRotation = handRotation;
	}

	/**
	 * Use the weapon
	 * @param owner The owner of the weapon
	 */
	public void use(Character owner) {
		return;
	}
	
	/* (non-Javadoc)
	 * @see fr.iutvalence.info.dut.m2107.entities.Item#update(fr.iutvalence.info.dut.m2107.storage.Layer)
	 */
	@Override
	public void update(Layer layer) {
		this.remainingTime -= DisplayManager.deltaTime();
	}

	/**
	 * Return the damage of the weapon
	 * @return the damage of the weapon
	 */
	public short getDamage() {return damage;}
	
	/**
	 * Return the range of the weapon
	 * @return the range of the weapon
	 */
	public short getRange() {return range;}
	
	/**
	 * Return the use time of the weapon
	 * @return the use time of the weapon
	 */
	public float getUseTime() {return useTime;}
	
	/**
	 * Return the knockback of the weapon
	 * @return the knockback of the weapon
	 */
	public short getKnockback() {return knockback;}
	
	/**
	 * Return the remaining time of the weapon
	 * @return the remaining time of the weapon
	 */
	public float getRemainingTime() {return remainingTime;}

	public float getLockTime() {return lockTime;}
	
	public short getHandRotation() {return handRotation;}
	
	public Weapon copy() {
		Item item = super.copy();
		Weapon newWeapon = new Weapon(item.getPosition(),
											item.getRotation(),
											(EntitySprite)item.getSprite(),
											item.getCollider(),
											item.getVelocity(),
											item.getSpeed(),
											item.getId(),
											item.name,
											item.description,
											item.rarity,
											item.MAX_STACK,
											item.value,
											this.damage,
											this.range,
											this.useTime,
											this.knockback,
											this.handRotation);
		return newWeapon;
	}
}
