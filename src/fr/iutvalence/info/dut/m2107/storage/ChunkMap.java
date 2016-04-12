package fr.iutvalence.info.dut.m2107.storage;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.lwjgl.util.vector.Vector2f;

import fr.iutvalence.info.dut.m2107.tiles.Tile;
import fr.iutvalence.info.dut.m2107.toolbox.Maths;

public class ChunkMap implements Map<Vector2i, Chunk>, Iterable<Chunk> {
	
	//// CHUNKMAP \\\\
	public void addTile(Tile til) {
		Chunk chk = get(Chunk.toChunkPosition(til.x, til.y));
		if (chk != null) {
			chk.add(til);
		}
	}
	public void addTilenChunk(Tile til) {
		Chunk chk = get(Chunk.toChunkPosition(til.x, til.y));
		if (chk != null) {
			chk.add(til);
		} else {
			Vector2i pos = Chunk.toChunkPosition(til.x, til.y);
			chk = new Chunk(pos);
			put(pos, chk);
			chk.add(til);
		}
	}
	public void removeTileAt(int x, int y) {
		Chunk chk = get(Chunk.toChunkPosition(x, y));
		if (chk != null) {
			chk.removeAt(x, y);
		}
	}
	
	public void generateSurroundingChunks(float left, float right, float top, float bottom, Vector2f center) {
		for (int y = Chunk.toChunkPosition(Maths.fastFloor(bottom + center.y)); y <= Chunk.toChunkPosition(Maths.fastFloor(top + center.y)); y++) {
			for (int x = Chunk.toChunkPosition(Maths.fastFloor(left + center.x)); x <= Chunk.toChunkPosition(Maths.fastFloor(right + center.x)); x++) {
				Vector2i pos = new Vector2i(x, y);
				if (!this.containsKey(pos)) {
					this.put(pos, new Chunk(pos));
				}
			}
		}
	}
	
	public List<Chunk> getSurroundingChunks(float left, float right, float top, float bottom, Vector2f center) {
		List<Chunk> chks = new ArrayList<Chunk>();
		for (int y = Chunk.toChunkPosition(Maths.fastFloor(bottom + center.y)); y <= Chunk.toChunkPosition(Maths.fastFloor(top + center.y)); y++) {
			for (int x = Chunk.toChunkPosition(Maths.fastFloor(left + center.x)); x <= Chunk.toChunkPosition(Maths.fastFloor(right + center.x)); x++) {
				Vector2i pos = new Vector2i(x, y);
				if (this.containsKey(pos)) {
					chks.add(this.get(pos));
				}
			}
		}
		return chks;
	}
	
	public int getTilesCount() {
		int count = 0;
		for (Chunk chk : this) {
			count += chk.getTilesCount();
		}
		return count;
	}
	
	
	//// MAP \\\\
	private List<Link> map = new ArrayList<Link>();
	
	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public boolean containsKey(Object arg0) {
		for (Link lnk : map) {
			if (lnk.position.equals(arg0)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean containsValue(Object arg0) {
		for (Link lnk : map) {
			if (lnk.chunk.equals(arg0)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Set<Map.Entry<Vector2i, Chunk>> entrySet() {
		Set<Map.Entry<Vector2i, Chunk>> set = new HashSet<Map.Entry<Vector2i, Chunk>>();
		for (Entry<Vector2i, Chunk> entry : map) {
			set.add(entry);
		}
		return set;
	}

	@Override
	public Chunk get(Object arg0) {
		for (Link lnk : map) {
			if (lnk.position.equals(arg0)) {
				return lnk.getValue();
			}
		}
		return null;
	}

	@Override
	public boolean isEmpty() {
		if (map.size() == 0)
			return true;
		return false;
	}

	@Override
	public Set<Vector2i> keySet() {
		Set<Vector2i> set = new HashSet<Vector2i>();
		for (Link lnk : map) {
			set.add(lnk.position);
		}
		return set;
	}

	@Override
	public Chunk put(Vector2i pos, Chunk chk) {
		Link lnk = null;
		if (!containsKey(pos)) {
			lnk = new Link(pos, chk);
			map.add(lnk);
			return lnk.chunk;
		}
		return null;
		
	}

	@Override
	public void putAll(Map<? extends Vector2i, ? extends Chunk> m) {
		for (Entry<? extends Vector2i, ? extends Chunk> entry : m.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public Chunk remove(Object arg0) {
		List<Link> toRem = new LinkedList<Link>();
		for (Link lnk : map) {
			if (lnk.position.equals((Vector2i) arg0))
					toRem.add(lnk);
		}
		for (Link entry : toRem) {
			map.remove(entry);
		}
		return null;
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public Collection<Chunk> values() {
		return chunkList();
	}
	
	private List<Chunk> chunkList() {
		List<Chunk> chks = new ArrayList<Chunk>();
		for (Link lnk : map) {
			chks.add(lnk.getValue());
		}
		return chks;
	}
	
	
	//// ITERABLE \\\\
	@Override
	public Iterator<Chunk> iterator() {
		return chunkList().iterator();
	}
	
	
	//// LINK \\\\
	private final class Link implements Map.Entry<Vector2i, Chunk> {
		
		private final Vector2i position;
		private Chunk chunk;
		
		public Link(Vector2i pos, Chunk chk) {
			this.position = pos;
			this.chunk = chk;
		}
		
		@Override
		public Vector2i getKey() {
			return position;
		}

		@Override
		public Chunk getValue() {
			return chunk;
		}

		@Override
		public Chunk setValue(Chunk value) {
			chunk = value;
			return chunk;
		}
		
	}
	
}
