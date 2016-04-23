package fr.iutvalence.info.dut.m2107.guiRendering;

public class GUIMeshData {
	
	private float[] positions;
	private float[] textureUVs;
	
	protected GUIMeshData(float[] positions, float[] textureUVs){
		this.positions = positions;
		this.textureUVs = textureUVs;
	}

	public float[] getPositions() {
		return positions;
	}

	public float[] getTextureUVs() {
		return textureUVs;
	}

	public int getVertexCount() {
		return positions.length/2;
	}
	
}
