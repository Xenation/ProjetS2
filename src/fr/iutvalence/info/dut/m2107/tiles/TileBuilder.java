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
		case Sand:
			return new FallingTile(type, x, y);
		case Creator:
			return new CreatingTile(type, x, y, TileType.Sand);
		case Piston:
			return new PushingTile(type, x, y);
		case PistonArm:
			return new DependantTile(type, x, y);
		case Water:
			return new LiquidTile(type, x, y);
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
		case Sand:
			stats.add("fallTime = "+((FallingTile)tile).fallingTime);
			break;
		case Creator:
			stats.add("created = "+((CreatingTile)tile).createdType);
			stats.add("time = "+((CreatingTile)tile).creatingTime);
			break;
		case Piston:
			stats.add("pushing = "+((PushingTile)tile).isPushing());
			stats.add("time = "+((PushingTile)tile).pushinginterval);
			break;
		case PistonArm:
			break;
		default:
			break;
		}
		return stats;
	}
	
}
