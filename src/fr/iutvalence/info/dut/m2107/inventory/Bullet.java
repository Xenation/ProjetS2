package fr.iutvalence.info.dut.m2107.inventory;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.entities.Collider;
import fr.iutvalence.info.dut.m2107.entities.LivingEntity;
import fr.iutvalence.info.dut.m2107.models.EntitySprite;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Layer;
import fr.iutvalence.info.dut.m2107.storage.Layer.LayerStore;

/**
 * A bullet ammunition
 * @author Voxelse
 *
 */
public class Bullet extends Ammunition {
	
	protected byte reboundCount;
	
	/**
	 * A constructor of a bullet
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
	public Bullet(Vector2f pos, float rot, EntitySprite spr, Collider col, Vector2f vel, short spd,
			short id, String name, String description, Rarity rarity, short maxStack, short value,
			short damage, short knockback, byte reboundCount) {
		super(pos, rot, spr, col, vel, spd, id, name, description, rarity, maxStack, value, damage, knockback);
		this.reboundCount = reboundCount;
	}
	
	/**
	 * A constructor of a bullet
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
	public Bullet(EntitySprite spr, Collider col, short spd,
			short id, String name, String description, Rarity rarity, short maxStack, short value,
			short damage, short knockback, byte reboundCount) {
		super(spr, col, spd, id, name, description, rarity, maxStack, value, damage, knockback);
		this.reboundCount = reboundCount;
	}

	/* (non-Javadoc)
	 * @see fr.iutvalence.info.dut.m2107.entities.Ammunition#update(fr.iutvalence.info.dut.m2107.storage.Layer)
	 */
	@Override
	public void update(Layer layer) {
		if(piercingEntity == null && piercingTile == null) {
			this.col.updateColPos();
			this.col.checkContinuousCollision();
			
			if(piercingEntity != null || piercingTile != null) {
				if(piercingEntity != null) {
					if(piercingEntity instanceof LivingEntity)
						((LivingEntity)piercingEntity).doDamage(this.damage, this.rot < 90 && this.rot > -90 ? this.knockback : -this.knockback);	
					GameWorld.layerMap.getStoredLayer(LayerStore.AMMUNITION).remove(this);
				} else {
					if (reboundCount > 0) {
						if(this.col.isOnDown(this.col, piercingTile) || this.col.isOnUp(this.col, piercingTile)) this.vel.y = -this.vel.y;
						if(this.col.isOnLeft(this.col, piercingTile) || this.col.isOnRight(this.col, piercingTile)) this.vel.x = -this.vel.x;
						this.piercingTile = null;
						reboundCount--;
					} else GameWorld.layerMap.getStoredLayer(LayerStore.AMMUNITION).remove(this);
				}
			}
		}
		
		super.update(layer);
	}
	
	public Bullet copy() {
		Ammunition ammo = super.copy();
		Bullet newBullet = new Bullet(ammo.getPosition(),
									ammo.getRotation(),
									(EntitySprite)ammo.getSprite(),
									ammo.getCollider(),
									ammo.getVelocity(),
									ammo.getSpeed(),
									ammo.getId(),
									ammo.name,
									ammo.description,
									ammo.rarity,
									ammo.MAX_STACK,
									ammo.value,
									ammo.damage,
									ammo.knockback,
									this.reboundCount);
		return newBullet;
	}
	
}