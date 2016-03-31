
/**
 * Represents a square on a chess board.
 */

public class Square {

	/** Initializes a square with PIECE on it. */
	Square(Piece piece) {
		this.piece = piece;
	}

	/** Clears this square of any pieces. */
	void clear() {
		this.piece = null;
	}

	/** Returns the piece on this square. */
	Piece getPiece() {
		return piece;
	}

	/** Places PIECE on this square. */
	void putPiece(Piece piece) {
		this.piece = piece;
	}

	/** Returns true iff this square has no piece on it. */
	boolean isEmpty() {
		if (this.piece == null) {
			return true;
		}
		return false;
	}


	/** The piece on this square. */
	private Piece piece;

}