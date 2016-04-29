package fr.iutvalence.info.dut.m2107.entities;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.models.Sprite;
import fr.iutvalence.info.dut.m2107.render.DisplayManager;
import fr.iutvalence.info.dut.m2107.render.Renderer;
import fr.iutvalence.info.dut.m2107.storage.Chunk;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Layer;
import fr.iutvalence.info.dut.m2107.tiles.Tile;
import fr.iutvalence.info.dut.m2107.toolbox.Maths;

public class Collider {

	private float minX, minY;
	private float maxX, maxY;
	
	private final float localMinX, localMinY;
	private final float localMaxX, localMaxY;
	
	private Entity ent;
	
	private boolean hasStepUp;

	public Collider(Sprite spr) {
		this.localMinX = -spr.getSize().x/2;
		this.localMinY = -spr.getSize().y/2;
		this.localMaxX = spr.getSize().x/2;
		this.localMaxY = spr.getSize().y/2;
		updateLocPos();
	}
	
	public Collider(Vector2f min, Vector2f max) {
		this.localMinX = min.x;
		this.localMinY = min.y;
		this.localMaxX = max.x;
		this.localMaxY = max.y;
		updateLocPos();
	}
	
	public Collider(float minX, float minY, float maxX, float maxY) {
		this.localMinX = minX;
		this.localMinY = minY;
		this.localMaxX = maxX;
		this.localMaxY = maxY;
		updateLocPos();
	}
	
	public void checkCharacterContinuousCollision() {
		updateColPos();
		
		hasStepUp = false;
		((Character)ent).isGrounded = false;
		
		int continuousStep = (int) ((Math.abs(((Character)this.ent).vel.x) + Math.abs(((Character)this.ent).vel.y))/8+1);

		float stepXtoAdd = ((Character)this.ent).vel.x * DisplayManager.deltaTime() / continuousStep;
		float stepYtoAdd = ((Character)this.ent).vel.y * DisplayManager.deltaTime() / continuousStep;
		float stepX = this.ent.pos.x;
		float stepY = this.ent.pos.y;
		
		for (int step = continuousStep; step > 0; step--) {
			if(((Character)this.ent).vel.x != 0) stepX += stepXtoAdd;
			if(((Character)this.ent).vel.y != 0) stepY += stepYtoAdd;
			Vector2f nextPos = new Vector2f(stepX, stepY);
			
			Collider encompassCol = encompassTrajectory(new Vector2f(this.ent.col.minX + this.getW()/2, this.ent.col.minY + this.getH()/2), nextPos);
			encompassCol.extendAll(this.getW()/2, this.getH()/2);
			
			checkCharacterCollision(encompassCol, stepX);
			
			updateColPos();
			if(((Character)this.ent).vel.x != 0) {
				this.minX += stepXtoAdd;
				this.maxX += stepXtoAdd;
			}
			if(((Character)this.ent).vel.y != 0) {
				this.minY += stepYtoAdd;
				this.maxY += stepYtoAdd;
			}
		}
	}
	
