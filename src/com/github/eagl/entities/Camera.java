package com.github.eagl.entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import com.github.eagl.render.DisplayManager;

/**
 * A Camera with a position and rotation
 * @author Xenation
 *
 */
public class Camera {
	
	/**
	 * The position of the camera
	 */
	private Vector2f position;
	/**
	 * The roation of the camera
	 */
	private float rotation;
	
	/**
	 * A new Camera at 0,0 and with a rotation of 0
	 */
	public Camera() {
		this.position = new Vector2f();
		this.rotation = 0;
	}
	
	/**
	 * Checks for inputs and moves the camera according to them
	 */
	public void update() {
		
		if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD8)) {
			this.position.y += 0.05 * DisplayManager.deltaTime();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD5)) {
			this.position.y -= 0.05 * DisplayManager.deltaTime();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD6)) {
			this.position.x += 0.05 * DisplayManager.deltaTime();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD4)) {
			this.position.x -= 0.05 * DisplayManager.deltaTime();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD0)) {
			setPosition(0, 0);
		}
		
	}
	
	/**
	 * Returns the position of the camera
	 * @return the position of the camera
	 */
	public Vector2f getPosition() {
		return position;
	}
	
	/**
	 * Changes the position vector of the camera
	 * @param position the new position vector of the camera
	 */
	public void setPosition(Vector2f position) {
		this.position.set(position);
	}
	
	/**
	 * Changes the components of the position vector of the camera
	 * @param x the new x component
	 * @param y the new y component
	 */
	public void setPosition(float x, float y) {
		this.position.set(x, y);
	}
	
	/**
	 * Increases the current position of the camera
	 * @param x the amount to add in x component
	 * @param y the amount to add in y component
	 */
	public void increasePosition(float x, float y) {
		this.position.x += x;
		this.position.y += y;
	}
	
	/**
	 * Returns the rotation of the camera
	 * @return the rotation of the camera
	 */
	public float getRotation() {
		return rotation;
	}
	
	/**
	 * Changes the rotation of the camera
	 * @param rotation the new rotation
	 */
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	
}
