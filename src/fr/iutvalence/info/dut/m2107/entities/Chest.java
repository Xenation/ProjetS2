package fr.iutvalence.info.dut.m2107.entities;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.inventory.Item;
import fr.iutvalence.info.dut.m2107.models.EntitySprite;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Layer;
import fr.iutvalence.info.dut.m2107.storage.Layer.LayerStore;

public class Chest extends LivingEntity {

	private List<Item> ownItem = new ArrayList<Item>();
	
	public Chest(Vector2f pos, EntitySprite spr, Collider col, int health, Item... item) {
		super(pos, spr, col, health);
		for (Item it : item)
			ownItem.add(Item.copyDropableItem(it, pos.x, pos.y));
	}

	@Override
	public void update(Layer layer) {
		super.update(layer);
	}	
	
	@Override
	protected void dead(Layer layer) {
		for (Item it : ownItem) {
			Vector2f nextVel = new Vector2f((float) (Math.random()*4-2), (float) (Math.random()*2+3));
			it.setVelocity(nextVel);
			GameWorld.layerMap.getStoredLayer(LayerStore.ITEM).add(it);
		}
		super.dead(layer);
	}
}
