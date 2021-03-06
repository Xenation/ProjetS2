package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.models.EntitySprite;
import fr.iutvalence.info.dut.m2107.render.DisplayManager;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Layer;
import fr.iutvalence.info.dut.m2107.toolbox.Maths;

public class Rat extends TerrestrialCreature {

	public boolean wallWalk = false;
	public boolean previousWalk = false;
	
	private float atlasCount;
	
	public Rat(Vector2f pos, EntitySprite spr, Collider col, short spd, int health, int jumpHeight) {
		super(pos, spr, col, spd, health, jumpHeight);
	}

	@Override
	public void update(Layer layer) {
		Vector2f move = new Vector2f();
		Vector2f.sub(GameWorld.player.pos, this.pos, move);
		if(move.length() > -30 && move.length() < 30 && this.recoil == 0) {
			if(!wallWalk && previousWalk) {
				if(this.scale.x > 0)
					this.vel.y = -this.spd;
				else this.vel.y = this.spd;
			}
			
			if(this.isGrounded || wallWalk) atlasCount += DisplayManager.deltaTime()*15;
			if(atlasCount >= 3) atlasCount -= 3;
			if(Maths.fastFloor(atlasCount) != this.getSprite().getAtlasIndex())
				this.getSprite().updateAtlasIndex(Maths.fastFloor(atlasCount));
			
			previousWalk = wallWalk;
			
			if(wallWalk) {
				this.rot = -90;
				this.vel.y += Maths.fastAbs(this.spd);
				this.vel.y += GameWorld.gravity * DisplayManager.deltaTime();
			} else {
				this.rot = 0;
			}
			this.vel.x += this.spd;
			
			Collider tmpCol = new Collider(this.col.getMin(), this.col.getMax());
			
			if(this.scale.x > 0) tmpCol.extendRight(this.col.getW());
			else tmpCol.extendLeft(this.col.getW());
			if(tmpCol.isCollidingWithPlayer()) {
				GameWorld.player.doDamage(0, 10 * (int)(this.scale.x/Maths.fastAbs(this.scale.x)));
			}
		}
		super.update(layer);
	}
}
