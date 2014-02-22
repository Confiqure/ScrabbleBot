package scrabble;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * Class for User Interface
 * 
 * @author MehSki11zOwn
 */
public class UI extends JFrame {

    /**
     * File location for game storage data.
     */
    public static File storage;

    /**
     * Array containing each tile on the grid.
     */
    public JButton[][] tiles = {{new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton()},
            {new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton()},
            {new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton()},
            {new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton()},
            {new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton()},
            {new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton()},
            {new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton()},
            {new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton()},
            {new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton()},
            {new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton()},
            {new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton()},
            {new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton()},
            {new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton()},
            {new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton()},
            {new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton()}};

    /**
     * Constructor.
     */
    public UI() {
        setTitle("ScrabbleBot");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                String storing = "";
                for (int row = 0; row < tiles.length; row ++) {
                    for (int column = 0; column < tiles[row].length; column ++) {
                        if (!tiles[row][column].getText().equals(" ")) {
                            storing += row + "," + column + "," + tiles[row][column].getText() + "\n";
                        }
                    }
                }
                try (final PrintWriter writer = new PrintWriter(storage, "UTF-8")) {
                    writer.print(storing.trim());
                    writer.close();
                } catch (final IOException ex) {
                    System.err.println("Unable to save tiles!");
                }
                dispose();
            }
        });
        setResizable(false);
        final JPanel columnPanel = new JPanel();
        columnPanel.setLayout(new BoxLayout(columnPanel, BoxLayout.Y_AXIS));
        for (int row = 0; row < tiles.length; row ++) {
            final JPanel rowPanel = new JPanel();
            rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));
            for (int column = 0; column < tiles[row].length; column ++) {
                final int rowNum = row, columnNum = column;
                if (getType(row, column) == 4) {
                    tiles[row][column].setBackground(Color.yellow);
                } else if (getType(row, column) == 3) {
                    tiles[row][column].setBackground(Color.green);
                } else if (getType(row, column) == 2) {
                    tiles[row][column].setBackground(Color.red);
                } else if (getType(row, column) == 1) {
                    tiles[row][column].setBackground(Color.blue);
                }
                tiles[row][column].setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
                tiles[row][column].setText(" ");
                tiles[row][column].setFocusable(false);
                tiles[row][column].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(final ActionEvent e) {
                        final String letter = "" + JOptionPane.showInputDialog(null, null, "Enter letter", JOptionPane.INFORMATION_MESSAGE).toUpperCase().toCharArray()[0];
                        tiles[rowNum][columnNum].setText(letter);
                    }
                });
                rowPanel.add(tiles[row][column]);
            }
            columnPanel.add(rowPanel);
        }
        add(columnPanel);
        setSize(681, 552);
        setLocation(Toolkit.getDefaultToolkit().getScreenSize().width - 681 - 3, 5);
        if (storage.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(storage))) {
                String line;
                while ((line = br.readLine()) != null) {
                    final String[] splits = line.split(",");
                    tiles[Integer.parseInt(splits[0])][Integer.parseInt(splits[1])].setText(splits[2]);
                }
                br.close();
            } catch (final IOException | NumberFormatException e) {
                System.err.println("Unable to load saved tiles!");
            }
        }
        setVisible(true);
    }

    /**
     * Returns the letter at the given (x, y) coordinate pair on the tile grid.
     * 
     * @param x x coordinate location on tile grid
     * @param y y coordinate location on tile grid
     * @return  the letter
     */
    public final String getLetter(final int x, final int y) {
        if (x < 0 || x > 14 || y < 0 || y > 14) {
            return null;
        }
        return tiles[y][x].getText().toLowerCase();
    }

    /**
     * Returns an ID corresponding to the tile type.
     * <p>
     * 0 indicates the tile is a Regular Tile<br>
     * 1 indicates that the tile is a Double Letter Tile<br>
     * 2 indicates that the tile is a Double Word Tile<br>
     * 3 indicates that the tile is a Triple Letter Tile<br>
     * 4 indicates that the tile is a Triple Word Tile<br>
     * 
     * @param x x coordinate location on tile grid
     * @param y y coordinate location on tile grid
     * @return  ID of tile type described above
     */
    public final int getType(final int x, final int y) {
        if ((x == 0 && (y == 3 || y == 11)) || (x == 3 && (y == 0 || y == 14)) || (x == 11 && (y == 0 || y == 14)) || (x == 14 && (y == 3 || y == 11))) {
            return 4;
        } else if ((x == 0 && (y == 6 || y == 8)) || (x == 3 && (y == 3 || y == 11)) || (x == 5 && (y == 5 || y == 9)) || (x == 6 && (y == 0 || y == 14)) || (x == 8 && (y == 0 || y == 14)) || (x == 9 && (y == 5 || y == 9)) || (x == 11 && (y == 3 || y == 11)) || (x == 14 && (y == 6 || y == 8))) {
            return 3;
        } else if ((x == 1 && (y == 5 || y == 9)) || (x == 3 && y == 7) || (x == 5 && (y == 1 || y == 13)) || (x == 7 && (y == 3 || y == 11)) || (x == 9 && (y == 1 || y == 13)) || (x == 11 && y == 7) || (x == 13 && (y == 5 || y == 9))) {
            return 2;
        } else if ((x == 1 && (y == 2 || y == 12)) || (x == 2 && (y == 1 || y == 4 || y == 10 || y == 13)) || (x == 4 && (y == 2 || y == 6 || y == 8 || y == 12)) || (x == 6 && (y == 4 || y == 10)) || (x == 8 && (y == 4 || y == 10)) || (x == 10 && (y == 2 || y == 6 || y == 8 || y == 12)) || (x == 12 && (y == 1 || y == 4 || y == 10 || y == 13)) || (x == 13 && (y == 2 || y == 12))) {
            return 1;
        }
        return 0;
    }
}
