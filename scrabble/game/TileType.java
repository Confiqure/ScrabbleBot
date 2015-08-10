package scrabble.game;

import java.awt.Color;


/**
 * 
 * Tile properties.
 * 
 * @author Robert G
 */
public enum TileType {

    CENTER(ScrabbleBoard.CENTRE_TILE_COORDS, Color.PINK),
    REGULAR(new int[][]{}, Color.GRAY),
    DOUBLE_LETTER(ScrabbleBoard.DOUBLE_LETTER_COORDS, Color.BLUE),
    DOUBLE_WORD(ScrabbleBoard.DOUBLE_WORD_COORDS, Color.RED),
    TRIPLE_LETTER(ScrabbleBoard.TRIPLE_LETTER_COORDS, Color.GREEN),
    TRIPLE_WORD(ScrabbleBoard.TRIPLE_WORD_COORDS, Color.YELLOW);

    /**
     * 
     * @param x
     * @param y
     * @return The TileType associated with the supplied x and y.
     */
    public static TileType getTileType(final int x, final int y) {
        for (final TileType t : TileType.values()) {
            if (t.compare(x, y)) {
                return t;
            }
        }
        return TileType.REGULAR;
    }

    private final int[][] co_ords;
    private final Color color;

    private TileType(final int[][] co_ords, final Color color) {
        this.co_ords = co_ords;
        this.color = color;
    }

    /**
     * 
     * @param x 
     * @param y
     * @return 1 if x and y match an entry in this objects tile co_ord array.
     */
    public boolean compare(final int x, final int y) {
        for (final int[] co_ord : co_ords) {
            if (co_ord[0] == x && co_ord[1] == y) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 
     * @return The color of this TileType
     */
    public Color getColor() {
        return color;
    }

}
