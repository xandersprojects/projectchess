package game.pieces;

/**
 * Represents a chess piece.
 */

public abstract class Piece {
	
	/** Sets the color of the piece. 1 if white, 0 if black. */
	void setColor(int color) {
		_color = color
	}

	/** Returns the color of the piece. 1 if white, 0 if black. */
	int getColor() {
		return _color;
	}

	/** The color of the piece. 1 if white, 0 if black. */
	private int _color;

}