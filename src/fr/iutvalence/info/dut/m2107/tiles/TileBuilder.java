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
		Tile t = null;
		switch (type) {
		case Dirt:
		case Stone:
		case Grass:
		case Log:
		case Leaves:
		case Sand:
		case Water:
		case Cobweb:
			t = new Tile(type, x, y);
			break;
		case Fader:
			t = new FadingTile(type, x, y);
			break;
		case Spikes:
			DamagingSupportedTile spike = new DamagingSupportedTile(type, x, y, 1, 10);
			spike.setDepending(GameWorld.chunkMap.getBottomTile(spike));
			t = spike;
			break;
		case Creator:
			t = new CreatingTile(type, x, y, TileType.Sand);
			break;
		case Piston:
			t = new PushingTile(type, x, y);
			break;
		case PistonArm:
			t = new DependantFixedTile(type, x, y);
			break;
		case Torch:
			t = new LightingTile(type, x, y);
			break;
		default:
			t = new Tile(type, x, y);
			break;
		}
		t.updateAdjacents = true;
		return t;
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
		case Planks:
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
		case Torch:
			LightingTile torch = (LightingTile) tile;
			EventManager.sendEvent(new TileDestroyedEvent(torch));
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
		stats.add("bbotleft = "+tile.getbBotLeft());
		stats.add("btopleft = "+tile.getbTopLeft());
		stats.add("bbotright = "+tile.getbBotRight());
		stats.add("btopright = "+tile.getbTopRight());
		stats.add("light = "+tile.light.x+", "+tile.light.y+", "+tile.light.z);
		switch (tile.type) {
		case Dirt:
		case Stone:
		case Grass:
		case Log:
		case Leaves:
		case Sand:
		case Water:
		case Planks:
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
		case Torch:
			LightingTile l = (LightingTile) tile;
			stats.add("color = "+l.getColor().x+", "+l.getColor().y+", "+l.getColor().z);
			stats.add("intensity = "+l.getIntensity());
			stats.add("range = "+l.getRange());
			break;
		default:
			break;
		}
		return stats;
	}
	
}
