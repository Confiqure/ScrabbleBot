package scrabble.game.wrappers;

import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import scrabble.game.Game;
import scrabble.game.TileType;
import scrabble.util.CharLimit;

/**
 * 
 * Class containing Tile data.
 * 
 * @author Robert-G
 */
public class Tile extends JButton {

    private static final long serialVersionUID = 1L;
    private final TileType type;
    private final Point location;
    private final int letterBonus;
    private final int wordBonus;
    private String letter = " ";
    private int letterValue = 0;

    public Tile(final int x, final int y) {
        type = TileType.getTileType(x, y);
        switch (type) {
            case DOUBLE_LETTER:
                letterBonus = 2;
                wordBonus = 1;
                break;
            case DOUBLE_WORD:
                letterBonus = 1;
                wordBonus = 2;
                break;
            case TRIPLE_LETTER:
                letterBonus = 3;
                wordBonus = 1;
                break;
            case TRIPLE_WORD:
                letterBonus = 1;
                wordBonus = 3;
                break;
            default:
                letterBonus = 1;
                wordBonus = 1;
                break;
        }
        location = new Point(x, y);
        setBackground(type.getColor());
        setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        setText(letter);
        setFocusable(false);
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final String curText = getText();
                final JTextField input = new JTextField(new CharLimit(1), (curText.equals(" ") ? "" : curText), 1);
                final Object[] info = { "Enter a letter.", input };
                final int returnVal = JOptionPane.showConfirmDialog(null, info, "Input", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (returnVal == JOptionPane.OK_OPTION) {
                    final String enteredVal = input.getText();
                    if (enteredVal.equals(" ") || Pattern.matches("[a-zA-Z]+", enteredVal)) {
                         setLetter(enteredVal.charAt(0));
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid character entered.", "Warning!", JOptionPane.WARNING_MESSAGE);
                        actionPerformed(e);
                    }
                }
            }
        });
    }
    
    /**
     * Clears the current letter.
     */
    public void clearLetter() {
        setLetter(" ");
    }
    
    /**
     * 
     * @return the current letter on this tile.
     */
    public String getLetter() {
        return this.letter;
    }
    
    /**
     * 
     * @return the letter multiplier.
     */
    public int getLetterBonus() {
        return this.letterBonus;
    }
    
    /**
     * 
     * @return The value current letters value if a letter is displayed, else 0;
     */
    public int getLetterValue() {
        return this.letterValue;
    }

    /**
     * 
     * The location refers to the tiles position on the game board and not position on screen.
     * 
     * @return the location of this Tile.
     */
    @Override
    public Point getLocation() {
        return this.location;
    }
    
    /**
     * 
     * @return the TileType of this Tile.
     */
    public TileType getTileType() {
        return this.type;
    }

    /**
     * 
     * @return the word bonus value of this tile
     */
    public int getWordBonus() {
        return this.wordBonus;
    }

    /**
     * 
     * @param c character to set the letter to
     * @see     scrabble.game.wrappers.Tile.setLetter(String letter);
     */
    public void setLetter(char c) {
        setLetter(String.valueOf(c));
    }

    /**
     * Sets the currently displayed letter.
     * 
     * @param letter the letter to display.
     */
    public void setLetter(final String letter) {
        this.letter = letter;
        this.letterValue = letter.equals(" ") ? 0 : Game.getLetterValue(letter);
        this.setText(letter.toUpperCase());
    }

}
