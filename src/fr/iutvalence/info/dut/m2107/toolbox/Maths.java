package fr.iutvalence.info.dut.m2107.toolbox;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import fr.iutvalence.info.dut.m2107.entities.Camera;

/**
 * Contains Various static methods to do general calculations
 * @author Xenation
 *
 */
public class Maths {
	
	public static Matrix4f createTransformationMatrix(Vector2f translation, float rotation) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(-rotation), new Vector3f(0, 0, 1), matrix, matrix);
		return matrix;
	}
	
	public static Matrix4f createTransformationMatrix(Vector2f translation, Vector2f scale) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.scale(new Vector3f(scale.x, scale.y, 1f), matrix, matrix);
		return matrix;
	}
	
	public static Matrix4f createTransformationMatrix(Vector2f translation, Vector2f scale, float rotation) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.scale(new Vector3f(scale.x, scale.y, 1f), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(-rotation), new Vector3f(0, 0, 1), matrix, matrix);
		return matrix;
	}
	
	public static Matrix4f createTransformationMatrix(Vector2f translation, Vector2f scale, float rotation, float depth) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(new Vector3f(translation.x, translation.y, depth), matrix, matrix);
		Matrix4f.scale(new Vector3f(scale.x, scale.y, 1f), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(-rotation), new Vector3f(0, 0, 1), matrix, matrix);
		return matrix;
	}
	
	public static Matrix4f createTransformationMatrix(int x, int y) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(new Vector2f(x, y), matrix, matrix);
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
	
	public static float fastAbs(float x) {
		if (x >= 0) {
			return x;
		} else {
			return -x;
		}
	}
	
	public static float roundDecim(float f, int decimal) {
		float pow = pow(10, decimal);
		return ((int) (f*pow))/pow;
	}
	
	public static float pow(double a, int b) {
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
	
}
