package fr.iutvalence.info.dut.m2107.storage;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.render.Renderer;
import fr.iutvalence.info.dut.m2107.tiles.Tile;
import fr.iutvalence.info.dut.m2107.tiles.TileBuilder;
import fr.iutvalence.info.dut.m2107.tiles.TileOrientation;
import fr.iutvalence.info.dut.m2107.tiles.TileType;
import fr.iutvalence.info.dut.m2107.toolbox.Maths;

/**
 * Defines a map of chunks.
 * The keys are the position of the chunks
 * The values are the chunks
 * Implements Map and Iterable
 * @author Xenation
 *
 */
public class ChunkMap implements Map<Vector2i, Chunk>, Iterable<Chunk> {
	
	private float tilesPositionOffset;
	
	public ChunkMap() {
		this.tilesPositionOffset = 0;
	}
	
	public ChunkMap(float tilesPositionOffset) {
		this.tilesPositionOffset = tilesPositionOffset;
	}
	
	//// CHUNKMAP \\\\
	/**
	 * Updates every chunk of this chunkMap
	 */
	public void update() {
		for (Chunk chk : getScreenChunks()) {
			chk.update();
		}
	}
	
	/**
	 * Adds a tile in this chunkMap
	 * @param til the tile to add
	 */
	public void addTile(Tile til) {
		Chunk chk = get(Chunk.toChunkPosition(til.x, til.y));
		if (chk != null) {
			chk.add(til);
		}
	}
	/**
	 * Adds a tile in this chunkMap and creates the chunk that contains it if it doesn't exist
	 * @param til the tile to add
	 */
	public void addTilenChunk(Tile til) {
		Chunk chk = get(Chunk.toChunkPosition(til.x, til.y));
		if (chk != null) {
			chk.add(til);
		} else {
			Vector2i pos = Chunk.toChunkPosition(til.x, til.y);
			chk = new Chunk(pos);
			put(pos, chk);
			chk.add(til);
		}
	}
	/**
	 * Sets(Overwrites) the tile (at the new tile's position) in this chunkMap
	 * @param til the tile to set
	 */
	public void setTile(Tile til) {
		Chunk chk = get(Chunk.toChunkPosition(til.x, til.y));
		if (chk != null) {
			chk.set(til);
		}
	}
	/**
	 * Sets(Overwrites) the tile (at the new tile's position) in this chunkMap and creates the chunk that contains it if it doesn't exist
	 * @param til the tile to set
	 */
	public void setTilenChunk(Tile til) {
		Chunk chk = get(Chunk.toChunkPosition(til.x, til.y));
		if (chk != null) {
			chk.set(til);
		} else {
			Vector2i pos = Chunk.toChunkPosition(til.x, til.y);
			chk = new Chunk(pos);
			put(pos, chk);
			chk.add(til);
		}
	}
	/**
	 * Removes the tile at the given coordinates
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public void removeTileAt(int x, int y) {
		Chunk chk = get(Chunk.toChunkPosition(x, y));
		if (chk != null) {
			chk.removeAt(x, y);
		}
	}
	
	public void removeTile(Tile tile) {
		Chunk chk = get(Chunk.toChunkPosition(tile.x, tile.y));
		if (chk != null) {
			chk.remove(tile);
		}
	}
	
	/**
	 * Sets the orientation of the tile at the given coordinates to the given orientation
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param orientation the new orientation of the tile
	 */
	public void rotateTileAt(int x, int y, TileOrientation orientation) {
		getTileAt(x, y).setOrientation(orientation);
	}
	
	/**
	 * Moves the given tile to the given coordinates
	 * @param tile the tile to move
	 * @param nx the new x coordinate
	 * @param ny the new y coordinate
	 */
	public void moveTile(Tile tile, int nx, int ny) {
		Chunk curChk = getChunkAt(Chunk.toChunkPosition(tile.x), Chunk.toChunkPosition(tile.y));
		Chunk nchk = getChunkAt(Chunk.toChunkPosition(nx), Chunk.toChunkPosition(ny));
		if (getTileAt(nx, ny) != null) {
			removeTileAt(nx, ny);
		}
		if (nchk != curChk) {
			curChk.removeAt(tile.x, tile.y);
			nchk.add(tile);
		}
		tile.x = nx;
		tile.y = ny;
	}
	
