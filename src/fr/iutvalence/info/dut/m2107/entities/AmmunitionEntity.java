package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.items.Effect;
import fr.iutvalence.info.dut.m2107.models.Sprite;
import fr.iutvalence.info.dut.m2107.render.DisplayManager;
import fr.iutvalence.info.dut.m2107.storage.Layer;

public class AmmunitionEntity extends MovableEntity {

	protected final int DESTROY_TIME = 5;
	
	protected int damage;
	protected int knockback;
	protected Effect effect;
	
	protected float remainingTime = 0;

	protected WeaponEntity ownWeapon;
	
	public AmmunitionEntity(Vector2f pos, float rot, Sprite spr, Collider col, Vector2f vel, int damage, float spd, int knockback, WeaponEntity ownWeapon) {
		super(pos, rot, spr, col, vel, spd);
		this.vel.scale(spd);
		this.damage = damage;
		this.knockback = knockback;
		this.ownWeapon = ownWeapon;
	}
	
	@Override
	public void update(Layer layer) {
		this.remainingTime += DisplayManager.deltaTime();
		if(this.remainingTime >= DESTROY_TIME) layer.remove(this);
		super.update(layer);
	}
	
	public void addWeaponStats(WeaponEntity weapon) {
		this.damage += weapon.damage;
		this.knockback += weapon.knockback;
	}
}
