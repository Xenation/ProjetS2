package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.models.EntitySprite;
import fr.iutvalence.info.dut.m2107.render.DisplayManager;
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
	 * The pivot of the character's weapon
	 */
	protected Entity pivot = new Entity(new Vector2f(.65f, -.3f), SpriteDatabase.getEmptySpr(), null);
	
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
	public Character(Vector2f pos, EntitySprite spr) {
		super(pos, spr);
	}
	
	/**
	 * Constructor of a character
	 * @param pos The position of the character
	 * @param spr The sprite of the character
	 * @param col The collider of the character
	 */
	public Character(Vector2f pos, EntitySprite spr, Collider col) {
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
	public Character(Vector2f pos, float rot, EntitySprite spr, Collider col, Vector2f vel, float spd, int health, int armor,
			int jumpHeight) {
		super(pos, rot, spr, col, vel, spd, health, armor, jumpHeight);
	}
	
	/* (non-Javadoc)
	 * @see fr.iutvalence.info.dut.m2107.entities.LivingEntity#update(fr.iutvalence.info.dut.m2107.storage.Layer)
	 */
	@Override
	public void update(Layer layer) {
		//LivingEntity sword = new LivingEntity(new Vector2f(.7f, 0), 0, SpriteDatabase.getSwordSpr(), new Collider(SpriteDatabase.getSwordSpr()), new Vector2f(), 0, 10, 0, 0);
		//LivingEntity bow = new LivingEntity(new Vector2f(-0.3f, 0), 0, SpriteDatabase.getBowSpr(), new Collider(SpriteDatabase.getBowSpr()), new Vector2f(), 0, 10, 0, 0);
		
		if(this.itemOnHand != null) this.itemOnHand.update(layer); 
		
		this.vel.x = Maths.lerp(this.vel.x, 0, 0.25f);
		this.vel.y -= GameWorld.gravity * DisplayManager.deltaTime();
		
		this.col.checkCharacterContinuousCollision();
		
		this.col.checkStepDown();
		
		if(!(this instanceof Player) && this.vel.x > 0 && this.recoil == 0) {
			this.scale.setX(Maths.fastAbs(this.scale.x));
			this.pivot.pos.x = .65f;
		} else if(!(this instanceof Player) && this.vel.x < 0 && this.recoil == 0) {
			this.scale.setX(-Maths.fastAbs(this.scale.x));
			this.pivot.pos.x = -.65f;
		}
		pivot.setRotation(GameWorld.player.getDegreeShoot());
		
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

	/**
	 * Return the state of grounded of the character
	 * @return the state of grounded of the character
	 */
	public boolean isGrounded() {return isGrounded;}

	/**
	 * Return the previous state of grounded of the character
	 * @return the previous state of grounded of the character
	 */
	public boolean isPrevGrounded() {return prevGrounded;}
	
}
