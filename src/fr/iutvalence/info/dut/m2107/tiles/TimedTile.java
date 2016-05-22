package fr.iutvalence.info.dut.m2107.tiles;

import fr.iutvalence.info.dut.m2107.events.EventManager;
import fr.iutvalence.info.dut.m2107.events.TileTimeElapsedEvent;
import fr.iutvalence.info.dut.m2107.render.DisplayManager;

public class TimedTile extends Tile {
	
	protected int time;
	
	public TimedTile(TileType type, int x, int y, float time) {
		super(type, x, y);
		this.time = (int) time*1000;
	}
	
	public float getTime() {
		return time;
	}
	
	public void resetTime(float resetTime) {
		time = (int) resetTime*1000;
	}
	
	public void softUpdate() {
		if (time >= 0) {
			time -= DisplayManager.deltaTime()*1000;
		} else {
			EventManager.sendEvent(new TileTimeElapsedEvent(this));
			this.toUpdate = true;
		}
	}
	
}
