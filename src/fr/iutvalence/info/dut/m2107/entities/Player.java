package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.fontMeshCreator.GUIText;
import fr.iutvalence.info.dut.m2107.guiRendering.GUIElement;
import fr.iutvalence.info.dut.m2107.render.DisplayManager;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Input;
import fr.iutvalence.info.dut.m2107.storage.Layer;
import fr.iutvalence.info.dut.m2107.toolbox.Maths;

public class Player extends Character{

	protected boolean isInControl = true;
	
	private GUIText playerGUI = new GUIText("", .8f, 0.82f, 0, 0.5f, false);
	
	private Inventory inventory = new Inventory();
	private String strInventory;
	
	private Item[] quick_Bar = new Item[8];
	float width = 0.05f;
	float height = 0.05f*DisplayManager.aspectRatio;
	float posX = .5f - width/2;
	float posY = height;
	float offsetX = width;
	int selectSlot = 0;
	GUIElement selectQuickBar;
	
	private float degreeShoot = 0;
	private Vector2f shoot;

	public Player(Vector2f pos) {
		super(pos, SpriteDatabase.getPlayerSpr());
		playerGUI.setColour(0, 1, 0);
		playerGUI.setLineHeight(0.024);
		initQuickBar();
		initInventory();
	}
	
	private void initInventory() {
		// TODO Take the item saved from a file save
		this.inventory.add(ItemDatabase.get(0), 10);
		this.inventory.add(ItemDatabase.get(1), 5);
		
		strInventory = this.inventory.toString();
		System.out.println(strInventory);
	}
	
	private void initQuickBar() {
		this.quick_Bar[0] = ItemDatabase.get(2);
		this.quick_Bar[1] = ItemDatabase.get(3);
		this.quick_Bar[2] = ItemDatabase.get(0);
		this.quick_Bar[3] = ItemDatabase.get(1);
		
		for (int slotNumber = 0; slotNumber < 8; slotNumber++) {
			GUIElement quickBar = new GUIElement("gui/quick_bar_slot", new Vector2f(posX - offsetX*3.5f + offsetX*slotNumber, 1-posY), width, height);
			if(this.quick_Bar[slotNumber] != null) {
				GUIElement quickBarItem = new GUIElement(this.quick_Bar[slotNumber].getSprite().getTextureID(), new Vector2f(posX - offsetX*3.5f + offsetX*slotNumber + width/5, 1-posY - height/5), width - width/2.5f, height - height/2.5f);
			}
		}
		selectQuickBar = new GUIElement("gui/select_quick_bar_slot", new Vector2f(posX - offsetX*3.5f + selectSlot*offsetX, 1-posY), width, height);
	}

	@Override
	public void update(Layer layer) {
		if(!strInventory.equals(this.inventory.toString())) {
			strInventory = this.inventory.toString();
			System.out.println(strInventory);
		}
		
		input();
		
		updateShootVal();
		
		updateQuickBar();
		
		if(this.itemToUse != null) {
			this.itemToUse.rot = this.degreeShoot+45;
			this.itemToUse.pos = this.pos;
		}
		
		if(Input.isUseWeapon() && this.itemToUse != null)
			if(this.itemToUse instanceof Bow)
				((Bow) this.itemToUse).use(this);
			if(this.itemToUse instanceof Sword)
				((Sword) this.itemToUse).use(this);
	
		playerGUI.updateText("IsGrounded : " + this.isGrounded +
							"\nIsInAir : " + !this.isGrounded);
		super.update(layer);
	}

	private void updateShootVal() {
		shoot = (Vector2f) new Vector2f(GameWorld.camera.getMouseWorldX() - this.pos.x,
				GameWorld.camera.getMouseWorldY() - this.pos.y).normalise();

		if (shoot.y > 0) degreeShoot = (float) (Math.atan(shoot.x / shoot.y)*180/Math.PI-90);
		else degreeShoot = (float) (Math.atan(shoot.x / shoot.y)*180/Math.PI+90);
		
	}

	private void updateQuickBar() {
		selectSlot += Input.WheelScrolling();
		if(selectSlot > 7) selectSlot -= 8;
		if(selectSlot < 0) selectSlot += 8;
		selectQuickBar.setPosition(new Vector2f(posX - offsetX*3.5f + selectSlot*offsetX, selectQuickBar.getPosition().y));			
		
		if(this.itemToUse != this.quick_Bar[selectSlot]) {				
			if(this.quick_Bar[selectSlot] != null) {
				if(this.itemToUse == null) {
					if(!(this.quick_Bar[selectSlot] instanceof Weapon))
						this.itemToUse = new Item(this.quick_Bar[selectSlot]);
					else this.itemToUse = this.quick_Bar[selectSlot];
						GameWorld.layerMap.getLayer(0).add(this.itemToUse);
				} else {
					GameWorld.layerMap.getLayer(0).remove(this.itemToUse);
					if(!(this.quick_Bar[selectSlot] instanceof Weapon))
						this.itemToUse =  new Item(this.quick_Bar[selectSlot]);
					else this.itemToUse = this.quick_Bar[selectSlot];
					GameWorld.layerMap.getLayer(0).add(GameWorld.player.getItemToUse());						
				}
			} else {
				GameWorld.layerMap.getLayer(0).remove(GameWorld.player.getItemToUse());
				this.itemToUse = null;
			}
		}
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
		
		if(this.vel.y < -70) vel.y = -70;
		if(this.vel.y > 70) vel.y = 70;
		
		if(this.vel.x < -70) this.vel.x = -70;
		if(this.vel.x > 70) this.vel.x = 70;
	}
	
	public Inventory getInventory() {return this.inventory;}
	
	public Item[] getQuickBar() {return this.quick_Bar;}

	public float getDegreeShoot() {return degreeShoot;}
	public Vector2f getShoot() {return shoot;}
}
