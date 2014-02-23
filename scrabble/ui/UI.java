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
import scrabble.game.board.Board;
import scrabble.util.IO;

public class UI extends JFrame {

	private static final long serialVersionUID = 1L;

	private final JMenuBar menuBar = new JMenuBar();
	private final JMenu options = new JMenu("Options");
	private final JMenuItem bestMove = new JMenuItem("Get best move");
	private final JMenuItem tilesInHand = new JMenuItem("Change tiles in hand");
	private final JMenu file = new JMenu("File");
	private final JMenuItem loadGame = new JMenuItem("Load game");
	private final JMenuItem saveGame = new JMenuItem("Save game");
	private final JFileChooser fileChooser = new JFileChooser();
	private final Board board = new Board();
	private final Game game = new Game(this);

	private String letters;

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
				processEvent();
			}

		});
		options.add(tilesInHand);
		menuBar.add(options);
		loadGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == loadGame) {
			        final int returnVal = fileChooser.showDialog(UI.this, "Load");
			        if (returnVal == JFileChooser.APPROVE_OPTION) {
			            final File file = fileChooser.getSelectedFile();
			            IO.loadTiles(board, file);
			        }
			   } 
			}
			
		});
		file.add(loadGame);
		saveGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == saveGame) {
					saveGame();
			   } 
			}
			
		});
		file.add(saveGame);
		menuBar.add(file);
		setJMenuBar(menuBar);
		add(board);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				saveGame();
			}
		});
		pack();
		setLocationRelativeTo(getOwner());
		setResizable(false);
		setVisible(true);
	}
	
	private void saveGame() {
		final int returnVal = fileChooser.showDialog(UI.this, "Save");
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            final File file = fileChooser.getSelectedFile();
            IO.saveTiles(board, file);
        }
	}

	private void getBestMove() {
		new Thread() {
			@Override
			public void run() {
				game.getBestMove(letters);
			}
		}.start();
	}
	
	public Board getBoard() {
		return board;
	}
	
	private void requestLettersInHand() {
		String last = "";
		last += letters;
		do {
			letters = JOptionPane.showInputDialog("Enter letters currently in hand.");
		} while (letters == null || letters.equals(last) || letters.equals("null"));
		if (!Pattern.matches("[a-zA-Z]+", letters)) {
			JOptionPane.showMessageDialog(null, "Invalid character entered.", "Warning!", JOptionPane.WARNING_MESSAGE);
			requestLettersInHand();
		}
	}

	private void processEvent() {
		requestLettersInHand();
		getBestMove();
	}

}
