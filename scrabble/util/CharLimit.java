package scrabble.util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * 
 * @author Robert G
 */
public class CharLimit extends PlainDocument {

    private static final long serialVersionUID = 1L;
    
    /**
     * <b>int</b> limit The maximum amount of characters allowed.
     */
    private final int limit;
    
    /**
     * Constructs a new <b>CharLimitDocument</b> with the specified limit. <br>
     * When passed as a parameter to a <b>JTextField</b> it will limit the amount of characters the user can
     * input to that of the limit supplied upon construction.
     * 
     * @param limit the max amount of characters allowed.
     */
    public CharLimit(final int limit) {
        this.limit = limit;
    }
    
    /**
     * 
     * @param offset
     * @param attr
     * @throws javax.swing.text.BadLocationException
     */
    @Override
    public void insertString(final int offset, final String str, final AttributeSet attr) throws BadLocationException {
        if (str == null || (getLength() + str.length()) > limit) return;
        super.insertString(offset, str, attr);
    }
    
}