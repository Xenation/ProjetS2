package com.github.eagl.shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public abstract class ShaderProgram {
	
	/**
	 * The ID of the OpenGL program
	 */
	private int programID;
	/**
	 * The ID of the vertex shader
	 */
	private int vertexShaderID;
	/**
	 * The ID of the fragment shader
	 */
	private int fragmentShaderID;
	
	/**
	 * Buffer to temporary store the matrices to be loaded
	 */
	private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
	
	/**
	 * A ShaderProgram with the specified vertex and fragment shaders files
	 * @param vertexFile the path to the vertex shader file
	 * @param fragmentFile the path to the fragment shader file
	 */
	public ShaderProgram(String vertexFile, String fragmentFile) {
		vertexShaderID = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
		fragmentShaderID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
		programID = GL20.glCreateProgram();
		GL20.glAttachShader(programID, vertexShaderID);
		GL20.glAttachShader(programID, fragmentShaderID);
		bindAttributes();
		GL20.glLinkProgram(programID);
		GL20.glValidateProgram(programID);
		getAllUniformLocations();
	}
	
	/**
	 * Gets the locations of all the uniform variables necessary for the program
	 */
	protected abstract void getAllUniformLocations();
	
	/**
	 * Gets the location of the specified uniform variable
	 * @param uniformName the name of the uniform variable to look for
	 * @return the location of the uniform variable
	 */
	protected int getUniformLocation(String uniformName) {
		return GL20.glGetUniformLocation(programID, uniformName);
	}
	
	/**
	 * loads a float at the specified location in the shader
	 * @param location the location of the uniform variable in which to load the float
	 * @param value the float to load
	 */
	protected void loadFloat(int location, float value) {
		GL20.glUniform1f(location, value);
	}
	
	/**
	 * loads a vector (3 components) at the specified location in the shader
	 * @param location the location of the uniform variable in which to load the vector
	 * @param vector the vector to load
	 */
	protected void loadVector(int location, Vector3f vector) {
		GL20.glUniform3f(location, vector.x, vector.y, vector.z);
	}
	
	/**
	 * loads a vector (2 components) at the specified location in the shader
	 * @param location the location of the uniform variable in which to load the vector
	 * @param vector the vector to load
	 */
	protected void load2DVector(int location, Vector2f vector) {
		GL20.glUniform2f(location, vector.x, vector.y);
	}
	
	/**
	 * loads a boolean at the specified location in the shader
	 * since booleans do not exist in GLSL 1 is loaded if true, 0 otherwise
	 * @param location the location of the uniform variable in which to load the boolean
	 * @param value the boolean to load
	 */
	protected void loadBoolean(int location, boolean value) {
		float toLoad = 0;
		if (value)
			toLoad = 1;
		GL20.glUniform1f(location, toLoad);
	}
	
	/**
	 * loads a matrix at the specified location in the shader
	 * @param location the location of the uniform variable in which to load the matrix
	 * @param matrix the matrix to load
	 */
	protected void loadMatrix(int location, Matrix4f matrix) {
		matrix.store(matrixBuffer);
		matrixBuffer.flip();
		GL20.glUniformMatrix4(location, false, matrixBuffer);
	}
	
	/**
	 * Starts the shader program
	 */
	public void start() {
		GL20.glUseProgram(programID);
	}
	
	/**
	 * Stops the shader program
	 */
	public void stop() {
		GL20.glUseProgram(0);
	}
	
	/**
	 * Deletes the shaders and the program from OpenGL
	 */
	public void cleanUp() {
		stop();
		GL20.glDetachShader(programID, vertexShaderID);
		GL20.glDetachShader(programID, fragmentShaderID);
		GL20.glDeleteShader(vertexShaderID);
		GL20.glDeleteShader(fragmentShaderID);
		GL20.glDeleteProgram(programID);
	}
	
	/**
	 * binds the VAOs attributes to shader variables
	 */
	protected abstract void bindAttributes();
	
	/**
	 * Binds the specified attribute to the specified variable
	 * @param attribute the attribute to bind
	 * @param variableName the name of the variable to bind the attribute to
	 */
	protected void bindAttribute(int attribute, String variableName) {
		GL20.glBindAttribLocation(programID, attribute, variableName);
	}
	
	/**
	 * Loads the specified shader file
	 * @param file the path to the shader file to load
	 * @param type the type of shader to load
	 * @return the ID of the loaded shader
	 */
	private static int loadShader(String file, int type) {
		StringBuilder shaderSource = new StringBuilder();
		try{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while((line = reader.readLine())!=null){
				shaderSource.append(line).append("\n");
			}
			reader.close();
		}
		catch(IOException e){
			System.err.println("Could not read file!");
			e.printStackTrace();
			System.exit(-1);
		}
		int shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, shaderSource);
		GL20.glCompileShader(shaderID);
		if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE){
			System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
			System.out.println("Could not compile shader!");
			System.exit(-1);
		}
		return shaderID;
	}
	
}
