package fr.iutvalence.info.dut.m2107.tiles;

public enum TileOrientation {
	UP,
	RIGHT,
	DOWN,
	LEFT;
	
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
	
	public TileOrientation getOpposite() {
		switch (this) {
		case DOWN:
			return UP;
		case LEFT:
			return RIGHT;
		case RIGHT:
			return LEFT;
		case UP:
			return DOWN;
		default:
			return LEFT;
		}
	}
	
}
