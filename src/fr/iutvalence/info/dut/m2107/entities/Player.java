package fr.iutvalence.info.dut.m2107.entities;

import java.util.Random;

import org.lwjgl.Sys;
import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.fontMeshCreator.GUIText;
import fr.iutvalence.info.dut.m2107.gui.GUIElement;
import fr.iutvalence.info.dut.m2107.gui.GUISprite;
import fr.iutvalence.info.dut.m2107.inventory.Bow;
import fr.iutvalence.info.dut.m2107.inventory.Inventory;
import fr.iutvalence.info.dut.m2107.inventory.InventorySlot;
import fr.iutvalence.info.dut.m2107.inventory.Item;
import fr.iutvalence.info.dut.m2107.inventory.ItemDatabase;
import fr.iutvalence.info.dut.m2107.inventory.Sword;
import fr.iutvalence.info.dut.m2107.inventory.Weapon;
import fr.iutvalence.info.dut.m2107.render.DisplayManager;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Input;
import fr.iutvalence.info.dut.m2107.storage.Layer;
import fr.iutvalence.info.dut.m2107.toolbox.Maths;

public class Player extends Character{
	
	// Temporary
	private GUIText playerGUI = new GUIText("", .8f, 0.77f, 1, 0.5f, false, true);
	// Temporary
	
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
	private float posY = 2 - height*1.5f;
	private int selectSlot = 0;
	private GUIElement selectQuickBar;
	
	
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
	
	private float atlasCount = 0;
	private float atlasAdd = 0.25f;
	
	private Vector2f[] pivotPos = {new Vector2f(0.725f, -.15f),
									new Vector2f(0.725f, -.35f),
									new Vector2f(.9f, -.25f),
									new Vector2f(.85f, .8f),
									new Vector2f(0.725f, -.25f)};

	/**
	 * Constructor of a player
	 */
	public Player() {
		super(new Vector2f(), SpriteDatabase.getPlayerSpr() , new Collider(-.75f, -1.75f, .75f, 1.75f));
		this.initQuickBar();
		this.initInventory();
		this.hpGUI = new GUIElement(SpriteDatabase.getHeartStr(), new Vector2f(-1 + width, 1 - height), width/2, height/2);
		
		this.initLayer();
		this.layer.add(this.pivot);
		this.initPivot();
	}
	
	/**
	 * Initialize the inventory
	 */
	private void initInventory() {
		// TODO Take the item saved from a file save
		addItem(ItemDatabase.get(0), 10);
		addItem(ItemDatabase.get(1), 5);
		addItem(ItemDatabase.get(4), 1);
		addItem(ItemDatabase.get(5), 1);
		addItem(ItemDatabase.get(6), 1);
		addItem(ItemDatabase.get(7), 1);
		addItem(ItemDatabase.get(8), 1);
		
		//this.inventory.initInventory();
	}
	
	/**
	 * Initialize the quick bar
	 */
	private void initQuickBar() {
		for (int slotNumber = 0; slotNumber < this.quickBar.length; slotNumber++)
			this.quickBar[slotNumber] = new InventorySlot();
		
		this.quickBar[0].setItem(ItemDatabase.get(2));
		this.quickBar[1].setItem(ItemDatabase.get(3));
		
		for (int slotNumber = 0; slotNumber < this.quickBar.length; slotNumber++) {
			this.quickBar[slotNumber].setBackground(new GUIElement(SpriteDatabase.getQuickBarSlotStr(), new Vector2f(-width*3.5f + width*slotNumber, 1-posY), width, height));
			if(this.quickBar[slotNumber].getItem() != null) {
				this.quickBar[slotNumber].setItemSprite(new GUIElement(new GUISprite(this.quickBar[slotNumber].getItem().getSprite().getAtlas(), this.quickBar[slotNumber].getItem().getSprite().getSize()), new Vector2f(), width, height));
				this.quickBar[slotNumber].setQuantity(new GUIText("" + this.quickBar[slotNumber].getItem().getStack() , .8f, -width, -width/4, width, true));
			}
			this.quickBar[slotNumber].display();
		}
		GameWorld.guiLayerMap.getLayer(0).add(selectQuickBar = new GUIElement(SpriteDatabase.getSelectQuickBarSlotStr(), new Vector2f(-width*3.5f + selectSlot*width, 1-posY), width, height));
	}

	/* (non-Javadoc)
	 * @see fr.iutvalence.info.dut.m2107.entities.Character#update(fr.iutvalence.info.dut.m2107.storage.Layer)
	 */
	@Override
	public void update(Layer layer) {		
		input();
		
		updateSpriteAnimation();
		
		System.out.println(this.inventory);
		
		updateShootVal();
		
		updateQuickBar();
		
		updateInvulnerability();
		
		updateScale();
		
		useItem();
		
		playerGUI.updateText("IsGrounded : " + this.isGrounded);
		super.update(layer);
	}

	private void useItem() {
		if(Input.isMouseLeft() && this.itemOnHand != null && GameWorld.camera.isFree()) {
			if(this.itemOnHand instanceof Bow)
				((Bow) this.itemOnHand).use(this);
			if(this.itemOnHand instanceof Sword)
				((Sword) this.itemOnHand).use(this);
		}			
	}
	
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
	
	private void updateScale() {
		if(this.vel.x > 0) this.scale.setX(1);
		else if(this.vel.x < 0) this.scale.setX(-1);
		
		if(GameWorld.player.getDegreeShoot() < 90 && GameWorld.player.getDegreeShoot() > -90)
			this.pivot.pos.x = Maths.fastAbs(this.pivot.pos.x);
		else
			this.pivot.pos.x = -Maths.fastAbs(this.pivot.pos.x);
	}
	
