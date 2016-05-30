package fr.iutvalence.info.dut.m2107.entities;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.inventory.Ammunition;
import fr.iutvalence.info.dut.m2107.inventory.Arrow;
import fr.iutvalence.info.dut.m2107.models.AbstractSprite;
import fr.iutvalence.info.dut.m2107.render.DisplayManager;
import fr.iutvalence.info.dut.m2107.render.Renderer;
import fr.iutvalence.info.dut.m2107.storage.Chunk;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Input;
import fr.iutvalence.info.dut.m2107.storage.Layer;
import fr.iutvalence.info.dut.m2107.storage.Layer.LayerStore;
import fr.iutvalence.info.dut.m2107.tiles.Tile;
import fr.iutvalence.info.dut.m2107.toolbox.Maths;

public class Collider {

	/**
	 * The minimum x or y of the collider
	 */
	private float minX, minY;
	
	/**
	 * The maximum x or y of the collider
	 */
	private float maxX, maxY;
	
	/**
	 * The local minimum x or y of the collider
	 */
	private final float localMinX, localMinY;
	
	/**
	 * The local maximum x or y of the collider
	 */
	private final float localMaxX, localMaxY;
	
	/**
	 * The entity the collider is set to
	 */
	private Entity ent;

	/**
	 * Constructor of a collider
	 * @param spr The sprite to fit
	 */
	public Collider(AbstractSprite spr) {
		this.localMinX = -spr.getSize().x/2;
		this.localMinY = -spr.getSize().y/2;
		this.localMaxX = spr.getSize().x/2;
		this.localMaxY = spr.getSize().y/2;
		initPos();
	}
	
	/**
	 * Constructor of a collider
	 * @param col The collider to copy
	 */
	public Collider(Collider col) {
		this.localMinX = col.localMinX;
		this.localMinY = col.localMinY;
		this.localMaxX = col.localMaxX;
		this.localMaxY = col.localMaxY;
		this.ent = col.ent;
		initPos();
	}
	
	/**
	 * Constructor of a collider
	 * @param min The minimum Vector2f
	 * @param max The maximum Vector2f
	 */
	public Collider(Vector2f min, Vector2f max) {
		this.localMinX = min.x;
		this.localMinY = min.y;
		this.localMaxX = max.x;
		this.localMaxY = max.y;
		initPos();
	}
	
	/**
	 * Constructor of a collider
	 * @param minX The minimum x
	 * @param minY The minimum y
	 * @param maxX The maximum x
	 * @param maxY The maximum y
	 */
	public Collider(float minX, float minY, float maxX, float maxY) {
		this.localMinX = minX;
		this.localMinY = minY;
		this.localMaxX = maxX;
		this.localMaxY = maxY;
		initPos();
	}
	
	/**
	 * Check the discrete collision of a character
	 */
	/*public void checkCharacterDiscreteCollision() {
		updateColPos();
		
		((Character)ent).hasStepUp = false;
		((Character)ent).isGrounded = false;
		
		Vector2f nextPos = new Vector2f(((Character)this.ent).pos.x + (((Character)this.ent).vel.x * DisplayManager.deltaTime()), ((Character)this.ent).pos.y + (((Character)this.ent).vel.y * DisplayManager.deltaTime()));
		Collider encompassCol = ((Character)this.ent).col.encompassTrajectory(((Character)this.ent).pos, nextPos);
		encompassCol.extendAll(((Character)this.ent).col.getW()/2, ((Character)this.ent).col.getH()/2);
		
		checkCharacterCollision(encompassCol);
	}*/
	
	public void checkContinuousCollision() {
		if(this.ent instanceof Ammunition)
			AmmunitionContinuousCollision(((Ammunition)this.ent));
		else
			TerrestrialContinuousCollision();
	}
	
	/**
	 * Check the continuous collision of a character
	 */
	private void TerrestrialContinuousCollision() {
		updateColPos();
		
		((TerrestrialCreature)ent).hasStepUp = false;
		((TerrestrialCreature)ent).isGrounded = false;
		if(this.ent instanceof Player) ((Player)ent).wallSlide = false;
		if(this.ent instanceof Rat) ((Rat)ent).wallWalk = false;
		
		Collider globalCollider = encompassCollider(this, new Vector2f(this.minX + this.getW()/2 + ((TerrestrialCreature)this.ent).vel.x * DisplayManager.deltaTime(), this.minY + this.getH()/2 + ((TerrestrialCreature)this.ent).vel.y * DisplayManager.deltaTime()), 3);
		
		List<Tile> globalTiles = generateGlobalSurroundingTiles(globalCollider);
		
		int continuousStep = initStepCollision(((TerrestrialCreature)this.ent).vel);
		
		float stepXtoAdd = ((TerrestrialCreature)this.ent).vel.x * DisplayManager.deltaTime() / continuousStep;
		float stepYtoAdd = ((TerrestrialCreature)this.ent).vel.y * DisplayManager.deltaTime() / continuousStep;
		float stepX = this.ent.pos.x;
		float stepY = this.ent.pos.y;
		
		for (int step = continuousStep; step > 0; step--) {
			if(((TerrestrialCreature)this.ent).vel.x != 0) stepX += stepXtoAdd;
			if(((TerrestrialCreature)this.ent).vel.y != 0) stepY += stepYtoAdd;
			Vector2f nextPos = new Vector2f(stepX, stepY);
			
			Collider encompassCol = encompassCollider(this, nextPos, 0.5f);
			
			if(this.ent instanceof Player)
				checkPlayerCollision(globalTiles, encompassCol);
			else if(this.ent instanceof Rat)
				checkCrawlingCollision(globalTiles, encompassCol);
			else 
				checkTerrestrialCollision(globalTiles, encompassCol);
			
			if(((TerrestrialCreature)this.ent).vel.x != 0) {
				this.minX += stepXtoAdd;
				this.maxX += stepXtoAdd;
			}
			if(((TerrestrialCreature)this.ent).vel.y != 0) {
				this.minY += stepYtoAdd;
				this.maxY += stepYtoAdd;
			}
			updateColPos();
		}
		if(!(this.ent instanceof Rat)) checkStepDown();
	}
	