	/**
	 * Moves the given tile to the given coordinates. Doesn't remove the tile at the new position.
	 * @param tile the tile to move
	 * @param nx the new x coordinate
	 * @param ny the new y coordinate
	 */
	private void moveTileUnsafe(Tile tile, int nx, int ny) {
		Chunk curChk = getChunkAt(Chunk.toChunkPosition(tile.x), Chunk.toChunkPosition(tile.y));
		Chunk nchk = getChunkAt(Chunk.toChunkPosition(nx), Chunk.toChunkPosition(ny));
		if (nchk != curChk) {
			curChk.remove(tile);
			nchk.add(tile);
		}
		tile.x = nx;
		tile.y = ny;
	}
	
	/**
	 * Moves the given tile by one in the given direction
	 * @param tile the tile to move
	 * @param orientation the direction
	 */
	public void moveTile(Tile tile, TileOrientation orientation) {
		switch (orientation) {
		case DOWN:
			moveTile(tile, tile.x, tile.y-1);
			break;
		case LEFT:
			moveTile(tile, tile.x-1, tile.y);
			break;
		case RIGHT:
			moveTile(tile, tile.x+1, tile.y);
			break;
		case UP:
			moveTile(tile, tile.x, tile.y+1);
			break;
		default:
			break;
		}
	}
	
	/**
	 * Moves the given tile by one in the given direction. Doesn't remove the tile at the new position.
	 * @param tile the tile to move
	 * @param orientation the direction
	 */
	private void moveTileUnsafe(Tile tile, TileOrientation orientation) {
		switch (orientation) {
		case DOWN:
			moveTileUnsafe(tile, tile.x, tile.y-1);
			break;
		case LEFT:
			moveTileUnsafe(tile, tile.x-1, tile.y);
			break;
		case RIGHT:
			moveTileUnsafe(tile, tile.x+1, tile.y);
			break;
		case UP:
			moveTileUnsafe(tile, tile.x, tile.y+1);
			break;
		default:
			break;
		}
	}
	
	/**
	 * pushes all consecutive tiles starting at the given coordinates and in direction of the given orientation
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param orientation the direction in which to push the tiles
	 */
	public void pushTiles(int x, int y, TileOrientation orientation) {
		Tile prev = getTileAt(x, y);
		if (prev != null) {
			Tile current = getAdjacentTile(prev, orientation);
			while (current != null) {
				moveTileUnsafe(prev, orientation);
				prev = current;
				current = getAdjacentTile(current, orientation);
			}
			moveTileUnsafe(prev, orientation);
		}
	}
	
	/**
	 * Fills the zone defined by start and end with the given type of tile
	 * @param type the type of tile to set
	 * @param start the starting position of the zone
	 * @param end the ending position of the zone
	 */
	public void fillZone(TileType type, Vector2i start, Vector2i end) {
		if (start.x > end.x) {
			int tmp = start.x;
			start.x = end.x;
			end.x = tmp;
		}
		if (start.y > end.y) {
			int tmp = start.y;
			start.y = end.y;
			end.y = tmp;
		}
		for (int y = start.y; y <= end.y; y++) {
			for (int x = start.x; x <= end.x; x++) {
				setTilenChunk(TileBuilder.buildTile(type, x, y));
			}
		}
	}
	
	/**
	 * Deletes all the tiles in the zone defined by start and end
	 * @param start the starting position of the zone
	 * @param end the ending position of the zone
	 */
	public void emptyZone(Vector2i start, Vector2i end) {
		if (start.x > end.x) {
			int tmp = start.x;
			start.x = end.x;
			end.x = tmp;
		}
		if (start.y > end.y) {
			int tmp = start.y;
			start.y = end.y;
			end.y = tmp;
		}
		for (int y = start.y; y <= end.y; y++) {
			for (int x = start.x; x <= end.x; x++) {
				removeTileAt(x, y);
			}
		}
	}
	
	/**
	 * Returns the tile located at the given coordinates
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @return the tile located at the given coordinates
	 */
	public Tile getTileAt(int x, int y) {
		Chunk chk = getChunkAt(Chunk.toChunkPosition(x), Chunk.toChunkPosition(y));
		if (chk != null) {
			return chk.getTileAt(x, y);
		}
		return null;
	}
	
	public float getTilesPositionOffset() {
		return tilesPositionOffset;
	}
	
	public Tile getTileAt(float x, float y) {
		Chunk chk = getChunkAt(Chunk.toChunkPosition((int) (x+tilesPositionOffset)), Chunk.toChunkPosition((int) (y+tilesPositionOffset)));
		if (chk != null) {
			return chk.getTileAt(toTilePosition(x), toTilePosition(y));
		}
		return null;
	}
	
	public int toTilePosition(float f) {
		return Maths.fastFloor(f-tilesPositionOffset);
	}
	
	public float toTileCenterVisualPosition(float f) {
		return Maths.fastFloor(f-tilesPositionOffset)+tilesPositionOffset;
	}
	
