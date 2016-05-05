package fr.iutvalence.info.dut.m2107.fontRendering;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import fr.iutvalence.info.dut.m2107.fontMeshCreator.FontType;
import fr.iutvalence.info.dut.m2107.fontMeshCreator.GUIText;

/**
 * Defines a FontRenderer
 * @author Xenation
 *
 */
public class FontRenderer {
	
	/**
	 * The shader used by this renderer
	 */
	private FontShader shader;
	
	/**
	 * A FontRenderer that uses a new FontShader
	 */
	public FontRenderer() {
		shader = new FontShader();
	}
	
	/**
	 * Renders a map of GUITexts
	 * @param texts the map of the GUITexts to render
	 */
	public void render(Map<FontType, List<GUIText>> texts) {
		prepare();
		for (FontType font : texts.keySet()) {
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, font.getTextureAtlas());
			for (GUIText text : texts.get(font)) {
				if (!text.isDebug() || (text.isDebug() && TextMaster.renderDebug()))
					renderText(text);
			}
		}
		endRendering();
	}
	
	/**
	 * Cleans Up the Shader
	 */
	public void cleanUp(){
		shader.cleanUp();
	}
	
	/**
	 * Starts the shader
	 */
	private void prepare() {
		shader.start();
	}
	
	/**
	 * Renders a single text
	 * @param text the GUIText to render
	 */
	private void renderText(GUIText text) {
		GL30.glBindVertexArray(text.getMesh());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		shader.loadColour(text.getColour());
		shader.loadTranslation(text.getPosition());
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, text.getVertexCount());
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}
	
	/**
	 * Stops the shader
	 */
	private void endRendering() {
		shader.stop();
	}

}
