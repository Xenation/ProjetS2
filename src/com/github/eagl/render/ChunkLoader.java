package com.github.eagl.render;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import com.github.eagl.storage.Chunk;

public class ChunkLoader {
	
	public static final ChunkLoader CHUNK_LOADER = new ChunkLoader();
	
	private List<Integer> textures = new ArrayList<Integer>();
	
	private Map<Integer, Integer> vaoMap = new HashMap<Integer, Integer>();
	
	private Map<Integer, FloatBuffer> debugBuffers = new HashMap<Integer, FloatBuffer>();
	
	private int currentVao;
	
	public int createChunkVao() {
		int vaoID = createVAO();
		storeEmptyInterleavedChunkBuffer(0, 1, 2);
		unbindVAO();
		return vaoID;
	}
	
	public int addToVao(int vaoID, int relX, int relY, float[] positions, float[] textureUVs) {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vaoMap.get(vaoID));
		int tileIndex = relY*Chunk.CHUNK_SIZE + relX;
		int tileFloatSize = 16;
//		int floatSize = Float.BYTES;
		FloatBuffer dataAdd = storeInterleavedDataInFloatBuffer(positions, textureUVs);
		
		float[] dataArr = new float[dataAdd.capacity()];
		dataAdd.get(dataArr);
		System.out.println("\nNEW QUAD:\n\tlen"+dataArr.length+"\n\tfIndex"+tileIndex*tileFloatSize+"\n\tQIndex"+tileIndex);
		
		for (int i = 0; i < dataArr.length; i++) {
			debugBuffers.get(vaoID).put(tileIndex*tileFloatSize+i, dataArr[i]);
		}
		
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, debugBuffers.get(vaoID), GL15.GL_DYNAMIC_DRAW);
		
//		GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, tileIndex*tileFloatSize*floatSize, dataAdd); (doesn't work for unknown reasons)
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		return vaoID;
	}
	
	public String debugBuffers() {
		String str = "<~~ BUFFERS DEBUG ~~>\n";
		for (Integer vao : debugBuffers.keySet()) {
			str += "["+vao+"] -> \t";
			int consecFloats = 0;
			float[] data = new float[debugBuffers.get(vao).capacity()];
			debugBuffers.get(vao).get(data);
			for (int i = 0; i < data.length; i++) {
				float val = data[i];
				if (consecFloats >= 4) {
					consecFloats = 0;
					str += "\n\t";
				}
				str += val + ", ";
				consecFloats++;
			}
			str += "\n\n";
		}
		return str;
	}
	
	public String debugGLBuffers() {
		String str = "<~~ GL BUFFERS DEBUG ~~>\n";
		for (Integer vao : debugBuffers.keySet()) {
			str += "["+vao+"] -> \t";
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vaoMap.get(vao));
			for (int i = 0; i < 1024; i++) {
				ByteBuffer buf = BufferUtils.createByteBuffer(16);
				GL15.glGetBufferSubData(GL15.GL_ARRAY_BUFFER, i*4*Float.BYTES, buf);
				FloatBuffer flBuf = buf.asFloatBuffer();
				float[] data = new float[flBuf.capacity()];
				flBuf.get(data);
				str += data[0] + ", " + data[1] + ", " + data[2] + ", " + data[3] + "\n\t";
			}
			str += "\n\n";
		}
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		return str;
	}
	
	public void unloadAll() {
		for (int texture : textures) {
			GL11.glDeleteTextures(texture);			
		}
		for (int vao : vaoMap.keySet()) {
			GL30.glDeleteVertexArrays(vao);
			GL15.glDeleteBuffers(vaoMap.get(vao));
		}
	}
	
	private void storeEmptyInterleavedChunkBuffer(int attribPos, int attribUV, int coordSize) {
		int vboID = GL15.glGenBuffers();
		vaoMap.put(currentVao, vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		int bufferSize = coordSize*2*4 * Chunk.CHUNK_SIZE*Chunk.CHUNK_SIZE; // = coordSize*nbCoords*verticesPerQuad*tilesPerChunk
//		FloatBuffer buffer = BufferUtils.createFloatBuffer(bufferSize);
//		for (int i = 0; i < bufferSize; i++) {
//			buffer.put(0);
//		}
		FloatBuffer buffer = BufferUtils.createFloatBuffer(bufferSize);
		buffer.flip();
		FloatBuffer debBuffer = BufferUtils.createFloatBuffer(bufferSize);
		debBuffer.put(buffer);
		debugBuffers.put(currentVao, debBuffer);
		System.out.println(debBuffer.capacity());
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_DYNAMIC_DRAW);
		GL20.glVertexAttribPointer(attribPos, coordSize, GL11.GL_FLOAT, false, 16, 0);
		GL20.glVertexAttribPointer(attribUV, coordSize, GL11.GL_FLOAT, false, 16, 8);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	private int createVAO() {
		int vaoID = GL30.glGenVertexArrays();
		currentVao = vaoID;
		GL30.glBindVertexArray(vaoID);
		return vaoID;
	}
	
	private void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
	public int loadTexture(String fileName) {
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture("PNG", new FileInputStream("res/"+fileName+".png"));
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int textureID = texture.getTextureID();
		textures.add(textureID);
		return textureID;
	}
	
	private FloatBuffer storeInterleavedDataInFloatBuffer(float[] data1, float[] data2) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data1.length + data2.length);
		buffer.rewind();
		// Stores position and textureUV (x,y,u,v) for each vertex
		for (int i = 0; i < data1.length; i += 2) {
			buffer.put(data1[i]);
			buffer.put(data1[i+1]);
			buffer.put(data2[i]);
			buffer.put(data2[i+1]);
		}
		buffer.flip();
		return buffer;
	}
	
}
