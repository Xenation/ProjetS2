package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.Sys;
import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.inventory.Item;
import fr.iutvalence.info.dut.m2107.inventory.Weapon;
import fr.iutvalence.info.dut.m2107.models.EntitySprite;
import fr.iutvalence.info.dut.m2107.storage.Layer;
import fr.iutvalence.info.dut.m2107.toolbox.Maths;

/**
 * A character entity
 * @author Voxelse
 *
 */
public class Character extends TerrestrialCreature{
	
	/**
	 * The item on hand
	 */
	protected Item itemOnHand;
	
	/**
	 * The pivot of the character's weapon
	 */
	protected Entity pivot = new Entity(new Vector2f(.725f, -.3f), SpriteDatabase.getEmptySpr(), null);

	/**
	 * Constructor of a character
	 * @param pos The position of the character
	 * @param rot The rotation of the character
	 * @param spr The sprite of the character
	 * @param col The collider of the character
	 * @param vel The velocity of the character
	 * @param spd The speed of the character
	 * @param health The health of the character
	 * @param armor The armor of the character
	 * @param jumpHeight The jump height of the character
	 */
	public Character(Vector2f pos, float rot, EntitySprite spr, Collider col, Vector2f vel, short spd, int health, int jumpHeight) {
		super(pos, rot, spr, col, vel, spd, health, jumpHeight);
	}
	
	/* (non-Javadoc)
	 * @see fr.iutvalence.info.dut.m2107.entities.LivingEntity#update(fr.iutvalence.info.dut.m2107.storage.Layer)
	 */
	@Override
	public void update(Layer layer) {
		
		if(this.itemOnHand != null) {
			if(this.itemOnHand instanceof Weapon) this.pivot.rot = Maths.lerp(this.pivot.rot, ((Weapon)this.itemOnHand).getHandRotation(), 0.05f);
			this.itemOnHand.update(layer);
		}
		
		if(this.vel.x > 0 && this.recoil == 0 && this.itemOnHand != null && ((Weapon)this.itemOnHand).getLockTime() < Sys.getTime()) {
			this.pivot.pos.x = Maths.fastAbs(this.pivot.pos.x);
			if(this.itemOnHand != null)
				this.pivot.scale.x = 1;
		} else if(this.vel.x < 0 && this.recoil == 0 && this.itemOnHand != null && ((Weapon)this.itemOnHand).getLockTime() < Sys.getTime()) {
			this.pivot.pos.x = -Maths.fastAbs(this.pivot.pos.x);
			if(this.itemOnHand != null)
				this.pivot.scale.x = -1;
		}
		
		super.update(layer);
	}
	
	/**
	 * 
	 * @return
	 */
	public Entity getPivot() {return pivot;}
	
	/**
	 * 
	 */
	public void initPivot() {pivot.initLayer();}
	
	/**
	 * Set the item on hand of the character
	 * @param itemOnHand The item to set
	 */
	public void setItemOnHand(Item itemOnHand) {this.itemOnHand = itemOnHand;}
	
	/**
	 * Return the item on hand of the character
	 * @return the item on hand of the character
	 */
	public Item getItemOnHand() {return this.itemOnHand;}
	
}
