package fr.iutvalence.info.dut.m2107.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.iutvalence.info.dut.m2107.tiles.Tile;
import fr.iutvalence.info.dut.m2107.tiles.TileType;

public class Chunk implements Iterable<Tile> {
	
	public static final int CHUNK_SIZE = 16;
	
	private final Vector2i position;
	private Map<TileType, List<Tile>> tiles = new HashMap<TileType, List<Tile>>();
	private int tilesCount = 0;
	
//	private final int vaoID;
//	private final int textureID;

	public Chunk(Vector2i pos) {
		this.position = pos;
		// Creates the Chunk VAO
		//this.vaoID = ChunkLoader.CHUNK_LOADER.createChunkVao();
		//this.textureID = ChunkLoader.CHUNK_LOADER.loadTexture("tile_dirt");
	}
	
//	public int getVaoID() {
//		return vaoID;
//	}
//	
//	public int getTextureID() {
//		return textureID;
//	}
	
	public Vector2i getPosition() {
		return position;
	}
	
	public int getX() {
		return position.x;
	}

	public int getY() {
		return position.y;
	}
	
	public Set<TileType> types() {
		return tiles.keySet();
	}
	
	public List<Tile> getTiles(TileType typ) {
		return tiles.get(typ);
	}
	
	public int getTilesCount() {
		return tilesCount;
	}

	public boolean equals(Object obj) {
		Chunk chk = (Chunk) obj;
		if (position.equals(chk.position))
			return true;
		return false;
	}
	
	public Tile add(Tile til) {
		if (getTileAt(til.x, til.y) == null) {
			List<Tile> listAdd = tiles.get(til.getType());
			if (listAdd == null) {
				listAdd = new ArrayList<Tile>();
				tiles.put(til.getType(), listAdd);
			}
			listAdd.add(til);
			// To update the chunk VAO (not complete)
			//ChunkLoader.CHUNK_LOADER.addToVao(vaoID, til.getRelX(this), til.getRelY(this), TileSprite.POSITIONS, TileSprite.TEXTUREUVS);
			tilesCount++;
			return til;
		}
		return null;
	}
	
	public Tile set(Tile til) {
		Tile cur = getTileAt(til.x, til.y);
		if (cur == null) {
			add(til);
		} else {
			List<Tile> listAdd = tiles.get(til.getType());
			if (listAdd == null) {
				listAdd = new ArrayList<Tile>();
				tiles.put(til.getType(), listAdd);
				tiles.get(cur.getType()).remove(cur);
				listAdd.add(til);
			} else if (listAdd != tiles.get(cur.getType())) {
				tiles.get(cur.getType()).remove(cur);
				listAdd.add(til);
			} else {
				listAdd.set(listAdd.indexOf(cur), til);
			}
			return til;
		}
		return null;
	}
	
	public void removeAt(int x, int y) {
		TileType rTyp = null;
		Tile rem = null;
		for (TileType typ : tiles.keySet()) {
			for (Tile til : tiles.get(typ)) {
				if (til.x == x && til.y == y) {
					rTyp = typ;
					rem = til;
				}
			}
		}
		if (rTyp != null) {
			tiles.get(rTyp).remove(rem);
			if (tiles.get(rTyp).size() == 0) {
				tiles.remove(rTyp);
			}
			tilesCount--;
		}
	}
	
	public Tile getTileAt(int x, int y) {
		for (TileType typ : tiles.keySet()) {
			for (Tile til : tiles.get(typ)) {
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
		if (i < 0 && i % CHUNK_SIZE != 0) {
			n = i/CHUNK_SIZE-1;
		} else {
			n = i/CHUNK_SIZE;
		}
		return n;
	}
	
	//// ITERABLE \\\\
	@Override
	public Iterator<Tile> iterator() {
		List<Tile> tils = new ArrayList<Tile>();
		for (TileType typ : tiles.keySet()) {
			for (Tile tile : tiles.get(typ)) {
				tils.add(tile);
			}
		}
		return tils.iterator();
	}
	
}