	/**
	 * Check the collision of character
	 * @param encompassCol The collider which encompass the current position and the next position
	 */
	private void checkPlayerCollision(List<Tile> globalTiles, Collider encompassCol) {
		List<Tile> surroundTile = generateSurroundingTiles(globalTiles, encompassCol);
		if(surroundTile.size() == 0) return;
		
		Vector2f modVel = new Vector2f(1, 1);
		
		for (Tile tile : surroundTile) {
			if(isOnLeft(this, tile)) { // if the tile is on my left
				if(this.minY < tile.y + Tile.TILE_SIZE && this.maxY > tile.y && !checkTilePosition(globalTiles, tile.x+1, tile.y)) {					
					// I'm on the perfect right of the tile
					if(this.minY >= tile.y && this.minY <= tile.y + Tile.TILE_SIZE) {
						// My bottom is between the tile height
						if(!checkTilePosition(globalTiles, tile.x, tile.y+1) &&
								!checkTilePosition(globalTiles, tile.x, tile.y+2) &&
								!checkTilePosition(globalTiles, tile.x, tile.y+3) &&
								!checkTilePosition(globalTiles, tile.x, tile.y+4) &&
								!checkTilePosition(globalTiles, tile.x+1, tile.y+4)) {
							// There is no block to prevent the StepUp
							modVel.y = 0;
							ent.pos.y = tile.y + Tile.TILE_SIZE + this.getH()/2;
							((Player)ent).isGrounded = true;
							((Player)ent).hasStepUp = true;
						} else if(Input.isJumping() && !((Player)this.ent).prevGrounded && ((Player)this.ent).leftWallJump) {
							// Jump input and previously grounded
							((Player)this.ent).vel.y = ((Player)this.ent).jumpHeight;
							((Player)this.ent).leftWallJump = false;
							TerrestrialContinuousCollision();
						} else {
							if(!((Player)this.ent).prevGrounded && ((Player)this.ent).vel.y < 0) {
								((Player)this.ent).vel.y = Maths.lerp(((Player)this.ent).vel.y, -5, 0.05f);
								((Player)this.ent).wallSlide = true;
							}
							modVel.x = 0;
							ent.pos.x = tile.x + Tile.TILE_SIZE + this.getW()/2;
							((Player)this.ent).rightWallJump = true;
						}
					} else {
						// I can't StepUp or wall jump so I block the x movement and stick to the tile
						modVel.x = 0;
						ent.pos.x = tile.x + Tile.TILE_SIZE + this.getW()/2;
					}
				} else {
					// I'm under or above the right tile
					if(isOnUp(this, tile)) {
						if(checkTilePosition(globalTiles, tile.x, tile.y-1) || checkTilePosition(globalTiles, tile.x, tile.y-2) || checkTilePosition(globalTiles, tile.x, tile.y-3) || checkTilePosition(globalTiles, tile.x, tile.y-4)) {
							modVel.x = 0;
							ent.pos.x = tile.x + Tile.TILE_SIZE + this.getW()/2;
						} else modVel.y = 0;
					} else if(isOnDown(this, tile)) {
						if(checkTilePosition(globalTiles, tile.x, tile.y+1) || checkTilePosition(globalTiles, tile.x, tile.y+2) || checkTilePosition(globalTiles, tile.x, tile.y+3) || checkTilePosition(globalTiles, tile.x, tile.y+4)) {
							modVel.x = 0;
							ent.pos.x = tile.x + Tile.TILE_SIZE + this.getW()/2;
						} else modVel.y = 0;
					}
				}
			} else if(isOnRight(this, tile)) { // if the tile is on my right
				if(this.minY < tile.y + Tile.TILE_SIZE && this.maxY > tile.y && !checkTilePosition(globalTiles, tile.x-1, tile.y)) {					
					// I'm on the perfect left of the tile
					if(this.minY >= tile.y && this.minY <= tile.y + Tile.TILE_SIZE) {
						// My bottom is between the tile height
						if(!checkTilePosition(globalTiles, tile.x, tile.y+1) &&
								!checkTilePosition(globalTiles, tile.x, tile.y+2) &&
								!checkTilePosition(globalTiles, tile.x, tile.y+3) &&
								!checkTilePosition(globalTiles, tile.x, tile.y+4) &&
								!checkTilePosition(globalTiles, tile.x-1, tile.y+4)) {
							modVel.y = 0;
							ent.pos.y = tile.y + Tile.TILE_SIZE + this.getH()/2;
							((Player)ent).isGrounded = true;
							((Player)ent).hasStepUp = true;
						} else if(Input.isJumping() && !((Player)this.ent).prevGrounded && ((Player)this.ent).rightWallJump) {
							// Jump input and previously grounded
							((Player)this.ent).vel.y = ((Player)this.ent).jumpHeight;
							((Player)this.ent).rightWallJump = false;
							TerrestrialContinuousCollision();
						} else {
							if(!((Player)this.ent).prevGrounded && ((Player)this.ent).vel.y < 0) {
								((Player)this.ent).vel.y = Maths.lerp(((Player)this.ent).vel.y, -5, 0.05f);
								((Player)this.ent).wallSlide = true;
							}
							modVel.x = 0;
							ent.pos.x = tile.x - this.getW()/2;
							((Player)this.ent).leftWallJump = true;
						}
					} else {
						// I can't StepUp or wall jump so I block the x movement and stick to the tile
						modVel.x = 0;
						ent.pos.x = tile.x - this.getW()/2;
					}
				} else {
					// I'm under or above the right tile
					if(isOnUp(this, tile)) {
						if(checkTilePosition(globalTiles, tile.x, tile.y-1) || checkTilePosition(globalTiles, tile.x, tile.y-2) || checkTilePosition(globalTiles, tile.x, tile.y-3) || checkTilePosition(globalTiles, tile.x, tile.y-4)) {
							modVel.x = 0;
							ent.pos.x = tile.x - this.getW()/2;
						} else modVel.y = 0;
					} else if(isOnDown(this, tile)) {
						if(checkTilePosition(globalTiles, tile.x, tile.y+1) || checkTilePosition(globalTiles, tile.x, tile.y+2) || checkTilePosition(globalTiles, tile.x, tile.y+3) || checkTilePosition(globalTiles, tile.x, tile.y+4)) {
							modVel.x = 0;
							ent.pos.x = tile.x - this.getW()/2;
						} else modVel.y = 0;
					}
				}
			} else if(isOnUp(this, tile)) { //if the tile is above me
				if(this.minX < tile.x + Tile.TILE_SIZE && this.maxX > tile.x && !checkTilePosition(globalTiles, tile.x, tile.y-1)) {
					// I'm under the tile
					modVel.y = 0;
					ent.pos.y = tile.y - this.getH()/2;
				}
			} else if(isOnDown(this, tile)) { // if the tile is under me
				if(this.minX < tile.x + Tile.TILE_SIZE && this.maxX > tile.x && !checkTilePosition(globalTiles, tile.x, tile.y+1)) {
					// I'm above the tile					
					modVel.y = 0;
					((Player)ent).isGrounded = true;
					if(!((Player)ent).hasStepUp) ent.pos.y = tile.y + Tile.TILE_SIZE + this.getH()/2;
				}
			}
		}
		
		if(!((Player)ent).hasStepUp) ((Player)this.ent).vel.x *= modVel.x;
		((Player)this.ent).vel.y *= modVel.y;
	}
	
