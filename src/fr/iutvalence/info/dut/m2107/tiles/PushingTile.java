package fr.iutvalence.info.dut.m2107.tiles;

import fr.iutvalence.info.dut.m2107.events.EventManager;
import fr.iutvalence.info.dut.m2107.events.Listener;
import fr.iutvalence.info.dut.m2107.events.TileActivatedEvent;

public class PushingTile extends Tile implements Listener {
	
	public static float DEF_INTERVAL = .5f;
	
	private boolean isPushing;
	protected float pushinginterval;
	
	public PushingTile(TileType type, int x, int y) {
		super(type, x, y);
		this.isPushing = false;
		this.pushinginterval = DEF_INTERVAL;
	}

	public boolean isPushing() {
		return isPushing;
	}

	public void setPushing(boolean isPushing) {
		this.isPushing = isPushing;
		if (isPushing)
			EventManager.sendEvent(new TileActivatedEvent(this));
	}
	
	public void getTileActivated(TileActivatedEvent event) {
		System.out.println("TILE ACTIVATED! (on "+this+")");
	}

}
