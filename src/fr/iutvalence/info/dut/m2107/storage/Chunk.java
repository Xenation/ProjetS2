package fr.iutvalence.info.dut.m2107.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.iutvalence.info.dut.m2107.tiles.Tile;
import fr.iutvalence.info.dut.m2107.tiles.TileBuilder;
import fr.iutvalence.info.dut.m2107.tiles.TileVariant;

/**
 * Defines a chunk of tiles.
 * All the tiles of the same type are in a same list.
 * @author Xenation
 *
 */
public class Chunk implements Iterable<Tile> {
	
	/**
	 * The Size (in tiles) of a chunk
	 */
	public static final int CHUNK_SIZE = 16;
	
	/**
	 * The position of the chunk
	 */
	private final Vector2i position;
	/**
	 * The Map that orders tiles by their type
	 */
	private Map<TileVariant, List<Tile>> tiles = new HashMap<TileVariant, List<Tile>>();
	/**
	 * The number of tiles in this chunk
	 */
	private int tilesCount = 0;
	
//	private final int vaoID;
//	private final int textureID;
	
	/**
	 * A chunk with the given position
	 * @param pos
	 */
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
	
	/**
	 * Updates each tile of this chunk and deletes the tiles that have returned false for their update.
	 */
	public void update() {
		List<Tile> toRemove = new ArrayList<Tile>();
		for (Tile tile : this) {
			if (!tile.update()) {
				toRemove.add(tile);
			}
		}
		for (Tile tile : toRemove) {
			remove(tile);
		}
	}
	
	/**
	 * Changes the list in which this tile is stored
	 * @param tile the tile that changed its variant
	 * @param oldVariant the old variant of the tile
	 */
	public void updateVariant(Tile tile, TileVariant oldVariant) {
		tiles.get(oldVariant).remove(tile);
		add(tile);
	}
	
	/**
	 * Returns the position of this chunk
	 * @return the position of this chunk
	 */
	public Vector2i getPosition() {
		return position;
	}
	
	/**
	 * Returns the x coordinate of the position of this chunk
	 * @return the x coordinate of the position of this chunk
	 */
	public int getX() {
		return position.x;
	}
	
	/**
	 * Returns the y coordinate of the position of this chunk
	 * @return the y coordinate of the position of this chunk
	 */
	public int getY() {
		return position.y;
	}
	
	/**
	 * Returns a set of all the types present in this chunk
	 * @return a set of all the types present in this chunk
	 */
	public Set<TileVariant> variants() {
		return tiles.keySet();
	}
	
	/**
	 * Returns a list of all the tiles of the given type in this chunk
	 * @param typ the type of tile to look for
	 * @return a list of all the tiles of the given type
	 */
	public List<Tile> getTiles(TileVariant var) {
		return tiles.get(var);
	}
	
	/**
	 * Returns the number of tiles in this chunk
	 * @return the number of tiles in this chunk
	 */
	public int getTilesCount() {
		return tilesCount;
	}
	
	/**
	 * A chunk is considered equal to another if its position is the same as the other
	 * @param obj needs to be a chunk
	 * @return true if the position is equal, false otherwise 
	 */
	public boolean equals(Object obj) {
		Chunk chk = (Chunk) obj;
		if (position.equals(chk.position))
			return true;
		return false;
	}
	
	/**
	 * Adds a tile to this chunk.
	 * @param til the tile to add
	 * @return if no tile exists at the position of the new tile the new tile is returned otherwise null
	 */
	public Tile add(Tile til) {
		if (getTileAt(til.x, til.y) == null) {
			List<Tile> listAdd = tiles.get(til.getVariant());
			if (listAdd == null) {
				listAdd = new ArrayList<Tile>();
				tiles.put(til.getVariant(), listAdd);
			}
			listAdd.add(til);
			// To update the chunk VAO (not complete)
			//ChunkLoader.CHUNK_LOADER.addToVao(vaoID, til.getRelX(this), til.getRelY(this), TileSprite.POSITIONS, TileSprite.TEXTUREUVS);
			tilesCount++;
			return til;
		}
		return null;
	}
	
	/**
	 * Sets a tile in the chunk deleting the one that has the same position
	 * @param til the tile to set
	 * @return the tile that has been set
	 */
	public Tile set(Tile til) {
		Tile cur = getTileAt(til.x, til.y);
		if (cur == null) {
			add(til);
		} else {
			List<Tile> listAdd = tiles.get(til.getVariant());
			if (listAdd == null) {
				listAdd = new ArrayList<Tile>();
				tiles.put(til.getVariant(), listAdd);
				tiles.get(cur.getVariant()).remove(cur);
				listAdd.add(til);
			} else if (listAdd != tiles.get(cur.getVariant())) {
				tiles.get(cur.getVariant()).remove(cur);
				listAdd.add(til);
			} else {
				listAdd.set(listAdd.indexOf(cur), til);
			}
			TileBuilder.destroyTile(cur);
			return til;
		}
		return null;
	}
	
	/**
	 * Removes a tile at the given coordinates
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public void removeAt(int x, int y) {
		TileVariant rVar = null;
		Tile rem = null;
		for (TileVariant var : tiles.keySet()) {
			for (Tile til : tiles.get(var)) {
				if (til.x == x && til.y == y) {
					rVar = var;
					rem = til;
				}
			}
		}
		if (rVar != null) {
			tiles.get(rVar).remove(rem);
			if (tiles.get(rVar).size() == 0) {
				tiles.remove(rVar);
			}
			tilesCount--;
			TileBuilder.destroyTile(rem);
		}
	}
	
	/**
	 * Removes a tile by reference
	 * @param tile the tile to remove
	 */
	public void remove(Tile tile) {
		tiles.get(tile.getVariant()).remove(tile);
		if (tiles.get(tile.getVariant()).size() == 0) {
			tiles.remove(tile.getVariant());
		}
		tilesCount--;
		TileBuilder.destroyTile(tile);
	}
	
	/**
	 * Returns the tile at the given coordinates
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @return the tile at the given coordinates
	 */
	public Tile getTileAt(int x, int y) {
		for (TileVariant var : tiles.keySet()) {
			for (Tile til : tiles.get(var)) {
				if (til.x == x && til.y == y)
					return til;
			}
		}
		return null;
	}
	
	/**
	 * Converts a world position to a chunk position
	 * @param worldPos the world position to convert
	 * @return the converted position
	 */
	public static Vector2i toChunkPosition(Vector2i worldPos) {
		return new Vector2i(toChunkPosition(worldPos.x), toChunkPosition(worldPos.y));
	}
	/**
	 * Converts world coordinates to a chunk position
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @return the converted position
	 */
	public static Vector2i toChunkPosition(int x, int y) {
		return new Vector2i(toChunkPosition(x), toChunkPosition(y));
	}
	/**
	 * Converts a given world coordinate to a chunk coordinate
	 * @param i the coordinate to convert
	 * @return the converted coordinate
	 */
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
		for (TileVariant var : tiles.keySet()) {
			for (Tile tile : tiles.get(var)) {
				tils.add(tile);
			}
		}
		return tils.iterator();
	}
	
}