	private void checkTerrestrialCollision(List<Tile> globalTiles, Collider encompassCol) {
		List<Tile> surroundTile = generateSurroundingTiles(globalTiles, encompassCol);
		if(surroundTile.size() == 0) return;
		
		Vector2f modVel = new Vector2f(1, 1);
		
		for (Tile tile : surroundTile) {
			if(isOnLeft(this, tile)) { // if the tile is on my left
				if(this.minY < tile.y + Tile.TILE_SIZE && this.maxY > tile.y && !checkTilePosition(globalTiles, tile.x+1, tile.y)) {					
					// I'm on the perfect right of the tile
					if(this.minY >= tile.y && this.minY <= tile.y + Tile.TILE_SIZE) {
						// My bottom is between the tile height
						if(!checkTilePosition(globalTiles, tile.x, tile.y+1) &&
								!checkTilePosition(globalTiles, tile.x, tile.y+2) &&
								!checkTilePosition(globalTiles, tile.x, tile.y+3) &&
								!checkTilePosition(globalTiles, tile.x, tile.y+4) &&
								!checkTilePosition(globalTiles, tile.x+1, tile.y+4)) {
							// There is no block to prevent the StepUp
							modVel.y = 0;
							ent.pos.y = tile.y + Tile.TILE_SIZE + this.getH()/2;
							((TerrestrialCreature)ent).isGrounded = true;
							((Character)ent).hasStepUp = true;
						} else {
							if(!((TerrestrialCreature)this.ent).prevGrounded && ((TerrestrialCreature)this.ent).vel.y < 0) {
								((TerrestrialCreature)this.ent).vel.y = Maths.lerp(((TerrestrialCreature)this.ent).vel.y, -5, 0.05f);
							}
							modVel.x = 0;
							ent.pos.x = tile.x + Tile.TILE_SIZE + this.getW()/2;
						}
					} else {
						// I can't StepUp or wall jump so I block the x movement and stick to the tile
						modVel.x = 0;
						ent.pos.x = tile.x + Tile.TILE_SIZE + this.getW()/2;
					}
				} else {
					// I'm under or above the right tile
					if(isOnUp(this, tile)) {
						if(checkTilePosition(globalTiles, tile.x, tile.y-1) || checkTilePosition(globalTiles, tile.x, tile.y-2) || checkTilePosition(globalTiles, tile.x, tile.y-3) || checkTilePosition(globalTiles, tile.x, tile.y-4)) {
							modVel.x = 0;
							ent.pos.x = tile.x + Tile.TILE_SIZE + this.getW()/2;
						} else modVel.y = 0;
					} else if(isOnDown(this, tile)) {
						if(checkTilePosition(globalTiles, tile.x, tile.y+1) || checkTilePosition(globalTiles, tile.x, tile.y+2) || checkTilePosition(globalTiles, tile.x, tile.y+3) || checkTilePosition(globalTiles, tile.x, tile.y+4)) {
							modVel.x = 0;
							ent.pos.x = tile.x + Tile.TILE_SIZE + this.getW()/2;
						} else modVel.y = 0;
					}
				}
			} else if(isOnRight(this, tile)) { // if the tile is on my right
				if(this.minY < tile.y + Tile.TILE_SIZE && this.maxY > tile.y && !checkTilePosition(globalTiles, tile.x-1, tile.y)) {					
					// I'm on the perfect left of the tile
					if(this.minY >= tile.y && this.minY <= tile.y + Tile.TILE_SIZE) {
						// My bottom is between the tile height
						if(!checkTilePosition(globalTiles, tile.x, tile.y+1) &&
								!checkTilePosition(globalTiles, tile.x, tile.y+2) &&
								!checkTilePosition(globalTiles, tile.x, tile.y+3) &&
								!checkTilePosition(globalTiles, tile.x, tile.y+4) &&
								!checkTilePosition(globalTiles, tile.x-1, tile.y+4)) {
							modVel.y = 0;
							ent.pos.y = tile.y + Tile.TILE_SIZE + this.getH()/2;
							((TerrestrialCreature)ent).isGrounded = true;
							((TerrestrialCreature)ent).hasStepUp = true;
						} else {
							if(!((TerrestrialCreature)this.ent).prevGrounded && ((TerrestrialCreature)this.ent).vel.y < 0) {
								((TerrestrialCreature)this.ent).vel.y = Maths.lerp(((TerrestrialCreature)this.ent).vel.y, -5, 0.05f);
							}
							modVel.x = 0;
							ent.pos.x = tile.x - this.getW()/2;
						}
					} else {
						// I can't StepUp or wall jump so I block the x movement and stick to the tile
						modVel.x = 0;
						ent.pos.x = tile.x - this.getW()/2;
					}
				} else {
					// I'm under or above the right tile
					if(isOnUp(this, tile)) {
						if(checkTilePosition(globalTiles, tile.x, tile.y-1) || checkTilePosition(globalTiles, tile.x, tile.y-2) || checkTilePosition(globalTiles, tile.x, tile.y-3) || checkTilePosition(globalTiles, tile.x, tile.y-4)) {
							modVel.x = 0;
							ent.pos.x = tile.x - this.getW()/2;
						} else modVel.y = 0;
					} else if(isOnDown(this, tile)) {
						if(checkTilePosition(globalTiles, tile.x, tile.y+1) || checkTilePosition(globalTiles, tile.x, tile.y+2) || checkTilePosition(globalTiles, tile.x, tile.y+3) || checkTilePosition(globalTiles, tile.x, tile.y+4)) {
							modVel.x = 0;
							ent.pos.x = tile.x - this.getW()/2;
						} else modVel.y = 0;
					}
				}
			} else if(isOnUp(this, tile)) { //if the tile is above me
				if(this.minX < tile.x + Tile.TILE_SIZE && this.maxX > tile.x && !checkTilePosition(globalTiles, tile.x, tile.y-1)) {
					// I'm under the tile
					modVel.y = 0;
					ent.pos.y = tile.y - this.getH()/2;
				}
			} else if(isOnDown(this, tile)) { // if the tile is under me
				if(this.minX < tile.x + Tile.TILE_SIZE && this.maxX > tile.x && !checkTilePosition(globalTiles, tile.x, tile.y+1)) {
					// I'm above the tile					
					modVel.y = 0;
					((TerrestrialCreature)ent).isGrounded = true;
					if(!((TerrestrialCreature)ent).hasStepUp) ent.pos.y = tile.y + Tile.TILE_SIZE + this.getH()/2;
				}
			}
		}
		
		if(!((TerrestrialCreature)ent).hasStepUp) ((TerrestrialCreature)this.ent).vel.x *= modVel.x;
		((TerrestrialCreature)this.ent).vel.y *= modVel.y;
	}
	
