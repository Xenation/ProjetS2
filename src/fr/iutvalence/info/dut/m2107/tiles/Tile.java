package fr.iutvalence.info.dut.m2107.tiles;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import fr.iutvalence.info.dut.m2107.events.EventManager;
import fr.iutvalence.info.dut.m2107.events.TileVariantChangedEvent;
import fr.iutvalence.info.dut.m2107.storage.Chunk;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.toolbox.Maths;

/**
 * Defines a Tile
 * @author Xenation
 *
 */
public class Tile {
	
	/**
	 * The SIZE of a tile
	 */
	public static final float TILE_SIZE = 1;
	
	public static final Vector3f LIGHT_COLOR = new Vector3f(1, 1, 1);
	public static final float LIGHT_INT = .25f;
	public static final float LIGHT_RANGE = 3;
	
	/**
	 * the x and y coordinates of the tile
	 */
	public int x, y;
	/**
	 * the type of the tile
	 */
	protected TileType type;
	
	/**
	 * The variant of this tile
	 */
	private TileVariant variant;
	
	/**
	 * The orientation of this tile
	 */
	protected TileOrientation orientation;
	
	/**
	 * Whether this tile needs to be updated
	 */
	protected boolean toUpdate;
	/**
	 * Whether the lighting of this tile needs to be updated.
	 */
	public boolean updateLight;
	
	public boolean updateAdjacents;
	
	public Vector3f light = new Vector3f(0, 0, 0);
	public Vector3f prevLight = new Vector3f(0, 0, 0);
	
	public boolean isNaturalLight;
	
	private int freeNb = 0;
	
	/**
	 * The top tile
	 */
	private Tile top;
	/**
	 * The right tile
	 */
	private Tile right;
	/**
	 * The bottom tile
	 */
	private Tile bottom;
	/**
	 * The left tile
	 */
	private Tile left;
	
	private Tile bBotLeft;
	private Tile bTopLeft;
	private Tile bBotRight;
	private Tile bTopRight;
	
	/**
	 * The Chunk that contains this tile
	 */
	private Chunk chunk;
	
	/**
	 * A Tile with the specified type and coordinates
	 * @param type the type of the tile
	 * @param x the x coordinate of the tile
	 * @param y the y coordinate of the tile
	 */
	public Tile(TileType type, int x, int y) {
		this.type = type;
		this.variant = type.getBaseVariant();
		this.x = x;
		this.y = y;
		this.orientation = TileOrientation.RIGHT;
		this.toUpdate = true;
	}
	
	/**
	 * A Tile with the specified type, variant and coordinates
	 * @param type the type of the tile
	 * @param variant the variant of the tile
	 * @param x the x coordinate of the tile
	 * @param y the y coordinate of the tile
	 */
	public Tile(TileType type, TileVariant variant, int x, int y) {
		this.type = type;
		this.variant = variant;
		this.x = x;
		this.y = y;
		this.orientation = TileOrientation.RIGHT;
		this.toUpdate = true;
	}
	
	/**
	 * A Tile with the specified type, coordinates and orientation
	 * @param type the type of the tile
	 * @param x the x coordinate of the tile
	 * @param y the y coordinate of the tile
	 * @param orientation the orientation of the tile
	 */
	public Tile(TileType type, int x, int y, TileOrientation orientation) {
		this.type = type;
		this.variant = type.getBaseVariant();
		this.x = x;
		this.y = y;
		this.orientation = orientation;
		this.toUpdate = true;
	}
	
	/**
	 * A Tile with the specified type, variant, coordinates and orientation
	 * @param type the type of the tile
	 * @param variant the variant of the tile
	 * @param x the x coordinate of the tile
	 * @param y the y coordinate of the tile
	 * @param orientation the orientation of the tile
	 */
	public Tile(TileType type, TileVariant variant, int x, int y, TileOrientation orientation) {
		this.type = type;
		this.variant = variant;
		this.x = x;
		this.y = y;
		this.orientation = orientation;
		this.toUpdate = true;
	}
	
