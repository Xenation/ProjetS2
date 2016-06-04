package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.Sys;
import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.models.EntitySprite;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Layer;
import fr.iutvalence.info.dut.m2107.toolbox.Maths;

public class Slime extends TerrestrialCreature {

	private int jumpDelay;
	
	private short jumpWaitTime = 2000;
	
	public Slime(Vector2f pos, EntitySprite spr, Collider col, short spd, int health, int jumpHeight) {
		super(pos, spr, col, spd, health, jumpHeight);
	}

	@Override
	public void update(Layer layer) {
		float moveX = GameWorld.player.pos.x - this.pos.x;
		if(moveX < 40 && moveX > -40 && this.recoil == 0) {
			if(this.isGrounded) {
					if(moveX < 0) this.spd = (short) -Maths.fastAbs(this.spd);
					else if(moveX > 0) this.spd = (short) Maths.fastAbs(this.spd);
			}
			if(this.recoil == 0) {
				if(!this.isGrounded) this.vel.x += this.spd;
				
				if(this.isGrounded && jumpDelay < Sys.getTime()) {
					jumpDelay = (int) Sys.getTime() + jumpWaitTime;
					this.vel.y = this.jumpHeight;
				}
				
				if(this.col.isCollidingWithPlayer())
					GameWorld.player.doDamage(1, 10 * (int)(this.scale.x/Maths.fastAbs(this.scale.x)));
			}
		}
		super.update(layer);
	}
	
}
