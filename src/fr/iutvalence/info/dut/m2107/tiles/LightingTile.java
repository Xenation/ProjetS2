package fr.iutvalence.info.dut.m2107.tiles;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import fr.iutvalence.info.dut.m2107.entities.Entity;
import fr.iutvalence.info.dut.m2107.storage.Chunk;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Layer;
import fr.iutvalence.info.dut.m2107.toolbox.Maths;

public class LightingTile extends Tile {
	
	public static final Vector3f DEF_COLOR = new Vector3f(1, .75f, .75f);
	public static final float DEF_INTENSITY = .75f;
	public static final float DEF_RANGE = 10;
	
	private Vector3f color;
	private float intensity;
	private float range;
	
	public LightingTile(TileType type, int x, int y, Vector3f color, float intensity, float range) {
		super(type, x, y);
		this.color = color;
		this.intensity = intensity;
		this.range = range;
	}
	
	public LightingTile(TileType type, int x, int y) {
		super(type, x, y);
		this.color = new Vector3f();
		this.color.set(DEF_COLOR);
		this.intensity = DEF_INTENSITY;
		this.range = DEF_RANGE;
	}
	
	public void softUpdate() {
		for (Chunk chk : GameWorld.chunkMap.getSurroundingChunks(-range, range, range, -range, new Vector2f(x, y))) {
			for (Tile tile : chk) {
				float flat = 0.25f*range;
				float distance = Maths.distance(tile.x, tile.y, x, y);
				float distanceFade = 1;
				if (distance > flat)
					distanceFade = ((range-distance)/(range-flat));
				if (distanceFade < 0) distanceFade = 0;
				tile.light.x += color.x * intensity * distanceFade;
				tile.light.y += color.y * intensity * distanceFade;
				tile.light.z += color.z * intensity * distanceFade;
			}
		}
		for (Chunk chk : GameWorld.backChunkMap.getSurroundingChunks(-range, range, range, -range, new Vector2f(x, y))) {
			for (Tile tile : chk) {
				float flat = 0.25f*range;
				float distance = Maths.distance(tile.x, tile.y, x, y);
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
				float distance = Maths.distance(x, y, entity.getAbsolutePosition());
				float distanceFade = 1;
				if (distance > flat)
					distanceFade = ((range-distance)/(range-flat));
				if (distanceFade < .1f) continue;
				if (distanceFade < 0) distanceFade = 0;
				entity.getLight().x += color.x * intensity * distanceFade;
				entity.getLight().y += color.y * intensity * distanceFade;
				entity.getLight().z += color.z * intensity * distanceFade;
			}
		}
	}

	public Vector3f getColor() {
		return color;
	}

	public void setColor(Vector3f color) {
		this.color = color;
	}

	public float getIntensity() {
		return intensity;
	}

	public void setIntensity(float intensity) {
		this.intensity = intensity;
	}

	public float getRange() {
		return range;
	}

	public void setRange(float range) {
		this.range = range;
	}
	
}
