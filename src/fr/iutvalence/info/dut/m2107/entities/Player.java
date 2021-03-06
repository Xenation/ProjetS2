package fr.iutvalence.info.dut.m2107.entities;

import java.util.Random;

import org.lwjgl.Sys;
import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.fontMeshCreator.GUIText;
import fr.iutvalence.info.dut.m2107.gui.GUIElement;
import fr.iutvalence.info.dut.m2107.gui.GUIMovable;
import fr.iutvalence.info.dut.m2107.gui.GUISlot;
import fr.iutvalence.info.dut.m2107.gui.GUISprite;
import fr.iutvalence.info.dut.m2107.inventory.Bow;
import fr.iutvalence.info.dut.m2107.inventory.Gun;
import fr.iutvalence.info.dut.m2107.inventory.Inventory;
import fr.iutvalence.info.dut.m2107.inventory.InventorySlot;
import fr.iutvalence.info.dut.m2107.inventory.Item;
import fr.iutvalence.info.dut.m2107.inventory.ItemDatabase;
import fr.iutvalence.info.dut.m2107.inventory.Staff;
import fr.iutvalence.info.dut.m2107.inventory.Sword;
import fr.iutvalence.info.dut.m2107.inventory.Weapon;
import fr.iutvalence.info.dut.m2107.render.DisplayManager;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Input;
import fr.iutvalence.info.dut.m2107.storage.Layer;
import fr.iutvalence.info.dut.m2107.toolbox.Maths;

public class Player extends Character{
	
	/**
	 * The inventory of the player
	 */
	private Inventory inventory = new Inventory();

	/**
	 * The quick bar of the player
	 */
	private InventorySlot[] quickBar = new InventorySlot[8];
	
	// Temporary
	private float width = 0.1f;
	private float height = 0.1f;
	private float posY = -.85f;
	private short selectSlot = 0;
	private GUIElement selectQuickBar;

	private GUIElement quickBarGUI;
	private boolean guiOn;
	
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

	/**
	 * The player's state for the right wall jump (true is currently wallJumping)
	 */
	protected boolean rightWallJump = false;
	
	/**
	 * The player's state for the left wall jump (true is currently wallJumping)
	 */
	protected boolean leftWallJump = false;
	
	protected boolean wallSlide = false;
	
	/**
	 * The index of the player's atlas
	 */
	private float atlasCount = 0;
	
	/**
	 * The amount to add each frame according the animation state
	 */
	private float atlasAdd = 0.25f;
	
	/**
	 * A bunch of position to set the weapon's pivot according to the animation
	 */
	private Vector2f[] pivotPos = {new Vector2f(0.725f, -.15f),
									new Vector2f(0.725f, -.35f),
									new Vector2f(1, -.25f),
									new Vector2f(.85f, .8f),
									new Vector2f(1.3f, -.25f),
									new Vector2f(.4f, -.25f)};

	/**
	 * Constructor of a player
	 */
	public Player() {
		super(new Vector2f(7.5f, 0), SpriteDatabase.getPlayerSpr() ,new Collider(-.75f, -1.75f, .75f, 1.75f), MovableEntity.DEF_SPD, LivingEntity.DEF_HEALTH, LivingEntity.DEF_JUMP_HEIGHT);
	}
	
	public void init() {
		this.quickBarGUI = new GUIElement(SpriteDatabase.getQuickBarStr(), new Vector2f(0, posY), width*this.quickBar.length, height);
		GameWorld.guiLayerMap.getLayer(1).add(this.quickBarGUI);
		
		this.initLayer();
		this.layer.add(this.pivot);
		this.initPivot();
		

		this.inventory.init();
	}
	
	/**
	 * Initialize the inventory
	 */
	public void initInventory() {
		// TODO Take the item saved from a file save
		addItem(ItemDatabase.get(3));
		guiOn = true;
	}
	
	/**
	 * Initialize the quick bar
	 */
	public void initQuickBar() {
		for (int slotNumber = 0; slotNumber < this.quickBar.length; slotNumber++)
			this.quickBar[slotNumber] = new InventorySlot();
		
		selectQuickBar = new GUIElement(SpriteDatabase.getSelectQuickBarSlotStr(), new Vector2f(-width*this.quickBar.length/2 + width*.5f, 0), width, height);
		selectQuickBar.setParent(quickBarGUI);
	}
	
