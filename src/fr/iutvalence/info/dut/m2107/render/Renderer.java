package fr.iutvalence.info.dut.m2107.render;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import fr.iutvalence.info.dut.m2107.entities.Entity;
import fr.iutvalence.info.dut.m2107.fontMeshCreator.GUIText;
import fr.iutvalence.info.dut.m2107.gui.GUIShader;
import fr.iutvalence.info.dut.m2107.models.*;
import fr.iutvalence.info.dut.m2107.shaders.Shader;
import fr.iutvalence.info.dut.m2107.storage.Chunk;
import fr.iutvalence.info.dut.m2107.storage.GUILayer;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Layer;
import fr.iutvalence.info.dut.m2107.tiles.Tile;
import fr.iutvalence.info.dut.m2107.tiles.TileVariant;
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
	public static float UNITS_Y = 30;
	
	/**
	 * The left boundary of the screen
	 */
	public static float BOUNDARY_LEFT = -UNITS_Y/2*DisplayManager.aspectRatio;
	
	/**
	 * The right boundary of the screen
	 */
	public static float BOUNDARY_RIGHT = -BOUNDARY_LEFT;
	
	/**
	 * The bottom boundary of the screen
	 */
	public static float BOUNDARY_BOTTOM = -UNITS_Y/2;
	
	/**
	 * The top boundary of the screen
	 */
	public static float BOUNDARY_TOP = UNITS_Y/2;
	
	/**
	 * the orthogonal projection matrix
	 */
	private Matrix4f projectionMatrix;
	
	/**
	 * the shader used by this renderer to render the GameWorld.
	 */
	private Shader shader;
	
	/**
	 * The shader used by this renderer to render GUI.
	 */
	private GUIShader guiShader;
	
	/**
	 * The clear color (=background color)
	 */
	private Vector3f bgColor = new Vector3f(126f/255, 192f/255, 238f/255);
	
	
	/**
	 * Initialises the rendering frame of the renderers
	 * @param units_y the height of the rendered frame
	 */
	public static void init(float units_y) {
		UNITS_Y = units_y;
		BOUNDARY_LEFT = -UNITS_Y/2*DisplayManager.aspectRatio;
		BOUNDARY_RIGHT = -BOUNDARY_LEFT;
		BOUNDARY_BOTTOM = -UNITS_Y/2;
		BOUNDARY_TOP = UNITS_Y/2;
	}
	
	/**
	 * A new Renderer.
	 * Initialises a new shader, the orthogonal projection matrix,
	 * Loads the projectionMatrix to the shader
	 */
	public Renderer() {
		shader = new Shader();
		guiShader = new GUIShader();
		createOrthoProjectionMatrix(BOUNDARY_LEFT, BOUNDARY_RIGHT, BOUNDARY_BOTTOM, BOUNDARY_TOP, 5, -10);
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
		glClearColor(bgColor.x, bgColor.y, bgColor.z, 1);
	}
	
	/**
	 * Renders all the tiles in the gameWorld through the view of the gameWorld's camera
	 */
	public void render() {
		shader.start();
		shader.loadViewMatrix(GameWorld.camera);
		shader.loadDepth(0);
		shader.loadAlpha(1);
		
		// Background Chunks Rendering
		prepareAtlas(Atlas.TILE_ATLAS);
		for (Chunk chk : GameWorld.backChunkMap.getSurroundingChunks(BOUNDARY_LEFT-.5f, BOUNDARY_RIGHT+.5f, BOUNDARY_TOP+.5f, BOUNDARY_BOTTOM-.5f, GameWorld.camera.getPosition())) {
			for (TileVariant var : chk.variants()) {
				prepareSprite(var.sprite);
				
				for (Tile tile : chk.getTiles(var)) {
					
					Matrix4f matrix = Maths.createTransformationMatrix(tile.x+GameWorld.backChunkMap.getTilesPositionOffset(), tile.y+GameWorld.backChunkMap.getTilesPositionOffset(), tile.getOrientation());
					shader.loadTransformation(matrix);
					shader.loadLight(tile.light);
					
					// Draws the sprite
					glDrawArrays(GL_QUADS, 0, 4);
				}
				
				unbindSprite();
			}
		}
		
		// Chunks Rendering
		prepareAtlas(Atlas.TILE_ATLAS);
		for (Chunk chk : GameWorld.chunkMap.getSurroundingChunks(BOUNDARY_LEFT, BOUNDARY_RIGHT, BOUNDARY_TOP, BOUNDARY_BOTTOM, GameWorld.camera.getPosition())) {
			for (TileVariant var : chk.variants()) {
				prepareSprite(var.sprite);
				
				for (Tile tile : chk.getTiles(var)) {
					
					Matrix4f matrix = Maths.createTransformationMatrix(tile.x, tile.y, tile.getOrientation());
					shader.loadTransformation(matrix);
					shader.loadLight(tile.light);
					
					// Draws the sprite
					glDrawArrays(GL_QUADS, 0, 4);
				}
				
				unbindSprite();
			}
		}
		
		// Layers rendering
		shader.loadNaturalLight(new Vector3f(-1, -1, -1));
		for (int i = GameWorld.layerMap.getLayersCount()-1; i >= 0; i--) {
			Layer layer = GameWorld.layerMap.getLayer(i);
			shader.loadDepth(layer.getDepth());
			for (Atlas atl : layer.atlases()) {
				prepareAtlas(atl);
				for (AbstractSprite spr : layer.sprites(atl)) {
					if (spr != null) {
						prepareSprite(spr);
						
						for (Entity ent : layer.getEntities(atl, spr)) {
							Matrix4f matrix = Maths.createTransformationMatrix(ent.getPosition(), ent.getScale(), ent.getRotation());
							shader.loadTransformation(matrix);
							shader.loadAlpha(ent.getAlpha());
							shader.loadLight(ent.getLight());
							
							glDrawArrays(GL_QUADS, 0, 4);
							
							if (ent.getLayer() != null) {
								Matrix4f mat = Maths.createTransformationMatrix(ent.getPosition(), ent.getRotation());
								unbindSprite();
								renderSubLayers(ent, mat);
								prepareSprite(spr);
								prepareAtlas(atl);
							}
						}
						
						unbindSprite();
					}
				}
			}
		}
		
		shader.stop();
		
		// GUI rendering
		guiShader.start();
		
		for (int i = GameWorld.guiLayerMap.getLayersCount()-1; i >= 0; i--) {
			GUILayer layer = (GUILayer) GameWorld.guiLayerMap.getLayer(i);
			for (Atlas atl : layer.atlases()) {
				prepareAtlas(atl);
				for (Entity ent : layer.getEntities(atl)) {
					if (ent.getSprite() != null) {
						prepareSprite(ent.getSprite());
						
						Matrix4f mat = Maths.createTransformationMatrix(ent.getPosition(), ent.getScale(), ent.getRotation());
						
						guiShader.loadTransformation(mat);
						if (ent instanceof GUIText) {
							guiShader.loadColourFilter(((GUIText)ent).getColour());
						} else {
							guiShader.loadColourFilter(new Vector3f(1, 1, 1));
						}
						
						glDrawArrays(GL_QUADS, 0, ent.getSprite().getVertexCount());
						
						if (ent.getLayer() != null) {
							Vector2f pos = new Vector2f(ent.getPosition());
							unbindSprite();
							renderGuiSubLayers(ent, pos);
							prepareSprite(ent.getSprite());
							prepareAtlas(atl);
						}
						
						unbindSprite();
					}
				}
			}
		}
		
		guiShader.stop();
		
	}
	
	/**
	 * Renders a sub layer (a layer contained by an entity)
	 * @param layer the sub layer to render
	 * @param matrix the transformation matrix from the entity containing the layer
	 */
	private void renderSubLayers(Entity entity, Matrix4f matrix) {
		Matrix4f mat = new Matrix4f();
		for (Atlas atl : entity.getLayer().atlases()) {
			prepareAtlas(atl);
			for (AbstractSprite spr : entity.getLayer().sprites(atl)) {
				if (spr != null) {
					prepareSprite(spr);
					
					for (Entity ent : entity.getLayer().getEntities(atl, spr)) {
						Matrix4f.load(matrix, mat);
						Maths.addTransformationMatrix(mat, ent.getPosition(), ent.getScale(), ent.getRotation());
						shader.loadTransformation(mat);
						shader.loadAlpha(ent.getAlpha());
						
						glDrawArrays(GL_QUADS, 0, 4);
						
						if (ent.getLayer() != null) {
							unbindSprite();
							renderSubLayers(ent, mat);
							prepareSprite(spr);
							prepareAtlas(atl);
						}
					}
					
					unbindSprite();
				}
			}
		}
	}
	
	/**
	 * Renders a sub layer of gui elements
	 * @param entity the gui entity that has the sub layer to render
	 */
	private void renderGuiSubLayers(Entity entity, Vector2f pos) {
		Matrix4f mat;
		Vector2f nPos;
		GUILayer layer = (GUILayer) entity.getLayer();
		for (Atlas atl : layer.atlases()) {
			prepareAtlas(atl);
			for (Entity ent : layer.getEntities(atl)) {
				if (ent.getSprite() != null) {
					prepareSprite(ent.getSprite());
					
					nPos = new Vector2f(pos.x + ent.getPosition().x, pos.y + ent.getPosition().y);
					
					mat = Maths.createTransformationMatrix(nPos, ent.getScale(), ent.getRotation());
					
					guiShader.loadTransformation(mat);
					if (ent instanceof GUIText) {
						guiShader.loadColourFilter(((GUIText)ent).getColour());
					} else {
						guiShader.loadColourFilter(new Vector3f(1, 1, 1));
					}
					
					glDrawArrays(GL_QUADS, 0, ent.getSprite().getVertexCount());
					
					if (ent.getLayer() != null) {
						unbindSprite();
						renderGuiSubLayers(ent, nPos);
						prepareSprite(ent.getSprite());
						prepareAtlas(atl);
					}
					
					unbindSprite();
				}
			}
		}
	}
	
	/**
	 * Prepares the display by clearing it
	 */
	public void prepare() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glClearDepth(1);
	}
	
	/**
	 * Prepares a sprite by enabling its attributes
	 * @param spr the sprite to prepare
	 */
	private void prepareSprite(AtlasSprite spr) {
		GL30.glBindVertexArray(spr.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
	}
	
	/**
	 * Prepares an atlas by enabling its texture
	 * @param atlas the atlas to prepare
	 */
	private void prepareAtlas(Atlas atlas) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, atlas.getTextureID());
	}
	
	/**
	 * Unbinds the active sprite
	 */
	private void unbindSprite() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
	}
	
	/**
	 * Cleans Up the shader
	 */
	public void cleanUp() {
		shader.cleanUp();
		guiShader.cleanUp();
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
