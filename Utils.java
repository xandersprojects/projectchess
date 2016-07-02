import java.lang.ArrayIndexOutOfBoundsException;
import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.LinkedList;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	/** Given a square number, convert it to its square name in 
	  * standard algebraic chess notation. */
	public static String sqToNotation(int sqNum) {
		String ret = "";
		int file = sqNum % 16;
		String rank = Integer.toString((sqNum / 16) + 1);
		switch (file) {
			case 0: 
				ret = "a" + rank;
				break;
			case 1: 
				ret = "b" + rank;
				break;
			case 2: 
				ret = "c" + rank;
				break;
			case 3: 
				ret = "d" + rank;
				break;
			case 4: 
				ret = "e" + rank;
				break;
			case 5: 
				ret = "f" + rank;
				break;
			case 6: 
				ret = "g" + rank;
				break;
			case 7: 
				ret = "h" + rank;
				break;
			default: 
				break;
		}
		return ret;
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
	  * MOVELIST is the move history so far in the current game. (used for en passant purposes)

	  * Since regex_translate is responsible for creating the Move object,
	  * it is its job to ensure that the move is "feasible," but not necessarily
	  * legal under the current board circumstances.

	  * If the parameters given form no legal move, returns null. */

	public static Move regex_translate(String pQualp, String pQual, String qual, String pawn,
						   String capt, String targCast, String targ, String kCast,
						   String qCast, String promo, String checkM, String full, Board board,
						   int color, ArrayList<ArrayList<String>> moveList) {
		Square[] all = board.getSquares();
		Move ret;
		if (pQualp != null) { /* If a piece is moving, or a pawn is making a capture */
			/* Deal with pawn captures here */
			if (pawn != null) {
				int targSq = decodeSquare(targ);
				if (all[targSq].isEmpty()) {
					/* Deal with en passant. */
					if (targSq / 16 == 5 || targSq / 16 == 2) { /* Pawn taking empty square on 6th rank --> en passant (3rd for black) */
						return enPassantHandler(full, targSq, color, board, moveList);
					} else {
						System.out.println("There is no target capture.");
						return null;
					}
				}
				Piece targCapt = all[targSq].getPiece();
				if (color == targCapt.getColor()) {
					System.out.println("Are you serious you want to capture your own piece?");
					return null;
				}
				int[] pawnForwards;
				if (color == 1) {
					pawnForwards = Arrays.copyOfRange(Pawn.whitePawnPossible(), 2, 4);
				} else {
					pawnForwards = Arrays.copyOfRange(Pawn.blackPawnPossible(), 2, 4);
				}
				Pawn found = null;
				char character = full.charAt(0);
				int test = 0;
				for (int i = 0; i < pawnForwards.length; i++) {
					test = targSq - pawnForwards[i];
					if (all[test].isEmpty()) {
						continue;
					} else if ((color == 1 && all[test].getPiece().getTextRepr().compareTo("P") == 0) ||
							   (color == 0 && all[test].getPiece().getTextRepr().compareTo("p") == 0)) {
						int foundFile = test % 16;
						int supposed = (character - 97) % 16;
						if (foundFile == supposed) {
							found = (Pawn) all[test].getPiece();
							break;
						}
					}
				}
				if (found == null) {
					System.out.println("Hurrr");
					System.out.println(full);
					System.out.println("No pawn can make this move.");
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
							newPiece = new Queen(targSq, color);
							break;
						case "N":
							newPiece = new Knight(targSq, color);
							break;
						case "B":
							newPiece = new Bishop(targSq, color);
							break;
						case "R":
							newPiece = new Rook(targSq, color);
							break;
						default:
							System.out.println("That is an invalid promotion piece.");
							break;
					}
					if (newPiece == null) {
						return null;
					}
					return new Move(found, test, targSq, full, newPiece, false, false, false);
				}
				return new Move(found, test, targSq, full, null, false, false, false);
			} else {
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
							qCast, promo, checkM, pQualp + targ, board, color, moveList);
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
				return new Move(involved, sqFound, dest, full, null, false, false, false);				
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
					return new Move(pawnPiece, foundStart, dest, full, newPiece, false, false, false);
				}

				ret = new Move(pawnPiece, foundStart, dest, full, null, false, false, false);
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
					return new Move(king, 4, 6, full, null, true, false, false);
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
					return new Move(king, 116, 118, full, null, true, false, false);
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
					return new Move(king, 4, 2, full, null, false, true, false);
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
					return new Move(king, 116, 114, full, null, false, true, false);
				}
			}
		}
		return null;
	}

	/* Given the FULL algebraic chess notation of an en passant move made by player
	 * who controls COLOR pieces on BOARD, and the TARGSQ
	 * target square of the capture which should be empty, returns a valid Move
	 * if all rules obeying en passant are satisfied. Returns null otherwise. */
	static Move enPassantHandler(String full, int targSq, int color, Board board, ArrayList<ArrayList<String>> moveList) {
		/* First of all, verify that there is a pawn to capture. */
		Square[] squares = board.getSquares();
		String targSqName = sqToNotation(targSq);

		System.out.println("The target square is: " + sqToNotation(targSq));

		int targPawnSq = -1;
		if (color == 1) {
			targPawnSq = targSq - 16;
		} else if (color == 0) {
			targPawnSq = targSq + 16;
		}
		System.out.println("The target pawn is on square: " + sqToNotation(targPawnSq));
		if (squares[targPawnSq].isEmpty()) {
			return null;
		} else {
			Piece targPiece = squares[targPawnSq].getPiece();
			if (targPiece.getPieceCode() != 1 || targPiece.getColor() == color) {
				return null;
			}
			/* Now we have verified that the targPiece is an enemy pawn. */
			Pawn targPawn = (Pawn) targPiece;
			/* Now we check if that pawn was the last to move two spaces. */
			if (targPawn.didTwo()) {
				/* Must look at previous move */
				int outerLength = moveList.size();
				if (color == 1) {
					outerLength--;
				}
				ArrayList<String> moveSet = moveList.get(outerLength - 1);
				int innerLength = moveSet.size();
				String lastMove = moveSet.get(innerLength - 1);
				if (lastMove.compareTo(sqToNotation(targPawn.getPosition())) == 0) {
					/* Find the pawn */ /* TO DO: FIND FOUND AND START */
					Pawn found = null;
					String targetFile = targSqName.substring(0, 1);
					String origFile = full.substring(0, 1);
					int[] pawnForwards;
					if (color == 1) {
						pawnForwards = Arrays.copyOfRange(Pawn.whitePawnPossible(), 2, 4);
					} else {
						pawnForwards = Arrays.copyOfRange(Pawn.blackPawnPossible(), 2, 4);
					}
					int pawnStart = -1;
					if (targetFile.compareTo(origFile) > 0) { // targetFile is to the right of origFile
						if (color == 1) {
							pawnStart = targSq - pawnForwards[1];
						} else {
							pawnStart = targSq - pawnForwards[0];
						}
						System.out.println("pawnStart " + Integer.toString(pawnStart));
						found = (Pawn) squares[pawnStart].getPiece();
					} else { // targetFile is to the left of origFile
						if (color == 1) {
							pawnStart = targSq - pawnForwards[0];
						} else {
							pawnStart = targSq - pawnForwards[1];
						}
						System.out.println("pawnStart " + Integer.toString(pawnStart));
						found = (Pawn) squares[pawnStart].getPiece();
					}
					/* This is a valid en passant move which obeys all the rules! */
					System.out.println("Successful En Passant!");
					return new Move(found, pawnStart, targSq, full, null, false, false, true);
				} else {
					return null;
				}
			}
		}
		return null;
	}

	/** Makes the move MOVE on BOARD. If TEST is 1, this indicates that we
	  * are just testing a move, and the properties of the piece should not 
	  * be altered (i.e. the piece should not be considered as having moved) */
	public static void makeMove(Move move, Board board, int test) {

		Square[] all = board.getSquares();

		/* Piece in question */
		Piece moving = move.getPiece();

		if (test == 0) {
			moving.nowMoved();
		}

		/* Special case for en passant. */
		if (move.getEnPassant()) {
			all[move.getDest()].putPiece(moving);
			moving.setPosition(move.getDest());
			all[move.getStart()].clear();
			if (move.getPiece().getColor() == 1) {
				all[move.getDest() - 16].clear();
			} else {
				all[move.getDest() + 16].clear();
			}
			return;
		}

		/* If pawn has moved forward two spaces, set that field to true */
		if (moving.getPieceCode() == 1) {
			if (Math.abs(move.getStart() - move.getDest()) == 32) {
				Pawn pawn = (Pawn) moving;
				if (test == 0) {
					pawn.movedTwo();
				}
			}
		}

		/* Special if cases for castling */
		if (move.getKCast()) {
			/* The king should be contained within the move. */
			/* The rook is found separately here. */
			Rook rook = (Rook) board.getSquares()[move.getDest() + 1].getPiece();
			all[move.getDest()].putPiece(moving);
			moving.setPosition(move.getDest());
			all[move.getDest() - 1].putPiece(rook);
			rook.setPosition(move.getDest() - 1);
			all[move.getStart()].clear();
			all[move.getDest() + 1].clear();
			if (test == 0) {
				System.out.println(move.getStr());
			}
			return;
		} else if (move.getQCast()) {
			Rook rook = (Rook) board.getSquares()[move.getDest() - 2].getPiece();
			all[move.getDest()].putPiece(moving);
			moving.setPosition(move.getDest());
			all[move.getDest() + 1].putPiece(rook);
			rook.setPosition(move.getDest() + 1);
			all[move.getStart()].clear();
			all[move.getDest() - 2].clear();
			if (test == 0) {
				System.out.println(move.getStr());
			}
			return;
		}

		/* Handle promotion case */
		if (move.getPromo() != null) {
			all[move.getDest()].putPiece(move.getPromo());
			move.getPromo().setPosition(move.getDest());
		} else { /* Place original piece on destination square */
			all[move.getDest()].putPiece(moving);
			if (test == 0) {
				moving.setPosition(move.getDest());
			}
		}
		/* Clear piece on the start square */
		all[move.getStart()].clear();
		/* Print out the move made. */
		if (test == 0) {
			System.out.println(move.getStr());
		}
	}


	/* Returns an ArrayList of all possible square positions this PIECE
	 * can maneuver to on BOARD. If CONTROL is:
	 * 0, then we are looking for only the squares pawns control (i.e. not the squares
	 * directly in front of the pawn it can go to, but where it could check a king)
	 * 1, then we are looking for only the squares the pawn control.
	 * 2, then we are looking for everything. */
	public static ArrayList<Integer> findScope(Piece piece, Board board, int control, ArrayList<ArrayList<String>> moveList) {
		int color = piece.getColor();
		int[] possibles = piece.getBases();
		int currPos = piece.getPosition();
		Square[] squares = board.getSquares();
		ArrayList<Integer> ret = new ArrayList<Integer>();
		if (piece.getPieceCode() == 1) { // Deal with pawn case separately

			for (int i = 0; i < possibles.length; i++) {
				int direction = possibles[i];
				int curr = currPos;
				switch (i) {
					case 0:
						if (control == 0 || control == 2) {
							curr += direction;
							if (squares[curr].isEmpty()) {
								ret.add(curr);
							}														
						}
						break;
					case 1:
						if (control == 0 || control == 2) {
							if (!piece.hasMoved()) {
								curr += (direction / 2);
								if (squares[curr].isEmpty()) {
									curr += (direction / 2);
									if (squares[curr].isEmpty()) {
										ret.add(curr);
									}
								}
							}							
						}
						break;
					default:
						if (control == 1 || control == 2) {
							curr += direction;
							if (!squares[curr].isEmpty()) {
								if (squares[curr].getPiece().getColor() != color) {
									ret.add(curr);
								}
							} else { // En Passant Handler
								if (moveList.size() > 1) {
									int outerLength = moveList.size();
									if (color == 1) {
										outerLength--;
									}
									ArrayList<String> moveSet = moveList.get(outerLength - 1);
									int innerLength = moveSet.size();
									String lastMove = moveSet.get(innerLength - 1);
									if (curr / 16 == 5 && color == 1) {
										int enemyPawn = curr - 16;
										if (squares[enemyPawn].isEmpty()) {
											break;
										}
										Piece enemy = squares[enemyPawn].getPiece();
										if (enemy.getPieceCode() == 1 && enemy.getColor() != color) {
											Pawn enPawn = (Pawn) enemy;
											if (lastMove.compareTo(sqToNotation(curr - 16)) == 0 && enPawn.didTwo()) {
												ret.add(curr);
											}
										}
									} else if (curr / 16 == 2 && color == 0) {
										int enemyPawn = curr + 16;
										if (squares[enemyPawn].isEmpty()) {
											break;
										}
										Piece enemy = squares[enemyPawn].getPiece();
										if (enemy.getPieceCode() == 1 && enemy.getColor() != color) {
											Pawn enPawn = (Pawn) enemy;
											if (lastMove.compareTo(sqToNotation(curr + 16)) == 0 && enPawn.didTwo()) {
												ret.add(curr);
											}
										}
									}
								}
							}
						}
						break;
				}
			}

		} else if (piece.getPieceCode() == 3 || 
				   piece.getPieceCode() == 6) { // Deal with knight, king case separately

			for (int i = 0; i < possibles.length; i++) {
				int direction = possibles[i];
				int curr = currPos;
				curr += direction;
				if (inBounds(curr)) {
					if (squares[curr].isEmpty()) {
						ret.add(curr);
					} else {
						if (squares[curr].getPiece().getColor() != color) {
							ret.add(curr);
						}
					}
				}
			}

		} else { // Bishop, Queen, or Rook: Sliding pieces

			for (int i = 0; i < possibles.length; i++) {
				int direction = possibles[i];
				int curr = currPos;
				curr += direction;
				while (inBounds(curr)) {
					if (!squares[curr].isEmpty()) {
						if (squares[curr].getPiece().getColor() == color) {
							break;
						} else if (squares[curr].getPiece().getColor() != color) {
							ret.add(curr);
							break;
						}
					} else {
						ret.add(curr);
						curr += direction;
					}
				}
			}

		}
		return ret;
	}

	/* Returns true if player with COLOR pieces is in check on BOARD. */
	public static boolean isCheck(int color, Board board, ArrayList<ArrayList<String>> moveList) {
		int kingSquare = -1;
		Square[] squares = board.getSquares();
		ArrayList<Integer> enemyScopes = new ArrayList<Integer>();

		for (int i = 0; i < 128; i++) {
			if (squares[i].isEmpty()) {
				continue;
			}
			Piece piece = squares[i].getPiece();
			/* King found */
			if (piece.getPieceCode() == 6 && color == piece.getColor()) {
				kingSquare = i;
			} else if (piece.getColor() != color) {
				ArrayList<Integer> scope = findScope(piece, board, 2, moveList);
				for (int j = 0; j < scope.size(); j++) {
					enemyScopes.add(scope.get(j));
				}
			}
		}

		for (int x = 0; x < enemyScopes.size(); x++) {
			if (enemyScopes.get(x) == kingSquare) {
				return true;
			}
		}

		return false;

	}

	/* Returns a list of all possible moves for player with COLOR pieces on
	 * the current BOARD position. */
	public static ArrayList<Move> moveGenerator(int color, Board board, ArrayList<ArrayList<String>> moveList) {
		ArrayList<Move> ret = new ArrayList<Move>();

		Square[] squares = board.getSquares();

		for (int i = 0; i < 128; i++) {
			if (squares[i].isEmpty()) {
				continue;
			}
			Piece piece = squares[i].getPiece();
			if (piece.getColor() == color) {
				int code = piece.getPieceCode();
				String pattern = "^(([RNBQK]{1}([a-h]+[1-8]|[a-h]|[1-8])?)|([a-h]))?([x])?(([a-h]+[1-8])|(0-0)|(0-0-0))([=][RNBQ])?([+]|[#])?$";
				Pattern anyMove = Pattern.compile(pattern);
				switch (code) {
					case 1: // Pawn
						ArrayList<Integer> pawnThrusts = findScope(piece, board, 0, moveList);

						for (int j = 0; j < pawnThrusts.size(); j++) {
							String moveStr = sqToNotation(pawnThrusts.get(j));
							/* Handle the promotion case */
							if (pawnThrusts.get(j) / 16 == 7 || pawnThrusts.get(j) / 16 == 0) {
								String[] promos = {"=Q", "=R", "=N", "=B"};
								for (int k = 0; k < promos.length; k++) {
									String complete = moveStr + promos[k];
									Matcher mPromo = anyMove.matcher(complete);
									Move move = null;
									if (mPromo.find()) {
										move = Utils.regex_translate(mPromo.group(1), mPromo.group(2), 
											mPromo.group(3), mPromo.group(4), mPromo.group(5), mPromo.group(6),
											mPromo.group(7), mPromo.group(8), mPromo.group(9), mPromo.group(10),
											mPromo.group(11), complete, board, color, moveList);
									}
									if (!sanityCheck(move, color, board, moveList)) {
										ret.add(move);
									}
								}
								break;
							}
							Matcher m = anyMove.matcher(moveStr);
							Move move = null;
							if (m.find()) {
								move = Utils.regex_translate(m.group(1), m.group(2), m.group(3),
												m.group(4), m.group(5), m.group(6),
												m.group(7), m.group(8), m.group(9),
												m.group(10), m.group(11), moveStr, board,
												color, moveList);
							}
							if (!sanityCheck(move, color, board, moveList)) {
								ret.add(move);
							}
						}

						/* Next: PAWN CAPTURES */

						ArrayList<Integer> pawnCaps = findScope(piece, board, 1, moveList);
						for (int j = 0; j < pawnCaps.size(); j++) {
							String moveStr = "";
							String pawnSq = sqToNotation(piece.getPosition());
							String file = pawnSq.substring(0, 1);
							moveStr = file + "x" + sqToNotation(pawnCaps.get(j));
							/* Handle promotion case */
							if (pawnCaps.get(j) / 16 == 7 || pawnCaps.get(j) / 16 == 0) {
								String[] promos = {"=Q", "=R", "=N", "=B"};
								for (int k = 0; k < promos.length; k++) {
									String complete = moveStr + promos[k];
									Matcher mPromo = anyMove.matcher(complete);
									Move move = null;
									if (mPromo.find()) {
										move = Utils.regex_translate(mPromo.group(1), mPromo.group(2), 
											mPromo.group(3), mPromo.group(4), mPromo.group(5), mPromo.group(6),
											mPromo.group(7), mPromo.group(8), mPromo.group(9), mPromo.group(10),
											mPromo.group(11), complete, board, color, moveList);
									}
									if (!sanityCheck(move, color, board, moveList)) {
										ret.add(move);
									}
								}
								break;
							}
							Matcher m = anyMove.matcher(moveStr);
							Move move = null;
							if (m.find()) {
								move = Utils.regex_translate(m.group(1), m.group(2), m.group(3),
												m.group(4), m.group(5), m.group(6),
												m.group(7), m.group(8), m.group(9),
												m.group(10), m.group(11), moveStr, board,
												color, moveList);
							}
							if (!sanityCheck(move, color, board, moveList)) {
								ret.add(move);
							}
						}
						break;
					default: // Anything else

						ArrayList<Integer> pieceMoves = findScope(piece, board, 2, moveList);
						for (int j = 0; j < pieceMoves.size(); j++) {
							String moveStr = piece.getTextRepr().toUpperCase();

							if (!squares[pieceMoves.get(j)].isEmpty()) {
								moveStr = moveStr + "x";
							}
							moveStr = moveStr + sqToNotation(pieceMoves.get(j));
							Matcher m = anyMove.matcher(moveStr);
							Move move = null;
							if (m.find()) {
								move = Utils.regex_translate(m.group(1), m.group(2), m.group(3),
												m.group(4), m.group(5), m.group(6),
												m.group(7), m.group(8), m.group(9),
												m.group(10), m.group(11), moveStr, board,
												color, moveList);
								if (move == null) {
									int piecePos = piece.getPosition();
									String sqName = sqToNotation(piecePos);
									String[] ambiguities = new String[3];
									ambiguities[0] = sqName.substring(0, 1);
									ambiguities[1] = sqName.substring(1, 2);
									ambiguities[2] = sqName;
									int incr = 0;
									while (move == null) {
										String tryMove = moveStr.substring(0, 1) + ambiguities[incr] + moveStr.substring(1);
										Matcher mTry = anyMove.matcher(tryMove);
										if (mTry.find()) {
											move = Utils.regex_translate(mTry.group(1), mTry.group(2), mTry.group(3),
														mTry.group(4), mTry.group(5), mTry.group(6),
														mTry.group(7), mTry.group(8), mTry.group(9),
														mTry.group(10), mTry.group(11), tryMove, board,
														color, moveList);
										}
										incr++;
									}
								}
							}
							if (!sanityCheck(move, color, board, moveList)) {
								ret.add(move);
							}
						}
						break;
				}
			}
		}
		return ret;
	}

	/* Performs a sanity check on the move MOVE made by player with
	 * COLOR pieces on BOARD. Returns true if the move made would leave
	 * the player initiating the move in check. */
	public static boolean sanityCheck(Move move, int color, Board board, ArrayList<ArrayList<String>> moveList) {
		Square[] testSquares = Board.copySquares(board);
		Board testBoard = new Board(testSquares, board.getP1(), board.getP2(), board.getNextToMove());
		makeMove(move, testBoard, 1);
		if (isCheck(color, testBoard, moveList)) {
			return true;
		}
		return false;
	}

}

