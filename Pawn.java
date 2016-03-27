package chess;

/**
 * Represents a pawn.
 */

public class Pawn extends Piece {

	/** Initializes a COLOR pawn at POSITION. */
	Pawn(int position, int color) {
		super();
		this.setSliding(true);
		this.setColor(color);
		_moved = false;
		int[] possibles = {16, 32};
		this.setValue(1.0);
		_position = position;
	}


	/** True iff this pawn has already moved. */
	private boolean _moved;
	/** Position of this pawn on the board. */
	private int _position;
}