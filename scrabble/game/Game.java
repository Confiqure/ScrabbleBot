package scrabble.game;

import java.awt.Cursor;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import scrabble.game.movefinder.MoveFinder;
import scrabble.game.scrabbleboard.ScrabbleBoard;
import scrabble.game.wrappers.Regex;
import scrabble.game.wrappers.Tile;
import scrabble.ui.UI;
import scrabble.util.IO;

/**
 * @author MehSki11zOwn
 *
 */
public class Game {
	
	/**
	 *An enum containing every letter of the English alphabet, eash assigned a point value.
	 */
	public enum Letter {
		
		A(1), B(4), C(4), D(2), E(1),
		F(4), G(3), H(3), I(1), J(10),
		K(5), L(2), M(4), N(2), O(1),
		P(4), Q(10), R(1), S(1), T(1),
		U(2), V(5), W(4), X(8), Y(3), 
		Z(10);
		
		private final int value;
		
		private Letter(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
		
	}
	
	public static Letter getLetter(char character) {
		for (Letter letter : Letter.values()) {
			if (letter.name().equalsIgnoreCase(String.valueOf(character))) {
				return letter;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param character
	 * @return the value of the specified char.
	 */
	public static int getLetterValue(char character) {
		final Letter letter = getLetter(character);
		return letter == null ? 1 : letter.getValue();
	}
	
	/**
	 * 
	 * @param character
	 * @return the value of the specified String.
	 */
	public static int getLetterValue(String character) {
		return getLetterValue(character.charAt(0));
	}
	
	/**
	 * ScrabbleBoard the board displayed by the user interface.
	 */
	private final ScrabbleBoard board;

	/**
	 * MoveFinder the movefinder that will find the bext available move on the board.
	 */
	private final MoveFinder moveFinder;

	/**
	 * Constructs a new game.
	 */
	public Game() {
		board = new ScrabbleBoard();
		moveFinder = new MoveFinder(board);
	}
	
	/**
	 * Scans each possible move and loops through all the possible words that can be formed and prints highest scoring move.
	 */
	public void getBestMove(UI parent, String tilesInHand) {
		parent.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		parent.setTitle("ScrabbleBot, Finding best move...");
		ScrabbleMove bestMove = null;
		for (final Regex regex : moveFinder.getValidTiles()) {
			for (final String word : IO.getWords(tilesInHand + regex.playOff)) {
				if (word.matches(regex.regex) && !word.equals(regex.playOff)) {
					final ScrabbleMove move = getScrabbleMove(regex, word);
					if (move != null) {
						if (bestMove == null || move.wordScore > bestMove.wordScore) {
							System.out.println("Better move found: " + move);
							bestMove = move;
						}
					}
				}
			}
		}
		parent.setCursor(Cursor.getDefaultCursor());
		parent.setTitle("ScrabbleBot");
		if (bestMove != null) {
			System.out.println("Best move: " + bestMove);
			final String newWord = bestMove.word.substring(0, 1).toUpperCase() + bestMove.word.substring(1).toLowerCase();
			final int option = JOptionPane.showConfirmDialog(parent, "Do you want to add \"" + newWord + "\" to the board?",
					"Move found: " + newWord + ", which scores: " + bestMove.wordScore + ".", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
			if (option == JOptionPane.OK_OPTION) {
				final char[] chars = bestMove.word.toCharArray();
				for (int i = 0; i < chars.length && i < bestMove.tiles.size(); i++) {
					bestMove.tiles.get(i).setLetter(chars[i]);
				}
			}
		} else {
			JOptionPane.showConfirmDialog(parent, "No moves found.", "Warning.", JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE);
		}
	}
	
	/**
	 * 
	 * @return board.
	 */
	public ScrabbleBoard getBoard() {
		return this.board;
	}
	
	/**
	 * 
	 * @param r
	 * @param word
	 * @return
	 */
	private ScrabbleMove getScrabbleMove(final Regex r, final String word) {
		final ScrabbleMove move = new ScrabbleMove();
		final ArrayList<Integer> wordBonuses = new ArrayList<Integer>();
		final char[] chars = word.toCharArray();
		final int wordLength = word.length();
		int playoffX = r.start.x, playoffY = r.start.y;
		final String playoff = board.getLetterAt(playoffX, playoffY).toLowerCase();
		for (int i = 0; i < chars.length; i++) {
			if (String.valueOf(chars[i]).equalsIgnoreCase(playoff)) {
				/**
				 * Needs a lot of work as somtimes returns wrong position resulting in unplayable word being place.
				 * Only happens in words where the playoff letter appears more than once.
				 */
				playoffY = r.vert ? playoffY - i : playoffY;
				playoffX = r.vert ? playoffX : playoffX - i;
				if (!r.vert && playoffX + wordLength > board.width() || r.vert && playoffY + wordLength >= board.height()) {
					continue;
				}
				break;
			}
		}
		if (!r.vert && playoffX + wordLength > board.width() || r.vert && playoffY + wordLength > board.height()) {
			//word doesn't fit on the board from playoff position.
			//previously calculated playoff position is wrong.
			return null;
		}
		move.word = word;
		for (int i = 0; i < wordLength; i++) {
			final Tile tile = board.getTileAt(r.vert ? playoffX : playoffX + i, r.vert ? playoffY + i : playoffY);
			move.tiles.add(tile);
			if (tile.getLetterValue() > 0) {
				move.wordScore += tile.getLetterValue();
				continue;
			} else {
				move.wordScore += getLetterValue(chars[i]) * tile.getLetterBonus();
				wordBonuses.add(tile.getWordBonus());
			}
		}
		for (int bonus : wordBonuses) {
			move.wordScore = move.wordScore * bonus;
		}
		return move;
	}
	
	private class ScrabbleMove {
		
		final ArrayList<Tile> tiles = new ArrayList<Tile>();
		String word;
		int wordScore;
		
		@Override
		public String toString() {
			String info = word + ", score["+wordScore+"] tiles to play on[";
			for (Tile t : tiles) {
				info+= t.getLocation();
			}
			info +="]";
			return info;
		}
		
	}
	
}
