import java.lang.NumberFormatException;

/**
 * Represents a move.
 */

public class Move {

	Move(Piece piece, int start, int dest, String str) {
		_piece = piece;
		_start = start;
		_dest = dest;
		_str = str;
	}

	Piece getPiece() {
		return _piece;
	}

	int getStart() {
		return _start;
	}

	int getDest() {
		return _dest;
	}

	String getStr() {
		return _str;
	}

	/** Piece involved in this move. */
	private Piece _piece;
	/** Start square of this move. */
	private int _start;
	/** Destination square of this move. */
	private int _dest;
	/** String representation of this move. */
	private String _str;
}

