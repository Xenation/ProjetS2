package fr.iutvalence.info.dut.m2107.storage;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Input {
	
	private static boolean isJumping;
	private static boolean useWeapon;
	
	private static boolean isMoveUp;
	private static boolean isMoveDown;
	private static boolean isMoveLeft;
	private static boolean isMoveRight;
	
	private static boolean isFocusOnPlayer;
	private static boolean isVSync;
	private static boolean isWriteWorld;
	private static boolean isLoadWorld;
	
	private static boolean isKey0;
	private static boolean isKey1;
	private static boolean isKey2;
	private static boolean isKey3;
	private static boolean isKey4;
	private static boolean isKey5;
	private static boolean isKey6;
	private static boolean isKey7;
	private static boolean isKey8;
	private static boolean isKey9;
	private static boolean isKeyWater;
	
	private static boolean isNumPad0;
	
	private static boolean isTileRotate;
	
	private static boolean isLShift;
	
	private static boolean isMouseLeft;
	private static boolean isMouseRight;
	
	private static boolean isEscape;
	
	private static boolean isPaused;
	
	private static int wheelScrolling;
	
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

	public static boolean isJumping() {return isJumping;}
	public static boolean isUseWeapon() {return useWeapon;}

	public static boolean isMoveUp() {return isMoveUp;}
	public static boolean isMoveDown() {return isMoveDown;}
	public static boolean isMoveLeft() {return isMoveLeft;}
	public static boolean isMoveRight() {return isMoveRight;}

	public static boolean isFocusOnPlayer() {return isFocusOnPlayer;}
	
	public static boolean isVSync() {return isVSync;}
	
	public static boolean isWriteWorld() {return isWriteWorld;}
	public static boolean isLoadWorld() {return isLoadWorld;}
	
	public static boolean isKey0() {return isKey0;}
	public static boolean isKey1() {return isKey1;}
	public static boolean isKey2() {return isKey2;}
	public static boolean isKey3() {return isKey3;}
	public static boolean isKey4() {return isKey4;}
	public static boolean isKey5() {return isKey5;}
	public static boolean isKey6() {return isKey6;}
	public static boolean isKey7() {return isKey7;}
	public static boolean isKey8() {return isKey8;}
	public static boolean isKey9() {return isKey9;}
	public static boolean isKeyWater() {return isKeyWater;}
	
	public static boolean isNumPad0() {return isNumPad0;}
	
	public static boolean isTileRotate() {return isTileRotate;}
	
	public static boolean isLShift() {return isLShift;}
	
	public static boolean isMouseLeft() {return isMouseLeft;}
	public static boolean isMouseRight() {return isMouseRight;}

	public static boolean isEscape() {return isEscape;}

	public static boolean isPaused() {return isPaused;}
	
	public static int WheelScrolling() {return wheelScrolling;}
}