import java.util.ArrayList;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents an AI chess player.
 */

public class ComputerPlayer extends Player {
	
	ComputerPlayer(int color) {
		this.setColor(color);
		this.setType(1);
	}

	/** Proposes a move by calling minimax function. */
	public Move proposeMove(int color, Board board, ArrayList<ArrayList<String>> moveList, int depth) {

		String pattern = "^(([RNBQK]{1}([a-h]+[1-8]|[a-h]|[1-8])?)|([a-h]))?([x])?(([a-h]+[1-8])|(0-0)|(0-0-0))([=][RNBQ])?([+]|[#])?$";
		Pattern anyMove = Pattern.compile(pattern);

		ArrayList<String> moves = Utils.moveStrGenerator(color, board, moveList);

		ScoredMove propose = null;

		for (int a = 0; a < moves.size(); a++) {

			Square[] testSquares = Board.copySquares(board);
			Board testBoard = new Board(testSquares, board.getP1(), board.getP2(), board.getNextToMove());
			testBoard.printBoard();

			String testMove = moves.get(a);
			Matcher m = anyMove.matcher(testMove);
			Move move = null;
			if (m.find()) {
				move = Utils.regex_translate(m.group(1), m.group(2), m.group(3),
								m.group(4), m.group(5), m.group(6),
								m.group(7), m.group(8), m.group(9),
								m.group(10), m.group(11), testMove, testBoard,
								color, moveList);
			}

			// System.out.println("FIRST LAYER: ");
			// System.out.println("ACTUAL BOARD BEFORE: ");
			// board.printBoard();
			// System.out.println("TESTBOARD BEFORE: ");
			// testBoard.printBoard();

			Utils.makeMove(move, testBoard, 0);

			// System.out.println("ACTUAL BOARD AFTER: ");
			// board.printBoard();
			// System.out.println("TESTBOARD AFTER: ");
			// testBoard.printBoard();

			ScoredMove scored = minimax(color, testBoard, moveList, depth - 1, testMove);

			if (propose == null) {
				propose = scored;
			} else if (color == 1) {
				if (scored.getValue() > propose.getValue()) {
					propose = scored;
				}
			} else if (color == 0) {
				if (scored.getValue() < propose.getValue()) {
					propose = scored;
				}
			}

		}

		String finalMoveStr = propose.getMove();
		Matcher m = anyMove.matcher(finalMoveStr);
		Move finalMove = null;
		if (m.find()) {
			finalMove = Utils.regex_translate(m.group(1), m.group(2), m.group(3),
							m.group(4), m.group(5), m.group(6),
							m.group(7), m.group(8), m.group(9),
							m.group(10), m.group(11), finalMoveStr, board,
							color, moveList);
		}

		return finalMove;

	}

	/** Naive recursive minimax algorithm to help the AI choose a move.
	  * Given the COLOR of the AI player, the current BOARD, MOVELIST, and
	  * a set DEPTH of search, returns a ScoredMove which includes the Move
	  * and the centipawn value of that move. */
	public ScoredMove minimax(int color, Board board, ArrayList<ArrayList<String>> moveList, int depth, String moveStr) {

		// System.out.println("Color: " + Integer.toString(color));
		// System.out.println("Now analyzing: " + moveStr + " at depth: " + Integer.toString(depth));

		/* Base case: Depth is 0 */
		if (depth == 0) {
			return new ScoredMove(moveStr, centipawnValue(board));
		}

		String pattern = "^(([RNBQK]{1}([a-h]+[1-8]|[a-h]|[1-8])?)|([a-h]))?([x])?(([a-h]+[1-8])|(0-0)|(0-0-0))([=][RNBQ])?([+]|[#])?$";
		Pattern anyMove = Pattern.compile(pattern);

		/* If we are dealing with the white player, we are trying to maximize our move */
		if (color == 1) {
			ScoredMove best = new ScoredMove(null, Double.NEGATIVE_INFINITY);

			ArrayList<String> nextDepth = Utils.moveStrGenerator(0, board, moveList);

			for (int b = 0; b < nextDepth.size(); b++) {

				Square[] testSquares = Board.copySquares(board);
				Board testBoard = new Board(testSquares, board.getP1(), board.getP2(), board.getNextToMove());

				String testMove = nextDepth.get(b);
				Matcher m = anyMove.matcher(testMove);
				Move move = null;
				if (m.find()) {
					move = Utils.regex_translate(m.group(1), m.group(2), m.group(3),
									m.group(4), m.group(5), m.group(6),
									m.group(7), m.group(8), m.group(9),
									m.group(10), m.group(11), testMove, testBoard,
									0, moveList);
				}

				// System.out.println("BOARD before: ");
				// board.printBoard();
				// System.out.println("TESTBOARD before: ");
				// testBoard.printBoard();

				Utils.makeMove(move, testBoard, 0);

				// System.out.println("BOARD after: ");
				// board.printBoard();
				// System.out.println("TESTBOARD after: ");
				// testBoard.printBoard();

				ScoredMove eval = minimax(0, testBoard, moveList, depth - 1, nextDepth.get(b));
				if (eval.getValue() > best.getValue()) {
					best = eval;
				}

			}
			return best;
		} else {
		/* If we are dealing with the black player, we are trying to minimize our move */
			ScoredMove best = new ScoredMove(null, Double.POSITIVE_INFINITY);

			ArrayList<String> nextDepth = Utils.moveStrGenerator(1, board, moveList);		

			for (int c = 0; c < nextDepth.size(); c++) {

				Square[] testSquares = Board.copySquares(board);
				Board testBoard = new Board(testSquares, board.getP1(), board.getP2(), board.getNextToMove());

				String testMove = nextDepth.get(c);
				Matcher m = anyMove.matcher(testMove);
				Move move = null;
				if (m.find()) {
					move = Utils.regex_translate(m.group(1), m.group(2), m.group(3),
									m.group(4), m.group(5), m.group(6),
									m.group(7), m.group(8), m.group(9),
									m.group(10), m.group(11), testMove, testBoard,
									1, moveList);
				}

				// System.out.println("BOARD before: ");
				// board.printBoard();
				// System.out.println("TESTBOARD before: ");
				// testBoard.printBoard();

				Utils.makeMove(move, testBoard, 0);

				// System.out.println("BOARD after: ");
				// board.printBoard();
				// System.out.println("TESTBOARD after: ");
				// testBoard.printBoard();

				ScoredMove eval = minimax(1, testBoard, moveList, depth - 1, nextDepth.get(c));
				if (eval.getValue() < best.getValue()) {
					best = eval;
				}

			}
			return best;
		}

	}

	/** Calculate the centipawn value of the board. Factors taken into consideration:
	  * 	- MATERIAL
	  */
	public double centipawnValue(Board board) {
		return materialCount(board);
	}

	/** Returns nominal material balance count of this BOARD. */
	public double materialCount(Board board) {
		double count = 0.0;
		Square[] all = board.getSquares();
		for (int i = 0; i < 128; i++) {
			if (all[i].isEmpty()) {
				continue;
			}
			Piece piece = all[i].getPiece();
			if (piece.getColor() == 1) {
				count += piece.getValue();
			} else {
				count -= piece.getValue();
			}
		}
		return count;
	}

}