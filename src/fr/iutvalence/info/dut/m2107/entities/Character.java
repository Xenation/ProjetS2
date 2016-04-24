package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.models.Sprite;
import fr.iutvalence.info.dut.m2107.storage.Layer;
import fr.iutvalence.info.dut.m2107.tiles.Tile;

public class Character extends LivingEntity{

	public enum CharacterType{
		PLAYER,
		MOB,
	}
	
	protected boolean isGrounded = true;
	protected boolean prevGrounded = isGrounded;
	
	protected CharacterType type;
	
	protected Entity itemToUse;
	
	public Character() {
		super();
	}
	
	public Character(CharacterType type, Vector2f pos, Sprite spr) {
		super(pos, spr);
		this.type = type;
	}

	public Character(Vector2f pos, float rot, Sprite spr, Collider col, Vector2f vel, float spd, int health, int armor,
			int jumpHeight) {
		super(pos, rot, spr, col, vel, spd, health, armor, jumpHeight);
	}
	
	@Override
	public void update(Layer layer) {
		// Temporary discrete collision //
//		Vector2f nextPos = new Vector2f(this.pos.x + (this.vel.x * DisplayManager.deltaTime()), this.pos.y + (this.vel.y * DisplayManager.deltaTime()));
//		Collider encompassCol = this.col.encompassTrajectory(this.pos, nextPos);
//		encompassCol.extendAll(this.col.getW()/2, this.col.getH()/2);
		
//		this.col.checkCharacterCollision(encompassCol, this);
		//////////////////////////////////
		this.col.checkCharacterContinuousCollision();
		
		// STEP DOWN
		if(isGrounded == false && prevGrounded == true && this.vel.y < 0) {
			
			Collider tmp = new Collider(new Vector2f(this.col.getMinX(), this.col.getMinY()), new Vector2f(this.col.getMaxX(), this.col.getMaxY()));
			tmp.extendDown(Tile.TILE_SIZE + 0.1f);
			Tile tmpTile = tmp.isCollidingWithMap(tmp);
			if(tmpTile != null) {
				this.vel.y = 0;
				this.pos.y = tmpTile.y + Tile.TILE_SIZE + this.spr.getSize().y/2;
				this.isGrounded = true;
			}
		}
		prevGrounded = isGrounded;
		
		/*for (Entity entity : layer) {
			if(entity != this) this.col.checkCollision(this, entity);
		}*/
		super.update(layer);
	}
	
	public void setItemToUse(Entity itemToUse) {this.itemToUse = itemToUse;}
	public Entity getItemToUse() {return this.itemToUse;}
	public CharacterType getType() {return this.type;}

}
