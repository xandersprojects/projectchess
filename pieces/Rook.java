package game.pieces;

/**
 * Represents a rook.
 */

public class Rook extends Piece {

	/** Initializes a COLOR rook at POSITION. */
	Rook(int position, int color) {
		super();
		_color = color;
		_sliding = true;
		possibles = {16, 1, -16, -1};
		value = 5.0;
		_position = position;
	}
	
	/** Position of this rook on the board. */
	private int _position;

}