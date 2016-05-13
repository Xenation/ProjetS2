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
	
	public boolean update() {
		time -= DisplayManager.deltaTime();
		if (time < 0)
			EventManager.sendEvent(new TileTimeElapsedEvent(this));
		return type.updateBehaviors(this);
	}
	
}
