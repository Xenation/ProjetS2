package fr.iutvalence.info.dut.m2107.tiles;

import fr.iutvalence.info.dut.m2107.events.EventManager;
import fr.iutvalence.info.dut.m2107.events.TileTimeElapsedEvent;
import fr.iutvalence.info.dut.m2107.render.DisplayManager;

public class TimedTile extends Tile {
	
	protected float time;
	
	public TimedTile(TileType type, int x, int y, float time) {
		super(type, x, y);
		this.time = time;
	}
	
	public float getTime() {
		return time;
	}
	
	public void resetTime(float resetTime) {
		time = resetTime;
	}
	
	public void softUpdate() {
		super.softUpdate();
		if (time >= 0) {
			time -= DisplayManager.deltaTime();
		} else {
			EventManager.sendEvent(new TileTimeElapsedEvent(this));
			this.toUpdate = true;
			this.updateBehavior = true;
		}
	}
	
}
