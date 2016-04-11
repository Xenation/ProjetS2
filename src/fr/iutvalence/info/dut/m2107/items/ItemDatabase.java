package fr.iutvalence.info.dut.m2107.items;

import java.io.*;
import java.util.*;
import org.json.*;

/**
 * This class create and initialize a List of item which is the ItemDatabase of the game
 * @author boureaue
 *
 */

public class ItemDatabase {
	
	/**
	 * A list of Item which contain all the items of the game
	 */
	public static List<Item> itemDatabase = new ArrayList<Item>();

	/**
	 * Create the ItemDatabase from a JSON file
	 * @throws JSONException
	 * @throws IOException
	 */
	public static void createItemDatabase() throws JSONException, IOException {
			
		File file = new File("json/item.json");
		FileReader fileReader;
		fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		
		String str = bufferedReader.readLine();
		String tmp = bufferedReader.readLine();
		while(tmp != null) {
			str += tmp;
			tmp = bufferedReader.readLine();
		}
		bufferedReader.close();
		
		JSONArray arr = new JSONArray(str);
		
		for (int i = 0; i < arr.length(); i++) {
			itemDatabase.add(new Item(arr.getJSONObject(i).getInt("id"),
											arr.getJSONObject(i).getString("name"),
											arr.getJSONObject(i).getString("description"),
											Rarity.valueOf(arr.getJSONObject(i).getString("rarity")),
											arr.getJSONObject(i).getInt("maxStack"),
											arr.getJSONObject(i).getInt("value")));	
		}
	}
	
	/**
	 * Return an item from the ItemDatabase with a certain name
	 * @param name The name you want to find
	 * @return the item with the specified name
	 */
	public static Item findItemName(String name) {
		for (Item item : itemDatabase) {
			if(item.Name().equals(name))
				return item;
		}
		return null;
	}
	
	/**
	 * Return a list which contain all the items of the ItemDatabase with a specified Rarity
	 * @param rarity The Rarity you want to filter
	 * @return a list of items with a Rarity filter from the ItemDatabase
	 */
	public static List<Item> findRarity(Rarity rarity) {
		List<Item> rarityList = new ArrayList<Item>();
		for (Item item : itemDatabase) {
			if(item.Rarity() == rarity)
				rarityList.add(item);
		}
		return rarityList;
	}
}