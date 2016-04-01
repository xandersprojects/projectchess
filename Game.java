import java.util.Scanner;

/**
 * Represents a chess game.
 */

public class Game {

	Game(int player1, int player2) {
		Player white = null;
		Player black = null;
		if (player1 == 0) {
			white = new HumanPlayer(1);
		} else if (player1 == 1) {
			white = new ComputerPlayer(1);
		}

		if (player2 == 0) {
			black = new HumanPlayer(0);
		} else if (player2 == 1) {
			black = new ComputerPlayer(1);
		}
		_player1 = white;
		_player2 = black;
		_board = new Board(_player1, _player2);
	}


	Player getPlayer1() {
		return _player1;
	}

	Player getPlayer2() {
		return _player2;
	}

	Board getBoard() {
		return _board;
	}

	/* Prints, in text format, the current board. */
	void printBoard() {
		System.out.println("  ===================");
		Square[] current = getBoard().getSquares();
		String rank = "|";
		for (int i = current.length - 1; i >= 0; i--) {

			if (i % 16 == 0) {
				String ranknum = Integer.toString(i / 16 + 1);
				Piece curr = current[i].getPiece();
				if (curr == null) {
					rank = "-" + " " + rank;
				} else {
					rank = curr.getTextRepr() + " " + rank;
				}
				rank = ranknum + " | " + rank;
				System.out.println(rank);
				rank = "|";
				continue;
			}
			if ((i & 8) == 8) {
				continue;
			}
			Piece curr = current[i].getPiece();
			if (curr == null) {
				rank = "-" + " " + rank;
				continue;
			} else {
				rank = curr.getTextRepr() + " " + rank;
			}
		}
		System.out.println("  ===================");
		System.out.println("    a b c d e f g h  ");
	}

	/** While loop controlling game flow. */
	void play() {
		while (true /* Game is not yet over*/) {
			printBoard();
			System.out.println();
			/* White player's move */
			if (_player1.getType() == 0) { /** Player 1 is human */
				HumanPlayer white = (HumanPlayer) _player1;
				Scanner scan = new Scanner(System.in);
				System.out.println("Your move: ");
				String trying = scan.next();
				Move propose = white.proposeMove(_board, trying);
				while (propose == null)  {
					System.out.println("That move is invalid. Try again: ");
					trying = scan.next();
					propose = white.proposeMove(_board, trying);
				}
				/* Actually make the move. */
			}
		}
	}

	/* Player 1 of this game, handling white. */
	private Player _player1;
	/* Player 2 of this game, handling black. */
	private Player _player2;
	/* The board for this game. */
	private Board _board;
	
}