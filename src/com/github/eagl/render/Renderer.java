package com.github.eagl.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import org.lwjgl.opengl.GL13;

import com.github.eagl.models.*;

/**
 * Allows the rendering of objects to the screen
 * @author Xenation
 *
 */
public class Renderer {
	
	/**
	 * The amount of Units in height in the screen
	 */
	public static final float UNITS_Y = 20;
	
	/**
	 * Initialises the orthogonal projection matrix of OGL
	 */
	public Renderer() {
		glOrtho(-DisplayManager.ASPECT_RATIO * (UNITS_Y/2), DisplayManager.ASPECT_RATIO * (UNITS_Y/2), -UNITS_Y/2, UNITS_Y/2, -1, 1);
	}
	
	/**
	 * Renders a sprite to the screen
	 * @param spr the sprite to render
	 */
	public void render(AbstractSprite spr) {
		prepareSprite(spr);
		
		// Enables the client side Vertex Array and TextureUVs
		glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_TEXTURE_COORD_ARRAY);
		
		// Draws the sprite
		glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
		
		// Disables the client side Vertex Array and TextureUVs
		glDisableClientState(GL_TEXTURE_COORD_ARRAY);
		glDisableClientState(GL_VERTEX_ARRAY);
		
		unbindBuffer();
	}
	
	/**
	 * Prepares the display by clearing it
	 */
	public void prepare() {
		// Clear Display
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glClearDepth(1);
	}
	
	/**
	 * Prepares a sprite by binding its buffer and texture
	 * @param spr the sprite to prepare
	 */
	private void prepareSprite(AbstractSprite spr) {
		// How the values for the stride and offset are calculated
//		int floatByteSize = 4;
//		int positionFloatCount = 2;
//		int textureFloatCount = 2;
//		int floatsPerVertex = positionFloatCount + textureFloatCount;
//		int vertexFloatSizeInBytes = floatByteSize * floatsPerVertex;
//		int byteOffset = floatByteSize * positionFloatCount;
		
		// Bind the Buffer
		glBindBuffer(GL_ARRAY_BUFFER, spr.getVboID());
		// Point toward the vertex part of the buffer
		glVertexPointer(2, GL_FLOAT, 16, 0);
		// Point toward the texUV part of the buffer
		glTexCoordPointer(2, GL_FLOAT, 16, 8);
		// Bind the texture
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, spr.getTextureID());
	}
	
	/**
	 * Unbinds the active buffer
	 */
	private void unbindBuffer() {
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
}
