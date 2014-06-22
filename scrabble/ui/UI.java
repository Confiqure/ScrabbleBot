package scrabble.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
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
	 * JButton The bestMove button that will be attached to the menu at the top of this user interface.
	 * When the user clicks this component an event will be triggered and the next best available move will be calculated.
	 */
	private final JButton bestMove = new JButton("Best move");
	/**
	 * JButton The tilesInHand button that will be attached to the menu at the top of this user interface.
	 * When the user clicks this component an event will be triggered requesting the user to input the tiles they currently have in hand.
	 */
	private final JButton tilesInHand = new JButton("Change Tiles");
	/**
	 * JButton The loadGame button that will be attached to the menu at the top of this user interface.
	 * When the user clicks this component an event will be triggered requesting the user to select a file to load
	 * a previous game from.
	 */
	private final JButton loadGame = new JButton("Load Game");
	/**
	 * JButton The saveGame button that will be attached to the menu at the top of this user interface.
	 * When the user clicks this component an event will be triggered requesting the user to select a location
	 * to save the current game to.
	 */
	private final JButton saveGame = new JButton("Save Game");
	/**
	 * JButton The clear board button that will be attached to the menu at the top of this user interface.
	 */
	private final JButton newGame = new JButton("New Game");
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
		menuBar.setLayout(new GridLayout(1, 5));
		bestMove.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				getBestMove();
			}
			
		});
		menuBar.add(bestMove);
		tilesInHand.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				requestLettersInHand();
			}

		});
		menuBar.add(tilesInHand);
		newGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				game.getBoard().clear();
			}
			
		});
		menuBar.add(newGame);
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
		menuBar.add(loadGame);
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
		menuBar.add(saveGame);
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
		if (letters == null || letters.isEmpty()) {
			requestLettersInHand();
		}
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
