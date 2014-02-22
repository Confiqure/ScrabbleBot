package scrabble;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * Finds all possible moves
 * 
 * @author MehSki11zOwn
 */
public class MoveFinder {
    private ArrayList<Scrabble.Regex> board = new ArrayList<>();

    /**
     * Tests for different possible move combinations and builds an ArrayList containing them all. Returns an ArrayList containing Scrabble.Regex entries.
     *
     * @return a list of possible moves
     * @see    java.util.ArrayList
     */
    public ArrayList<Scrabble.Regex> getValidTiles() {
        board = new ArrayList<>();
        for (int x = 0; x < 15; x ++) {
            for (int y = 0; y < 15; y ++) {
                String playOff = Scrabble.ui.getLetter(x, y);
                if (!playOff.equals(" ")) {
                    checkTop(x, y, playOff);
                    checkLeft(x, y, playOff);
                }
            }
        }
        System.out.println("Regexes: " + board.size());
        return board;
    }

    /**
     * Checks for possible moves going vertically on the board.
     * 
     * @param x       x coordinate location on tile grid
     * @param y       y coordinate location on tile grid
     * @param playOff letter(s) on game board to play off of
     */
    public void checkTop(final int x, final int y, String playOff) {
        int before = 0, after = 0;
        final String left = Scrabble.ui.getLetter(x, y - 1);
        if (left != null) {
            for (int i = 1; i <= (y > 7 ? y : 14 - y); i ++) {
                final String next = Scrabble.ui.getLetter(x, y - i);
                if (next != null) {
                    if (!next.equals(" ")) {
                        return;
                    } else {
                        final String topLeft = Scrabble.ui.getLetter(x - 1, y - i), topRight = Scrabble.ui.getLetter(x + 1, y - i), topTop = Scrabble.ui.getLetter(x, y - i - 1);
                        if ((topLeft == null || topLeft.equals(" ")) && (topRight == null || topRight.equals(" ")) && (topTop == null || topTop.equals(" "))) {
                            before ++;
                        } else {
                            break;
                        }
                    }
                }
            }
            final String right = Scrabble.ui.getLetter(x, y + 1);
            if (right != null) {
                for (int i = 1; i <= (y > 7 ? y : 14 - y); i ++) {
                    final String next = Scrabble.ui.getLetter(x, y + i);
                    if (next != null) {
                        if (!next.equals(" ")) {
                            playOff += next;
                        } else {
                            final String bottomLeft = Scrabble.ui.getLetter(x - 1, y + i), bottomRight = Scrabble.ui.getLetter(x + 1, y + i), bottomBottom = Scrabble.ui.getLetter(x, y + i + 1);
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
     * Checks for possible moves going horizontally on the board.
     * 
     * @param x       x coordinate location on tile grid
     * @param y       y coordinate location on tile grid
     * @param playOff letter(s) on game board to play off of
     */
    public void checkLeft(final int x, final int y, String playOff) {
        int left = 0, right = 0;
        final String top = Scrabble.ui.getLetter(x - 1, y);
        if (top != null) {
            for (int i = 1; i <= (x > 7 ? x : 14 - x); i ++) {
                final String next = Scrabble.ui.getLetter(x - i, y);
                if (next != null) {
                    if (!next.equals(" ")) {
                        return;
                    } else {
                        final String topLeft = Scrabble.ui.getLetter(x - i, y - 1), bottomLeft = Scrabble.ui.getLetter(x - i, y + 1), leftLeft = Scrabble.ui.getLetter(x - i - 1, y);
                        if ((topLeft == null || topLeft.equals(" ")) && (bottomLeft == null || bottomLeft.equals(" ")) && (leftLeft == null || leftLeft.equals(" "))) {
                            left ++;
                        } else {
                            break;
                        }
                    }
                }
            }
            final String bottom = Scrabble.ui.getLetter(x + 1, y);
            if (bottom != null) {
                for (int i = 1; i <= (x > 7 ? x : 14 - x); i ++) {
                    final String next = Scrabble.ui.getLetter(x + i, y);
                    if (next != null) {
                        if (!next.equals(" ")) {
                            playOff += next;
                        } else {
                            final String topRight = Scrabble.ui.getLetter(x + i, y - 1), bottomRight = Scrabble.ui.getLetter(x + i, y + 1), rightRight = Scrabble.ui.getLetter(x + i + 1, y);
                            if ((topRight == null || topRight.equals(" ")) && (bottomRight == null || bottomRight.equals(" ")) && (rightRight == null || rightRight.equals(" "))) {
                                right ++;
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
            if (left != 0 || right != 0) {
                addBoard(x, y, playOff, left, right, false);
            }
        }
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
        board.add(new Scrabble.Regex(new Point(x, y), regex, playOff, vert));
    }
}
