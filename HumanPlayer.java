import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.IllegalArgumentException;

import java.util.ArrayList;

/**
 * Represents a human chess player.
 */

public class HumanPlayer extends Player {
	
	/** Constructor of a HumanPlayer controlling COLOR pieces. */
	HumanPlayer(int color) {
		this.setColor(color);
		this.setType(0);
	}

	/** User inputted proposed move to make. */
	public Move proposeMove(Board board, String move, ArrayList<ArrayList<String>> moveList) {
		String pattern = "^(([RNBQK]{1}([a-h]+[1-8]|[a-h]|[1-8])?)|([a-h]))?([x])?(([a-h]+[1-8])|(0-0)|(0-0-0))([=][RNBQ])?([+]|[#])?$";
		Pattern anyMove = Pattern.compile(pattern);
		Matcher m = anyMove.matcher(move);
		Move propose = null;
		if (m.find()) {

			System.out.println("Found");

			/** A SUMMARY OF THE 11 GROUPS FROM REGEX PATTERN:
			  * 1 - Piece to move along with an optional qualifier, OR a Pawn to move
			  * 2 - Piece to move along with an optional qualifier
			  * 3 - The optional qualifier
			  * 4 - The pawn to move
			  * 5 - The optional "x" denoting a capture
			  * 6 - The target square, OR a castling move
			  * 7 - The target square
			  * 8 - If castling kingside, 0-0 is stored here
			  * 9 - If castling queenside, 0-0-0 is stored here
			  * 10 - Optional Promotion handling information, containing the "=" and the piece to promote to
			  * 11 - Optional check "+" or checkmate "#"
			*/

			propose = Utils.regex_translate(m.group(1), m.group(2), m.group(3),
												m.group(4), m.group(5), m.group(6),
												m.group(7), m.group(8), m.group(9),
												m.group(10), m.group(11), move, board,
												this.getColor(), moveList);
			
		}
		return propose;
	}

	/** Makes a move on BOARD. */
	void makeMove(Move move, Board board) {

		Square[] all = board.getSquares();

		/* Piece in question */
		Piece moving = move.getPiece();
		moving.nowMoved();

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
				pawn.movedTwo();
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
			System.out.println(move.getStr());
			return;
		} else if (move.getQCast()) {
			Rook rook = (Rook) board.getSquares()[move.getDest() - 2].getPiece();
			all[move.getDest()].putPiece(moving);
			moving.setPosition(move.getDest());
			all[move.getDest() + 1].putPiece(rook);
			rook.setPosition(move.getDest() + 1);
			all[move.getStart()].clear();
			all[move.getDest() - 2].clear();
			System.out.println(move.getStr());
			return;
		}

		/* Handle promotion case */
		if (move.getPromo() != null) {
			all[move.getDest()].putPiece(move.getPromo());
			move.getPromo().setPosition(move.getDest());
		} else { /* Place original piece on destination square */
			all[move.getDest()].putPiece(moving);
			moving.setPosition(move.getDest());
		}
		/* Clear piece on the start square */
		all[move.getStart()].clear();
		/* Print out the move made. */
		System.out.println(move.getStr());
	}

	int squareToPos(String square) {
		if (square.length() != 2) {
			throw new IllegalArgumentException();
		}
		return -1;
	}

}