	private void checkCrawlingCollision(List<Tile> globalTiles, Collider encompassCol) {
		boolean top = false;
		List<Tile> surroundTile = generateSurroundingTiles(globalTiles, encompassCol);
		if(surroundTile.size() == 0) return;
		Vector2f modVel = new Vector2f(1, 1);
		for (Tile tile : surroundTile) {
			if(isOnLeft(this, tile)) { // if the tile is on my left
				if(this.minY < tile.y + Tile.TILE_SIZE && this.maxY > tile.y && !checkTilePosition(globalTiles, tile.x+1, tile.y)) {
					// I'm on the perfect right of the tile
					((Rat)this.ent).wallWalk = true;
					this.ent.pos.x = tile.x + Tile.TILE_SIZE + this.getW()/2;
					((Rat) this.ent).getVelocity().y = ((Rat) this.ent).getVelocity().x + GameWorld.gravity * DisplayManager.deltaTime();
					modVel.x = 0;
					modVel.y = 1;
				} else {
					// I'm under or above the right tile
					if(isOnUp(this, tile)) {
						if(checkTilePosition(globalTiles, tile.x, tile.y-1)) {
							modVel.x = 0;
							ent.pos.x = tile.x + Tile.TILE_SIZE + this.getW()/2;
						} else modVel.y = 0;
					} else if(isOnDown(this, tile)) {
						if(checkTilePosition(globalTiles, tile.x, tile.y+1)) {
							modVel.x = 0;
							ent.pos.x = tile.x + Tile.TILE_SIZE + this.getW()/2;
						} else modVel.y = 0;
					}
				}
			} else if(isOnRight(this, tile)) { // if the tile is on my right
				if(this.minY < tile.y + Tile.TILE_SIZE && this.maxY > tile.y && !checkTilePosition(globalTiles, tile.x-1, tile.y)) {
					// I'm on the perfect left of the tile
					if(!((Rat)this.ent).wallWalk) {
						((Rat)this.ent).wallWalk = true;
						this.ent.pos.x = tile.x - this.getW()/2;
						((Rat) this.ent).getVelocity().y = ((Rat) this.ent).getVelocity().x + GameWorld.gravity * DisplayManager.deltaTime();
						modVel.x = 0;
						modVel.y = 1;
					}
				} else {
					// I'm under or above the right tile
					if(isOnUp(this, tile)) {
						if(checkTilePosition(globalTiles, tile.x, tile.y-1)) {
							modVel.x = 0;
							ent.pos.x = tile.x - this.getW()/2;
						} else modVel.y = 0;
					} else if(isOnDown(this, tile)) {
						if(checkTilePosition(globalTiles, tile.x, tile.y+1)) {
							modVel.x = 0;
							ent.pos.x = tile.x - this.getW()/2;
						} else modVel.y = 0;
					}
				}
			} else if(isOnUp(this, tile)) { //if the tile is above me
				if(this.minX < tile.x + Tile.TILE_SIZE && this.maxX > tile.x  && !checkTilePosition(globalTiles, tile.x, tile.y-1)) {
					// I'm under the tile
					top = true;
					modVel.y = 0;
					ent.pos.y = tile.y - this.getH()/2;
				}
			} else if(isOnDown(this, tile)) { // if the tile is under me
				if(this.minX < tile.x + Tile.TILE_SIZE && this.maxX > tile.x && !checkTilePosition(globalTiles, tile.x, tile.y+1)) {
					// I'm above the tile
					if(!((Rat)this.ent).wallWalk) {
						modVel.y = 0;
						((TerrestrialCreature)ent).isGrounded = true;
						ent.pos.y = tile.y + Tile.TILE_SIZE + this.getH()/2;
					}
				}
			}
		}
		((TerrestrialCreature)this.ent).vel.x *= modVel.x;
		((TerrestrialCreature)this.ent).vel.y *= modVel.y;
		if(top) ((TerrestrialCreature)this.ent).vel.y = 0;
	}
	
