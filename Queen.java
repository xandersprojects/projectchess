package chess;

/**
 * Represents a queen.
 */

public class Queen extends Piece {

	/** Initializes a COLOR queen at POSITION. */
	Queen(int position, int color) {
		super();
		this.setSliding(true);
		this.setColor(color);
		int[] possibles = {16, 17, 1, -15, -16, -17, -1, 15};
		this.setValue(9.0);
		_position = position;
	}

	/** Position of this queen on the board. */
	private int _position;

}