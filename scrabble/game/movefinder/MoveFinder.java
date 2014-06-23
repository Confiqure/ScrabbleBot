package scrabble.game.movefinder;

import java.awt.Point;
import java.util.ArrayList;

import scrabble.game.scrabbleboard.ScrabbleBoard;
import scrabble.game.wrappers.Regex;

/**
 *
 * Finds all possible moves
 * 
 * @author MehSki11zOwn
 */
public class MoveFinder {

	private ArrayList<Regex> tiles = new ArrayList<>();
	private final ScrabbleBoard board;

	public MoveFinder(ScrabbleBoard b) {
		this.board = b;
	}

	private void addBoard(final int x, final int y, final String playOff, final int before, final int after, final boolean vert) {
		String regex = "";
		if (before < 0) {
			regex += "[a-z]*";
		} else if (before > 0) {
			regex += "[a-z]{0," + before + "}";
		}
		regex += playOff;
		if (after < 0) {
			regex += "[a-z]*";
		} else if (after > 0) {
			regex += "[a-z]{0," + after + "}";
		}
		System.out.println(regex);
		tiles.add(new Regex(new Point(x, y), regex, playOff, vert));
	}

	/**
	 * Checks for possible moves going horizontally on the tiles.
	 * 
	 * @param x       x coordinate location on tile grid
	 * @param y       y coordinate location on tile grid
	 * @param playOff letter(s) on game tiles to play off of
	 */
	public void checkLeft(final int x, final int y, String playOff) {
		int up = 0, down = 0;
		final String top = board.getLetterAt(x - 1, y);
		if (top != null) {
			for (int i = 1; i <= (x > 7 ? x : 14 - x); i ++) {
				final String next = board.getLetterAt(x - i, y);
				if (next != null) {
					if (!next.equals(" ")) {
						return;
					} else {
						final String topLeft = board.getLetterAt(x - i, y - 1), bottomLeft = board.getLetterAt(x - i, y + 1), leftLeft = board.getLetterAt(x - i - 1, y);
						if ((topLeft == null || topLeft.equals(" ")) && (bottomLeft == null || bottomLeft.equals(" ")) && (leftLeft == null || leftLeft.equals(" "))) {
							up ++;
						} else {
							break;
						}
					}
				}
			}
			final String bottom = board.getLetterAt(x + 1, y);
			if (bottom != null) {
				for (int i = 1; i <= (x > 7 ? x : 14 - x); i ++) {
					final String next = board.getLetterAt(x + i, y);
					if (next != null) {
						if (!next.equals(" ")) {
							playOff += next;
						} else {
							final String topRight = board.getLetterAt(x + i, y - 1), bottomRight = board.getLetterAt(x + i, y + 1), rightRight = board.getLetterAt(x + i + 1, y);
							if ((topRight == null || topRight.equals(" ")) && (bottomRight == null || bottomRight.equals(" ")) && (rightRight == null || rightRight.equals(" "))) {
								down ++;
							} else {
								break;
							}
						}
					}
				}
			}
			if (up != 0 || down != 0) {
				addBoard(x, y, playOff, up, down, false);
			}
		}
	}

	/**
	 * Checks for possible moves going vertically on the tiles.
	 * 
	 * @param x       x coordinate location on tile grid
	 * @param y       y coordinate location on tile grid
	 * @param playOff letter(s) on game tiles to play off of
	 */
	public void checkTop(final int x, final int y, String playOff) {
		int before = 0, after = 0;
		final String left = board.getLetterAt(x, y - 1);
		if (left != null) {
			for (int i = 1; i <= (y > 7 ? y : 14 - y); i ++) {
				final String next = board.getLetterAt(x, y - i);
				if (next != null) {
					if (!next.equals(" ")) {
						return;
					} else {
						final String topLeft = board.getLetterAt(x - 1, y - i), topRight = board.getLetterAt(x + 1, y - i), topTop = board.getLetterAt(x, y - i - 1);
						if ((topLeft == null || topLeft.equals(" ")) && (topRight == null || topRight.equals(" ")) && (topTop == null || topTop.equals(" "))) {
							before ++;
						} else {
							break;
						}
					}
				}
			}
			final String right = board.getLetterAt(x, y + 1);
			if (right != null) {
				for (int i = 1; i <= (y > 7 ? y : 14 - y); i ++) {
					final String next = board.getLetterAt(x, y + i);
					if (next != null) {
						if (!next.equals(" ")) {
							playOff += next;
						} else {
							final String bottomLeft = board.getLetterAt(x - 1, y + i), bottomRight = board.getLetterAt(x + 1, y + i), bottomBottom = board.getLetterAt(x, y + i + 1);
							if ((bottomLeft == null || bottomLeft.equals(" ")) && (bottomRight == null || bottomRight.equals(" ")) && (bottomBottom == null || bottomBottom.equals(" "))) {
								after ++;
							} else {
								break;
							}
						}
					}
				}
			}
			if (before != 0 || after != 0) {
				addBoard(x, y, playOff, before, after, true);
			}
		}
	}

	/**
	 * Tests for different possible move combinations and builds an ArrayList containing them all. Returns an ArrayList containing Regex entries.
	 *
	 * @return a list of possible moves
	 * @see    java.util.ArrayList
	 */
	public ArrayList<Regex> getValidTiles() {
		tiles.clear();
		for (int x = 0; x < 15; x++) {
			for (int y = 0; y < 15; y++) {
				String playOff = board.getLetterAt(x, y);
				if (!playOff.equals(" ")) {
					checkTop(x, y, playOff);
					checkLeft(x, y, playOff);
				}
			}
		}
		return tiles;
	}
}