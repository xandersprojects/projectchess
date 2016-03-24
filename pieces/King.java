package game.pieces;

/**
 * Represents a king.
 */

public class King extends Piece {

	/** Initializes a COLOR king at POSITION. */
	King(int position, int color) {
		super();
		_sliding = true;
		_color = color;
		possibles = {16, 17, 1, -15, -16, -17, -1, 15};
		value = 9000.0;
		_position = position
	}

	/** Position of this king on the board. */
	private int _position;

}