package fr.iutvalence.info.dut.m2107.tiles;

import java.util.ArrayList;

public class TileBuilder {
	
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