	/**
	 * Updates the attributes of this tile that change every frame.
	 */
	public void softUpdate() {
		if (isNaturalLight) {
			for (Chunk chk : GameWorld.chunkMap.getSurroundingChunks(-LIGHT_RANGE, LIGHT_RANGE, LIGHT_RANGE, -LIGHT_RANGE, new Vector2f(x, y))) {
				for (Tile tile : chk) {
					float flat = 0.25f*LIGHT_RANGE;
					float distance = Maths.distance(tile.x, tile.y, x, y);
					float distanceFade = 1;
					if (distance > flat)
						distanceFade = ((LIGHT_RANGE-distance)/(LIGHT_RANGE-flat));
					if (distanceFade < 0) distanceFade = 0;
					tile.light.x += LIGHT_COLOR.x * LIGHT_INT*freeNb * distanceFade;
					tile.light.y += LIGHT_COLOR.y * LIGHT_INT*freeNb * distanceFade;
					tile.light.z += LIGHT_COLOR.z * LIGHT_INT*freeNb * distanceFade;
				}
			}
			if (chunk.isBackground) {
				for (Chunk chk : GameWorld.backChunkMap.getSurroundingChunks(-LIGHT_RANGE, LIGHT_RANGE, LIGHT_RANGE, -LIGHT_RANGE, new Vector2f(x, y))) {
					for (Tile tile : chk) {
						float flat = 0.25f*LIGHT_RANGE;
						float distance = Maths.distance(tile.x, tile.y, x, y);
						float distanceFade = 1;
						if (distance > flat)
							distanceFade = ((LIGHT_RANGE-distance)/(LIGHT_RANGE-flat));
						if (distanceFade < 0) distanceFade = 0;
						tile.light.x += LIGHT_COLOR.x * LIGHT_INT*freeNb * distanceFade * .75f;
						tile.light.y += LIGHT_COLOR.y * LIGHT_INT*freeNb * distanceFade * .75f;
						tile.light.z += LIGHT_COLOR.z * LIGHT_INT*freeNb * distanceFade * .75f;
					}
				}
			}
		}
		return;
	}
	
	/**
	 * Updates this tile entirely:<br>
	 * - resets the links<br>
	 * - updates using behaviors<br>
	 * This update is not to be made every frame since it may be heavy on processing time.
	 * @return <tt>true</tt> if this tile stays, <tt>false</tt> if it needs to be destroyed.
	 */
	public boolean heavyUpdate() {
		this.toUpdate = false;
		resetAdjacentLinks();
		if (updateAdjacents) {
			adjacentsToUpdate();
			updateAdjacents = false;
		}
		return this.type.updateBehaviors(this);
	}
	
	public void updateNaturalLight() {
		freeNb = 4;
		
		if (!chunk.isBackground) {
			if (top != null) freeNb--;
			if (left != null) freeNb--;
			if (right != null) freeNb--;
			if (bottom != null) freeNb--;
			
			if (freeNb != 0
					&& (bBotLeft == null
					|| bBotRight == null
					|| bTopLeft == null
					|| bTopRight == null)) {
				isNaturalLight = true;
			} else if (isNaturalLight) {
				isNaturalLight = false;
			}
		} else {
			if (top != null) freeNb--;
			if (left != null) freeNb--;
			if (right != null) freeNb--;
			if (bottom != null) freeNb--;
			
			if (freeNb != 0
					&& (bBotLeft == null
					&& bBotRight == null
					&& bTopLeft == null
					&& bTopRight == null)) {
				isNaturalLight = true;
			} else if (isNaturalLight) {
				isNaturalLight = false;
			}
		}
		
//		if (top == null && (bTopLeft == null || (bTopLeft != null && bTopLeft.top == null) || bTopRight == null || (bTopRight != null && bTopRight.top == null))
//				|| bottom == null && (bBotLeft == null || (bBotLeft != null && bBotLeft.bottom == null) || bBotRight == null || (bBotRight != null && bBotRight.bottom == null))
//				|| left == null && (bBotLeft == null || (bBotLeft != null && bBotLeft.left == null) || bTopLeft == null || (bTopLeft != null && bTopLeft.left == null))
//				|| right == null && (bBotRight == null || (bBotRight != null && bBotRight.right == null) || bTopRight == null || (bTopRight != null && bTopRight.right == null))) {
//			if (chunk.isBackground) {
//				naturalLight.x = .75f;
//				naturalLight.y = .75f;
//				naturalLight.z = .75f;
//			} else {
//				naturalLight.x = 1;
//				naturalLight.y = 1;
//				naturalLight.z = 1;
//			}
//		} else if ((top != null && (top.top == null || top.left == null || top.right == null)
//				|| bottom != null && (bottom.bottom == null || bottom.left == null || bottom.right == null)
//				|| left != null && (left.left == null || left.top == null || left.bottom == null)
//				|| right != null && (right.right == null || right.top == null || right.bottom == null))
//				&& ((bTopLeft == null || bTopLeft != null && (bTopLeft.top == null || bTopLeft.left == null))
//				|| (bTopRight == null || bTopRight != null && (bTopRight.top == null || bTopRight.right == null))
//				|| (bBotLeft == null || bBotLeft != null && (bBotLeft.bottom == null || bBotLeft.left == null))
//				|| (bBotRight == null || bBotRight != null && (bBotRight.bottom == null || bBotRight.right == null)))) {
//			if (chunk.isBackground) {
//				naturalLight.x = .375f;
//				naturalLight.y = .375f;
//				naturalLight.z = .375f;
//			} else {
//				naturalLight.x = .5f;
//				naturalLight.y = .5f;
//				naturalLight.z = .5f;
//			}
//		} else {
//			naturalLight.x = 0;
//			naturalLight.y = 0;
//			naturalLight.z = 0;
//		}
		updateLight = false;
	}
	
