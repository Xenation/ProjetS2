package fr.iutvalence.info.dut.m2107.toolbox;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import fr.iutvalence.info.dut.m2107.entities.Camera;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Vector2i;
import fr.iutvalence.info.dut.m2107.tiles.Tile;
import fr.iutvalence.info.dut.m2107.tiles.TileOrientation;

/**
 * Contains Various static methods to do general calculations
 * @author Xenation
 *
 */
public class Maths {
	
	/**
	 * The Forward unit vector (pointing out of the screen in front of you)
	 */
	private static final Vector3f FORWARD = new Vector3f(0, 0, 1);
	
	/**
	 * Creates a transformation matrix using a position(translation) and rotation
	 * @param translation the translation to apply (position)
	 * @param rotation the rotation
	 * @return a transformation matrix using a position(translation) and rotation
	 */
	public static Matrix4f createTransformationMatrix(Vector2f translation, float rotation) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(-rotation), FORWARD, matrix, matrix);
		return matrix;
	}
	
	/**
	 * Creates a transformation matrix using a position(translation) and scale
	 * @param translation the translation to apply (position)
	 * @param scale the scale
	 * @return a transformation matrix using a position(translation) and scale
	 */
	public static Matrix4f createTransformationMatrix(Vector2f translation, Vector2f scale) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.scale(new Vector3f(scale.x, scale.y, 1f), matrix, matrix);
		return matrix;
	}
	
	/**
	 * Creates a transformation matrix using a position(translation), scale and rotation
	 * @param translation the translation to apply (position)
	 * @param scale the scale
	 * @param rotation the rotation
	 * @return a transformation matrix using a position(translation), scale and rotation
	 */
	public static Matrix4f createTransformationMatrix(Vector2f translation, Vector2f scale, float rotation) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.scale(new Vector3f(scale.x, scale.y, 1f), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(-rotation), FORWARD, matrix, matrix);
		return matrix;
	}
	

	/**
	 * Add a transformation to the given matrix using a position(translation), scale and rotation
	 * @param matrix the base matrix
	 * @param translation the translation to apply (position)
	 * @param scale the scale
	 * @param rotation the rotation
	 * @return a transformation matrix using a position(translation), scale and rotation
	 */
	public static void addTransformationMatrix(Matrix4f matrix, Vector2f translation, Vector2f scale, float rotation) {
		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.scale(new Vector3f(scale.x, scale.y, 1f), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(-rotation), FORWARD, matrix, matrix);
	}
	
	/**
	 * Creates a transformation matrix using a position(translation), scale, rotation and depth
	 * @param translation the translation to apply (position)
	 * @param scale the scale
	 * @param rotation the rotation
	 * @param depth the depth
	 * @return a transformation matrix using a position(translation), scale, rotation and depth
	 */
	public static Matrix4f createTransformationMatrix(Vector2f translation, Vector2f scale, float rotation, float depth) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(new Vector3f(translation.x, translation.y, depth), matrix, matrix);
		Matrix4f.scale(new Vector3f(scale.x, scale.y, 1f), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(-rotation), FORWARD, matrix, matrix);
		return matrix;
	}
	
	/**
	 * Creates a transformation matrix using x and y coordinates
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @return a transformation matrix using x and y coordinates
	 */
	public static Matrix4f createTransformationMatrix(int x, int y) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(new Vector2f(x, y), matrix, matrix);
		return matrix;
	}
	
	/**
	 * Creates a transformation matrix using x and y coordinates and an orientation
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param orientation the orientation to apply
	 * @return a transformation matrix using x and y coordinates
	 */
	public static Matrix4f createTransformationMatrix(float x, float y, TileOrientation orientation) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		switch (orientation) {
		case DOWN:
			y++;
			break;
		case LEFT:
			x++;
			break;
		case RIGHT:
			break;
		case UP:
			x++;
			break;
		default:
			break;
		}
		Matrix4f.translate(new Vector2f(x, y), matrix, matrix);
		switch (orientation) {
		case DOWN:
			Matrix4f.rotate(-orientation.getRadians(), FORWARD, matrix, matrix);
			break;
		case LEFT:
			Matrix4f.scale(new Vector3f(-1f, 1f, 1f), matrix, matrix);
			break;
		case RIGHT:
			break;
		case UP:
			Matrix4f.rotate(-orientation.getRadians(), FORWARD, matrix, matrix);
			break;
		default:
			break;
		}
		
		return matrix;
	}
	
	/**
	 * Creates a view matrix using the specified camera's attributes
	 * @param camera the camera used to view
	 * @return the view matrix from that camera
	 */
	public static Matrix4f createViewMatrix(Camera camera) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.rotate((float) Math.toRadians(camera.getRotation()), FORWARD, matrix, matrix);
		Vector2f negPos = new Vector2f(-camera.getPosition().x, -camera.getPosition().y);
		Matrix4f.translate(negPos, matrix, matrix);
		return matrix;
	}
	
	/**
	 * Linear interpolation between two values
	 * @param a start value
	 * @param b end value
	 * @param d step value
	 * @return the linearly interpolated value from a to b using d step
	 */
	public static float lerp(float a, float b, float d) {
		return (1-d)*a + d*b;
	}
	
	/**
	 * Allows to calculate the floor value of the specified float.
	 * Calculates faster than Math.floor()
	 * @param x the float to floor
	 * @return the integer value of the floored float
	 */
	public static int fastFloor(float x) {
		int xi = (int) x;
		return x<xi ? xi-1 : xi;
	}
	
	/**
	 * Returns the absolute value of the specified float
	 * @param x the float
	 * @return the absolute value of the float
	 */
	public static float fastAbs(float x) {
		if (x >= 0) {
			return x;
		} else {
			return -x;
		}
	}
	
	/**
	 * Returns a float truncated at a certain decimal after the comma
	 * @param f the float to truncate
	 * @param decimal the nth decimal to truncate at
	 * @return a float truncated at the decimal after the comma
	 */
	public static float truncate(float f, int decimal) {
		float pow = pow(10, decimal);
		return ((int) (f*pow))/pow;
	}
	
	/**
	 * Returns a power b
	 * This method is faster than Math.pow() because it doesn't handle double as power
	 * @param a a float
	 * @param b the power (positive integer)
	 * @return a power b
	 */
	public static float pow(float a, int b) {
		float result = 1;
		if (b < 0) throw new IllegalArgumentException("Power b must be a positive integer");
		while(b > 0) {
			if (b % 2 != 0) {
				result *= a;
				b--;
			}
			a *= a;
			b /= 2;
		}
		return result;
	}
	
	/**
	 * Returns a float rounded at a certain decimal after the comma
	 * @param number The number to round
	 * @param decimal The number of number after the comma
	 * @return The rounded number
	 */
	public static float round(float number, int decimal) {
	    float pow = pow(10, decimal);
	    float tmp = number * pow;
	    if(number >= 0) return (float) (int) ((tmp - (int) tmp) >= 0.5f ? tmp + 1 : tmp) / pow;
	    else return (float) (int) ((tmp - (int) tmp) <= -0.5f ? tmp - 1 : tmp) / pow;
	}
	
	public static float distance(Vector2f pos1, Vector2f pos2) {
		return (float) Math.sqrt(pow(pos2.x-pos1.x, 2) + pow(pos2.y-pos1.y, 2));
	}
	
	public static float distance(float x1, float y1, Vector2f pos2) {
		return (float) Math.sqrt(pow(pos2.x-x1, 2) + pow(pos2.y-y1, 2));
	}
	
	public static float distance(int x1, int y1, int x2, int y2) {
		return (float) Math.sqrt(pow(x2-x1, 2) + pow(y2-y1, 2));
	}
	
	public static List<Vector2i> bresenham(int x0, int y0, int x1, int y1) {
		List<Vector2i> line = new ArrayList<Vector2i>();
		
		int dx = Math.abs(x1 - x0);
		int dy = Math.abs(y1 - y0);
		
		int sx = x0 < x1 ? 1 : -1;
		int sy = y0 < y1 ? 1 : -1;
		
		int err = dx - dy;
		int e2;
		
		while (true) {
			line.add(new Vector2i(x0, y0));
			
			if (x0 == x1 && y0 == y1)
				break;
				
			e2 = 2 * err;
			if (e2 > -dy) {
				err = err - dy;
				x0 = x0 + sx;
			}
			
			if (e2 < dx) {
				err = err + dx;
				y0 = y0 + sy;
			}
		}
		return line;
	}
	
	public static List<Tile> tileBresenham(Tile tile1, Tile tile2) {
		List<Tile> tiles = new ArrayList<Tile>();
		
		int x0 = tile1.x;
		int y0 = tile1.y;
		
		int x1 = tile2.x;
		int y1 = tile2.y;
		
		int dx = Math.abs(tile2.x - tile1.x);
		int dy = Math.abs(tile2.y - tile1.y);
		
		int sx = tile1.x < tile2.x ? 1 : -1;
		int sy = tile1.y < tile2.y ? 1 : -1;
		
		int err = dx - dy;
		int e2;
		
		Tile t = tile1;
		
		while (true) {
			if (t != null)
				tiles.add(t);
			
			if (x0 == x1 && y0 == y1)
				break;
			
			e2 = 2 * err;
			if (e2 > -dy) {
				err = err - dy;
				x0 = x0 + sx;
				if (t != null) {
					if (sx == -1) t = t.getLeft();
					else if (sx == 1) t = t.getRight();
				}
			}
			
			if (e2 < dx) {
				err = err + dx;
				y0 = y0 + sy;
				if (t != null) {
					if (sy == -1) t = t.getBottom();
					else if (sy == 1) t = t.getTop();
				}
			}
			
			if (t == null) {
				t = GameWorld.chunkMap.getTileAt(x0, y0);
			}
		}
		
		return tiles;
	}
	
}
