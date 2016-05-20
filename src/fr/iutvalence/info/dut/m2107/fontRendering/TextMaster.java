package fr.iutvalence.info.dut.m2107.fontRendering;

import org.lwjgl.util.vector.Vector3f;

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
	 * Whether debug texts needs to be rendered
	 */
	private static boolean renderDebug = false;
	
	/**
	 * The default font in which to write
	 */
	public static FontType font;
	
	/**
	 * Debug color
	 */
	public static Vector3f debugColor = new Vector3f(1, 1, 1);
//	public static Vector3f debugColor = new Vector3f(1, .662745f, .223529f);
	
	/**
	 * Initialises the renderer, loader and font
	 */
	public static void init() {
		loader = Loader.TEXT_LOADER;
		font = new FontType("Pixel");
	}
	
	/**
	 * Initialises the renderer, loader and font
	 */
	public static void init(boolean debug) {
		loader = Loader.TEXT_LOADER;
		font = new FontType("Pixel");
		renderDebug = debug;
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
	}
	
	/**
	 * Unloads a text from VAO and removes if from texts map
	 * @param text the text to remove
	 */
	public static void removeText(GUIText text) {
		loader.unloadVAO(text.getMesh());
	}
	
	/**
	 * Returns whether the debug text needs to be rendered
	 * @return whether the debug text needs to be rendered
	 */
	public static boolean renderDebug() {
		return renderDebug;
	}
	
}
