package fr.iutvalence.info.dut.m2107.guiRendering;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * Defines a GUIRenderer
 * @author Xenation
 *
 */
public class GUIRenderer {
	
	/**
	 * The shader used by this renderer
	 */
	private GUIShader shader;
	
	/**
	 * A GUIRenderer that uses a new GUIShader
	 */
	public GUIRenderer() {
		this.shader = new GUIShader();
	}
	
	public void cleanUp() {
		shader.cleanUp();
	}
	
	public void render(List<GUIElement> elems) {
		shader.start();
		for (GUIElement elem : elems) {
			render(elem);
		}
		shader.stop();
	}
	
	private void render(GUIElement elem) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, elem.getTextureID());
		
		GL30.glBindVertexArray(elem.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		shader.loadTranslation(elem.getRealPosition());
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, elem.getVertexCount());
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}
	
}
