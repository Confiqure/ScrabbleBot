ScrabbleBot
===========

ScrabbleBot is an ongoing project open to the community started by MehSki11zOwn. The goal is to innovate a Java bot that will play any game of Scrabble you face it with by analyzing the tiles available both in the player's hand and on the board to form the highest scoring move possible.

The current configuration of the script is made to play *Words With Friends*, a Scrabble spin-off commonly played via Facebook.

To run:
Download the compiled JAR file and run via:
java -jar Scrabble.jar <game name> <letters you have>
It will boot up a moment later with a tiled UI. Simply click on any tile to enter the letter that goes there. **NOTE:** If NO letter goes in a space, please enter the space character (" ") in the tile - do NOT leave it blank! All tiles are created with spaces by default.

The current script will form the highest scoring move on the board, but with a few exceptions:
- Can only make words by playing off of ONE tile, i.e:
- Can not join two already-played words together
- Can not play new words adjacent to existing words to form cross-section words

It is my goal that with the help of the community, the various formulas and processes in this script can be altered until we have one of the first fully-operational, self-contained, Java Scrabble bots!
