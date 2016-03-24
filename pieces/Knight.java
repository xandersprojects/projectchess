package game.pieces;

/**
 * Represents a knight.
 */

public class Knight extends Piece {

	/** Initializes a COLOR knight at POSITION. */
	Knight(int position, int color) {
		super();
		_sliding = false;
		_color = color;
		possibles = {33, 18, -14, -31, -33, -18, 14, 31};
		value = 3.0;
		_position = position;
	}

	/** Position of this knight on the board. */
	private int _position;

}