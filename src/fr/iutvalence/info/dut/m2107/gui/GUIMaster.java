package fr.iutvalence.info.dut.m2107.gui;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.util.vector.Vector3f;

import fr.iutvalence.info.dut.m2107.fontMeshCreator.FontType;
import fr.iutvalence.info.dut.m2107.fontMeshCreator.GUIText;
import fr.iutvalence.info.dut.m2107.fontMeshCreator.TextMeshData;
import fr.iutvalence.info.dut.m2107.render.Loader;
import fr.iutvalence.info.dut.m2107.storage.GameWorld;
import fr.iutvalence.info.dut.m2107.storage.Layer;

/**
 * Allows to easily manage gui elements
 * @author Xenation
 *
 */
public class GUIMaster {
	
	/**
	 * The loader used to load text elements
	 */
	private static Loader textLoader;
	
	/**
	 * The Loader used to load gui elements
	 */
	private static Loader guiLoader;
	
	/**
	 * The default font to use
	 */
	public static FontType font;
	
	/**
	 * The color of debug text
	 */
	public static Vector3f debugColor = new Vector3f(1, 1, 1);
	
	/**
	 * Allows to remove loaded texts without the need to specify the layer it is linked to
	 */
	private static Map<GUIText, Layer> textLinks = new HashMap<GUIText, Layer>();
	/**
	 * Allows to remove loaded elements without the need to specify the layer it is linked to
	 */
	private static Map<GUIElement, Layer> elementLinks = new HashMap<GUIElement, Layer>();
	
	/**
	 * Initialises the renderer, loader and font
	 */
	public static void init() {
		textLoader = Loader.TEXT_LOADER;
		guiLoader = Loader.GUI_LOADER;
		font = new FontType("Pixel");
	}
	
	/**
	 * Loads a text to VAO
	 * @param txt the text to load
	 */
	public static GUIText loadText(GUIText txt) {
		FontType font = txt.getFont();
		TextMeshData data = font.loadText(txt);
		int vao = textLoader.loadtoVao(data.getVertexPositions(), data.getTextureCoords());
		if (txt.getSprite().getVaoID() != 0) {
			textLoader.unloadVAO(txt.getSprite().getVaoID());
		}
		txt.setMeshInfo(vao, data.getVertexCount());
		return txt;
	}
	
	/**
	 * Adds a text to the specified layer.
	 * @param txt the text to add
	 * @param layerIndex the index of the layer
	 * @return the text that has been added
	 */
	public static GUIText addText(GUIText txt, int layerIndex) {
		Layer lay = GameWorld.guiLayerMap.getLayer(layerIndex);
		textLinks.put(txt, lay);
		lay.add(txt);
		return txt;
	}
	
	/**
	 * Adds a text to the 0 layer
	 * @param txt the text to add
	 * @return the text that has been added
	 */
	public static GUIText addText(GUIText txt) {
		return addText(txt, 0);
	}
	
	/**
	 * Adds a GUI element to the specified layer
	 * @param elem the element to add
	 * @param layerIndex the index of the layer
	 * @return the element that has been added
	 */
	public static GUIElement addElement(GUIElement elem, int layerIndex) {
		Layer lay = GameWorld.guiLayerMap.getLayer(layerIndex);
		elementLinks.put(elem, lay);
		lay.add(elem);
		return elem;
	}
	
	/**
	 * Removes a text by unloading it and removing it from its layer
	 * @param text the text to remove
	 */
	public static void removeText(GUIText text) {
		textLoader.unloadVAO(text.getMesh());
		textLinks.get(text).remove(text);
	}
	
	/**
	 * Removes an element by unloading it and removing it from its layer
	 * @param elem the element to remove
	 */
	public static void removeElement(GUIElement elem) {
		guiLoader.unloadVAO(elem.getSprite().getVaoID());
		elementLinks.get(elem).remove(elem);
	}
	
}
