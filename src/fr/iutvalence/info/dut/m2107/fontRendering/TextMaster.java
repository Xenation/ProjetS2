package fr.iutvalence.info.dut.m2107.fontRendering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.iutvalence.info.dut.m2107.fontMeshCreator.FontType;
import fr.iutvalence.info.dut.m2107.fontMeshCreator.GUIText;
import fr.iutvalence.info.dut.m2107.fontMeshCreator.TextMeshData;
import fr.iutvalence.info.dut.m2107.render.Loader;

/**
 * Used to manage the texts
 * @author Xenation
 *
 */
public class TextMaster {
	
	/**
	 * The loader to use when creating new text
	 */
	private static Loader loader;
	/**
	 * A map that groups GUITexts by FontType
	 */
	private static Map<FontType, List<GUIText>> texts = new HashMap<FontType, List<GUIText>>();
	/**
	 * The Renderer to use when rendering text
	 */
	private static FontRenderer renderer;
	
	/**
	 * The default font in which to write
	 */
	public static FontType font;
	
	/**
	 * Initialises the renderer, loader and font
	 */
	public static void init() {
		renderer = new FontRenderer();
		loader = Loader.TEXT_LOADER;
		font = new FontType("CourierNew");
	}
	
	/**
	 * Uses the renderer to render the texts
	 */
	public static void render() {
		renderer.render(texts);
	}
	
	/**
	 * Loads a text to VAO and adds it to texts map
	 * @param txt the text to load
	 */
	public static void loadText(GUIText txt) {
		FontType font = txt.getFont();
		TextMeshData data = font.loadText(txt);
		int vao = loader.loadtoVao(data.getVertexPositions(), data.getTextureCoords());
		txt.setMeshInfo(vao, data.getVertexCount());
		List<GUIText> textBatch = texts.get(font);
		if (textBatch == null) {
			textBatch = new ArrayList<GUIText>();
			texts.put(font, textBatch);
		}
		textBatch.add(txt);
	}
	
	/**
	 * Unloads a text from VAO and removes if from texts map
	 * @param text the text to remove
	 */
	public static void removeText(GUIText text) {
		List<GUIText> textBatch = texts.get(text.getFont());
		if (textBatch != null) {
			textBatch.remove(text);
			loader.unloadVAO(text.getMesh());
			if (textBatch.isEmpty()) {
				texts.remove(textBatch);
			}
		}
	}
	
	/**
	 * Cleans Up the renderer
	 */
	public static void cleanUp() {
		renderer.cleanUp();
	}
	
}
