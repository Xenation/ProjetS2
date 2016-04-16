package fr.iutvalence.info.dut.m2107.tiles;

public enum TileOrientation {
	UP,
	RIGHT,
	DOWN,
	LEFT;
	
	public static final float[] UVS_UP = {
			0, 1,
			0, 0,
			1, 1,
			1, 0
	};
	public static final float[] UVS_RIGHT = {
			0, 0,
			1, 0,
			0, 1,
			1, 1
	};
	public static final float[] UVS_DOWN = {
			1, 0,
			1, 1,
			0, 0,
			0, 1
	};
	public static final float[] UVS_LEFT = {
			1, 0,
			0, 0,
			1, 1,
			0, 1
	};
	
	public float[] getTexureUVs() {
		switch (this) {
		case UP:
			return UVS_UP;
		case RIGHT:
			return UVS_RIGHT;
		case DOWN:
			return UVS_DOWN;
		case LEFT:
			return UVS_LEFT;
		default:
			return UVS_RIGHT;
		}
	}
	
	public float getRadians() {
		float deg = 0;
		switch (this) {
		case DOWN:
			deg = 90;
			break;
		case LEFT:
			deg = 180;
			break;
		case RIGHT:
			deg = 0;
			break;
		case UP:
			deg = -90;
			break;
		default:
			break;
		}
		return (float) Math.toRadians(deg);
	}
	
	public TileOrientation getNext() {
		switch (this) {
		case DOWN:
			return LEFT;
		case LEFT:
			return UP;
		case RIGHT:
			return DOWN;
		case UP:
			return RIGHT;
		default:
			return RIGHT;
		}
	}
	
}
