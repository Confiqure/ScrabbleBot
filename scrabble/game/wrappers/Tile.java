package scrabble.game.wrappers;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import scrabble.game.tiletype.TileType;

/**
 * @author Robert G
 *
 */
public class Tile extends JButton {

	private static final long serialVersionUID = 1L;

	private TileType type;

	public Tile(int x, int y) {
		this.type = TileType.getTileType(x, y);
		setBackground(type.getColor());
		setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
		setText(" ");
		setFocusable(false);
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				String letter = JOptionPane.showInputDialog(null, null, "Enter letter", JOptionPane.INFORMATION_MESSAGE).substring(0, 1).toUpperCase();
				if (letter != null && !letter.equals(" ") && !letter.matches(".*\\d.*")) {
					setText(letter);
				} else {
					JOptionPane.showMessageDialog(null, "Invalid character entered.", "Warning!", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
	}

	public TileType getTileType() {
		return this.type;
	}

}