	private boolean eyeAnimation = false;
	
	private void updateSpriteAnimation() {
		if(this.isGrounded) {
			if(atlasCount >= 65 && atlasCount < 76) {
				atlasCount += DisplayManager.deltaTime()*100/2;
				if(atlasCount < 70) atlasCount = 71;
				else if(atlasCount + atlasAdd >= 77) atlasCount = 0;
				this.pivot.pos.x = pivotPos[0].x;
				this.pivot.pos.y = pivotPos[0].y;
			} else if(atlasCount >= 80 && atlasCount <= 85) {
				if(atlasCount - atlasAdd <= 80) atlasCount = 71;
				else atlasCount += -DisplayManager.deltaTime()*100/2;
				this.pivot.pos.x = pivotPos[0].x;
				this.pivot.pos.y = pivotPos[0].y;
			} else {
				if(!Input.isMoveLeft() && !Input.isMoveRight()) {
					if(atlasAdd > 0)
						this.pivot.pos.y = Maths.lerp(this.pivot.pos.y, pivotPos[1].y, .015f);
					else
						this.pivot.pos.y = Maths.lerp(this.pivot.pos.y, pivotPos[0].y, .015f);
					
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
					if(atlasCount >= 25 && atlasCount <= 34)
						this.pivot.pos.x = pivotPos[2].x;
					else
						this.pivot.pos.x = -pivotPos[2].x;
					
					if(this.pivot.pos.x > 0)
						this.pivot.pos.x = Maths.lerp(this.pivot.pos.x, pivotPos[2].x, .5f);
					else
						this.pivot.pos.x = Maths.lerp(this.pivot.pos.x, -pivotPos[2].x, .5f);
					
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
				this.pivot.pos.x = pivotPos[3].x;
				
				if(this.scale.x < 0)
					if(GameWorld.player.getDegreeShoot() < 90 && GameWorld.player.getDegreeShoot() > -90)
						this.pivot.pos.y = Maths.lerp(this.pivot.pos.y, pivotPos[2].y, 0.25f);
					else
						this.pivot.pos.y = Maths.lerp(this.pivot.pos.y, pivotPos[3].y, 0.25f);
				else
					if(GameWorld.player.getDegreeShoot() < 90 && GameWorld.player.getDegreeShoot() > -90)
						this.pivot.pos.y = Maths.lerp(this.pivot.pos.y, pivotPos[3].y, 0.25f);
					else
						this.pivot.pos.y = Maths.lerp(this.pivot.pos.y, pivotPos[2].y, 0.25f);
				
				if(atlasCount >= 80 && atlasCount <= 85) {
					atlasCount += 1;
					if(atlasCount > 85) atlasCount = 85;
				} else atlasCount = 80;
			} else {
				/*this.pivot.pos.y = Maths.lerp(this.pivot.pos.y, pivotPos[2].y, .5f);
				
				if(this.pivot.pos.x > 0)
					this.pivot.pos.x = Maths.lerp(this.pivot.pos.x, pivotPos[2].x, .5f);
				else
					this.pivot.pos.x = Maths.lerp(this.pivot.pos.x, -pivotPos[2].x, .5f);
				*/
				atlasAdd = DisplayManager.deltaTime()*100/2;
				if(atlasCount >= 65 && atlasCount <= 70) {
					atlasCount += 1;
					if(atlasCount > 70) atlasCount = 70;
				} else if(atlasCount >= 80 && atlasCount <= 85)
					atlasCount = 70;
				else atlasCount = 65;
			}
		}
		this.spr.updateAtlasIndex(Maths.fastFloor(atlasCount));
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
		
		if(this.itemOnHand != this.quickBar[selectSlot].getItem()) {
			if(this.quickBar[selectSlot].getItem() != null) {
				if(this.itemOnHand != null)
					this.pivot.getLayer().remove(this.itemOnHand);
				
				if(!(this.quickBar[selectSlot].getItem() instanceof Weapon)) {
					this.itemOnHand = new Item(this.quickBar[selectSlot].getItem());
					this.pivot.getLayer().add(this.itemOnHand);
				} else {
					this.itemOnHand = this.quickBar[selectSlot].getItem();
					if(this.itemOnHand instanceof Bow)	this.itemOnHand.setPosition(new Vector2f(-.3f, 0));
					if(this.itemOnHand instanceof Sword)this.itemOnHand.setPosition(new Vector2f(.7f, -0.02f));
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
		if(isGrounded) wallSlide = false;
		
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
		
		if(!rightWallJump) this.vel.x += -this.spd/2;
		if(!leftWallJump) this.vel.x += this.spd/2;
		
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
		return addItem(item, item.getStack());
	}
	
	public byte addQuickBarItem(Item item, int stack) {
		if(stack <= item.getMAX_STACK()) {
			for (InventorySlot slot : quickBar) {
				if(slot.getItem() != null) {
					if(slot.getItem().getId() == item.getId()) {
						if(slot.getItem().getStack() + stack <= slot.getItem().getMAX_STACK()) {
							slot.getItem().changeStack(stack);
							return 1;
						} return -1;
					}
				}
			}
		} return 0;
	}
	
	public boolean removeQuickBarItem(int index, int stack) {
		if((this.quickBar[index].getItem().getStack() - stack) <= 0) {
			this.quickBar[index].setItem(null);
			this.quickBar[index].getItemSprite().remove();
			this.quickBar[index].setItemSprite(null);
			this.quickBar[index].getQuantity().remove();
			this.quickBar[index].setQuantity(null);
			return false;
		}
		this.quickBar[index].getQuantity().updateText("" + this.quickBar[index].getItem().getStack());
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
	public Item getQuickBarItem(int index) {return this.quickBar[index].getItem();}

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
