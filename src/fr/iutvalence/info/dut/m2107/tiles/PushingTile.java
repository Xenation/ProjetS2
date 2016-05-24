package fr.iutvalence.info.dut.m2107.tiles;

import fr.iutvalence.info.dut.m2107.events.EventManager;
import fr.iutvalence.info.dut.m2107.events.Listener;
import fr.iutvalence.info.dut.m2107.events.TileActivatedEvent;

public class PushingTile extends TimedTile implements Listener {
	
	public static float DEF_INTERVAL = .5f;
	
	private boolean isPushing;
	
	public PushingTile(TileType type, int x, int y) {
		super(type, x, y, DEF_INTERVAL);
		this.isPushing = false;
	}

	public boolean isPushing() {
		return isPushing;
	}

	public void setPushing(boolean isPushing) {
		this.isPushing = isPushing;
		if (isPushing)
			EventManager.sendEvent(new TileActivatedEvent(this));
	}

}
