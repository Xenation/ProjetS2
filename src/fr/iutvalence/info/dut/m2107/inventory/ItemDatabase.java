package fr.iutvalence.info.dut.m2107.inventory;

import java.util.*;

import fr.iutvalence.info.dut.m2107.entities.Collider;
import fr.iutvalence.info.dut.m2107.entities.SpriteDatabase;

/**
 * This class create and initialize a List of item which is the ItemDatabase of the game
 * @author boureaue
 *
 */

public class ItemDatabase {
	
	/**
	 * A list of Item which contain all the items of the game
	 */
	private static List<Item> itemDatabase = new ArrayList<Item>();

	/**
	 * Create the ItemDatabase
	 */
	public static void create() {
		// AMMUNIITON
		//					TYPE	SPR							 			COL											ID	NAME				DESC					RARITY			MAX		VAL		DMG		KNO		SPD
		itemDatabase.add(new Arrow	(SpriteDatabase.getArrowSpr(),			new Collider(-.01f, -.01f, .01f, .01f),		0, 	"Arrow",			"A basic arrow", 		Rarity.POOR,	50,		1,		1,		1,		25));
		itemDatabase.add(new Arrow	(SpriteDatabase.getLightningArrowSpr(),	new Collider(-.01f, -.01f, .01f, .01f),		1,	"Lightning Arrow", 	"A lightning arrow",	Rarity.COMMON,	25,		5,		2,		20,		100));
		
		// WEAPON
		//					TYPE	SPR								ID	NAME		DESC				RARITY			MAX		VAL		DMG 	RNG		USE		KNOC
		itemDatabase.add(new Bow	(SpriteDatabase.getBowSpr(),	2,	"Bow",		"A simple bow",		Rarity.POOR,	1,		3, 		3, 		0, 		.5f,	3));
		itemDatabase.add(new Sword	(SpriteDatabase.getSwordSpr(),	3,	"Sword",	"A simple Sword",	Rarity.POOR,	1,		3, 		5, 		3, 		.25f,	7));
		itemDatabase.add(new Sword	(SpriteDatabase.getSwordSpr(),	4,	"Sword1",	"A simple Sword1",	Rarity.POOR,	1,		3, 		5, 		3, 		.25f,	7));
		itemDatabase.add(new Sword	(SpriteDatabase.getSwordSpr(),	5,	"Sword2",	"A simple Sword2",	Rarity.POOR,	1,		3, 		5, 		3, 		.25f,	7));
		itemDatabase.add(new Sword	(SpriteDatabase.getSwordSpr(),	6,	"Sword3",	"A simple Sword3",	Rarity.POOR,	1,		3, 		5, 		3, 		.25f,	7));
		itemDatabase.add(new Sword	(SpriteDatabase.getSwordSpr(),	7,	"Sword4",	"A simple Sword4",	Rarity.POOR,	1,		3, 		5, 		3, 		.25f,	7));
		itemDatabase.add(new Sword	(SpriteDatabase.getSwordSpr(),	8,	"Sword5",	"A simple Sword5",	Rarity.POOR,	1,		3, 		5, 		3, 		.25f,	7));
	}
	
	/**
	 * Create the ItemDatabase from a JSON file
	 * @throws JSONException
	 * @throws IOException
	 */
	/*public static void createItemDatabase() throws JSONException, IOException {
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
		
	}*/
	
	/**
	 * Return an item from the ItemDatabase with a certain name
	 * @param name The name you want to find
	 * @return the item with the specified name
	 */
	public static Item findItemName(String name) {
		for (Item item : itemDatabase) {
			if(item.getName().equals(name))
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
			if(item.getRarity() == rarity)
				rarityList.add(item);
		}
		return rarityList;
	}
	
	/**
	 * Get an item from the itemDatabase
	 * @param index The index of the item
	 * @return A new instance of the item desired
	 */
	public static Item get(int index) {
		Item item = itemDatabase.get(index);
		if(item instanceof Arrow)
			return new Arrow((Arrow)item);
		if(item instanceof Bullet)
			return new Bullet((Bullet)item);
		
		if(item instanceof Bow)
			return new Bow((Bow)item);
		if(item instanceof Sword)
			return new Sword((Sword)item);
		return null;
	}
}