	/**
	 * Check the step down of a character
	 */
	private void checkStepDown() {
		if(((TerrestrialCreature) this.ent).isGrounded == false && ((TerrestrialCreature) this.ent).prevGrounded == true && ((TerrestrialCreature) this.ent).vel.y < 0) {
			Collider tmp = new Collider(new Vector2f(this.getMinX(), this.getMinY()), new Vector2f(this.getMaxX(), this.getMaxY()));
			tmp.extendDown(Tile.TILE_SIZE + 0.1f);
			Tile tmpTile = tmp.isCollidingWithMap(tmp);
			if(tmpTile != null) {
				((TerrestrialCreature) this.ent).vel.y = 0;
				((TerrestrialCreature) this.ent).pos.y = tmpTile.y + Tile.TILE_SIZE + this.getH()/2;
				((TerrestrialCreature) this.ent).isGrounded = true;
			}
		}
		((TerrestrialCreature) this.ent).prevGrounded = ((TerrestrialCreature) this.ent).isGrounded;
	}
	
	private int initStepCollision(Vector2f vel) {
		int continuousStep = (int) ((Maths.fastAbs(vel.x) + Maths.fastAbs(vel.y))/8+1)*2;
		continuousStep *= DisplayManager.deltaTime()*20;
		if(continuousStep < 1) continuousStep = 1;
		
		return continuousStep;
	}
	
