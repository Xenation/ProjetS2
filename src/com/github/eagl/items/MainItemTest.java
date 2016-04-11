package com.github.eagl.items;

import java.io.IOException;
import org.json.JSONException;

public class MainItemTest {
	
	public static void main(String[] args){
	
		Inventory inventory = new Inventory();
		
		try { ItemDatabase.createItemDatabase(); } catch (JSONException e) {} catch (IOException e) {}
		
		inventory.add(ItemDatabase.findItemName("Unicorne"), 50);
		inventory.add(ItemDatabase.findItemName("Bed"), 1);
		inventory.add(ItemDatabase.findItemName("Fire Plant"), 1);
		inventory.add(ItemDatabase.findItemName("Sunglasses"), 1);
		
		inventory.remove(ItemDatabase.findItemName("Unicorne"), 25);
		
		//inventory.sortValue((byte)1);
		//inventory.sortName((byte)1);
		//inventory.sortRarity((byte)1);
		//inventory.sortStack((byte)1);
		
		System.out.println(inventory);
	
	}
}