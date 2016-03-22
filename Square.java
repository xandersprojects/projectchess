package game;

/**
 * Represents a square on a chess board.
 */

public class Square {

	/** Initializes a square with PIECE on it. */
	public Square(Piece piece) {
		this.piece = piece;
	}

	/** Clears this square of any pieces. */
	public clear() {
		this.piece = NULL;
	}

	/** Returns the piece on this square. */
	public getPiece() {
		return piece;
	}

	/** Places PIECE on this square. */
	public putPiece(Piece piece) {
		this.piece = piece;
	}

	/** Returns true iff this square has no piece on it. */
	public isEmpty() {
		if (this.piece == NULL) {
			return true;
		}
		return false;
	}


	/** The piece on this square. */
	private Piece piece;

}