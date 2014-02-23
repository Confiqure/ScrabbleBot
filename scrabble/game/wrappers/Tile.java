package scrabble.game.wrappers;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import scrabble.game.tiletype.TileType;

/**
 * @author Robert G
 *
 */
public class Tile extends JButton {

	private static final long serialVersionUID = 1L;
	private final TileType type;

	public Tile(int x, int y) {
		type = TileType.getTileType(x, y);
		setBackground(type.getColor());
		setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
		setText(" ");
		setFocusable(false);
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				String letter = null;
				try {
					letter = JOptionPane.showInputDialog(null, "Enter a letter").substring(0, 1).toUpperCase();
				} catch (Exception e1) {}
				if (letter != null && Pattern.matches("[a-zA-Z]+", letter)) {
					setText(letter);
				} else {
					JOptionPane.showMessageDialog(null, "Invalid character entered.", "Warning!", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
	}

	public TileType getTileType() {
		return type;
	}

}
