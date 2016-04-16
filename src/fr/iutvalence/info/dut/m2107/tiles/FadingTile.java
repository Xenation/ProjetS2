package fr.iutvalence.info.dut.m2107.tiles;

/**
 * Defines a Fading tile.
 * has a lifetime
 * @author Xenation
 *
 */
public class FadingTile extends Tile {
	
	/**
	 * The default lifetime
	 */
	public static final float DEF_TIME = 5;
	
	/**
	 * The current time remaining to live #tragicfate
	 */
	protected float timeRemaining;
	
	/**
	 * A FadingTile with the given type and coordinates
	 * @param type the type of the tile
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public FadingTile(TileType type, int x, int y) {
		super(type, x, y);
		this.timeRemaining = DEF_TIME;
	}
	
	/**
	 * A FadingTile with the given type, coordinates and lifetime
	 * @param type the type of the tile
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param time the lifetime
	 */
	public FadingTile(TileType type, int x, int y, float time) {
		super(type, x, y);
		this.timeRemaining = time;
	}
	
	/**
	 * Returns the remaining time to live
	 * @return the remaining time to live
	 */
	public float getTimeRemaining() {
		return timeRemaining;
	}
	
	/**
	 * Sets the remaining time to live
	 * @param timeRemaining the remaining time to live
	 */
	public void setTimeRemaining(float timeRemaining) {
		this.timeRemaining = timeRemaining;
	}
	
}
