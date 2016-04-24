package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.models.Sprite;
import fr.iutvalence.info.dut.m2107.storage.Layer;

public class LivingEntity extends MovableEntity {

	private static final int DEF_HEALTH = 10;
	private static final int DEF_ARMOR = 10;
	private static final int DEF_JUMP_HEIGHT = 15;
	
	protected int health;
	protected int armor;
	protected int jumpHeight;
	
	public LivingEntity(Vector2f pos, float rot, Sprite spr, Collider col, Vector2f vel, float spd, int health, int armor, int jumpHeight) {
		super(pos, rot, spr, col, vel, spd);
		this.health = health;
		this.armor = armor;
		this.jumpHeight = jumpHeight;
	}
	
	public LivingEntity(Vector2f pos, Sprite spr) {
		super(pos, spr);
		this.health = DEF_HEALTH;
		this.armor = DEF_ARMOR;
		this.jumpHeight = DEF_JUMP_HEIGHT;
	}


	public LivingEntity() {
		super();
		this.health = DEF_HEALTH;
		this.armor = DEF_ARMOR;
		this.jumpHeight = DEF_JUMP_HEIGHT;
	}
	
	@Override
	public void update(Layer layer) {
		super.update(layer);
		if(this.health <= 0) layer.remove(this);
	}

	public void takeDamage(int damage) {
		this.health -= damage;
	}

	public int getHealth() {return health;}

	public int getArmor() {return armor;}

	public int getJumpHeight() {return jumpHeight;}

}