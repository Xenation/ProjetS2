package fr.iutvalence.info.dut.m2107.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.lwjgl.Sys;
import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.render.Renderer;
import fr.iutvalence.info.dut.m2107.storage.Chunk;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.tiles.Tile;

public class Collider {

	private float minX, minY;
	private float maxX, maxY;
	
	private MovableEntity ent;
	
	private float cautionDistance = 0.01f;

	public Collider() {}
	
	public Collider(Vector2f min, Vector2f max) {
		this.minX = min.x;
		this.minY = min.y;
		this.maxX = max.x;
		this.maxY = max.y;
	}
	
	////////////temporary player parameter ////////////
	public boolean update(Collider surroundMove, Player player) {
		updateColPos();
		
		List<Tile> surroundTile = generateSurroundingTiles(surroundMove);
//		System.out.println(surroundTile.size());
//		Collections.reverse(surroundTile);
		
		player.isGrounded = false;
		
		for (Tile tile : surroundTile) {
			if(isCollidingLeft(this, tile)) { // if the tile is on my left
				if(this.minY <= tile.y + Tile.TILE_SIZE && this.maxY >= tile.y) {
					// I'm on the right of the tile
					if((this.minY >= tile.y && this.minY <= tile.y + Tile.TILE_SIZE) &&
							GameWorld.chunkMap.getTopTile(tile) == null &&
							GameWorld.chunkMap.getTileAt(tile.x, tile.y+2) == null &&
							GameWorld.chunkMap.getTileAt(tile.x, tile.y+3) == null &&
							GameWorld.chunkMap.getTileAt(tile.x, tile.y+4) == null &&
							GameWorld.chunkMap.getTileAt(tile.x+1, tile.y+4) == null &&
							GameWorld.chunkMap.getTileAt(tile.x+2, tile.y+4) == null) {
						// My bottom is between the tile height and there is no block to prevent the StepUp
						ent.vel.y = 0;
						ent.pos.y += tile.y + Tile.TILE_SIZE - this.minY + cautionDistance*2;
//						System.out.println("Step Up Left\t\t" + Sys.getTime());
					} else {
						// I can't StepUp so I block the x movement and I stick to the tile
						ent.vel.x = 0;
						ent.pos.x = tile.x + Tile.TILE_SIZE + this.getW()/2 + cautionDistance;
//						System.out.println("Left\t\t" + Sys.getTime());
					}
				} else {
					//I'm above or under the tile
					if(GameWorld.chunkMap.getTopTile(tile) == null || GameWorld.chunkMap.getBottomTile(tile) == null) {
						ent.vel.y = 0;
//						System.out.println("Slide Right\t\t" + Sys.getTime());
					}
//					else System.out.println("???");
				}
			}else
			if(isCollidingRight(this, tile)) { // if the tile is on my right
				if(this.minY <= tile.y + Tile.TILE_SIZE && this.maxY >= tile.y) {
					// I'm on the left of the tile
					if((this.minY >= tile.y && this.minY <= tile.y + Tile.TILE_SIZE) &&
							GameWorld.chunkMap.getTopTile(tile) == null &&
							GameWorld.chunkMap.getTileAt(tile.x, tile.y+2) == null &&
							GameWorld.chunkMap.getTileAt(tile.x, tile.y+3) == null &&
							GameWorld.chunkMap.getTileAt(tile.x, tile.y+4) == null &&
							GameWorld.chunkMap.getTileAt(tile.x-1, tile.y+4) == null &&
							GameWorld.chunkMap.getTileAt(tile.x-2, tile.y+4) == null) {
						// My bottom is between the tile height and there is no block to prevent the StepUp
						ent.vel.y = 0;
						ent.pos.y += tile.y + Tile.TILE_SIZE - this.minY + cautionDistance*2;
//						System.out.println("Step Up Right\t\t" + Sys.getTime());
					} else {
						// I can't StepUp so I block the x movement and I stick to the tile
						ent.vel.x = 0;
						ent.pos.x = tile.x - this.getW()/2 - cautionDistance;
//						System.out.println("Right\t\t" + Sys.getTime());
					}
				} else {
					if(GameWorld.chunkMap.getTopTile(tile) == null || GameWorld.chunkMap.getBottomTile(tile) == null) {
						//I'm above or under the tile
						ent.vel.y = 0;
//					System.out.println("Slide Right\t\t" + Sys.getTime());
					} 
//					else System.out.println("???");
				}
			}else
			if(isCollidingUp(this, tile)) { //if the tile is above me
				if(this.minX <= tile.x + Tile.TILE_SIZE && this.maxX >= tile.x) {
					// I'm under the tile
					ent.vel.y = 0;
					ent.pos.y = tile.y - this.getH()/2 - cautionDistance;
//					System.out.println("Up\t\t" + Sys.getTime());
//				} else {
//					ent.vel.x = 0;
//					System.out.println("Slide Up");
				}
			}else
			if(isCollidingDown(this, tile)) { // if the tile is under me
				if(this.minX <= tile.x + Tile.TILE_SIZE && this.maxX >= tile.x) {
					// I'm above the tile
					ent.vel.y = 0;
//					ent.pos.y = tile.y + Tile.TILE_SIZE + this.getH()/2 + cautionDistance;
					player.isGrounded = true;
//					System.out.println("Down\t\t" + Sys.getTime());
				} 
//				else {
//					ent.vel.x = 0;
//					System.out.println("Slide Down");
//				}
			}
		}
		return true;
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
	
	public List<Tile> generateSurroundingTiles(Collider col) {
		List<Tile> tiles = new ArrayList<Tile>();
		for (Chunk chunk : GameWorld.chunkMap.getSurroundingChunks(Renderer.BOUNDARY_LEFT, Renderer.BOUNDARY_RIGHT, Renderer.BOUNDARY_TOP, Renderer.BOUNDARY_BOTTOM, ent.pos)) 
			for (Tile tile : chunk)
				if(!isColliding(col, tile))
					tiles.add(tile);
		return tiles;
	}
	
	public Tile isCollindingWithMap() {
		for (Chunk chunk : GameWorld.chunkMap.getSurroundingChunks(Renderer.BOUNDARY_LEFT, Renderer.BOUNDARY_RIGHT, Renderer.BOUNDARY_TOP, Renderer.BOUNDARY_BOTTOM, new Vector2f(this.minX, this.minY))) 
			for (Tile tile : chunk)
				if(!isColliding(this, tile))
					return tile;
		return null;
	}
	
	public void updateColPos() {
		this.minX = ent.pos.x - ent.spr.getSize().x/2 +.1f;
		this.minY = ent.pos.y - ent.spr.getSize().y/2 +.1f;
		
		this.maxX = ent.pos.x + ent.spr.getSize().x/2 -.1f;
		this.maxY = ent.pos.y + ent.spr.getSize().y/2 -.1f;
	}
	
	public void extendAll(float width, float height) {
		extendWidth(width -.1f);
		extendHeight(height -.1f);
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
	
	public void extendUp  (float up)   {this.maxY += up;}
	public void extendDown(float down) {this.minY -= down;}
	
	
	public float getMinX() {return minX;}
	public float getMinY() {return minY;}

	public float getMaxX() {return maxX;}
	public float getMaxY() {return maxY;}

	
	public float getW() {return maxX - minX;}
	public float getH() {return maxY - minY;}
	
	
	public void setEnt(MovableEntity ent) {this.ent = ent;}
	public MovableEntity getEnt() {return this.ent;}

	@Override
	public String toString() {
		return "Collider [minX=" + minX + ", minY=" + minY + ", maxX=" + maxX + ", maxY=" + maxY + ", getW()=" + getW()
				+ ", getH()=" + getH() + "]";
	}
}

/*// ENTITY
public void checkCollision(Player thisEntity, Entity entity) {
	if (thisEntity.col.isCollidingNext(entity.col, new Vector2f(thisEntity.pos.x + thisEntity.vel.x * thisEntity.spd * DisplayManager.deltaTime(),
			thisEntity.pos.y + thisEntity.vel.y * thisEntity.spd * DisplayManager.deltaTime()))) {
		if(!this.isCollidingLeft(entity.col)) {
			thisEntity.vel.x = 0;
			thisEntity.pos.x = entity.pos.x + entity.col.getW();
		}else if(!this.isCollidingRight(entity.col)) {
			thisEntity.vel.x = 0;
			thisEntity.pos.x = entity.pos.x - this.getW();
		}else if(!this.isCollidingTop(entity.col)) {
			thisEntity.vel.y = 0;
			thisEntity.pos.y = entity.pos.y - this.getH();
		}else if(!this.isCollidingBot(entity.col)) {
			thisEntity.vel.y = 0;
			thisEntity.pos.y = entity.pos.y + entity.col.getH();
			thisEntity.isGrounded = true;
		}
	}
}

public boolean isCollidingNext(Collider col, Vector2f nextThis){
	if ((col.getWX() < nextThis.x + this.w)
			&& (col.getWX() + col.w > nextThis.x)
				&& (col.getWY() < nextThis.y + this.h)
					&& (col.getWY() + col.h > nextThis.y)) {
			return true;
		}
	return false;
}

public boolean isCollidingLeft(Collider col) {
	if (col.getWX() + col.w > this.getWX()) return true;
	return false;
}

public boolean isCollidingRight(Collider col) {
	if (col.getWX() < this.getWX() + this.w) return true;
	return false;
}

public boolean isCollidingTop(Collider col) {
	if (col.getWY() < this.getWY() + this.h) return true;
	return false;
}

public boolean isCollidingBot(Collider col) {
	if (col.getWY() + col.h > this.getWY()) return true;
	return false;
}
// <--*/

// TILE
/*public void checkCollision(Player thisEntity, Tile tile) {
	if (thisEntity.col.isCollidingNext(tile, new Vector2f(thisEntity.pos.x + thisEntity.vel.x * thisEntity.spd * DisplayManager.deltaTime(),
															thisEntity.pos.y + thisEntity.vel.y * thisEntity.spd * DisplayManager.deltaTime()))) {	
		float thisLeft = getWX() - this.w/2;
		float thisRight = getWX() + this.w/2;
		float thisTop = getWY() + this.h/2;
		float thisBottom = getWY() - this.h/2;
		
		float tileLeft = tile.x;
		float tileRight = tile.x + Tile.TILE_SIZE;
		float tileTop = tile.y + Tile.TILE_SIZE;
		float tileBottom = tile.y;
		
		if(!this.isCollidingLeft(tile)) {
			if(GameWorld.chunkMap.getRightTile(tile) != null && (thisBottom >= tileTop || thisTop <= tileBottom)) {
				thisEntity.vel.y = 0;
				System.out.println("1");
			} else {
				if(GameWorld.chunkMap.getTopTile(tile) == null && GameWorld.chunkMap.getTileAt(tile.x+1, tile.y+1) == null && (thisBottom >= tileBottom && thisBottom <= tileTop)) {
					thisEntity.pos.y += Tile.TILE_SIZE;
					System.out.println("2");
				} else {
					thisEntity.vel.x = 0;
					thisEntity.pos.x = tile.x + Tile.TILE_SIZE + this.w/2;
					System.out.println("3");
				}
			}
		}
		if(!this.isCollidingRight(tile)) {
			if(GameWorld.chunkMap.getLeftTile(tile) != null && (thisBottom >= tileTop || thisTop <= tileBottom)) {
				thisEntity.vel.y = 0;
				System.out.println("4");
			} else {
				if(GameWorld.chunkMap.getTopTile(tile) == null && GameWorld.chunkMap.getTileAt(tile.x-1, tile.y+1) == null && (thisBottom >= tileBottom && thisBottom <= tileTop)) {
					thisEntity.pos.y += Tile.TILE_SIZE;
					System.out.println("5");
				} else {
					thisEntity.vel.x = 0;
					thisEntity.pos.x = tile.x - this.getW()/2;
					System.out.println("6");
				}
			}
		}
		if(!this.isCollidingTop(tile)) {
			if(GameWorld.chunkMap.getBottomTile(tile) == null || !(thisLeft >= tileRight || thisRight <= tileLeft)) {
				thisEntity.vel.y = 0;
				thisEntity.pos.y = tile.y - this.getH()/2;
				System.out.println("top");
			}
		}
		if(!this.isCollidingBot(tile)) {
			if(GameWorld.chunkMap.getTopTile(tile) == null || !(thisLeft >= tileRight || thisRight <= tileLeft)) {
				thisEntity.vel.y = 0;
				thisEntity.pos.y = tile.y + Tile.TILE_SIZE + this.h/2;
				thisEntity.isGrounded = true;
				System.out.println("bot");
			}
		}
	}
}

public boolean isCollidingNext(Tile tile, Vector2f nextThis){
	if ((tile.getX() < nextThis.x + this.w/2)
			&& (tile.getX() + Tile.TILE_SIZE > nextThis.x - this.w/2)
				&& (tile.getY() < nextThis.y + this.h/2)
					&& (tile.getY() + Tile.TILE_SIZE > nextThis.y - this.h/2)) {
			return true;
		}
	return false;
}

public boolean isCollidingLeft(Tile tile) {
	if (tile.getX() + Tile.TILE_SIZE > this.getWX() - this.w/2) return true;
	return false;
}

public boolean isCollidingRight(Tile tile) {
	if (tile.getX() < this.getWX() + this.w/2) return true;
	return false;
}

public boolean isCollidingTop(Tile tile) {
	if (tile.getY() < this.getWY() + this.h/2) return true;
	return false;
}

public boolean isCollidingBot(Tile tile) {
	if (tile.getY() + Tile.TILE_SIZE > this.getWY() - this.h/2) return true;
	return false;
}*/
// <--
