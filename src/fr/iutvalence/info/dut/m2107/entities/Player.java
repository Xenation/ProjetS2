package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.Sys;
import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.fontMeshCreator.GUIText;
import fr.iutvalence.info.dut.m2107.guiRendering.GUIElement;
import fr.iutvalence.info.dut.m2107.guiRendering.GUIMaster;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Input;
import fr.iutvalence.info.dut.m2107.storage.Layer;
import fr.iutvalence.info.dut.m2107.toolbox.Maths;

public class Player extends Character{
	
	// Temporary
	private GUIText playerGUI = new GUIText("", .8f, 0.82f, 0, 0.5f, false, true);
	// Temporary
	
	/**
	 * The inventory of the player
	 */
	private Inventory inventory = new Inventory();

	/**
	 * The quick bar of the player
	 */
	private Item[] quickBar = new Item[8];
	
	 // Temporary
	 private float width = 0.1f;
	 private float height = 0.1f;
	 private float posY = 2 - height*1.5f;
	 private int selectSlot = 0;
	 private GUIElement selectQuickBar;
	 private GUIElement[] sprQuickBar = new GUIElement[8];
	 private GUIText[] textQuickBar = new GUIText[8];
	 
	 
	 private GUIElement hpGUI;
	 // Temporary
	
	 
	 protected float invulnerabilityTime;
	 protected float invulnerabilityFadeStep = -0.1f;
		
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
		super(new Vector2f(), SpriteDatabase.getPlayerSpr() , new Collider(-.5f, -1.92f, .5f, 1.92f));
		this.initQuickBar();
		this.initInventory();
		this.hpGUI = new GUIElement(SpriteDatabase.getHeartStr(), new Vector2f(-1 + width, 1 - height), width/2, height/2);
		
