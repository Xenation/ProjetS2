package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.models.Sprite;
import fr.iutvalence.info.dut.m2107.render.DisplayManager;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Layer;

public class ArrowEntity extends AmmunitionEntity {
	
	protected boolean isPierce = false;
	
	public ArrowEntity(Vector2f pos, float rot, Sprite spr, Collider col, Vector2f vel, int dmg, float spd, int knockback, WeaponEntity ownWeapon) {
		super(pos, rot, spr, col, vel, dmg, spd, knockback, ownWeapon);
	}

	@Override
	public void update(Layer layer) {
		
		if(!isPierce) {
			this.vel.y -= GameWorld.gravity * DisplayManager.deltaTime();
		
			if(this.vel.y >= 0) this.rot = (float) (Math.atan(this.vel.x / this.vel.y)*180/Math.PI-45);
			else this.rot = (float) (Math.atan(this.vel.x / this.vel.y)*180/Math.PI+135);
			
			this.col.updateColPos();
			if(this.col.isContinuousCollidingWithMap()) this.isPierce = true;
			
			// temporary code for entity collision detection
			/*for (Entity entity : layer) {
				if(entity != this && GameWorld.player != entity && !this.col.isColliding(this.col, entity.col)) {
					this.isPierce = true;
					this.vel = new Vector2f();
				}
			}*/
		}
		
		super.update(layer);
	}
	
}
