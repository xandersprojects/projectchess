import java.lang.ArrayIndexOutOfBoundsException;

/**
 * Various utility functions for the chess program.
 */

class Utils {

	/** Given the index of a square on the board, returns whether or
	  * not it is in the board or not. */
	public static boolean inBounds(int sqNum) {
		System.out.println(sqNum);
		return (((sqNum & 8) == 0) && sqNum > 0 && sqNum < 128);
	}

	/** Given a square name as a string (e.g. e4), convert it to its
	  * indexed location on a 1D length 128 array. */
	public static int decodeSquare(String sq) {
		String file = sq.substring(0, 1);
		int rank = Integer.parseInt(sq.substring(1, 2));
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
	  * BOARD is the current board.
	  * COLOR is the color of the player making this move.

	  * Since regex_translate is responsible for creating the Move object,
	  * it is its job to ensure that the move is "feasible," but not necessarily
	  * legal under the current board circumstances.

	  * If the parameters given form no legal move, returns null. */

	public static Move regex_translate(String pQualp, String pQual, String qual, String pawn,
						   String capt, String targCast, String targ, String kCast,
						   String qCast, String promo, String checkM, String full, Board board,
						   int color) {
		Square[] all = board.getSquares();
		Move ret;
		if (pQualp != null) { /* If a piece is moving, or a pawn is making a capture */
			/* Deal with pawn captures here */
			if (pQual.length() == 1) { /* Dealing with a piece with no qualifier */
				int[] deltas = null;
				String found = null;
				switch (pQual) {
					case "Q":
						deltas = Queen.bases();
						if (color == 1) {
							found = "Q";
						} else {
							found = "q";
						}
						break;
					case "K":
						deltas = King.bases();
						if (color == 1) {
							found = "K";
						} else {
							found = "k";
						}
						break;
					case "R":
						deltas = Rook.bases();
						if (color == 1) {
							found = "R";
						} else {
							found = "r";
						}
						break;
					case "N":
						deltas = Knight.bases();
						if (color == 1) {
							found = "N";
						} else {
							found = "n";
						}
						break;
					case "B":
						deltas = Bishop.bases();
						if (color == 1) {
							found = "B";
						} else {
							found = "b";
						}
						break;
					default:
						System.out.println("That piece does not exist.");
						break;
				}
				if (deltas == null) {
					return null;
				}
				if (targ != null) {
					int dest = decodeSquare(targ);
					int sqFound = -1;
					if (!all[dest].isEmpty() && all[dest].getPiece().getColor() == color) {
						return null;
					}
					/** Handle the Knight case separately */

					/** For Sliding Pieces only */
					for (int i = 0; i < deltas.length; i++) {
						System.out.println(deltas[i]);
						int deltaCurr = deltas[i];
						int test = dest + deltaCurr;
						while (inBounds(test)) {
							Piece trying = all[test].getPiece();
							if (trying != null) {
								if (trying.getTextRepr().compareTo(found) == 0) {
									if (sqFound != -1 && sqFound != test) {
										System.out.println("There is more than one piece that can maneuver here. Please specify.");
										return null;
									} else {
										System.out.println("reached");
										sqFound = test;
									}
								}
							}
							test += deltaCurr;
						}
					}
					if (sqFound == -1) {
						System.out.println("Hmm. This move doesn't seem possible. Try again.");
						return null;
					}
					Piece involved = all[sqFound].getPiece();
					return new Move(involved, sqFound, dest, full, null);
				}
			}

		} else { /* If we are dealing with an ordinary pawn move, or castling */
			/* Look at targ. If contains something, then this is a pawn move. */
			if (targ != null) {
				/* Translate the target square to integer index on the board. */
				int dest = decodeSquare(targ);
				/* If there is already a piece on the target square, this move is not possible */
				if (!board.getSquares()[dest].isEmpty()) {
					return null;
				}
				int n;
				/* If the target square is the fourth rank, pawn could possibly move two spaces */
				if (((Character.toString(targ.charAt(1))).compareTo("4") == 0 && color == 1) ||
					(Character.toString(targ.charAt(1))).compareTo("5") == 0 && color == 0) {
					n = 2;
				} else {
					n = 1;
				}
				/* Creating the possible pawn movement array */
				int[] pawnForwards = new int[n];
				for (int i = 0; i < n; i++) { /* Grabs possibilities for pawn move */
					if (color == 1) {
						pawnForwards[i] = Pawn.whitePawnPossible()[i];
					} else {
						pawnForwards[i] = Pawn.blackPawnPossible()[i];
					}
					
				}
				/* Invoking the pawn movement array, find the target pawn. */
				Pawn pawnPiece = null;
				int foundStart = 0;
				for (int i = 0; i < n; i++) {
					/* If a pawn has already been found, break out of the loop. */
					if (pawnPiece != null) {
						break;
					}
					int trySquare = dest - pawnForwards[i];
					/* If the square is not empty, look in it. */
					try {
						if (!board.getSquares()[trySquare].isEmpty()) {
							Piece found = all[trySquare].getPiece();
							/* A piece is found. Check if it is a same color pawn as the player */
							if (found.getPieceCode() == 1 && color == found.getColor()) {
								/* If so, we have found the target pawn. */
								pawnPiece = (Pawn) found;
								foundStart = trySquare;
							}
						}						
					} catch (ArrayIndexOutOfBoundsException e) {
						/* Off the board. */
						return null;
					}
				}

				if (pawnPiece == null) {
					return null;
				}

				if (((Character.toString(targ.charAt(1))).compareTo("8") == 0 && color == 1) ||
					(Character.toString(targ.charAt(1))).compareTo("1") == 0 && color == 0) {
					if (promo == null) {
						System.out.println("You must include promotion information.");
						return null;
					}
					String newStr = Character.toString(promo.charAt(1));
					System.out.println(newStr);
					Piece newPiece = null;
					switch (newStr) {
						case "Q":
							newPiece = new Queen(dest, color);
							break;
						case "N":
							newPiece = new Knight(dest, color);
							break;
						case "B":
							newPiece = new Bishop(dest, color);
							break;
						case "R":
							newPiece = new Rook(dest, color);
							break;
						default:
							System.out.println("That is an invalid promotion piece.");
							break;
					}
					if (newPiece == null) {
						return null;
					}
					return new Move(pawnPiece, foundStart, dest, full, newPiece);
				}

				ret = new Move(pawnPiece, foundStart, dest, full, null);
				return ret;
			}

			/* Look at kCast. If contains something, this is a kingside castling move. */

		}
		return null;
	}

	

}