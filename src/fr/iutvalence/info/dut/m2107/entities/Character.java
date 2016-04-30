package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.models.Sprite;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Layer;
import fr.iutvalence.info.dut.m2107.toolbox.Maths;

/**
 * A character entity
 * @author Voxelse
 *
 */
public class Character extends LivingEntity{
	
	/**
	 * The character is or not grounded
	 */
	protected boolean isGrounded = true;
	
	/**
	 * The previous state of isGrounded
	 */
	protected boolean prevGrounded = true;
	
	/**
	 * The character has or not step up
	 */
	protected boolean hasStepUp;
	
	/**
	 * The item on hand
	 */
	protected Item itemOnHand;
	
	/**
	 * The character's facing direction
	 */
	protected boolean isFacingRight = true;
	
	/**
	 * Constructor of a character
	 */
	public Character() {
		super();
	}
	
	/**
	 * Constructor of a character
	 * @param pos The position of the character
	 * @param spr The sprite of the character
	 */
	public Character(Vector2f pos, Sprite spr) {
		super(pos, spr);
	}
	
	/**
	 * Constructor of a character
	 * @param pos The position of the character
	 * @param spr The sprite of the character
	 * @param col The collider of the character
	 */
	public Character(Vector2f pos, Sprite spr, Collider col) {
		super(pos, spr, col);
	}

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
	public Character(Vector2f pos, float rot, Sprite spr, Collider col, Vector2f vel, float spd, int health, int armor,
			int jumpHeight) {
		super(pos, rot, spr, col, vel, spd, health, armor, jumpHeight);
	}
	
	/* (non-Javadoc)
	 * @see fr.iutvalence.info.dut.m2107.entities.LivingEntity#update(fr.iutvalence.info.dut.m2107.storage.Layer)
	 */
	@Override
	public void update(Layer layer) {

		this.col.checkCharacterContinuousCollision();
		
		this.col.checkStepDown();
		
		if(GameWorld.player.getShoot().x > 0) isFacingRight = true;
		if(GameWorld.player.getShoot().x < 0) isFacingRight = false;
		
		if(isFacingRight) this.scale.setX(Maths.fastAbs(this.scale.x));
		else this.scale.setX(-Maths.fastAbs(this.scale.x));
		
		/*for (Entity entity : layer) {
			if(entity != this) this.col.checkCollision(this, entity);
		}*/
		super.update(layer);
	}
	
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

	/**
	 * Return the state of grounded of the character
	 * @return the state of grounded of the character
	 */
	public boolean isGrounded() {return isGrounded;}

	/**
	 * Return the state of the facing character's direction
	 * @return the state of the facing character's direction
	 */
	public boolean isFacingRight() {return isFacingRight;}
}
