package fr.iutvalence.info.dut.m2107.fontRendering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.iutvalence.info.dut.m2107.fontMeshCreator.FontType;
import fr.iutvalence.info.dut.m2107.fontMeshCreator.GUIText;
import fr.iutvalence.info.dut.m2107.fontMeshCreator.TextMeshData;
import fr.iutvalence.info.dut.m2107.render.Loader;

public class TextMaster {
	
	private static Loader loader;
	private static Map<FontType, List<GUIText>> texts = new HashMap<FontType, List<GUIText>>();
	private static FontRenderer renderer;
	
	public static FontType font;
	
	public static void init() {
		renderer = new FontRenderer();
		loader = Loader.TEXT_LOADER;
		font = new FontType("CourierNew");
	}
	
	public static void render() {
		renderer.render(texts);
	}
	
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
	
	public static void removeText(GUIText text) {
		List<GUIText> textBatch = texts.get(text.getFont());
		if (textBatch != null) {
			textBatch.remove(text);
			Loader.TEXT_LOADER.unloadVAO(text.getMesh());
			if (textBatch.isEmpty()) {
				texts.remove(textBatch);
			}
		}
	}
	
	public static void cleanUp() {
		renderer.cleanUp();
	}
	
}
