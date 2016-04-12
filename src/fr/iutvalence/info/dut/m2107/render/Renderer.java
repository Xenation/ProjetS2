package fr.iutvalence.info.dut.m2107.render;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import fr.iutvalence.info.dut.m2107.models.*;
import fr.iutvalence.info.dut.m2107.shaders.Shader;
import fr.iutvalence.info.dut.m2107.storage.Chunk;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.tiles.Tile;
import fr.iutvalence.info.dut.m2107.tiles.TileType;
import fr.iutvalence.info.dut.m2107.toolbox.Maths;

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
	 * the orthogonal projection matrix
	 */
	private Matrix4f projectionMatrix;
	
	/**
	 * the shader used by this renderer
	 */
	private Shader shader;
	
	/**
	 * A new Renderer.
	 * Initialises a new shader, the orthogonal projection matrix,
	 * Loads the projectionMatrix to the shader
	 */
	public Renderer() {
		shader = new Shader();
		createOrthoProjectionMatrix(-DisplayManager.aspectRatio*UNITS_Y/2, DisplayManager.aspectRatio*UNITS_Y/2, -UNITS_Y/2, UNITS_Y/2, 2, -5);
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}
	
	/**
	 * Renders all the tiles in the gameWorld through the view of the gameWorld's camera
	 * @param gameWorld the game world to render
	 */
	public void render(GameWorld gameWorld) {
		shader.start();
		shader.loadViewMatrix(gameWorld.getCamera());
		
		for (Chunk chk : gameWorld.getChunkMap().getSurroundingChunks(-UNITS_Y/2*DisplayManager.aspectRatio, UNITS_Y/2*DisplayManager.aspectRatio, UNITS_Y/2, -UNITS_Y/2, gameWorld.getCamera().getPosition())) {
			for (TileType typ : chk.types()) {
				prepareSprite(typ.getSprite());
				
				for (Tile tile : chk.getTiles(typ)) {
					
					Matrix4f matrix = Maths.createTransformationMatrix(tile.x, tile.y);
					shader.loadTransformation(matrix);
					
					// Draws the sprite
					glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
				}
				
				unbindSprite();
			}
			
			/// Code to Render whole chunks (not working)
//			prepareChunk(chk);
//			
//			for (Tile til : chk) {
//				Matrix4f matrix = Maths.createTransformationMatrix(til.x, til.y);
//				shader.loadTransformation(matrix);
//				
//				int tileIndex = til.getRelY(chk)*Chunk.CHUNK_SIZE + til.getRelX(chk);
//				glDrawArrays(GL_TRIANGLE_STRIP, tileIndex*4, 4);
//			}
//			
//			unbindSprite();
		}
		
		shader.stop();
	}
	
	/**
	 * Prepares the display by clearing it
	 */
	public void prepare() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glClearDepth(1);
	}
	
//	private void prepareChunk(Chunk chk) {
//		GL30.glBindVertexArray(chk.getVaoID());
//		GL20.glEnableVertexAttribArray(0);
//		GL20.glEnableVertexAttribArray(1);
//		GL13.glActiveTexture(GL13.GL_TEXTURE0);
//		GL11.glBindTexture(GL11.GL_TEXTURE_2D, chk.getTextureID());
//	}
	
	/**
	 * Prepares a sprite by enabling its attributes and texture
	 * @param spr the sprite to prepare
	 */
	private void prepareSprite(AbstractSprite spr) {
		GL30.glBindVertexArray(spr.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, spr.getTextureID());
	}
	
	/**
	 * Unbinds the active sprite
	 */
	private void unbindSprite() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
	}
	
	public void cleanUp() {
		shader.cleanUp();
	}
	
	/**
	 * Creates a projection matrix using the specified left, right, bottom, top, near and far boundaries
	 * @param left the left limit
	 * @param right the right limit
	 * @param bottom the bottom limit
	 * @param top the top limit
	 * @param near the near plane
	 * @param far the far plane
	 */
	public void createOrthoProjectionMatrix(float left, float right, float bottom, float top, float near, float far) {
        float x_orth = 2 / (right - left);
        float y_orth = 2 / (top - bottom);
        float z_orth = -2 / (far - near);
        
        float tx = -(right + left) / (right - left);
        float ty = -(top + bottom) / (top - bottom);
        float tz = -(far + near) / (far - near);
        
        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_orth;
        projectionMatrix.m10 = 0;
        projectionMatrix.m20 = 0;
        projectionMatrix.m30 = 0;
        projectionMatrix.m01 = 0;
        projectionMatrix.m11 = y_orth;
        projectionMatrix.m21 = 0;
        projectionMatrix.m31 = 0;
        projectionMatrix.m02 = 0;
        projectionMatrix.m12 = 0;
        projectionMatrix.m22 = z_orth;
        projectionMatrix.m32 = 0;
        projectionMatrix.m03 = tx;
        projectionMatrix.m13 = ty;
        projectionMatrix.m23 = tz;
        projectionMatrix.m33 = 1;
	}
	
}
