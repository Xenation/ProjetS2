package fr.iutvalence.info.dut.m2107.inventory;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.entities.Character;
import fr.iutvalence.info.dut.m2107.entities.Collider;
import fr.iutvalence.info.dut.m2107.entities.Entity;
import fr.iutvalence.info.dut.m2107.models.EntitySprite;
import fr.iutvalence.info.dut.m2107.render.DisplayManager;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Layer;
import fr.iutvalence.info.dut.m2107.tiles.Tile;

/**
 * An ammunition item
 * @author Voxelse
 *
 */
public class Ammunition extends Item {
	
	/**
	 * Time before destruction
	 */
	protected final short DESTROY_TIME = 60;
	
	/**
	 * Time remaining before destruction
	 */
	protected float remainingTime = 0;
	
	/**
	 * The damage of the ammo
	 */
	protected short damage;
	
	/**
	 * The knockback of the ammo
	 */
	protected short knockback;
	
	protected Entity piercingEntity = null;
	
	protected Tile piercingTile = null;

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
	public Ammunition(Vector2f pos, float rot, EntitySprite spr, Collider col, Vector2f vel, short spd,
				short id, String name, String description, Rarity rarity, short maxStack, short value,
				short damage, short knockback) {
		super(pos, rot, spr, col, vel, spd, id, name, description, rarity, maxStack, value);
		this.damage = damage;
		this.knockback = knockback;
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
	public Ammunition(EntitySprite spr, Collider col, short spd,
				short id, String name, String description, Rarity rarity, short maxStack, short value,
				short damage, short knockback) {
		super(spr, col, spd, id, name, description, rarity, maxStack, value);
		this.damage = damage;
		this.knockback = knockback;
	}

	/* (non-Javadoc)
	 * @see fr.iutvalence.info.dut.m2107.entities.Item#update(fr.iutvalence.info.dut.m2107.storage.Layer)
	 */
	@Override
	public void update(Layer layer) {
		if(this.id == 9) this.rot += 2;
		this.remainingTime += DisplayManager.deltaTime();
		if(this.remainingTime >= DESTROY_TIME) layer.remove(this);
		super.update(layer);
	}
	
	/**
	 * Initialize the collision, the position, rotation and velocity of the ammunition
	 * @param owner 
	 */
	public void initLaunch(Character owner) {
		this.col = new Collider(this.col);
		this.pos = new Vector2f(GameWorld.player.getPosition().x + GameWorld.player.getPivot().getPosition().x, GameWorld.player.getPosition().y + GameWorld.player.getPivot().getPosition().y);
		this.rot = GameWorld.player.getDegreeShoot();
		this.vel = new Vector2f(GameWorld.player.getShoot().x, GameWorld.player.getShoot().y);
		this.vel.scale(this.spd);
		if(this instanceof Arrow) {
			this.vel.x += GameWorld.player.getVelocity().x/2;
			this.vel.y += GameWorld.player.getVelocity().y/2;
		}
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
	public short getDamage() {return damage;}
	
	/**
	 * Return the knockback of the ammo
	 * @return the knockback of the ammo
	 */
	public short getKnockback() {return knockback;}


	public Tile getPiercingTile() {return piercingTile;}
	public void setPiercingTile(Tile piercingTile) {this.piercingTile = piercingTile;}
	
	public Entity getPiercingEntity() {return piercingEntity;}
	public void setPiercingEntity(Entity piercingEntity) {this.piercingEntity = piercingEntity;}
	
	public Ammunition copy() {
		Item item = super.copy();
		Ammunition newAmmo = new Ammunition(item.getPosition(),
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
											this.knockback);
		return newAmmo;
	}
	
}
