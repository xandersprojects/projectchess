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

	/** Returns whether or not this is a "sliding" piece.
	  * Sliding is defined as whether a piece that can move to 
	  * squares up until the edge of the board or up until it
	  * encounters another piece and cannot move further. */
	boolean isSliding() {
		return _sliding;
	}

	/** Sets sliding property of this piece. Useful for promotion, etc. */
	void setSliding(boolean sliding) {
		_sliding = sliding;
	}


	/** The color of the piece. 1 if white, 0 if black. */
	private int _color;
	/** True iff this is a sliding piece. */
	private boolean _sliding;
	/** All possible base moves for this piece. */
	private int[] possibles;
	/** Value of this piece. */
	private double value;

}