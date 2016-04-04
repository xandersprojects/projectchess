
/**
 * Represents a chess piece.
 */

public abstract class Piece {
	
	/** Sets the color of the piece. 1 if white, 0 if black. */
	void setColor(int color) {
		_color = color;
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

	/** Returns the nominal value of this piece. */
	double getValue() {
		return value;
	}

	/** Sets the nominal value of this piece. */
	void setValue(double worth) {
		value = worth;
	}

	void setTextRepr(String str) {
		_textRepr = str;
	}

	String getTextRepr() {
		return _textRepr;
	}

	void setPosition(int pos) {
		_position = pos;
	}

	int getPosition() {
		return _position;
	}

	void setPossibles(int[] possible) {
		_possibles = possible;
	}

	int[] getPossibles() {
		return _possibles;
	}

	void setPieceCode(int code) {
		_pieceCode = code;
	}

	int getPieceCode() {
		return _pieceCode;
	}


	/** The color of the piece. 1 if white, 0 if black. */
	private int _color;
	/** True iff this is a sliding piece. */
	private boolean _sliding;
	/** All possible base moves for this piece. */
	private int[] _possibles;
	/** Value of this piece. */
	private double value;
	/** Text representation of this piece on a text board. */
	private String _textRepr;
	/** Position of this knight on the board. */
	private int _position;
	/** Code of the piece. P=1, R=2, N=3, B=4, Q=5, K=6 */
	private int _pieceCode;

}