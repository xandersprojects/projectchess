
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

	void setPieceCode(int code) {
		_pieceCode = code;
	}

	int getPieceCode() {
		return _pieceCode;
	}

	/** Returns whether or not this piece has moved already. */
	public boolean hasMoved() {
		return _moved;
	}

	/** Sets moved to true after the piece has moved. */
	public void nowMoved() {
		_moved = true;
	}

	/** Sets moved to false upon initialization. */
	public void notMoved() {
		_moved = false;
	}

	abstract int[] getBases();


	/** The color of the piece. 1 if white, 0 if black. */
	private int _color;
	/** True iff this is a sliding piece. */
	private boolean _sliding;
	/** Value of this piece. */
	private double value;
	/** Text representation of this piece on a text board. */
	private String _textRepr;
	/** Position of this piece on the board. */
	private int _position;
	/** Code of the piece. P=1, R=2, N=3, B=4, Q=5, K=6 */
	private int _pieceCode;
	/** Indicates whether the piece has moved or not. */
	private boolean _moved;
}