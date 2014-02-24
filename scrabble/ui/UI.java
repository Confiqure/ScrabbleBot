package scrabble.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import scrabble.game.Game;
import scrabble.util.IO;

/**
* User Interface for ScrabbleBot
* 
* @author MehSki11zOwn, Robert-G
*/
public class UI extends JFrame {

    private static final long serialVersionUID = 1L;

    private final JMenuBar menuBar = new JMenuBar();
    private final JMenu file = new JMenu("File"), options = new JMenu("Options");
    private final JMenuItem loadGame = new JMenuItem("Load game"), saveGame = new JMenuItem("Save game"), bestMove = new JMenuItem("Calculate best move");
    private final JFileChooser fileChooser = new JFileChooser();
    private final Game game = new Game();
    private String loadedGame = "New";
    private boolean saved = true;

    /**
     * Constructs a new UI.
     */
    public UI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ignored) {}
        setSaved(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                if (!saved) {
                    saveGame();
                }
            }
        });
        loadGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (e.getSource() == loadGame) {
                    final int returnVal = fileChooser.showDialog(UI.this, "Load");
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        final File file = fileChooser.getSelectedFile();
                        IO.loadTiles(game.getBoard(), file);
                        loadedGame = file.getName().substring(0, file.getName().indexOf(".") == -1 ? file.getName().length() : file.getName().indexOf("."));
                        setSaved(true);
                    }
                }
            }
        });
        file.add(loadGame);
        saveGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (e.getSource() == saveGame) {
                    saveGame();
                }
            }
        });
        file.add(saveGame);
        menuBar.add(file);
        bestMove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                final String letters = JOptionPane.showInputDialog("Enter letters currently in hand.");
                if (letters == null || !Pattern.matches("[a-zA-Z]+", letters)) {
                    JOptionPane.showMessageDialog(UI.this, "Invalid input received.", "Warning!", JOptionPane.WARNING_MESSAGE);
                }
                game.getBestMove(letters);
            }
        });
        options.add(bestMove);
        menuBar.add(options);
        setJMenuBar(menuBar);
        add(game.getBoard());
        pack();
        setLocationRelativeTo(getOwner());
        setResizable(false);
        setVisible(true);
    }

    /**
     * Sets the Game saved state.
     * 
     * @param savedState Game saved state
     */
    public final void setSaved(final boolean savedState) {
        saved = savedState;
        setTitle("ScrabbleBot - " + loadedGame + (saved ? "" : " *"));
    }

    private void saveGame() {
        final int returnVal = fileChooser.showDialog(UI.this, "Save");
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            final File selected = fileChooser.getSelectedFile();
            IO.saveTiles(game.getBoard(), selected);
            loadedGame = selected.getName().substring(0, selected.getName().indexOf(".") == -1 ? selected.getName().length() : selected.getName().indexOf("."));
            setSaved(true);
        }
    }

}
