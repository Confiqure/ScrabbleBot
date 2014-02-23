package scrabble.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import scrabble.game.board.Board;

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
	public static void loadTiles(Board board, File file) {
		if (file.exists()) {
			try (BufferedReader br = new BufferedReader(new FileReader(file))) {
				String line;
				while ((line = br.readLine()) != null) {
					final String[] splits = line.split(", ");
					for (String inf : splits) {
						final String[] vars = inf.split("#");
						board.setTile(Integer.parseInt(vars[0]), Integer.parseInt(vars[1]), vars[2]);
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
	public static void saveTiles(Board board, File file) {
		if (!file.isDirectory()) {
			file.mkdirs();
		}
		try (final FileWriter writer = new FileWriter(file)) {
			for (int row = 0; row < board.getTiles().length; row ++) {
				for (int column = 0; column < board.getTiles()[row].length; column ++) {
					final String letter = board.getLetter(row, column);
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