	private void initQuickBarSlot(Item item, byte slotNumber) {
		this.quickBar[slotNumber].setItem(item);
		GUISlot s = new GUISlot(new Vector2f(-width*this.quickBar.length/2 + width*(slotNumber+.5f), 0), width, height, quickBar[slotNumber]);
		s.setItem(new GUIMovable(new GUISprite(this.quickBar[slotNumber].getItem().getSprite().getAtlas(), this.quickBar[slotNumber].getItem().getSprite().getSize()), new Vector2f(0, 0), width, height));
		this.quickBar[slotNumber].setItemSprite(s);
		this.quickBar[slotNumber].setQuantity(new GUIText("" + this.quickBar[slotNumber].getItem().getStack() , .8f, -width, -width/4, width, true));
		
		this.quickBar[slotNumber].getGUISprite().setRotation(-45);
		float scaleMult = this.quickBar[slotNumber].getGUISprite().getSprite().getSize().x*this.quickBar[slotNumber].getGUISprite().getSprite().getSize().y;
		this.quickBar[slotNumber].getGUISprite().setScale((Vector2f)this.quickBar[slotNumber].getGUISprite().getScale().scale(1/ (scaleMult != 1 ? scaleMult : 1.5f)));
		
		this.quickBar[slotNumber].prepareDisplay();
		
		this.quickBar[slotNumber].getItemSprite().setParent(this.quickBarGUI);
	}
	
	public void unloadGUIElements() {
		GameWorld.guiLayerMap.getLayer(1).remove(quickBarGUI);
		GameWorld.guiLayerMap.getLayer(1).remove(inventory.getInventoryGUI());
		guiOn = false;
	}
	
	public boolean isGUIOn() {
		return guiOn;
	}

	/* (non-Javadoc)
	 * @see fr.iutvalence.info.dut.m2107.entities.Character#update(fr.iutvalence.info.dut.m2107.storage.Layer)
	 */
	@Override
	public void update(Layer layer) {
		input();
		
		updateSpriteAnimation();
		
		updateShootVal();
		
		updateQuickBar();
		
		updateInvulnerability();
		
		useItem();
		
		super.update(layer);
	}

	/**
	 * Update the usage of the player's hand item
	 */
	private void useItem() {
		if(Input.isMouseLeftDown() && this.itemOnHand != null && GameWorld.camera.getTarget() == this && !Input.isDragingGUI && !Input.isOverGUI)
			((Weapon)this.itemOnHand.getClass().cast(this.itemOnHand)).use(this);
	}
	
	/**
	 * Update the player's invulnerability
	 */
	private void updateInvulnerability() {
		if(this.invulnerabilityTime > Sys.getTime()/1000f) {
			this.alpha += this.invulnerabilityFadeStep;
			if(this.itemOnHand != null) this.itemOnHand.alpha += this.invulnerabilityFadeStep;
			if(this.alpha >= 1) this.invulnerabilityFadeStep = -.1f;
			else if(this.alpha <= 0.5f) this.invulnerabilityFadeStep = .1f;
		} else {
			this.alpha = 1;
			if(this.itemOnHand != null) this.itemOnHand.alpha = 1;
		}
	}
	
	/**
	 * Store the state of the eye animation (true is blinking)
	 */
	private boolean eyeAnimation = false;
	
