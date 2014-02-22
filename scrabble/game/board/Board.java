package scrabble.game.board;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import scrabble.wrappers.Tile;

/**
 * @author Robert G
 *
 */
public class Board extends JPanel {

	private static final long serialVersionUID = 1L;

	public static final int[][] DOUBLE_LETTER_CO_ORDS = {{1, 5}, {1, 9}, {3, 7}, {5, 1}, {5, 13}, {7, 3}, {7, 11}, {9, 1}, {9, 13}, {11, 7}, {13, 5}, {13, 9}};
	public static final int[][] DOUBLE_WORD_CO_ORDS = {{1, 2}, {1, 12}, {2, 1}, {2, 4}, {2, 10}, {2, 13}, {4, 2}, {4, 6}, {4, 8}, {4, 12}, {6, 4}, {6, 10}, {8, 4}, {8, 10}, {10, 2}, {10, 6}, {10, 8}, {10, 12}};
	public static final int[][] TRIPLE_LETTER_CO_ORDS = {{0, 6}, {0, 8}, {3, 3}, {3, 11}, {5, 5}, {5, 9}, {6, 0}, {6, 14}, {8, 0}, {8, 14}, {9, 5}, {9, 9}, {11, 3}, {11, 11}, {14, 6}, {14, 8}};
	public static final int[][] TRIPLE_WORD_CO_ORDS = {{0, 3}, {0, 11}, {3, 0}, {3, 14}, {11, 0}, {11, 14}, {14, 3}, {14, 11}};

	private final Tile[][] tiles = new Tile[15][15];

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

	public Tile[][] getTiles() {
		return this.tiles;
	}

}
