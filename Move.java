import java.lang.NumberFormatException;

/**
 * Represents a move.
 */

public class Move {

	Move(Piece piece, int start, int dest, String str, Piece promo, boolean kCast, boolean qCast, boolean enPassant) {
		_piece = piece;
		_start = start;
		_dest = dest;
		_str = str;
		_promo = promo;
		_kCast = kCast;
		_qCast = qCast;
		_enPassant = enPassant;
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

	Piece getPromo() {
		return _promo;
	}

	boolean getKCast() {
		return _kCast;
	}

	boolean getQCast() {
		return _qCast;
	}

	boolean getEnPassant() {
		return _enPassant;
	}

	/** Piece involved in this move. */
	private Piece _piece;
	/** Start square of this move. */
	private int _start;
	/** Destination square of this move. */
	private int _dest;
	/** String representation of this move. */
	private String _str;
	/** String indicating promotion */
	private Piece _promo;
	/** Boolean indicating kingside castle? */
	private boolean _kCast;
	/** Boolean indicating queenside castle? */
	private boolean _qCast;
	/** Boolean indicating enPassant */
	private boolean _enPassant;
}

