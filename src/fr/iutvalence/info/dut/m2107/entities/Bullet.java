package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.models.Sprite;
import fr.iutvalence.info.dut.m2107.render.DisplayManager;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Layer;

public class Bullet extends Ammunition {

	public Bullet(Vector2f pos, float rot, Sprite spr,
				int id, String name, String description, Rarity rarity, int maxStack, int value,
				int damage, int knockback, Vector2f vel, int speed) {
		super(pos, rot, spr, id, name, description, rarity, maxStack, value, damage, knockback, vel, speed);
	}
	
	public Bullet(Sprite spr, Collider col,
				int id, String name, String description, Rarity rarity, int maxStack, int value,
				int damage, int knockback, int speed) {
		super(spr, col, id, name, description, rarity, maxStack, value, damage, knockback, speed);
	}

	public Bullet(Bullet bullet) {
		super(bullet);
	}

	@Override
	public void update(Layer layer) {
		if(this.col.isContinuousCollidingWithMap()) GameWorld.layerMap.getLayer(0).remove(this);
		this.pos.x += this.vel.x * DisplayManager.deltaTime();
		this.pos.y += this.vel.y * DisplayManager.deltaTime();
		super.update(layer);
	}

}