	/**
	 * Returns the tile located at the left of the given tile
	 * @param til the reference tile
	 * @return the tile at the left of the reference tile
	 */
	public Tile getLeftTile(Tile til) {
		return getTileAt(til.x-1, til.y);
	}
	/**
	 * Returns the tile located at the right of the given tile
	 * @param til the reference tile
	 * @return the tile at the right of the reference tile
	 */
	public Tile getRightTile(Tile til) {
		return getTileAt(til.x+1, til.y);
	}
	/**
	 * Returns the tile located at the bottom of the given tile
	 * @param til the reference tile
	 * @return the tile at the bottom of the reference tile
	 */
	public Tile getBottomTile(Tile til) {
		return getTileAt(til.x, til.y-1);
	}
	/**
	 * Returns the tile located at the top of the given tile
	 * @param til the reference tile
	 * @return the tile at the top of the reference tile
	 */
	public Tile getTopTile(Tile til) {
		return getTileAt(til.x, til.y+1);
	}
	
	/**
	 * Returns the tile in front of the given one (using its orientation)
	 * @param til the reference tile
	 * @return the tile in front of the reference tile
	 */
	public Tile getTileFront(Tile til) {
		return getAdjacentTile(til, til.getOrientation());
	}
	
	/**
	 * Returns the adjacent tile at the given orientation
	 * @param til the reference tile
	 * @param orientation the orientation indicates which adjacent to return
	 * @return the tile adjacent to the orientation
	 */
	public Tile getAdjacentTile(Tile til, TileOrientation orientation) {
		switch (orientation) {
		case DOWN:
			return getBottomTile(til);
		case LEFT:
			return getLeftTile(til);
		case RIGHT:
			return getRightTile(til);
		case UP:
			return getTopTile(til);
		default:
			return getRightTile(til);
		}
	}
	
	/**
	 * Returns the chunk located at the given chunk coordinates
	 * @param x the x chunk coordinate
	 * @param y the y chunk coordinate
	 * @return the chunk located at the given chunk coordinates
	 */
	public Chunk getChunkAt(int x, int y) {
		for (Chunk chk : this) {
			if (chk.getPosition().x == x && chk.getPosition().y == y) {
				return chk;
			}
		}
		return null;
	}
	
	/**
	 * Adds empty new chunks that touch the zone defined by center, left, right, top and bottom
	 * @param left the distance to the left from the center
	 * @param right the distance to the right from the center
	 * @param top the distance to the top from the center
	 * @param bottom the distance to the bottom from the center
	 * @param center the center of the zone
	 */
	public void generateSurroundingChunks(float left, float right, float top, float bottom, Vector2f center) {
		for (int y = Chunk.toChunkPosition(Maths.fastFloor(bottom + center.y)); y <= Chunk.toChunkPosition(Maths.fastFloor(top + center.y)); y++) {
			for (int x = Chunk.toChunkPosition(Maths.fastFloor(left + center.x)); x <= Chunk.toChunkPosition(Maths.fastFloor(right + center.x)); x++) {
				Vector2i pos = new Vector2i(x, y);
				if (!this.containsKey(pos)) {
					this.put(pos, new Chunk(pos));
				}
			}
		}
	}
	
	/**
	 * Returns the chunks that touch the zone defined by center, left, right, top and bottom
	 * @param left the distance to the left from the center
	 * @param right the distance to the right from the center
	 * @param top the distance to the top from the center
	 * @param bottom the distance to the bottom from the center
	 * @param center the center of the zone
	 * @return a list of the chunks that touch the zone
	 */
	public List<Chunk> getSurroundingChunks(float left, float right, float top, float bottom, Vector2f center) {
		List<Chunk> chks = new ArrayList<Chunk>();
		for (int y = Chunk.toChunkPosition(Maths.fastFloor(bottom + center.y)); y <= Chunk.toChunkPosition(Maths.fastFloor(top + center.y)); y++) {
			for (int x = Chunk.toChunkPosition(Maths.fastFloor(left + center.x)); x <= Chunk.toChunkPosition(Maths.fastFloor(right + center.x)); x++) {
				Vector2i pos = new Vector2i(x, y);
				if (this.containsKey(pos)) {
					chks.add(this.get(pos));
				}
			}
		}
		return chks;
	}
	
	/**
	 * Returns the chunks that touch the zone renderer by the renderer
	 * @return a list of the chunks that touch the screen zone
	 */
	public List<Chunk> getScreenChunks() {
		return getSurroundingChunks(Renderer.BOUNDARY_LEFT, Renderer.BOUNDARY_RIGHT, Renderer.BOUNDARY_TOP, Renderer.BOUNDARY_BOTTOM, GameWorld.camera.getPosition());
	}
	
