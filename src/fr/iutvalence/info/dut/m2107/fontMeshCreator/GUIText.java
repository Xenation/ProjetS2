package fr.iutvalence.info.dut.m2107.fontMeshCreator;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import fr.iutvalence.info.dut.m2107.entities.Entity;
import fr.iutvalence.info.dut.m2107.gui.GUIMaster;
import fr.iutvalence.info.dut.m2107.gui.TextSprite;
import fr.iutvalence.info.dut.m2107.storage.GUILayer;

/**
 * Represents a piece of text in the game.
 * @author Karl
 *
 */
public class GUIText extends Entity {
	
	/**
	 * The text to be written
	 */
	private String textString;
	/**
	 * the size of the font
	 */
	private float fontSize;
	
	/**
	 * The colour of the text
	 */
	private Vector3f colour = new Vector3f(0f, 0f, 0f);
	
	/**
	 * The max size of a line
	 */
	private float lineMaxSize;
	/**
	 * The number of lines
	 */
	private int numberOfLines;
	/**
	 * The height of a line
	 */
	private double lineHeight = TextMeshCreator.LINE_HEIGHT;
	
	/**
	 * The font of this text
	 */
	private FontType font;
	
	/**
	 * Whether the text is centered or not
	 */
	private boolean centerText = false;
	
	/**
	 * The width of the written text
	 */
	private float width;
	/**
	 * The height of the written text
	 */
	private float height;
	
	/**
	 * A Text using the given text, font size, x/y coordinates, maximum line length and centered.
	 * @param text the text
	 * @param fontSize the size of the font (1 = normal size, 2 = two times bigger)
	 * @param posX the X coordinate
	 * @param posY the Y coordinate
	 * @param maxLineLength the maximum length of a line
	 * @param centered whether the text needs to be centered
	 * @param isDebug whether the text is used for debug or not.
	 */
	public GUIText(String text, float fontSize, float posX, float posY, float maxLineLength, boolean centered, boolean isDebug) {
		super(new Vector2f(posX+1, posY-1), new TextSprite());
		this.textString = text;
		this.fontSize = fontSize;
		this.font = GUIMaster.font;
		this.lineMaxSize = maxLineLength;
		this.centerText = centered;
		this.colour = GUIMaster.debugColor;
		GUIMaster.loadText(this);
	}
	
	/**
	 * A Text using the given text, font size, x/y coordinates, maximum line length and centered.
	 * @param text the text
	 * @param fontSize the size of the font (1 = normal size, 2 = two times bigger)
	 * @param posX the X coordinate
	 * @param posY the Y coordinate
	 * @param maxLineLength the maximum length of a line
	 * @param centered whether the text needs to be centered
	 */
	public GUIText(String text, float fontSize, float posX, float posY, float maxLineLength, boolean centered) {
		this(text, fontSize, posX, posY, maxLineLength, centered, false);
	}
	
	/**
	 * Updates the text of this element.
	 * @param str the new text
	 */
	public void updateText(String str) {
		if (!textString.equals(str)) {
			this.textString = str;
			GUIMaster.loadText(this);
		}
	}

	/**
	 * Remove the text from the screen.
	 */
	public void remove() {
		GUIMaster.deleteText(this);
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
	 * Sets the position of this Text Element
	 * @param x the new X coordinate
	 * @param y the new Y coordinate
	 */
	public void setPosition(float x, float y) {
		this.pos.x = x+1;
		this.pos.y = y-1;
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
	 * Sets the size of the written text of this element.<br>Do not change unless the model of this element changes.
	 * @param width the new width
	 * @param height the new height
	 */
	public void setSize(float width, float height) {
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Returns the width of the written text
	 * @return the width of the written text
	 */
	public float getWidth() {
		return width;
	}
	
	/**
	 * Returns the height of the written text
	 * @return the height of the written text
	 */
	public float getHeight() {
		return height;
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
	
	/**
	 * Sets the string of this text (does not update the Mesh).<br>To update the mesh use <tt>updateText(String)</tt>
	 * @param str the new string
	 */
	public void setTextString(String str) {
		this.textString = str;
	}
	
	/**
	 * Returns the height of a line
	 * @return the height of a line
	 */
	public double getLineHeight() {
		return lineHeight;
	}
	
	/**
	 * Sets the height of a line
	 * @param lineHeight the new height of a line
	 */
	public void setLineHeight(double lineHeight) {
		this.lineHeight = lineHeight;
	}
	
	@Override
	public void initLayer() {
		this.layer = new GUILayer();
	}
	
	public GUILayer getLayer() {
		return (GUILayer) this.layer;
	}

}
