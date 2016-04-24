package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.enginetest.MainGameTester;
import fr.iutvalence.info.dut.m2107.entities.Character.CharacterType;
import fr.iutvalence.info.dut.m2107.items.AmmunitionItem;
import fr.iutvalence.info.dut.m2107.items.Effect;
import fr.iutvalence.info.dut.m2107.items.WeaponItem.WeaponType;
import fr.iutvalence.info.dut.m2107.models.Sprite;
import fr.iutvalence.info.dut.m2107.render.DisplayManager;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Layer;

public class WeaponEntity extends Entity {

	protected int damage;
	protected int range;
	protected float useTime;
	protected int knockback;
	protected Effect effect;
	
	protected WeaponType type;
	
	protected float remainingTime;
	
	protected Character owner;
	
	public WeaponEntity(WeaponType type, Vector2f pos, float rot, Sprite spr, Collider col, int damage, int range, float useTime, int knockback, Character owner) {
		super(pos, rot, spr, col);
		this.damage = damage;
		this.range = range;
		this.useTime = useTime;
		remainingTime = useTime;
		this.knockback = knockback;

		this.type = type;
		this.owner = owner;
	}

	public void use() {
		if(this.remainingTime >= this.useTime) {
			this.remainingTime = 0;
			switch (type) {
				case BOW:
					this.bowUse();
					break;
				case SWORD:
					this.useSword();
					break;
			}
		} else System.out.println((useTime - remainingTime) + "seconds left to use");
	}

	@Override
	public void update(Layer layer) {
		this.remainingTime += DisplayManager.deltaTime();
		this.pos = this.owner.pos;
	}
	
	private void bowUse() {
		if(owner.getType() == CharacterType.PLAYER) {
			AmmunitionItem arrow = GameWorld.player.getInventory().getArrow();
			if(arrow != null) {
				AmmunitionEntity arrowEnt = EntityDatabase.ammo(arrow, MainGameTester.degreeShoot, MainGameTester.shootX, MainGameTester.shootY, this);
				arrowEnt.addWeaponStats(this);
				GameWorld.layerMap.getLayer(0).add(arrowEnt);
				GameWorld.player.getInventory().remove(arrow, 1);
			} else System.out.println("No more arrow in inventory");
		}
	}
	
	private void useSword() {
		Collider tmpCol = new Collider(owner.col.getMin(), owner.col.getMax());
		tmpCol.extendRight(range);
		Entity ent = tmpCol.isCollidingWithEntity(GameWorld.layerMap.getLayer(1));
		if(ent != this.owner)
			if(ent instanceof LivingEntity) {
				((LivingEntity) ent).takeDamage(this.damage);
				System.out.println(this.damage + " damage to " + ent + "\t" + ((LivingEntity) ent).health + " hp left");
			}
	}
	
	public int getDamage() {return damage;}
	public int getRange() {return range;}
	public float getUseTime() {return useTime;}
	public int getKnockback() {return knockback;}
	public Effect getEffect() {return effect;}
	public Character getOwner() {return owner;}
}
