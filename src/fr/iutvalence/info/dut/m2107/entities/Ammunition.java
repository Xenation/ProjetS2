package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.models.Sprite;
import fr.iutvalence.info.dut.m2107.render.DisplayManager;
import fr.iutvalence.info.dut.m2107.storage.Layer;

public abstract class Ammunition extends Item {
	
	private static final Vector2f DEF_VEL = new Vector2f(0, 0);
	
	protected final int DESTROY_TIME = 5;
	protected float remainingTime = 0;
	
	protected int damage;
	protected int knockback;
	protected Vector2f vel;
	protected int speed;

	public Ammunition(Vector2f pos, float rot, Sprite spr,
					int id, String name, String description, Rarity rarity, int maxStack, int value,
					int damage, int knockback, Vector2f velocity, int speed) {
		super(pos, rot, spr, id, name, description, rarity, maxStack, value);
		this.damage = damage;
		this.knockback = knockback;
		this.vel = velocity;
		this.vel.scale(speed);
	}
	
	public Ammunition(Sprite spr,
					int id, String name, String description, Rarity rarity, int maxStack, int value,
					int damage, int knockback, int speed) {
		super(spr, id, name, description, rarity, maxStack, value);
		this.damage = damage;
		this.knockback = knockback;
		this.vel = DEF_VEL;
		this.speed = speed;
	}
	
	public Ammunition(Ammunition ammo) {
		super(ammo);
		this.damage = ammo.damage;
		this.knockback = ammo.knockback;
		this.speed = ammo.speed;
	}

	@Override
	public void update(Layer layer) {
		this.pos.x += this.vel.x * DisplayManager.deltaTime();
		this.pos.y += this.vel.y * DisplayManager.deltaTime();
		this.remainingTime += DisplayManager.deltaTime();
		if(this.remainingTime >= DESTROY_TIME) layer.remove(this);
		super.update(layer);
	}
	
	public void addWeaponStats(Weapon weapon) {
		this.damage += weapon.damage;
		this.knockback += weapon.knockback;
	}

	public int getDamage() {return damage;}
	public int getKnockback() {return knockback;}
	public Vector2f getVelocity() {return vel;}
	public int getSpeed() {return speed;}
}