	/**
	 * Check the continuous collision with the map
	 * @return true when colliding otherwise false
	 */
	private Entity AmmunitionContinuousCollision(Ammunition ammo) {
		int continuousStep = initStepCollision(ammo.getVelocity());
		
		float stepXtoAdd = ammo.getVelocity().x * DisplayManager.deltaTime() / continuousStep;
		float stepYtoAdd = ammo.getVelocity().y * DisplayManager.deltaTime() / continuousStep;
		float stepX = ammo.col.minX + this.getW()/2;
		float stepY = ammo.col.minY + this.getH()/2;
		
		Collider globalCollider = encompassCollider(ammo.col, new Vector2f(ammo.col.minX + this.getW()/2 + ammo.getVelocity().x * DisplayManager.deltaTime(), ammo.col.minY + this.getH()/2 + ammo.getVelocity().y * DisplayManager.deltaTime()), 3);
		
		List<Tile> globalTiles = generateGlobalSurroundingTiles(globalCollider);
		
		for (int step = continuousStep; step > 0; step--) {
			stepX += stepXtoAdd;
			stepY += stepYtoAdd;
			Vector2f nextPos = new Vector2f(stepX, stepY);
			
			Collider encompassCol = encompassCollider(ammo.col, nextPos, 0.5f);
			
			Tile tileColliding = isCollidingWithReducedMap(globalTiles, encompassCol);
			Entity entColliding = isCollidingWithEntity(new Layer[] {GameWorld.layerMap.getStoredLayer(LayerStore.MOBS), GameWorld.layerMap.getStoredLayer(LayerStore.DECORATION)});
			
			if(tileColliding != null || entColliding != null) {
				ammo.setVelocity(new Vector2f(0, 0));
				ammo.pos.x = nextPos.x -(float) (Math.cos((ammo.rot)*Math.PI/180)*ammo.spr.getSize().x/2.5f);
				ammo.pos.y = nextPos.y -(float) -(Math.sin((ammo.rot)*Math.PI/180)*ammo.spr.getSize().y/2.5f);
				if(entColliding != null) {
					ammo.pos = new Vector2f(ammo.pos.x - entColliding.pos.x, ammo.pos.y - entColliding.pos.y);
					return entColliding;
				}
				if(ammo instanceof Arrow)
					((Arrow)this.ent).setPiercingTile(tileColliding);
				return entColliding;
			}
			
			this.minX += stepXtoAdd;
			this.minY += stepYtoAdd;
			this.maxX += stepXtoAdd;
			this.maxY += stepYtoAdd;
		}
		return null;
	}
	
	private Collider encompassCollider(Collider entCollider, Vector2f nextPos, float extendValue) {
		Collider encompassCol = encompassTrajectory(new Vector2f(entCollider.minX + entCollider.getW()/2, entCollider.minY + entCollider.getH()/2), nextPos);
		encompassCol.extendAll(entCollider.getW()*extendValue, entCollider.getH()*extendValue);
		return encompassCol;
	}

	/**
	 * Encompass the current position and the next position in a new collider
	 * @param actualPos The actual position
	 * @param nextPos The next position
	 * @return The new collider which encompass the both position
	 */
	public Collider encompassTrajectory(Vector2f actualPos, Vector2f nextPos) {
		Collider encompassCol;
		if(actualPos.getX() <= nextPos.getX()) {
			if(actualPos.getY() <= nextPos.getY())
				encompassCol = new Collider(new Vector2f(actualPos.x , actualPos.y), new Vector2f(nextPos.x , nextPos.y));
			else
				encompassCol = new Collider(new Vector2f(actualPos.x , nextPos.y), new Vector2f(nextPos.x , actualPos.y));
		} else {
			if(actualPos.getY() <= nextPos.getY())
				encompassCol = new Collider(new Vector2f(nextPos.x , actualPos.y), new Vector2f(actualPos.x , nextPos.y));
			else
				encompassCol = new Collider(new Vector2f(nextPos.x, nextPos.y), new Vector2f(actualPos.x, actualPos.y));
		}
		return encompassCol;
	}
	
