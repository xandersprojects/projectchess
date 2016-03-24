package game.pieces;

/**
 * Represents a pawn.
 */

public class Pawn extends Piece {

	/** Initializes a COLOR pawn at POSITION. */
	Pawn(int position, int color) {
		super();
		_sliding = true;
		_color = color;
		_moved = false;
		possibles = {16, 32};
		value = 1.0;
		_position = position;
	}


	/** True iff this pawn has already moved. */
	private boolean _moved;
	/** Position of this pawn on the board. */
	private int _position;
}