		this.initLayer();
		this.layer.add(this.pivot);
		this.initPivot();
		this.pivot.setPosition(new Vector2f(0.75f, -.35f));
	}
	
	/**
	 * Initialize the inventory
	 */
	private void initInventory() {
		// TODO Take the item saved from a file save
		addItem(ItemDatabase.get(0), 50);
		addItem(ItemDatabase.get(1), 15);
		
		this.inventory.initInventory();
	}
	
	/**
	 * Initialize the quick bar
	 */
	private void initQuickBar() {
		this.quickBar[0] = ItemDatabase.get(2);
		this.quickBar[1] = ItemDatabase.get(3);
		
		for (int slotNumber = 0; slotNumber < 8; slotNumber++) {
			new GUIElement(SpriteDatabase.getQuickBarSlotStr(), new Vector2f(-width*3.5f + width*slotNumber, 1-posY), width, height);
			if(this.quickBar[slotNumber] != null) {
				sprQuickBar[slotNumber] = new GUIElement(this.quickBar[slotNumber].getSprite().getTextureID(), new Vector2f(-width*3.5f + width*slotNumber, 1-posY), width - width/2.5f, height - height/2.5f);
				textQuickBar[slotNumber] = new GUIText("" + this.quickBar[slotNumber].stack , .8f, width*2.875f + width*slotNumber/2, 1-height/1.5f, .1f, true);
			}
		}
		selectQuickBar = new GUIElement(SpriteDatabase.getSelectQuickBarSlotStr(), new Vector2f(-width*3.5f + selectSlot*width, 1-posY), width, height);
	}

	/* (non-Javadoc)
	 * @see fr.iutvalence.info.dut.m2107.entities.Character#update(fr.iutvalence.info.dut.m2107.storage.Layer)
	 */
	@Override
	public void update(Layer layer) {		
		input();
		
		updateShootVal();
		updateQuickBar();
		
		if(this.invulnerabilityTime > Sys.getTime()/1000f) {
			this.alpha += this.invulnerabilityFadeStep;
			if(this.itemOnHand != null) this.itemOnHand.alpha += this.invulnerabilityFadeStep;
			if(this.alpha >= 1) this.invulnerabilityFadeStep = -.1f;
			else if(this.alpha <= 0.5f) this.invulnerabilityFadeStep = .1f;
		} else {
			this.alpha = 1;
			if(this.itemOnHand != null) this.itemOnHand.alpha = 1;
		}
		
		if(this.degreeShoot > -90 && this.degreeShoot < 90) {
			this.scale.setX(Maths.fastAbs(this.scale.x));
			this.pivot.pos.x = .75f;
		} else {
			this.scale.setX(-Maths.fastAbs(this.scale.x));
			this.pivot.pos.x = -.75f;
		}
		pivot.setRotation(GameWorld.player.getDegreeShoot());
		
		if(Input.isMouseLeft() && this.itemOnHand != null && GameWorld.camera.isFree()) {
			if(this.scale.x == -1 && this.degreeShoot < 90 && this.degreeShoot > -90) {
				this.scale.x = 1;
				this.pivot.pos.x = .65f;
			}
			else if(this.scale.x == 1 && (this.degreeShoot > 90 || this.degreeShoot < -90)) {
				this.scale.x = -1;
				this.pivot.pos.x = -.65f;
			}
			if(this.itemOnHand instanceof Bow)
				((Bow) this.itemOnHand).use(this);
			if(this.itemOnHand instanceof Sword)
				((Sword) this.itemOnHand).use(this);
		}
		
		playerGUI.updateText("IsGrounded : " + this.isGrounded);
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
		
		if (Input.isKey1())	this.selectSlot = 0;
		if (Input.isKey2())	this.selectSlot = 1;
		if (Input.isKey3()) this.selectSlot = 2;
		if (Input.isKey4()) this.selectSlot = 3;
		if (Input.isKey5())	this.selectSlot = 4;
		if (Input.isKey6())	this.selectSlot = 5;
		if (Input.isKey7())	this.selectSlot = 6;
		if (Input.isKey8())	this.selectSlot = 7;
		
		selectSlot -= Input.WheelScrolling();
		if(selectSlot > 7) selectSlot -= 8;
		if(selectSlot < 0) selectSlot += 8;
		selectQuickBar.setPosition(new Vector2f(-width*3.5f + selectSlot*width, selectQuickBar.getPosition().y));			
		
		if(this.itemOnHand != this.quickBar[selectSlot]) {
			if(this.quickBar[selectSlot] != null) {
				if(this.itemOnHand != null)
					this.pivot.getLayer().remove(this.itemOnHand);
				
				if(!(this.quickBar[selectSlot] instanceof Weapon)) {
					this.itemOnHand = new Item(this.quickBar[selectSlot]);
					this.pivot.getLayer().add(this.itemOnHand);
				} else {
					this.itemOnHand = this.quickBar[selectSlot];
					if(this.itemOnHand instanceof Bow)	this.itemOnHand.setPosition(new Vector2f(-.3f, 0));
					if(this.itemOnHand instanceof Sword)this.itemOnHand.setPosition(new Vector2f(.7f, 0));
					this.pivot.getLayer().add(this.itemOnHand);
				}
			} else {
				this.pivot.getLayer().remove(this.itemOnHand);
				this.itemOnHand = null;
			}
		}		
	}
	
	/**
	 * Update the player velocity with inputs
	 */
	private void input() {		
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
		
		if(this.vel.y < -70) vel.y = -70;
		if(this.vel.y > 70) vel.y = 70;
		
		if(this.vel.x < -70) this.vel.x = -70;
		if(this.vel.x > 70) this.vel.x = 70;
	}
	
	public boolean addItem(Item item, int stack) {
		byte addState = addQuickBarItem(item, stack);
		if(addState == 1) return true;
		else if(addState == -1) return false;
		return this.inventory.add(item, stack);
	}
	
	public boolean addItem(Item item) {
		return addItem(item, item.stack);
	}
	
	public byte addQuickBarItem(Item item, int stack) {
		if(stack <= item.getMAX_STACK()) {
			for (Item it : quickBar) {
				if(it != null) {
					if(it.id == item.id) {
						if(it.stack + stack <= it.MAX_STACK) {
							it.changeStack(stack);
							return 1;
						} return -1;
					}
				}
			}
		} return 0;
	}
	
	public boolean removeQuickBarItem(int index, int stack) {
		if((this.quickBar[index].stack -= stack) <= 0) {
			this.quickBar[index] = null;
			GUIMaster.removeElement(this.sprQuickBar[index]);
			textQuickBar[index].updateText("");
			return false;
		}
		textQuickBar[index].updateText("" + this.quickBar[index].stack);
		return true;
	}
	
	/**
	 * Return the inventory of the player
	 * @return the inventory of the player
	 */
	public Inventory getInventory() {return this.inventory;}
	
	/**
	 * Return the item of the quick bar at a specified index of the player
	 * @return the item of the quick bar at a specified index of the player
	 */
	public Item getQuickBarItem(int index) {return this.quickBar[index];}

	/**
	 * Return the length of the quick bar
	 * @return the length of the quick bar
	 */
	public int getQuickBarLength() {return this.quickBar.length;}
	
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
