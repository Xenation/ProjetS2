package fr.iutvalence.info.dut.m2107.entities;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.inventory.Item;
import fr.iutvalence.info.dut.m2107.models.EntitySprite;

public class Chest extends LivingEntity {

	private List<Item> ownItem = new ArrayList<Item>();
	
	public Chest(Vector2f pos, EntitySprite spr, Collider col, int health, Item... item) {
		super(pos, spr, col, health);
		for (Item it : item) ownItem.add(it);
	}

}
