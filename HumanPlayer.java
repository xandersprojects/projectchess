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
		String pattern = "([RNBQK]?|[a-h]?)([a-h]?|[1-8]?)([x]?)([a-h]+[1-8])([=][RNBQ])?([+]|[#])?";
		Pattern anyMove = Pattern.compile(pattern);
		Matcher m = anyMove.matcher(move);
		Move propose;
		if (m.find()) {
			propose = new Move(m.group(1), m.group(2), m.group(4), m.group(5));
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