	/**
	 * Returns <tt>true</tt> if this tile needs to be updated, <tt>false</tt> otherwise.
	 * @return
	 */
	public boolean toUpdate() {
		return this.toUpdate;
	}
	
	/**
	 * Sets the toUpdate state of this tile.
	 * @param toUpdate the new toUpdate state
	 */
	public void toUpdate(boolean toUpdate) {
		this.toUpdate = toUpdate;
	}
	
	/**
	 * Request update of adjacent tiles.
	 */
	public void adjacentsToUpdate() {
		if (top != null) {
			top.toUpdate = true;
			if (top.top != null) top.top.toUpdate = true;
			if (top.left != null) top.left.toUpdate = true;
			if (top.right != null) top.right.toUpdate = true;
		}
		if (bottom != null) {
			bottom.toUpdate = true;
			if (bottom.bottom != null) bottom.bottom.toUpdate = true;
			if (bottom.left != null) bottom.left.toUpdate = true;
			if (bottom.right != null) bottom.right.toUpdate = true;
		}
		if (right != null) {
			right.toUpdate = true;
			if (right.right != null) right.right.toUpdate = true;
			if (right.top != null) right.top.toUpdate = true;
			if (right.bottom != null) right.bottom.toUpdate = true;
		}
		if (left != null) {
			left.toUpdate = true;
			if (left.left != null) left.left.toUpdate = true;
			if (left.top != null) left.top.toUpdate = true;
			if (left.bottom != null) left.bottom.toUpdate = true;
		}
		if (bBotLeft != null) {
			bBotLeft.toUpdate = true;
			if (bBotLeft.left != null) bBotLeft.left.toUpdate = true;
			if (bBotLeft.bottom != null) bBotLeft.bottom.toUpdate = true;
		}
		if (bBotRight != null) {
			bBotRight.toUpdate = true;
			if (bBotRight.right != null) bBotRight.right.toUpdate = true;
			if (bBotRight.bottom != null) bBotRight.bottom.toUpdate = true;
		}
		if (bTopLeft != null) {
			bTopLeft.toUpdate = true;
			if (bTopLeft.left != null) bTopLeft.left.toUpdate = true;
			if (bTopLeft.top != null) bTopLeft.top.toUpdate = true;
		}
		if (bTopRight != null) {
			bTopRight.toUpdate = true;
			if (bTopRight.right != null) bTopRight.right.toUpdate = true;
			if (bTopRight.top != null) bTopRight.top.toUpdate = true;
		}
	}

	/**
	 * Returns the type of this tile
	 * @return the type of this tile
	 */
	public TileType getType() {
		return type;
	}
	
	/**
	 * Returns the variant of this tile
	 * @return the variant of this tile
	 */
	public TileVariant getVariant() {
		return variant;
	}
	
	/**
	 * Sets the variant of this tile
	 * @param var the new variant
	 */
	public void setVariant(TileVariant var) {
		TileVariant old = this.variant;
		this.variant = var;
		chunk.updateVariant(this, old);
		EventManager.sendEvent(new TileVariantChangedEvent(this, old));
	}
	
