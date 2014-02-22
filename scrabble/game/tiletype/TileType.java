package scrabble.game.tiletype;

import java.awt.Color;

import scrabble.game.board.Board;

public enum TileType {
	
	REGULAR(new int[][]{}, Color.GRAY),
	DOUBLE_LETTER(Board.DOUBLE_LETTER_CO_ORDS, Color.RED),
	DOUBLE_WORD(Board.DOUBLE_WORD_CO_ORDS, Color.BLUE),
	TRIPLE_LETTER(Board.TRIPLE_LETTER_CO_ORDS, Color.GREEN),
	TRIPLE_WORD(Board.TRIPLE_WORD_CO_ORDS, Color.YELLOW);
	
	private final int[][] co_ords;
	private final Color color;
	
	private TileType(int[][] co_ords, Color color) {
		this.co_ords = co_ords;
		this.color = color;
	}
	
	public int compare(int x, int y) {
		int result = -1;
		for (int[] co_ords : this.co_ords) {
    		if (co_ords[0] == x && co_ords[1] == y) {
    			result = 1;
    			break;
    		}
		}
		return result;
	}
	
	public static TileType getTileType(int x, int y) {
		for (TileType t : TileType.values()) {
			if (t.compare(x, y) == 1) {
				return t;
			}
		}
		return TileType.REGULAR;
	}

	public Color getColor() {
		return color;
	}
	
}
