package game.pieces;

/**
 * Represents a bishop.
 */

public class Bishop extends Piece {

	/** Initializes a COLOR bishop at POSITION. */
	Bishop(int position, int color) {
		super();
		_sliding = true;
		_color = color;
		possibles = {17, -15, -17, 15};
		value = 3.0;
		_position = position
	}

	/** Position of this bishop on the board. */
	private int _position;

}