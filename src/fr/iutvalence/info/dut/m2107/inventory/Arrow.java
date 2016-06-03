package fr.iutvalence.info.dut.m2107.inventory;

import org.lwjgl.util.vector.Vector3f;

import fr.iutvalence.info.dut.m2107.entities.Collider;
import fr.iutvalence.info.dut.m2107.entities.LivingEntity;
import fr.iutvalence.info.dut.m2107.models.EntitySprite;
import fr.iutvalence.info.dut.m2107.render.DisplayManager;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Layer;
import fr.iutvalence.info.dut.m2107.storage.Layer.LayerStore;

/**
 * An arrow ammunition
 * @author Voxelse
 *
 */
public class Arrow extends Ammunition {
	
	public Vector3f color = null;
	
	/**
	 * Constructor of an arrow
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
	public Arrow(EntitySprite spr, Collider col, short spd,
				short id, String name, String description, Rarity rarity, short maxStack, short value,
				short damage, short knockback, Vector3f color) {
		super(spr, col, spd, id, name, description, rarity, maxStack, value, damage, knockback);
		this.color = color;
	}

	/**
	 * Constructor of an arrow
	 * @param arrow The arrow to copy
	 */
	public Arrow(Arrow arrow) {
		super(arrow);
		this.color = arrow.color;
	}

	/* (non-Javadoc)
	 * @see fr.iutvalence.info.dut.m2107.entities.Ammunition#update(fr.iutvalence.info.dut.m2107.storage.Layer)
	 */
	@Override
	public void update(Layer layer) {
		if(piercingEntity == null && piercingTile == null) {
			this.vel.y -= GameWorld.gravity * DisplayManager.deltaTime();
			
			if(this.vel.y >= 0) this.rot = (float) (Math.atan(this.vel.x / this.vel.y)*180/Math.PI-90);
			else this.rot = (float) (Math.atan(this.vel.x / this.vel.y)*180/Math.PI+90);
			
			this.col.updateColPos();
			
			float extendSideX = (float) (Math.cos((rot)*Math.PI/180)*this.spr.getSize().x/2.5f);
			this.col.extendRight(extendSideX);
			this.col.extendLeft(-extendSideX);
			
			float extendSideY = (float) (Math.sin((rot)*Math.PI/180)*this.spr.getSize().y/2.5f);
			this.col.extendUp(-extendSideY);
			this.col.extendDown(extendSideY);
			
			this.col.checkContinuousCollision();
			
			if(piercingEntity != null) {
				if(piercingEntity instanceof LivingEntity)
					((LivingEntity)piercingEntity).doDamage(this.damage, this.rot < 90 && this.rot > -90 ? this.knockback : -this.knockback);
				
				this.setParent(piercingEntity);
				GameWorld.layerMap.getStoredLayer(LayerStore.AMMUNITION).remove(this);
			}
		}
		super.update(layer);
	}

}
