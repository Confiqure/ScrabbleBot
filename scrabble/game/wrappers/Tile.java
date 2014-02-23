package scrabble.game.wrappers;

import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import scrabble.game.tiletype.TileType;
import scrabble.ui.charlimitdocument.CharLimitDocument;

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
				final String curText = getText();
				final JTextField input = new JTextField(new CharLimitDocument(1), (curText.equals(" ") ? "" : curText), 1);
				final Object[] info = { "Enter a letter.", input };
				final int returnVal = JOptionPane.showConfirmDialog(null, info, "Input!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if (returnVal == JOptionPane.OK_OPTION) {
					final String enteredVal = input.getText().toUpperCase();
					if (enteredVal.equals(" ") || Pattern.matches("[a-zA-Z]+", enteredVal)) {
						 setText(enteredVal);
					} else {
						JOptionPane.showMessageDialog(null, "Invalid character entered.", "Warning!", JOptionPane.WARNING_MESSAGE);
						actionPerformed(e);
					}
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
