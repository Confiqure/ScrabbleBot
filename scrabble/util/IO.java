package scrabble.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;

import scrabble.game.scrabbleboard.ScrabbleBoard;

/**
 * @author Robert G
 *
 */
public class IO {

	private static final String user_agent = "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; .NET CLR 1.0.3705; .NET CLR 1.1.4322; .NET CLR 1.2.30703; H010818)";
	private static final ArrayList<String> wordCache = new ArrayList<String>(); 
	private static String lastChars = "";
	
	/**
	 * 
	 * @param board the board to load the tiles to.
	 * @param directory
	 * @param fileName
	 */
	public static void loadTiles(ScrabbleBoard board, File file) {
		board.clear();
		if (file.exists() && file.getName().contains(".game")) {
			try (BufferedReader br = new BufferedReader(new FileReader(file))) {
				String line;
				while ((line = br.readLine()) != null) {
					final String[] split = line.split(", ");
					for (String inf : split) {
						final String[] vars = inf.split("#");
						board.getTileAt(Integer.parseInt(vars[0]), Integer.parseInt(vars[1])).setLetter(vars[2].toUpperCase().charAt(0));
					}
				}
				br.close();
			} catch (final IOException | NumberFormatException e) {
				System.err.println("Unable to load saved tiles!");
			}
		}
	}

	/**
	 * 
	 * @param board the board to save the tiles from.
	 * @param file
	 * @param fileName
	 */
	public static void saveTiles(ScrabbleBoard board, File file) {
		try {
			if (!file.exists()) {
        		file = new File(file.getParentFile(), (file.getName() + ".game"));
        		file.createNewFile();
        	}
			final FileWriter writer = new FileWriter(file);
			for (int row = 0; row < board.getAllTiles().length; row ++) {
				for (int column = 0; column < board.getAllTiles()[row].length; column ++) {
					final String letter = board.getLetterAt(row, column);
					if (letter != null && !letter.equals(" ")) {
						writer.write(row + "#" + column + "#" + letter + ", ");
					}
				}
			}
			writer.close();
		} catch (final IOException ex) {
			System.err.println("Unable to save tiles!");
		}
	}
	
	/**
	 * Returns a list of possible words that can be formed using the characters inputted.
	 * 
	 * @param chars a String containing each character to use
	 * @return      a list of possible words
	 * @see         java.util.ArrayList
	 */
	public static ArrayList<String> getWords(final String chars) {
		if (!chars.equalsIgnoreCase(lastChars)) {
			wordCache.clear();
			lastChars = chars;
			String total = "";
			try {
				final URLConnection spoof = new URL("http://wordfinder.yourdictionary.com/unscramble/" + chars + "?remember_tiles=false").openConnection();
				spoof.setRequestProperty("User-Agent", user_agent);
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
						wordCache.add(row.substring(row.indexOf(">") + 1, row.startsWith("<td>") ? row.indexOf("</td>") : row.indexOf("</a>")));
					}
				}
			}
			Collections.sort(wordCache);
		}
		return wordCache;
	}

}
