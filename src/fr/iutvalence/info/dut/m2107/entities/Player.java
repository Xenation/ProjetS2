package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.fontMeshCreator.GUIText;
import fr.iutvalence.info.dut.m2107.items.Inventory;
import fr.iutvalence.info.dut.m2107.items.Item;
import fr.iutvalence.info.dut.m2107.items.SpriteDatabase;
import fr.iutvalence.info.dut.m2107.render.DisplayManager;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Input;
import fr.iutvalence.info.dut.m2107.storage.Layer;
import fr.iutvalence.info.dut.m2107.toolbox.Maths;

public class Player extends Character{

	protected boolean isInControl = true;
	
	private GUIText playerGUI = new GUIText("", .8f, 0.82f, 0, 0.5f, false);
	
	private Inventory inventory = new Inventory();
	private Item[] quick_Bar = new Item[8];

	public Player(Vector2f pos) {
		super(Character.CharacterType.PLAYER, pos, SpriteDatabase.getPlayerSpr());
		playerGUI.setColour(0, 1, 0);
		playerGUI.setLineHeight(0.024);
	}
	
	@Override
	public void update(Layer layer) {
		input();
		
		if(Input.isUseWeapon()) this.weapon.use();
		
		playerGUI.updateText("IsGrounded : " + this.isGrounded +
							"\nIsInAir : " + !this.isGrounded);
		super.update(layer);
	}

	private void input() {
		this.vel.y -= GameWorld.gravity * DisplayManager.deltaTime();
		
		if (isInControl) {
			if (Input.isJumping() && this.isGrounded) this.vel.y = this.jumpHeight;
			
			//if (Input.isMoveUp())	this.vel.y += this.spd/2;
			//if (Input.isMoveDown())	this.vel.y -= this.spd/2;
			if (Input.isMoveRight())this.vel.x += this.spd;
			if (Input.isMoveLeft())	this.vel.x -= this.spd;
		}
		
		this.vel.x = Maths.lerp(this.vel.x, 0, 0.25f);
		
		/*if(this.vel.y < -70) vel.y = -70;
		if(this.vel.y > 70) vel.y = 70;
		
		if(this.vel.x < -70) this.vel.x = -70;
		if(this.vel.x > 70) this.vel.x = 70;*/
	}
	
	public Inventory getInventory() {return this.inventory;}
}
