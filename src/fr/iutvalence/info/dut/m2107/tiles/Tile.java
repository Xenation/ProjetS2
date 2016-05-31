package fr.iutvalence.info.dut.m2107.tiles;

import java.util.Arrays;

import fr.iutvalence.info.dut.m2107.events.EventManager;
import fr.iutvalence.info.dut.m2107.events.TileVariantChangedEvent;
import fr.iutvalence.info.dut.m2107.storage.Chunk;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;

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
	public boolean updateLight;
	
	private boolean[] sides = new boolean[4];
	
	private Tile top;
	private Tile right;
	private Tile bottom;
	private Tile left;
	
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
	
	public void softUpdate() {
		return;
	}
	
	public boolean heavyUpdate() {
		this.toUpdate = false;
		resetAdjacentLinks();
		return this.type.updateBehaviors(this);
	}
	
	public boolean toUpdate() {
		return this.toUpdate;
	}
	
	public void toUpdate(boolean toUpdate) {
		this.toUpdate = toUpdate;
	}
	
	public void updateSides() {
		if (this.variant.isTransparent) return;
		for (int i = 0; i < sides.length; i++) {
			sides[i] = false;
		}
		if (top != null && left != null && top.left != null && top.top != null && left.left != null
				&& right != null && bottom != null
				&& top.right != null && left.bottom != null
				&& top.top.left != null && left.left.top != null) {
			sides[0] = true;
		}
		if (top != null && right != null && top.right != null && top.top != null && right.right != null
				&& bottom != null && left != null
				&& top.left != null && right.bottom != null
				&& top.top.right != null && right.right.top != null) {
			sides[1] = true;
		}
		if (bottom != null && right != null && bottom.right != null && bottom.bottom != null && right.right != null
				&& left != null && top != null
				&& bottom.left != null && right.top != null
				&& bottom.bottom.right != null && right.right.bottom != null) {
			sides[2] = true;
		}
		if (bottom != null && left != null && bottom.left != null && bottom.bottom != null && left.left != null
				&& top != null && right != null
				&& bottom.right != null && left.top != null
				&& bottom.bottom.left != null && left.left.bottom != null) {
			sides[3] = true;
		}
		boolean[] sidesCp = Arrays.copyOf(sides, 4);
		switch (orientation) {
		case DOWN:
			sides[0] = sidesCp[1];
			sides[1] = sidesCp[2];
			sides[2] = sidesCp[3];
			sides[3] = sidesCp[0];
			break;
		case LEFT:
			break;
		case RIGHT:
			break;
		case UP:
			sides[0] = sidesCp[3];
			sides[1] = sidesCp[0];
			sides[2] = sidesCp[1];
			sides[3] = sidesCp[2];
			break;
		default:
			break;
		}
		updateLight = false;
	}
	
	public void adjacentsToUpdate() {
		if (top != null) {
			top.toUpdate = true;
//			if (top.right != null) top.right.toUpdate = true;
//			if (top.left != null) top.left.toUpdate = true;
//			if (top.top != null) {
//				top.top.toUpdate = true;
//				if (top.top.left != null) top.top.left.toUpdate = true;
//				if (top.top.right != null) top.top.right.toUpdate = true;
//			}
		}
		if (bottom != null) {
			bottom.toUpdate = true;
//			if (bottom.right != null) bottom.right.toUpdate = true;
//			if (bottom.left != null) bottom.left.toUpdate = true;
//			if (bottom.bottom != null) {
//				bottom.bottom.toUpdate = true;
//				if (bottom.bottom.left != null) bottom.bottom.left.toUpdate = true;
//				if (bottom.bottom.right != null) bottom.bottom.right.toUpdate = true;
//			}
		}
		if (right != null) {
			right.toUpdate = true;
//			if (right.right != null) {
//				right.right.toUpdate = true;
//				if (right.right.top != null) right.right.top.toUpdate = true;
//				if (right.right.bottom != null) right.right.bottom.toUpdate = true;
//			}
		}
		if (left != null) {
			left.toUpdate = true;
//			if (left.left != null) {
//				left.left.toUpdate = true;
//				if (left.left.top != null) left.left.top.toUpdate = true;
//				if (left.left.bottom != null) left.left.bottom.toUpdate = true;
//			}
		}
	}

	/**
	 * Returns the type of this tile
	 * @return the type of this tile
	 */
	public TileType getType() {
		return type;
	}
	
	public TileVariant getVariant() {
		return variant;
	}
	
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
	
	public Chunk getChunk() {return chunk;}
	public void setChunk(Chunk chunk) {this.chunk = chunk;}
	
	public void resetAdjacentLinks() {
		this.top = GameWorld.chunkMap.getTopTile(this);
		this.right = GameWorld.chunkMap.getRightTile(this);
		this.bottom = GameWorld.chunkMap.getBottomTile(this);
		this.left = GameWorld.chunkMap.getLeftTile(this);
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
	
	public boolean[] getSides() {return sides;}
	
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
