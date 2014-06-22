package scrabble.game.tiletype;

import java.awt.Color;

import scrabble.game.scrabbleboard.ScrabbleBoard;

/**
 * @author Robert G
 * 
 * TileType
 */
public enum TileType {

	CENTRE(ScrabbleBoard.CENTRE_TILE_COORDS, Color.PINK),
	REGULAR(new int[][]{}, Color.GRAY),
	DOUBLE_LETTER(ScrabbleBoard.DOUBLE_LETTER_COORDS, Color.RED),
	DOUBLE_WORD(ScrabbleBoard.DOUBLE_WORD_COORDS, Color.BLUE),
	TRIPLE_LETTER(ScrabbleBoard.TRIPLE_LETTER_COORDS, Color.GREEN),
	TRIPLE_WORD(ScrabbleBoard.TRIPLE_WORD_COORDS, Color.YELLOW);

	/**
	 * 
	 * @param x
	 * @param y
	 * @return The TileType associated with the supplied x and y.
	 */
	public static TileType getTileType(int x, int y) {
		for (TileType t : TileType.values()) {
			if (t.compare(x, y)) {
				return t;
			}
		}
		return TileType.REGULAR;
	}
	private final int[][] co_ords;
	private final Color color;

	private TileType(int[][] co_ords, Color color) {
		this.co_ords = co_ords;
		this.color = color;
	}

	/**
	 * 
	 * @param x 
	 * @param y
	 * @return 1 if x and y match an entry in this objects tile co_ord array.
	 */
	public boolean compare(int x, int y) {
		for (int[] co_ords : this.co_ords) {
			if (co_ords[0] == x && co_ords[1] == y) {
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
