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
	
	// Temporary
	private GUIText playerGUI = new GUIText("", .8f, 0.82f, 0, 0.5f, false);
	// Temporary
	
	/**
	 * The inventory of the player
	 */
	private Inventory inventory = new Inventory();
	
	// Temporary
	private String strInventory;
	// Temporary
	
	/**
	 * The quick bar of the player
	 */
	private Item[] quick_Bar = new Item[8];
	
	 // Temporary
	 private float width = 0.1f;
	 private float height = 0.1f;
	 private float posX = 0;
	 private float posY = 2 - height*1.5f;
	 private int selectSlot = 0;
	 private GUIElement selectQuickBar;
	 // Temporary
	
	/**
	 * The angle between the player and the camera
	 */
	private float degreeShoot = 0;
	
	/**
	 * The normalized vector between the player and the camera
	 */
	private Vector2f shoot;

	public boolean rightWallJump = false;
	public boolean leftWallJump = false;

	/**
	 * Constructor of a player
	 */
	public Player() {
		super(new Vector2f(), SpriteDatabase.getPlayerSpr() , new Collider(-.5f, -1.8f, .5f, 1.8f));
		playerGUI.setColour(0, 1, 0);
		playerGUI.setLineHeight(0.024);
		initQuickBar();
		initInventory();
	}
	
	/**
	 * Initialize the inventory
	 */
	private void initInventory() {
		// TODO Take the item saved from a file save
		
		this.inventory.add(ItemDatabase.get(0), 10);
		this.inventory.add(ItemDatabase.get(1), 5);
		
		strInventory = this.inventory.toString();
		System.out.println(strInventory);
	}
	
	/**
	 * Initialize the quick bar
	 */
	private void initQuickBar() {
		this.quick_Bar[0] = ItemDatabase.get(2);
		this.quick_Bar[1] = ItemDatabase.get(3);
		this.quick_Bar[2] = ItemDatabase.get(0);
		this.quick_Bar[3] = ItemDatabase.get(1);
		
		for (int slotNumber = 0; slotNumber < 8; slotNumber++) {
			new GUIElement("gui/quick_bar_slot", new Vector2f(posX - width*3.5f + width*slotNumber, 1-posY), width, height);
			if(this.quick_Bar[slotNumber] != null) {
				new GUIElement(this.quick_Bar[slotNumber].getSprite().getTextureID(), new Vector2f(posX - width*3.5f + width*slotNumber, 1-posY), width - width/2.5f, height - height/2.5f);
			}
		}
		selectQuickBar = new GUIElement("gui/select_quick_bar_slot", new Vector2f(posX - width*3.5f + selectSlot*width, 1-posY), width, height);
	}

	/* (non-Javadoc)
	 * @see fr.iutvalence.info.dut.m2107.entities.Character#update(fr.iutvalence.info.dut.m2107.storage.Layer)
	 */
	@Override
	public void update(Layer layer) {
		if(!strInventory.equals(this.inventory.toString())) {
			strInventory = this.inventory.toString();
			System.out.println(strInventory);
		}
		
		input();
		
		updateShootVal();
		
		updateQuickBar();
		
		if(this.itemOnHand != null) {
			this.itemOnHand.rot = this.degreeShoot;
			this.itemOnHand.pos = this.pos;
		}
		
		if(Input.isMouseLeft() && this.itemOnHand != null) {
			if(this.itemOnHand instanceof Bow)
				((Bow) this.itemOnHand).use(this);
			if(this.itemOnHand instanceof Sword)
				((Sword) this.itemOnHand).use(this);
		}
		
		playerGUI.updateText("IsGrounded : " + this.isGrounded +
							"\nIsInAir : " + !this.isGrounded);
		super.update(layer);
	}

	/**
	 * Update the shoot values
	 */
	private void updateShootVal() {
		shoot = (Vector2f) new Vector2f(GameWorld.camera.getMouseWorldX() - this.pos.x,
				GameWorld.camera.getMouseWorldY() - this.pos.y).normalise();

		if (shoot.y > 0) degreeShoot = (float) (Math.atan(shoot.x / shoot.y)*180/Math.PI-90);
		else degreeShoot = (float) (Math.atan(shoot.x / shoot.y)*180/Math.PI+90);
	}

	/**
	 * Update the quick bar
	 */
	private void updateQuickBar() {
		selectSlot += Input.WheelScrolling();
		if(selectSlot > 7) selectSlot -= 8;
		if(selectSlot < 0) selectSlot += 8;
		selectQuickBar.setPosition(new Vector2f(posX - width*3.5f + selectSlot*width, selectQuickBar.getPosition().y));			
		
		if(this.itemOnHand != this.quick_Bar[selectSlot]) {
			if(this.quick_Bar[selectSlot] != null) {
				if(this.itemOnHand == null) {
					if(!(this.quick_Bar[selectSlot] instanceof Weapon))
						this.itemOnHand = new Item(this.quick_Bar[selectSlot]);
					else this.itemOnHand = this.quick_Bar[selectSlot];
						GameWorld.layerMap.getLayer(0).add(this.itemOnHand);
				} else {
					GameWorld.layerMap.getLayer(0).remove(this.itemOnHand);
					if(!(this.quick_Bar[selectSlot] instanceof Weapon))
						this.itemOnHand =  new Item(this.quick_Bar[selectSlot]);
					else this.itemOnHand = this.quick_Bar[selectSlot];
					GameWorld.layerMap.getLayer(0).add(this.itemOnHand);						
				}
			} else {
				GameWorld.layerMap.getLayer(0).remove(this.itemOnHand);
				this.itemOnHand = null;
			}
		}
	}
	
	/**
	 * Update the player velocity with inputs
	 */
	private void input() {
		this.vel.y -= GameWorld.gravity * DisplayManager.deltaTime();
		
		if(isGrounded) rightWallJump = true;
		if(isGrounded) leftWallJump = true;
		
		if (GameWorld.camera.isFree()) {
			if (Input.isJumping() && this.isGrounded) this.vel.y = this.jumpHeight;
			
			//if (Input.isMoveUp())	this.vel.y += this.spd/2;
			//if (Input.isMoveDown())	this.vel.y += -this.spd/2;
			if (Input.isMoveRight()) {
				if(!rightWallJump || !leftWallJump) this.vel.x += this.spd/6;
				else this.vel.x += this.spd;
			}
			if (Input.isMoveLeft()) {
				if(!leftWallJump || !rightWallJump) this.vel.x += -this.spd/6;
				else this.vel.x += -this.spd;
			}
		}
		
		if(rightWallJump == false) this.vel.x += -this.spd/2;
		if(leftWallJump == false) this.vel.x += this.spd/2;
		
		this.vel.x = Maths.lerp(this.vel.x, 0, 0.25f);
		
		if(this.vel.y < -70) vel.y = -70;
		if(this.vel.y > 70) vel.y = 70;
		
		if(this.vel.x < -70) this.vel.x = -70;
		if(this.vel.x > 70) this.vel.x = 70;
	}
	
	/**
	 * Return the inventory of the player
	 * @return the inventory of the player
	 */
	public Inventory getInventory() {return this.inventory;}
	
	/**
	 * Return the quick bar of the player
	 * @return the quick bar of the player
	 */
	public Item[] getQuickBar() {return this.quick_Bar;}

	/**
	 * Return the angle of shot
	 * @return the angle of shot
	 */
	public float getDegreeShoot() {return degreeShoot;}
	
	/**
	 * Return the vector of shot
	 * @return the vector of shot
	 */
	public Vector2f getShoot() {return shoot;}
}
