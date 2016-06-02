package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.Sys;
import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.models.EntitySprite;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Layer;
import fr.iutvalence.info.dut.m2107.storage.Layer.LayerStore;
import fr.iutvalence.info.dut.m2107.toolbox.Maths;

public class Slime extends TerrestrialCreature {

	private int jumpDelay;
	
	private short jumpWaitTime = 2000;
	
	public Slime(Vector2f pos, float rot, EntitySprite spr, Collider col, Vector2f vel, float spd, int health,
			int jumpHeight) {
		super(pos, rot, spr, col, vel, spd, health, jumpHeight);
	}

	@Override
	public void update(Layer layer) {
		if(this.isGrounded) {
			float moveX = GameWorld.player.pos.x - this.pos.x;
			if(moveX < 0) this.spd = -Maths.fastAbs(this.spd);
			else if(moveX > 0) this.spd = Maths.fastAbs(this.spd);
		}
		if(this.recoil == 0) {
			if(!this.isGrounded) this.vel.x += this.spd;
			
			if(this.isGrounded && jumpDelay < Sys.getTime()) {
				jumpDelay = (int) Sys.getTime() + jumpWaitTime;
				this.vel.y = this.jumpHeight;
			}
			
			Entity ent = this.col.isCollidingWithEntity(new Layer[] {GameWorld.layerMap.getStoredLayer(LayerStore.PLAYER)});
			if(ent == GameWorld.player)
				((LivingEntity) ent).doDamage(1, 10 * (int)(this.scale.x/Maths.fastAbs(this.scale.x)));
		}
		super.update(layer);
	}
	
}
