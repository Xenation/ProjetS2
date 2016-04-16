package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.items.Inventory;
import fr.iutvalence.info.dut.m2107.models.Sprite;
import fr.iutvalence.info.dut.m2107.render.DisplayManager;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Layer;
import fr.iutvalence.info.dut.m2107.tiles.Tile;
import fr.iutvalence.info.dut.m2107.toolbox.Maths;

public class Player extends LivingEntity{

	protected boolean isGrounded = true;
	protected boolean prevGrounded = isGrounded;
	protected boolean isInControl = true;
	
	private Inventory inventory = new Inventory();

	public Player(Vector2f pos, Sprite sprite) {
		super(pos, sprite);
	}

	public Player() {
		super();
	}

	@Override
	public void update(Layer layer) {
		
		Vector2f nextPos = input();
		Collider surroundPos = null;
		
		if(this.pos.getX() <= nextPos.getX()) {
			if(this.pos.getY() <= nextPos.getY())
				surroundPos = new Collider(new Vector2f(this.pos.x , this.pos.y),
											new Vector2f(nextPos.x , nextPos.y));
			else surroundPos = new Collider(new Vector2f(this.pos.x , nextPos.y),
											new Vector2f(nextPos.x , this.pos.y));
		} else {
			if(this.pos.getY() <= nextPos.getY())
				surroundPos = new Collider(new Vector2f(nextPos.x , this.pos.y),
											new Vector2f(this.pos.x , nextPos.y));
			else surroundPos = new Collider(new Vector2f(nextPos.x, nextPos.y),
											new Vector2f(this.pos.x, this.pos.y));
		}
		surroundPos.extendAll(this.spr.getSize().x/2, this.spr.getSize().y/2);
		
		//////////// temporary player parameter ////////////
		this.col.update(surroundPos , this);
		
		if(isGrounded == false && prevGrounded == true && this.vel.y < 0) {
			
			Collider tmp = new Collider(new Vector2f(this.col.getMinX(), this.col.getMinY()), new Vector2f(this.col.getMaxX(), this.col.getMaxY()));
			tmp.extendDown(Tile.TILE_SIZE + 0.01f);
			Tile tmpTile = tmp.isCollindingWithMap();
			if(tmpTile != null) {
				this.vel.y = 0;
				this.pos.y = tmpTile.y + Tile.TILE_SIZE  + this.spr.getSize().y/2 -.099f;
			}
			//System.out.println("StepDown");
		}
		prevGrounded = isGrounded;
		
		
		/*for (Entity entity : layer) {
			if(entity != this) this.col.checkCollision(this, entity);
		}*/
		
		super.update(layer);
	}

	private Vector2f input() {
		this.vel.y -= GameWorld.gravity * DisplayManager.deltaTime();

//		while(Keyboard.next()){
//			if (Keyboard.getEventKeyState()) {
//		        if (Keyboard.getEventKey() == Keyboard.KEY_SPACE && isGrounded) {
//		        	this.vel.y = this.jumpHeight;
//		        	isGrounded = false;
//		        }
//			}
//		}
		
		if (isInControl) {
			if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && isGrounded) {
				this.vel.y = this.jumpHeight;
			}
			
			if (Keyboard.isKeyDown(Keyboard.KEY_Z))
				this.vel.y += this.spd/2;
			if (Keyboard.isKeyDown(Keyboard.KEY_S))
				this.vel.y -= this.spd/2;
			if (Keyboard.isKeyDown(Keyboard.KEY_D))
				this.vel.x += this.spd;
			if (Keyboard.isKeyDown(Keyboard.KEY_Q))
				this.vel.x -= this.spd;
		}
		
		this.vel.x = Maths.lerp(this.vel.x, 0, 0.25f);
		
		if(vel.y < -70) vel.y = -70;
		if(vel.y > 70) vel.y = 70;

		return new Vector2f(this.pos.x + this.vel.x * DisplayManager.deltaTime(), this.pos.y + this.vel.y * DisplayManager.deltaTime());
	}
}
