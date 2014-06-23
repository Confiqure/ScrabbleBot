package scrabble.game.wrappers;

import java.awt.Point;

/**
 * @author MehSki11zOwn
 * Class containing all regex data from each move possibility.
 */
public class Regex {

    /**
     * Starting point on grid for regex.
     */
    public Point start;

    /**
     * Regex equation.
     */
    public String regex;

    /**
     * Letter(s) to play off of.
     */
    public String playOff;

    /**
     * Vertical move.
     */
    public boolean vert;

    /**
     * Constructor
     * 
     * @param start   starting point on grid for regex
     * @param regex   regex equation
     * @param playOff letter(s) to play off of
     * @param vert    vertical move
     */
    public Regex(final Point start, final String regex, final String playOff, final boolean vert) {
        this.start = start;
        this.regex = regex;
        this.vert = vert;
    }
    
}