package fr.iutvalence.info.dut.m2107.storage;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Input {
	
	/** Boolean for player's jumping input */
	private static boolean isJumping;
	
	/** Boolean for player's use weapon input */
	private static boolean useWeapon;
	
	/** Boolean for move up input */
	private static boolean isMoveUp;
	
	/** Boolean for move down input */
	private static boolean isMoveDown;
	
	/** Boolean for move left input */
	private static boolean isMoveLeft;
	
	/** Boolean for move right input */
	private static boolean isMoveRight;
	
	/** Boolean for player's camera focus input */
	private static boolean isFocusOnPlayer;
	
	/** Boolean for VSync enable/disable input */
	private static boolean isVSync;
	
	/** Boolean for save world input */
	private static boolean isWriteWorld;
	
	/** Boolean for load world input */
	private static boolean isLoadWorld;
	
	/** Boolean for input 0 */
	private static boolean isKey0;
	
	/** Boolean for input 1 */
	private static boolean isKey1;
	
	/** Boolean for input 2 */
	private static boolean isKey2;
	
	/** Boolean for input 3 */
	private static boolean isKey3;
	
	/** Boolean for input 4 */
	private static boolean isKey4;
	
	/** Boolean for input 5 */
	private static boolean isKey5;
	
	/** Boolean for input 6 */
	private static boolean isKey6;
	
	/** Boolean for input 7 */
	private static boolean isKey7;
	
	/** Boolean for input 8 */
	private static boolean isKey8;
	
	/** Boolean for input 9 */
	private static boolean isKey9;
	
	/** Boolean for input water */
	private static boolean isKeyWater;
	
	/** Boolean for input numpad 0*/
	private static boolean isNumPad0;
	
	/** Boolean for input tile rotation */
	private static boolean isTileRotate;
	
	/** Boolean for block selection input */
	private static boolean isLShift;
	
	/** Boolean for mouse left click */
	private static boolean isMouseLeft;
	
	/** Boolean for mouse right click */
	private static boolean isMouseRight;
	
	/** Boolean for input escape */
	private static boolean isEscape;
	
	/** Boolean for paused input */
	private static boolean isPaused;
	
	/** Integer for wheel scroll */
	private static int wheelScrolling;
	
	/**
	 * Update the keyboard and mouse input
	 */
	public static void input() {
		
		isJumping = false;
		useWeapon = false;
		isFocusOnPlayer = false;
		isVSync = false;
		isWriteWorld = false;
		isLoadWorld = false;
		isKey0 = false;
		isKey1 = false;
		isKey2 = false;
		isKey3 = false;
		isKey4 = false;
		isKey5 = false;
		isKey6 = false;
		isKey7 = false;
		isKey8 = false;
		isKey9 = false;
		isKeyWater = false;
		isNumPad0 = false;
		isTileRotate = false;
		
		// Check button stay down
		if(Keyboard.isKeyDown(Keyboard.KEY_Z)) isMoveUp = true;
		else isMoveUp = false;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_S)) isMoveDown = true;
		else isMoveDown = false;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_Q)) isMoveLeft = true;
		else isMoveLeft = false;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) isMoveRight = true;
		else isMoveRight = false;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))	isLShift = true;
        else isLShift = false;
        
		if(Mouse.isButtonDown(0)) isMouseLeft = true;
		else isMouseLeft = false;
        
		if(Mouse.isButtonDown(1)) isMouseRight = true;
		else isMouseRight = false;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) isEscape = true;
		else isEscape = false;
		
		// Check button down or up
		while(Keyboard.next()){
			if (Keyboard.getEventKeyState()) {
		        if(Keyboard.getEventKey() == Keyboard.KEY_SPACE)	isJumping = true;
		        if(Keyboard.getEventKey() == Keyboard.KEY_A)    	useWeapon = true;
		        if(Keyboard.getEventKey() == Keyboard.KEY_TAB)     	isFocusOnPlayer = true;
		        if(Keyboard.getEventKey() == Keyboard.KEY_V)       	isVSync = true;
		        if(Keyboard.getEventKey() == Keyboard.KEY_DIVIDE)  	isWriteWorld = true;
		        if(Keyboard.getEventKey() == Keyboard.KEY_MULTIPLY)	isLoadWorld = true;
		        if(Keyboard.getEventKey() == Keyboard.KEY_0)		isKey0 = true;
		        if(Keyboard.getEventKey() == Keyboard.KEY_1)		isKey1 = true;
		        if(Keyboard.getEventKey() == Keyboard.KEY_2)		isKey2 = true;
		        if(Keyboard.getEventKey() == Keyboard.KEY_3)		isKey3 = true;
		        if(Keyboard.getEventKey() == Keyboard.KEY_4)		isKey4 = true;
		        if(Keyboard.getEventKey() == Keyboard.KEY_5)		isKey5 = true;
		        if(Keyboard.getEventKey() == Keyboard.KEY_6)		isKey6 = true;
		        if(Keyboard.getEventKey() == Keyboard.KEY_7)		isKey7 = true;
		        if(Keyboard.getEventKey() == Keyboard.KEY_8)		isKey8 = true;
		        if(Keyboard.getEventKey() == Keyboard.KEY_9)		isKey9 = true;
		        if(Keyboard.getEventKey() == Keyboard.KEY_W)		isKeyWater = true;
		        if(Keyboard.getEventKey() == Keyboard.KEY_NUMPAD0)	isNumPad0 = true;
		        if(Keyboard.getEventKey() == Keyboard.KEY_R)		isTileRotate = true;
		        if(Keyboard.getEventKey() == Keyboard.KEY_P)		isPaused = !isPaused;
		    } else {
		    	// Do ... when released
		    }
		}
		
		wheelScrolling = Mouse.getDWheel()/120;
	}

	/** @return player's jumping input */
	public static boolean isJumping() {return isJumping;}
	
	/** @return player's use weapon input */
	public static boolean isUseWeapon() {return useWeapon;}

	/** @return move up input */
	public static boolean isMoveUp() {return isMoveUp;}

	/** @return move down input */
	public static boolean isMoveDown() {return isMoveDown;}

	/** @return move left input */
	public static boolean isMoveLeft() {return isMoveLeft;}

	/** @return move right input */
	public static boolean isMoveRight() {return isMoveRight;}

	/** @return player camera focus input */
	public static boolean isFocusOnPlayer() {return isFocusOnPlayer;}
	
	/** @return VSync input */
	public static boolean isVSync() {return isVSync;}
	
	/** @return write world input */
	public static boolean isWriteWorld() {return isWriteWorld;}
	
	/** @return load world input */
	public static boolean isLoadWorld() {return isLoadWorld;}
	
	/** @return input 0 */
	public static boolean isKey0() {return isKey0;}
	
	/** @return input 1 */
	public static boolean isKey1() {return isKey1;}
	
	/** @return input 2 */
	public static boolean isKey2() {return isKey2;}
	
	/** @return input 3 */
	public static boolean isKey3() {return isKey3;}
	
	/** @return input 4 */
	public static boolean isKey4() {return isKey4;}
	
	/** @return input 5 */
	public static boolean isKey5() {return isKey5;}
	
	/** @return input 6 */
	public static boolean isKey6() {return isKey6;}
	
	/** @return input 7 */
	public static boolean isKey7() {return isKey7;}
	
	/** @return input 8 */
	public static boolean isKey8() {return isKey8;}

	/** @return input 9 */
	public static boolean isKey9() {return isKey9;}
	
	/** @return input water */
	public static boolean isKeyWater() {return isKeyWater;}
	
	/** @return input numpad 0 */
	public static boolean isNumPad0() {return isNumPad0;}
	
	/** @return tile rotate input */
	public static boolean isTileRotate() {return isTileRotate;}
	
	/** @return block selection input */
	public static boolean isLShift() {return isLShift;}
	
	/** @return left mouse click */
	public static boolean isMouseLeft() {return isMouseLeft;}
	
	/** @return right mouse click */
	public static boolean isMouseRight() {return isMouseRight;}

	/** @return escape input */
	public static boolean isEscape() {return isEscape;}

	/** @return paused input */
	public static boolean isPaused() {return isPaused;}
	
	/** @return wheel scroll value */
	public static int WheelScrolling() {return wheelScrolling;}
}