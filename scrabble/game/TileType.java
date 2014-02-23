package scrabble.game;

import java.awt.Color;

/**
* Different tile types.
* 
* @author Robert-G
*/
public enum TileType {

    CENTRE(Board.CENTRE_TILE_COORDS, Color.PINK),
    REGULAR(new int[][]{}, Color.GRAY),
    DOUBLE_LETTER(Board.DOUBLE_LETTER_COORDS, Color.RED),
    DOUBLE_WORD(Board.DOUBLE_WORD_COORDS, Color.BLUE),
    TRIPLE_LETTER(Board.TRIPLE_LETTER_COORDS, Color.GREEN),
    TRIPLE_WORD(Board.TRIPLE_WORD_COORDS, Color.YELLOW);

    private final int[][] coords;
    private final Color color;

    private TileType(final int[][] coords, final Color color) {
        this.coords = coords;
        this.color = color;
    }

    /**
     * Checks if x and y match an entry in this coordinate array.
     * 
     * @param x x coordinate
     * @param y y coordinate
     * @return  true if match, otherwise false
     */
    public boolean compare(final int x, final int y) {
        for (final int[] coord : coords) {
            if (coord[0] == x && coord[1] == y) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the color of this TileType
     * 
     * @return the color
     * @see    java.awt.Color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Returns the TileType associated with the x and y coordinate pair.
     * 
     * @param x x coordinate
     * @param y y coordinate
     * @return  the TileType
     */
    public static TileType getTileType(final int x, final int y) {
        for (final TileType t : TileType.values()) {
            if (t.compare(x, y)) {
                return t;
            }
        }
        return TileType.REGULAR;
    }

}
