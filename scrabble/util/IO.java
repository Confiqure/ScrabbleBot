package scrabble.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import scrabble.game.board.Board;

public class IO {

	public static void saveTiles(Board board, File file, String fileName) {
		if (!file.isDirectory()) {
			file.mkdirs();
		}
		final File saveFile = new File(file, fileName);
		try (final FileWriter writer = new FileWriter(saveFile)) {
			for (int row = 0; row < board.getTiles().length; row ++) {
				for (int column = 0; column < board.getTiles()[row].length; column ++) {
					if (!board.getTiles()[row][column].getText().equals(" ")) {
						writer.write(row + "#" + column + "#" + board.getTiles()[row][column].getText() + ", ");
					}
				}
			}
			writer.close();
		} catch (final IOException ex) {
			System.err.println("Unable to save tiles!");
		}
	}
	
	public static void loadTiles(Board board, File directory, String fileName) {
		final File file = new File(directory, fileName);
    	if (file.exists()) {
    		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
    			String line;
    			while ((line = br.readLine()) != null) {
    				final String[] splits = line.split(", ");
    				for (String inf : splits) {
    					final String[] vars = inf.split("#");
    					board.getTiles()[Integer.parseInt(vars[0])][Integer.parseInt(vars[1])].setText(vars[2]);
    				}
    			}
    			br.close();
    		} catch (final IOException | NumberFormatException e) {
    			System.err.println("Unable to load saved tiles!");
    		}
    	}
	}

}
