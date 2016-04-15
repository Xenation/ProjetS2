package fr.iutvalence.info.dut.m2107.tiles;

import java.util.ArrayList;

/**
 * Used to easily build (instantiate) tiles
 * @author Xenation
 *
 */
public class TileBuilder {
	
	/**
	 * Instantiates a new tile using the specified type and coordinates
	 * Depending on the type the created tile will be a sub-class of Tile or Tile
	 * @param type the type of the tile
	 * @param x the x coordinate of the tile
	 * @param y the y coordinate of the tile
	 * @return the instantiated tile
	 */
	public static Tile buildTile(TileType type, int x, int y) {
		switch (type) {
		case Dirt:
		case Stone:
		case Grass:
		case Log:
		case Leaves:
			return new Tile(type, x, y);
		case Fader:
			return new FadingTile(type, x, y);
		case Spikes:
			return new DamagingTile(type, x, y);
		default:
			return new Tile(type, x, y);
		}
	}
	
	/**
	 * Returns a list of strings that represent the specified tile's state
	 * More or less and different stats will be returned depending on the tile's type
	 * @param tile the tile to debug
	 * @return a list of strings that contains the stats of the tile 
	 */
	public static ArrayList<String> getStats(Tile tile) {
		ArrayList<String> stats = new ArrayList<String>();
		stats.add("type = "+tile.type.name());
		stats.add("x = "+tile.x);
		stats.add("y = "+tile.y);
		switch (tile.type) {
		case Dirt:
		case Stone:
		case Grass:
		case Log:
		case Leaves:
			break;
		case Fader:
			stats.add("time = "+((FadingTile)tile).timeRemaining);
			break;
		case Spikes:
			stats.add("dmg = "+((DamagingTile)tile).damage);
			break;
		default:
			break;
		}
		return stats;
	}
	
}
