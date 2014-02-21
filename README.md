ScrabbleBot
===========

This is an ongoing project open to the community started by MehSki11zOwn. The goal is to make this into a bot that will play any game of Scrabble you face it with by looking at the tiles available to it both in the player's hand and on the board to form the highest scoring move possible.

The current configuration of the script is made for Words With Friends, a Scrabble spin-off commonly played via Facebook.

To run:
Compile the source file and run with:
java Scrabble <game name> <letters you have>
It will boot up with a tiled UI. Simply click on any tile to enter the letter that goes there. *NOTE:* If NO letter goes in a space, please enter the space character (" ") in the tile - do NOT leave it blank! All tiles are created with spaces by default.

The current script will form the highest scoring move on the board, but with a few exceptions:
- Can only make words by playing off of ONE tile, i.e.:
- Can not join two already-played words together
- Can not play new words adjacent to existing words to form cross-section words

It is my goal that with the help of the community, the various formulas can be tweaked until we have one of the first fully-operational, self contained, Java Scrabble bots!
