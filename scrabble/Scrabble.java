package scrabble;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Scrabble {
    private static String letters;
    private static File storage;
    private static ArrayList<Regex> board = new ArrayList<>();
    private static final Map<String, Integer> pointKey = new HashMap<>();
    private static UI ui;

    public static void main(final String[] args) {
        storage = new File(System.getProperty("user.home") + File.separator + "ScrabbleBot" + File.separator + args[0] + ".txt");
        letters = args[1];
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (final ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ignored) {}
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
        ui = new UI();
        getValidTiles();
        getBestMove();
    }

    private static void getBestMove() {
        Regex reg = new Regex(null, null, null, false);
        String newWord = "";
        int highest = 0, words = 0;
        for (final Regex regex : board) {
            for (final String word : getWords(letters + regex.playOff)) {
                words ++;
                final int points = getPoints(regex, word);
                if (word.matches(regex.regex) && !word.equals(regex.playOff) && points > highest) {
                    reg = regex;
                    newWord = word;
                    highest = points;
                }
            }
        }
        System.out.println("Scanned " + words + " words");
        System.out.println(reg.start + "\t" + newWord + "\t" + highest + "\t" + reg.regex);
    }

    private static void getValidTiles() {
        board = new ArrayList<>();
        for (int x = 0; x < 15; x ++) {
            for (int y = 0; y < 15; y ++) {
                String playOff = ui.getLetter(x, y);
                if (!playOff.equals(" ")) {
                    checkTop(x, y, playOff);
                    checkLeft(x, y, playOff);
                }
            }
        }
        System.out.println("Regexes: " + board.size());
    }

    private static void checkTop(final int x, final int y, String playOff) {
        int before = 0, after = 0;
        final String left = ui.getLetter(x, y - 1);
        if (left != null) {
            for (int i = 1; i <= (y > 7 ? y : 14 - y); i ++) {
                final String next = ui.getLetter(x, y - i);
                if (next != null) {
                    if (!next.equals(" ")) {
                        return;
                    } else {
                        final String topLeft = ui.getLetter(x - 1, y - i), topRight = ui.getLetter(x + 1, y - i), topTop = ui.getLetter(x, y - i - 1);
                        if ((topLeft == null || topLeft.equals(" ")) && (topRight == null || topRight.equals(" ")) && (topTop == null || topTop.equals(" "))) {
                            before ++;
                        } else {
                            break;
                        }
                    }
                }
            }
            final String right = ui.getLetter(x, y + 1);
            if (right != null) {
                for (int i = 1; i <= (y > 7 ? y : 14 - y); i ++) {
                    final String next = ui.getLetter(x, y + i);
                    if (next != null) {
                        if (!next.equals(" ")) {
                            playOff += next;
                        } else {
                            final String bottomLeft = ui.getLetter(x - 1, y + i), bottomRight = ui.getLetter(x + 1, y + i), bottomBottom = ui.getLetter(x, y + i + 1);
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

    private static void checkLeft(final int x, final int y, String playOff) {
        int up = 0, down = 0;
        final String top = ui.getLetter(x - 1, y);
        if (top != null) {
            for (int i = 1; i <= (x > 7 ? x : 14 - x); i ++) {
                final String next = ui.getLetter(x - i, y);
                if (next != null) {
                    if (!next.equals(" ")) {
                        return;
                    } else {
                        final String topLeft = ui.getLetter(x - i, y - 1), bottomLeft = ui.getLetter(x - i, y + 1), leftLeft = ui.getLetter(x - i - 1, y);
                        if ((topLeft == null || topLeft.equals(" ")) && (bottomLeft == null || bottomLeft.equals(" ")) && (leftLeft == null || leftLeft.equals(" "))) {
                            up ++;
                        } else {
                            break;
                        }
                    }
                }
            }
            final String bottom = ui.getLetter(x + 1, y);
            if (bottom != null) {
                for (int i = 1; i <= (x > 7 ? x : 14 - x); i ++) {
                    final String next = ui.getLetter(x + i, y);
                    if (next != null) {
                        if (!next.equals(" ")) {
                            playOff += next;
                        } else {
                            final String topRight = ui.getLetter(x + i, y - 1), bottomRight = ui.getLetter(x + i, y + 1), rightRight = ui.getLetter(x + i + 1, y);
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

    private static void addBoard(final int x, final int y, final String playOff, final int before, final int after, final boolean vert) {
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
        board.add(new Regex(new Point(x, y), regex, playOff, vert));
    }

    private static ArrayList<String> getWords(final String chars) {
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

    private static int getPoints(final Regex r, final String word) {
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
            switch (ui.getType(test.x, test.y)) {
                case 0:
                    total += pointKey.get(word.toCharArray()[i] + "");
                    break;
                case 1:
                    total += pointKey.get(word.toCharArray()[i] + "") * 2;
                    break;
                case 2:
                    total += pointKey.get(word.toCharArray()[i] + "");
                    multiplier *= 2;
                    break;
                case 3:
                    total += pointKey.get(word.toCharArray()[i] + "") * 3;
                    break;
                case 4:
                    total += pointKey.get(word.toCharArray()[i] + "");
                    multiplier *= 3;
                    break;
            }
        }
        return total * multiplier;
    }

    private static class Regex {
        private Point start;
        private String regex, playOff;
        private boolean vert;

        public Regex(final Point start, final String regex, final String playOff, final boolean vert) {
            this.start = start;
            this.regex = regex;
            this.playOff = playOff;
            this.vert = vert;
        }
    }

    private static class UI extends JFrame {
        private JButton[][] tiles = {{new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton(), new JButton()},
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

        public final String getLetter(final int x, final int y) {
            if (x < 0 || x > 14 || y < 0 || y > 14) {
                return null;
            }
            return tiles[y][x].getText().toLowerCase();
        }

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
}