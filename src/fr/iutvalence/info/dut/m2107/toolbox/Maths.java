package fr.iutvalence.info.dut.m2107.toolbox;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import fr.iutvalence.info.dut.m2107.entities.Camera;
import fr.iutvalence.info.dut.m2107.tiles.TileOrientation;

/**
 * Contains Various static methods to do general calculations
 * @author Xenation
 *
 */
public class Maths {
	
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
		Matrix4f.rotate((float) Math.toRadians(-rotation), new Vector3f(0, 0, 1), matrix, matrix);
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
		Matrix4f.rotate((float) Math.toRadians(-rotation), new Vector3f(0, 0, 1), matrix, matrix);
		return matrix;
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
		Matrix4f.rotate((float) Math.toRadians(-rotation), new Vector3f(0, 0, 1), matrix, matrix);
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
	public static Matrix4f createTransformationMatrix(int x, int y, TileOrientation orientation) {
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
			Matrix4f.rotate(-orientation.getRadians(), new Vector3f(0, 0, 1), matrix, matrix);
			break;
		case LEFT:
			Matrix4f.scale(new Vector3f(-1f, 1f, 1f), matrix, matrix);
			break;
		case RIGHT:
			break;
		case UP:
			Matrix4f.rotate(-orientation.getRadians(), new Vector3f(0, 0, 1), matrix, matrix);
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
		Matrix4f.rotate((float) Math.toRadians(camera.getRotation()), new Vector3f(0, 0, 1), matrix, matrix);
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
	 * @param b the power (integer)
	 * @return a power b
	 */
	public static float pow(float a, int b) {
		float result = 1;
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
	    return (float) (int) ((tmp - (int) tmp) >= 0.5f ? tmp + 1 : tmp) / pow;
	}
	
}
