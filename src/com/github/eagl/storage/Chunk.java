package com.github.eagl.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.eagl.models.TileSprite;
import com.github.eagl.tiles.Tile;

public class Chunk implements Iterable<Tile> {
	
	private final Vector2i position;
	private Map<TileSprite, List<Tile>> tiles = new HashMap<TileSprite, List<Tile>>();
	
	
	public Chunk(Vector2i pos) {
		this.position = pos;
	}
	
	public Vector2i getPosition() {
		return position;
	}
	
	public Set<TileSprite> sprites() {
		return tiles.keySet();
	}
	
	public List<Tile> getTiles(TileSprite spr) {
		return tiles.get(spr);
	}

	public boolean equals(Object obj) {
		Chunk chk = (Chunk) obj;
		if (position.equals(chk.position))
			return true;
		return false;
	}
	
	public Tile add(Tile til) {
		if (getTileAt(til.x, til.y) == null) {
			List<Tile> listAdd = tiles.get(til.getSprite());
			if (listAdd == null) {
				listAdd = new ArrayList<Tile>();
				tiles.put(til.getSprite(), listAdd);
			}
			listAdd.add(til);
			return til;
		}
		return null;
	}
	
	public void removeAt(int x, int y) {
		TileSprite rSpr = null;
		Tile rem = null;
		for (TileSprite spr : tiles.keySet()) {
			for (Tile til : tiles.get(spr)) {
				if (til.x == x && til.y == y) {
					rSpr = spr;
					rem = til;
				}
			}
		}
		if (rSpr != null) {
			tiles.get(rSpr).remove(rem);
			if (tiles.get(rSpr).size() == 0) {
				tiles.remove(rSpr);
			}
		}
	}
	
	public Tile getTileAt(int x, int y) {
		for (TileSprite spr : tiles.keySet()) {
			for (Tile til : tiles.get(spr)) {
				if (til.x == x && til.y == y)
					return til;
			}
		}
		return null;
	}
	
	public static Vector2i toChunkPosition(Vector2i worldPos) {
		return new Vector2i(toChunkPosition(worldPos.x), toChunkPosition(worldPos.y));
	}
	public static Vector2i toChunkPosition(int x, int y) {
		return new Vector2i(toChunkPosition(x), toChunkPosition(y));
	}
	public static int toChunkPosition(int i) {
		int n;
		if (i < 0 && i % 8 != 0) {
			n = i/8-1;
		} else {
			n = i/8;
		}
		return n;
	}
	
	//// ITERABLE \\\\
	@Override
	public Iterator<Tile> iterator() {
		List<Tile> tils = new ArrayList<Tile>();
		for (TileSprite spr : tiles.keySet()) {
			for (Tile tile : tiles.get(spr)) {
				tils.add(tile);
			}
		}
		return tils.iterator();
	}
	
}
