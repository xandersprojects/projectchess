import java.lang.NumberFormatException;
/**
 * Represents a move.
 */

public class Move {
	
	Move(String piece, String start, String dest, String promotion) {
		_dest = decodeSquare(dest);
	}


	/** Given a square name as a string (e.g. e4), convert it to its
	  * indexed location on a 1D length 128 array. */
	public int decodeSquare(String dest) {
		String file = dest.substring(0, 1);
		int rank = Integer.parseInt(dest.substring(1, 2));
		int location = 0;
		switch (file) {
			case "a": 
				location = 16 * (rank - 1);
				break;
			case "b": 
				location = 1 + 16 * (rank - 1);
				break;
			case "c": 
				location = 2 + 16 * (rank - 1);
				break;
			case "d": 
				location = 3 + 16 * (rank - 1);
				break;
			case "e": 
				location = 4 + 16 * (rank - 1);
				break;
			case "f": 
				location = 5 + 16 * (rank - 1);
				break;
			case "g": 
				location = 6 + 16 * (rank - 1);
				break;
			case "h": 
				location = 7 + 16 * (rank - 1);
				break;
			default: 
				break;
		}
		return location;
	}

	/** Piece involved in this move. */
	private Piece _piece;
	/** Start square of this move. */
	private int _start;
	/** Destination square of this move. */
	private int _dest;
}