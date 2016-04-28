package fr.iutvalence.info.dut.m2107.guiRendering;

import java.util.ArrayList;
import java.util.List;

import fr.iutvalence.info.dut.m2107.render.Loader;

public class GUIMaster {
	
	private static List<GUIElement> elements = new ArrayList<GUIElement>();
	
	private static GUIRenderer renderer;
	
	public static void init() {
		renderer = new GUIRenderer();
	}
	
	public static void render() {
		renderer.render(elements);
	}
	
	public static void loadElement(GUIElement elem, String textureName) {
		GUIMeshData data = GUIMeshCreator.generateQuad(elem.width, elem.height);
		elem.vaoID = Loader.GUI_LOADER.loadtoVao(data.getPositions(), data.getTextureUVs());
		elem.textureID = Loader.GUI_LOADER.loadTexture(textureName);
		elem.vertexCount = data.getVertexCount();
		elements.add(elem);
	}
	
	public static void loadElement(GUIElement elem, int textureID) {
		GUIMeshData data = GUIMeshCreator.generateQuad(elem.width, elem.height);
		elem.vaoID = Loader.GUI_LOADER.loadtoVao(data.getPositions(), data.getTextureUVs());
		elem.textureID = textureID;
		elem.vertexCount = data.getVertexCount();
		elements.add(elem);
	}
	
	public static void removeElement(GUIElement elem) {
		elements.remove(elem);
		Loader.GUI_LOADER.unloadVAO(elem.vaoID);
		Loader.GUI_LOADER.unloadTexture(elem.textureID);
	}
	
	public static void cleanUp() {
		renderer.cleanUp();
	}
	
}
