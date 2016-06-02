package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.Sys;
import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.inventory.Weapon;
import fr.iutvalence.info.dut.m2107.models.EntitySprite;
import fr.iutvalence.info.dut.m2107.render.DisplayManager;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Layer;
import fr.iutvalence.info.dut.m2107.toolbox.Maths;

public class TerrestrialCreature extends LivingEntity {

	/**
	 * The creature is or not grounded
	 */
	protected boolean isGrounded = true;
	
	/**
	 * The previous state of isGrounded
	 */
	protected boolean prevGrounded = true;
	
	/**
	 * The creature has or not step up
	 */
	protected boolean hasStepUp;
	
	
	public TerrestrialCreature(Vector2f pos, float rot, EntitySprite spr, Collider col, Vector2f vel, short spd, int health,
						int jumpHeight) {
		super(pos, rot, spr, col, vel, spd, health, jumpHeight);
	}

	@Override
	public void update(Layer layer) {
		
		this.vel.x = Maths.lerp(this.vel.x, 0, 0.25f);
		this.vel.y -= GameWorld.gravity * DisplayManager.deltaTime();
		
		this.col.checkContinuousCollision();
		
		if(this.vel.x > 0 && this.recoil == 0)
			if(this instanceof Character && ((Character)this).itemOnHand != null && ((Character)this).itemOnHand instanceof Weapon) {
				if(((Weapon)((Character)this).itemOnHand).getLockTime() < Sys.getTime())
					this.scale.setX(Maths.fastAbs(this.scale.x));
			} else this.scale.setX(Maths.fastAbs(this.scale.x));
		if(this.vel.x < 0 && this.recoil == 0) {
			if(this instanceof Character && ((Character)this).itemOnHand != null && ((Character)this).itemOnHand instanceof Weapon) {
				if(((Weapon)((Character)this).itemOnHand).getLockTime() < Sys.getTime())
					this.scale.setX(-Maths.fastAbs(this.scale.x));
			} else this.scale.setX(-Maths.fastAbs(this.scale.x));
		}
		super.update(layer);
	}
	
	
	/**
	 * Return the state of grounded of the creature
	 * @return the state of grounded of the creature
	 */
	public boolean isGrounded() {return isGrounded;}

	/**
	 * Return the previous state of grounded of the creature
	 * @return the previous state of grounded of the creature
	 */
	public boolean isPrevGrounded() {return prevGrounded;}

	
	
}