	public void checkCharacterCollision(Collider encompassCol, float stepX) {
		List<Tile> surroundTile = generateSurroundingTiles(encompassCol);
		if(surroundTile.size() == 0) return;
		
		Vector2f modVel = new Vector2f(1, 1);
		
		for (Tile tile : surroundTile) {
			if(isCollidingLeft(this, tile)) { // if the tile is on my left
				if(this.minY <= tile.y + Tile.TILE_SIZE && this.maxY >= tile.y && GameWorld.chunkMap.getRightTile(tile) == null) {
					// I'm on the right of the tile
					if((this.minY >= tile.y && this.minY <= tile.y + Tile.TILE_SIZE) &&
							GameWorld.chunkMap.getTopTile(tile) == null &&
							GameWorld.chunkMap.getTileAt(tile.x, tile.y+2) == null &&
							GameWorld.chunkMap.getTileAt(tile.x, tile.y+3) == null &&
							GameWorld.chunkMap.getTileAt(tile.x, tile.y+4) == null &&
							GameWorld.chunkMap.getTileAt(tile.x+1, tile.y+4) == null &&
							GameWorld.chunkMap.getTileAt(tile.x+2, tile.y+4) == null) {
						// My bottom is between the tile height and there is no block to prevent the StepUp
						modVel.y = 0;
						ent.pos.y = tile.y + Tile.TILE_SIZE + this.getH()/2;
						((Character)ent).isGrounded = true;
						hasStepUp = true;
					} else {
						// I can't StepUp so I block the x movement and I stick to the tile
						modVel.x = 0;
						ent.pos.x = tile.x + Tile.TILE_SIZE + this.getW()/2;
					}
				} else {
					//I'm above or under the tile
					if(((Character)this.ent).vel.y <= 0) {
						if(GameWorld.chunkMap.getTopTile(tile) == null) modVel.y = 0;
						else modVel.x = 0;
					} else {
						if(GameWorld.chunkMap.getBottomTile(tile) == null) modVel.y = 0;
						else modVel.x = 0;
					}
				}
			}else
			if(isCollidingRight(this, tile)) { // if the tile is on my right
				if(this.minY <= tile.y + Tile.TILE_SIZE && this.maxY >= tile.y && GameWorld.chunkMap.getLeftTile(tile) == null) {
					// I'm on the left of the tile
					if((this.minY >= tile.y && this.minY <= tile.y + Tile.TILE_SIZE) &&
							GameWorld.chunkMap.getTopTile(tile) == null &&
							GameWorld.chunkMap.getTileAt(tile.x, tile.y+2) == null &&
							GameWorld.chunkMap.getTileAt(tile.x, tile.y+3) == null &&
							GameWorld.chunkMap.getTileAt(tile.x, tile.y+4) == null &&
							GameWorld.chunkMap.getTileAt(tile.x-1, tile.y+4) == null &&
							GameWorld.chunkMap.getTileAt(tile.x-2, tile.y+4) == null) {
						// My bottom is between the tile height and there is no block to prevent the StepUp
						modVel.y = 0;
						ent.pos.y = tile.y + Tile.TILE_SIZE + this.getH()/2;
						((Character)ent).isGrounded = true;
						hasStepUp = true;
					} else {
						// I can't StepUp so I block the x movement and I stick to the tile
						modVel.x = 0;
						ent.pos.x = tile.x - this.getW()/2;
					}
				} else {
					//I'm above or under the tile
					if(((Character)this.ent).vel.y <= 0) {
						if(GameWorld.chunkMap.getTopTile(tile) == null) modVel.y = 0;
						else modVel.x = 0;
					} else {
						if(GameWorld.chunkMap.getBottomTile(tile) == null) modVel.y = 0;
						else modVel.x = 0;
					}
				}
			}else
			if(isCollidingUp(this, tile)) { //if the tile is above me
				if(this.minX <= tile.x + Tile.TILE_SIZE && this.maxX >= tile.x && GameWorld.chunkMap.getBottomTile(tile) == null) {
					// I'm under the tile
					modVel.y = 0;
					ent.pos.y = tile.y - this.getH()/2;
				}
			}else
			if(isCollidingDown(this, tile)) { // if the tile is under me
				if(this.minX <= tile.x + Tile.TILE_SIZE && this.maxX >= tile.x && GameWorld.chunkMap.getTopTile(tile) == null) {
					// I'm above the tile
					modVel.y = 0;
					((Character)ent).isGrounded = true;
					if(!hasStepUp) ent.pos.y = tile.y + Tile.TILE_SIZE + this.getH()/2;
				}
			}
		}
		
		if(!hasStepUp) ((Character)this.ent).vel.x *= modVel.x;
		((Character)this.ent).vel.y *= modVel.y;
	}
	
	public boolean isContinuousCollidingWithMap() {
		int continuousStep = (int) ((Math.abs(((Ammunition)this.ent).vel.x) + Math.abs(((Ammunition)this.ent).vel.y))/8+1);
		float stepXtoAdd = ((Ammunition)this.ent).vel.x * DisplayManager.deltaTime() / continuousStep;
		float stepYtoAdd = ((Ammunition)this.ent).vel.y * DisplayManager.deltaTime() / continuousStep;
		float stepX = this.ent.pos.x;
		float stepY = this.ent.pos.y;
		
		for (int step = continuousStep; step > 0; step--) {
			stepX += stepXtoAdd;
			stepY += stepYtoAdd;
			Vector2f nextPos = new Vector2f(stepX, stepY);
			
			Collider encompassCol = encompassTrajectory(new Vector2f(this.ent.col.minX + this.getW()/2, this.ent.col.minY + this.getH()/2), nextPos);
			encompassCol.extendAll(this.getW()/2, this.getH()/2);
			
			Tile tileColliding = isCollidingWithMap(encompassCol);
			if(tileColliding != null) {
				this.ent.pos = nextPos;
				((Ammunition)this.ent).vel = new Vector2f(0, 0);
				return true;
			}
			
			this.minX += stepXtoAdd;
			this.minY += stepYtoAdd;
			this.maxX += stepXtoAdd;
			this.maxY += stepYtoAdd;
		}
		return false;
	}
	
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
	
