package scrabble;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import scrabble.ui.UI;

/**
 *
 * Main class
 * 
 * @author MehSki11zOwn
 */
public class Scrabble {

    public static UI ui;

    /**
     * Main method.
     */
    public static void main(final String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (final ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ignored) {}
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ui = new UI();
            }
        });
    }

}
