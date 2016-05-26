package fr.iutvalence.info.dut.m2107.fontMeshCreator;

/**
 * Stores the vertex data for all the quads on which a text will be rendered.
 * @author Karl
 *
 */
public class TextMeshData {
	
	private float[] vertexPositions;
	private float[] textureCoords;
	
	protected TextMeshData(float[] vertexPositions, float[] textureCoords){
		this.vertexPositions = vertexPositions;
		this.textureCoords = textureCoords;
	}

	public float[] getVertexPositions() {
		return vertexPositions;
	}

	public float[] getTextureCoords() {
		return textureCoords;
	}

	public int getVertexCount() {
		return vertexPositions.length/2;
	}
	
	/**
	 * Returns the maximum X value of all the vertices
	 * @return the maximum X value of all the vertices
	 */
	public float getMaxX() {
		float max = -1;
		for (int i = 0; i < vertexPositions.length; i = i+2) {
			float f = vertexPositions[i];
			if (f > max) {
				max = f;
			}
		}
		return (-1-max)*-1;
	}
	
	/**
	 * Returns the maximum Y value of all the vertices
	 * @return the maximum Y value of all the vertices
	 */
	public float getMaxY() {
		float min = 1;
		for (int i = 1; i < vertexPositions.length; i = i+2) {
			float f = vertexPositions[i];
			if (f < min) {
				min = f;
			}
		}
		float max = 0;
		for (int i = 1; i < vertexPositions.length; i = i+2) {
			float f = vertexPositions[i]-min;
			if (f > max) {
				max = f;
			}
		}
		return max;
	}
	
}