	/**
	 * Only meant to be used on world loading
	 * @param var
	 */
	public void setVariantUnsafe(TileVariant var) {
		this.variant = var;
	}

	/**
	 * Returns the x coordinate of this tile
	 * @return the x coordinate of this tile
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Returns the y coordinate of this tile
	 * @return the y coordinate of this tile
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Returns the orientation of this tile
	 * @return the orientation of this tile
	 */
	public TileOrientation getOrientation() {
		return orientation;
	}
	
	/**
	 * Sets the orientation of this tile
	 * @param orientation the new orientation of this tile
	 */
	public void setOrientation(TileOrientation orientation) {
		this.orientation = orientation;
	}
	
	public Tile getTop() {return top;}
	protected void setTop(Tile top) {this.top = top;}

	public Tile getRight() {return right;}
	protected void setRight(Tile right) {this.right = right;}

	public Tile getBottom() {return bottom;}
	protected void setBottom(Tile bottom) {this.bottom = bottom;}

	public Tile getLeft() {return left;}
	protected void setLeft(Tile left) {this.left = left;}
	
	public Tile getbBotLeft() {return bBotLeft;}

	public Tile getbTopLeft() {return bTopLeft;}

	public Tile getbBotRight() {return bBotRight;}

	public Tile getbTopRight() {return bTopRight;}

	public Chunk getChunk() {return chunk;}
	public void setChunk(Chunk chunk) {this.chunk = chunk;}
	
	/**
	 * Reset the adjacent tiles links.
	 */
	public void resetAdjacentLinks() {
		if (!chunk.isBackground) {
			this.top = GameWorld.chunkMap.getTopTile(this);
			this.right = GameWorld.chunkMap.getRightTile(this);
			this.bottom = GameWorld.chunkMap.getBottomTile(this);
			this.left = GameWorld.chunkMap.getLeftTile(this);
			this.bBotLeft = GameWorld.backChunkMap.getTileAt(x, y);
			this.bBotRight = GameWorld.backChunkMap.getTileAt(x+1, y);
			this.bTopLeft = GameWorld.backChunkMap.getTileAt(x, y+1);
			this.bTopRight = GameWorld.backChunkMap.getTileAt(x+1, y+1);
		} else {
			this.top = GameWorld.backChunkMap.getTopTile(this);
			this.right = GameWorld.backChunkMap.getRightTile(this);
			this.bottom = GameWorld.backChunkMap.getBottomTile(this);
			this.left = GameWorld.backChunkMap.getLeftTile(this);
			this.bBotLeft = GameWorld.chunkMap.getTileAt(x-1, y-1);
			this.bBotRight = GameWorld.chunkMap.getTileAt(x, y-1);
			this.bTopLeft = GameWorld.chunkMap.getTileAt(x-1, y);
			this.bTopRight = GameWorld.chunkMap.getTileAt(x, y);
		}
		updateLight = true;
	}

	/**
	 * Returns the x coordinate of this tile relative to a specified chunk
	 * @param chk the chunk to use to get the relative position
	 * @return the x coordinate of this tile relative to the specified chunk
	 */
	public int getRelX(Chunk chk) {
		return x-chk.getX()*Chunk.CHUNK_SIZE;
	}
	
	/**
	 * Returns the y coordinate of this tile relative to a specified chunk
	 * @param chk the chunk to use to get the relative position
	 * @return the y coordinate of this tile relative to the specified chunk
	 */
	public int getRelY(Chunk chk) {
		return y-chk.getY()*Chunk.CHUNK_SIZE;
	}
	
	/**
	 * Returns the x coordinate of the tile in front
	 * @return the x coordinate of the tile in front
	 */
	public int getFrontX() {
		switch (orientation) {
		case DOWN:
			return x;
		case LEFT:
			return x-1;
		case RIGHT:
			return x+1;
		case UP:
			return x;
		default:
			return x;
		}
	}
	
	/**
	 * Returns the y coordinate of the tile in front
	 * @return the y coordinate of the tile in front
	 */
	public int getFrontY() {
		switch (orientation) {
		case DOWN:
			return y-1;
		case LEFT:
			return y;
		case RIGHT:
			return y;
		case UP:
			return y+1;
		default:
			return y;
		}
	}
	
}
