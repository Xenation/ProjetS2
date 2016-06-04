package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.models.EntitySprite;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Layer;
import fr.iutvalence.info.dut.m2107.toolbox.Maths;

public class Zombie extends Character {

	public Zombie(Vector2f pos, EntitySprite spr, Collider col, short spd, int health, int jumpHeight) {
		super(pos, spr, col, spd, health, jumpHeight);
	}

	@Override
	public void update(Layer layer) {
		float moveX = GameWorld.player.pos.x - this.pos.x;
		if(moveX < 40 && moveX > -40 && this.recoil == 0) {
			if(moveX > 2) this.vel.x += this.spd/2;
			else if(moveX < -2) this.vel.x += -this.spd/2;
			else {
				Collider tmpCol = new Collider(this.col.getMin(), this.col.getMax());
				if(this.scale.x == 1) tmpCol.extendRight(2);
				else tmpCol.extendLeft(2);
				
				if(tmpCol.isCollidingWithPlayer())
					GameWorld.player.doDamage(1, 10 * (int)(this.scale.x/Maths.fastAbs(this.scale.x)));
			}
		}
		super.update(layer);
	}
}
