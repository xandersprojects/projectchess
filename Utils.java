import java.lang.ArrayIndexOutOfBoundsException;
import java.util.Scanner;

/**
 * Various utility functions for the chess program.
 */

class Utils {

	/** Given the index of a square on the board, returns whether or
	  * not it is in the board or not. */
	public static boolean inBounds(int sqNum) {
		return (((sqNum & 8) == 0) && sqNum >= 0 && sqNum < 128);
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

			/* Deal with regular piece moves. */
			int[] deltas = null;
			String found = null;
			switch (Character.toString(pQual.charAt(0))) {
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
			int dest = decodeSquare(targ);
			int sqFound = -1;
			if (!all[dest].isEmpty() && all[dest].getPiece().getColor() == color) {
				return null;
			}
			if (capt != null && all[dest].isEmpty()) {
				Scanner scan = new Scanner(System.in);
				System.out.println("This move does not involve a capture. Play " + pQualp + targ + "? (Y/N)");
				String trying = scan.next();
				if (trying.compareTo("Y") == 0 || trying.compareTo("y") == 0 || trying.compareTo("Yes") == 0 || trying.compareTo("yes") == 0) {
					return regex_translate(pQualp, pQual, qual, pawn, null, targCast, targ, kCast,
						qCast, promo, checkM, pQualp + targ, board, color);
				} else if (trying.compareTo("N") == 0 || trying.compareTo("n") == 0 || trying.compareTo("No") == 0 || trying.compareTo("no") == 0) {
					System.out.println("Cancelling previous move request");
					return null;
				} else {
					System.out.println("Did not understand your response. Cancelling.");
					return null;
				}
			}
			/** Handle the Knight and King case separately */
			if (found.compareTo("N") == 0 || found.compareTo("n") == 0 ||
				found.compareTo("K") == 0 || found.compareTo("k") == 0) {

				for (int i = 0; i < deltas.length; i++) {
					int deltaCurr = deltas[i];
					int test = dest + deltaCurr;
					if (!inBounds(test)) {
						continue;
					}
					Piece trying = all[test].getPiece();
					if (trying != null) {
						if (trying.getTextRepr().compareTo(found) == 0) {
							if (sqFound != -1 && sqFound != test) {
								if (pQual.length() == 1) {
									System.out.println("There is more than one knight that can maneuver here. Please specify.");
									return null;
								} else if (pQual.length() == 2) {
									char character = pQual.charAt(1);
									if (Character.isDigit(character)) {
										int rank = Character.getNumericValue(character) - 1;
										int testVal = test / 16;
										if (rank == testVal) {
											if (sqFound / 16 == rank) {
												System.out.println("There is more than one knight that can maneuver here. Please specify.");
												return null;
											}											
											sqFound = test;
										}
									} else {
										int qualifier = character;
										qualifier -= 97;
										int testVal = test % 16;
										if (qualifier == testVal) {
											if (sqFound % 16 == qualifier) {
												System.out.println("There is more than one knight that can maneuver here. Please specify.");
												return null;
											}
											sqFound = test;
										}
									}
								} else if (pQual.length() == 3) {
									String qualifier = pQual.substring(1, 3);
									int qualifNum = decodeSquare(qualifier);
									if (qualifNum == test) {
										sqFound = test;
									}
								}
							} else {
								if (pQual.length() == 1) {
									System.out.println("a");
									sqFound = test;
								} else if (pQual.length() == 2) {
									char character = pQual.charAt(1);
									if (Character.isDigit(character)) {
										int rank = Character.getNumericValue(character) - 1;
										int testVal = test / 16;
										if (testVal != rank) {
											continue;
										} else {
											sqFound = test;
										}
									} else {
										int qualifier = character;
										qualifier -= 97;
										int testVal = test % 16;
										if (qualifier != testVal) {
											continue;
										} else {
											sqFound = test;
										}
									}
								} else if (pQual.length() == 3) {
									String qualifier = pQual.substring(1, 3);
									int qualifNum = decodeSquare(qualifier);
									if (qualifNum == test) {
										sqFound = test;
									}
								}
							}
						}
					}
				}
			} else { /* Handle sliding pieces */
				for (int i = 0; i < deltas.length; i++) {
					int deltaCurr = deltas[i];
					int test = dest + deltaCurr;
					while (inBounds(test)) {
						Piece trying = all[test].getPiece();
						if (trying != null) {
							if (trying.getTextRepr().compareTo(found) == 0) {
								if (sqFound != -1 && sqFound != test) {
									if (pQual.length() == 1) {
										System.out.println("There is more than one piece that can maneuver here. Please specify.");
										return null;
									} else if (pQual.length() == 2) {
										char character = pQual.charAt(1);
										if (Character.isDigit(character)) {
											int rank = Character.getNumericValue(character) - 1;
											int testVal = test / 16;
											if (rank == testVal) {
												if (sqFound / 16 == rank) {
													System.out.println("There is more than one piece that can maneuver here. Please specify.");
													return null;
												}											
												sqFound = test;
												break;
											}
										} else {
											int qualifier = character;
											qualifier -= 97;
											int testVal = test % 16;
											if (qualifier == testVal) {
												if (sqFound % 16 == qualifier) {
													System.out.println("There is more than one piece that can maneuver here. Please specify.");
													return null;
												}
												sqFound = test;
												break;
											}
										}
									} else if (pQual.length() == 3) {
										String qualifier = pQual.substring(1, 3);
										int qualifNum = decodeSquare(qualifier);
										if (qualifNum == test) {
											sqFound = test;
											break;
										}
									}
								} else {
									if (pQual.length() == 1) {
										sqFound = test;
										break;
									} else if (pQual.length() == 2) {
										char character = pQual.charAt(1);
										if (Character.isDigit(character)) {
											int rank = Character.getNumericValue(character) - 1;
											int testVal = test / 16;
											if (testVal != rank) {
												break;											
											} else {
												sqFound = test;
												break;
											}
										} else {
											int qualifier = character;
											qualifier -= 97;
											int testVal = test % 16;
											if (qualifier != testVal) {
												break;												
											} else {
												sqFound = test;
												break;												
											}
										}
									} else if (pQual.length() == 3) {
										String qualifier = pQual.substring(1, 3);
										int qualifNum = decodeSquare(qualifier);
										if (qualifNum == test) {
											sqFound = test;
											break;
										}
									}
								}
							} else {
								break;
							}
						}
						test += deltaCurr;
					}
				}
			}
			if (sqFound == -1) {
				System.out.println("Hmm. This move doesn't seem possible. Try again.");
				return null;
			}
			Piece involved = all[sqFound].getPiece();
			return new Move(involved, sqFound, dest, full, null, false, false);

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
				boolean twoSpaces = false;
				/* If the target square is the fourth rank, pawn could possibly move two spaces */
				if (((Character.toString(targ.charAt(1))).compareTo("4") == 0 && color == 1) ||
					(Character.toString(targ.charAt(1))).compareTo("5") == 0 && color == 0) {
					n = 2;
					twoSpaces = true;
				} else {
					n = 1;
				}
				if (twoSpaces && color == 1) {
					int testSq = dest - 16;
					if (!all[testSq].isEmpty() && all[testSq].getPiece().getTextRepr() != "P") {
						System.out.println("There is another piece in the way.");
						return null;
					}
				} else if (twoSpaces && color == 0) {
					int testSq = dest + 16;
					if (!all[testSq].isEmpty() && all[testSq].getPiece().getTextRepr() != "p") {
						System.out.println("There is another piece in the way.");
						return null;
					}
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
					return new Move(pawnPiece, foundStart, dest, full, newPiece, false, false);
				}

				ret = new Move(pawnPiece, foundStart, dest, full, null, false, false);
				return ret;
			} else if (kCast != null) {
				/* Look at kCast. If contains something, this is a kingside castling move. */
				if (color == 1) {
					if (all[4].isEmpty()) {
						return null;
					}
					/* Find the king */
					if (all[4].getPiece().getTextRepr() != "K") {
						return null;
					}
					King king = (King) all[4].getPiece();
					/* Ensure the king hasn't moved yet */
					if (king.hasMoved()) {
						return null;
					}
					/* Ensure the two squares to the right of the king are vacated. */
					if (!all[5].isEmpty() || !all[6].isEmpty()) {
						return null;
					}
					/* Find the rook. */
					if (all[7].getPiece().getTextRepr() != "R") {
						return null;
					}
					Rook rook = (Rook) all[7].getPiece();
					/* Ensure the rook hasn't moved yet. */
					if (rook.hasMoved()) {
						return null;
					}
					/* Return the castling move. Castling will be handled by the player. */
					return new Move(king, 4, 6, full, null, true, false);
				} else {
					if (all[116].isEmpty()) {
						return null;
					}
					/* Find the king */
					if (all[116].getPiece().getTextRepr() != "k") {
						return null;
					}
					King king = (King) all[116].getPiece();
					/* Ensure the king hasn't moved yet */
					if (king.hasMoved()) {
						return null;
					}
					/* Ensure the two squares to the right of the king are vacated. */
					if (!all[117].isEmpty() || !all[118].isEmpty()) {
						return null;
					}
					/* Find the rook. */
					if (all[119].getPiece().getTextRepr() != "r") {
						return null;
					}
					Rook rook = (Rook) all[119].getPiece();
					/* Ensure the rook hasn't moved yet. */
					if (rook.hasMoved()) {
						return null;
					}
					/* Return the castling move. Castling will be handled by the player. */
					return new Move(king, 116, 118, full, null, true, false);
				}
			} else if (qCast != null) {
				/* Look at qCast. If contains something, this is a queenside castling move. */
				if (color == 1) {
					if (all[4].isEmpty()) {
						return null;
					}
					/* Find the king */
					if (all[4].getPiece().getTextRepr() != "K") {
						return null;
					}
					King king = (King) all[4].getPiece();
					/* Ensure the king hasn't moved yet */
					if (king.hasMoved()) {
						return null;
					}
					/* Ensure the three squares to the left of the king are vacated. */
					if (!all[1].isEmpty() || !all[2].isEmpty() || !all[3].isEmpty()) {
						return null;
					}
					/* Find the rook. */
					if (all[0].getPiece().getTextRepr() != "R") {
						return null;
					}
					Rook rook = (Rook) all[0].getPiece();
					/* Ensure the rook hasn't moved yet. */
					if (rook.hasMoved()) {
						return null;
					}
					/* Return the castling move. Castling will be handled by the player. */
					return new Move(king, 4, 2, full, null, false, true);
				} else {
					if (all[116].isEmpty()) {
						return null;
					}
					/* Find the king */
					if (all[116].getPiece().getTextRepr() != "k") {
						return null;
					}
					King king = (King) all[116].getPiece();
					/* Ensure the king hasn't moved yet */
					if (king.hasMoved()) {
						return null;
					}
					/* Ensure the three squares to the left of the king are vacated. */
					if (!all[113].isEmpty() || !all[114].isEmpty() || !all[115].isEmpty()) {
						return null;
					}
					/* Find the rook. */
					if (all[112].getPiece().getTextRepr() != "r") {
						return null;
					}
					Rook rook = (Rook) all[112].getPiece();
					/* Ensure the rook hasn't moved yet. */
					if (rook.hasMoved()) {
						return null;
					}
					/* Return the castling move. Castling will be handled by the player. */
					return new Move(king, 116, 114, full, null, false, true);
				}
			}
		}
		return null;
	}

	

}