package fr.iutvalence.info.dut.m2107.items;

import fr.iutvalence.info.dut.m2107.models.Sprite;

public class ConsumableItem extends Item {
	
	public ConsumableItem(int id, Sprite spr, ItemType type, String name, String description, Rarity rarity, int maxStack, int value) {
		super(id, spr, type, name, description, rarity, maxStack, value);
		
	}

}