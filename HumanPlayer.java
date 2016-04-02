import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.lang.IllegalArgumentException;

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
	Move proposeMove(Board board, String move) {
		String pattern = "^(([RNBQK]{1}([a-h]|[1-8])?)|([a-h]))?([x])?(([a-h]+[1-8])|(0-0)|(0-0-0))([=][RNBQ])?([+]|[#])?$";
		Pattern anyMove = Pattern.compile(pattern);
		Matcher m = anyMove.matcher(move);
		Move propose;
		if (m.find()) {

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

			System.out.println();
			System.out.println("Group 1: " + m.group(1));
			System.out.println("Group 2: " + m.group(2));
			System.out.println("Group 3: " + m.group(3));
			System.out.println("Group 4: " + m.group(4));
			System.out.println("Group 5: " + m.group(5));
			System.out.println("Group 6: " + m.group(6));
			System.out.println("Group 7: " + m.group(7));
			System.out.println("Group 8: " + m.group(8));
			System.out.println("Group 9: " + m.group(9));
			System.out.println("Group 10: " + m.group(10));
			System.out.println("Group 11: " + m.group(11));

			// propose = new Move(m.group(1), m.group(2), m.group(4), m.group(5));
			System.out.println("Found");
		}
		return null;
	}

	/** Makes a move on BOARD. */
	Move makeMove(Board board) {
		return null;
	}

	int squareToPos(String square) {
		if (square.length() != 2) {
			throw new IllegalArgumentException();
		}
		return -1;
	}

}