	/**
	 * Check if the collider collide with a tile on the map
	 * @param encompassCol the collider to check with
	 * @return The tile colliding with the collider
	 */
	public Tile isCollidingWithMap(Collider encompassCol) {
		for (Chunk chunk : GameWorld.chunkMap.getSurroundingChunks(Renderer.BOUNDARY_LEFT, Renderer.BOUNDARY_RIGHT, Renderer.BOUNDARY_TOP, Renderer.BOUNDARY_BOTTOM, new Vector2f(this.minX, this.minY))) { 
			for (Tile tile : chunk)
				if(isColliding(encompassCol, tile))
					return tile;
		}
		return null;
	}
	
	public Tile isCollidingWithReducedMap(List<Tile> globalTiles, Collider encompassCol) {
		for (Tile tile : globalTiles)
			if(isColliding(encompassCol, tile))
				return tile;
		return null;
	}
	
	/**
	 * Check if this entity collide with an other
	 * @param layer The layer to check in
	 * @return The entity colliding
	 */
	public Entity isCollidingWithEntity(Layer[] layer) {
		for (Layer lay : layer) {			
			for (Entity ent : lay)
				if(isColliding(this, ent.col))
					return ent;
		}
		return null;
	}
	
	public boolean checkTilePosition(List<Tile> globalTiles, int x, int y) {
		for (Tile tile : globalTiles)
			if(tile.x == x && tile.y == y) return true;
		return false;
	}
	
	/**
	 * Generate all the solid tiles from the surrounding chunks colliding with a specific collider
	 * @param col The collider generation based on
	 * @return A list of solid tiles colliding with
	 */
	public List<Tile> generateGlobalSurroundingTiles(Collider col) {
		List<Tile> tiles = new ArrayList<Tile>();
		for (Chunk chunk : GameWorld.chunkMap.getSurroundingChunks(Renderer.BOUNDARY_LEFT, Renderer.BOUNDARY_RIGHT, Renderer.BOUNDARY_TOP, Renderer.BOUNDARY_BOTTOM, new Vector2f(col.localMinX + col.getW()/2, col.localMinY + col.getH()/2))) { 
			for (Tile tile : chunk)
				if(isColliding(col, tile) && tile.getType().isSolid())
					tiles.add(tile);
		}
		return tiles;
	}
	
	/**
	 * Generate all the tiles from a reduced map colliding with a specific collider
	 * @param globalTiles The reduced map
	 * @param col The collider generating based on
	 * @return A list of tiles colliding with
	 */
	public List<Tile> generateSurroundingTiles(List<Tile> globalTiles, Collider col) {
		List<Tile> tiles = new ArrayList<Tile>();
		for (Tile tile : globalTiles)
			if(isColliding(col, tile))
				tiles.add(tile);
		return tiles;
	}
	
	/**
	 * Check the collision between a collider and a tile
	 * @param col The collider to check
	 * @param tile The tile to check
	 * @return true if colliding otherwise false
	 */
	public boolean isColliding(Collider col, Tile tile) {
		return !(isOnLeft(col, tile) || isOnRight(col, tile) ||
				isOnUp(col, tile) || isOnDown(col, tile));
	}
	
	/**
	 * Check the left collision between a collider and a tile
	 * @param col The collider to check
	 * @param tile The tile to check
	 * @return true if colliding otherwise false
	 */
	public boolean isOnLeft(Collider col, Tile tile) {
		float tileMaxX = tile.x + Tile.TILE_SIZE;
		return tileMaxX <= col.minX;
	}
	
	/**
	 * Check the right collision between a collider and a tile
	 * @param col The collider to check
	 * @param tile The tile to check
	 * @return true if colliding otherwise false
	 */
	public boolean isOnRight(Collider col, Tile tile) {
		float tileMinX = tile.x;
		return col.maxX <= tileMinX;
	}
	
	/**
	 * Check the up collision between a collider and a tile
	 * @param col The collider to check
	 * @param tile The tile to check
	 * @return true if colliding otherwise false
	 */
	public boolean isOnUp(Collider col, Tile tile) {
		float tileMinY = tile.y;
		return col.maxY <= tileMinY;
	}
	
	/**
	 * Check the down collision between a collider and a tile
	 * @param col The collider to check
	 * @param tile The tile to check
	 * @return true if colliding otherwise false
	 */
	public boolean isOnDown(Collider col, Tile tile) {
		float tileMaxY = tile.y + Tile.TILE_SIZE;
		return tileMaxY <= col.minY;
	}
	
	/**
	 * Check the collision between a collider and an other collider
	 * @param col The collider to check
	 * @param other The other collider to check
	 * @return true if colliding otherwise false
	 */
	public boolean isColliding(Collider col, Collider other) {
		return !(isOnLeft(col, other) || isOnRight(col, other) ||
				isOnUp(col, other) || isOnDown(col, other));
	}
	
	/**
	 * Check the left collision between a collider and an other collider
	 * @param col The collider to check
	 * @param other The other collider to check
	 * @return true if colliding otherwise false
	 */
	public boolean isOnLeft(Collider col, Collider other) {
		float otherMaxX = other.maxX;
		return otherMaxX <= col.minX;
	}
	
