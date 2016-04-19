package fr.iutvalence.info.dut.m2107.listeners;

import fr.iutvalence.info.dut.m2107.events.Listener;
import fr.iutvalence.info.dut.m2107.events.TileDestroyedEvent;
import fr.iutvalence.info.dut.m2107.events.TileTouchGroundEvent;
import fr.iutvalence.info.dut.m2107.events.TileVariantChangedEvent;

public class TileListener implements Listener {
	
	public void getTileDestroyed(TileDestroyedEvent event) {
		System.out.println("Tile of type "+event.getTile().getType().name()+" destroyed at "+event.getTile().x+", "+event.getTile().y);
	}
	
	public void getTileVariantChanged(TileVariantChangedEvent event) {
		System.out.println("Tile changed variant from "+event.getOldVariant().name()+" to "+event.getTile().getVariant().name());
	}
	
	public void getTileTouchGround(TileTouchGroundEvent event) {
		System.out.println("Tile at "+event.getTile().x+", "+event.getTile().y+" touched ground at "+event.getGroundTile().x+", "+event.getGroundTile().y);
	}
	
}
