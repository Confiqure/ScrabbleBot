package scrabble.game.wrappers;

import java.awt.Font;
import java.awt.Point;
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
	private final Point location;

	public Tile(int x, int y) {
		type = TileType.getTileType(x, y);
		location = new Point(x, y);
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

	/**
	 * 
	 * @return the TileType of this Tile.
	 */
	public TileType getTileType() {
		return type;
	}
	
	/**
	 * @return the location of this Tile.
	 * The location refers to the tiles position on the game board and not position on screen.
	 */
	public Point getLocation() {
		return this.location;
	}

}
