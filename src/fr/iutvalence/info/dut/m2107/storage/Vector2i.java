package fr.iutvalence.info.dut.m2107.storage;

/**
 * Defines a vector composed of two integers
 * @author Xenation
 *
 */
public class Vector2i {
	
	/**
	 * a component of this vector
	 */
	public int x, y;
	
	/**
	 * A Vector with x and y to 0
	 */
	public Vector2i() {
		this.x = 0;
		this.y = 0;
	}
	
	/**
	 * A Vector with the given components
	 * @param x the x component
	 * @param y the y component
	 */
	public Vector2i(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * A Vector2i is equal to another if their respective x and y are equal
	 * @param obj needs to be a Vector2i
	 * @return true if the x and y coordinates are equal between the vectors, false otherwise
	 */
	public boolean equals(Object obj) {
		Vector2i vec = (Vector2i) obj;
		if (vec.x == x && vec.y == y)
			return true;
		return false;
	}
	
}
