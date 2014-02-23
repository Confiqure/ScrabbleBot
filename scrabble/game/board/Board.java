package scrabble.game.board;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import scrabble.game.wrappers.Tile;

/**
 * @author Robert G
 * 
 * The board class is used to construct a new Scrabble board.
 *
 */
public class Board extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * int[][] The x and y co_ords of the centre tile.
	 */
	public static final int[][] CENTRE_TILE_CO_ORDS = {{7, 7}};
	
	/**
	 * int[][] The x and y co_ords of the double letter score tiles.
	 */
	public static final int[][] DOUBLE_LETTER_CO_ORDS = {{1, 5}, {1, 9}, {3, 7}, {5, 1}, {5, 13}, {7, 3}, {7, 11}, {9, 1}, {9, 13}, {11, 7}, {13, 5}, {13, 9}};
	
	/**
	 * int[][] The x and y co_ords of the double word score tiles.
	 */
	public static final int[][] DOUBLE_WORD_CO_ORDS = {{1, 2}, {1, 12}, {2, 1}, {2, 4}, {2, 10}, {2, 13}, {4, 2}, {4, 6}, {4, 8}, {4, 12}, {6, 4}, {6, 10}, {8, 4}, {8, 10}, {10, 2}, {10, 6}, {10, 8}, {10, 12}};
	
	/**
	 * int[][] The x and y co_ords of the triple letter score tiles.
	 */
	public static final int[][] TRIPLE_LETTER_CO_ORDS = {{0, 6}, {0, 8}, {3, 3}, {3, 11}, {5, 5}, {5, 9}, {6, 0}, {6, 14}, {8, 0}, {8, 14}, {9, 5}, {9, 9}, {11, 3}, {11, 11}, {14, 6}, {14, 8}};
	
	/**
	 * int[][] The x and y co_ords of the triple word score tiles.
	 */
	public static final int[][] TRIPLE_WORD_CO_ORDS = {{0, 3}, {0, 11}, {3, 0}, {3, 14}, {11, 0}, {11, 14}, {14, 3}, {14, 11}};

	/**
	 * Tile[][] The tiles that make up this board.
	 */
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
	 */
	public final Tile getTile(final int x, final int y) {
		if (x < 0 || x > 14 || y < 0 || y > 14) {
			return null;
		}
		return tiles[x][y];
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
	 * 
	 * @return the tiles currently loaded in this board.
	 */
	public Tile[][] getTiles() {
		return this.tiles;
	}
	
	/**
	 * Sets the letter on the tile at the given x and y to the letter supplied.
	 * @param x
	 * @param y
	 * @param letter
	 */
	public void setTile(int x, int y, String letter) {
		final Tile t = getTile(x, y);
		if (t != null) {
			t.setText(letter);
		}
	}

}
