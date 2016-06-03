package fr.iutvalence.info.dut.m2107.entities;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import fr.iutvalence.info.dut.m2107.storage.Chunk;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Layer;
import fr.iutvalence.info.dut.m2107.tiles.Tile;
import fr.iutvalence.info.dut.m2107.toolbox.Maths;

public class Light extends MovableEntity {
	
	private float intensity;
	
	private float range;
	
	private Vector3f color;
	
	public Light(Vector2f pos, Vector3f color, float intensity, float range) {
		super(pos, SpriteDatabase.getEmptySpr());
		this.intensity = intensity;
		this.range = range;
		this.color = color;
	}
	
	public void update(Layer layer) {
		for (Chunk chk : GameWorld.chunkMap.getSurroundingChunks(-range, range, range, -range, getAbsolutePosition())) {
			for (Tile tile : chk) {
				float flat = 0.25f*range;
				float distance = Maths.distance(new Vector2f(tile.x+.5f, tile.y+.5f), getAbsolutePosition());
				float distanceFade = 1;
				if (distance > flat)
					distanceFade = ((range-distance)/(range-flat));
				if (distanceFade < 0) distanceFade = 0;
				tile.light.x += color.x * intensity * distanceFade;
				tile.light.y += color.y * intensity * distanceFade;
				tile.light.z += color.z * intensity * distanceFade;
			}
		}
		for (Chunk chk : GameWorld.backChunkMap.getSurroundingChunks(-range, range, range, -range, getAbsolutePosition())) {
			for (Tile tile : chk) {
				float flat = 0.25f*range;
				float distance = Maths.distance(new Vector2f(tile.x+.5f, tile.y+.5f), getAbsolutePosition());
				float distanceFade = 1;
				if (distance > flat)
					distanceFade = ((range-distance)/(range-flat));
				if (distanceFade < 0) distanceFade = 0;
				tile.light.x += color.x * intensity * distanceFade * .75f;
				tile.light.y += color.y * intensity * distanceFade * .75f;
				tile.light.z += color.z * intensity * distanceFade * .75f;
			}
		}
		for (Layer lay : GameWorld.layerMap.getLayers()) {
			for (Entity entity : lay) {
				float flat = 0.25f*range;
				float distance = Maths.distance(getAbsolutePosition(), entity.getAbsolutePosition());
				float distanceFade = 1;
				if (distance > flat)
					distanceFade = ((range-distance)/(range-flat));
				if (distanceFade < .1f) continue;
				if (distanceFade < 0) distanceFade = 0;
				entity.light.x += color.x * intensity * distanceFade;
				entity.light.y += color.y * intensity * distanceFade;
				entity.light.z += color.z * intensity * distanceFade;
			}
		}
	}
	
}
