package scrabble.game;

import java.awt.Component;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import scrabble.game.movefinder.MoveFinder;
import scrabble.game.scrabbleboard.ScrabbleBoard;
import scrabble.game.tiletype.TileType;
import scrabble.game.wrappers.Regex;
import scrabble.game.wrappers.Tile;

/**
 * @author MehSki11zOwn
 *
 */
public class Game {

	/**
	 * Map mapping each letter in the English alphabet to a specific point value.
	 */
	private final Map<String, Integer> pointKey = new HashMap<>();
	
	/**
	 * ScrabbleBoard the board displayed by the user interface.
	 */
	private final ScrabbleBoard board = new ScrabbleBoard();
	
	/**
	 * 
	 */
	private final MoveFinder moveFinder = new MoveFinder(board);

	/**
	 * Constructs a new game.
	 */
	public Game() {
		pointKey.put("a", 1);
		pointKey.put("b", 4);
		pointKey.put("c", 4);
		pointKey.put("d", 2);
		pointKey.put("e", 1);
		pointKey.put("f", 4);
		pointKey.put("g", 3);
		pointKey.put("h", 3);
		pointKey.put("i", 1);
		pointKey.put("j", 10);
		pointKey.put("k", 5);
		pointKey.put("l", 2);
		pointKey.put("m", 4);
		pointKey.put("n", 2);
		pointKey.put("o", 1);
		pointKey.put("p", 4);
		pointKey.put("q", 10);
		pointKey.put("r", 1);
		pointKey.put("s", 1);
		pointKey.put("t", 1);
		pointKey.put("u", 2);
		pointKey.put("v", 5);
		pointKey.put("w", 4);
		pointKey.put("x", 8);
		pointKey.put("y", 3);
		pointKey.put("z", 10);
	}

	/**
	 * Scans each possible move and loops through all the possible words that can be formed and prints highest scoring move.
	 */
	public void getBestMove(Component parent, String tilesInHand) {
		if (!board.hasValidContent()) {
			return;
		}
		Regex reg = new Regex(null, null, null, false);
		String newWord = "";
		int highest = 0;
		for (final Regex regex : moveFinder.getValidTiles()) {
			for (final String word : getWords(tilesInHand + regex.playOff)) {
				final int points = getPoints(regex, word);
				if (word.matches(regex.regex) && !word.equals(regex.playOff) && points > highest) {
					reg = regex;
					newWord = word;
					highest = points;
				}
			}
		}
		final int option = JOptionPane.showConfirmDialog(parent, "Do you want to add " + newWord + " to the board?",
				"Move found.", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
		if (option == JOptionPane.OK_OPTION) {
			final String startLetter = board.getLetterAt(reg.start.x, reg.start.y);
			final char[] chars = newWord.toCharArray();
			Point startPoint = null;
			if (!reg.vert) {
				for (int x = 0; x < chars.length; x++) {
					if (String.valueOf(chars[x]).equals(startLetter)) {
						startPoint = new Point(reg.start.x - x, reg.start.y);
					}
				}
			} else {
				for (int y = 0; y < chars.length; y++) {
					if (String.valueOf(chars[y]).equals(startLetter)) {
						startPoint = new Point(reg.start.x, reg.start.y - y);
					}
				}
			}
			Tile tile;
			for (int i = 0; i < chars.length; i++) {
				tile = !reg.vert ? board.getTileAt(startPoint.x + i, startPoint.y) : board.getTileAt(startPoint.x, startPoint.y + i);
				tile.setText(String.valueOf(chars[i]).toUpperCase());
			}
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
	 * Calculates the amount of points a specific word would yield when played in a specific position on the board.
	 * 
	 * @param r    the Regex to scan
	 * @param word the word to calculate points from
	 * @return     the amount of points the word yields
	 * @see        Regex
	 */
	public int getPoints(final Regex r, final String word) {
		final ArrayList<Point> used = new ArrayList<>();
		final char[] usedStr = r.playOff.toCharArray();
		for (int i = 0; i < usedStr.length; i ++) {
			used.add(new Point(r.start.x + (r.vert ? 0 : i), r.start.y + (r.vert ? i : 0)));
		}
		int x, y, multiplier = 1, total = 0;
		x = r.start.x - (r.vert ? 0 : word.indexOf(r.playOff));
		y = r.start.y - (r.vert ? word.indexOf(r.playOff) : 0);
		final char[] array = word.toCharArray();
		for (int i = 0; i < array.length; i ++) {
			final Point test = new Point(x + (r.vert ? 0 : i), y + (r.vert ? i : 0));
			int changed = 0;
			for (final Point p : used) {
				if (p.x == test.x && p.y == test.y) {
					changed = pointKey.get(word.toCharArray()[i] + "");
					break;
				}
			}
			if (changed != 0) {
				total += changed;
				continue;
			}
			switch (TileType.getTileType(test.x, test.y)) {
			case DOUBLE_LETTER:
				total += pointKey.get(word.toCharArray()[i] + "") * 2;
				break;
			case DOUBLE_WORD:
				total += pointKey.get(word.toCharArray()[i] + "");
				multiplier *= 2;
				break;
			case TRIPLE_LETTER:
				total += pointKey.get(word.toCharArray()[i] + "") * 3;
				break;
			case TRIPLE_WORD:
				total += pointKey.get(word.toCharArray()[i] + "");
				multiplier *= 3;
				break;
			default:
				total += pointKey.get(word.toCharArray()[i] + "");
				break;

			}
		}
		return total * multiplier;
	}

	/**
	 * Returns a list of possible words that can be formed using the characters inputted.
	 * 
	 * @param chars a String containing each character to use
	 * @return      a list of possible words
	 * @see         java.util.ArrayList
	 */
	public ArrayList<String> getWords(final String chars) {
		final ArrayList<String> words = new ArrayList<>();
		String total = "";
		try {
			final URLConnection spoof = new URL("http://wordfinder.yourdictionary.com/unscramble/" + chars + "?remember_tiles=false").openConnection();
			spoof.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.5; Windows NT 5.0;    H010818)");
			try (final BufferedReader in = new BufferedReader(new InputStreamReader(spoof.getInputStream()))) {
				String str;
				while ((str = in.readLine()) != null) {
					total += str;
				}
				in.close();
			}
		} catch (final IOException ex) {}
		total = total.substring(total.indexOf("<hr>") + 4, total.indexOf("</section>", total.indexOf("<hr>")));
		for (final String table : total.split("<a name=\"[0-9]\">")) {
			if (!table.startsWith("</a>")) {
				continue;
			}
			for (final String line : table.split("<a href=")) {
				if (!line.startsWith("'http://www.yourdictionary.com/")) {
					continue;
				}
				for (final String row : line.split("<tr>")) {
					if (row.equals("<td>")) {
						continue;
					}
					words.add(row.substring(row.indexOf(">") + 1, row.startsWith("<td>") ? row.indexOf("</td>") : row.indexOf("</a>")));
				}
			}
		}
		return words;
	}

}
