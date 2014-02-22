package scrabble;

import javax.swing.SwingUtilities;
import scrabble.ui.UI;

/**
 *
 * Main class
 * 
 * @author MehSki11zOwn
 */
public class Scrabble {

	/**
	 * Main method.
	 */
	public static void main(final String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new UI();
			}

		});
	}

}