	/**
	 * Update the animation of the player
	 */
	private void updateSpriteAnimation() {
		if(this.isGrounded) {
			if(atlasCount >= 65 && atlasCount < 76) {
				atlasCount += DisplayManager.deltaTime()*100/2;
				if(atlasCount < 70) atlasCount = 71;
				else if(atlasCount + atlasAdd >= 77) atlasCount = 0;
				
				if(this.scale.x > 0)
					this.pivot.pos.x = Maths.lerp(this.pivot.pos.x, pivotPos[0].x, .15f);
				else this.pivot.pos.x = Maths.lerp(this.pivot.pos.x, -pivotPos[0].x, .15f);
				
				this.pivot.pos.y = Maths.lerp(this.pivot.pos.y, pivotPos[0].y, .25f);
			} else if(atlasCount >= 80 && atlasCount <= 85) {
				if(atlasCount - atlasAdd <= 80) atlasCount = 71;
				else atlasCount += -DisplayManager.deltaTime()*100/2;
				
				if(this.scale.x > 0)
					this.pivot.pos.x = Maths.lerp(this.pivot.pos.x, pivotPos[0].x, .15f);
				else this.pivot.pos.x = Maths.lerp(this.pivot.pos.x, -pivotPos[0].x, .15f);
				
				this.pivot.pos.y = Maths.lerp(this.pivot.pos.y, pivotPos[0].y, .15f);
			} else {
				if(!Input.isMoveLeft() && !Input.isMoveRight()) {
					if(atlasAdd > 0)
						this.pivot.pos.y = Maths.lerp(this.pivot.pos.y, pivotPos[1].y, .015f);
					else this.pivot.pos.y = Maths.lerp(this.pivot.pos.y, pivotPos[0].y, .015f);
					
					if(this.scale.x > 0)
						this.pivot.pos.x = Maths.lerp(this.pivot.pos.x, pivotPos[0].x, .25f);
					else this.pivot.pos.x = Maths.lerp(this.pivot.pos.x, -pivotPos[0].x, .25f);
					
					if(atlasCount + atlasAdd <= 0) {
						atlasCount = 0;
						atlasAdd = DisplayManager.deltaTime()*100/4/2;
					} else if(atlasCount + atlasAdd >= 13 && atlasCount < 16 && !eyeAnimation) {
						atlasAdd = -DisplayManager.deltaTime()*100/4/2;
					} else if((int)(atlasCount + atlasAdd) == 9 && atlasAdd > 0 && new Random().nextBoolean()) {
						atlasCount = 13;
						eyeAnimation = true;
					} else if(atlasCount < 16 && atlasCount + atlasAdd >= 16) {
						atlasCount = 13;
						atlasAdd = -DisplayManager.deltaTime()*100/4/2;
						eyeAnimation = false;
					} else if(atlasCount >= 16) {
						atlasCount = 0;
						atlasAdd = DisplayManager.deltaTime()*100/4/2;
					}
				} else {
					if((atlasCount >= 25 && atlasCount <= 30) || (atlasCount >= 45 && atlasCount <= 51))
						if(this.scale.x < 0)
							this.pivot.pos.x = Maths.lerp(this.pivot.pos.x, -pivotPos[4].x, .1f);
						else this.pivot.pos.x = Maths.lerp(this.pivot.pos.x, pivotPos[4].x, .1f);
					else
						if(this.scale.x < 0)
							this.pivot.pos.x = Maths.lerp(this.pivot.pos.x, -pivotPos[5].x, .1f);
						else this.pivot.pos.x = Maths.lerp(this.pivot.pos.x, pivotPos[5].x, .1f);
					
					if(atlasCount + atlasAdd >= 51) atlasCount = 27;
					else if(atlasCount < 16 || atlasCount > 51) {
						atlasAdd = DisplayManager.deltaTime()*100/2;
						atlasCount = 16 - atlasAdd;
					}
				}
				atlasCount += atlasAdd;
			}
		} else {			
			if(wallSlide) {
				if(this.scale.x < 0)
					this.pivot.pos.x = -pivotPos[3].x;
				else this.pivot.pos.x = pivotPos[3].x;
				
				this.pivot.pos.y = Maths.lerp(this.pivot.pos.y, pivotPos[3].y, .5f);
				if(atlasCount >= 80 && atlasCount <= 85) {
					atlasCount += 1;
					if(atlasCount > 85) atlasCount = 85;
				} else atlasCount = 80;
			} else {
				this.pivot.pos.y = Maths.lerp(this.pivot.pos.y, pivotPos[2].y, .5f);
				
				if(this.pivot.pos.x > 0)
					this.pivot.pos.x = Maths.lerp(this.pivot.pos.x, pivotPos[2].x, .25f);
				else this.pivot.pos.x = Maths.lerp(this.pivot.pos.x, -pivotPos[2].x, .25f);
				
				atlasAdd = DisplayManager.deltaTime()*100/2;
				if(atlasCount >= 65 && atlasCount <= 70) {
					atlasCount += 1;
					if(atlasCount > 70) atlasCount = 70;
				} else if(atlasCount >= 80 && atlasCount <= 85)
					atlasCount = 70;
				else atlasCount = 65;
			}
		}
		if(Maths.fastFloor(atlasCount) != this.getSprite().getAtlasIndex())
			this.getSprite().updateAtlasIndex(Maths.fastFloor(atlasCount));
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
		
		if (Input.isKey1())			this.selectSlot = 0;
		else if (Input.isKey2())	this.selectSlot = 1;
		else if (Input.isKey3()) 	this.selectSlot = 2;
		else if (Input.isKey4()) 	this.selectSlot = 3;
		else if (Input.isKey5())	this.selectSlot = 4;
		else if (Input.isKey6())	this.selectSlot = 5;
		else if (Input.isKey7())	this.selectSlot = 6;
		else if (Input.isKey8())	this.selectSlot = 7;
		
		selectSlot -= Input.WheelScrolling();
		if(selectSlot > 7) selectSlot -= 8;
		if(selectSlot < 0) selectSlot += 8;
		selectQuickBar.setPosition(new Vector2f(-width*3.5f + selectSlot*width, selectQuickBar.getPosition().y));			
		
		if(this.itemOnHand != this.quickBar[selectSlot].getItem()) {
			if(this.quickBar[selectSlot].getItem() != null) {
				if(this.itemOnHand != null)
					this.itemOnHand.setParent(null);
				
				if(!(this.quickBar[selectSlot].getItem() instanceof Weapon)) {
					this.itemOnHand = this.quickBar[selectSlot].getItem().copy();
					this.itemOnHand.setParent(this.pivot);
				} else {
					this.itemOnHand = this.quickBar[selectSlot].getItem();
					if(this.itemOnHand instanceof Bow)	this.itemOnHand.setPosition(new Vector2f(-.3f, 0));
					else if(this.itemOnHand instanceof Sword)this.itemOnHand.setPosition(new Vector2f(.7f, -0.02f));
					else if(this.itemOnHand instanceof Gun)this.itemOnHand.setPosition(new Vector2f(.35f, 0.1f));
					else if(this.itemOnHand instanceof Staff)this.itemOnHand.setPosition(new Vector2f(.25f, 0));
					this.itemOnHand.setParent(this.pivot);
				}
			} else {
				this.itemOnHand.setParent(null);
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
		if(isGrounded) wallSlide = false;
		
		if(Input.isInventory() || this.inventory.getExitButton().clicked()) this.inventory.changeDisplay();
		
		if (GameWorld.camera.isFocusing()) {
			if (Input.isJumping() && this.isGrounded) this.vel.y = this.jumpHeight;
			
			if (Input.isMoveRight()) {
				if(!rightWallJump || !leftWallJump) this.vel.x += this.spd/6;
				else this.vel.x += this.spd;
			}
			if (Input.isMoveLeft()) {
				if(!leftWallJump || !rightWallJump) this.vel.x += -this.spd/6;
				else this.vel.x += -this.spd;
			}
		}
		
		if(!rightWallJump) this.vel.x += -this.spd/2;
		if(!leftWallJump) this.vel.x += this.spd/2;
		
		if(this.vel.y < -70) vel.y = -70;
		if(this.vel.y > 70) vel.y = 70;
		
		if(this.vel.x < -70) this.vel.x = -70;
		if(this.vel.x > 70) this.vel.x = 70;
	}
	
	/**
	 * Add an item with an amount of stack to the player's inventory
	 * @param item The item to add
	 * @param stack The amount of item to add
	 * @return True if added
	 */
	public boolean addItem(Item item, short stack) {
		byte addState = addQuickBarItem(item, stack);
		if(addState == 1) return true;
		else if(addState == -1) return false;
		return this.inventory.add(item, stack);
	}
	
	/**
	 * Add an item with the stack stored into this item
	 * @param item The item to add
	 * @return True if added
	 */
	public boolean addItem(Item item) {
		return addItem(item, item.getStack());
	}
	
	/**
	 * Add an item and an amount of stack to the quick bar
	 * @param item The item to add
	 * @param stack The amount of item to add
	 * @return 1 if added, 0 if not found , -1 if found but no more room
	 */
	public byte addQuickBarItem(Item item, short stack) {		
		if(stack <= item.getMAX_STACK()) {
			for (byte i = 0; i < this.quickBar.length; i++) {
				if(quickBar[i].getItem() != null) {
					if(quickBar[i].getItem().getId() == item.getId()) {
						if(quickBar[i].getItem().getStack() + stack <= quickBar[i].getItem().getMAX_STACK()) {
							quickBar[i].getItem().changeStack(stack);
							return 1;
						} return -1;
					}
				} else {
					Item it;
					if((it = ItemDatabase.get(item.getId())) instanceof Weapon) {
						this.initQuickBarSlot(it, i);
						return 1;
					}
				}
			}
		} return 0;
	}
	
	/**
	 * Remove a certain stack of an item into the quick bar
	 * @param index The index of the quick bar
	 * @param stack The number of item to remove
	 */
	public void removeQuickBarItem(int index, short stack) {
		if((this.quickBar[index].getItem().getStack() - stack) <= 0) {
			this.quickBar[index].setItem(null);
			this.quickBar[index].getItemSprite().remove();
			this.quickBar[index].setItemSprite(null);
			this.quickBar[index].getQuantity().remove();
			this.quickBar[index].setQuantity(null);
		}
		this.quickBar[index].getQuantity().updateText("" + this.quickBar[index].getItem().getStack());
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
	public Item getQuickBarItem(int index) {return this.quickBar[index].getItem();}

	/**
	 * Return the length of the quick bar
	 * @return the length of the quick bar
	 */
	public short getQuickBarLength() {return (short) this.quickBar.length;}
	
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
	
	public boolean getWallSlide() {return wallSlide;}
}
