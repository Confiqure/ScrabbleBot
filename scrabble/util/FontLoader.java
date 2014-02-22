package scrabble.util;

import java.awt.Font;
import java.awt.GraphicsEnvironment;

/**
 * @author Robert G
 *
 */
public class FontLoader {

	/**
	 * 
	 * @param fontName
	 * @param type
	 * @param style
	 * @param size
	 * @return
	 */
	public Font loadFont(String fontName, int type, int style, int size) {
		try {
			Font font = Font.createFont(type, this.getClass().getResourceAsStream("/scrabble/fonts/" + fontName));
			font = font.deriveFont(style, size);
			final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(font);
			return font;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Font("Segoe UI Semibold", style, size);
	}

}

