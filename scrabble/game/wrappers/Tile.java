package scrabble.game.wrappers;

import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import scrabble.game.TileType;

/**
* Class containing Tile data.
* 
* @author Robert-G
*/
public class Tile extends JButton {

    private static final long serialVersionUID = 1L;
    private final TileType type;
    private final Point location;

    /**
     * Initializes new Tile on board.
     * 
     * @param x x coordinate
     * @param y y coordinate
     */
    public Tile(final int x, final int y) {
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
                } catch (final HeadlessException ex) {}
                if (letter == null) {
                    return;
                }
                if (letter.isEmpty() || letter.equals(" ")) {
                    setText(" ");
                    scrabble.Scrabble.ui.setSaved(false);
                } else if (Pattern.matches("[a-zA-Z]+", letter)) {
                    setText(letter);
                    scrabble.Scrabble.ui.setSaved(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid character entered.", "Warning!", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    /**
     * Returns the TileType of this Tile.
     * 
     * @return the TileType
     * @see    scrabble.game.TileType
     */
    public TileType getTileType() {
        return type;
    }

    /**
     * Returns the location of the Tile's position on the game board, not its position on screen.
     * 
     * @return the location
     * @see    java.awt.Point
     */
    @Override
    public Point getLocation() {
        return this.location;
    }

}
