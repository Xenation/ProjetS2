package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.items.Inventory;
import fr.iutvalence.info.dut.m2107.models.Sprite;
import fr.iutvalence.info.dut.m2107.render.DisplayManager;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Layer;
import fr.iutvalence.info.dut.m2107.toolbox.Maths;

public class Player extends LivingEntity{

	protected boolean isGrounded = true;
	protected boolean isInControl = true;
	
	private Inventory inventory = new Inventory();
	
	public Player(Vector2f pos, float rotation, Sprite sprite, Layer layer, Collider col, Vector2f velocity, float speed, int health,
			int armor, int jumpHeight) {
		super(pos, rotation, sprite, layer, col, velocity, speed, health, armor, jumpHeight);
	}

	public Player(Vector2f pos, Sprite sprite, Layer layer) {
		super(pos, sprite, layer);
	}

	public Player() {
		super();
	}

	@Override
	public void update(Layer layer) {
		input();
		
//		for (Chunk chunk : GameWorld.chunkMap.getSurroundingChunks(-Renderer.UNITS_Y/2*DisplayManager.aspectRatio, Renderer.UNITS_Y/2*DisplayManager.aspectRatio, Renderer.UNITS_Y/2, -Renderer.UNITS_Y/2, this.pos)) {
//			for (Tile tile : chunk) {
//				System.out.println(tile);
//			}
//		}
		
		for (Entity entity : layer) {
			if(entity != this) this.col.checkCollision(this, entity);
		}
		
		super.update(layer);
	}

	private void input() {
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
	        	isGrounded = false;
			}
			
			if (Keyboard.isKeyDown(Keyboard.KEY_Z))
				this.vel.y += this.spd;
			if (Keyboard.isKeyDown(Keyboard.KEY_S))
				this.vel.y -= this.spd;
			if (Keyboard.isKeyDown(Keyboard.KEY_D))
				this.vel.x += this.spd;
			if (Keyboard.isKeyDown(Keyboard.KEY_Q))
				this.vel.x -= this.spd;
		}
		
		if(this.pos.y < 0 || this.pos.y + this.vel.y * this.spd * DisplayManager.deltaTime() < 0) {
			this.vel.y = 0;
			this.pos.y = 0;
			isGrounded = true;
		}
		this.vel.x = Maths.lerp(this.vel.x, 0, 0.25f);
		
		if(vel.x < 0.0000001f && vel.x > -0.0000001f) vel.x = 0;
		if(vel.y < 0.0000001f && vel.y > -0.0000001f) vel.y = 0;
	}
}
