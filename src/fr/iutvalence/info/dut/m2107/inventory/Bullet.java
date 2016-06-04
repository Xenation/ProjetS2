package fr.iutvalence.info.dut.m2107.inventory;

import org.lwjgl.util.vector.Vector3f;

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
	public Bullet(EntitySprite spr, Collider col, short spd,
			short id, String name, String description, Rarity rarity, short maxStack, short value,
			short damage, short knockback, Vector3f color, byte reboundCount) {
		super(spr, col, spd, id, name, description, rarity, maxStack, value, damage, knockback, color);
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
					if (reboundCount > 0 || reboundCount == -1) {
						if(this.col.isOnDown(this.col, piercingTile) || this.col.isOnUp(this.col, piercingTile)) {
							this.vel.y = -this.vel.y;
							this.piercingTile = null;
							if(reboundCount > 0) reboundCount--;
							this.update(layer);
							return;
						}
						if(this.col.isOnLeft(this.col, piercingTile) || this.col.isOnRight(this.col, piercingTile)) {
							this.vel.x = -this.vel.x;
							this.piercingTile = null;
							if(reboundCount > 0) reboundCount--;
							this.update(layer);
							return;
						}

					} else GameWorld.layerMap.getStoredLayer(LayerStore.AMMUNITION).remove(this);
				}
			}
		}
		
		super.update(layer);
	}
	
	public Bullet copy() {
		Ammunition ammo = super.copy();
		Bullet newBullet = new Bullet((EntitySprite)ammo.getSprite(),
									ammo.getCollider(),
									ammo.getSpeed(),
									ammo.getId(),
									ammo.name,
									ammo.description,
									ammo.rarity,
									ammo.MAX_STACK,
									ammo.value,
									ammo.damage,
									ammo.knockback,
									ammo.color,
									this.reboundCount);
		return newBullet;
	}
	
}