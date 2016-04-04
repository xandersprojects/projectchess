import java.lang.NumberFormatException;

/**
 * Represents a move.
 */

public class Move {

	/** Given the components of a string that complies with the
	  * regex of a chess move, translates these components into
	  * a single Move object, and returns it.
	  * A SUMMARY OF THE 11 GROUPS FROM REGEX PATTERN:
	  * 1 - pQualp - Piece to move along with an optional qualifier, OR a Pawn to move
	  * 2 - pQual - Piece to move along with an optional qualifier
	  * 3 - qual - The optional qualifier
	  * 4 - pawn - The pawn to move
	  * 5 - capt - The optional "x" denoting a capture
	  * 6 - targCast - The target square, OR a castling move
	  * 7 - targ - The target square
	  * 8 - kCast - If castling kingside, 0-0 is stored here
	  * 9 - qCast - If castling queenside, 0-0-0 is stored here
	  * 10 - promo - Optional Promotion handling information, containing the "=" and the piece to promote to
	  * 11 - checkM - Optional check "+" or checkmate "#"
	  * FULL is the entire move, as a single string

	  * Since regex_translate is responsible for creating the Move object,
	  * it is its job to ensure the legality of a move.

	  * If the parameters given form no legal move, returns null. */

	public Move regex_translate(String pQualp, String pQual, String qual, String pawn,
						   String capt, String targCast, String targ, String kCast,
						   String qCast, String promo, String checkM, String full) {
		Move ret;
		if (pQualp != null) { /* If a piece is moving, or a pawn is making a capture */

		} else { /* If we are dealing with an ordinary pawn move, or castling */
			/* Look at targ. If contains something, then this is a pawn move. */
				/* Translate the target square to integer index on the board. */
				/* Invoking the pawn movement array, find the target pawn. */
				/* Go into the Pawn object and find the starting square. */
				/* Check to see if target square contains 8 -- if so, expect a promotion. */
				/* Check promo string to get promotion information. */
				/* */
		}
		return null;
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

