package scrabble.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import scrabble.game.scrabbleboard.ScrabbleBoard;

/**
 * @author Robert G
 *
 */
public class IO {

	/**
	 * 
	 * @param board the board to load the tiles to.
	 * @param directory
	 * @param fileName
	 */
	public static void loadTiles(ScrabbleBoard board, File file) {
		if (file.exists() && file.getName().contains(".game")) {
			try (BufferedReader br = new BufferedReader(new FileReader(file))) {
				String line;
				while ((line = br.readLine()) != null) {
					final String[] splits = line.split(", ");
					for (String inf : splits) {
						final String[] vars = inf.split("#");
						board.setLetterAtTile(Integer.parseInt(vars[0]), Integer.parseInt(vars[1]), vars[2].toUpperCase());
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

}
