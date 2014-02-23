package scrabble.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

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
	private final JMenu menu = new JMenu("Options");
	private final JMenuItem item = new JMenuItem("Change tiles in hand");
	private final File storageDirectory = new File(System.getProperty("user.home") + File.separator + "ScrabbleBot" + File.separator);
	private final Board board = new Board();

	private String fileName;
	private String letters;

	public UI() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ignored) {
		}
		do {
			fileName = JOptionPane.showInputDialog("Enter name of game/storage file.") + ".txt";
		} while (fileName == null || fileName.equalsIgnoreCase("null.txt") || fileName.isEmpty());
		requestLettersInHand();
		setTitle("ScrabbleBot");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menuBar.add(menu);
		menu.add(item);
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				processEvent();
			}

		});
		setJMenuBar(menuBar);
		IO.loadTiles(board, storageDirectory, fileName);
		add(board);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent e) {
				IO.saveTiles(board, storageDirectory, fileName);
				dispose();
			}
		});
		pack();
		setLocationRelativeTo(getOwner());
		setResizable(false);
		setVisible(true);
		getBestMove();
	}

	private void getBestMove() {
		new Thread() {
			@Override
			public void run() {
				new Game(getUI()).getBestMove(letters);
			}
		}.start();
	}
	
	public Board getBoard() {
		return board;
	}

	public UI getUI() {
		return this;
	}
	
	private void requestLettersInHand() {
		String last = "";
		last += letters;
		do {
			letters = JOptionPane.showInputDialog("Enter letters currently in hand.");
		} while (letters == null || letters.equals(last) || letters.equals("null"));
	}

	private void processEvent() {
		requestLettersInHand();
		getBestMove();
	}

}
