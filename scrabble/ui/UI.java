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
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

import scrabble.game.Game;
import scrabble.ui.charlimitdocument.CharLimitDocument;
import scrabble.util.IO;

public class UI extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * JMenuBar The menu bar that will appear at the top of the user interface.
	 */
	private final JMenuBar menuBar = new JMenuBar();
	/**
	 * JMenu The options menu that will be attached to the JMenuBar at the top of this user interface.
	 */
	private final JMenu options = new JMenu("Options");
	/**
	 * JMenuItem The bestMove menu item that will be attached to the options menu at the top of this user interface.
	 * When the user selects this component an event will be triggered and the next best available move will be calculated.
	 */
	private final JMenuItem bestMove = new JMenuItem("Get best move");
	/**
	 * JMenuItem The tilesInHand menu item that will be attached to the options menu at the top of this user interface.
	 * When the user selects this component an event will be triggered requesting the user to input the tiles they currently have in hand.
	 */
	private final JMenuItem tilesInHand = new JMenuItem("Change tiles in hand");
	/**
	 * JMenu The file menu that will be attached to the JMenuBar at the top of this user interface.
	 */
	private final JMenu file = new JMenu("File");
	/**
	 * JMenuItem The loadGame menu item that will be attached to the file menu at the top of this user interface.
	 * When the user selects this component an event will be triggered requesting the user to select a file to load
	 * a previous game from.
	 */
	private final JMenuItem loadGame = new JMenuItem("Load game");
	/**
	 * JMenuItem The saveGame menu item that will be attached to the file menu at the top of this user interface.
	 * When the user selects this component an event will be triggered requesting the user to select a location
	 * to save the current game to..
	 */
	private final JMenuItem saveGame = new JMenuItem("Save game");
	/**
	 * JFileChooser The fileChooser this user interface uses to obtain file location information from the user.
	 */
	private final JFileChooser fileChooser = new JFileChooser();
	/**
	 * FileNameExtensionFilter the fileExtensionFilter for the fileChooser.
	 * Used to make sure a user doesn't try to load a non .game file.
	 */
	private final FileNameExtensionFilter fileExtensionFilter = new FileNameExtensionFilter("saved .game files", "game");
	/**
	 * Game the game.
	 */
	private final Game game = new Game();
	/**
	 * String the current letters in hand.
	 */
	private String letters;

	/**
	 * Constructs a new UI.
	 */
	public UI() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ignored) {}
		setTitle("ScrabbleBot");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bestMove.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				getBestMove();
			}
			
		});
		options.add(bestMove);
		tilesInHand.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				requestLettersInHand();
			}

		});
		options.add(tilesInHand);
		menuBar.add(options);
		fileChooser.setFileHidingEnabled(true);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setFileFilter(fileExtensionFilter);
		fileChooser.setAcceptAllFileFilterUsed(false);
		loadGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == loadGame) {
			        final int returnVal = fileChooser.showDialog(UI.this, "Load");
			        if (returnVal == JFileChooser.APPROVE_OPTION) {
			            final File file = fileChooser.getSelectedFile();
			            if (fileChooser.accept(file)) {
			            	IO.loadTiles(game.getBoard(), file);
			            }
			        }
			   } 
			}
			
		});
		file.add(loadGame);
		saveGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == saveGame) {
					if (game.getBoard().hasValidContent()) {
						saveGame();
					} else {
						JOptionPane.showMessageDialog(null, "There is nothing to save!", "Warning!", JOptionPane.WARNING_MESSAGE);
					}
			   } 
			}
			
		});
		file.add(saveGame);
		menuBar.add(file);
		setJMenuBar(menuBar);
		add(game.getBoard());
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (game.getBoard().hasValidContent()) {
					saveGame();
				}
			}
		});
		pack();
		setLocationRelativeTo(getOwner());
		setResizable(false);
		setVisible(true);
	}
	
	/**
	 * Opens a dialogue and prompts the user to select a file to save the current game to.
	 */
	private void saveGame() {
		final int returnVal = fileChooser.showDialog(this, "Save");
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            IO.saveTiles(game.getBoard(), fileChooser.getSelectedFile());
        }
	}

	/**
	 *  @see also scrabble.game.Game.getBestMove(String letters)
	 */
	private void getBestMove() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				game.getBestMove(UI.this, letters);
			}
		});
	}
	
	/**
	 * Displays an input dialogue prompting the user to enter the letters they currently have in hand.
	 */
	private void requestLettersInHand() {
		final JTextField input = new JTextField(new CharLimitDocument(7), letters, 7);
		final Object[] info = { "Enter tiles currently in hand.", input	};
		final int returnVal = JOptionPane.showConfirmDialog(this, info, "Input!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
		if (returnVal == JOptionPane.OK_OPTION) {
			final String enteredVal = input.getText().toUpperCase();
			if (Pattern.matches("[a-zA-Z]+", enteredVal)) {
				letters = enteredVal;
			} else {
				JOptionPane.showMessageDialog(null, "Invalid character entered.", "Warning!", JOptionPane.WARNING_MESSAGE);
				requestLettersInHand();
			}
		}
	}

}
