import java.util.ArrayList;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents an AI chess player.
 */

public class ComputerPlayer extends Player {
	
	ComputerPlayer(int color, long[] zobrist) {
		this.setColor(color);
		_zobrist = zobrist;
		this.setType(1);
	}

	/** Proposes a move by calling minimax function. */
	public Move proposeMove(int color, Board board, ArrayList<ArrayList<String>> moveList, int depth) {

		String pattern = "^(([RNBQK]{1}([a-h]+[1-8]|[a-h]|[1-8])?)|([a-h]))?([x])?(([a-h]+[1-8])|(0-0)|(0-0-0))([=][RNBQ])?([+]|[#])?$";
		Pattern anyMove = Pattern.compile(pattern);

		ArrayList<String> moves = Utils.moveStrGenerator(color, board, moveList);
		ArrayList<String> allProposed = new ArrayList<String>();
		ArrayList<Double> allProposedVals = new ArrayList<Double>();

		ScoredMove propose = null;

		for (int a = 0; a < moves.size(); a++) {

			Square[] testSquares = Board.copySquares(board);
			Board testBoard = new Board(testSquares, board.getP1(), board.getP2(), board.getNextToMove());

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

			Utils.makeMove(move, testBoard, 0);

			int toPass = 1;
			if (color == 1) {
				toPass = 0;
			}

			ScoredMove alpha = new ScoredMove(null, Double.NEGATIVE_INFINITY);
			ScoredMove beta = new ScoredMove(null, Double.POSITIVE_INFINITY);

			ScoredMove scored = minimaxABP(toPass, testBoard, moveList, depth - 1, testMove, alpha, beta);

			// ScoredMove scored = minimax(toPass, testBoard, moveList, depth - 1, testMove);

			if (propose == null) {
				propose = scored;
				allProposed.add(scored.getMove());
				allProposedVals.add(scored.getValue());
			} else if (color == 1) {
				if (scored.getValue() > propose.getValue()) {
					propose = scored;
					allProposed.add(scored.getMove());
					allProposedVals.add(scored.getValue());
				}
			} else if (color == 0) {
				if (scored.getValue() < propose.getValue()) {
					propose = scored;
					allProposed.add(scored.getMove());
					allProposedVals.add(scored.getValue());
				}
			}

		}

		String finalMoveStr = propose.getMove();
		if (finalMoveStr == null) {
			finalMoveStr = moves.get(0);
		}
		Matcher m = anyMove.matcher(finalMoveStr);
		Move finalMove = null;
		if (m.find()) {
			finalMove = Utils.regex_translate(m.group(1), m.group(2), m.group(3),
							m.group(4), m.group(5), m.group(6),
							m.group(7), m.group(8), m.group(9),
							m.group(10), m.group(11), finalMoveStr, board,
							color, moveList);
		}

		System.out.println("All instantaneously best moves: ");
		for (int a = 0; a < allProposed.size(); a++) {
			if (a == allProposed.size() - 1) {
				System.out.println("The final move was accepted as best: " + allProposed.get(a) + " at value " + Double.toString(allProposedVals.get(a)) + ".");
			} else {
				System.out.println(allProposed.get(a) + " was rejected at value " + Double.toString(allProposedVals.get(a)) + ".");
			}
		}

		return finalMove;

	}

	/** Naive recursive minimax algorithm to help the AI choose a move.
	  * Given the COLOR of the AI player, the current BOARD, MOVELIST, and
	  * a set DEPTH of search, returns a ScoredMove which includes the Move
	  * and the centipawn value of that move.
	  * Uses Alpha Beta Pruning to speedup search. */
	public ScoredMove minimaxABP(int color, Board board, ArrayList<ArrayList<String>> moveList, int depth, String moveStr,
								 ScoredMove alpha, ScoredMove beta) {

		/* Base case: Depth is 0 */
		if (depth == 0) {
			return new ScoredMove(moveStr, centipawnValue(board, moveList));
		}

		String pattern = "^(([RNBQK]{1}([a-h]+[1-8]|[a-h]|[1-8])?)|([a-h]))?([x])?(([a-h]+[1-8])|(0-0)|(0-0-0))([=][RNBQ])?([+]|[#])?$";
		Pattern anyMove = Pattern.compile(pattern);

		/* If we are dealing with the white player, we are trying to maximize our move */
		if (color == 1) {

			ArrayList<String> nextDepth = Utils.moveStrGenerator(1, board, moveList);

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
									1, moveList);
				}

				Utils.makeMove(move, testBoard, 0);

				if (Utils.isCheckMate(0, testBoard, moveList)) {
					return new ScoredMove(moveStr, Double.POSITIVE_INFINITY);
				}

				ScoredMove eval = minimaxABP(0, testBoard, moveList, depth - 1, moveStr, alpha, beta);

				if (eval.getValue() >= beta.getValue()) {
					return beta;
				}
				if (eval.getValue() > alpha.getValue()) {
					alpha = eval;
				}

			}
			return alpha;
		} else {
		/* If we are dealing with the black player, we are trying to minimize our move */

			ArrayList<String> nextDepth = Utils.moveStrGenerator(0, board, moveList);		

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
									0, moveList);
				}

				Utils.makeMove(move, testBoard, 0);

				if (Utils.isCheckMate(1, testBoard, moveList)) {
					return new ScoredMove(moveStr, Double.NEGATIVE_INFINITY);
				}

				ScoredMove eval = minimaxABP(1, testBoard, moveList, depth - 1, moveStr, alpha, beta);
				
				if (eval.getValue() <= alpha.getValue()) {
					return alpha;
				}
				if (eval.getValue() < beta.getValue()) {
					beta = eval;
				}

			}
			return beta;
		}

	}

	/** Calculate the centipawn value of the board. Factors taken into consideration:
	  * 	- MATERIAL
	  */
	public static double centipawnValue(Board board, ArrayList<ArrayList<String>> moveList) {
		return materialCount(board) + centerPieces(board);
	}

	/** Returns how many pieces there are in the center four squares of the board.
	  * Positive for white pieces.
	  * Negative for black pieces. */
	public static double centerPieces(Board board) {
		Square[] squares = board.getSquares();
		int[] centers = {51, 52, 67, 68};
		double ret = 0.0;
		for (int i = 0; i < centers.length; i++) {
			int centerSq = centers[i];
			if (squares[centerSq].isEmpty()) {
				continue;
			}
			if (squares[centerSq].getPiece().getColor() == 1) {
				ret += 0.5;
			} else {
				ret -= 0.5;
			}
		}
		return ret;
	}

	/** Returns nominal material balance count of this BOARD. */
	public static double materialCount(Board board) {
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

	/* Zobrist table generated for this game. */
	long[] _zobrist;

}