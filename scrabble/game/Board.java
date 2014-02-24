package scrabble.game;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import scrabble.game.wrappers.Tile;

/**
 * The board class is used to construct a new Scrabble board.
 * 
 * @author Robert-G
 */
public class Board extends JPanel {

    private static final long serialVersionUID = 1L;

    /**
     * The x and y coordinates of the center tile.
     */
    public static final int[][] CENTRE_TILE_COORDS = {{7, 7}};

    /**
     * The x and y coordinates of the double word score tiles.
     */
    public static final int[][] DOUBLE_LETTER_COORDS = {
        {1, 2}, {1, 12}, {2, 1}, {2, 4}, {2, 10}, {2, 13}, {4, 2}, {4, 6}, {4, 8}, {4, 12}, {6, 4}, {6, 10}, {8, 4}, {8, 10}, {10, 2}, {10, 6}, {10, 8}, {10, 12}
    };

    /**
     * The x and y coordinates of the double letter score tiles.
     */
    public static final int[][] DOUBLE_WORD_COORDS = {
        {1, 5}, {1, 9}, {3, 7}, {5, 1}, {5, 13}, {7, 3}, {7, 11}, {9, 1}, {9, 13}, {11, 7}, {13, 5}, {13, 9}
    };

    /**
     * The x and y coordinates of the triple letter score tiles.
     */
    public static final int[][] TRIPLE_LETTER_COORDS = {
        {0, 6}, {0, 8}, {3, 3}, {3, 11}, {5, 5}, {5, 9}, {6, 0}, {6, 14}, {8, 0}, {8, 14}, {9, 5}, {9, 9}, {11, 3}, {11, 11}, {14, 6}, {14, 8}
    };

    /**
     * The x and y coordinates of the triple word score tiles.
     */
    public static final int[][] TRIPLE_WORD_COORDS = {{0, 3}, {0, 11}, {3, 0}, {3, 14}, {11, 0}, {11, 14}, {14, 3}, {14, 11}};

    private final Tile[][] tiles = new Tile[15][15];

    /**
     * Constructs a new board.
     */
    public Board() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        for (int row = 0; row < tiles.length; row ++) {
            final JPanel rowPanel = new JPanel();
            rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));
            for (int column = 0; column < tiles[row].length; column ++) {
                tiles[row][column] = new Tile(row, column);
                rowPanel.add(tiles[row][column]);
            }
            add(rowPanel);
        }
    }

    /**
     * Returns the Tile at the given (x, y) coordinate pair on the tile grid.
     * 
     * @param x x coordinate location on tile grid
     * @param y y coordinate location on tile grid
     * @return  the Tile
     * @see     scrabble.game.wrappers.Tile
     */
    public final Tile getTile(final int x, final int y) {
        if (x < 0 || x > 14 || y < 0 || y > 14) {
            return null;
        }
        return tiles[y][x];
    }

    /**
     * Returns the letter at the given (x, y) coordinate pair on the tile grid.
     * 
     * @param x x coordinate location on tile grid
     * @param y y coordinate location on tile grid
     * @return  the letter
     */
    public final String getLetter(final int x, final int y) {
        final Tile t = getTile(x, y);
        return t == null ? null : t.getText().toLowerCase();
    }

    /**
     * Returns the tiles currently loaded in this board.
     * 
     * @return the tiles as a two-dimensional array
     */
    public Tile[][] getTiles() {
        return this.tiles;
    }

    /**
     * Sets the letter on the tile at the given x and y to the letter supplied.
     * 
     * @param x
     * @param y
     * @param letter
     */
    public void setTile(final int x, final int y, final String letter) {
        final Tile t = getTile(x, y);
        if (t != null) {
            t.setText(letter);
        }
    }

}