	public Tile isCollidingWithMap(Collider encompassCol) {
		for (Chunk chunk : GameWorld.chunkMap.getSurroundingChunks(Renderer.BOUNDARY_LEFT, Renderer.BOUNDARY_RIGHT, Renderer.BOUNDARY_TOP, Renderer.BOUNDARY_BOTTOM, new Vector2f(this.minX, this.minY))) 
			for (Tile tile : chunk)
				if(!isColliding(encompassCol, tile))
					return tile;
		return null;
	}
	
	public Entity isCollidingWithEntity(Layer layer) {
		for (Entity ent : layer){
				if(!isColliding(this, ent.col))
					return ent;
		}
		return null;
	}
	
	public List<Tile> generateSurroundingTiles(Collider col) {
		List<Tile> tiles = new ArrayList<Tile>();
		for (Chunk chunk : GameWorld.chunkMap.getSurroundingChunks(Renderer.BOUNDARY_LEFT, Renderer.BOUNDARY_RIGHT, Renderer.BOUNDARY_TOP, Renderer.BOUNDARY_BOTTOM, ent.pos)) 
			for (Tile tile : chunk)
				if(!isColliding(col, tile))
					tiles.add(tile);
		return tiles;
	}
	
	public boolean isColliding(Collider col, Tile tile) {
		return isCollidingLeft(col, tile) || isCollidingRight(col, tile) ||
				isCollidingUp(col, tile) || isCollidingDown(col, tile);
	}
	
	public boolean isCollidingLeft(Collider col, Tile tile) {
		float tileMaxX = tile.x + Tile.TILE_SIZE;
		return tileMaxX <= col.minX;
	}
	
	public boolean isCollidingRight(Collider col, Tile tile) {
		float tileMinX = tile.x;
		return col.maxX <= tileMinX;
	}
	
	public boolean isCollidingUp(Collider col, Tile tile) {
		float tileMinY = tile.y;
		return col.maxY <= tileMinY;
	}
	
	public boolean isCollidingDown(Collider col, Tile tile) {
		float tileMaxY = tile.y + Tile.TILE_SIZE;
		return tileMaxY <= col.minY;
	}
	
	public boolean isColliding(Collider col, Collider other) {
		return isCollidingLeft(col, other) || isCollidingRight(col, other) ||
				isCollidingUp(col, other) || isCollidingDown(col, other);
	}
	
	public boolean isCollidingLeft(Collider col, Collider other) {
		float otherMaxX = other.maxX;
		return otherMaxX <= col.minX;
	}
	
	public boolean isCollidingRight(Collider col, Collider other) {
		float otherMinX = other.minX;
		return col.maxX <= otherMinX;
	}
	
	public boolean isCollidingUp(Collider col, Collider other) {
		float otherMinY = other.minY;
		return col.maxY <= otherMinY;
	}
	
	public boolean isCollidingDown(Collider col, Collider other) {
		float otherMaxY = other.maxY;
		return otherMaxY <= col.minY;
	}

	public void updateColPos() {
		this.minX = Maths.round(ent.pos.x + localMinX, 5);
		this.minY = Maths.round(ent.pos.y + localMinY, 5);
		
		this.maxX = Maths.round(ent.pos.x + localMaxX, 5);
		this.maxY = Maths.round(ent.pos.y + localMaxY, 5);
	}
	
	private void updateLocPos() {
		this.minX = localMinX;
		this.minY = localMinY;
		
		this.maxX = localMaxX;
		this.maxY = localMaxY;
	}
	
	public void extendAll(float width, float height) {
		extendWidth(width);
		extendHeight(height);
	}
	
	public void extendWidth(float width) {
		extendLeft(width);
		extendRight(width);
	}
	
	public void extendHeight(float height) {
		extendUp(height);
		extendDown(height);
	}
	
	public void extendLeft (float left)  {this.minX -= left;}
	public void extendRight(float right) {this.maxX += right;}
	
	public void extendDown(float down) {this.minY -= down;}
	public void extendUp  (float up)   {this.maxY += up;}
	
	public Vector2f getMin() {return new Vector2f(minX, minY);}
	public Vector2f getMax() {return new Vector2f(maxX, maxY);}
	
	public float getMinX() {return minX;}
	public float getMinY() {return minY;}

	public float getMaxX() {return maxX;}
	public float getMaxY() {return maxY;}

	public float getActualW() {return maxX - minX;}
	public float getActualH() {return maxY - minY;}
	
	public float getW() {return localMaxX - localMinX;}
	public float getH() {return localMaxY - localMinY;}
	
	
	public void setEnt(Entity ent) {this.ent = ent;}
	public Entity getEnt() {return this.ent;}

	@Override
	public String toString() {
		return "Collider [minX=" + minX + ", minY=" + minY + ", maxX=" + maxX + ", maxY=" + maxY + ", W="
				+ getW() + ", H=" + getH() + "]";
	}


}