package chess;

/**
 * Represents a bishop.
 */

public class Bishop extends Piece {

	/** Initializes a COLOR bishop at POSITION. */
	Bishop(int position, int color) {
		super();
		this.setSliding(true);
		this.setColor(color);
		int[] possibles = {17, -15, -17, 15};
		this.setValue(3.0);
		_position = position;
	}

	/** Position of this bishop on the board. */
	private int _position;

}