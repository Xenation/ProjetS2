package fr.iutvalence.info.dut.m2107.guiRendering;

import org.lwjgl.util.vector.Vector2f;

public class GUIElement {
	
	protected int vaoID;
	protected int textureID;
	protected int vertexCount;
	
	private Vector2f position;
	public final float width;
	public final float height;
	
	public GUIElement(String textureName, Vector2f pos, float width, float height) {
		this.position = pos;
		this.width = width;
		this.height = height;
		GUIMaster.loadElement(this, textureName);
	}
	
	public GUIElement(int textureID, Vector2f pos, float width, float height) {
		this.position = pos;
		this.width = width;
		this.height = height;
		GUIMaster.loadElement(this, textureID);
	}

	public int getVaoID() {
		return vaoID;
	}

	public void setVaoID(int vaoID) {
		this.vaoID = vaoID;
	}

	public int getTextureID() {
		return textureID;
	}

	public void setTextureID(int textureID) {
		this.textureID = textureID;
	}

	public int getVertexCount() {
		return vertexCount;
	}

	public void setVertexCount(int vertexCount) {
		this.vertexCount = vertexCount;
	}

	public Vector2f getPosition() {
		return position;
	}
	
	public Vector2f getRealPosition() {
		return new Vector2f(position.x, position.y);
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}
	
}
