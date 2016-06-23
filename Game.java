import java.util.Scanner;
import java.util.ArrayList;

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
			black = new ComputerPlayer(0);
		}
		_player1 = white;
		_player2 = black;
		_movesList = new ArrayList<ArrayList<String>>();
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

	ArrayList<ArrayList<String>> getHistory() {
		return _movesList;
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

	void printMoveHistory() {
		for (int i = 0; i < _movesList.size(); i++) {
			ArrayList<String> completeTurn = _movesList.get(i);
			System.out.print(completeTurn.get(0) + ". ");
			for (int j = 1; j < completeTurn.size(); j++) {
				System.out.print(completeTurn.get(j) + " ");
			}
			System.out.println();
		}
	}

	/** While loop controlling game flow. */
	void play() {
		while (true /* Game is not yet over*/) {
			printBoard();
			System.out.println();
			ArrayList<String> turn = new ArrayList<String>();
			turn.add(String.valueOf(_movesList.size() + 1));
			_movesList.add(turn);
			/* White player's move */
			if (_player1.getType() == 0) { /** Player 1 is human */
				ArrayList<Move> moves = Utils.moveGenerator(1, _board, _movesList);
				for (int i = 0; i < moves.size(); i++) {
					System.out.println(moves.get(i).getStr());
				}
				HumanPlayer white = (HumanPlayer) _player1;
				Scanner scan = new Scanner(System.in);
				System.out.println("Your move: ");
				String trying = scan.next();
				Move propose = white.proposeMove(_board, trying, _movesList);
				while (propose == null)  {
					System.out.println("That move is invalid. Try again: ");
					trying = scan.next();
					propose = white.proposeMove(_board, trying, _movesList);
				}
				/* Check that the move is chess-valid i.e. doesn't move a pinned piece; doesn't move out of check; */
				/* Checking valid move goes in Utils. Players actually make the move. */
				/* Actually make the move. */
				white.makeMove(propose, _board);
				turn.add(propose.getStr());
				if (Utils.isCheck(0, _board, _movesList)) {
					System.out.println("Check!");
				}
			}
			printMoveHistory();
			printBoard();
			System.out.println();
			if (_player2.getType() == 0) { /** Player 2 is human */
				ArrayList<Move> moves = Utils.moveGenerator(0, _board, _movesList);
				for (int i = 0; i < moves.size(); i++) {
					System.out.println(moves.get(i).getStr());
				}
				HumanPlayer black = (HumanPlayer) _player2;
				Scanner scan = new Scanner(System.in);
				System.out.println("Your move: ");
				String trying = scan.next();
				Move propose = black.proposeMove(_board, trying, _movesList);
				while (propose == null) {
					System.out.println("That move is invalid. Try again: ");
					trying = scan.next();
					propose = black.proposeMove(_board, trying, _movesList);
				}
				black.makeMove(propose, _board);
				turn.add(propose.getStr());
				if (Utils.isCheck(1, _board, _movesList)) {
					System.out.println("Check!");
				}
			}
			printMoveHistory();
		}
	}

	/* Player 1 of this game, handling white. */
	private Player _player1;
	/* Player 2 of this game, handling black. */
	private Player _player2;
	/* The board for this game. */
	private Board _board;
	/* List of moves for this game. */
	private ArrayList<ArrayList<String>> _movesList;
	
}