	/**
	 * Returns the amount of tiles in the chunks that touch the zone defined by center, left, right, top and bottom
	 * @param left the distance to the left from the center
	 * @param right the distance to the right from the center
	 * @param top the distance to the top from the center
	 * @param bottom the distance to the bottom from the center
	 * @param center the center of the zone
	 * @return the total number of tiles in the chunks that touch the zone
	 */
	public int getSurroundingTilesCount(float left, float right, float top, float bottom, Vector2f center) {
		int count = 0;
		for (int y = Chunk.toChunkPosition(Maths.fastFloor(bottom + center.y)); y <= Chunk.toChunkPosition(Maths.fastFloor(top + center.y)); y++) {
			for (int x = Chunk.toChunkPosition(Maths.fastFloor(left + center.x)); x <= Chunk.toChunkPosition(Maths.fastFloor(right + center.x)); x++) {
				Vector2i pos = new Vector2i(x, y);
				if (this.containsKey(pos)) {
					count += this.get(pos).getTilesCount();
				}
			}
		}
		return count;
	}
	
	/**
	 * Returns the total number of tiles in this chunkMap
	 * @return the total number of tiles in this chunkMap
	 */
	public int getTilesCount() {
		int count = 0;
		for (Chunk chk : this) {
			count += chk.getTilesCount();
		}
		return count;
	}
	
	/**
	 * Returns the total number of chunks in this chunkMap
	 * @return the total number of chunks in this chunkMap
	 */
	public int getChunkCount() {
		return this.size();
	}
	
	/**
	 * Returns the list of chunks in this chunkMap
	 * @return the list of chunks in this chunkMap
	 */
	private List<Chunk> chunkList() {
		List<Chunk> chks = new ArrayList<Chunk>();
		for (Link lnk : map) {
			chks.add(lnk.getValue());
		}
		return chks;
	}
	
	
	//// MAP \\\\
	/**
	 * The list of links of this map
	 */
	private List<Link> map = new ArrayList<Link>();
	
	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public boolean containsKey(Object arg0) {
		for (Link lnk : map) {
			if (lnk.position.equals(arg0)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean containsValue(Object arg0) {
		for (Link lnk : map) {
			if (lnk.chunk.equals(arg0)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Set<Map.Entry<Vector2i, Chunk>> entrySet() {
		Set<Map.Entry<Vector2i, Chunk>> set = new HashSet<Map.Entry<Vector2i, Chunk>>();
		for (Entry<Vector2i, Chunk> entry : map) {
			set.add(entry);
		}
		return set;
	}

	@Override
	public Chunk get(Object arg0) {
		for (Link lnk : map) {
			if (lnk.position.equals(arg0)) {
				return lnk.getValue();
			}
		}
		return null;
	}

	@Override
	public boolean isEmpty() {
		if (map.size() == 0)
			return true;
		return false;
	}

	@Override
	public Set<Vector2i> keySet() {
		Set<Vector2i> set = new HashSet<Vector2i>();
		for (Link lnk : map) {
			set.add(lnk.position);
		}
		return set;
	}

	@Override
	public Chunk put(Vector2i pos, Chunk chk) {
		Link lnk = null;
		if (!containsKey(pos)) {
			lnk = new Link(pos, chk);
			map.add(lnk);
			return lnk.chunk;
		}
		return null;
		
	}

	@Override
	public void putAll(Map<? extends Vector2i, ? extends Chunk> m) {
		for (Entry<? extends Vector2i, ? extends Chunk> entry : m.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public Chunk remove(Object arg0) {
		List<Link> toRem = new LinkedList<Link>();
		for (Link lnk : map) {
			if (lnk.position.equals((Vector2i) arg0))
					toRem.add(lnk);
		}
		for (Link entry : toRem) {
			map.remove(entry);
		}
		return null;
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public Collection<Chunk> values() {
		return chunkList();
	}
	
	
	//// ITERABLE \\\\
	@Override
	public Iterator<Chunk> iterator() {
		return chunkList().iterator();
	}
	
	
	//// LINK \\\\
	private final class Link implements Map.Entry<Vector2i, Chunk> {
		
		private final Vector2i position;
		private Chunk chunk;
		
		public Link(Vector2i pos, Chunk chk) {
			this.position = pos;
			this.chunk = chk;
		}
		
		@Override
		public Vector2i getKey() {
			return position;
		}

		@Override
		public Chunk getValue() {
			return chunk;
		}

		@Override
		public Chunk setValue(Chunk value) {
			chunk = value;
			return chunk;
		}
		
	}
	
}
