package com.github.eagl.storage;

public class Vector2i {
	
	public int x, y;
	
	public Vector2i() {
		this.x = 0;
		this.y = 0;
	}

	public Vector2i(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean equals(Object obj) {
		Vector2i vec = (Vector2i) obj;
		if (vec.x == x && vec.y == y)
			return true;
		return false;
	}
	
}