	/**
	 * Check the right collision between a collider and an other collider
	 * @param col The collider to check
	 * @param other The other collider to check
	 * @return true if colliding otherwise false
	 */
	public boolean isOnRight(Collider col, Collider other) {
		float otherMinX = other.minX;
		return col.maxX <= otherMinX;
	}
	
	/**
	 * Check the up collision between a collider and an other collider
	 * @param col The collider to check
	 * @param other The other collider to check
	 * @return true if colliding otherwise false
	 */
	public boolean isOnUp(Collider col, Collider other) {
		float otherMinY = other.minY;
		return col.maxY <= otherMinY;
	}
	
	/**
	 * Check the down collision between a collider and an other collider
	 * @param col The collider to check
	 * @param other The other collider to check
	 * @return true if colliding otherwise false
	 */
	public boolean isOnDown(Collider col, Collider other) {
		float otherMaxY = other.maxY;
		return otherMaxY <= col.minY;
	}

	/**
	 * Update the collider position
	 */
	public void updateColPos() {
		this.minX = Maths.round(ent.pos.x + localMinX, 5);
		this.minY = Maths.round(ent.pos.y + localMinY, 5);
		
		this.maxX = Maths.round(ent.pos.x + localMaxX, 5);
		this.maxY = Maths.round(ent.pos.y + localMaxY, 5);
	}
	
	/**
	 * Initialize the collider position
	 */
	private void initPos() {
		this.minX = localMinX;
		this.minY = localMinY;
		
		this.maxX = localMaxX;
		this.maxY = localMaxY;
	}
	
	/**
	 * Extend the collider by a width and a height
	 * @param width The width to extend
	 * @param height The height to extend
	 */
	public void extendAll(float width, float height) {
		extendWidth(width);
		extendHeight(height);
	}
	
	/**
	 * Extend the collider by a width
	 * @param width The width to extend
	 */
	public void extendWidth(float width) {
		extendLeft(width);
		extendRight(width);
	}
	
	/**
	 * Extend the collider by a height
	 * @param height The height to extend
	 */
	public void extendHeight(float height) {
		extendUp(height);
		extendDown(height);
	}
	
	/**
	 * Extend the left of the collider
	 * @param left The amount to extend
	 */
	public void extendLeft (float left)  {
		this.minX = Maths.round(this.minX - left, 5);
	}
	
	/**
	 * Extend the right of the collider
	 * @param right The amount to extend
	 */
	public void extendRight(float right) {
		this.maxX = Maths.round(this.maxX + right, 5);
	}
	
	/**
	 * Extend the down of the collider
	 * @param down The amount to extend
	 */
	public void extendDown(float down) {
		this.minY = Maths.round(this.minY - down, 5);
	}
	
	/**
	 * Extend the up of the collider
	 * @param up The amount to extend
	 */
	public void extendUp  (float up)   {
		this.maxY = Maths.round(this.maxY + up, 5);
	}
	
	/**
	 * Return the minimum position of the collider
	 * @return the minimum position of the collider
	 */
	public Vector2f getMin() {return new Vector2f(minX, minY);}
	
	/**
	 * Return the maximum position of the collider
	 * @return the maximum position of the collider
	 */
	public Vector2f getMax() {return new Vector2f(maxX, maxY);}
	
	/**
	 * Return the minimum x position of the collider
	 * @return the minimum x position of the collider
	 */
	public float getMinX() {return minX;}
	
	/**
	 * Return the minimum y position of the collider
	 * @return the minimum y position of the collider
	 */
	public float getMinY() {return minY;}

	/**
	 * Return the maximum x position of the collider
	 * @return the maximum x position of the collider
	 */
	public float getMaxX() {return maxX;}
	
	/**
	 * Return the maximum y position of the collider
	 * @return the maximum y position of the collider
	 */
	public float getMaxY() {return maxY;}

	/**
	 * Return the actual width of the collider
	 * @return the actual width of the collider
	 */
	public float getActualW() {return maxX - minX;}
	
	/**
	 * Return the actual height of the collider
	 * @return the actual height of the collider
	 */
	public float getActualH() {return maxY - minY;}
	
	/**
	 * Return the width of the collider
	 * @return the width of the collider
	 */
	public float getW() {return localMaxX - localMinX;}
	
	/**
	 * Return the height of the collider
	 * @return the height of the collider
	 */
	public float getH() {return localMaxY - localMinY;}
	
	/**
	 * Set the entity owner of the collider
	 * @param ent The entity to set
	 */
	public void setEnt(Entity ent) {this.ent = ent;}
	
	/**
	 * Return the entity owner of the collider
	 * @return the entity owner of the collider
	 */
	public Entity getEnt() {return this.ent;}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Collider [minX=" + minX + ", minY=" + minY + ", maxX=" + maxX + ", maxY=" + maxY + ", W="
				+ getW() + ", H=" + getH() + "]";
	}
}