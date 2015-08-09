package scrabble.game.scrabbleboard;

import java.awt.GridLayout;

import javax.swing.JPanel;

import scrabble.game.wrappers.Tile;

/**
 * @author Robert G
 * 
 * The board class is used to construct a new Scrabble board.
 *
 */
public class ScrabbleBoard extends JPanel {

	private static final long serialVersionUID = 1L;

    /**
     * The x and y coordinates of the center tile.
     */
    public static final int[][] CENTRE_TILE_COORDS = {{7, 7}};

    /**
     * The x and y coordinates of the double letter wordScore tiles.
     */
    public static final int[][] DOUBLE_LETTER_COORDS = {
        {1, 2}, {1, 12}, {2, 1}, {2, 4}, {2, 10}, {2, 13}, {4, 2}, {4, 6}, {4, 8}, {4, 12}, {6, 4}, {6, 10}, {8, 4}, {8, 10}, {10, 2}, {10, 6}, {10, 8}, {10, 12}, {12, 1}, {12, 4}, {12, 10}, {12, 13}, {13, 2}, {13, 12}
    };

    /**
     * The x and y coordinates of the double word wordScore tiles.
     */
    public static final int[][] DOUBLE_WORD_COORDS = {
        {1, 5}, {1, 9}, {3, 7}, {5, 1}, {5, 13}, {7, 3}, {7, 11}, {9, 1}, {9, 13}, {11, 7}, {13, 5}, {13, 9}
    };

    /**
     * The x and y coordinates of the triple letter wordScore tiles.
     */
    public static final int[][] TRIPLE_LETTER_COORDS = {
        {0, 6}, {0, 8}, {3, 3}, {3, 11}, {5, 5}, {5, 9}, {6, 0}, {6, 14}, {8, 0}, {8, 14}, {9, 5}, {9, 9}, {11, 3}, {11, 11}, {14, 6}, {14, 8}
    };

    /**
     * The x and y coordinates of the triple word wordScore tiles.
     */
    public static final int[][] TRIPLE_WORD_COORDS = {{0, 3}, {0, 11}, {3, 0}, {3, 14}, {11, 0}, {11, 14}, {14, 3}, {14, 11}};
    
	/**
	 * The width of this board.
	 */
	private final int width = 15;
	
	/**
	 * The height of this board.
	 */
	private final int height = width;

	/**
	 * Tile[][] The tiles that make up this board.
	 */
	private final Tile[][] tiles = new Tile[width][height];

	/**
	 * Constructs a new scrabble board.
	 */
	public ScrabbleBoard() {
		setLayout(new GridLayout(width, height));
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				final Tile tile = new Tile(x, y);
				tiles[x][y] = tile;
				add(tile);
			}
		}
	}
	
	/**
	 * Clears all letters currently on the board.
	 */
	public void clear() {
		for (final Tile[] row : tiles) {
			for (final Tile column : row) {
				column.clearLetter();
			}
		}
	}
	
	/**
	 * 
	 * @return the width of this board.
	 */
	public int width() {
		return width;
	}
	
	/**
	 * 
	 * @return the height of this board.
	 */
	public int height() {
		return height;
	}
	
	/**
	 * Returns the Tile at the given (x, y) coordinate pair on the tile grid.
	 * 
	 * @param x x coordinate location on tile grid
	 * @param y y coordinate location on tile grid
	 * @return  the Tile
	 */
	public Tile getTileAt(final int x, final int y) {
		return (x > -1 && x < width && y > -1 && y < height) ? tiles[x][y] : null;
	}

	/**
	 * Returns the letter at the given (x, y) coordinate pair on the tile grid.
	 * 
	 * @param x x coordinate location on tile grid
	 * @param y y coordinate location on tile grid
	 * @return  the letter
	 */
	public String getLetterAt(final int x, final int y) {
		final Tile t = getTileAt(x, y);
		return t == null ? null : t.getText().toLowerCase();
	}

	/**
	 * 
	 * @return the tiles currently loaded in this board.
	 */
	public Tile[][] getAllTiles() {
		return this.tiles;
	}
	
	/**
	 * Sets the letter on the tile at the given x and y to the letter supplied.
	 * @param x
	 * @param y
	 * @param letter
	 */
	public void setLetterAtTile(int x, int y, String letter) {
		final Tile t = getTileAt(x, y);
		if (t != null) {
			t.setLetter(letter.charAt(0));
		}
	}
	
	/**
	 * Checks if this board has any content (letters entered on it).
	 * @return true if any letters discovered else false.
	 */
	public boolean hasValidContent() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (!getLetterAt(x, y).equals(" ")) {
					return true;
				}
			}
		}
		return false;
	}

}
