package fr.iutvalence.info.dut.m2107.entities;

import java.util.*;

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
	
	private static List<Ammunition> ammunitionDatabase = new ArrayList<Ammunition>();
	//private static List<Armor> armorDatabase = new ArrayList<Armor>();
	private static List<Weapon> weaponDatabase = new ArrayList<Weapon>();

	/**
	 * Create the ItemDatabase
	 */
	public static void create() {
		// AMMUNIITON
		//							TYPE	SPR							 			ID	NAME				DESC				RARITY			MAX		VAL		DMG		KNO		SPD
		ammunitionDatabase.add(new Arrow	(SpriteDatabase.getArrowSpr(),			0, 	"Arrow",			"A basic arrow", 	Rarity.POOR,	50,		1,		1,		1,		25));
		ammunitionDatabase.add(new Arrow	(SpriteDatabase.getSpectralArrowSpr(),	1,	"Spectral Arrow", 	"A spectral arrow",	Rarity.COMMON,	25,		5,		2,		1,		100));
		
		// WEAPON
		//						TYPE	SPR								ID	NAME		DESC				RARITY			MAX		VAL		DMG 	RNG		USE		KNOC
		weaponDatabase.add(new Bow		(SpriteDatabase.getBowSpr(),	0,	"Bow",		"A simple bow",		Rarity.POOR,	1,		3, 		3, 		0, 		1,		1));
		weaponDatabase.add(new Sword	(SpriteDatabase.getSwordSpr(),	1,	"Sword",	"A simple Sword",	Rarity.POOR,	1,		3, 		5, 		2, 		1,		1));
		
		itemDatabase.addAll(ammunitionDatabase);
		itemDatabase.addAll(weaponDatabase);
		for (Item item : itemDatabase) {
			System.out.println(item);
		}
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