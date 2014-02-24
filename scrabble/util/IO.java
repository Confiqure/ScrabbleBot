package scrabble.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import scrabble.game.Board;

/**
 * Loads and saves data from disk.
 * 
 * @author Robert-G
 */
public class IO {

    /**
     * Loads Tile information.
     * 
     * @param board the board to load data from
     * @param file  the file path
     */
    public static void loadTiles(final Board board, final File file) {
        if (file.exists()) {
            try (final BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    final String[] splits = line.split(", ");
                    for (final String inf : splits) {
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
     * Saves Tile information.
     * 
     * @param board the board to save data to
     * @param file  the file path
     */
    public static void saveTiles(final Board board, final File file) {
        if (!file.isDirectory()) {
            file.mkdirs();
        }
        try (final FileWriter writer = new FileWriter(file)) {
            for (int row = 0; row < board.getTiles().length; row ++) {
                for (int column = 0; column < board.getTiles()[row].length; column ++) {
                    final String letter = board.getLetter(column, row);
                    if (letter != null && !letter.equals(" ")) {
                        writer.write(column + "#" + row + "#" + letter + ", ");
                    }
                }
            }
            writer.close();
        } catch (final IOException ex) {
            System.err.println("Unable to save tiles!");
        }
    }

}
