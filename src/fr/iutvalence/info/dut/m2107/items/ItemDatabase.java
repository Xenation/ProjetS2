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
	 * Create the ItemDatabase
	 */
	public static void create() {
		double start = (double)System.currentTimeMillis();
		
		itemDatabase.add(new Item(0,	"Fire Plant",	"A plant who shoots fire !",	Rarity.POOR,	50,	1));
		itemDatabase.add(new Item(1,	"Bed",			"Sleepy ?",						Rarity.COMMON,	1,	15));
		itemDatabase.add(new Item(2,	"Unicorn",		"A fabulous mount.",			Rarity.RAINBOW,	99,	999));
		itemDatabase.add(new Item(3,	"Sunglasses",	"It will give you cool.",		Rarity.RARE,	5,	100));
		
		double end = (double)System.currentTimeMillis();
		System.out.print("Create : " + (end - start) + '\t');
	}
	
	/**
	 * Create the ItemDatabase from a JSON file
	 * @throws JSONException
	 * @throws IOException
	 */
	public static void createItemDatabase() throws JSONException, IOException {
		//
		double start = (double)System.currentTimeMillis();
		
		File file = new File("json/item.json");
		FileReader fileReader = new FileReader(file);
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
		//
		double end = (double)System.currentTimeMillis();
		System.out.print("Read : " + (end - start) + '\t');
		//
		double startWrite = (double)System.currentTimeMillis();
		
		File fileToWrite = new File("json/itemWrite.json");
		fileToWrite.createNewFile();
		FileWriter fileWriter = new FileWriter(fileToWrite.getAbsoluteFile());
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		
		JSONArray array = new JSONArray();
		for (Item item : itemDatabase) {
			JSONObject obj = new JSONObject();
			obj.put("id", item.id());
			obj.put("name", item.name());
			obj.put("description", item.description());
			obj.put("rarity", item.rarity());
			obj.put("maxStack", item.max_stack());
			obj.put("value", item.value());
			array.put(item.id(), obj);
		}
		bufferedWriter.write(array.toString());
		bufferedWriter.close();
		//
		double endWrite = (double)System.currentTimeMillis();
		System.out.println("Write : " + (endWrite - startWrite));
		
	}
	
	/**
	 * Return an item from the ItemDatabase with a certain name
	 * @param name The name you want to find
	 * @return the item with the specified name
	 */
	public static Item findItemName(String name) {
		for (Item item : itemDatabase) {
			if(item.name().equals(name))
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
			if(item.rarity() == rarity)
				rarityList.add(item);
		}
		return rarityList;
	}
}