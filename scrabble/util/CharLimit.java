package scrabble.ui.charlimitdocument;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * @author Robert G
 *
 */
public class CharLimitDocument extends PlainDocument {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * <b>int</b> limit The maximum amount of characters allowed.
	 */
	private int limit;
	
	/**
	 * Constructs a new <b>CharLimitDocument</b> with the specified limit. <br>
	 * When passed as a parameter to a <b>JTextField</b> it will limit the amount of characters the user can
	 * input to that of the limit supplied upon construction.
	 * 
	 * @param limit the max amount of characters allowed.
	 */
	public CharLimitDocument(int limit) {
		this.limit = limit;
	}
	
	/**
	 * 
	 */
	@Override
	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		if (str == null || (getLength() + str.length()) > limit) {
			return;
		} else {
			super.insertString(offset, str, attr);
		}
	}
	
}