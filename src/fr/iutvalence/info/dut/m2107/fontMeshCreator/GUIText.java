package fr.iutvalence.info.dut.m2107.fontMeshCreator;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import fr.iutvalence.info.dut.m2107.entities.Entity;
import fr.iutvalence.info.dut.m2107.fontRendering.TextMaster;
import fr.iutvalence.info.dut.m2107.fontRendering.TextSprite;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;

/**
 * Represents a piece of text in the game.
 * @author Karl
 *
 */
public class GUIText extends Entity {

	private String textString;
	private float fontSize;
	
	private Vector3f colour = new Vector3f(0f, 0f, 0f);
	
	private float lineMaxSize;
	private int numberOfLines;
	private double lineHeight = TextMeshCreator.LINE_HEIGHT;

	private FontType font;

	private boolean centerText = false;
	private final boolean isDebug;
	
	public GUIText(String text, float fontSize, float posX, float posY, float maxLineLength, boolean centered, boolean isDebug) {
		super(new Vector2f(posX+1, posY-1), new TextSprite());
		this.textString = text;
		this.fontSize = fontSize;
		this.font = TextMaster.font;
		this.lineMaxSize = maxLineLength;
		this.centerText = centered;
		this.isDebug = isDebug;
		this.colour = TextMaster.debugColor;
		TextMaster.loadText(this);
		GameWorld.guiLayerMap.getLayer(0).addStreamed(this);
	}
	
	public GUIText(String text, float fontSize, float posX, float posY, float maxLineLength, boolean centered) {
		this(text, fontSize, posX, posY, maxLineLength, centered, false);
	}
	
	public void updateText(String str) {
		if (!textString.equals(str)) {
			this.textString = str;
			TextMaster.loadText(this);
		}
	}

	/**
	 * Remove the text from the screen.
	 */
	public void remove() {
		TextMaster.removeText(this);
		GameWorld.guiLayerMap.getLayer(0).removeStreamed(this);
	}

	/**
	 * @return The font used by this text.
	 */
	public FontType getFont() {
		return font;
	}

	/**
	 * Set the colour of the text.
	 * 
	 * @param r
	 *            - red value, between 0 and 1.
	 * @param g
	 *            - green value, between 0 and 1.
	 * @param b
	 *            - blue value, between 0 and 1.
	 */
	public void setColour(float r, float g, float b) {
		colour.set(r, g, b);
	}

	/**
	 * @return the colour of the text.
	 */
	public Vector3f getColour() {
		return colour;
	}

	/**
	 * @return The number of lines of text. This is determined when the text is
	 *         loaded, based on the length of the text and the max line length
	 *         that is set.
	 */
	public int getNumberOfLines() {
		return numberOfLines;
	}

	/**
	 * @return The position of the top-left corner of the text in screen-space.
	 *         (0, 0) is the top left corner of the screen, (1, 1) is the bottom
	 *         right.
	 */
	public Vector2f getPosition() {
		return this.pos;
	}

	/**
	 * @return the ID of the text's VAO, which contains all the vertex data for
	 *         the quads on which the text will be rendered.
	 */
	public int getMesh() {
		return this.spr.getVaoID();
	}

	/**
	 * Set the VAO and vertex count for this text.
	 * 
	 * @param vao
	 *            - the VAO containing all the vertex data for the quads on
	 *            which the text will be rendered.
	 * @param verticesCount
	 *            - the total number of vertices in all of the quads.
	 */
	public void setMeshInfo(int vao, int verticesCount) {
		TextSprite spr = (TextSprite) this.spr;
		spr.setVaoID(vao);
		spr.setVertexCount(verticesCount);
	}

	/**
	 * @return The total number of vertices of all the text's quads.
	 */
	public int getVertexCount() {
		TextSprite spr = (TextSprite) this.spr;
		return spr.getVertexCount();
	}

	/**
	 * @return the font size of the text (a font size of 1 is normal).
	 */
	protected float getFontSize() {
		return fontSize;
	}

	/**
	 * Sets the number of lines that this text covers (method used only in
	 * loading).
	 * 
	 * @param number
	 */
	protected void setNumberOfLines(int number) {
		this.numberOfLines = number;
	}

	/**
	 * @return {@code true} if the text should be centered.
	 */
	protected boolean isCentered() {
		return centerText;
	}

	/**
	 * @return The maximum length of a line of this text.
	 */
	protected float getMaxLineSize() {
		return lineMaxSize;
	}

	/**
	 * @return The string of text.
	 */
	public String getTextString() {
		return textString;
	}
	
	public void setTextString(String str) {
		this.textString = str;
	}

	public double getLineHeight() {
		return lineHeight;
	}

	public void setLineHeight(double lineHeight) {
		this.lineHeight = lineHeight;
	}

	public boolean isDebug() {
		return isDebug;
	}

}
