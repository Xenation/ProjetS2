package fr.iutvalence.info.dut.m2107.inventory;

import org.lwjgl.Sys;
import org.lwjgl.util.vector.Vector3f;

import fr.iutvalence.info.dut.m2107.entities.Collider;
import fr.iutvalence.info.dut.m2107.entities.LivingEntity;
import fr.iutvalence.info.dut.m2107.models.EntitySprite;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Layer;
import fr.iutvalence.info.dut.m2107.storage.Layer.LayerStore;

public class Orb extends Ammunition {
	
	private float launchTime = Sys.getTime();
	
	private static final short lifeTime = 5;
	
	public Orb(EntitySprite spr, Collider col, short spd,
			short id, String name, String description, Rarity rarity, short maxStack,
			short value, short damage, short knockback, Vector3f color) {
		super(spr, col, spd, id, name, description, rarity, maxStack, value, damage, knockback, color);
	}

	@Override
	public void update(Layer layer) {
		if(launchTime + lifeTime*1000 < Sys.getTime()) GameWorld.layerMap.getStoredLayer(LayerStore.AMMUNITION).remove(this);
		
		this.col.updateColPos();
		this.col.checkContinuousCollision();
		
		if(piercingEntity != null || piercingTile != null) {
			if(piercingEntity != null) {
				if(piercingEntity instanceof LivingEntity)
					((LivingEntity)piercingEntity).doDamage(this.damage, this.rot < 90 && this.rot > -90 ? this.knockback : -this.knockback);
			} else GameWorld.layerMap.getStoredLayer(LayerStore.AMMUNITION).remove(this);
		}
		piercingEntity = null;
		super.update(layer);
	}
	
	public Orb copy() {
		Ammunition ammo = super.copy();
		Orb newOrb = new Orb((EntitySprite)ammo.getSprite(),
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
									ammo.color);
		return newOrb;
	}
}
