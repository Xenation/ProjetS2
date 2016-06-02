package fr.iutvalence.info.dut.m2107.tiles;

import java.util.ArrayList;

import fr.iutvalence.info.dut.m2107.events.EventManager;
import fr.iutvalence.info.dut.m2107.events.TileDestroyedEvent;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;

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
		case Sand:
		case Water:
			return new Tile(type, x, y);
		case Fader:
			return new FadingTile(type, x, y);
		case Spikes:
			DamagingSupportedTile spike = new DamagingSupportedTile(type, x, y, 1, 10);
			spike.setDepending(GameWorld.chunkMap.getBottomTile(spike));
			return spike;
		case Creator:
			return new CreatingTile(type, x, y, TileType.Sand);
		case Piston:
			PushingTile t = new PushingTile(type, x, y);
			return t;
		case PistonArm:
			return new DependantFixedTile(type, x, y);
		default:
			return new Tile(type, x, y);
		}
	}
	
	/**
	 * Destroys a tile in the right way
	 * @param tile the tile to destroy
	 */
	public static void destroyTile(Tile tile) {
		switch (tile.type) {
		case Dirt:
		case Stone:
		case Grass:
		case Log:
		case Leaves:
		case Sand:
		case Water:
			EventManager.sendEvent(new TileDestroyedEvent(tile));
//			EventManager.unregister(tile);
			break;
		case Fader:
			FadingTile fading = (FadingTile) tile;
			EventManager.sendEvent(new TileDestroyedEvent(fading));
//			EventManager.unregister(t);
			break;
		case Spikes:
			DamagingSupportedTile spike = (DamagingSupportedTile) tile;
			EventManager.sendEvent(new TileDestroyedEvent(spike));
//			EventManager.unregister(damaging);
			break;
		case Creator:
			CreatingTile creating = (CreatingTile) tile;
			EventManager.sendEvent(new TileDestroyedEvent(creating));
//			EventManager.unregister(creating);
			break;
		case Piston:
			PushingTile pushing = (PushingTile) tile;
			EventManager.sendEvent(new TileDestroyedEvent(pushing));
//			EventManager.unregister(pushing);
			break;
		case PistonArm:
			DependantFixedTile dependant = (DependantFixedTile) tile;
			EventManager.sendEvent(new TileDestroyedEvent(dependant));
//			EventManager.unregister(dependant);
			break;
		default:
//			EventManager.unregister(tile);
			break;
		}
		tile.adjacentsToUpdate();
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
		stats.add("Ori = "+tile.orientation.name());
		stats.add("toUp = "+tile.toUpdate());
		stats.add("top = "+tile.getTop());
		stats.add("right = "+tile.getRight());
		stats.add("bottom = "+tile.getBottom());
		stats.add("left = "+tile.getLeft());
		stats.add("sides[0] = "+tile.getSides()[0]);
		stats.add("sides[1] = "+tile.getSides()[1]);
		stats.add("sides[2] = "+tile.getSides()[2]);
		stats.add("sides[3] = "+tile.getSides()[3]);
		switch (tile.type) {
		case Dirt:
		case Stone:
		case Grass:
		case Log:
		case Leaves:
		case Sand:
			break;
		case Fader:
			stats.add("time = "+((TimedTile)tile).time);
			break;
		case Spikes:
			stats.add("dmg = "+((DamagingSupportedTile)tile).damage);
			break;
		case Creator:
			stats.add("created = "+((CreatingTile)tile).createdType);
			stats.add("time = "+((CreatingTile)tile).time);
			break;
		case Piston:
			stats.add("pushing = "+((PushingTile)tile).isPushing());
			stats.add("time = "+((PushingTile)tile).time);
			break;
		case PistonArm:
			break;
		default:
			break;
		}
		return stats;
	}
	
}
